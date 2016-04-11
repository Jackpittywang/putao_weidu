package com.putao.wd.webview;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.start.putaozi.CommonQuestionActivity;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.BasicWebView;

import butterknife.Bind;

/**
 * 通用webview
 * Created by yangq on 2016/4/11.
 */
public class BaseWebViewActivity extends PTWDActivity<GlobalApplication> implements BasicWebView.OnWebViewLoadUrlCallback {

    public static final String URL = "url";
    public static final String TITLE = "title";

    @Bind(R.id.wv_content)
    BasicWebView wv_content;

    @Bind(R.id.pb_webview)
    ProgressBar pb_webview;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_webview;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        String title = args.getString(TITLE);
        if (StringUtils.isEmpty(title)) title = "葡萄纬度";
        setMainTitle(title);
        String url = args.getString(URL);
        wv_content.setOnWebViewLoadUrlCallback(this);
        wv_content.loadUrl(url);

        wv_content.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb_webview.setProgress(newProgress);
                if (newProgress >= 100)
                    pb_webview.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Override
    public void onParsePutaoUrl(String scheme, JSONObject result) {
        Logger.w("BaseWebView = " + result.toString());
        PutaoParse.parseUrl(this, scheme, result);

    }

    @Override
    public void onWebPageLoaderFinish(String url) {

    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
    }

}
