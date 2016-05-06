package com.putao.wd.pt_discovery;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.DisCovery;
import com.putao.wd.pt_discovery.adapter.SpecialAdapter;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * 发现专题列表类
 * Created by Administrator on 2016/5/5.
 */
public class SpecialActivity extends PTWDActivity {
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;

    private SpecialAdapter specialAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discovery_campaign_special;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        navigation_bar.setMainTitle("专题");
        ArrayList<DisCovery> disCoveries = new ArrayList<>();
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        specialAdapter = new SpecialAdapter(mContext, disCoveries);
        rv_content.setAdapter(specialAdapter);
        addListener();
    }

    private void addListener() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ptl_refresh.refreshComplete();
            }
        });

        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rv_content.noMoreLoading();
            }
        });

        specialAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Serializable serializable, int position) {
                startActivity(SpecialListActivity.class);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
