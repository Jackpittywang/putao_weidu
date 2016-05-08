package com.putao.wd.pt_discovery;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.api.DisCoveryApi;
import com.putao.wd.model.DisCovery;
import com.putao.wd.model.DiscoveryResource;
import com.putao.wd.model.Resou;
import com.putao.wd.pt_companion.ArticlesDetailActivity;
import com.putao.wd.pt_companion.DividerDecoration;
import com.putao.wd.pt_companion.adapter.ReplyListsAdapter;
import com.putao.wd.pt_discovery.adapter.ResourceAdapter;
import com.squareup.okhttp.internal.framed.Variant;
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
import com.sunnybear.library.view.recycler.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.sunnybear.library.view.recycler.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

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
    private List<DiscoveryResource> resous;
    private List<String> tags;
    private List<String> drawables;
    private List<String> hotTags;
    private String title;
    private ResourceAdapter mAdapter;

    private int mPage;

    private boolean isShowHead;
    private View childAt;
    private RecyclerView.LayoutParams layoutParams;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_resource;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

        freshResource();


        mAdapter = new ResourceAdapter(mActivity, resous, drawables, hotTags);
        // Set layout manager
        rv_discovery.setAdapter(mAdapter);
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
//            @Override
//            public void onLoadMore() {
//                networkRequest(DisCoveryApi.getfindVideo(String.valueOf(mPage)),
//                        new SimpleFastJsonCallback<ArrayList<DisCovery>>(DisCovery.class, loading) {
//
//                            @Override
//                            public void onSuccess(String url, ArrayList<DisCovery> result) {
//                                if (result != null && result.size() > 0) {
////                                    mAdapter.addAll(result);
//                                }
//                                rv_discovery.loadMoreComplete();
//                                checkLoadMoreComplete(result);
//                                loading.dismiss();
//                            }
//                        }, false);
//            }
        });
        final LinearLayoutManager discoveryLayoutManager = (LinearLayoutManager) rv_discovery.getLayoutManager();

        rv_discovery.setOnItemClickListener(new OnItemClickListener<DiscoveryResource>() {
            @Override
            public void onItemClick(DiscoveryResource resou, int position) {
                if (position == 0) {
                    return;
                }
                EventBusHelper.post(resou.getTitle(), AccountConstants.EventBus.EVENT_DISCOVERY_RESOURCE);
            }
        });

        rv_discovery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (childAt == null) {
                    childAt = discoveryLayoutManager.getChildAt(1);
                    layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                }
                if (discoveryLayoutManager.findFirstVisibleItemPosition() > 0 && !isShowHead) {
//                    layoutManager1.removeViewAt(1);
                    discoveryLayoutManager.removeView(childAt);
//                    rv_discovery.setLayoutManager(layoutManager1);
                    /*RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams();
                    layoutParams.setMargins(DensityUtil.dp2px(mActivity, 15), 0, 0, 0);
                    childAt.setLayoutParams(layoutParams);*/
//                    RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams();
                    rl_main.addView(childAt);
                    mAdapter.notifyDataSetChanged();
                    isShowHead = true;
                } else if (discoveryLayoutManager.findFirstVisibleItemPosition() == 0 && isShowHead) {
                    rl_main.removeView(childAt);
                    childAt.setLayoutParams(layoutParams);
                    discoveryLayoutManager.addView(childAt, 1);
                    mAdapter.notifyDataSetChanged();
                    isShowHead = false;
                }
            }
        });
    }

    /**
     * 加载视频列表
     */
    private void getDisCovery() {
        rv_discovery.noMoreLoading();
        ptl_refresh.refreshComplete();
        loading.dismiss();
        rv_discovery.loadMoreComplete();

//        networkRequestCache(DisCoveryApi.getfindVideo(String.valueOf(mPage)),
//                new SimpleFastJsonCallback<ArrayList<DisCovery>>(DisCovery.class, loading) {
//
//                    @Override
//                    public void onSuccess(String url, ArrayList<DisCovery> result) {
//                        cacheData(url, result);
//                        if (result != null && result.size() > 0) {
////                            disCoveries = result;
////                            adapter.replaceAll(result);
////                            rl_no_discovery.setVisibility(View.GONE);
//                            ptl_refresh.setVisibility(View.VISIBLE);
//                            mPage++;
//                            rv_discovery.loadMoreComplete();
//                        } else {
//                            rv_discovery.noMoreLoading();
////                            rl_no_discovery.setVisibility(View.VISIBLE);
//                            ptl_refresh.setVisibility(View.GONE);
//                        }
//                        ptl_refresh.refreshComplete();
//                        loading.dismiss();
//                    }
//
//                    @Override
//                    public void onFailure(String url, int statusCode, String msg) {
//                        super.onFailure(url, statusCode, msg);
//                        //多了尾布局，因此至少是1
//                        if (mAdapter.getItemCount() <= 1) {
////                            rl_no_discovery_failure.setVisibility(View.VISIBLE);
////                            rl_no_discovery.setVisibility(View.GONE);
//                            ptl_refresh.setVisibility(View.GONE);
//                            ptl_refresh.refreshComplete();
//                        }
//                    }
//                }, 600 * 1000);
    }

    private void checkLoadMoreComplete(ArrayList<DisCovery> result) {

    }

    private void freshResource() {
//        networkRequest(CompanionApi.getCompanyArticleComment("1", "1", "1", "1"), new SimpleFastJsonCallback<DiscoveryResource>(DiscoveryResource.class,loading) {
//            @Override
//            public void onSuccess(String url, DiscoveryResource result) {
//
//            }
//        });

        mPage = 0;
        networkRequest(DisCoveryApi.getFindResource(String.valueOf(mPage)), new SimpleFastJsonCallback<ArrayList<DiscoveryResource>>(DiscoveryResource.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<DiscoveryResource> result) {
                cacheData(url, result);
                if (result != null && result.size() > 0) {
                    resous = result;
                    resous.add(0, new DiscoveryResource());
                    resous.add(1, new DiscoveryResource());
                    mAdapter.replaceAll(result);
                    mPage++;
                    rv_discovery.loadMoreComplete();
                } else {
                    rv_discovery.noMoreLoading();
                }
                ptl_refresh.refreshComplete();
                loading.dismiss();
            }
        });

