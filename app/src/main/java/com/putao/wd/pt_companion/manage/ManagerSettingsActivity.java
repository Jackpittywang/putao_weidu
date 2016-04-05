package com.putao.wd.pt_companion.manage;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragmentActivity;

/**
 * 管理设置
 * Created by guchenkai on 2015/12/11.
 */
public class ManagerSettingsActivity extends BasicFragmentActivity {
    public static final String KEY_MANAGER_SETTINGS = "manager_settings";
    public static final String KEY_MANAGER = "manager";

    public static final String TYPE_SETTING_EQUIPMENT = "setting_equipment";
    public static final String TYPE_SETTING_PRODUCT = "setting_product";
    public static final String TYPE_SETTING_USE_COUNT = "setting_use_count";
    public static final String TYPE_SETTING_USE_TIME = "setting_use_time";

    private String settingType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        settingType = args.getString(KEY_MANAGER_SETTINGS);
        switch (settingType) {
            case TYPE_SETTING_EQUIPMENT://受控设备
                addFragment(ControlledEquipmentFragment.class);
                break;
            case TYPE_SETTING_PRODUCT://受控产品
                addFragment(ControlledProductFragment.class);
                break;
            case TYPE_SETTING_USE_COUNT://每日使用次数
                addFragment(UseCountEveryDayFragment.class);
                break;
            case TYPE_SETTING_USE_TIME://每日使用时间
                addFragment(UseTimeEveryTimeFragment.class);
                break;
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
