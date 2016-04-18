package com.putao.wd.pt_companion;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Collection;
import com.putao.wd.model.ServiceMessage;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.pt_companion.adapter.LookHistoryAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/18.
 */
public class LookHistoryActivity extends PTWDActivity {
    public static final String HISTORY_SERVICE_ID = "history_service_id";
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rl_no_history)
    RelativeLayout rl_no_history;
    @Bind(R.id.rv_lookHistory)
    LoadMoreRecyclerView rv_lookHistory;

    private LookHistoryAdapter adapter;
    private ArrayList<ServiceMessageList> messageLists;
    private String service_id;
    private int mPage = 1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_commpain_lookhistory;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        service_id = args.getString(HISTORY_SERVICE_ID);
        adapter = new LookHistoryAdapter(this, null);
        rv_lookHistory.setAdapter(adapter);
        lookHistoryData();
        addListener();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void addListener() {
        rv_lookHistory.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loaadMoreData();
            }
        });
    }

    /**
     * 加载查看历史文章列表
     */
    private void lookHistoryData() {
        mPage = 1;
        networkRequest(CompanionApi.lookHistoryData(service_id, "", String.valueOf(mPage)), new SimpleFastJsonCallback<ServiceMessage>(ServiceMessage.class, loading) {
            @Override
            public void onSuccess(String url, ServiceMessage result) {
                if (result != null && result.getLists().size() > 0) {
                    messageLists = result.getLists();
                    adapter.replaceAll(messageLists);
                    rv_lookHistory.setVisibility(View.VISIBLE);
                    rl_no_history.setVisibility(View.GONE);
                } else {
                    rv_lookHistory.setVisibility(View.GONE);
                    rl_no_history.setVisibility(View.VISIBLE);
                }
                ptl_refresh.refreshComplete();
                checkLoadMoreComplete(messageLists);
                loading.dismiss();
            }
        });
    }

    private void checkLoadMoreComplete(ArrayList<ServiceMessageList> result) {
        if (result == null)
            rv_lookHistory.noMoreLoading();
        else mPage++;
    }

    /**
     * 上拉加载更多
     */
    private void loaadMoreData() {
        networkRequest(CompanionApi.lookHistoryData(service_id, "", String.valueOf(mPage)), new SimpleFastJsonCallback<ServiceMessage>(ServiceMessage.class, loading) {
            @Override
            public void onSuccess(String url, ServiceMessage result) {
                if (result != null && result.getLists().size() > 0) {
                    messageLists = result.getLists();
                    adapter.addAll(messageLists);
                    rv_lookHistory.setVisibility(View.VISIBLE);
                    rl_no_history.setVisibility(View.GONE);
                } else {
                    rv_lookHistory.setVisibility(View.GONE);
                    rl_no_history.setVisibility(View.VISIBLE);
                }
                ptl_refresh.refreshComplete();
                checkLoadMoreComplete(messageLists);
                loading.dismiss();
            }
        });
    }
}
