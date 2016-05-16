package com.putao.wd.pt_companion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.putao.ptx.push.core.NetworkUtil;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.SubscribeList;
import com.putao.wd.pt_companion.adapter.SubscriptionNumberAdapter;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * 订阅号列表
 * Created by Administrator on 2016/5/5.
 */
public class SubscriptionNumberActivity extends PTWDActivity implements OnItemClickListener<SubscribeList> {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.rl_no_Subscribe)
    RelativeLayout rl_no_Subscribe;
    @Bind(R.id.rl_subscribe_failure)
    RelativeLayout rl_subscribe_failure;
    @Bind(R.id.btn_no_data)
    Button btn_no_data;

    private SubscriptionNumberAdapter adapter;
    protected ArrayList<SubscribeList> subscribeLists;
    private int mPage = 1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_companion_subribe_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        adapter = new SubscriptionNumberAdapter(mContext, null);
        rv_content.setAdapter(adapter);

        addListener();
        getSubscribeList();
    }

    //点击事件
    private void addListener() {
        adapter.setOnItemClickListener(this);

        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                networkRequest(CompanionApi.getSubscribeList(String.valueOf(mPage)), new SimpleFastJsonCallback<ArrayList<SubscribeList>>(SubscribeList.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<SubscribeList> result) {
                        if (result != null && result.size() > 0) {
                            subscribeLists = result;
                            adapter.addAll(result);
                        }
                        rv_content.loadMoreComplete();
                        checkLoadMoreComplete(result);
                        loading.dismiss();
                    }
                });
            }
        });

        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSubscribeList();
            }
        });

        btn_no_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtil.isNetworkAvailable(mContext))
                    ToastUtils.showToastShort(mContext, "获取数据失败");
                else
                    getSubscribeList();
            }
        });
    }


    private void getSubscribeList() {
        mPage = 1;
        networkRequest(CompanionApi.getSubscribeList(String.valueOf(mPage)),
                new SimpleFastJsonCallback<ArrayList<SubscribeList>>(SubscribeList.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<SubscribeList> result) {
                        if (result != null && result.size() > 0) {
                            subscribeLists = result;
                            adapter.replaceAll(result);
                            mPage++;
                            rv_content.loadMoreComplete();
                            ptl_refresh.setVisibility(View.VISIBLE);
                            rl_no_Subscribe.setVisibility(View.GONE);
                        } else {
                            rl_no_Subscribe.setVisibility(View.VISIBLE);
                            ptl_refresh.setVisibility(View.GONE);
                        }

                        checkLoadMoreComplete(result);
                        ptl_refresh.refreshComplete();
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        //多了尾布局，因此至少是1
                        if (adapter.getItemCount() <= 1) {
                            rl_subscribe_failure.setVisibility(View.VISIBLE);
                            ptl_refresh.setVisibility(View.GONE);
                            ptl_refresh.refreshComplete();
                        }
                    }
                }

        );
    }

    private void checkLoadMoreComplete(ArrayList<SubscribeList> result) {
        if (result == null)
            rv_content.noMoreLoading();
        else mPage++;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }

    @Override
    public void onItemClick(SubscribeList subscribeList, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_BIND, true);
        bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, subscribeList);
        startActivity(OfficialAccountsActivity.class, bundle);
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_COMPANION)
    private void onSubscribe(String tag) {
        getSubscribeList();
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_COMPANION)
    private void refresh_subscribe(String tag) {
        getSubscribeList();
    }
}
