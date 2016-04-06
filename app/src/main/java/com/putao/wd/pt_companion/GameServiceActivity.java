package com.putao.wd.pt_companion;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.GameList;
import com.putao.wd.pt_companion.adapter.GameDetailAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 游戏服务支持
 * Created by zhanghao on 2016/04/05.
 */
public class GameServiceActivity extends PTWDActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_step_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        setMainTitle("服务支持");
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
}


