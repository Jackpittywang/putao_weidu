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
public class MarketingActivity extends PTWDActivity {
    @Bind(R.id.rv_marketing)
    LoadMoreRecyclerView rv_marketing;

    private MarketingAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_marketing;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        adapter = new MarketingAdapter(mContext, null);
        rv_marketing.setAdapter(adapter);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
