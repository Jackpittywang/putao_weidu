package com.putao.wd.companion.manage;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.companion.manage.adapter.ControlledProductAdapter;
import com.putao.wd.model.Management;
import com.putao.wd.model.ManagementProduct;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

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
    private List<ManagementProduct> selectItem = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_controlled_product;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        adapter = new ControlledProductAdapter(mActivity, null);
        brv_product.setAdapter(adapter);
        networkRequest(ExploreApi.getManagement(), new SimpleFastJsonCallback<Management>(Management.class, loading) {
            @Override
            public void onSuccess(String url, Management result) {
                if (null != result) {
                    adapter.replaceAll(result.getProduct_list());
                    for (ManagementProduct product : result.getProduct_list()) {
                        if (product.getStatus() == 1) {
                            selectItem.add(product);
                        }
                    }
                }
                loading.dismiss();
            }
        });

        addListener();
    }

    private void addListener() {
        brv_product.setOnItemClickListener(new OnItemClickListener<ManagementProduct>() {
            @Override
            public void onItemClick(ManagementProduct item, int position) {
                if (item.getStatus() != 1) {
                    item.setStatus(1);
                    selectItem.add(item);
                } else {
                    item.setStatus(0);
                    selectItem.remove(item);
                }
                adapter.replace(position, item);
            }
        });
    }

//    private List<ControllItem> getTestData() {
//        List<ControllItem> list = new ArrayList<>();
//        ControllItem msgitem = new ControllItem();
//        msgitem.setName("淘淘向右走");
//        list.add(msgitem);
//
//        msgitem = new ControllItem();
//        msgitem.setName("班得瑞的奇幻花园");
//        list.add(msgitem);
//
//        msgitem = new ControllItem();
//        msgitem.setName("旋转吧,魔方");
//        list.add(msgitem);
//
//        msgitem = new ControllItem();
//        msgitem.setName("萌撕拉");
//        list.add(msgitem);
//        return list;
//    }

    @Override
    public void onRightAction() {
        EventBusHelper.post(selectItem, EVENT_CONTROLLED_PRODUT);
       mActivity.finish();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}