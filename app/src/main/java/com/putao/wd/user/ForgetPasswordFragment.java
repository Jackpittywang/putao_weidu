package com.putao.wd.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountApi;
import com.putao.wd.account.AccountCallback;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.base.PTWDFragment;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;

import butterknife.Bind;
import butterknife.OnClick;

/*
**忘记密码
**create by wangou
 */
public class ForgetPasswordFragment extends PTWDFragment implements View.OnClickListener {
    @Bind(R.id.btn_nextstep)
    Button btn_nextstep;//下一步
    @Bind(R.id.et_mobile)
    CleanableEditText et_mobile;//手机号
    @Bind(R.id.et_sms_verify)
    CleanableEditText et_sms_verify;//获取验证码

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget_password;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    //-------|下一步-------------|获取验证码
    @OnClick({R.id.btn_nextstep, R.id.tb_get_verify})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_nextstep://下一步
                startFragment(ResetPasswordFragment.class);
                break;
            case R.id.tb_get_verify://获取验证码
                String mobile = et_mobile.getText().toString();
                if (StringUtils.isEmpty(mobile)) {
                    ToastUtils.showToastLong(mActivity, "请输入手机号码");
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
        }
    }
}