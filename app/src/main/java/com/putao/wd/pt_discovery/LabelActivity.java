package com.putao.wd.pt_discovery;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.DisCovery;
import com.putao.wd.pt_discovery.adapter.LabelAdapter;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * 标签类
 * Created by Administrator on 2016/5/6.
 */
public class LabelActivity extends PTWDActivity {
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;

    private LabelAdapter labelAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discovery_campaign_special;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        navigation_bar.setMainTitle("标签名");

        ArrayList<DisCovery> disCoveries = new ArrayList<>();
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        disCoveries.add(new DisCovery());
        labelAdapter = new LabelAdapter(mContext, disCoveries);
        rv_content.setAdapter(labelAdapter);
        addListenrer();
    }

    private void addListenrer() {
        rv_content.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Serializable serializable, int position) {

            }
        });

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
