package com.putao.wd.pt_companion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.api.BlackboardApi;
import com.putao.wd.api.CreateApi;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.home.adapter.CompanionAdapter;
import com.putao.wd.model.Companion;
import com.putao.wd.model.CompanionBlackboard;
import com.putao.wd.model.CompanionBlackboards;
import com.putao.wd.model.Creates;
import com.putao.wd.model.Diarys;
import com.putao.wd.pt_companion.manage.adapter.BlackboardActivityAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.NavigationBar;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 葡萄黑板报
 * Created by zhanghao on 2016/04/05.
 */
public class BlackboardActivity extends PTWDActivity implements OnItemClickListener {
    private static int mPage = 1;

    private BlackboardActivityAdapter mBlackboardActivityAdapter;
    @Bind(R.id.rv_collection)
    LoadMoreRecyclerView rv_collection;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_companion_blackboard;
    }

    private List<String> list = new ArrayList<>();
    ArrayList<CompanionBlackboard> mCompanionBlackboards = new ArrayList<>();

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();

        mCompanionBlackboards.add(new CompanionBlackboard("话题", "1234123134", "0", "话题话题", "聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊", 200, 1, 1, "1月12日-12月12日"));
        mCompanionBlackboards.add(new CompanionBlackboard("文章", "1234123134", "0", "话题话题", "聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊聊", 300, 1, 1, "1月12日-12月12日"));
        mCompanionBlackboards.add(new CompanionBlackboard("创意", "2234123134", "0", "话题话题", "聊聊聊聊聊聊聊聊聊", 533, 1, 1, "1月12日-12月12日"));
        mCompanionBlackboards.add(new CompanionBlackboard("活动", "5323231311", "0", "话题话题", "聊聊聊聊聊聊聊聊聊", 900, 1, 1, "1月12日-12月12日"));

        //遍历集合
        for (int position = 0; position < mCompanionBlackboards.size(); position++) {
            CompanionBlackboard blackboard = mCompanionBlackboards.get(position);
            if (!list.contains(blackboard.getDate())) {
                blackboard.setShowData(true);
                list.add(blackboard.getDate());
            } else {
                blackboard.setShowData(false);
            }
        }

        mBlackboardActivityAdapter = new BlackboardActivityAdapter(mContext, mCompanionBlackboards);
        rv_collection.setAdapter(mBlackboardActivityAdapter);
        addListener();
    }

    private void addListener() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ptl_refresh.refreshComplete();
               // getDiaryIndex();
            }
        });
        rv_collection.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                networkRequest(BlackboardApi.getDiaryData(mPage),
                        new SimpleFastJsonCallback<CompanionBlackboards>(CompanionBlackboards.class, loading) {
                            @Override
                            public void onSuccess(String url, CompanionBlackboards result) {
                                if (result != null && result.getData().size() > 0)
                                    mBlackboardActivityAdapter.addAll(result.getData());
                                rv_collection.loadMoreComplete();
                                checkLoadMoreComplete(result.getCurrent_page(), result.getTotal_page());
                                loading.dismiss();
                            }
                        });
                rv_collection.noMoreLoading();
            }
        });
       // mBlackboardActivityAdapter.setOnItemClickListener(this);
    }


    private void getDiaryIndex() {
        mPage = 1;
        networkRequest(BlackboardApi.getDiaryData(mPage),
                new SimpleFastJsonCallback<CompanionBlackboards>(Diarys.class, loading) {
                    @Override
                    public void onSuccess(String url, CompanionBlackboards result) {
                        if (result != null && result.getData().size() > 0) {
                            mBlackboardActivityAdapter.replaceAll(result.getData());
                            ptl_refresh.setVisibility(View.VISIBLE);
                        } else {
                            ptl_refresh.setVisibility(View.GONE);
                        }
                        ptl_refresh.refreshComplete();
                        checkLoadMoreComplete(result.getCurrent_page(), result.getTotal_page());
                        loading.dismiss();
                    }
                });
    }


    private boolean hasMoreData;

    private void checkLoadMoreComplete(int currentPage, int totalPage) {
        if (currentPage == totalPage) {
            hasMoreData = false;
            rv_collection.noMoreLoading();
        } else {
            hasMoreData = true;
            mPage++;
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onItemClick(Serializable serializable, int position) {
        String response = mCompanionBlackboards.get(position).getResponse();

        if(TextUtils.equals(response,"文章")){
            startActivity(new Intent(this,ArticleDetailsActivity.class));
        }else{
            startActivity(new Intent(this,TopicDetailsActivity.class));
        }
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }

//    private void initData() {
//        mPage = 1;
//        networkRequest(CreateApi.getCreateList(1, mPage),
//                new SimpleFastJsonCallback<CompanionBlackboard>(Creates.class, loading) {
//                    @Override
//                    public void onSuccess(String url, Creates result) {
//                        mBlackboardActivityAdapter.replaceAll(result.getData());
//                        ptl_refresh.refreshComplete();
//                        checkLoadMoreComplete(result.getCurrentPage(), result.getTotalPage());
//                        mLoading.dismiss();
//                        loading.dismiss();
//                    }
//
//                    @Override
//                    public void onFailure(String url, int statusCode, String msg) {
//                        super.onFailure(url, statusCode, msg);
//                        ptl_refresh.refreshComplete();
//                    }
//                }, false);
//    }
//
//    private void checkLoadMoreComplete(int currentPage, int totalPage) {
//        if (currentPage == totalPage) {
//            hasMoreData = false;
//            rv_created.noMoreLoading();
//        } else {
//            hasMoreData = true;
//            mPage++;
//        }
//    }
}


