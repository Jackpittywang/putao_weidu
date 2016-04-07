package com.putao.wd.pt_companion;


import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ArticleDetailActs;
import com.putao.wd.pt_companion.adapter.ArticleDetailForActivitiesAdapter;
import com.sunnybear.library.controller.eventbus.Subcriber;
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
    @Bind(R.id.tv_title)
    TextView tv_title;
    private ArticleDetailForActivitiesAdapter mArtivleDetailActsAdapter;
    private ArrayList<ArticleDetailActs> objects;
    ViewGroup.LayoutParams mRvLayoutParams;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_for_activities;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        wv_load.loadUrl("http://wap.baidu.com");
        mRvLayoutParams = rv_content.getLayoutParams();
        mArtivleDetailActsAdapter = new ArticleDetailForActivitiesAdapter(mContext, null);
        rv_content.setAdapter(mArtivleDetailActsAdapter);
        initData();

    }

    private void initData() {
        objects = new ArrayList<>();
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

    @Subcriber(tag = ArticleDetailForActivitiesAdapter.EVENT_REFRESH_HEIGHT)
    private void setHeight(String str) {
        mRvLayoutParams.height = 0;
        for (ArticleDetailActs act : objects) {
            mRvLayoutParams.height += act.getHeight();
        }
        rv_content.setLayoutParams(mRvLayoutParams);
    }
}
