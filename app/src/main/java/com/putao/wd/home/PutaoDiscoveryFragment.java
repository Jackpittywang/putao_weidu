package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.api.DisCoveryApi;
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

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/5.
 */
public class PutaoDiscoveryFragment extends PTWDFragment implements OnItemClickListener, OnClickListener {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_discovery)
    LoadMoreRecyclerView rv_discovery;
    @Bind(R.id.rl_no_discovery)
    RelativeLayout rl_no_discovery;

    private int currentPage = 1;
    private DiscoveryAdapter adapter;
    ArrayList<DisCovery> disCoveries;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
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
                getDisCovery();
            }
        });
        /**
         * 上拉加载更多
         * */
        rv_discovery.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                networkRequest(DisCoveryApi.getfindVideo(currentPage),
                        new SimpleFastJsonCallback<ArrayList<DisCovery>>(DisCovery.class, loading) {

                            @Override
                            public void onSuccess(String url, ArrayList<DisCovery> result) {
                                if (result != null && result.size() > 0) {
                                    disCoveries = result;
                                    rl_no_discovery.setVisibility(View.GONE);
                                    rv_discovery.setVisibility(View.VISIBLE);
                                    adapter.replaceAll(result);
                                } else {
                                    rl_no_discovery.setVisibility(View.VISIBLE);
                                    rv_discovery.setVisibility(View.GONE);
                                }
                                checkLoadMoreComplete(result);
                                loading.dismiss();
                            }
                        });
            }
        });
        rv_discovery.setOnItemClickListener(this);
        rl_no_discovery.setOnClickListener(this);
    }

    /**
     * 加载视频列表
     */
    private void getDisCovery() {
        currentPage = 1;
        networkRequestCache(DisCoveryApi.getfindVideo(currentPage),
                new SimpleFastJsonCallback<ArrayList<DisCovery>>(DisCovery.class, loading) {

                    @Override
                    public void onSuccess(String url, ArrayList<DisCovery> result) {
                        cacheData(url, result);
                        if (result != null && result.size() > 0) {
                            disCoveries = result;
                            rl_no_discovery.setVisibility(View.GONE);
                            rv_discovery.setVisibility(View.VISIBLE);
                            adapter.replaceAll(result);
                        } else {
                            rl_no_discovery.setVisibility(View.VISIBLE);
                            rv_discovery.setVisibility(View.GONE);
                        }
                        checkLoadMoreComplete(result);
                        ptl_refresh.refreshComplete();
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        rl_no_discovery.setVisibility(View.VISIBLE);
                        rv_discovery.setVisibility(View.GONE);
                        ptl_refresh.refreshComplete();
                    }
                }, 60 * 1000);
    }

    private void checkLoadMoreComplete(ArrayList<DisCovery> result) {
        if (result.size() < 5)
            rv_discovery.noMoreLoading();
        else currentPage++;
    }

    @Override
    public void onItemClick(Serializable serializable, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(YoukuVideoPlayerActivity.BUNDLE_VID, disCoveries.get(position).getVideo_url());
        startActivity(YoukuVideoPlayerActivity.class, bundle);
    }

    @OnClick({R.id.rl_no_discovery})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_no_discovery:
                getDisCovery();
                break;
        }
    }
}
