package com.putao.wd.pt_companion;


import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ArticleDetailsActivity extends PTWDActivity{

    private static final String EVENT_TITLE = "event_title";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_blackboard_article;
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

    @Subcriber(tag = EVENT_TITLE)
    public void eventTitle(String title){

    }

}
