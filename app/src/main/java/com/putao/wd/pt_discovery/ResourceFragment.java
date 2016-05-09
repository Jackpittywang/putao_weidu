package com.putao.wd.pt_discovery;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.api.DisCoveryApi;
import com.putao.wd.model.DisCovery;
import com.putao.wd.model.DiscoveryResource;
import com.putao.wd.model.FindResource;
import com.putao.wd.model.ResourceBanner;
import com.putao.wd.model.ResourceBannerAndTag;
import com.putao.wd.pt_discovery.adapter.ResourceAdapter;
import com.putao.wd.webview.BaseWebViewActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ResourceFragment extends BasicFragment {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_discovery)
    LoadMoreRecyclerView rv_discovery;
    @Bind(R.id.rl_main)
    RelativeLayout rl_main;


    private BasicRecyclerView rv_discovery_hot_tag;
    private List<FindResource> resous;
    private List<ResourceBanner> banners;
    //    private List<ResourceTag> hotTags;
    private ResourceBannerAndTag bannerAndTag;
    private ResourceAdapter mAdapter;

    private int mPage;

    private boolean isShowHead;
    private RecyclerView childAt;
    private RecyclerView.LayoutParams layoutParams;
    private int mScrollX;
    private RelativeLayout.LayoutParams mRelativeLayoutParams;
    private Handler mHandler;
    private Runnable mSetHeaderRunnable;
    private Runnable mRemoveHeaderRunnable;
    private LinearLayoutManager mDiscoveryLayoutManager;

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
        mHandler = new Handler();

        mDiscoveryLayoutManager = (LinearLayoutManager) rv_discovery.getLayoutManager();
        mRelativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(mActivity, 80));
//        mRelativeLayoutParams.setMargins(0, 500, 0, 0);
        mSetHeaderRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    ((ViewGroup) childAt.getParent()).removeView(childAt);
                    childAt.setBackgroundColor(0xffffffff);
                    int margin = DensityUtil.dp2px(mActivity, 10);
                    childAt.setLayoutParams(mRelativeLayoutParams);
                    childAt.setPadding(DensityUtil.dp2px(mActivity, 15), margin, 0, margin);
                    rl_main.addView(childAt);
                    isShowHead = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mRemoveHeaderRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    ((ViewGroup) childAt.getParent()).removeView(childAt);
                    mDiscoveryLayoutManager.removeViewAt(1);
                    rv_discovery.addView(childAt, 1, layoutParams);
                    isShowHead = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
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

        rv_discovery.setOnItemClickListener(new OnItemClickListener<FindResource>() {
            @Override
            public void onItemClick(FindResource resou, int position) {
                EventBusHelper.post(position, AccountConstants.EventBus.EVENT_DISCOVERY_RESOURCE);
            }
        });

        rv_discovery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (childAt == null) {
                    childAt = (RecyclerView) mDiscoveryLayoutManager.getChildAt(1);
                    layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                }
                if (mDiscoveryLayoutManager.findFirstVisibleItemPosition() > 0 && !isShowHead) {
                    checkHeader(mSetHeaderRunnable);
                } else if (mDiscoveryLayoutManager.findFirstVisibleItemPosition() == 0 && isShowHead) {
                    checkHeader(mRemoveHeaderRunnable);
                }
            }
        });
    }


    private void checkHeader(Runnable runnable) {
        ViewParent parent = childAt.getParent();
        if (null != parent) {
            runnable.run();
        } else {
            mHandler.postDelayed(runnable, 200);
        }
    }

    /**
     * 加载视频列表
     */
    private void getDisCovery() {
        rv_discovery.noMoreLoading();
        ptl_refresh.refreshComplete();
        loading.dismiss();
        rv_discovery.loadMoreComplete();

    }

    private void checkLoadMoreComplete(ArrayList<DisCovery> result) {

    }

    private void freshResource() {

        mPage = 0;
        networkRequest(DisCoveryApi.getFindResource(String.valueOf(mPage)), new SimpleFastJsonCallback<DiscoveryResource>(DiscoveryResource.class, loading) {
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

                    mAdapter.replaceAll(resous);
                    mPage++;
                    rv_discovery.loadMoreComplete();
                } else {
                    rv_discovery.noMoreLoading();
                }
                ptl_refresh.refreshComplete();
                loading.dismiss();
            }
        });

        networkRequest(DisCoveryApi.getResouceTop(), new SimpleFastJsonCallback<ResourceBannerAndTag>(ResourceBannerAndTag.class, loading) {
            @Override
            public void onSuccess(String url, ResourceBannerAndTag result) {
                cacheData(url, result);
                if (result != null) {
                    bannerAndTag = result;
                    banners = bannerAndTag.getBanner();
                } else {
                    bannerAndTag = new ResourceBannerAndTag();
                    banners = new ArrayList<>();
                }
                mAdapter.setBannerAndTag(bannerAndTag);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Subcriber(tag = AccountConstants.EventBus.EVENT_DISCOVERY_CAROUSEL)
    public void eventDiscoveryCarousel(int position) {

        ResourceBanner bann = banners.get(position);
        Bundle bundle = new Bundle();
        bundle.putString(BaseWebViewActivity.TITLE, bann.getBanner_title());
        bundle.putString(BaseWebViewActivity.SERVICE_ID, bann.getSid());
        bundle.putString(BaseWebViewActivity.URL, StringUtils.equals(bann.getLocation_type(), "1") ? bann.getLink_url() : bann.getLocation());
        startActivity(BaseWebViewActivity.class, bundle);
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_DISCOVERY_HOT_TAG)
    public void eventDiscoveryHotTag(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(AccountConstants.Bundle.BUNDLE_DISCOVRY_RESOURCE_TAG, type);
        startActivity(SpecialListActivity.class, bundle);
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_DISCOVERY_RESOURCE)
    public void eventDiscoveryResource(int position) {

        FindResource reso = resous.get(position);
        if (position != 0 && position != 1) {
            Bundle bundle = new Bundle();
            bundle.putString(BaseWebViewActivity.TITLE, reso.getTitle());
            bundle.putString(BaseWebViewActivity.SERVICE_ID, reso.getSid());
            bundle.putString(BaseWebViewActivity.URL, reso.getLink_url());
            startActivity(BaseWebViewActivity.class, bundle);
        }
        ToastUtils.showToastShort(mActivity, reso.getLink_url() != null ? reso.getLink_url() : "nonono");
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
