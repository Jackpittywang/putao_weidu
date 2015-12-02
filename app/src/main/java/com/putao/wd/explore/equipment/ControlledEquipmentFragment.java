package com.putao.wd.explore.equipment;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;

/**
 * 探索号-受控设备
 * Created by wangou on 2015/12/2.
 */
public class ControlledEquipmentFragment  extends BasicFragment implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_controlled_equipment;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}