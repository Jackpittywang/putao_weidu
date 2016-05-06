package com.putao.wd.pt_discovery;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.DisCovery;
import com.putao.wd.pt_discovery.adapter.CampaignAdapter;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 发现的活动列表
 * Created by Administrator on 2016/5/5.
 */
public class CampaignActivity extends PTWDActivity {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;

    private CampaignAdapter campaignAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discovery_campaign_special;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        ArrayList<DisCovery> disCoveries = new ArrayList<>();
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        campaignAdapter = new CampaignAdapter(mContext, disCoveries);
        rv_content.setAdapter(campaignAdapter);
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
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

}
