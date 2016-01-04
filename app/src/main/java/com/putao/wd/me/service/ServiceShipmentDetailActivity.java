package com.putao.wd.me.service;

import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.ServiceShipmentDto;
import com.putao.wd.dto.ServiceShipmentInfoItemDto;
import com.putao.wd.me.service.view.ServiceShipmentInfoItem;
import com.putao.wd.model.Service;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.image.ImageDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 售后物流信息
 * Created by wangou on 15/11/29.
 */
public class ServiceShipmentDetailActivity extends PTWDActivity<GlobalApplication> {

//    public static final String KEY_ORDER_UUID = "serviceUuid";

    @Bind(R.id.tv_company_name)
    TextView tv_company_name;
    @Bind(R.id.tv_express_number)
    TextView tv_express_number;
    @Bind(R.id.tv_express_status)
    TextView tv_express_status;
    @Bind(R.id.ll_package_shipment)
    LinearLayout ll_package_shipment;

//    private String serviceUuid = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_shipment_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

//        serviceUuid = args.getString(KEY_ORDER_UUID);
//        if (serviceUuid == null) serviceUuid = "";
//
//        initComponent();
//        //开始请求包裹信息;
//
//        //初始化物流信息
//        initShipmentData();

    }

    private void initShipmentData() {
        // 测试数据
        ServiceShipmentDto serviceShipmentDto = new ServiceShipmentDto();
        serviceShipmentDto.setIconUrl("http://bbs.putao.com/windid/attachment/avatar/000/66/22/662295_small.jpg");
        serviceShipmentDto.setName("葡萄探索号包裹，快递一天到达");
        serviceShipmentDto.setNumber(3);
        List<String> infoArr = new ArrayList<String>();
        infoArr.add("再上海宝山区发货再上海宝山区发货再上海宝山区发货再上海宝山区发货再上海宝山区发货再上海宝山区发货再上海宝山区发货再上海宝山区发货");
        infoArr.add("再上海宝山区发货22222再上海宝山区发货22222再上海宝山区发货22222再上海宝山区发货22222再上海宝山区发货22222再上海宝山区发货22222再上海宝山区发货22222");
        infoArr.add("再上海宝山区发货33333333");
        infoArr.add("再上海宝山区发货444444");
        serviceShipmentDto.setShipmentInfoList(infoArr);

        //刷新界面
//        refreshView(serviceShipmentDto);
    }


    /**
     * 售后物流信息
     */
    private void getExpressService(){
        networkRequest(OrderApi.getExpressService(""), new SimpleFastJsonCallback<ArrayList<Service>>(Service.class, loading) {
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

    private void initComponent() {
//        img_goods = (ImageDraweeView) findViewById(R.id.img_goods);
//        tv_name = (TextView) findViewById(R.id.tv_name);
//        tv_number = (TextView) findViewById(R.id.tv_number);
//        tv_package_status = (TextView) findViewById(R.id.tv_package_status);
//        ll_package_shipment = (LinearLayout) findViewById(R.id.ll_package_shipment);
    }

//    private void refreshView(ServiceShipmentDto serviceShipmentDto) {
//
//        img_goods.setImageURL(serviceShipmentDto.getIconUrl());
//        tv_name.setText(serviceShipmentDto.getName());
//        tv_number.setText("共" + serviceShipmentDto.getNumber() + "件");
//
//        List<String> infoArr = serviceShipmentDto.getShipmentInfoList();
//        if (infoArr == null || infoArr.size() < 1) {
//            tv_package_status.setText("还没有物流信息");
//            tv_package_status.setTextColor(0xff959595);
//            ll_package_shipment.setVisibility(View.INVISIBLE);
//            return;
//        }
//        tv_package_status.setText("有物流信息");
//        tv_package_status.setTextColor(0xff985ec9);
//        ll_package_shipment.setVisibility(View.VISIBLE);
//        ll_package_shipment.removeAllViews();
//        int infoSize = infoArr.size();
//        for (int i = 0; i < infoSize; i++) {
//            ServiceShipmentInfoItemDto serviceShipmentInfoItemDto = new ServiceShipmentInfoItemDto();
//            if (i == 0) serviceShipmentInfoItemDto.setShowHighLightImage(true);
//            else
//                serviceShipmentInfoItemDto.setShowHighLightImage(false);
//
//            if (i == 0) { // 第一个
//                serviceShipmentInfoItemDto.setShowTopLine(false);
//                serviceShipmentInfoItemDto.setShowBottomLine(true);
//            } else if (infoSize > 1 && i == infoSize - 1) { //最后一个
//                serviceShipmentInfoItemDto.setShowTopLine(true);
//                serviceShipmentInfoItemDto.setShowBottomLine(false);
//            } else { //中间的
//                serviceShipmentInfoItemDto.setShowTopLine(true);
//                serviceShipmentInfoItemDto.setShowBottomLine(true);
//            }
//            serviceShipmentInfoItemDto.setShipmentInfo(infoArr.get(i));
//            ServiceShipmentInfoItem serviceShipmentInfoItem = new ServiceShipmentInfoItem(this, serviceShipmentInfoItemDto);
//            ll_package_shipment.addView(serviceShipmentInfoItem);
//        }


//    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
//    }
}
