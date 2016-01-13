package com.putao.wd.home;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.util.Logger;

/**
 * 陪伴
 * Created by guchenkai on 2016/1/13.
 */
public class PutaoCompanionFragment extends BasicFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_companion;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        Logger.d("PutaoCompanionFragment启动");
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
