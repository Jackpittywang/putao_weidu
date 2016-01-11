package com.putao.wd.companion.manage;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.view.select.RadioBar;
import com.sunnybear.library.view.select.RadioItem;

import butterknife.Bind;

/**
 * 探索号-每日使用次数
 * Created by wangou on 2015/12/2.
 */
public class UseCountEveryDayFragment extends PTWDFragment implements RadioBar.OnRadioItemSelectListener {
    public static final String EVENT_USECOUNT_EVERYDAY = "usecount_everyday";
    private String useCount = "";

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
        EventBusHelper.post(useCount, EVENT_USECOUNT_EVERYDAY);
        mActivity.finish();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onRadioItemSelect(RadioItem item, int position) {
        switch (item.getId()) {
            case R.id.ri_unlimit:
                useCount = "不限";
                break;
            case R.id.ri_one:
                useCount = "1次";
                break;
            case R.id.ri_three:
                useCount = "3次";
                break;
            case R.id.ri_five:
                useCount = "5次";
                break;
            case R.id.ri_ten:
                useCount = "10次";
                break;
        }
    }
}