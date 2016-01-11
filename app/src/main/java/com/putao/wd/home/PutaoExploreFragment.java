package com.putao.wd.home;

import android.os.Bundle;

import com.putao.wd.base.PTWDFragment;

/**
 * 探索(首页)
 * Created by guchenkai on 2016/1/11.
 */
public class PutaoExploreFragment extends PTWDFragment {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
