package com.putao.wd.explore.equipment;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.EquipmentItem;
import com.putao.wd.explore.equipment.adapter.ControlledEquipmentAdatper;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 探索号-受控设备
 * Created by wangou on 2015/12/2.
 */
public class ControlledEquipmentActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.brv_equipment)
    BasicRecyclerView brv_equipment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_controlled_equipment;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        if(initDataTest().size()!=0) {
            ControlledEquipmentAdatper controlledEquipmentAdatper = new ControlledEquipmentAdatper(mContext, initDataTest());
            brv_equipment.setAdapter(controlledEquipmentAdatper);
        }
    }


    private List<EquipmentItem> initDataTest() {
        List<EquipmentItem> list=new ArrayList<>();
        for(int i=1;i<=3;i++) {
            EquipmentItem msgitem=new EquipmentItem();
            msgitem.setName("设备名称"+i);
            list.add(msgitem);
        }
        return list;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}