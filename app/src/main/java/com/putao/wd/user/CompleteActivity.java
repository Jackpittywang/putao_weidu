package com.putao.wd.user;

import android.os.Bundle;
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
import com.sunnybear.library.view.CleanableEditText;

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
    @Bind(R.id.et_nickname)
    CleanableEditText et_nickname;
    @Bind(R.id.et_intro)
    CleanableEditText et_intro;

    private SelectPopupWindow mSelectPopupWindow;

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

            }

            @Override
            public void onSecondClick(View v) {

            }
        };
        networkRequest(UserApi.getUserInfo(), new SimpleFastJsonCallback<UserInfo>(UserInfo.class, loading) {
            @Override
            public void onSuccess(String url, UserInfo result) {
                Logger.i("js", "获取用户信息");
                et_nickname.setText(result.getNick_name());
                et_intro.setText(result.getProfile());
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

    @Override
    public void onRightAction() {
        super.onRightAction();
        //保存用户信息
        //String nick_name, String head_img, String profile
        networkRequest(UserApi.userEdit("", "", ""), new SimpleFastJsonCallback<ArrayList<String>>(String.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<String> result) {
                Logger.i("保存用户信息");
                // ActivityManager.getInstance().finishCurrentActivity();
            }
        });
    }
}
