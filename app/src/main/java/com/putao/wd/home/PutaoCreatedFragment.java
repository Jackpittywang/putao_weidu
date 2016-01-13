package com.putao.wd.home;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.util.Logger;

/**
 * 创造(首页)
 * Created by guchenkai on 2016/1/13.
 */
public class PutaoCreatedFragment extends BasicFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_created;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        Logger.d("PutaoCreatedFragment启动");
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
