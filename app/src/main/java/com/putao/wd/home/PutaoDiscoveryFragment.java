package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.putao.ptx.push.core.NetworkUtil;
import com.putao.wd.R;
import com.putao.wd.api.DisCoveryApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.home.adapter.DiscoveryAdapter;
import com.putao.wd.model.DisCovery;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.animators.ScaleInAnimation;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/5.
 */
public class PutaoDiscoveryFragment extends PTWDFragment implements OnClickListener {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_discovery)
    LoadMoreRecyclerView rv_discovery;
    @Bind(R.id.rl_no_discovery)
    RelativeLayout rl_no_discovery;
    @Bind(R.id.rl_no_discovery_failure)
    RelativeLayout rl_no_discovery_failure;
    @Bind(R.id.btn_no_data)
    Button btn_no_data;

    private int currentPage = 1;
    private DiscoveryAdapter adapter;
    ArrayList<DisCovery> disCoveries;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        navigation_bar.setLeftClickable(false);
        navigation_bar.getLeftView().setVisibility(View.GONE);
        adapter = new DiscoveryAdapter(mActivity, null);
        adapter.setAnimations(new ScaleInAnimation(1.0F));
        rv_discovery.setAdapter(adapter);
        getDisCovery();
        addListener();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onStart() {
        super.onStart();
        getDisCovery();
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
                networkRequest(DisCoveryApi.getfindVideo(String.valueOf(currentPage)),
                        new SimpleFastJsonCallback<ArrayList<DisCovery>>(DisCovery.class, loading) {

                            @Override
                            public void onSuccess(String url, ArrayList<DisCovery> result) {
                                if (result != null && result.size() > 0) {
                                    disCoveries = result;
                                    adapter.addAll(result);
                                }
                                rv_discovery.loadMoreComplete();
                                checkLoadMoreComplete(result);
                                loading.dismiss();
                            }
                        }, false);
            }
        });
        rl_no_discovery.setOnClickListener(this);
        btn_no_data.setOnClickListener(this);
    }

    /**
     * 加载视频列表
     */
    private void getDisCovery() {
        currentPage = 1;
        networkRequestCache(DisCoveryApi.getfindVideo(String.valueOf(currentPage)),
                new SimpleFastJsonCallback<ArrayList<DisCovery>>(DisCovery.class, loading) {

                    @Override
                    public void onSuccess(String url, ArrayList<DisCovery> result) {
                        cacheData(url, result);
                        if (result != null && result.size() > 0) {
                            disCoveries = result;
                            adapter.replaceAll(result);
                            rl_no_discovery.setVisibility(View.GONE);
                            ptl_refresh.setVisibility(View.VISIBLE);
                            currentPage++;
                            rv_discovery.loadMoreComplete();
                        } else {
                            rv_discovery.noMoreLoading();
                            rl_no_discovery.setVisibility(View.VISIBLE);
                            ptl_refresh.setVisibility(View.GONE);
                        }
                        ptl_refresh.refreshComplete();
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        //多了尾布局，因此至少是1
                        if (adapter.getItemCount() <= 1) {
                            rl_no_discovery_failure.setVisibility(View.VISIBLE);
                            rl_no_discovery.setVisibility(View.GONE);
                            ptl_refresh.setVisibility(View.GONE);
                            ptl_refresh.refreshComplete();
                        }
                    }
                }, 600 * 1000);
    }

    private void checkLoadMoreComplete(ArrayList<DisCovery> result) {
        if (result == null)
            rv_discovery.noMoreLoading();
        else currentPage++;
    }

    @OnClick({R.id.rl_no_discovery})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_no_discovery:
                getDisCovery();
                break;
            case R.id.btn_no_data:
                if (NetworkUtil.isNetworkAvailable(mActivity)) {//没有网络连接
                    ToastUtils.showToastShort(mActivity, "获取数据失败");
                } else
                    getDisCovery();
                break;
        }
    }
}
