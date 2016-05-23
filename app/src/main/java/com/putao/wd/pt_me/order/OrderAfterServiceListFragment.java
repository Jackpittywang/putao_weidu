package com.putao.wd.pt_me.order;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;

/**
 * Created by riven_chris on 16/5/23.
 */
public class OrderAfterServiceListFragment extends BasicFragment {

    public static OrderAfterServiceListFragment newInstance() {
        OrderAfterServiceListFragment fragment = new OrderAfterServiceListFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_after_service_list;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
