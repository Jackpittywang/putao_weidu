package com.putao.wd.home;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;

/**
 * 探索号
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoExploreFragment extends BasicFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
