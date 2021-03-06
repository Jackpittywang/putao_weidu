package com.putao.wd.home;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.api.CreateApi;
import com.putao.wd.created.CreateCommentActivity;
import com.putao.wd.created.CreateDetailActivity;
import com.putao.wd.home.adapter.CreateAdapter;
import com.putao.wd.model.Create;
import com.putao.wd.model.Creates;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.LoadingHUD;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;

import butterknife.Bind;

/**
 * 创造(首页)
 * Created by guchenkai on 2016/1/13.
 */
@Deprecated
public class PutaoCreatedSecondFragment extends BasicFragment implements OnItemClickListener<Create>, PullToRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadMoreListener {
    @Bind(R.id.rv_created)
    LoadMoreRecyclerView rv_created;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;

    public static final String EVENT_ADD_CREAT_COOL = "event_add_creat_cool";
    public static final String EVENT_ADD_CREAT_NOT_COOL = "event_add_creat_not_cool";
    public static final String EVENT_CREAT_CONCERNS_CHANGE = "event_creat_concerns_change";
    private CreateAdapter adapter;
    private int mPage;
    private boolean hasMoreData;
    private LoadingHUD mLoading;


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
        mLoading = LoadingHUD.getInstance(mActivity);
        mLoading.show();
    }

    private void initData() {
        mPage = 1;
        networkRequest(CreateApi.getCreateList(1, mPage),
                new SimpleFastJsonCallback<Creates>(Creates.class, loading) {
                    @Override
                    public void onSuccess(String url, Creates result) {
                        adapter.replaceAll(result.getData());
                        ptl_refresh.refreshComplete();
                        checkLoadMoreComplete(result.getCurrentPage(), result.getTotalPage());
                        mLoading.dismiss();
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ptl_refresh.refreshComplete();
                    }
                }, false);
    }

    private void checkLoadMoreComplete(int currentPage, int totalPage) {
        if (currentPage == totalPage) {
            hasMoreData = false;
            rv_created.noMoreLoading();
        } else {
            hasMoreData = true;
            mPage++;
        }
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
        bundle.putSerializable(CreateDetailActivity.CREATE, (Serializable) adapter.getItems());
        bundle.putInt(CreateDetailActivity.POSITION, position);
        bundle.putInt(CreateDetailActivity.PAGE_COUNT, mPage);
        bundle.putBoolean(CreateDetailActivity.HAS_MORE_DATA, hasMoreData);
        startActivity(CreateDetailActivity.class, bundle);
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
                        checkLoadMoreComplete(result.getCurrentPage(), result.getTotalPage());
                        loading.dismiss();
                    }
                });
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

    @Subcriber(tag = CreateAdapter.NOT_COOL)
    public void notCool(String id) {
        networkRequest(CreateApi.setCreateAction(id, 2),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {

                    }
                });
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

    @Subcriber(tag = EVENT_ADD_CREAT_COOL)
    public void eventAddCoolCount(int position) {
        if (adapter.getItemCount() > position) {

            Create item = adapter.getItem(position);
            item.getVote().setUp(item.getVote().getUp() + 1);
            item.setVote_status(1);
            adapter.notifyItemChanged(position);
        }
    }

    @Subcriber(tag = EVENT_ADD_CREAT_NOT_COOL)
    public void eventAddNotCoolCount(int position) {
        if (adapter.getItemCount() > position) {
            Create item = adapter.getItem(position);
            item.getVote().setDown(item.getVote().getDown() + 1);
            item.setVote_status(2);
            adapter.notifyItemChanged(position);
        }
    }

    @Subcriber(tag = EVENT_CREAT_CONCERNS_CHANGE)
    public void ChangeConcerns(int position) {
        if (adapter.getItemCount() > position) {
            Create item = adapter.getItem(position);
            item.setFollow_status(item.getFollow_status() == 1 ? 0 : 1);
        }
    }
}
