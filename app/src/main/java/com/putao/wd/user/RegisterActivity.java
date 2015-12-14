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
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;
import com.sunnybear.library.view.TimeButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册页面
 * Created by guchenkai on 2015/11/29.
 */
public class RegisterActivity extends PTWDActivity implements View.OnClickListener, TextWatcher {
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
                //String mobile, String password, String code
//                String phone = et_mobile.getText().toString();
//                String sms_verify = et_sms_verify.getText().toString();
//                String password = et_password.getText().toString();
//                networkRequest(AccountApi.register(phone, password, sms_verify), new AccountCallback(loading) {
//                    @Override
//                    public void onSuccess(JSONObject result) {
//                        Logger.d(result.toJSONString());
//                    }
//
//                    @Override
//                    public void onError(String error_msg) {
//                        ToastUtils.showToastLong(mContext, error_msg);
//                    }
//                });
                startActivity(CompleteActivity.class);
                break;
            case R.id.tv_user_protocol://用户服务协议
                startActivity(ProtocolActivity.class);
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        String mobile = et_mobile.getText().toString().trim();
        if (StringUtils.isEmpty(mobile) || StringUtils.checkMobileFormat(mobile)) {
            ToastUtils.showToastLong(mContext, "您输入的手机号码有误，请再检查一下吧");
            tb_get_verify.reset();
            return;
        }
        networkRequest(AccountApi.checkMobile(mobile), new AccountCallback(loading) {
            @Override
            public void onSuccess(JSONObject result) {
                Logger.d(result.toJSONString());
                ToastUtils.showToastLong(mContext, "1234");
            }

            @Override
            public void onError(String error_msg) {
                ToastUtils.showToastLong(mContext, "您的手机已注册过了，请试一下登陆吧");
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
