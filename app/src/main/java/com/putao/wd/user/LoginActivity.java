package com.putao.wd.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountApi;
import com.putao.wd.account.AccountCallback;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录模块
 * Created by guchenkai on 2015/11/27.
 */
public class LoginActivity extends PTWDActivity implements View.OnClickListener, TextWatcher {
    public static final String EVENT_LOGIN = "login";

    @Bind(R.id.et_mobile)
    CleanableEditText et_mobile;
    @Bind(R.id.et_password)
    CleanableEditText et_password;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.rl_graph_verify)
    RelativeLayout rl_graph_verify;//图形验证码

    private int mErrorCount = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        et_mobile.addTextChangedListener(this);
        et_password.addTextChangedListener(this);
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
                                AccountHelper.login(result);
                                networkRequest(UserApi.login(), new SimpleFastJsonCallback<ArrayList<String>>(String.class, null) {
                                    @Override
                                    public void onSuccess(String url, ArrayList<String> result) {
                                        Logger.i("登录成功");
                                        ActivityManager.getInstance().finishCurrentActivity();
                                    }
                                });
                            }

                            @Override
                            public void onError(String error_msg) {
                                ToastUtils.showToastLong(mContext, error_msg);
                                mErrorCount++;
                                if (mErrorCount == 3)
                                    rl_graph_verify.setVisibility(View.VISIBLE);
                            }
                        });
                break;
            case R.id.tv_register://注册新用户
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_forget://忘记密码
                startActivity(ForgetPasswordActivity.class);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            btn_login.setClickable(true);
            btn_login.setBackgroundResource(R.drawable.btn_get_focus);
        } else {
            btn_login.setClickable(false);
            btn_login.setBackgroundResource(R.drawable.btn_los_focus);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        EventBusHelper.post(EVENT_LOGIN, EVENT_LOGIN);
    }
}
