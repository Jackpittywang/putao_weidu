package com.putao.wd.me.attention;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.CreateApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.created.CreateBasicDetailActivity;
import com.putao.wd.created.CreateCommentActivity;
import com.putao.wd.created.CreateDetailActivity;
import com.putao.wd.created.adapter.FancyAdapter;
import com.putao.wd.model.Create;
import com.putao.wd.model.Creates;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;


import java.io.Serializable;
import java.util.List;

import butterknife.Bind;

/**
 * 我的关注
 * Created by zhanghao on 2016/1/25.
 */
public class ConcernsActivity extends PTWDActivity implements PullToRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadMoreListener, OnItemClickListener<Create>, View.OnClickListener {
    @Bind(R.id.rv_created)
    LoadMoreRecyclerView rv_created;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.ll_empty)
    LinearLayout ll_empty;

    private boolean isRefresh = false;
    private FancyAdapter adapter;
    private boolean hasMoreData;
    private int mPage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_concerns;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        Logger.d("PutaoCreatedFragment启动");
        addNavigation();
        adapter = new FancyAdapter(mContext, null);
        rv_created.setAdapter(adapter);
        initData();
        addListenter();
    }

    private void initData() {
        mPage = 1;
        networkRequest(CreateApi.getCreateMyfollows(mPage),
                new SimpleFastJsonCallback<Creates>(Creates.class, loading) {
                    @Override
                    public void onSuccess(String url, Creates result) {
                        List<Create> details = result.getData();
                        if (details != null && details.size() > 0) {
                            ll_empty.setVisibility(View.GONE);
                            ptl_refresh.setVisibility(View.VISIBLE);
                            adapter.replaceAll(details);
                        } else {
                            ptl_refresh.setVisibility(View.GONE);
                            ll_empty.setVisibility(View.VISIBLE);
                        }
                        checkLoadMoreComplete(result.getCurrentPage(), result.getTotalPage());
                        ptl_refresh.refreshComplete();
                        loading.dismiss();

                        /*adapter.replaceAll(result.getData());
                        checkLoadMoreComplete(result.getCurrentPage(), result.getTotalPage());
                        loading.dismiss();*/
                    }
                });
    }

    private void checkLoadMoreComplete(int currentPage, int totalPage) {
        if (currentPage == totalPage) {
            rv_created.noMoreLoading();
            hasMoreData = false;
        } else {
            mPage++;
            hasMoreData = true;
        }
    }

    private void addListenter() {
        rv_created.setOnItemClickListener(this);
        ll_empty.setOnClickListener(this);
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
        networkRequest(CreateApi.getCreateMyfollows(mPage),
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

    private Create mCreate;

    @Override
    public void onItemClick(Create create, int position) {
        mCreate = create;
        Bundle bundle = new Bundle();
        bundle.putSerializable(CreateDetailActivity.CREATE, (Serializable) adapter.getItems());
        bundle.putInt(CreateDetailActivity.POSITION, position);
        bundle.putInt(CreateDetailActivity.PAGE_COUNT, mPage);
        bundle.putBoolean(CreateDetailActivity.HAS_MORE_DATA, hasMoreData);
        YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_interested_detail);
        startActivity(ConcernsDetailActivity.class, bundle);
    }

    @Subcriber(tag = CreateBasicDetailActivity.EVENT_CONCERNS_REFRESH)
    private void eventRefresh(String str) {
        adapter.delete(mCreate);
        if (adapter.getItemCount() == 1) {
            ptl_refresh.setVisibility(View.GONE);
            ll_empty.setVisibility(View.VISIBLE);
        }
    }

    @Subcriber(tag = CreateCommentActivity.EVENT_ADD_CREAT_COMMENT)
    public void eventAddCommentCount(int position) {
        if (adapter.getItemCount() > position) {
            Create item = adapter.getItem(position);
            item.getComment().setCount(item.getComment().getCount() + 1);
            adapter.notifyItemChanged(position);
        }
    }

    @Subcriber(tag = CreateCommentActivity.EVENT_DELETE_CREAT_COMMENT)
    public void evenDeleteCommentCount(int position) {
        if (adapter.getItemCount() > position) {
            Create item = adapter.getItem(position);
            item.getComment().setCount(item.getComment().getCount() - 1);
            adapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onClick(View v) {
        initData();
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_interested_back);
    }
}
