package com.putao.wd.pt_companion;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.GameList;
import com.putao.wd.pt_companion.adapter.GameStepListAdapter;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * 游戏步骤选择页
 * Created by zhanghao on 2016/04/05.
 */
public class GameStepListActivity extends PTWDActivity {
    private GameStepListAdapter mGameStepListAdapter;
    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_step_list;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        ArrayList<GameList> gameLists = new ArrayList<>();
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        gameLists.add(new GameList());
        mGameStepListAdapter = new GameStepListAdapter(mContext, gameLists);
        rv_content.setAdapter(mGameStepListAdapter);
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Subcriber(tag = GameStepListAdapter.EVENT_START_TO_DETAIL)
    private void startDetail(String str) {
        startActivity(GameDetailActivity.class);
    }
}


