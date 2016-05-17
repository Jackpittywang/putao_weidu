package com.putao.wd.pt_discovery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.putao.ptx.push.core.NetworkUtil;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.api.DisCoveryApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Campaign;
import com.putao.wd.model.ResourceTag;
import com.putao.wd.model.Resources;
import com.putao.wd.pt_companion.ArticleDetailForActivitiesActivity;
import com.putao.wd.pt_discovery.adapter.CampaignAdapter;
import com.putao.wd.webview.BaseWebViewActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 发现的活动列表
 * Created by Administrator on 2016/5/5.
 */
public class CampaignActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;

    @Bind(R.id.ll_empty)
    LinearLayout ll_empty;
    @Bind(R.id.rl_failure)
    RelativeLayout rl_campaign_failure;
    @Bind(R.id.btn_no_data)
    Button btn_no_data;

    private CampaignAdapter campaignAdapter;
    private List<Resources> resources;
    private ResourceTag tag_info;
    private String tagId;
    private String title;
    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discovery_campaign_special;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        tagId = args.getString(AccountConstants.Bundle.BUNDLE_DISCOVRY_RESOURCE_TAG_ID);
        title = args.getString(AccountConstants.Bundle.BUNDLE_DISCOVRY_RESOURCE_TAG_TITLE);
        setMainTitle(title);
        campaignAdapter = new CampaignAdapter(mContext, null);
        rv_content.setAdapter(campaignAdapter);
        getTagCampaign();
        addListener();
    }


    private void addListener() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTagCampaign();
            }
        });

        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreTagList();
            }
        });

        rv_content.setOnItemClickListener(new OnItemClickListener<Resources>() {
            @Override
            public void onItemClick(Resources resources, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(AccountConstants.Bundle.BUNDLE_DISCOVERY_ARTICLE, ResourceFragment.RESOURCE);
                bundle.putString(ArticleDetailForActivitiesActivity.SHARE_ICON, resources.getIcon());
                bundle.putString(ArticleDetailForActivitiesActivity.ARTICLE, resources.getId());
                bundle.putString(BaseWebViewActivity.TITLE, resources.getTitle());
                bundle.putString(BaseWebViewActivity.SERVICE_ID, resources.getSid());
                bundle.putString(BaseWebViewActivity.URL, resources.getLink_url());
                startActivity(ArticleDetailForActivitiesActivity.class, bundle);
            }
        });
    }

    private void loadMoreTagList() {
        networkRequest(DisCoveryApi.getTagResources(tagId, String.valueOf(mPage)), new SimpleFastJsonCallback<Campaign>(Campaign.class, loading) {
            @Override
            public void onSuccess(String url, Campaign result) {
                if (result != null) {
                    resources = result.getResources();
                    if (resources != null && resources.size() > 0) {
                        campaignAdapter.addAll(resources);
                        mPage++;
                        rv_content.loadMoreComplete();
                    } else {
                        rv_content.noMoreLoading();
                    }
                    loading.dismiss();
                }
            }
        });
    }

    private void getTagCampaign() {
        mPage = 1;
        rv_content.reset();
        networkRequest(DisCoveryApi.getTagResources(tagId, String.valueOf(mPage)), new SimpleFastJsonCallback<Campaign>(Campaign.class, loading) {
            @Override
            public void onSuccess(String url, Campaign result) {
                cacheData(url, result);
                if (result != null) {
                    resources = result.getResources();
                    tag_info = result.getTag_info();
                    if (resources != null && resources.size() > 0) {
                        campaignAdapter.clear();
                        campaignAdapter.replaceAll(resources);
                        ll_empty.setVisibility(View.GONE);
                        ptl_refresh.setVisibility(View.VISIBLE);
                        rl_campaign_failure.setVisibility(View.GONE);

                        mPage++;
                        rv_content.loadMoreComplete();
                    } else {
                        ll_empty.setVisibility(View.VISIBLE);
                        ptl_refresh.setVisibility(View.GONE);
                        rl_campaign_failure.setVisibility(View.GONE);

                        rv_content.noMoreLoading();
                    }
                }
                campaignAdapter.setMainTitleNotify(tag_info.getTag_name());
                ptl_refresh.refreshComplete();
                loading.dismiss();
                //        setMainTitle(tag_info.getTag_name());
            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                super.onFailure(url, statusCode, msg);
                //因为多了尾布局，因此至少是1
                if (campaignAdapter.getItemCount() <= 1) {
                    rl_campaign_failure.setVisibility(View.VISIBLE);
                    ptl_refresh.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.GONE);
                    ptl_refresh.refreshComplete();
                }
            }
        });

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.ll_empty, R.id.btn_no_data})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_empty:
                getTagCampaign();
                break;
            case R.id.btn_no_data:
                if (!NetworkUtil.isNetworkAvailable(mContext))
                    ToastUtils.showToastShort(mContext, "获取数据失败");
                else
                    getTagCampaign();
                break;
        }
    }
}
