package com.putao.wd.pt_me.collection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.CollectionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Collection;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;
import com.sunnybear.library.view.recycler.listener.OnItemLongClickListener;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * 我的收藏
 * Created by Administrator on 2016/4/5.
 */
public class CollectionActivity extends PTWDActivity implements PullToRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadMoreListener, OnItemClickListener<Collection>, View.OnClickListener {
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_collection)
    LoadMoreRecyclerView rv_collection;
    @Bind(R.id.ll_empty)
    LinearLayout ll_empty;

    private CollectionAdapter adapter;
    private int mPage = 1;
    private boolean hasMoreData;
    private AlertDialog mDeleteDialog;
    private boolean isCollection = true;
    private Collection mCollection;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        adapter = new CollectionAdapter(this, null);
        rv_collection.setAdapter(adapter);
        initData();
        addListenter();
    }

    private void initData() {
        mPage = 1;
        networkRequest(CollectionApi.getCollection(mPage),
                new SimpleFastJsonCallback<ArrayList<Collection>>(Collection.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Collection> result) {
                        if (result != null && result.size() > 0) {
                            isCollection = false;
                            ll_empty.setVisibility(View.GONE);
                            ptl_refresh.setVisibility(View.VISIBLE);
                            rv_collection.setAdapter(adapter);
                        } else {
                            ptl_refresh.setVisibility(View.GONE);
                            ll_empty.setVisibility(View.VISIBLE);
                        }
                        checkLoadMoreComplete(result);
                        ptl_refresh.refreshComplete();
                        loading.dismiss();
                    }
                }
        );
    }

    private void checkLoadMoreComplete(ArrayList<Collection> list) {
        if (null == list) {
            rv_collection.noMoreLoading();
            hasMoreData = false;
        } else {
            mPage++;
            hasMoreData = true;
        }
    }

    private void addListenter() {
        rv_collection.setOnItemClickListener(this);
        ll_empty.setOnClickListener(this);
        ptl_refresh.setOnRefreshListener(this);
        rv_collection.setOnLoadMoreListener(this);
        rv_collection.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Serializable serializable, int position) {
                mDeleteDialog = new AlertDialog.Builder(mContext)
//                        .setTitle("删除收藏数据")
                        .setMessage("确定删除所选的收藏项？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                networkRequest(CreateApi.setCreateAction(mCollection.getId(), 3),
//                                        new SimpleFastJsonCallback<String>(String.class, loading) {
//                                            @Override
//                                            public void onSuccess(String url, String result) {
//                                                mCreate.setFollow_status(1);
////                                                adapter.replaceAll();
//                                            }
//                                        });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

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
        networkRequest(CollectionApi.getCollection(mPage),
                new SimpleFastJsonCallback<ArrayList<Collection>>(Collection.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Collection> result) {
                        if (result != null && result.size() > 0) {
                            adapter.addAll(result);
                        }
                        rv_collection.loadMoreComplete();
                        checkLoadMoreComplete(result);
                        loading.dismiss();
                    }
                });
    }

    @Override
    public void onItemClick(Collection collection, int position) {
        mCollection = collection;
        Bundle bundle = new Bundle();
        bundle.putSerializable(CollectionDetailActivity.CREATE, (Serializable) adapter.getItems());
        bundle.putInt(CollectionDetailActivity.POSITION, position);
        bundle.putInt(CollectionDetailActivity.PAGE_COUNT, mPage);
//        bundle.putBoolean(CollectionDetailActivity.HAS_MORE_DATA, hasMoreData);
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

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_interested_back);
    }
}
