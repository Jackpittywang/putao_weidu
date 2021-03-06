package com.putao.wd.pt_companion.manage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Management;
import com.putao.wd.model.ManagementDevice;
import com.putao.wd.model.ManagementProduct;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ToastUtils;


import java.util.List;

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
    @Bind(R.id.ll_empty)
    LinearLayout ll_empty;
    @Bind(R.id.ll_content)
    LinearLayout ll_content;
    @Bind(R.id.tv_empty)
    TextView tv_empty;

    private Bundle bundle;
    private Management management;
    private int deviceNum;
    private int productNum;
    private CountDownTimer mTimer;

    private final static String STOP_PLAY = "stop_play";
    private final static long STOP_TIME = 5 * 60 * 1000;
    private long mTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        loading.show();
        bundle = new Bundle();
//        Management
        initData();
    }

    private void initData() {
        networkRequest(ExploreApi.getManagement(), new SimpleFastJsonCallback<Management>(Management.class, loading) {
            @Override
            public void onSuccess(String url, Management result) {
                if (null != result && 0 != result.getSlave_device_list().size()) {
                    ll_empty.setVisibility(View.GONE);
                    ll_content.setVisibility(View.VISIBLE);
                    management = result;

                    setDeviceText(result.getSlave_device_list(), false);
                    setProductText(result.getProduct_list(), false);
                    tv_usecount_byday.setText(result.getUse_num().equals("0") ? "不限" : result.getUse_num() + "次");
                    tv_usetime_byday.setText(result.getUse_time().equals("0") ? "不限" : result.getUse_time() + "分钟");
                    Object stop_time = mDiskFileCacheHelper.getAsSerializable(STOP_PLAY);
                    if (null != stop_time) {
                        mTime = (System.currentTimeMillis()) - (long) stop_time;
                        if (mTime < STOP_TIME)
                            countDown(STOP_TIME - mTime);
                    }
                } else {
                    ll_empty.setVisibility(View.VISIBLE);
                    ll_content.setVisibility(View.GONE);
                }
                loading.dismiss();
            }
//            @Override
//            public void onSuccess(String url, String result) {
//                Logger.i("管理查询请求成功 = " + result.toString());
//                if (null != result && !"".equals(result)) {
//                    ll_empty.setVisibility(View.GONE);
//                    ll_content.setVisibility(View.VISIBLE);
//                }
//                loading.dismiss();
//            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                super.onFailure(url, statusCode, msg);
                ll_empty.setVisibility(View.VISIBLE);
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
                new AlertDialog.Builder(mContext).setTitle("").setMessage("让孩子停止使用葡萄产品?")
                        .setCancelable(false)
                        .setPositiveButton("停止", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                managementSetall();
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
                bundle.putSerializable(ManagerSettingsActivity.KEY_MANAGER, management);
                YouMengHelper.onEvent(mContext, YouMengHelper.AccompanyHome_control_item, "受控设备");
                startActivity(ManagerSettingsActivity.class, bundle);
                break;
            case R.id.ll_product://受控产品
                bundle.putString(ManagerSettingsActivity.KEY_MANAGER_SETTINGS, ManagerSettingsActivity.TYPE_SETTING_PRODUCT);
                bundle.putSerializable(ManagerSettingsActivity.KEY_MANAGER, management);
                YouMengHelper.onEvent(mContext, YouMengHelper.AccompanyHome_control_item, "受控产品");
                startActivity(ManagerSettingsActivity.class, bundle);
                break;
            case R.id.ll_usecount://使用次数
                bundle.putString(ManagerSettingsActivity.KEY_MANAGER_SETTINGS, ManagerSettingsActivity.TYPE_SETTING_USE_COUNT);
                YouMengHelper.onEvent(mContext, YouMengHelper.AccompanyHome_control_item, "每日使用次数");
                startActivity(ManagerSettingsActivity.class, bundle);
                break;
            case R.id.usetime://使用时间
                bundle.putString(ManagerSettingsActivity.KEY_MANAGER_SETTINGS, ManagerSettingsActivity.TYPE_SETTING_USE_TIME);
                YouMengHelper.onEvent(mContext, YouMengHelper.AccompanyHome_control_item, "每次使用时间");
                startActivity(ManagerSettingsActivity.class, bundle);
                break;
        }
    }

    private void managementSetall() {
        networkRequest(ExploreApi.managementSetall(), new SimpleFastJsonCallback<Management>(Management.class, loading) {
            @Override
            public void onSuccess(String url, Management result) {
                ToastUtils.showToastShort(mContext, "指令发送成功");
                countDown(STOP_TIME);
                mDiskFileCacheHelper.put(STOP_PLAY, System.currentTimeMillis());
                loading.dismiss();
            }
        });
    }

    /**
     * 设备数量显示
     */
    private void setDeviceText(List<ManagementDevice> devices, boolean fromEvent) {
        int count = 0;
        if (fromEvent) {
            count = devices.size();
        } else {
            deviceNum = devices.size();
            for (ManagementDevice device : devices) {
                if (device.getStatus().equals("1")) {
                    count++;
                }
            }
        }
        tv_equipment_name.setText(count + "个，共" + deviceNum + "个");
    }

    /**
     * 产品数量显示
     */
    private void setProductText(List<ManagementProduct> products, boolean fromEvent) {
        int count = 0;
        if (fromEvent) {
            count = products.size();
        } else {
            productNum = products.size();
            for (ManagementProduct product : products) {
                if (product.getStatus() == 1) {
                    count++;
                }
            }
        }
        tv_product_name.setText(count + "个，共" + productNum + "个");
    }

    private void countDown(long millisecond) {
        btn_stopuse.setEnabled(false);
        mTimer = new CountDownTimer(millisecond, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                btn_stopuse.setEnabled(true);
            }
        };
        mTimer.start();
    }

  /*  @Subcriber(tag = ControlledEquipmentFragment.EVENT_CONTROLLED_EQUIPMENT)
    public void eventControlledEquipment(List<ManagementDevice> devices) {
        setDeviceText(devices, true);
    }*/

    @Subcriber(tag = ControlledEquipmentFragment.EVENT_REFRESH_MANAGER)
    public void eventreFreshManager(String str) {
        initData();
    }

    @Subcriber(tag = ControlledProductFragment.EVENT_CONTROLLED_PRODUT)
    public void eventControlledProduct(List<ManagementProduct> products) {
        setProductText(products, true);
    }

    @Subcriber(tag = UseCountEveryDayFragment.EVENT_USECOUNT_EVERYDAY)
    public void eventUseCount(String useCount) {
        tv_usecount_byday.setText(useCount);
    }

    @Subcriber(tag = UseTimeEveryTimeFragment.EVENT_USETIME_EVERYTIME)
    public void eventUseTime(String useTime) {
        tv_usetime_byday.setText(useTime);
    }
}