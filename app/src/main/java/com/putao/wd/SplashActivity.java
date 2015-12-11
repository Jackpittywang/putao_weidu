package com.putao.wd;

import android.os.Bundle;
import android.os.Handler;

import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.controller.BasicFragmentActivity;

/**
 * 闪屏页面
 * Created by guchenkai on 2015/12/11.
 */
public class SplashActivity extends BasicFragmentActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
                ActivityManager.getInstance().finishCurrentActivity();
            }
        }, 3 * 1000);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
