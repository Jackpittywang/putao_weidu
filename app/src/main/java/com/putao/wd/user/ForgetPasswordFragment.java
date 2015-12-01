package com.putao.wd.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.sunnybear.library.controller.BasicFragment;

import butterknife.Bind;
import butterknife.OnClick;

/*
**忘记密码
**create by wangou
 */
public class ForgetPasswordFragment extends PTWDFragment implements View.OnClickListener {
    @Bind(R.id.btn_nextstep)
    Button btn_nextstep;

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

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forget})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_nextstep://登录
                startFragment(ResetPasswordFragment.class);
                break;

        }
    }
}