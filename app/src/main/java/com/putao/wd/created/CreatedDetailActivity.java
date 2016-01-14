package com.putao.wd.created;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.BasicFragmentActivity;

import butterknife.Bind;

/**
 * 创想详情
 * Created by guchenkai on 2016/1/11.
 */
public class CreatedDetailActivity extends PTWDActivity {
    @Bind(R.id.web)
    WebView web;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_created_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        web.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        web.loadUrl("http://i4k.co/?p=5266");
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
