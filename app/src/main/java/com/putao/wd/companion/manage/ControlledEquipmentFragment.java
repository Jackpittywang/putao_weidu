package com.putao.wd.companion.manage;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.companion.manage.adapter.ControlledEquipmentAdapter;
import com.putao.wd.model.Management;
import com.putao.wd.model.ManagementDevice;
import com.putao.wd.model.ManagementEdit;
import com.putao.wd.model.ManagementProduct;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
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
    @Bind(R.id.brv_equipment)
    BasicRecyclerView brv_equipment;

    private ControlledEquipmentAdapter adapter;
    private List<ManagementDevice> selectItem = new ArrayList<>();
    private Management mManagement;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_controlled_equipment;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        adapter = new ControlledEquipmentAdapter(mActivity, selectItem);
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

//    private List<ControllItem> getTestData() {
//        List<ControllItem> list = new ArrayList<>();
//        for (int i = 1; i <= 3; i++) {
//            ControllItem msgitem = new ControllItem();
//            msgitem.setName("设备名称" + i);
//            list.add(msgitem);
//        }
//        return list;
//    }

//    @Override
//    public void onRightAction() {
//        EventBusHelper.post(selectItem, EVENT_CONTROLLED_EQUIPMENT);
//        Management management = new Management();
//        management.setSlave_device_list(mManagement.getSlave_device_list());
//        mActivity.finish();
//        networkRequest(ExploreApi.managementEdit(JSONObject.toJSONString(management)), new SimpleFastJsonCallback<ManagementEdit>(ManagementEdit.class, loading) {
//            @Override
//            public void onSuccess(String url, ManagementEdit result) {
//                Logger.i("探索号管理信息保存成功");
//            }
//        });
//    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Subcriber(tag = ControlledEquipmentAdapter.MANAGER)
    public void eventControlledEquipmentAdapter(ManagementDevice devices) {
        if (devices.getStatus().equals("1")) {
            selectItem.add(devices);
        } else {
            selectItem.remove(devices);
        }
        Management management = new Management();
        management.setSlave_device_list(mManagement.getSlave_device_list());
        adapter.replaceAll(mManagement.getSlave_device_list());
        EventBusHelper.post(selectItem, EVENT_CONTROLLED_EQUIPMENT);
        networkRequest(ExploreApi.managementEdit(JSONObject.toJSONString(management)), new SimpleFastJsonCallback<ManagementEdit>(ManagementEdit.class, loading) {
            @Override
            public void onSuccess(String url, ManagementEdit result) {
                Logger.i("探索号管理信息保存成功");
            }
        });
    }
}