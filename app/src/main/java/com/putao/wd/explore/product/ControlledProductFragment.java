package com.putao.wd.explore.product;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.dto.ControlProductItem;
import com.putao.wd.explore.product.adapter.ControlledProductAdatper;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 探索号-受控产品
 * Created by wangou on 2015/12/2.
 */
public class ControlledProductFragment extends PTWDFragment implements View.OnClickListener {
    public static final String EVENT_CONTROLLED_PRODUCT = "controlled_product";
    private String productname="";

    @Bind(R.id.brv_controlled_product)
    BasicRecyclerView brv_controlled_product;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_controlled_product;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        if (initDataTest().size() != 0) {
            ControlledProductAdatper controlledProductAdatper = new ControlledProductAdatper(mActivity, initDataTest());
            brv_controlled_product.setAdapter(controlledProductAdatper);
        }
    }


    private List<ControlProductItem> initDataTest() {
        List<ControlProductItem> list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            ControlProductItem msgitem = new ControlProductItem();
            msgitem.setName("设备名称" + i);
            msgitem.setUri("https://static.pexels.com/photos/5854/sea-woman-legs-water-medium.jpg");
            list.add(msgitem);
        }
        return list;
    }

    @Override
    public void onRightAction() {
        EventBusHelper.post(productname, EVENT_CONTROLLED_PRODUCT);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}