package com.putao.wd.created;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.created.adapter.FancyAdapter;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

import java.io.Serializable;

import butterknife.Bind;

/**
 * 奇思妙想
 * Created by zhanghao on 2016/1/14.
 */
public class FancyActivity extends PTWDActivity implements PullToRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadMoreListener, OnItemClickListener {
    @Bind(R.id.rv_fancy)
    LoadMoreRecyclerView rv_fancy;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;

    private FancyAdapter mFancyAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fancy;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mFancyAdapter = new FancyAdapter(mContext, null);
        mFancyAdapter.add(new Marketing());
        mFancyAdapter.add(new Marketing());
        mFancyAdapter.add(new Marketing());
        rv_fancy.noMoreLoading();
        rv_fancy.setAdapter(mFancyAdapter);
        addListener();
    }

    private void addListener() {
        ptl_refresh.setOnRefreshListener(this);
        rv_fancy.setOnLoadMoreListener(this);
        rv_fancy.setOnItemClickListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onRefresh() {
        mFancyAdapter.clear();
        mFancyAdapter.add(new Marketing());
        mFancyAdapter.add(new Marketing());
        mFancyAdapter.add(new Marketing());
        ptl_refresh.refreshComplete();
    }

    @Override
    public void onLoadMore() {
        rv_fancy.noMoreLoading();
    }

    @Override
    public void onItemClick(Serializable serializable, int position) {
        startActivity(FancyDetailActivity.class);
    }
}
