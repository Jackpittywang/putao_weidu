package com.putao.wd.explore.manage;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.dto.ControllItem;
import com.putao.wd.explore.manage.adapter.ControlledEquipmentAdapter;
import com.putao.wd.explore.manage.adapter.ControlledProductAdapter;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 探索号-受控设备
 * Created by wangou on 2015/12/2.
 */
public class ControlledProductFragment extends PTWDFragment {
    public static final String EVENT_CONTROLLED_PRODUT = "controlled_product";

    @Bind(R.id.brv_product)
    BasicRecyclerView brv_product;

    private ControlledProductAdapter adapter;
    private List<ControllItem> selectItem = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_controlled_product;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        adapter = new ControlledProductAdapter(mActivity, getTestData());
        brv_product.setAdapter(adapter);
        addListener();
    }

    private void addListener() {
        brv_product.setOnItemClickListener(new OnItemClickListener<ControllItem>() {
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
        ControllItem msgitem = new ControllItem();
        msgitem.setName("淘淘向右走");
        list.add(msgitem);

        msgitem = new ControllItem();
        msgitem.setName("班得瑞的奇幻花园");
        list.add(msgitem);

        msgitem = new ControllItem();
        msgitem.setName("旋转吧,魔方");
        list.add(msgitem);

        msgitem = new ControllItem();
        msgitem.setName("萌撕拉");
        list.add(msgitem);
        return list;
    }

    @Override
    public void onRightAction() {
        EventBusHelper.post(selectItem, EVENT_CONTROLLED_PRODUT);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}