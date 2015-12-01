package com.putao.wd.user;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;


public class ProtocolFragment extends BasicFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_protocol;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}