package com.putao.wd.home;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.home.adapter.DiscoveryAdapter;
import com.putao.wd.model.DisCovery;
import com.putao.wd.video.YoukuVideoPlayerActivity;
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
public class PutaoDiscoveryFragment extends PTWDFragment implements OnItemClickListener {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_discovery)
    LoadMoreRecyclerView rv_discovery;

    private int currentPage = 1;
    private DiscoveryAdapter adapter;
    ArrayList<DisCovery> disCoveries;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        disCoveries = new ArrayList<>();
        adapter = new DiscoveryAdapter(mActivity, null);
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
         * 下拉刷新
         * */
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDisCovery();
            }
        });
        /**
         * 上拉加载更多
         * */
        rv_discovery.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                networkRequest(ExploreApi.getfindVideo(currentPage),
                        new SimpleFastJsonCallback<DisCovery>(DisCovery.class, loading) {
                            @Override
                            public void onSuccess(String url, DisCovery result) {
                                currentPage++;
                                disCoveries.clear();
                                if (null != result) {
                                    DisCovery covery = new DisCovery();
                                    covery.setTitle(result.getTitle());
                                    covery.setVideo_img(result.getVideo_img());
                                    disCoveries.add(covery);
                                    adapter.replaceAll(disCoveries);
                                }
                                if (disCoveries.size() < 0 && disCoveries == null) {
                                    rv_discovery.noMoreLoading();
                                } else {
                                    currentPage++;
                                    rv_discovery.loadMoreComplete();
                                }
                                loading.dismiss();
                            }
                        });
            }
        });
        rv_discovery.setOnItemClickListener(this);
    }

    /**
     * 刷新视频列表
     */
    private void refreshDisCovery() {
        currentPage = 1;
        networkRequest(ExploreApi.getfindVideo(currentPage),
                new SimpleFastJsonCallback<DisCovery>(DisCovery.class, loading) {
                    @Override
                    public void onSuccess(String url, DisCovery result) {
                        disCoveries.clear();
                        if (null != result) {
                            DisCovery covery = new DisCovery();
                            covery.setTitle(result.getTitle());
                            covery.setVideo_img(result.getVideo_img());
                            disCoveries.add(covery);
                            adapter.replaceAll(disCoveries);
                            adapter.notifyDataSetChanged();
                        }
                        if (disCoveries.size() == currentPage) {
                            rv_discovery.noMoreLoading();
                        } else {
                            currentPage++;
                            rv_discovery.loadMoreComplete();
                        }
                        ptl_refresh.refreshComplete();
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ptl_refresh.refreshComplete();
                    }
                });
    }

    /**
     * 加载视频列表
     */
    private void getDisCovery() {
        networkRequestCache(ExploreApi.getfindVideo(currentPage),
                new SimpleFastJsonCallback<DisCovery>(DisCovery.class, loading) {
                    @Override
                    public void onSuccess(String url, DisCovery result) {
                        disCoveries.clear();
                        cacheData(url, result);
                        if (null != result) {
                            DisCovery covery = new DisCovery();
                            covery.setTitle(result.getTitle());
                            covery.setVideo_img(result.getVideo_img());
                            disCoveries.add(covery);
                            adapter.replaceAll(disCoveries);
                        }
                        if (disCoveries.size() == currentPage) {
                            rv_discovery.noMoreLoading();
                        } else {
                            currentPage++;
                            rv_discovery.loadMoreComplete();
                        }
                        ptl_refresh.refreshComplete();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ptl_refresh.refreshComplete();
                    }
                }, 60 * 1000);
    }

    @Override
    public void onItemClick(Serializable serializable, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(YoukuVideoPlayerActivity.BUNDLE_VID, (position + 1) + "");
        System.out.println("=========================" + (position + 1));
        startActivity(YoukuVideoPlayerActivity.class, bundle);
    }
}
