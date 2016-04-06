package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.home.adapter.DiscoveryAdapter;
import com.putao.wd.model.DisCovery;
import com.putao.wd.model.StoreProduct;
import com.putao.wd.model.StoreProductHome;
import com.putao.wd.video.YoukuVideoPlayerActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.animators.ScaleInAnimation;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/5.
 */
public class PutaoDiscoveryFragment extends BasicFragment implements OnItemClickListener {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_discovery)
    LoadMoreRecyclerView rv_discovery;

    private int currentPage = 1;
    private DiscoveryAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        rv_discovery.setVisibility(View.VISIBLE);
        ArrayList<DisCovery> disCoveries = new ArrayList<>();
        disCoveries.add(new DisCovery());
        adapter = new DiscoveryAdapter(mActivity, disCoveries);
        adapter.setAnimations(new ScaleInAnimation(1.0F));
        rv_discovery.setAdapter(adapter);
        addListener();
        getDisCovery();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void addListener() {
        /**
         * 刷新
         * */
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDisCovery();
            }
        });
        /**
         * 下拉刷新
         * */
        rv_discovery.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                currentPage++;
//                networkRequest(StoreApi.getStoreHome(String.valueOf(currentPage)),
//                        new SimpleFastJsonCallback<StoreProductHome>(StoreProductHome.class, loading) {
//                            @Override
//                            public void onSuccess(String url, StoreProductHome result) {
//                                List<StoreProduct> products = result.getData();
//                                if (products != null && products.size() > 0)
//                                    adapter.addAll(products);
//                                if (result.getCurrent_page() != result.getTotal_page() && result.getTotal_page() != 0) {
//                                    currentPage++;
//                                    rv_content.loadMoreComplete();
//                                } else rv_content.noMoreLoading();
//                                loading.dismiss();
//                            }
//                        });
            }
        });
        rv_discovery.setOnItemClickListener(this);
    }

    /**
     * 刷新视频列表
     */
    private void refreshDisCovery() {
        currentPage = 1;
//        networkRequest(StoreApi.getStoreHome(String.valueOf(currentPage)),
//                new SimpleFastJsonCallback<StoreProductHome>(StoreProductHome.class, loading) {
//                    @Override
//                    public void onSuccess(String url, StoreProductHome result) {
//                        List<StoreProduct> products = result.getData();
//                        if (products != null && products.size() > 0)
//                            adapter.replaceAll(products);
//                        if (result.getCurrent_page() != result.getTotal_page() && result.getTotal_page() != 0) {
//                            currentPage++;
//                            rv_content.loadMoreComplete();
//                        } else rv_content.noMoreLoading();
//                        ptl_refresh.refreshComplete();
//                        loading.dismiss();
//                    }
//
//                    @Override
//                    public void onFailure(String url, int statusCode, String msg) {
//                        super.onFailure(url, statusCode, msg);
//                        ptl_refresh.refreshComplete();
//                    }
//                });
    }

    /**
     * 加载视频列表
     */
    private void getDisCovery() {
//        networkRequestCache(StoreApi.getStoreHome(String.valueOf(currentPage)),
//                new SimpleFastJsonCallback<StoreProductHome>(StoreProductHome.class, loading) {
//                    @Override
//                    public void onSuccess(String url, StoreProductHome result) {
//                        List<StoreProduct> products = result.getData();
//                        cacheData(url, result);
//                        if (products != null && products.size() > 0)
//                            adapter.replaceAll(products);
//                        if (result.getCurrent_page() != result.getTotal_page() && result.getTotal_page() != 0)
//                            rv_content.loadMoreComplete();
//                        else
//                            rv_content.noMoreLoading();
//                        ptl_refresh.refreshComplete();
////                        loading.dismiss();
//                    }
//
//                    @Override
//                    public void onFailure(String url, int statusCode, String msg) {
//                        super.onFailure(url, statusCode, msg);
//                        ptl_refresh.refreshComplete();
//                    }
//                }, 60 * 1000);
    }

    @Override
    public void onItemClick(Serializable serializable, int position) {
        Bundle bundle = new Bundle();
        System.out.println("hguidfshgjidfgjdgifdjhg");
        startActivity(YoukuVideoPlayerActivity.class, bundle);
    }
}
