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
import com.putao.wd.base.PTWDFragment;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册页面
 * Created by guchenkai on 2015/11/29.
 */
public class RegisterFragment extends PTWDFragment implements View.OnClickListener,TextWatcher {
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
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
                String mobile = et_mobile.getText().toString();
                if (StringUtils.isEmpty(mobile) || ""==mobile.trim() ) {
                    ToastUtils.showToastLong(mActivity, "请输入正确的手机号码");
                    return;
                }
                networkRequest(AccountApi.sendVerifyCode(mobile, AccountConstants.Action.ACTION_REGISTER),
                        new AccountCallback(loading) {
                            @Override
                            public void onSuccess(JSONObject result) {
                                ToastUtils.showToastLong(mActivity, "验证码发送成功");
                            }

                            @Override
                            public void onError(String error_msg) {
                                ToastUtils.showToastLong(mActivity, error_msg);
                            }
                        });
                break;
            case R.id.btn_next://下一步

                break;
            case R.id.tv_user_protocol://用户服务协议
                startFragment(ProtocolFragment.class);
                break;
        }
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
