package com.putao.wd.explore;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.explore.adapter.MarketingAdapter;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * 创想
 * Created by guchenkai on 2016/1/11.
 */
public class SmartActivity extends PTWDActivity {
    @Bind(R.id.rv_marketing)
    LoadMoreRecyclerView rv_marketing;

    private List<Marketing> markets;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smart;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        initData();
    }

    private void initView() {
        MarketingAdapter marketingAdapter = new MarketingAdapter(mContext,markets);
        rv_marketing.setAdapter(marketingAdapter);
    }

    private void initData() {
        initView();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
