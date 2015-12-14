package com.putao.wd.explore.manage;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.dto.ControllItem;
import com.putao.wd.explore.manage.adapter.ControlledEquipmentAdapter;
import com.putao.wd.model.ManagementEdit;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

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
    private List<ControllItem> selectItem = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_controlled_equipment;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        adapter = new ControlledEquipmentAdapter(mActivity, getTestData());
        brv_equipment.setAdapter(adapter);
        addListener();
    }

    private void addListener() {
        brv_equipment.setOnItemClickListener(new OnItemClickListener<ControllItem>() {
            @Override
            public void onItemClick(ControllItem item, int position) {
                if (!item.isSelect()) {
                    item.setIsSelect(true);
                    selectItem.add(item);
                } else {
                    item.setIsSelect(false);
                    selectItem.remove(item);
                }
                adapter.replace(position, item);
            }
        });
    }

    private List<ControllItem> getTestData() {
        List<ControllItem> list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            ControllItem msgitem = new ControllItem();
            msgitem.setName("设备名称" + i);
            list.add(msgitem);
        }
        return list;
    }

    @Override
    public void onRightAction() {
        EventBusHelper.post(selectItem, EVENT_CONTROLLED_EQUIPMENT);
        networkRequest(ExploreApi.managementEdit("afdakfakfakfknafaank"), new SimpleFastJsonCallback<ManagementEdit>(ManagementEdit.class, loading) {
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
}