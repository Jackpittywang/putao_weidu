package com.putao.wd.home.news;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

public class NewsDetailActivity  extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
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
