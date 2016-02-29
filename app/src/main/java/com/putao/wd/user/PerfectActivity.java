package com.putao.wd.user;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.UploadApi;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.SelectPopupWindow;
import com.putao.wd.model.UserInfo;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.UploadFileTask;
import com.sunnybear.library.model.http.callback.JSONObjectCallback;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;
import com.sunnybear.library.view.image.ImageDraweeView;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 完善用户信息
 * Created by guchenkai on 2015/11/29.
 */
public class PerfectActivity extends PTWDActivity implements View.OnClickListener {
    public static final String EVENT_USER_INFO_SAVE_SUCCESS = "user_info_save_success";
    public static final String ET_MOBILE = "et_mobile";
    public static final String ET_PASSWORD = "et_password";
    public static final String ET_SMS_VERIFY = "et_sms_verify";
    public static final String NICK_NAME = "nick_name";
    public static final String USER_INFO = "user_info";
    @Bind(R.id.ll_main)
    LinearLayout ll_main;
    @Bind(R.id.rl_header_icon)
    RelativeLayout rl_header_icon;
    @Bind(R.id.iv_header_icon)
    ImageDraweeView iv_header_icon;
    @Bind(R.id.et_nickname)
    CleanableEditText et_nickname;
    @Bind(R.id.et_intro)
    CleanableEditText et_intro;


    private JSONObject mObject;
    private SelectPopupWindow mSelectPopupWindow;
    private final int CAMERA_REQCODE = 1;
    private final int ALBUM_REQCODE = 2;
    private final int CHANGE_NICK = 3;
    private final int CHANGE_INFO = 4;
    //=====================上传相关===========================
    private String uploadToken;//上传token
    private File uploadFile;//上传文件
    private String sha1;//上传文件sha1

    private String filePath;//头像文件路径
    private String nick_name;//用户昵称
    private String profile;//个人简介

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = (Bundle) msg.obj;
            //上传PHP服务器
            upload(bundle.getString("ext"), bundle.getString("filename"), bundle.getString("hash"));
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_perfect;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        filePath = GlobalApplication.sdCardPath + File.separator + "head_icon.jpg";
        IndexActivity.isNotRefreshUserInfo = false;
        et_nickname.setText(AccountHelper.getUserNickName());
//        initInfo();
        mSelectPopupWindow = new SelectPopupWindow(mContext) {
            @Override
            public void onFirstClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQCODE);
            }

            @Override
            public void onSecondClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, ALBUM_REQCODE);
            }
        };
    }

    @Override
    public void onRightAction() {
        if (et_nickname.length() < 2) {
            ToastUtils.showToastShort(mContext, "请设置2-24字的用户昵称");

        }
        String ext = "";
        String filename = "";
        String hash = "";
        if (null != mObject) {
            ext = mObject.getString("ext");
            filename = mObject.getString("filename");
            hash = mObject.getString("hash");
        }
        networkRequest(UserApi.userAdd(ext, filename, hash, et_nickname.getText().toString(), et_intro.getText().toString()),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        perfect();
                    }

                    @Override
                    public void onFinish(String url, boolean isSuccess, String msg) {
                        super.onFinish(url, isSuccess, msg);
                        if (!TextUtils.isEmpty(msg))
                            ToastUtils.showToastShort(mContext, msg);
                    }
                });
    }

    private void perfect() {
        startActivity(IndexActivity.class);
        EventBusHelper.post(EVENT_USER_INFO_SAVE_SUCCESS, EVENT_USER_INFO_SAVE_SUCCESS);
        EventBusHelper.post(LoginActivity.EVENT_LOGIN, LoginActivity.EVENT_LOGIN);
        finish();
    }

/*    private void initInfo() {
        networkRequest(UserApi.getUserInfo(), new SimpleFastJsonCallback<UserInfo>(UserInfo.class, loading) {
            @Override
            public void onSuccess(String url, UserInfo result) {
                et_nickname.setText(result.getNick_name());
                AccountHelper.setUserInfo(result);
            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                super.onFailure(url, statusCode, msg);
                ToastUtils.showToastLong(mContext, "登录失败请重新登录");
            }
        });
    }*/

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.rl_header_icon})
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.rl_header_icon://选择用户头像
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
                uploadFile();
            }
        });
    }

    /**
     * 校检sha1
     *
     * @param uploadFilePath 上传文件路径
     */
    private void checkSha1(String uploadFilePath) {
        uploadFile = new File(uploadFilePath);
        sha1 = FileUtils.getSHA1ByFile(uploadFile);
        networkRequest(UploadApi.checkSha1(sha1), new JSONObjectCallback() {
            @Override
            public void onSuccess(String url, JSONObject result) {
                String hash = result.getString("hash");
                if (StringUtils.isEmpty(hash))
                    getUploadToken();
                else
                    upload("jpg", hash, hash);
            }

            @Override
            public void onCacheSuccess(String url, JSONObject result) {

            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {

            }
        });
    }

    /**
     * 上传文件
     */
    private void uploadFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UploadApi.uploadFile(uploadToken, sha1, uploadFile, new UploadFileTask.UploadCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        Logger.d(result.toJSONString());
                        mObject = result;
                       /* Bundle bundle = new Bundle();
                        bundle.putString("ext", result.getString("ext"));
                        bundle.putString("filename", result.getString("filename"));
                        bundle.putString("hash", result.getString("hash"));
                        //上传PHP服务器
                        mHandler.sendMessage(Message.obtain(mHandler, 0x01, bundle));*/
                    }
                });
            }
        }).start();
    }

    /**
     * 上传PHP服务器
     */
    private void upload(String ext, String filename, String filehash) {
        networkRequest(UserApi.userEdit(ext, filename, filehash),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        Logger.i("保存用户信息");
                        EventBusHelper.post(EVENT_USER_INFO_SAVE_SUCCESS, EVENT_USER_INFO_SAVE_SUCCESS);
                        EventBusHelper.post(LoginActivity.EVENT_LOGIN, LoginActivity.EVENT_LOGIN);
                        startActivity(IndexActivity.class);
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            switch (requestCode) {
                case CAMERA_REQCODE://相机选择
                    Bundle bundle = data.getExtras();
                    bitmap = (Bitmap) bundle.get("data");
//                    bitmap = ImageUtils.getSmallBitmap(picturePath, 320, 320);
                    iv_header_icon.setDefaultImage(bitmap);
                    ImageUtils.bitmapOutSdCard(bitmap, filePath);
                    checkSha1(filePath);
                    break;
                case ALBUM_REQCODE://相册选择
                    ToastUtils.showToastShort(this, "系统图库返回");
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);

                    Logger.d(picturePath);
                    cursor.close();
                    bitmap = ImageUtils.getSmallBitmap(picturePath, 320, 320);
                    iv_header_icon.resize(320, 320).setDefaultImage(bitmap);
                    ImageUtils.bitmapOutSdCard(bitmap, filePath);
                    checkSha1(filePath);
                    break;
            }
        }
    }
}
