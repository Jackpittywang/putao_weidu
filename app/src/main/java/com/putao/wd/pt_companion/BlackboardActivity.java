
package com.putao.wd.pt_companion;

import android.os.Bundle;
import com.putao.wd.R;
import com.putao.wd.api.BlackboardApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.CompanionBlackboard;
import com.putao.wd.pt_companion.manage.adapter.BlackboardActivityAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;
import java.io.Serializable;
import java.util.ArrayList;

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

    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<CompanionBlackboard> mCompanionBlackboards = new ArrayList<>();

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        mBlackboardActivityAdapter = new BlackboardActivityAdapter(mContext, null);
        rv_collection.setAdapter(mBlackboardActivityAdapter);
        initData();
        addListener();
    }

    private void addListener() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        rv_collection.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                networkRequest(BlackboardApi.getCreateList(mPage),
                        new SimpleFastJsonCallback<ArrayList<CompanionBlackboard>>(CompanionBlackboard.class, loading) {
                            @Override
                            public void onSuccess(String url, ArrayList<CompanionBlackboard> result) {
                                mBlackboardActivityAdapter.addAll(result);
                                rv_collection.loadMoreComplete();
                                checkLoadMoreComplete(result);
                                loading.dismiss();
                            }
                        });
                rv_collection.noMoreLoading();
            }
        });
        mBlackboardActivityAdapter.setOnItemClickListener(this);
    }

    private void initData() {
        mPage = 1;
        networkRequest(BlackboardApi.getCreateList(mPage),
                new SimpleFastJsonCallback<ArrayList<CompanionBlackboard>>(CompanionBlackboard.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<CompanionBlackboard> result) {
                        mBlackboardActivityAdapter.replaceAll(result);
                        ptl_refresh.refreshComplete();
                        checkLoadMoreComplete(result);
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ptl_refresh.refreshComplete();
                    }
                }, false);
    }

    private void checkLoadMoreComplete(ArrayList<CompanionBlackboard> result) {
        if (null == result || result.size() < 20)
            rv_collection.noMoreLoading();
        else
            mPage++;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onItemClick(Serializable serializable, int position) {
        int type = mCompanionBlackboards.get(position).getType();

        if (type == 1) {
            startActivity(ArticleDetailForActivitiesActivity.class);
        } else {
            startActivity(TopicDetailsActivity.class);
        }
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }
}



