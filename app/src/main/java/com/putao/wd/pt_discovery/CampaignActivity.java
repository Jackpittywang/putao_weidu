package com.putao.wd.pt_discovery;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.api.DisCoveryApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Campaign;
import com.putao.wd.model.DisCovery;
import com.putao.wd.model.Resources;
import com.putao.wd.model.SubscribeList;
import com.putao.wd.pt_discovery.adapter.CampaignAdapter;
import com.putao.wd.webview.BaseWebViewActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 发现的活动列表
 * Created by Administrator on 2016/5/5.
 */
public class CampaignActivity extends PTWDActivity {
    public static final String TAGID = "tag_id";
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;

    private CampaignAdapter campaignAdapter;
    private List<Resources> resources;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discovery_campaign_special;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        campaignAdapter = new CampaignAdapter(mContext, null);
        rv_content.setAdapter(campaignAdapter);
        addListener();
        getTagCampaign(args.getString(TAGID));
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

        rv_content.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Serializable serializable, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(BaseWebViewActivity.TITLE, resources.get(position).getTitle());
                bundle.putString(BaseWebViewActivity.SERVICE_ID, resources.get(position).getSid());
                bundle.putString(BaseWebViewActivity.URL, resources.get(position).getLink_url());
                startActivity(BaseWebViewActivity.class, bundle);
            }
        });
    }

    private void getTagCampaign(String tagId) {
        networkRequest(DisCoveryApi.getTagResources(String.valueOf(tagId)), new SimpleFastJsonCallback<Campaign>(Campaign.class, loading) {
            @Override
            public void onSuccess(String url, Campaign result) {
                if (result != null) {
                    resources = result.getResources();
                    campaignAdapter.addAll(resources);
                }
            }
        });
    }

//    private void checkLoadMoreComplete(ArrayList<SubscribeList> result) {
//        if (result == null)
//            rv_content.noMoreLoading();
//        else mPage++;
//    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

}
