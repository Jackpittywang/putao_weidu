package com.putao.wd.pt_companion;


import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ArticleDetailActs;
import com.putao.wd.pt_companion.adapter.ArticleDetailForActivitiesAdapter;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by zhanghao on 2016/4/6.
 */
public class ArticleDetailForActivitiesActivity extends PTWDActivity {

    @Bind(R.id.wv_load)
    BasicWebView wv_load;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    private ArticleDetailForActivitiesAdapter mArtivleDetailActsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_for_activities;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        wv_load.loadUrl("http://wap.baidu.com");
        mArtivleDetailActsAdapter = new ArticleDetailForActivitiesAdapter(mContext, null);
        rv_content.setAdapter(mArtivleDetailActsAdapter);
        initData();
    }

    private void initData() {
        ArrayList<ArticleDetailActs> objects = new ArrayList<>();
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        mArtivleDetailActsAdapter.replaceAll(objects);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
