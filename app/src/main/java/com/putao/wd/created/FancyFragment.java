package com.putao.wd.created;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.api.CreateApi;
import com.putao.wd.created.adapter.FancyAdapter;
import com.putao.wd.home.adapter.CreateAdapter;
import com.putao.wd.model.Create;
import com.putao.wd.model.Creates;
import com.putao.wd.model.Marketing;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;

import butterknife.Bind;

/**
 * 奇思妙想
 * Created by zhanghao on 2016/1/14.
 */
public class FancyFragment extends BasicFragment implements PullToRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadMoreListener, OnItemClickListener<Create> {
    @Bind(R.id.rv_created)
    LoadMoreRecyclerView rv_created;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;

    private FancyAdapter adapter;
    private int mPage;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fancy;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        Logger.d("PutaoCreatedFragment启动");
        adapter = new FancyAdapter(mActivity, null);
        rv_created.setAdapter(adapter);
        initData();
        addListenter();
    }

    private void initData() {
        mPage = 1;
        networkRequest(CreateApi.getCreateList(2, mPage),
                new SimpleFastJsonCallback<Creates>(Creates.class, loading) {
                    @Override
                    public void onSuccess(String url, Creates result) {
                        adapter.replaceAll(result.getData());
                        ptl_refresh.refreshComplete();
                        checkLoadMoreComplete(result.getCurrentPage(), result.getTotalPage());
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
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {
        networkRequest(CreateApi.getCreateList(2, mPage),
                new SimpleFastJsonCallback<Creates>(Creates.class, loading) {
                    @Override
                    public void onSuccess(String url, Creates result) {
                        adapter.addAll(result.getData());
                        rv_created.loadMoreComplete();
                        checkLoadMoreComplete(result.getCurrentPage(), result.getTotalPage());
                        loading.dismiss();
                    }
                });
    }

    @Override
    public void onItemClick(Create create, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CreateBasicDetailActivity.CREATE, create);
        bundle.putBoolean(CreateBasicDetailActivity.SHOW_PROGRESS, false);
        startActivity(CreateBasicDetailActivity.class,bundle);
    }

    @Subcriber(tag = CreateAdapter.COOL)
    public void cool(String id) {
        networkRequest(CreateApi.setCreateAction(id, 1),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {

                    }
                });
    }

    @Subcriber(tag = FancyAdapter.COMMENT)
    public void eventComment(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, id);
        startActivity(CreateCommentActivity.class, bundle);
    }
}
