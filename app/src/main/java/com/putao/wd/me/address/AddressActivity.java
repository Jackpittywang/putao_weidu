package com.putao.wd.me.address;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragmentActivity;

/**
 * 收货人地址
 * Created by guchenkai on 2015/11/26.
 */
public class AddressActivity extends BasicFragmentActivity {
    public static final String KEY_IS_ADD = "isAdd";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addFragment(AddressListFragment.class);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
