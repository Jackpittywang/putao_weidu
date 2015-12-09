package com.putao.wd.explore.usetime;

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
 * 探索号-每日使用时间
 * Created by wangou on 2015/12/2.
 */
public class UseTimeEveryTimeActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.brv_usertime_everytime)
    BasicRecyclerView brv_usertime_everytime;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_use_time_every_time;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        if(initDataTest().size()!=0) {
            ControlledEquipmentAdatper controlledEquipmentAdatper = new ControlledEquipmentAdatper(mContext, initDataTest());
            brv_usertime_everytime.setAdapter(controlledEquipmentAdatper);
        }
    }


    private List<EquipmentItem> initDataTest() {
        EquipmentItem msgitem;
        msgitem=new EquipmentItem();
        msgitem.setName("不限");
        List<EquipmentItem> list=new ArrayList<>();
        list.add(msgitem);
        msgitem=new EquipmentItem();
        msgitem.setName("5" + "分钟");
        list.add(msgitem);
        msgitem=new EquipmentItem();
        msgitem.setName("10"+"分钟");
        list.add(msgitem);
        msgitem=new EquipmentItem();
        msgitem.setName("20"+"分钟");
        list.add(msgitem);
        msgitem=new EquipmentItem();
        msgitem.setName("30"+"分钟");
        list.add(msgitem);
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