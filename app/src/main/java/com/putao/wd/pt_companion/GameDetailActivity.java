package com.putao.wd.pt_companion;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.GameList;
import com.putao.wd.pt_companion.adapter.GameDetailAdapter;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 游戏详情页
 * Created by zhanghao on 2016/04/05.
 */
public class GameDetailActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    private GameDetailAdapter mGameDetailAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        ArrayList<GameList> gameLists = new ArrayList();
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        mGameDetailAdapter = new GameDetailAdapter(mContext, gameLists);
        rv_content.setAdapter(mGameDetailAdapter);
        addListener();
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
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.tv_game_step, R.id.tv_game_service})
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
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }
}


