package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.api.CreateApi;
import com.putao.wd.created.CreateBasicDetailActivity;
import com.putao.wd.home.adapter.CreateAdapter;
import com.putao.wd.model.Create;
import com.putao.wd.model.Creates;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import butterknife.Bind;

/**
 * 创造(首页)
 * Created by guchenkai on 2016/1/13.
 */
public class PutaoCreatedSecondFragment extends BasicFragment implements OnItemClickListener<Create>, PullToRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadMoreListener, View.OnClickListener {
    @Bind(R.id.rv_created)
    LoadMoreRecyclerView rv_created;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;

    private CreateAdapter adapter;
    private int mPage;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_created1;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        Logger.d("PutaoCreatedFragment启动");
        adapter = new CreateAdapter(mActivity, null);
        rv_created.setAdapter(adapter);
        initData();
        addListenter();
    }

    private void initData() {
        mPage = 1;
        networkRequest(CreateApi.getCreateList(1, mPage),
                new SimpleFastJsonCallback<Creates>(Creates.class, loading) {
                    @Override
                    public void onSuccess(String url, Creates result) {
                        adapter.replaceAll(result.getData());
                        ptl_refresh.refreshComplete();
                        checkLoadMoreComplete(result.getCurrent_page(), result.getTotal_page());
                        loading.dismiss();
                    }
                });
    }

    private void checkLoadMoreComplete(int currentPage, int totalPage) {
        if (currentPage == totalPage)
            rv_created.noMoreLoading();
        else mPage++;
    }

    private void addListenter() {
        rv_created.setOnItemClickListener(this);
        ptl_refresh.setOnRefreshListener(this);
        rv_created.setOnLoadMoreListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onItemClick(Create create, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CreateBasicDetailActivity.CREATE, create);
        bundle.putBoolean(CreateBasicDetailActivity.SHOW_PROGRESS, true);
        startActivity(CreateBasicDetailActivity.class,bundle);
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {
        networkRequest(CreateApi.getCreateList(1, mPage),
                new SimpleFastJsonCallback<Creates>(Creates.class, loading) {
                    @Override
                    public void onSuccess(String url, Creates result) {
                        adapter.addAll(result.getData());
                        rv_created.loadMoreComplete();
                        checkLoadMoreComplete(result.getCurrent_page(), result.getTotal_page());
                        loading.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
