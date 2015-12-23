package com.putao.wd.explore.manage;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.model.ManagementEdit;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.select.RadioBar;
import com.sunnybear.library.view.select.RadioItem;

import butterknife.Bind;

/**
 * 探索号-每日使用时间
 * Created by wangou on 2015/12/2.
 */
public class UseTimeEveryTimeFragment extends PTWDFragment implements RadioBar.OnRadioItemSelectListener {
    public static final String EVENT_USETIME_EVERYTIME = "usetime_everytime";
    private String useTime = "";

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
        //TODO
        networkRequest(ExploreApi.managementEdit(""), new SimpleFastJsonCallback<ManagementEdit>(ManagementEdit.class, loading) {
            @Override
            public void onSuccess(String url, ManagementEdit result) {
                EventBusHelper.post(useTime, EVENT_USETIME_EVERYTIME);
                ActivityManager.getInstance().finishCurrentActivity();
            }
        });


    }

    @Override
    public void onRadioItemSelect(RadioItem item, int position) {
        switch (item.getId()) {
            case R.id.ri_unlimit:
                useTime = "不限";
                break;
            case R.id.ri_five:
                useTime = "5分钟";
                break;
            case R.id.ri_ten:
                useTime = "10分钟";
                break;
            case R.id.ri_twenty:
                useTime = "20分钟";
                break;
            case R.id.ri_thirty:
                useTime = "30分钟";
                break;
        }
    }

}