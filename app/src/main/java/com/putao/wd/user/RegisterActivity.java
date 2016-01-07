package com.putao.wd.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountApi;
import com.putao.wd.account.AccountCallback;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.TimeButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册页面
 * Created by guchenkai on 2015/11/29.
 */
public class RegisterActivity extends PTWDActivity implements View.OnClickListener, TextWatcher, SwitchButton.OnSwitchClickListener {
    @Bind(R.id.et_mobile)
    CleanableEditText et_mobile;
    @Bind(R.id.et_graph_verify)
    CleanableEditText et_graph_verify;
    @Bind(R.id.et_sms_verify)
    CleanableEditText et_sms_verify;
    @Bind(R.id.et_password)
    CleanableEditText et_password;
    @Bind(R.id.btn_next)
    Button btn_next;
    @Bind(R.id.tb_get_verify)
    TimeButton tb_get_verify;
    @Bind(R.id.btn_is_look)
    SwitchButton btn_is_look;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        et_mobile.addTextChangedListener(this);
        et_password.addTextChangedListener(this);
        et_sms_verify.addTextChangedListener(this);
        btn_is_look.setOnSwitchClickListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.tb_get_verify, R.id.btn_next, R.id.tv_user_protocol})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tb_get_verify://获取验证码
                getVerifyCode();
                break;
            case R.id.btn_next://下一步
                String phone = et_mobile.getText().toString();
                String password = et_password.getText().toString();
                String sms_verify = et_sms_verify.getText().toString();
                networkRequest(AccountApi.register(phone, password, sms_verify), new AccountCallback(loading) {
                    @Override
                    public void onSuccess(JSONObject result) {
                        AccountHelper.login(result);
                        startActivity(CompleteActivity.class);
                        loading.dismiss();
                        finish();
                    }

                    @Override
                    public void onError(String error_msg) {
                        loading.dismiss();
                        ToastUtils.showToastShort(mContext, error_msg);
                    }
                });
                break;
            case R.id.tv_user_protocol://用户服务协议
                startActivity(ProtocolActivity.class);
                break;
        }
    }

    @Override
    public void onSwitchClick(View v, boolean isSelect) {
        if (!isSelect) //加密
            et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        else //不加密
            et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        String mobile = et_mobile.getText().toString().trim();
        if (StringUtils.isEmpty(mobile)) {
            tb_get_verify.reset();
            ToastUtils.showToastLong(mContext, "请输入手机号码");
            return;
        }
        networkRequest(AccountApi.sendVerifyCode(mobile, AccountConstants.Action.ACTION_REGISTER), new AccountCallback(loading) {
            @Override
            public void onSuccess(JSONObject result) {
                Logger.d(result.toJSONString());
                if (GlobalApplication.isDebug)
                    ToastUtils.showToastLong(mContext, "1234");
                else
                    ToastUtils.showToastLong(mContext, "验证码已发送");
            }

            @Override
            public void onError(String error_msg) {
                tb_get_verify.reset();
                ToastUtils.showToastLong(mContext, "您的手机已注册过了，请试一下登录吧");
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() > 0) {
            btn_next.setClickable(true);
            btn_next.setBackgroundResource(R.drawable.btn_get_focus);
        } else {
            btn_next.setClickable(false);
            btn_next.setBackgroundResource(R.drawable.btn_los_focus);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
