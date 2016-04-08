package com.putao.wd.pt_me.participation;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.CreateApi;
import com.putao.wd.api.ParticipationApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.created.CreateBasicDetailActivity;
import com.putao.wd.created.CreateCommentActivity;
import com.putao.wd.created.CreateDetailActivity;
import com.putao.wd.created.FancyFragment;
import com.putao.wd.home.PutaoCreatedSecondFragment;
import com.putao.wd.home.adapter.DiscoveryAdapter;
import com.putao.wd.model.Create;
import com.putao.wd.model.Creates;
import com.putao.wd.model.Participation;
import com.putao.wd.pt_me.attention.ConcernsDetailActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.animators.ScaleInAnimation;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 我的参与
 * Created by Administrator on 2016/4/5.
 */
public class ParticipationActivity extends PTWDActivity implements PullToRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadMoreListener, OnItemClickListener<Participation>, View.OnClickListener {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_participation)
    LoadMoreRecyclerView rv_participation;
    @Bind(R.id.ll_empty)
    LinearLayout ll_empty;

    private ParticipationAdapter adapter;
    private boolean hasMoreData;
    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_participation;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        adapter = new ParticipationAdapter(mContext, null);
        rv_participation.setAdapter(adapter);
        initData();
        addListenter();
    }

    private void initData() {
        mPage = 1;
        networkRequest(ParticipationApi.getQueryPart(mPage),
                new SimpleFastJsonCallback<ArrayList<Participation>>(Participation.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Participation> result) {
                        if (result != null && result.size() > 0) {
                            ll_empty.setVisibility(View.GONE);
                            ptl_refresh.setVisibility(View.VISIBLE);
                            adapter.replaceAll(result);
                        } else {
                            ptl_refresh.setVisibility(View.GONE);
                            ll_empty.setVisibility(View.VISIBLE);
                        }
                        checkLoadMoreComplete(result);
                        ptl_refresh.refreshComplete();
                        loading.dismiss();
                    }
                });
    }

    private void checkLoadMoreComplete(ArrayList<Participation> result) {
        if (null == result) {
            rv_participation.noMoreLoading();
        } else {
            mPage++;
        }
    }

    private void addListenter() {
        rv_participation.setOnItemClickListener(this);
        ll_empty.setOnClickListener(this);
        ptl_refresh.setOnRefreshListener(this);
        rv_participation.setOnLoadMoreListener(this);
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
        networkRequest(ParticipationApi.getQueryPart(mPage),
                new SimpleFastJsonCallback<ArrayList<Participation>>(Participation.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Participation> result) {
                        adapter.addAll(result);
                        rv_participation.loadMoreComplete();
                        checkLoadMoreComplete(result);
                        loading.dismiss();
                    }
                });
    }

    @Override
    public void onItemClick(Participation participation, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ParticipationDetailActivity.CREATE, (Serializable) adapter.getItems());
        bundle.putInt(ParticipationDetailActivity.POSITION, position);
        bundle.putInt(ParticipationDetailActivity.PAGE_COUNT, mPage);
//        bundle.putBoolean(CreateDetailActivity.HAS_MORE_DATA, participation);
        YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_interested_detail);
        startActivity(ParticipationDetailActivity.class, bundle);
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

