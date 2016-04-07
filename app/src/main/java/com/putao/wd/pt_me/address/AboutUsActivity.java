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
                if (count > 4) {
                    Intent intent = new Intent(AboutUsActivity.this, NetworkLogActivty.class);
                    startActivity(intent);
                    count = 0;
                }
                count++;
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                count = 0;
            }
        }, 0, 5000);

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
