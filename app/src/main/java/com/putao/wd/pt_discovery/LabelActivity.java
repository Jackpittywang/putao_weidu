package com.putao.wd.pt_discovery;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.api.DisCoveryApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Campaign;
import com.putao.wd.model.DisCovery;
import com.putao.wd.model.Resources;
import com.putao.wd.pt_discovery.adapter.LabelAdapter;
import com.putao.wd.webview.BaseWebViewActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 标签类
 * Created by Administrator on 2016/5/6.
 */
public class LabelActivity extends PTWDActivity {
    public static final String TAGID = "tag_id";
    public static final String TAG_NAME = "tag_name";
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.ll_empty)
    LinearLayout ll_empty;
    @Bind(R.id.rl_failure)
    RelativeLayout rl_failure;

    private LabelAdapter labelAdapter;
    private List<Resources> resources;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discovery_campaign_special;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        navigation_bar.setMainTitle(args.getString(TAG_NAME));
        labelAdapter = new LabelAdapter(mContext, null);
        rv_content.setAdapter(labelAdapter);
        addListenrer();
        getResourcesTag(args.getString(TAGID));
    }

    private void addListenrer() {
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

    private void getResourcesTag(String tagId) {
        networkRequest(DisCoveryApi.getTagResources(String.valueOf(tagId)), new SimpleFastJsonCallback<Campaign>(Campaign.class, loading) {
            @Override
            public void onSuccess(String url, Campaign result) {
                if (result != null) {
                    resources = result.getResources();
                    labelAdapter.addAll(resources);
                } else {
                    ll_empty.setVisibility(View.VISIBLE);
                    ptl_refresh.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
