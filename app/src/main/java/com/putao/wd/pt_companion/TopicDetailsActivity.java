package com.putao.wd.pt_companion;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * Created by Administrator on 2016/4/6.
 */
@Deprecated
public class TopicDetailsActivity extends PTWDActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_campaign_topic;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        finish();
    }
}
