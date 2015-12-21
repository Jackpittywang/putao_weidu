package com.putao.wd.explore.manage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Management;
import com.putao.wd.model.ManagementEdit;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索号-管理
 * Created by wangou on 2015/12/2.
 */
public class ManageActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.btn_stopuse)
    Button btn_stopuse;
    @Bind(R.id.tv_equipment_name)
    TextView tv_equipment_name;
    @Bind(R.id.iv_equipment_name)
    ImageView iv_equipment_name;
    @Bind(R.id.ll_equipment)
    LinearLayout ll_equipment;
    @Bind(R.id.tv_product_name)
    TextView tv_product_name;
    @Bind(R.id.iv_product_name)
    ImageView iv_product_name;
    @Bind(R.id.ll_product)
    LinearLayout ll_product;
    @Bind(R.id.tv_usecount_byday)
    TextView tv_usecount_byday;
    @Bind(R.id.iv_usecount_byday)
    ImageView iv_usecount_byday;
    @Bind(R.id.ll_usecount)
    LinearLayout ll_usecount;
    @Bind(R.id.tv_usetime_byday)
    TextView tv_usetime_byday;
    @Bind(R.id.iv_usetime_byday)
    ImageView iv_usetime_byday;
    @Bind(R.id.usetime)
    LinearLayout usetime;

    private Bundle bundle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        bundle = new Bundle();
        networkRequest(ExploreApi.getManagement(), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                Logger.i("探索号管理查询请求成功");
            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                super.onFailure(url, statusCode, msg);
                ToastUtils.showToastShort(mContext, msg);
            }
        });

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.btn_stopuse, R.id.ll_equipment, R.id.ll_product, R.id.ll_usecount, R.id.usetime})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stopuse://停止使用
                new AlertDialog.Builder(mContext).setTitle("").setMessage("让宝宝停止使用葡萄产品?")
                        .setCancelable(false)
                        .setPositiveButton("停止", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("再玩玩", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
                break;
            case R.id.ll_equipment://受控设备
                bundle.putString(ManagerSettingsActivity.KEY_MANAGER_SETTINGS, ManagerSettingsActivity.TYPE_SETTING_EQUIPMENT);
                startActivity(ManagerSettingsActivity.class, bundle);
                break;
            case R.id.ll_product://受控产品
                bundle.putString(ManagerSettingsActivity.KEY_MANAGER_SETTINGS, ManagerSettingsActivity.TYPE_SETTING_PRODUCT);
                startActivity(ManagerSettingsActivity.class, bundle);
                break;
            case R.id.ll_usecount://使用次数
                bundle.putString(ManagerSettingsActivity.KEY_MANAGER_SETTINGS, ManagerSettingsActivity.TYPE_SETTING_USE_COUNT);
                startActivity(ManagerSettingsActivity.class, bundle);
                break;
            case R.id.usetime://使用时间
                bundle.putString(ManagerSettingsActivity.KEY_MANAGER_SETTINGS, ManagerSettingsActivity.TYPE_SETTING_USE_TIME);
                startActivity(ManagerSettingsActivity.class, bundle);
                break;
        }
    }

    @Subcriber(tag = UseCountEveryDayFragment.EVENT_USECOUNT_EVERYDAY)
    public void eventUseCount(String useCount) {
        if (StringUtils.isEmpty(useCount))
            tv_usecount_byday.setText("不限");
        tv_usecount_byday.setText(useCount);
    }

    @Subcriber(tag = UseTimeEveryTimeFragment.EVENT_USETIME_EVERYTIME)
    public void eventUseTime(String useTime) {
        if (StringUtils.isEmpty(useTime))
            tv_usetime_byday.setText("不限");
        tv_usetime_byday.setText(useTime);
    }
}