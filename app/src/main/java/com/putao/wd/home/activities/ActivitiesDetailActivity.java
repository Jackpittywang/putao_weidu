package com.putao.wd.home.activities;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * 活动详情
 * Created by wango on 2015/12/4.
 *
 */
public class ActivitiesDetailActivity  extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activities_detail;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
