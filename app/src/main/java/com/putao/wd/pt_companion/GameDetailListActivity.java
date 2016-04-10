package com.putao.wd.pt_companion;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.CompanionCampaign;
import com.putao.wd.model.GameList;
import com.putao.wd.pt_companion.adapter.GameDetailAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 游戏详情页
 * Created by zhanghao on 2016/04/05.
 */
public class GameDetailListActivity extends PTWDActivity {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    private boolean isLoadMore = false;
    private GameDetailAdapter mGameDetailAdapter;
    private int mPosition;
    private int mPage;
    private ArrayList<String> list = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_detail_list;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        mPosition = args.getInt("position");
        ArrayList<GameList> gameLists = new ArrayList();
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        mGameDetailAdapter = new GameDetailAdapter(mContext, gameLists);
        rv_content.setAdapter(mGameDetailAdapter);
        initData();
        addListener();
    }

    /**
     * 下拉刷新 以及 最初的初始化
     */
    private void initData() {
        mPage = 1;
        networkRequest(CompanionApi.getCompanyServiceMenus(""),
                new SimpleFastJsonCallback<ArrayList<CompanionCampaign>>(CompanionCampaign.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<CompanionCampaign> result) {

                        isLoadMore = false;
                        ArrayList<CompanionCampaign> newResult = setIsSameDate(result);
//                        rv_content.replaceAll(newResult);
                        ptl_refresh.refreshComplete();
                        checkLoadMoreComplete(newResult);
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ptl_refresh.refreshComplete();
                    }
                }, false);
    }

    /**
     * 设置是否为同一天
     *
     * @param result
     * @return
     */
    private ArrayList<CompanionCampaign> setIsSameDate(ArrayList<CompanionCampaign> result) {

        // 如果是 下拉刷新或重新进入则将 list（存用户的临时集合） 集合清空
        if (!isLoadMore) {
            list.clear();
        }

        for (int position = 0; position < result.size(); position++) {
            CompanionCampaign blackboard = result.get(position);

            if (!list.contains(getTimeDate(blackboard))) {
                blackboard.setShowDate(true);
                list.add(getTimeDate(blackboard));
            } else {
                blackboard.setShowDate(false);
            }
        }
        return result;
    }

    /**
     * 取将时间装换为天数
     */
    private String getTimeDate(CompanionCampaign blackboard) {
        long time = Integer.valueOf(blackboard.getTime()) * 1000L;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.format(time);
    }

    private void checkLoadMoreComplete(ArrayList<CompanionCampaign> result) {
        if (null == result)
            rv_content.noMoreLoading();
        else
            mPage++;
    }

    private void addListener() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ptl_refresh.refreshComplete();
            }
        });
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rv_content.loadMoreComplete();
                rv_content.noMoreLoading();
            }
        });
        rv_content.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Serializable serializable, int position) {
                if (0 == mPosition)
                    startActivity(ArticleDetailForActivitiesActivity.class);
                else
                    startActivity(GameDetailActivity.class);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

  /*  @OnClick({R.id.tv_game_step, R.id.tv_game_service})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_game_step:
                startActivity(GameStepListActivity.class);
                break;
            case R.id.tv_game_service:
                startActivity(GameServiceActivity.class);
                break;
        }
    }*/

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        startActivity(OfficialAccountsActivity.class);
    }
}


