package com.putao.wd.user;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;

/**
 * 重置密码
 * Created by wango on 2015/12/1.
 */
public class ResetPasswordFragment extends BasicFragment{

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reset_password;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


}