package com.putao.wd.pt_companion;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * 游戏服务支持
 * Created by zhanghao on 2016/04/05.
 */
public class GameServiceActivity extends PTWDActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_detail_list;
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


