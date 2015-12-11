package com.putao.wd.explore.manage;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.view.select.RadioBar;
import com.sunnybear.library.view.select.RadioItem;

import butterknife.Bind;

/**
 * 探索号-每日使用时间
 * Created by wangou on 2015/12/2.
 */
public class UseTimeEveryTimeFragment extends PTWDFragment implements RadioBar.OnRadioItemSelectListener {
    public static final String EVENT_USETIME_EVERYTIME = "usetime_everytime";
    private String usetime = "";

    @Bind(R.id.rb_select)
    RadioBar rb_select;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_use_time_every_time;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        rb_select.setOnRadioItemSelectListener(this);

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onRightAction() {
        EventBusHelper.post(usetime, EVENT_USETIME_EVERYTIME);
    }

    @Override
    public void onRadioItemSelect(RadioItem item, int position) {
        switch (position) {
            case 0:
                usetime = "不限";
                break;
            case 1:
                usetime = "5分钟";
                break;
            case 2:
                usetime = "10分钟";
                break;
            case 3:
                usetime = "20分钟";
                break;
            case 4:
                usetime = "30分钟";
                break;
        }
    }

}