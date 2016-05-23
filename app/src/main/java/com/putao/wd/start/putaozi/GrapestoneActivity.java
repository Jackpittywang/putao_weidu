package com.putao.wd.start.putaozi;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.start.question.QuestionActivity;
import com.putao.wd.user.LoginActivity;
import com.putao.wd.webview.BaseWebViewActivity;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.BasicWebView;


import butterknife.Bind;

/**
 * 葡萄籽
 * Created by yanghx on 2015/12/22.
 */
public class GrapestoneActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener, BasicWebView.OnWebViewLoadUrlCallback {
    public static final String URL_GRAPESTONE = GlobalApplication.isDebug ? "http://api-weidu.ptdev.cn/faq/list" : "http://api-weidu.putao.com/faq/list";
    @Bind(R.id.wv_content)
    BasicWebView wv_content;
/*    @Bind(R.id.pb_webview)
    ProgressBar pb_webview;*/

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grapestone;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        wv_content.loadUrl(URL_GRAPESTONE);
        wv_content.setOnWebViewLoadUrlCallback(this);
        wv_content.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                /*pb_webview.setProgress(newProgress);
                if (newProgress >= 100)
                    pb_webview.setVisibility(View.GONE);*/
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRightAction() {
        if (!AccountHelper.isLogin())
            startActivity(LoginActivity.class);
        else
            startActivity(QuestionActivity.class);
    }

    @Override
    public void onParsePutaoUrl(String scheme, JSONObject result) {
        Logger.w("葡萄籽 = " + result.toString());
        String url = result.getString("url");
        if (!StringUtils.isEmpty(url)) {
            switch (url) {
                case "http://api-weidu.putao.com/faq/detail?product_id=null":
                    YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_qa_item, "综合");
                    break;
                case "http://api-weidu.putao.com/faq/detail?product_id=601":
                    YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_qa_item, "淘淘向右走");
                    break;
                case "http://api-weidu.putao.com/faq/detail?product_id=602":
                    YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_qa_item, "班得瑞的奇幻花园");
                    break;
                case "http://api-weidu.putao.com/faq/detail?product_id=603":
                    YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_qa_item, "旋转吧魔方");
                    break;
                case "http://api-weidu.putao.com/faq/detail?product_id=7000":
                    YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_qa_item, "麦斯丝");
                    break;
                case "http://api-weidu.putao.com/faq/detail?product_id=8000":
                    YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_qa_item, "Hello编程");
                    break;
                case "http://api-weidu.putao.com/faq/detail?product_id=8001":
                    YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_qa_item, "哈泥海洋");
                    break;
                case "http://api-weidu.putao.com/faq/detail?product_id=8002":
                    YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_qa_item, "涂涂世界");
                    break;
            }

            // args.putString(CommonQuestionActivity.URL_QUESTION, url);
            // startActivity(CommonQuestionActivity.class, args);
            args.putString(BaseWebViewActivity.URL, url);
            args.putString(BaseWebViewActivity.TITLE, "常见问题");
            startActivity(BaseWebViewActivity.class, args);

        }
    }

    @Override
    public void onWebPageLoaderFinish(String url) {

    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
    }
}
