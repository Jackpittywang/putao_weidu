package com.putao.wd.pt_discovery;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.putao.mtlib.util.NetManager;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.api.DisCoveryApi;
import com.putao.wd.model.DiscoveryResource;
import com.putao.wd.model.FindResource;
import com.putao.wd.model.ResourceBanner;
import com.putao.wd.model.ResourceBannerAndTag;
import com.putao.wd.model.ResourceTag;
import com.putao.wd.pt_companion.ArticleDetailForActivitiesActivity;
import com.putao.wd.pt_discovery.adapter.ResourceAdapter;
import com.putao.wd.webview.BaseWebViewActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ResourceFragment extends BasicFragment implements View.OnClickListener {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_discovery)
    LoadMoreRecyclerView rv_discovery;
    @Bind(R.id.rl_main)
    RelativeLayout rl_main;
    @Bind(R.id.rl_no_discovery)
    RelativeLayout rl_no_discovery;
    @Bind(R.id.rl_no_discovery_failure)
    RelativeLayout rl_no_discovery_failure;
    @Bind(R.id.btn_no_data)
    Button btn_no_data;

    public static final String RESOURCE = "resource";
    public static final String CAMPAIGN_TYPE = "1";

    private List<FindResource> resous;
    private List<ResourceBanner> banners;
    private ResourceBannerAndTag bannerAndTag;
    private ResourceAdapter mAdapter;

    private int mPage = 0;

    private boolean isHeaderFailure = false;
    private boolean isResourceFailure = false;

    private boolean isNoHeader = false;
    private boolean isNoResource = false;

    private boolean isShowHead;
    private RecyclerView.LayoutParams layoutParams;
    private RelativeLayout.LayoutParams mRelativeLayoutParams;

    private RecyclerView childAt;
    private LinearLayoutManager mDiscoveryLayoutManager;
    private RelativeLayout reChildAt;
    private boolean isScroll = true;
    private int mScrollX;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_resource;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        resous = new ArrayList<>();
        mAdapter = new ResourceAdapter(mActivity, null);
        rv_discovery.setAdapter(mAdapter);
        freshResource();
