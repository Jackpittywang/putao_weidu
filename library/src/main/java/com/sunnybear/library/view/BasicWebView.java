package com.sunnybear.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 基础WebView
 * Created by guchenkai on 2015/11/30.
 */
public class BasicWebView extends WebView {

    public BasicWebView(Context context) {
        this(context, null, 0);
    }

    public BasicWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化WebView
     */
    private void initView() {
        setWebSettings();
        setScrollBarStyle(SCROLLBARS_INSIDE_OVERLAY);//滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    /**
     * 设置WebSettings
     */
    private void setWebSettings() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);//开启对JavaScript的支持
        settings.setDefaultTextEncodingName("UTF-8");//设置字符编码
    }
}
