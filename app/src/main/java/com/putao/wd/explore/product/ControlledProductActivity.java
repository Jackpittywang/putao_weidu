package com.putao.wd.explore.product;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.ControlProductItem;
import com.putao.wd.explore.product.adapter.ControlledProductAdatper;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 探索号-受控产品
 * Created by wangou on 2015/12/2.
 */
public class ControlledProductActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.brv_controlled_product)
    BasicRecyclerView brv_controlled_product;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_controlled_product;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        if (initDataTest().size() != 0) {
            ControlledProductAdatper controlledProductAdatper = new ControlledProductAdatper(mContext, initDataTest());
            brv_controlled_product.setAdapter(controlledProductAdatper);
        }
    }

    private List<ControlProductItem> initDataTest() {
        List<ControlProductItem> list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            ControlProductItem msgitem = new ControlProductItem();
            msgitem.setName("设备名称" + i);
            list.add(msgitem);
        }
        return list;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}