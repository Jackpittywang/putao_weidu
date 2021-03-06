package com.putao.wd.webview;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.util.AppUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;

import java.net.URLDecoder;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

/**
 * 通用webview
 * Created by yangq on 2016/4/11.
 */
public class BaseWebViewActivity extends PTWDActivity<GlobalApplication> {

    public static final String URL = "url";
    public static final String TITLE = "title";
    public static final String SERVICE_ID = "service_id";
    public static final String SERVICE_ICON = "service_icon";

    public static String titleFromPage = "";
    public static String descriptionFromPage = "";
    public static String sharePicFromPage = "";
    public static String mServiceId = "";

    @Bind(R.id.wv_content)
    WebView wv_content;

    /*@Bind(R.id.pb_webview)
    ProgressBar pb_webview;*/

    public BaseWebViewActivity() {
    }


    private void setWebSettings() {
        WebSettings settings = wv_content.getSettings();
        settings.setJavaScriptEnabled(true);//开启对JavaScript的支持
        settings.setDefaultTextEncodingName("UTF-8");//设置字符编码

        settings.setSupportZoom(true);
        // 开启alert
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 开启按钮按下显示
        settings.setLightTouchEnabled(true);
        settings.setUseWideViewPort(true);
        wv_content.requestFocus();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_webview;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        String title = args.getString(TITLE);
        String serviceId = args.getString(SERVICE_ID);
        // 添加默认的显示文字
        if (StringUtils.isEmpty(title)) title = "葡萄纬度";
        setMainTitle(title);
        String url = "";
        url = args.getString(URL);
        if (!TextUtils.isEmpty(url))
            Logger.d(url);
        setWebSettings();

        // url = "http://static.uzu.wang/putaowd/pages/support.html";
        url = getInAppUrl(url, serviceId);
        wv_content.loadUrl(url);

        wv_content.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                /*pb_webview.setProgress(newProgress);
                if (newProgress >= 100)
                    pb_webview.setVisibility(View.GONE);*/
            }

        });

        wv_content.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String protocolHeader = getProtocolHeader(url);
                switch (protocolHeader) {
                    case ProtocolHeader.PROTOCOL_HEADER_HTTP:
                        view.loadUrl(url);
                        break;
                    case ProtocolHeader.PROTOCOL_HEADER_HTTPS:
                        view.loadUrl(url);
                        break;
                    case ProtocolHeader.PROTOCOL_HEADER_PUTAO:
                        // 网页加载完js会通过此方法把 article_title, description, share_pic 传过来
                        String scheme = getScheme(url);
                        String content = getContentUrl(url);
                        if (PutaoParse.PAGE_SETTING.equals(scheme)) {
                            // 获取文章标题
                            titleFromPage = JSON.parseObject(content).getString("article_title");
                            // 描述
                            descriptionFromPage = JSON.parseObject(content).getString("description");
                            // 分享的图片url
                            sharePicFromPage = JSON.parseObject(content).getString("share_pic");
                        } else {
                            if (url.contains("issue.html"))
                                YouMengHelper.onEvent(mContext, YouMengHelper.Activity_menu_should_ask);

                            PutaoParse.parseUrl(BaseWebViewActivity.this, scheme, args.getString(SERVICE_ICON), JSON.parseObject(content));
                        }
                        return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loading.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loading.dismiss();
            }
        });

    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Override
    public void onLeftAction() {
        super.onLeftAction();
    }

    /**
     * 把app里面的url加上inapp标志，网页端会通过此标志来判断是否是app里面调用
     *
     * @param url
     * @return
     */
    public static String getInAppUrl(String url, String serviceId) {
        if (StringUtils.isEmpty(url)) return "";
        // 此处如果判断有"inapp=" 不会在添加后面的字段，如果业务需求有变化，需要更改
        if (url.contains("inapp=")) return url;
        if (url.contains("?")) url = url + "&inapp=1";
        else url = url + "?inapp=1";
        url = url + "&token=" + AccountHelper.getCurrentToken();
        url = url + "&uid=" + AccountHelper.getCurrentUid();
        url = url + "&service_id=" + serviceId;
        url = url + "&device_id=" + AppUtils.getDeviceId(GlobalApplication.getInstance());
        url = url + "&appid=" + BasicApplication.app_id;
        return url;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wv_content != null) {
            wv_content.getSettings().setBuiltInZoomControls(true);
            wv_content.setVisibility(View.GONE);
            long delayTime = ViewConfiguration.getZoomControlsTimeout();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            wv_content.destroy();
                            wv_content = null;
                        }
                    });
                }
            }, delayTime);

        }
    }

    /**
     * 获得协议头
     *
     * @param url url
     * @return 协议头
     */
    private String getProtocolHeader(String url) {
        return url.substring(0, url.indexOf(":"));
    }


    /**
     * 获得Scheme
     *
     * @param url url
     * @return Scheme
     */
    private String getScheme(String url) {
        return url.substring(url.indexOf(":") + 3, url.indexOf("{") - 1);
    }

    /**
     * 获得真实url内容
     *
     * @param url url
     * @return 真实url内容
     */
    public String getContentUrl(String url) {
        return URLDecoder.decode(url.substring(url.indexOf("{"), url.length()));
    }

    /**
     * 协议头定义
     */
    public static final class ProtocolHeader {
        public static final String PROTOCOL_HEADER_HTTP = "http";
        public static final String PROTOCOL_HEADER_HTTPS = "https";
        public static final String PROTOCOL_HEADER_PUTAO = "putao";
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (wv_content != null) {
                wv_content.getClass().getMethod("onPause").invoke(wv_content, (Object[]) null);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (wv_content != null) {
                wv_content.getClass().getMethod("onResume").invoke(wv_content, (Object[]) null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
