package com.putao.wd.user;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.UploadApi;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.SelectPopupWindow;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.model.http.UploadFileTask;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;
import com.sunnybear.library.view.image.ImageDraweeView;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 完善用户信息
 * Created by guchenkai on 2015/11/29.
 */
public class CompleteActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.ll_main)
    LinearLayout ll_main;
    @Bind(R.id.iv_header_icon)
    ImageDraweeView iv_header_icon;
    @Bind(R.id.et_nickname)
    CleanableEditText et_nickname;
    @Bind(R.id.et_intro)
    CleanableEditText et_intro;

    private SelectPopupWindow mSelectPopupWindow;
    private String img_url;
    private String img_path;
    private final int CAMERA_REQCODE = 1;
    private final int ALBUM_REQCODE = 2;
    //=====================上传相关===========================
    private String uploadToken;//上传token

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complete;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        img_path = GlobalApplication.sdCardPath + File.separator + "head_icon.png";
        mSelectPopupWindow = new SelectPopupWindow(mContext) {
            @Override
            public void onFirstClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQCODE);
            }

            @Override
            public void onSecondClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, ALBUM_REQCODE);
            }
        };
//        networkRequest(UserApi.getUserInfo(), new SimpleFastJsonCallback<UserInfo>(UserInfo.class, loading) {
//            @Override
//            public void onSuccess(String url, UserInfo result) {
//                Logger.i("获取用户信息");
//                et_nickname.setText(result.getNick_name());
//                et_intro.setText(result.getProfile());
//                img_url = result.getHead_img();
//            }
//        });
        getUploadToken();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.iv_select_icon)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_select_icon://选择用户头像
                mSelectPopupWindow.show(ll_main);
                break;
        }
    }

    /**
     * 获得上传参数
     */
    private void getUploadToken() {
        networkRequest(UserApi.getUploadToken(), new SimpleFastJsonCallback<String>(String.class, null) {
            @Override
            public void onSuccess(String url, String result) {
                JSONObject jsonObject = JSON.parseObject(result);
                uploadToken = jsonObject.getString("uploadToken");
                Logger.d(uploadToken);
//                uploadFile(uploadToken, img_path);
            }
        });
    }

    /**
     * 上传文件
     *
     * @param uploadToken    上传token
     * @param uploadFilePath 上传文件路径
     */
    private void uploadFile(final String uploadToken, String uploadFilePath) {
        final File file = new File(uploadFilePath);
        new Thread() {
            @Override
            public void run() {
                UploadApi.uploadFile(uploadToken, file, new UploadFileTask.UploadCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        Logger.d(result.toJSONString());
                    }
                });
            }
        }.start();
    }

    /**
     * 保存用户信息
     * by yanghx
     * 请求参数 String类型 昵称、图片url、个人简介
     */
    @Override
    public void onRightAction() {
        super.onRightAction();
        String nick_name = et_nickname.getText().toString();
        String profile = et_intro.getText().toString();
        networkRequest(UserApi.userEdit(nick_name, img_url, profile), new SimpleFastJsonCallback<ArrayList<String>>(String.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<String> result) {
                Logger.i("保存用户信息");
                ActivityManager.getInstance().finishCurrentActivity();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            switch (requestCode) {
                case CAMERA_REQCODE:
                    Bundle bundle = data.getExtras();
                    bitmap = (Bitmap) bundle.get("data");
                    iv_header_icon.setDefaultImage(bitmap);
                    ImageUtils.bitmapOutSdCard(bitmap, img_path);
                    break;
                case ALBUM_REQCODE:
                    ToastUtils.showToastShort(this, "系统图库返回");
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    Logger.i("pt", picturePath);
                    cursor.close();
                    bitmap = ImageUtils.getSmallBitmap(picturePath, 320, 320);
                    iv_header_icon.resize(320, 320).setDefaultImage(bitmap);
                    break;
            }
        }
    }
}
