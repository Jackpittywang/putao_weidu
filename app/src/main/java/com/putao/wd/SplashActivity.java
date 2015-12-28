package com.putao.wd;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.putao.wd.store.invoice.InvoiceInfoActivity;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.PreferenceUtils;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_IS_FIRST, false))
                    startActivity(GuidanceActivity.class);
                else
                    startActivity(InvoiceInfoActivity.class);
                ActivityManager.getInstance().finishCurrentActivity();
            }
        }, 3 * 1000);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
