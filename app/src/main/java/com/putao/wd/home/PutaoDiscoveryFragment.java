package com.putao.wd.home;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;

/**
 * Created by Administrator on 2016/4/5.
 */
public class PutaoDiscoveryFragment extends PTWDFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
