package com.putao.wd.pt_me.participation;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Create;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

/**
 * 我的参与
 * Created by Administrator on 2016/4/5.
 */
public class ParticipationActivity extends PTWDActivity implements PullToRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadMoreListener, OnItemClickListener<Create>, View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_participation;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(Create create, int position) {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }
}
