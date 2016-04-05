package com.putao.wd.pt_companion.manage;

import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.model.Management;
import com.putao.wd.model.ManagementEdit;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.select.RadioBar;
import com.sunnybear.library.view.select.RadioItem;

import butterknife.Bind;

/**
 * 探索号-每日使用时间
 * Created by wangou on 2015/12/2.
 */
public class UseTimeEveryTimeFragment extends PTWDFragment implements RadioBar.OnRadioItemSelectListener {
    public static final String EVENT_USETIME_EVERYTIME = "usetime_everytime";
    private String useTime = "不限";
    private int mTime;

    @Bind(R.id.rb_select)
    RadioBar rb_select;
    @Bind(R.id.ri_unlimit)
    RadioItem ri_unlimit;
    @Bind(R.id.ri_1)
    RadioItem ri_1;
    @Bind(R.id.ri_2)
    RadioItem ri_2;
    @Bind(R.id.ri_3)
    RadioItem ri_3;
    @Bind(R.id.ri_4)
    RadioItem ri_4;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_use_time_every_time;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        rb_select.setOnRadioItemSelectListener(this);
        networkRequest(ExploreApi.getManagement(), new SimpleFastJsonCallback<Management>(Management.class, loading) {
            @Override
            public void onSuccess(String url, Management result) {
                useTime = result.getUse_time();
                loading.dismiss();
                switch (result.getUse_time()) {
                    case 15 + "":
                        rb_select.selectRadioItem(ri_1);
                        break;
                    case 30 + "":
                        rb_select.selectRadioItem(ri_2);
                        break;
                    case 45 + "":
                        rb_select.selectRadioItem(ri_3);
                        break;
                    case 60 + "":
                        rb_select.selectRadioItem(ri_4);
                        break;
                    default:
                        rb_select.selectRadioItem(ri_unlimit);
                        break;

                }
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onRightAction() {
        Management management = new Management();
        management.setUse_time(mTime + "");
        EventBusHelper.post(useTime, EVENT_USETIME_EVERYTIME);
        mActivity.finish();
        networkRequest(ExploreApi.managementEdit(JSONObject.toJSONString(management)), new SimpleFastJsonCallback<ManagementEdit>(ManagementEdit.class, loading) {
            @Override
            public void onSuccess(String url, ManagementEdit result) {
                Logger.i("探索号管理信息保存成功");
            }
        });
    }

    @Override
    public void onRadioItemSelect(RadioItem item, int position) {
        switch (item.getId()) {
            case R.id.ri_unlimit:
                mTime = 0;
                useTime = "不限";
                break;
            case R.id.ri_1:
                mTime = 15;
                break;
            case R.id.ri_2:
                mTime = 30;
                break;
            case R.id.ri_3:
                mTime = 45;
                break;
            case R.id.ri_4:
                mTime = 60;
                break;
        }
        if (R.id.ri_unlimit != item.getId())
            useTime = mTime + "分钟";
    }
}