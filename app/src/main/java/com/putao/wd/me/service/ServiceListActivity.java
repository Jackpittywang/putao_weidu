package com.putao.wd.me.service;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.ServiceDto;
import com.putao.wd.dto.ServiceGoodsDto;
import com.putao.wd.dto.ServiceShipmentListItemDto;
import com.putao.wd.me.service.adapter.ServiceListAdapter;
import com.putao.wd.model.Service;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;
import com.sunnybear.library.view.select.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * 售后列表
 * Created by wangou on 15/12/7.
 */
public class ServiceListActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    @Bind(R.id.rv_service)
    BasicRecyclerView rv_service;//售后列表
    @Bind(R.id.rl_no_service)
    RelativeLayout rl_no_service;//没有售后时的布局

    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar ll_title;

    private String TAG = ServiceListActivity.class.getName();
    private ServiceListAdapter adapter;
    private List<ServiceDto> serviceList;

    // 当前第几个被选中
    private int currentIndex = -1;
    private List<Button> buttonList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        // 测试数据
        serviceList = new ArrayList<ServiceDto>();

        for (int i = 0; i < 10; i++) {
            ServiceDto service = new ServiceDto();
            service.setServiceNo(Math.random() + "");
            service.setPurchaseTime(System.currentTimeMillis());
            service.setTotalCost(569);
            service.setCustomerName("葡萄科技");
            service.setCustomerAddress("宜山路218号");
            service.setCustomerPhone("13622222222");
            if (i == 0) service.setServiceStatus(ServiceCommon.ORDER_WAITING_PAY);
            else if (i == 1) service.setServiceStatus(ServiceCommon.ORDER_WAITING_SHIPMENT);
            else if (i == 2) service.setServiceStatus(ServiceCommon.ORDER_WAITING_SIGN);
            else if (i == 3) service.setServiceStatus(ServiceCommon.ORDER_SALE_SERVICE);
            else if (i == 4) service.setServiceStatus(ServiceCommon.ORDER_CANCLED);
            else if (i == 5) service.setServiceStatus(ServiceCommon.ORDER_CLOSED);
            else service.setServiceStatus(ServiceCommon.ORDER_WAITING_PAY);

            int num = (int) (Math.random() * 3);
            List<ServiceGoodsDto> goodsList = new ArrayList<ServiceGoodsDto>();
            for (int j = 0; j < num; j++) {
                ServiceGoodsDto goods = new ServiceGoodsDto();
                goods.setImageUrl("http://bbs.putao.com/windid/attachment/avatar/000/66/22/662295_small.jpg");
                goods.setNumber((int) Math.round(Math.random() * 10) + 1);
                goods.setPrice((int) Math.round(Math.random() * 100) + 1);
                goods.setName("葡萄探索号" + j);
                goods.setSpecification("颜色：紫色");
                if (j == 1) goods.setIsPreSale(true);
                goodsList.add(goods);

            }
            service.setGoodsList(goodsList);

            List<ServiceShipmentListItemDto> shipmentList = new ArrayList<ServiceShipmentListItemDto>();
            num = (int) (Math.random() * 3) + 1;
            Logger.i(TAG, "package num is:" + num);
            for (int j = 0; j < num; j++) {
                ServiceShipmentListItemDto serviceShipmentListItemDto = new ServiceShipmentListItemDto();
                serviceShipmentListItemDto.setTitle("包裹" + j);
                serviceShipmentListItemDto.setStatus((int) (Math.random() * 7));
                serviceShipmentListItemDto.setShipmentId(System.currentTimeMillis() + "");
                shipmentList.add(serviceShipmentListItemDto);
            }
            service.setShipmentList(shipmentList);

            serviceList.add(service);

        }
        // 测试数据结束
        if (serviceList == null || serviceList.size() == 0) {
            rl_no_service.setVisibility(View.VISIBLE);
            rv_service.setVisibility(View.INVISIBLE);
            return;
        } else {
            rl_no_service.setVisibility(View.INVISIBLE);
            rv_service.setVisibility(View.VISIBLE);
        }

        adapter = new ServiceListAdapter(mContext, serviceList);
        rv_service.setAdapter(adapter);
        //点击item
        rv_service.setOnItemClickListener(new OnItemClickListener<ServiceDto>() {

            @Override
            public void onItemClick(ServiceDto serviceDto, int position) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(ServiceDetailActivity.KEY_ORDER, serviceDto);
                startActivity(ServiceDetailActivity.class, bundle);
            }
        });

        refreshViewByType(0);
    }

    /**
     * 售后列表
     */
    private void getServiceLists(){
        networkRequest(OrderApi.getServiceLists(""), new SimpleFastJsonCallback<ArrayList<Service>>(Service.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Service> result) {
                Logger.d(result.toString());
            }

        });
    }
    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }




    /**
     * 根据类型刷新界面
     *
     * @param type
     */
    private void refreshViewByType(int type) {
        Logger.i(TAG, "type clicked :" + type);
    }


}
