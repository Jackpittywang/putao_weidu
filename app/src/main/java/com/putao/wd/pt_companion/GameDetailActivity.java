package com.putao.wd.pt_companion;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.BasicWebView;

import butterknife.Bind;

/**
 * 游戏详情页
 * Created by zhanghao on 2016/04/05.
 */
public class GameDetailActivity extends PTWDActivity {

    public static final String POSITION = "position";
    @Bind(R.id.wv_content)
    BasicWebView wv_content;
/*
    @Bind(R.id.pb_webview)
    ProgressBar pb_webview;
*/

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        wv_content.loadUrl("http://wap.baidu.com");
      /*  wv_content.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb_webview.setProgress(newProgress);
                if (newProgress >= 100)
                    pb_webview.setVisibility(View.GONE);
            }
        });*/
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
}


