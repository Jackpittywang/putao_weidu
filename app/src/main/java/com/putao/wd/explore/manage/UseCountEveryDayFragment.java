package com.putao.wd.explore.manage;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.sunnybear.library.view.select.RadioBar;
import com.sunnybear.library.view.select.RadioItem;

import butterknife.Bind;

/**
 * 探索号-每日使用次数
 * Created by wangou on 2015/12/2.
 */
public class UseCountEveryDayFragment extends PTWDFragment implements RadioBar.OnRadioItemSelectListener {
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
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onRadioItemSelect(RadioItem item, int position) {

    }
}