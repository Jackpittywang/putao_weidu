package com.putao.wd.pt_companion;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ServiceMessage;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.pt_companion.adapter.GameDetailAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/18.
 */
public class LookHistoryActivity extends PTWDActivity {
    public static final String HISTORY_SERVICE_ID = "history_service_id";
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_lookHistory)
    LoadMoreRecyclerView rv_lookHistory;
    @Bind(R.id.rl_no_history)
    RelativeLayout rl_no_history;

    List<ServiceMessageList> contents;
    private String service_id;
    GameDetailAdapter adapter;
    int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commpain_lookhistory;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        service_id = args.getString(HISTORY_SERVICE_ID);
        adapter = new GameDetailAdapter(this, null);
        rv_lookHistory.setAdapter(adapter);
        addListener();
        lookHistoryData(service_id);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    public void addListener() {
        rv_lookHistory.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreHistory(service_id);
            }
        });
    }

    /**
     * 加载数据
     */
    private void lookHistoryData(String service_id) {
        currentPage = 1;
        networkRequest(CompanionApi.lookHistoryData(service_id, ""), new SimpleFastJsonCallback<ServiceMessage>(ServiceMessage.class, loading) {

            @Override
            public void onSuccess(String url, ServiceMessage result) {
                if (result != null && result.getLists().size() > 0) {
                    contents = result.getLists();
                    adapter.replaceAll(contents);
                    rv_lookHistory.setVisibility(View.VISIBLE);
                    rl_no_history.setVisibility(View.GONE);
                } else {
                    ptl_refresh.setVisibility(View.GONE);
                    rl_no_history.setVisibility(View.VISIBLE);
                }
                ptl_refresh.refreshComplete();
                checkLoadMoreComplete(contents);
                loading.dismiss();
            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                super.onFailure(url, statusCode, msg);
                rl_no_history.setVisibility(View.VISIBLE);
                ptl_refresh.setVisibility(View.GONE);
                ptl_refresh.refreshComplete();
            }
        });
    }

    private void checkLoadMoreComplete(List<ServiceMessageList> result) {
        if (result == null)
            rv_lookHistory.noMoreLoading();
        else currentPage++;
    }

    /**
     * 上拉加载更多
     */
    private void loadMoreHistory(String service_id) {
        networkRequest(CompanionApi.lookHistoryData(service_id, ""), new SimpleFastJsonCallback<ServiceMessage>(ServiceMessage.class, loading) {

            @Override
            public void onSuccess(String url, ServiceMessage result) {
                if (result != null && result.getLists().size() > 0) {
                    contents = result.getLists();
                    adapter.addAll(contents);
                    rv_lookHistory.setVisibility(View.VISIBLE);
                    rl_no_history.setVisibility(View.GONE);
                } else {
                    ptl_refresh.setVisibility(View.GONE);
                    rl_no_history.setVisibility(View.VISIBLE);
                }
                ptl_refresh.refreshComplete();
                checkLoadMoreComplete(contents);
                loading.dismiss();
            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                super.onFailure(url, statusCode, msg);
                rl_no_history.setVisibility(View.VISIBLE);
                ptl_refresh.setVisibility(View.GONE);
                ptl_refresh.refreshComplete();
            }
        });
    }
}
