package com.putao.wd.created;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.BasicWebView;

import butterknife.Bind;

/**
 * 创想详情
 * Created by guchenkai on 2016/1/11.
 */
public class CreatedDetailActivity extends PTWDActivity {
    @Bind(R.id.wv_content)
    BasicWebView wv_content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_created_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        wv_content.loadUrl("http://3g.qq.com");
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
