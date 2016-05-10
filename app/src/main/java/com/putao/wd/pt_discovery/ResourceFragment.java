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
    //    private RecyclerView.LayoutParams layoutParams;
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
                cacheData(url, result);
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
        networkRequestCache(DisCoveryApi.getFindResource(String.valueOf(mPage)), new SimpleFastJsonCallback<DiscoveryResource>(DiscoveryResource.class, loading) {
            @Override
            public void onSuccess(String url, DiscoveryResource result) {
                cacheData(url, result);

                if (result != null) {
                    FindResource top = result.getTop();
                    List<FindResource> list = result.getList();
                    top.setIs_recommend(true);
                    top.setIs_top(true);
                    resous.clear();
                    resous.add(0, new FindResource());
                    resous.add(1, new FindResource());
                    resous.add(top);
                    resous.addAll(list);

                    if (list != null && list.size() > 0) {
//                        rl_no_discovery.setVisibility(View.GONE);
//                        ptl_refresh.setVisibility(View.VISIBLE);
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

//                        rl_no_discovery.setVisibility(View.VISIBLE);
//                        ptl_refresh.setVisibility(View.GONE);
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

                    isNoHeader = false;
                } else {
                    bannerAndTag = new ResourceBannerAndTag();
                    banners = new ArrayList<>();

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

    private void checkNetFailureAndData() {
        if (isHeaderFailure && isResourceFailure) {
            rl_no_discovery_failure.setVisibility(View.VISIBLE);
            rl_no_discovery.setVisibility(View.GONE);
            ptl_refresh.setVisibility(View.GONE);
        } else if (isNoHeader && isNoHeader) {
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
        bundle.putString(AccountConstants.Bundle.BUNDLE_DISCOVERY_ARTICLE, "resource");
        bundle.putString(BaseWebViewActivity.TITLE, bann.getBanner_title());
        bundle.putString(BaseWebViewActivity.SERVICE_ID, bann.getSid());
        bundle.putString(BaseWebViewActivity.URL, StringUtils.equals(bann.getLocation_type(), "1") ? bann.getLink_url() : bann.getLocation());
        startActivity(BaseWebViewActivity.class, bundle);
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_DISCOVERY_HOT_TAG)
    public void eventDiscoveryHotTag(ResourceTag tag) {
        Bundle bundle = new Bundle();
        bundle.putString(AccountConstants.Bundle.BUNDLE_DISCOVRY_RESOURCE_TAG_ID, tag.getId());
        if (StringUtils.equals("1", tag.getDisplay_type()))
            startActivity(CampaignActivity.class, bundle);
        else
            startActivity(LabelActivity.class, bundle);
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_DISCOVERY_RESOURCE)
    public void eventDiscoveryResource(FindResource reso) {

        Bundle bundle = new Bundle();
        bundle.putString(BaseWebViewActivity.TITLE, reso.getTitle());
        bundle.putString(BaseWebViewActivity.SERVICE_ID, reso.getSid());
        bundle.putString(BaseWebViewActivity.URL, reso.getLink_url());
        startActivity(BaseWebViewActivity.class, bundle);
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


/*
        loadResous();
loadDrawables();
loadTags();
        ptl_refresh.setVisibility(View.VISIBLE);
        mPage++;
        rv_discovery.loadMoreComplete();
        ptl_refresh.refreshComplete();
        loading.dismiss();
}


    private void loadTags() {
        hotTags = new ArrayList<>();
        hotTags.add("http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg");
        hotTags.add("http://weidu.file.putaocdn.com/file/d6bcf5c2c6d6cbc408ed7fca3718f76323c8d677.jpg");
        hotTags.add("http://weidu.file.putaocdn.com/file/4976bada04f9b3c31fb51e0cd6a3237dff026311.jpg");
        hotTags.add("http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg");
        hotTags.add("http://weidu.file.putaocdn.com/file/d6bcf5c2c6d6cbc408ed7fca3718f76323c8d677.jpg");
        hotTags.add("http://weidu.file.putaocdn.com/file/4976bada04f9b3c31fb51e0cd6a3237dff026311.jpg");
    }

    private void loadDrawables() {
        drawables = new ArrayList<>();
        drawables.add("http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg");
        drawables.add("http://weidu.file.putaocdn.com/file/d6bcf5c2c6d6cbc408ed7fca3718f76323c8d677.jpg");
        drawables.add("http://weidu.file.putaocdn.com/file/4976bada04f9b3c31fb51e0cd6a3237dff026311.jpg");
        drawables.add("http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg");
    }


*/
/**
 * 虚拟数据
 *//*

private void loadResous() {
        resous = new ArrayList<>();
        tags = new ArrayList<>();
        tags.add("one");
        tags.add("two");
        title = "一二三adfasdfasfasdfasdfasdfasdfasdfasdfasdfasadsfasdfa----sdfasdadf";
        resous.add(0,new Resou(false, false, "", null, ""));
        resous.add(1, new Resou(true, true, "http://weidu.file.putaocdn.com/file/4976bada04f9b3c31fb51e0cd6a3237dff026311.jpg", tags, title));
        title = "5yue5asdfasdfasdfasdf";
        resous.add(2,new Resou(false, false, "http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg", tags, title));

        tags.add("gogoasdfa");
        title = "wen";
        resous.add(3,new Resou(true, false, "http://weidu.file.putaocdn.com/file/d6bcf5c2c6d6cbc408ed7fca3718f76323c8d677.jpg", tags, title));
        title = "mangadsfasdfasdfasdfadsfasdfasdfasdfasdfasdfasdfas----dfasdfaa";
        resous.add(4,new Resou(false, false, "http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg", tags, title));
*/
