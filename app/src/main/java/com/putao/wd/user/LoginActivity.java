package com.putao.wd.user;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragmentActivity;

/**
 * 登录模块
 * Created by guchenkai on 2015/11/27.
 */
public class LoginActivity extends BasicFragmentActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addFragment(CompleteFragment.class);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
