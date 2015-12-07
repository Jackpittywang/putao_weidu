package com.putao.wd.me.address;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.me.address.fragment.ProvinceFragment;
import com.sunnybear.library.controller.BasicFragmentActivity;

/**
 * 城市选择
 * Created by guchenkai on 2015/12/7.
 */
public class CitySelectActivity extends BasicFragmentActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addFragment(ProvinceFragment.class);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
