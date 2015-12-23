package com.putao.wd.start.putaozi;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.BasicWebView;

import butterknife.Bind;

/**
 * 葡萄籽
 * Created by yanghx on 2015/12/22.
 */
public class GrapestoneActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    public static final String URL_GRAPESTONE = "http://static.uzu.wang/weidu_event/view/QA.html";
    @Bind(R.id.wv_content)
    BasicWebView wv_content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grapestone;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        wv_content.loadUrl(URL_GRAPESTONE);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }

}
