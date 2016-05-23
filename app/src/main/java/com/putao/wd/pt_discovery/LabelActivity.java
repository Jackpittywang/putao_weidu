package com.putao.wd.pt_discovery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.putao.ptx.push.core.NetworkUtil;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.DisCoveryApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Campaign;
import com.putao.wd.model.ResourceTag;
import com.putao.wd.model.Resources;
import com.putao.wd.pt_companion.ArticleDetailForActivitiesActivity;
import com.putao.wd.pt_discovery.adapter.LabelAdapter;
import com.putao.wd.webview.BaseWebViewActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 标签类
 * Created by Administrator on 2016/5/6.
 */
public class LabelActivity extends PTWDActivity implements View.OnClickListener {
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

    private LabelAdapter labelAdapter;
    private String tagId;
    private String title;
    private int mPage = 1;
    private List<Resources> resources;
    private ResourceTag tag_info;

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
        labelAdapter = new LabelAdapter(mContext, null);
        rv_content.setAdapter(labelAdapter);
        getTagList();
        addListenrer();
    }

    private void getTagList() {
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
                        labelAdapter.clear();
                        labelAdapter.replaceAll(resources);
                        ll_empty.setVisibility(View.GONE);
                        ptl_refresh.setVisibility(View.VISIBLE);
                        rl_campaign_failure.setVisibility(View.GONE);
                        mPage++;
                        rv_content.loadMoreComplete();

                    } else {
                        rv_content.noMoreLoading();
                        ll_empty.setVisibility(View.VISIBLE);
                        ptl_refresh.setVisibility(View.GONE);
                        rl_campaign_failure.setVisibility(View.GONE);
                    }
                }
                labelAdapter.setMainTitleNotify(tag_info.getTag_name());
                ptl_refresh.refreshComplete();
                loading.dismiss();
                //         setMainTitle(tag_info.getTag_name());
            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                super.onFailure(url, statusCode, msg);
                //因为多了尾布局，因此至少是1
                if (labelAdapter.getItemCount() <= 1) {
                    rl_campaign_failure.setVisibility(View.VISIBLE);
                    ptl_refresh.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.GONE);
                    ptl_refresh.refreshComplete();
                }
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
                        labelAdapter.addAll(resources);
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

    private void addListenrer() {
        rv_content.setOnItemClickListener(new OnItemClickListener<Resources>() {

            @Override
            public void onItemClick(Resources resources, int position) {
                YouMengHelper.onEvent(mContext,YouMengHelper.DiscoverHome_tag_list);

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

        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTagList();
            }
        });
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreTagList();
            }
        });
    }

    private void checkLoadMoreComplete(List<Resources> result) {
        if (result == null)
            rv_content.noMoreLoading();
        else mPage++;
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
                getTagList();
                break;
            case R.id.btn_no_data:
                if (!NetworkUtil.isNetworkAvailable(mContext))
                    ToastUtils.showToastShort(mContext, "获取数据失败");
                else
                    getTagList();
                break;
        }
    }
}
