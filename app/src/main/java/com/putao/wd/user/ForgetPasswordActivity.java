package com.putao.wd.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountApi;
import com.putao.wd.account.AccountCallback;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.UserInfo;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 忘记密码
 * create by wangou
 */
public class ForgetPasswordActivity extends PTWDActivity implements View.OnClickListener, TextWatcher {
    @Bind(R.id.btn_nextstep)
    Button btn_nextstep;//下一步
    @Bind(R.id.et_mobile)
    CleanableEditText et_mobile;//手机号
    @Bind(R.id.et_sms_verify)
    CleanableEditText et_sms_verify;//获取验证码
    @Bind(R.id.et_password)
    CleanableEditText et_password;//密码

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        et_mobile.addTextChangedListener(this);
        //et_password.addTextChangedListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    //-------|下一步-------------|获取验证码
    @OnClick({R.id.btn_nextstep, R.id.tb_get_verify})
    @Override
    public void onClick(View v) {
        final String mobile = et_mobile.getText().toString();
        switch (v.getId()) {
            case R.id.btn_nextstep://下一步
                if (11 != et_mobile.getText().toString().length() || "" == et_mobile.getText().toString().trim()) {
                    ToastUtils.showToastLong(mContext, "请输入正确手机号码");
                } else {
                    String smsCode = et_sms_verify.getText().toString();
                    final String password = et_password.getText().toString();
                    networkRequest(AccountApi.forget(mobile, smsCode, password), new AccountCallback(loading) {

                        @Override
                        public void onSuccess(JSONObject result) {
                            networkRequest(AccountApi.login(mobile, password),
                                    new AccountCallback(loading) {
                                        @Override
                                        public void onSuccess(JSONObject result) {
                                            AccountHelper.setCurrentUid(result.getString("uid"));
                                            AccountHelper.setCurrentToken(result.getString("token"));
                                            checkLogin();
                                        }

                                        @Override
                                        public void onError(String error_msg) {
                                        }
                                    });
                        }

                        @Override
                        public void onError(String error_msg) {
                            ToastUtils.showToastLong(mContext, error_msg);
                        }
                    });
                }
//                    startActivity(ResetPasswordAcitivity.class);

                break;
            case R.id.tb_get_verify://获取验证码
                if (StringUtils.isEmpty(mobile)) {
                    ToastUtils.showToastLong(mContext, "请输入手机号码");
                    return;
                }
                networkRequest(AccountApi.sendVerifyCode(mobile, AccountConstants.Action.ACTION_FORGET),
                        new AccountCallback(loading) {
                            @Override
                            public void onSuccess(JSONObject result) {
                                ToastUtils.showToastLong(mContext, "验证码发送成功");
                            }

                            @Override
                            public void onError(String error_msg) {
                                ToastUtils.showToastLong(mContext, error_msg);
                            }
                        });
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            btn_nextstep.setClickable(true);
            btn_nextstep.setBackgroundResource(R.drawable.btn_get_focus);
        } else {
            btn_nextstep.setClickable(false);
            btn_nextstep.setBackgroundResource(R.drawable.btn_los_focus);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 验证登录
     */
    private void checkLogin() {
        networkRequest(UserApi.getUserInfo(), new SimpleFastJsonCallback<UserInfo>(UserInfo.class, loading) {
            @Override
            public void onSuccess(String url, UserInfo result) {
                AccountHelper.setUserInfo(result);
                loading.dismiss();
                ActivityManager.getInstance().finishActivity(LoginActivity.class);
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        EventBusHelper.post(LoginActivity.EVENT_LOGIN, LoginActivity.EVENT_LOGIN);
    }
}