package com.putao.wd.start.putaozi;

import android.os.Bundle;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.BasicWebView;

import butterknife.Bind;

/**
 * 葡萄籽常见问题详情界面
 * Created by yanghx on 2016/1/8.
 */
public class CommonQuestionActivity extends PTWDActivity<GlobalApplication> {

    public static final String URL_QUESTION = "url_question";

    @Bind(R.id.wv_content)
    BasicWebView wv_content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_question;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        String url = args.getString(URL_QUESTION);
        wv_content.loadUrl(url);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

}
