package com.putao.wd.pt_companion;


import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * Created by zhanghao on 2016/4/6.
 */
public class ArticleDetailWithCommentsActivity extends PTWDActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_with_comments;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
