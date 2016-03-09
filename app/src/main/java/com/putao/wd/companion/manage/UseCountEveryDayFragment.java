package com.putao.wd.companion.manage;

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
 * 探索号-每日使用次数
 * Created by wangou on 2015/12/2.
 */
public class UseCountEveryDayFragment extends PTWDFragment implements RadioBar.OnRadioItemSelectListener {
    public static final String EVENT_USECOUNT_EVERYDAY = "usecount_everyday";
    private String useCount = "不限";
    private int mTime;

    @Bind(R.id.rb_select)
    RadioBar rb_select;
    @Bind(R.id.ri_one)
    RadioItem ri_one;
    @Bind(R.id.ri_three)
    RadioItem ri_three;
    @Bind(R.id.ri_five)
    RadioItem ri_five;
    @Bind(R.id.ri_ten)
    RadioItem ri_ten;
    @Bind(R.id.ri_unlimit)
    RadioItem ri_unlimit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_use_count_every_day;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        rb_select.setOnRadioItemSelectListener(this);
        networkRequest(ExploreApi.getManagement(), new SimpleFastJsonCallback<Management>(Management.class, loading) {
            @Override
            public void onSuccess(String url, Management result) {
                useCount = result.getUse_num();
                switch (result.getUse_num()) {
                    case 1 + "":
                        rb_select.selectRadioItem(ri_one);
                        break;
                    case 3 + "":
                        rb_select.selectRadioItem(ri_three);
                        break;
                    case 5 + "":
                        rb_select.selectRadioItem(ri_five);
                        break;
                    case 10 + "":
                        rb_select.selectRadioItem(ri_ten);
                        break;
                    default:
                        rb_select.selectRadioItem(ri_unlimit);
                        break;

                }
                loading.dismiss();
            }
        });
    }

    @Override
    public void onRightAction() {
        Management management = new Management();
        management.setUse_num(mTime + "");
        EventBusHelper.post(useCount, EVENT_USECOUNT_EVERYDAY);
        mActivity.finish();
        networkRequest(ExploreApi.managementEdit(JSONObject.toJSONString(management)), new SimpleFastJsonCallback<ManagementEdit>(ManagementEdit.class, loading) {
            @Override
            public void onSuccess(String url, ManagementEdit result) {
                Logger.i("探索号管理信息保存成功");
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onRadioItemSelect(RadioItem item, int position) {
        switch (item.getId()) {
            case R.id.ri_unlimit:
                mTime = 0;
                useCount = "不限";
                break;
            case R.id.ri_one:
                mTime = 1;
                break;
            case R.id.ri_three:
                mTime = 3;
                break;
            case R.id.ri_five:
                mTime = 5;
                break;
            case R.id.ri_ten:
                mTime = 10;
                break;
        }
        if (R.id.ri_unlimit != item.getId())
            useCount = mTime + "次";
    }
}