package com.putao.wd.explore.manage;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.view.select.RadioBar;
import com.sunnybear.library.view.select.RadioItem;

import butterknife.Bind;

/**
 * 探索号-每日使用次数
 * Created by wangou on 2015/12/2.
 */
public class UseCountEveryDayFragment extends PTWDFragment implements RadioBar.OnRadioItemSelectListener {
    public static final String EVENT_USECOUNT_EVERYDAY = "usecount_everyday";
    private String usecount="";

    @Bind(R.id.rb_select)
    RadioBar rb_select;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_use_count_every_day;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        rb_select.setOnRadioItemSelectListener(this);
    }

    @Override
    public void onRightAction() {
        EventBusHelper.post(usecount, EVENT_USECOUNT_EVERYDAY);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onRadioItemSelect(RadioItem item, int position) {
        switch (position){
            case 0:usecount="不限";break;
            case 1:usecount="1次";break;
            case 2:usecount="3次";break;
            case 3:usecount="5次";break;
            case 4:usecount="10次";break;
        }
    }
}