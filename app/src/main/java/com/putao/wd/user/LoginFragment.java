package com.putao.wd.user;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountApi;
import com.putao.wd.account.AccountCallback;
import com.putao.wd.base.PTWDFragment;
import com.sunnybear.library.view.CleanableEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录页面
 * Created by guchenkai on 2015/11/29.
 */
public class LoginFragment extends PTWDFragment implements View.OnClickListener {
    @Bind(R.id.et_mobile)
    CleanableEditText et_mobile;
    @Bind(R.id.et_password)
    CleanableEditText et_password;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavgation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forget})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登录
                networkRequest(AccountApi.login(et_mobile.getText().toString(), et_password.getText().toString()),
                        new AccountCallback(loading) {
                            @Override
                            public void onSuccess(JSONObject result) {

                            }

                            @Override
                            public void onError(String error_msg) {

                            }
                        });
                break;
            case R.id.tv_register://注册新用户

                break;
            case R.id.tv_forget://忘记密码
                startFragment(ForgetPasswordFragment.class);
                break;
        }
    }
}