//        networkRequest();

//        mPage = 0;
//
//        loadResous();
        loadDrawables();
        loadTags();
//
//        ptl_refresh.setVisibility(View.VISIBLE);
//        mPage++;
//        rv_discovery.loadMoreComplete();
//
//        ptl_refresh.refreshComplete();
//        loading.dismiss();
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


    /**
     * 虚拟数据
     */
    private void loadResous() {
//        resous = new ArrayList<>();
//        tags = new ArrayList<>();
//        tags.add("one");
//        tags.add("two");
//        title = "一二三adfasdfasfasdfasdfasdfasdfasdfasdfasdfasadsfasdfa----sdfasdadf";
//        resous.add(0,new Resou(false, false, "", null, ""));
//        resous.add(1, new Resou(true, true, "http://weidu.file.putaocdn.com/file/4976bada04f9b3c31fb51e0cd6a3237dff026311.jpg", tags, title));
//        title = "5yue5asdfasdfasdfasdf";
//        resous.add(2,new Resou(false, false, "http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg", tags, title));
//
//        tags.add("gogoasdfa");
//        title = "wen";
//        resous.add(3,new Resou(true, false, "http://weidu.file.putaocdn.com/file/d6bcf5c2c6d6cbc408ed7fca3718f76323c8d677.jpg", tags, title));
//        title = "mangadsfasdfasdfasdfadsfasdfasdfasdfasdfasdfasdfas----dfasdfaa";
//        resous.add(4,new Resou(false, false, "http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg", tags, title));
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Subcriber(tag = AccountConstants.EventBus.EVENT_DISCOVERY_CAROUSEL)
    public void eventDiscoveryCarousel(Bundle bundle) {

        String id = bundle.getString("id");
        if (StringUtils.equals(id, "订阅号")) {
            startActivity(ArticlesDetailActivity.class, bundle);
        } else if (StringUtils.equals(id, "h5页面")) {

        } else if (StringUtils.equals(id, "专题页")) {
            ToastUtils.showToastShort(mActivity, bundle.getInt("po") + id);
        }
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_DISCOVERY_HOT_TAG)
    public void eventDiscoveryHotTag(BasicRecyclerView rv_discovery_hot_tag) {

        this.rv_discovery_hot_tag = rv_discovery_hot_tag;
//        String id = bundle.getString("id");
//        if(StringUtils.equals(id,"活动")){
//            startActivity(ArticlesDetailActivity.class,bundle);
//        }else if(StringUtils.equals(id,"专题")){
//
//        }
//
//        int position = bundle.getInt("po");
//        ToastUtils.showToastShort(mActivity,position + "-------"+hotTags.get(position));

    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_DISCOVERY_RESOURCE)
    public void eventDiscoveryResource(String url) {


        Bundle bundle = new Bundle();
        bundle.putString("url", url);
//        startActivity(,bundle);

        ToastUtils.showToastShort(mActivity, url);
    }
}
