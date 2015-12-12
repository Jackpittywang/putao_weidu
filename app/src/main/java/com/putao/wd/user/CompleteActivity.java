package com.putao.wd.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.SelectPopupWindow;
import com.putao.wd.model.UserInfo;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;
import com.sunnybear.library.view.image.ImageDraweeView;

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
    private final int CAMERA_REQCODE = 1;
    private final int ALBUM_REQCODE = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complete;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        mSelectPopupWindow = new SelectPopupWindow(mContext) {
            @Override
            public void onFirstClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAMERA_REQCODE);
                mSelectPopupWindow.dismiss();
            }

            @Override
            public void onSecondClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQCODE);
            }
        };
        networkRequest(UserApi.getUserInfo(), new SimpleFastJsonCallback<UserInfo>(UserInfo.class, loading) {
            @Override
            public void onSuccess(String url, UserInfo result) {
                Logger.i("获取用户信息");
                et_nickname.setText(result.getNick_name());
                et_intro.setText(result.getProfile());
                img_url = result.getHead_img();
            }
        });

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
        if (requestCode == CAMERA_REQCODE) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                iv_header_icon.setImageBitmap(bitmap);
                ToastUtils.showToastShort(this, "Image saved to:\n" +
                        data.getData());
            }
        }else if (requestCode == ALBUM_REQCODE) {
            if (resultCode == RESULT_OK) {

            }
        }
    }
}
