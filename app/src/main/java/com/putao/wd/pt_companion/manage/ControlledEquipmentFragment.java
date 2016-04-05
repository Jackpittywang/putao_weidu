package com.putao.wd.pt_companion.manage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.pt_companion.manage.adapter.ControlledEquipmentAdapter;
import com.putao.wd.model.Management;
import com.putao.wd.model.ManagementDevice;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 探索号-受控设备
 * Created by wangou on 2015/12/2.
 */
public class ControlledEquipmentFragment extends PTWDFragment {
    public static final String EVENT_CONTROLLED_EQUIPMENT = "controlled_equipment";
    public static final String EVENT_REFRESH_MANAGER = "event_refresh_manager";
    @Bind(R.id.brv_equipment)
    BasicRecyclerView brv_equipment;

    private ControlledEquipmentAdapter adapter;
    private List<ManagementDevice> selectItem = new ArrayList<>();
    private Management mManagement;
    private boolean mShowDelete;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_controlled_equipment;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        adapter = new ControlledEquipmentAdapter(mActivity, selectItem, mShowDelete);
        brv_equipment.setAdapter(adapter);
        networkRequest(ExploreApi.getManagement(), new SimpleFastJsonCallback<Management>(Management.class, loading) {
            @Override
            public void onSuccess(String url, Management result) {
                mManagement = result;
                if (null != result) {
                    adapter.replaceAll(result.getSlave_device_list());
//                    for (ManagementDevice device : result.getSlave_device_list()) {
//                        if (device.getStatus().equals("1")) {
//                            selectItem.add(device);
//                        }
//                    }
                }
                loading.dismiss();
            }
        });
//        addListener();
    }

    private void addListener() {
        brv_equipment.setOnItemClickListener(new OnItemClickListener<ManagementDevice>() {
            @Override
            public void onItemClick(ManagementDevice item, int position) {
//                mManagement.getSlave_device_list().get(position).setStatus("1".equals(item.getStatus()) ? "0" : "1");
                if (!"1".equals(item.getStatus())) {
                    item.setStatus("1");
                    selectItem.add(item);
                } else {
                    item.setStatus("0");
                    selectItem.remove(item);
                }
                adapter.replace(position, item);
            }
        });
    }


    @Override
    public void onRightAction() {
       /* EventBusHelper.post(selectItem, EVENT_CONTROLLED_EQUIPMENT);
        Management management = new Management();
        management.setSlave_device_list(mManagement.getSlave_device_list());
        mActivity.finish();
        networkRequest(ExploreApi.managementEdit(JSONObject.toJSONString(management)), new SimpleFastJsonCallback<ManagementEdit>(ManagementEdit.class, loading) {
            @Override
            public void onSuccess(String url, ManagementEdit result) {
                Logger.i("探索号管理信息保存成功");
            }
        });*/
        mShowDelete = !mShowDelete;
        adapter.setShowDelete(mShowDelete);
        setRightTitle(mShowDelete ? "保存" : "编辑");
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Subcriber(tag = ControlledEquipmentAdapter.MANAGER)
    public void eventControlledEquipmentAdapter(final ManagementDevice devices) {
        if (devices.getStatus().equals("1")) {
            selectItem.add(devices);
        } else {
            selectItem.remove(devices);
        }

        Management management = new Management();
        management.setSlave_device_list(mManagement.getSlave_device_list());
        adapter.replaceAll(mManagement.getSlave_device_list());
        networkRequest(ExploreApi.managementEdit(JSONObject.toJSONString(management)), new SimpleFastJsonCallback<Management>(Management.class, loading) {
            @Override
            public void onSuccess(String url, Management result) {
                Logger.i("探索号管理信息保存成功");
                EventBusHelper.post(EVENT_REFRESH_MANAGER, EVENT_REFRESH_MANAGER);
                ToastUtils.showToastShort(mActivity, "0".equals(devices.getStatus()) ? "解绑成功" : "绑定成功");
            }
        });
    }

    @Subcriber(tag = ControlledEquipmentAdapter.EVENT_ITEM_DELETE)
    public void eventItemDelete(ManagementDevice device) {
        showDialog(device);
    }

    /**
     * 取消订单dialog
     */
    private void showDialog(final ManagementDevice device) {
        AlertDialog dialog = new AlertDialog.Builder(mActivity)
                .setTitle("提示")
                .setMessage("确定取消")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.delete(device);
                        networkRequest(ExploreApi.managementUnbind(device.getSlave_id(), device.getSlave_device_id()),
                                new SimpleFastJsonCallback<String>(String.class, loading) {
                                    @Override
                                    public void onSuccess(String url, String result) {
                                        EventBusHelper.post(EVENT_REFRESH_MANAGER, EVENT_REFRESH_MANAGER);
                                        Logger.i("探索号管理信息保存成功");
                                    }
                                });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}