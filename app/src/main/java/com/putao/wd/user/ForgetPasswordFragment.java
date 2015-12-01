package com.putao.wd.user;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;

/*
**忘记密码
**create by wangou
 */
public class ForgetPasswordFragment extends BasicFragment {

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
}