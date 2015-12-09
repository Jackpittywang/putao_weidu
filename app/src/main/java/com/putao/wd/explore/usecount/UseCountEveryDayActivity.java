package com.putao.wd.explore.usecount;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.UseCountItem;
import com.putao.wd.explore.equipment.adapter.ControlledEquipmentAdatper;
import com.putao.wd.explore.usecount.adapter.UseCountAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 探索号-每日使用次数
 * Created by wangou on 2015/12/2.
 */
public class UseCountEveryDayActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.brv_usertime_everyday)
    BasicRecyclerView brv_usertime_everyday;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_use_count_every_day;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        if(initDataTest().size()!=0) {
            UseCountAdapter useCountAdapter = new UseCountAdapter(mContext, initDataTest());
            brv_usertime_everyday.setAdapter(useCountAdapter);
        }
    }

//    @Override
//    public void onViewCreatedFinish(Bundle savedInstanceState) {
//        addNavigation();
//        if(initDataTest().size()!=0) {
//            ControlledEquipmentAdatper controlledEquipmentAdatper = new ControlledEquipmentAdatper(mActivity, initDataTest());
//            brv_usertime_everyday.setAdapter(controlledEquipmentAdatper);
//        }
//    }

    private List<UseCountItem> initDataTest() {
        UseCountItem msgitem;
        msgitem=new UseCountItem();
        msgitem.setName("不限");
        List<UseCountItem> list=new ArrayList<>();
        list.add(msgitem);
        msgitem=new UseCountItem();
        msgitem.setName("1" + "次");
        list.add(msgitem);
        msgitem=new UseCountItem();
        msgitem.setName("3"+"次");
        list.add(msgitem);
        msgitem=new UseCountItem();
        msgitem.setName("5"+"次");
        list.add(msgitem);
        msgitem=new UseCountItem();
        msgitem.setName("10"+"次");
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