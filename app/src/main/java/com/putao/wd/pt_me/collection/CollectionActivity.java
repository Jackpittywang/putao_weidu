package com.putao.wd.pt_me.collection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.CreateApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.created.adapter.FancyAdapter;
import com.putao.wd.model.Create;
import com.putao.wd.model.Creates;
import com.putao.wd.pt_me.participation.ParticipationAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;
import com.sunnybear.library.view.recycler.listener.OnItemLongClickListener;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/5.
 */
public class CollectionActivity extends PTWDActivity implements PullToRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadMoreListener, OnItemClickListener<Create>, View.OnClickListener {
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_participation)
    LoadMoreRecyclerView rv_participation;
    @Bind(R.id.ll_empty)
    LinearLayout ll_empty;

    private FancyAdapter adapter;
    private int mPage = 1;
    private boolean hasMoreData;
    private AlertDialog mDeleteDialog;
    private boolean isCollection = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_participation;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        adapter = new FancyAdapter(this, null);
        rv_participation.setAdapter(adapter);
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
                            isCollection = false;
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
            rv_participation.noMoreLoading();
            hasMoreData = false;
        } else {
            mPage++;
            hasMoreData = true;
        }
    }

    private void addListenter() {
        rv_participation.setOnItemClickListener(this);
        ll_empty.setOnClickListener(this);
        ptl_refresh.setOnRefreshListener(this);
        rv_participation.setOnLoadMoreListener(this);
        rv_participation.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Serializable serializable, int position) {
                mDeleteDialog = new AlertDialog.Builder(mContext)
                        .setTitle("删除收藏")
                        .setMessage("是否删除该条收藏数据")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                networkRequest(CreateApi.setCreateAction(mCreate.getId(), 3),
                                        new SimpleFastJsonCallback<String>(String.class, loading) {
                                            @Override
                                            public void onSuccess(String url, String result) {
                                                mCreate.setFollow_status(1);
                                                Logger.d(result.toString());

//                                        adapter.delete();
//                                        if (adapter.getItemCount() == 0) isCollection = true;
//                                        eventAddressDelete(address.getId());
//                                        getAddressLists();
                                            }
                                        });

                            }
                        }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDeleteDialog.dismiss();
                            }
                        }).show();
            }
        });
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
                        rv_participation.loadMoreComplete();
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
        bundle.putSerializable(CollectionDetailActivity.CREATE, (Serializable) adapter.getItems());
        bundle.putInt(CollectionDetailActivity.POSITION, position);
        bundle.putInt(CollectionDetailActivity.PAGE_COUNT, mPage);
        bundle.putBoolean(CollectionDetailActivity.HAS_MORE_DATA, hasMoreData);
        YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_interested_detail);
        startActivity(CollectionDetailActivity.class, bundle);
    }

//    @Subcriber(tag = CreateBasicDetailActivity.EVENT_CONCERNS_REFRESH)
//    private void eventRefresh(String str) {
////        adapter.delete(mCreate);
//        if (adapter.getItemCount() == 1) {
//            ptl_refresh.setVisibility(View.GONE);
//            ll_empty.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Subcriber(tag = CreateCommentActivity.EVENT_ADD_CREAT_COMMENT)
//    public void eventAddCommentCount(int position) {
//        if (adapter.getItemCount() > position) {
//            Create item = adapter.getItem(position);
//            item.getComment().setCount(item.getComment().getCount() + 1);
//            adapter.notifyItemChanged(position);
//        }
//    }
//
//    @Subcriber(tag = CreateCommentActivity.EVENT_DELETE_CREAT_COMMENT)
//    public void evenDeleteCommentCount(int position) {
//        if (adapter.getItemCount() > position) {
//            Create item = adapter.getItem(position);
//            item.getComment().setCount(item.getComment().getCount() - 1);
//            adapter.notifyItemChanged(position);
//        }
//    }
//
//    @Subcriber(tag = PutaoCreatedSecondFragment.EVENT_ADD_CREAT_COOL)
//    public void eventAddCoolCount(int position) {
//        addCool(position);
//    }
//
//
//    @Subcriber(tag = FancyFragment.EVENT_ADD_FANCY_COOL)
//    public void eventAddCoolCount2(int position) {
//        addCool(position);
//    }
//
//    private void addCool(int position) {
//        if (adapter.getItemCount() > position) {
//            Create item = adapter.getItem(position);
//            item.getVote().setUp(item.getVote().getUp() + 1);
//            item.setVote_status(1);
//            adapter.notifyItemChanged(position);
//        }
//    }

    @Override
    public void onClick(View v) {
        initData();
    }

//    @Override
//    public void onLeftAction() {
//        super.onLeftAction();
//        YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_interested_back);
//    }
}
