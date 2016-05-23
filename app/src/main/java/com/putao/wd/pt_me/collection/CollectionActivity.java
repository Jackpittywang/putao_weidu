package com.putao.wd.pt_me.collection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.putao.ptx.push.core.NetworkUtil;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.CollectionApi;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Collection;
import com.putao.wd.pt_companion.ArticleDetailForActivitiesActivity;
import com.putao.wd.webview.BaseWebViewActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;
import com.sunnybear.library.view.recycler.listener.OnItemLongClickListener;

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
    @Bind(R.id.rl_no_collection_failure)
    RelativeLayout rl_no_collection_failure;
    @Bind(R.id.btn_no_data)
    Button btn_no_data;

    private CollectionAdapter adapter;
    private int mPage = 1;
    private AlertDialog mDeleteDialog;
    private boolean isCollection = true;
    private ArrayList<Collection> mCollection;
    private int lastItemPosition;
    private int lastVisiblePosition;
    private boolean isFirst = true;

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
                            mCollection = result;
                            adapter.replaceAll(result);
                            isCollection = false;
                            ll_empty.setVisibility(View.GONE);
                            ptl_refresh.setVisibility(View.VISIBLE);
                            rl_no_collection_failure.setVisibility(View.GONE);
                            mPage++;

                            if (isFirst) {
                                checkMoreData();
                            } else {
                                rv_collection.loadMoreComplete();
                            }

                        } else {
                            rv_collection.noMoreLoading();
                            ptl_refresh.setVisibility(View.GONE);
                            ll_empty.setVisibility(View.VISIBLE);
                            rl_no_collection_failure.setVisibility(View.GONE);
                        }
                        ptl_refresh.refreshComplete();
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        //多了尾布局，因此至少是1
                        if (adapter.getItemCount() <= 1) {
                            rl_no_collection_failure.setVisibility(View.VISIBLE);
                            ptl_refresh.setVisibility(View.GONE);
                            ptl_refresh.refreshComplete();
                        }
                        loading.dismiss();
                    }
                });
    }

    private void checkMoreData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isFirst = false;
                        lastItemPosition = adapter.getItemCount() - 1;
                        LinearLayoutManager layoutManager = (LinearLayoutManager) rv_collection.getLayoutManager();
                        lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();
                        if (lastVisiblePosition >= lastItemPosition - 1) {
                            rv_collection.loadMoreComplete();
                            rv_collection.noMoreLoading();
                        } else {
                            rv_collection.loadMoreComplete();
                        }
                    }
                });
            }
        }, 500);
    }

    private void addListenter() {
        rv_collection.setOnItemClickListener(this);
        ll_empty.setOnClickListener(this);
        ptl_refresh.setOnRefreshListener(this);
        rv_collection.setOnLoadMoreListener(this);
        rv_collection.setOnItemLongClickListener(new OnItemLongClickListener<Collection>() {

            @Override
            public void onItemLongClick(final Collection collection, final int position) {
                mDeleteDialog = new AlertDialog.Builder(mContext)
//                        .setTitle("删除收藏数据")
                        .setMessage("确定删除所选的收藏项？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_collect_delete);
                                networkRequest(CompanionApi.cancelCollects(collection.getType() + "", collection.getId() + ""), new SimpleFastJsonCallback<String>(String.class, loading) {
                                    @Override
                                    public void onSuccess(String url, String result) {
                                        adapter.delete(collection);
                                        if (adapter.getItemCount() == 0) {
                                            isCollection = true;
                                            ll_empty.setVisibility(View.VISIBLE);
                                        }
                                        initData();
                                        loading.dismiss();
                                    }
                                });

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
        btn_no_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtil.isNetworkAvailable(mContext)) {//没有网络连接
                    ToastUtils.showToastShort(mContext, "获取数据失败");
                } else
                    initData();
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
                        if (null != result && result.size() > 0) {
                            mCollection = result;
                            adapter.addAll(result);
                        }
                        rv_collection.loadMoreComplete();
                        checkLoadMoreComplete(result);
                        loading.dismiss();
                    }
                });
    }

    private void checkLoadMoreComplete(ArrayList<Collection> result) {
        if (result == null)
            rv_collection.noMoreLoading();
        else mPage++;
    }


    @Override
    public void onItemClick(Collection collection, int position) {
        YouMengHelper.onEvent(mContext, YouMengHelper.Activity_list_detail);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_COLLECTION, collection);
        YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_collect_detail);
        bundle.putString(BaseWebViewActivity.URL, collection.getLink_url());
        startActivity(ArticleDetailForActivitiesActivity.class, bundle);
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

    @Subcriber(tag = AccountConstants.EventBus.EVENT_DELETE_COMPANION)
    private void refreshCollection(String tag) {
        initData();
    }
}