//        mRelativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(mActivity, 80));
        mDiscoveryLayoutManager = (LinearLayoutManager) rv_discovery.getLayoutManager();
        addListener();

    }

    private void addListener() {
        /**
         * 下拉刷新
         * */
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                freshResource();
            }
        });

        /**
         * 上拉加载更多
         * */
        rv_discovery.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getDisCovery();
            }
        });
        final LinearLayoutManager discoveryLayoutManager = (LinearLayoutManager) rv_discovery.getLayoutManager();

        rv_discovery.setOnItemClickListener(new OnItemClickListener<FindResource>() {
            @Override
            public void onItemClick(FindResource resou, int position) {
                EventBusHelper.post(resou, AccountConstants.EventBus.EVENT_DISCOVERY_RESOURCE);
            }
        });

        rv_discovery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (childAt == null) {
                    try {
                        reChildAt = (RelativeLayout) mDiscoveryLayoutManager.getChildAt(1);
                        childAt = (RecyclerView) reChildAt.getChildAt(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                  /*  childAt.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (isScroll)
                                mScrollX = mScrollX + dx;
                        }
                    });*/
                }
                if (null == childAt) return;
                if (mDiscoveryLayoutManager.findFirstVisibleItemPosition() > 0 && !isShowHead) {
                    ((ViewGroup) childAt.getParent()).removeView(childAt);
                    childAt.setBackgroundColor(0xffffffff);
                    rl_main.addView(childAt);
                    isShowHead = true;
                } else if (mDiscoveryLayoutManager.findFirstVisibleItemPosition() == 0 && isShowHead) {
                    ((ViewGroup) childAt.getParent()).removeView(childAt);
                    reChildAt.addView(childAt);
                    isScroll = false;
                    /*if (0 == ((LinearLayoutManager) childAt.getLayoutManager()).findFirstCompletelyVisibleItemPosition())
                        childAt.scrollBy(mScrollX, 0);*/
                    isScroll = true;
                    isShowHead = false;
                }
            }
        });
    }

    /**
     * 加载资源列表
     */
    private void getDisCovery() {
        networkRequest(DisCoveryApi.getFindResource(String.valueOf(mPage)), new SimpleFastJsonCallback<DiscoveryResource>(DiscoveryResource.class, loading) {
            @Override
            public void onSuccess(String url, DiscoveryResource result) {
                List<FindResource> listResult = result.getList();
                if (listResult != null && listResult.size() > 0) {
                    mAdapter.addAll(listResult);
                    mPage++;
                    rv_discovery.loadMoreComplete();
                } else {
                    rv_discovery.noMoreLoading();
                }
                loading.dismiss();
            }
        });
    }

    private void freshResource() {

        mPage = 0;
        rest();
        rv_discovery.reset();
        networkRequestCache(DisCoveryApi.getFindResource(String.valueOf(mPage)), new SimpleFastJsonCallback<DiscoveryResource>(DiscoveryResource.class, loading) {
            @Override
            public void onSuccess(String url, DiscoveryResource result) {
                cacheData(url, result);
                if (result != null) {
                    FindResource top = result.getTop();
                    List<FindResource> list = result.getList();
                    if ((list != null && list.size() > 0) || top != null) {
                        resous.clear();
                        resous.add(0, new FindResource());
                        resous.add(1, new FindResource());
                        if (top != null) {
                            top.setIs_recommend(true);
                            top.setIs_top(true);
                            top.setIs_show_view(true);
                            resous.add(top);
                        } else {
                            list.get(0).setIs_show_view(true);
                        }
                        resous.addAll(list);
                        mAdapter.replaceAll(resous);
                        mPage++;
                        rv_discovery.loadMoreComplete();
                        isNoResource = false;
                    } else {
                        isNoResource = true;
                        rv_discovery.noMoreLoading();
                    }
                }
                loading.dismiss();
                ptl_refresh.refreshComplete();
            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                super.onFailure(url, statusCode, msg);
                // 多了尾布局，因此至少是1
                if (mAdapter.getItemCount() <= 1) {
                    ptl_refresh.refreshComplete();
                    isResourceFailure = true;
                }
            }

            @Override
            public void onFinish(String url, boolean isSuccess, String msg) {
                super.onFinish(url, isSuccess, msg);
                checkNetFailureAndData();
            }
        }, 600 * 1000);

        networkRequestCache(DisCoveryApi.getResouceTop(), new SimpleFastJsonCallback<ResourceBannerAndTag>(ResourceBannerAndTag.class, loading) {
            @Override
            public void onSuccess(String url, ResourceBannerAndTag result) {
                cacheData(url, result);
                if (result != null) {
                    bannerAndTag = result;
                    banners = bannerAndTag.getBanner();
                    if(banners != null || bannerAndTag.getTag() != null){
                        isNoHeader = false;
                    }else {
                        isNoHeader = true;
                    }
                } else {
                    bannerAndTag = new ResourceBannerAndTag();
                    isNoHeader = true;
                }
                mAdapter.setBannerAndTag(bannerAndTag);
            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                super.onFailure(url, statusCode, msg);
                //多了尾布局，因此至少是1
                if (mAdapter.getItemCount() <= 1) {
                    ptl_refresh.refreshComplete();
                    isHeaderFailure = true;
                }
            }

            @Override
            public void onFinish(String url, boolean isSuccess, String msg) {
                super.onFinish(url, isSuccess, msg);
                checkNetFailureAndData();
            }
        }, 600 * 1000);
    }

    /**
     * 将标志位设置成原来的值
     */
    private void rest() {
        isHeaderFailure = false;
        isResourceFailure = false;
        isNoHeader = false;
        isNoResource = false;
    }

    private void checkNetFailureAndData() {
        if (isHeaderFailure && isResourceFailure) {
            rl_no_discovery_failure.setVisibility(View.VISIBLE);
            rl_no_discovery.setVisibility(View.GONE);
            ptl_refresh.setVisibility(View.GONE);
        } else if (isNoHeader && isNoResource) {
            rl_no_discovery.setVisibility(View.VISIBLE);
            rl_no_discovery_failure.setVisibility(View.GONE);
            ptl_refresh.setVisibility(View.GONE);
        } else {
            ptl_refresh.setVisibility(View.VISIBLE);
            rl_no_discovery.setVisibility(View.GONE);
            rl_no_discovery_failure.setVisibility(View.GONE);
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Subcriber(tag = AccountConstants.EventBus.EVENT_DISCOVERY_CAROUSEL)
    public void eventDiscoveryCarousel(int position) {
        ResourceBanner bann = banners.get(position);
        Bundle bundle = new Bundle();
        bundle.putString(AccountConstants.Bundle.BUNDLE_DISCOVERY_ARTICLE, RESOURCE);
        bundle.putString(ArticleDetailForActivitiesActivity.SHARE_ICON, bann.getIcon());
        bundle.putString(ArticleDetailForActivitiesActivity.ARTICLE, bann.getId());
        bundle.putString(BaseWebViewActivity.TITLE, bann.getBanner_title());
        bundle.putString(BaseWebViewActivity.SERVICE_ID, bann.getSid());
        bundle.putString(BaseWebViewActivity.URL, StringUtils.equals(bann.getLocation_type(), "1") ? bann.getLink_url() : bann.getLocation());
        startActivity(ArticleDetailForActivitiesActivity.class, bundle);
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_DISCOVERY_HOT_TAG)
    public void eventDiscoveryHotTag(ResourceTag tag) {
        Bundle bundle = new Bundle();
        bundle.putString(AccountConstants.Bundle.BUNDLE_DISCOVRY_RESOURCE_TAG_ID, tag.getId());
        bundle.putString(AccountConstants.Bundle.BUNDLE_DISCOVRY_RESOURCE_TAG_TITLE, tag.getTag_name());
        if (StringUtils.equals(CAMPAIGN_TYPE, tag.getDisplay_type()))
            startActivity(CampaignActivity.class, bundle);
        else
            startActivity(LabelActivity.class, bundle);
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_DISCOVERY_RESOURCE)
    public void eventDiscoveryResource(FindResource reso) {
        Bundle bundle = new Bundle();
        bundle.putString(AccountConstants.Bundle.BUNDLE_DISCOVERY_ARTICLE, RESOURCE);
        bundle.putString(ArticleDetailForActivitiesActivity.SHARE_ICON, reso.getIcon());
        bundle.putString(ArticleDetailForActivitiesActivity.ARTICLE, reso.getId());
        bundle.putString(BaseWebViewActivity.TITLE, reso.getTitle());
        bundle.putString(BaseWebViewActivity.SERVICE_ID, reso.getSid());
        bundle.putString(BaseWebViewActivity.URL, reso.getLink_url());
        startActivity(ArticleDetailForActivitiesActivity.class, bundle);
    }

    @OnClick({R.id.rl_no_discovery, R.id.btn_no_data})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_no_discovery:
                freshResource();
                break;
            case R.id.btn_no_data:
                if (NetManager.isNetworkAvailable(mActivity))
                    ToastUtils.showToastShort(mActivity, "获取数据失败");
                else
                    freshResource();
                break;
        }
    }
}