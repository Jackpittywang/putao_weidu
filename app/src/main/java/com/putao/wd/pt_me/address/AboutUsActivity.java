package com.putao.wd.pt_me.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.NetworkLogActivty;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

/**
 * 关于我们
 * Created by wangou on 2015/12/1.
 */
public class AboutUsActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.tvLog)
    TextView tvLog;
    private int count = 0;
    private long time = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        tvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time < 0) {
                    time = System.currentTimeMillis();
                }
                if (count > 4 && System.currentTimeMillis() - time < 3000) {
                    Intent intent = new Intent(AboutUsActivity.this, NetworkLogActivty.class);
                    startActivity(intent);
                    count = 0;
                    time = System.currentTimeMillis();
                }
                count++;
            }
        });

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
