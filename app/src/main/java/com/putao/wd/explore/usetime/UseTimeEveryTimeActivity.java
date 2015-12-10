package com.putao.wd.explore.usetime;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.UseTimeItem;
import com.putao.wd.explore.usetime.adapter.UseTimeAdapter;
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
        return R.layout.activity_use_time_every_time;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        if(initDataTest().size()!=0) {
            UseTimeAdapter userTimeAdapter = new UseTimeAdapter(mContext, initDataTest());
            brv_usertime_everytime.setAdapter(userTimeAdapter);
        }
    }


    private List<UseTimeItem> initDataTest() {
        UseTimeItem msgitem;
        msgitem=new UseTimeItem();
        msgitem.setName("不限");
        List<UseTimeItem> list=new ArrayList<>();
        list.add(msgitem);
        msgitem=new UseTimeItem();
        msgitem.setName("5" + "分钟");
        list.add(msgitem);
        msgitem=new UseTimeItem();
        msgitem.setName("10"+"分钟");
        list.add(msgitem);
        msgitem=new UseTimeItem();
        msgitem.setName("20"+"分钟");
        list.add(msgitem);
        msgitem=new UseTimeItem();
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