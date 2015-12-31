package com.putao.wd.me.service;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Service;
import com.putao.wd.model.ServiceList;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;


import java.util.ArrayList;

import butterknife.Bind;

/**
 * 订单详情
 * Created by wangou on 15/11/29.
 */
public class ServiceDetailActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    public static final String KEY_SERVICE_ID = "serviceId";

    @Bind(R.id.tv_service_no)
    TextView tv_service_no;
    @Bind(R.id.tv_order_purchase_time)
    TextView tv_order_purchase_time;
    @Bind(R.id.tv_service_order_status)
    TextView tv_service_order_status;
    @Bind(R.id.tv_service_info)
    TextView tv_service_info;
    @Bind(R.id.tv_customer_name)
    TextView tv_customer_name;
    @Bind(R.id.tv_customer_address)
    TextView tv_customer_address;
    @Bind(R.id.tv_customer_phone)
    TextView tv_customer_phone;
    @Bind(R.id.tv_pay_method)
    TextView tv_pay_method;
    @Bind(R.id.tv_shipment_method)
    TextView tv_shipment_method;
    @Bind(R.id.tv_receipt_type)
    TextView tv_receipt_type;
    @Bind(R.id.tv_receipt_head)
    TextView tv_receipt_head;
    @Bind(R.id.tv_receipt_content)
    TextView tv_receipt_content;
    @Bind(R.id.rv_service_detail)
    BasicRecyclerView rv_service_detail;
    @Bind(R.id.tv_goods_total_number)
    TextView tv_goods_total_number;
    @Bind(R.id.tv_cost)
    TextView tv_cost;
    @Bind(R.id.tv_shipment_fee_text)
    TextView tv_shipment_fee_text;
    @Bind(R.id.tv_total_cost)
    TextView tv_total_cost;




    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        String serviceId = getIntent().getExtras().getString(KEY_SERVICE_ID);
        networkRequest(OrderApi.getServiceDetail(serviceId), new SimpleFastJsonCallback<ArrayList<ServiceList>>(ServiceList.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<ServiceList> result) {
                Logger.d("售后详情 = " + result.toString());
//                if (result.getData().size() != 0) {
//
//                }
                loading.dismiss();
            }
        });

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    //    @OnClick(R.id.ll_shipment)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ll_shipment:// 全部
//                openShipmentDetailActivity();
//                break;
        }
    }

}

//    /**
//     * 打开包裹详情页面
//     */
//    private void openShipmentDetailActivity() {
//        Bundle bundle = new Bundle();
//        bundle.putString(ServiceShipmentDetailActivity.KEY_ORDER_UUID, serviceDto.getServiceNo());
//        startActivity(ServiceShipmentDetailActivity.class, bundle);
//    }
//


//    /**
//     * 设置订单状态的文字和顶部进度条显示
//     *
//     * @param status
//     */
//    private void setServiceStatus(int status) {
//        String statusStr = ServiceCommonState.getServiceStatusShowString(status);
//        TextView tv_service_status = (TextView) findViewById(R.id.tv_service_status);
//        tv_service_status.setText(statusStr);
//        if (status == ServiceCommonState.SERVICE_REFUND_CHECK || status == ServiceCommonState.SERVICE_REFUND_AGREE ||
//                status == ServiceCommonState.SERVICE_REFUND_SEND || status == ServiceCommonState.SERVICE_REFUND_RECEIVE ||
//                status == ServiceCommonState.SERVICE_REFUND_FINISH || status == ServiceCommonState.SERVICE_EXCHANGE_CHECK ||
//                status == ServiceCommonState.SERVICE_EXCHANGE_AGREE || status == ServiceCommonState.SERVICE_EXCHANGE_SEND ||
//                status == ServiceCommonState.SERVICE_EXCHANGE_RECEIVE || status == ServiceCommonState.SERVICE_EXCHANGE_BACK ||
//                status == ServiceCommonState.SERVICE_EXCHANGE_FINISH) {
//            View v_status_waiting_pay = findViewById(R.id.v_status_waiting_pay);
//            v_status_waiting_pay.setBackgroundColor(0xffffffff);
//            ImageView img_status_waiting_pay = (ImageView) findViewById(R.id.img_status_waiting_pay);
//            img_status_waiting_pay.setImageResource(R.drawable.img_details_as_steps_01_sel);
//        }
//        if (status == ServiceCommonState.SERVICE_REFUND_AGREE || status == ServiceCommonState.SERVICE_REFUND_SEND || status == ServiceCommonState.SERVICE_REFUND_RECEIVE ||
//                status == ServiceCommonState.SERVICE_REFUND_FINISH || status == ServiceCommonState.SERVICE_EXCHANGE_AGREE || status == ServiceCommonState.SERVICE_EXCHANGE_SEND ||
//                status == ServiceCommonState.SERVICE_EXCHANGE_RECEIVE || status == ServiceCommonState.SERVICE_EXCHANGE_BACK ||
//                status == ServiceCommonState.SERVICE_EXCHANGE_FINISH) {
//            View v_status_waiting_shipment = findViewById(R.id.v_status_waiting_shipment);
//            v_status_waiting_shipment.setBackgroundColor(0xffffffff);
//            ImageView img_status_waiting_shipment = (ImageView) findViewById(R.id.img_status_waiting_shipment);
//            img_status_waiting_shipment.setImageResource(R.drawable.img_details_as_steps_02_sel);
//        }
//        if (status == ServiceCommonState.SERVICE_REFUND_SEND || status == ServiceCommonState.SERVICE_REFUND_RECEIVE ||
//                status == ServiceCommonState.SERVICE_REFUND_FINISH || status == ServiceCommonState.SERVICE_EXCHANGE_SEND ||
//                status == ServiceCommonState.SERVICE_EXCHANGE_RECEIVE || status == ServiceCommonState.SERVICE_EXCHANGE_BACK ||
//                status == ServiceCommonState.SERVICE_EXCHANGE_FINISH) {
//            View v_status_waiting_sign = findViewById(R.id.v_status_waiting_sign);
//            v_status_waiting_sign.setBackgroundColor(0xffffffff);
//            ImageView img_status_waiting_sign = (ImageView) findViewById(R.id.img_status_waiting_sign);
//            img_status_waiting_sign.setImageResource(R.drawable.img_details_as_steps_03_sel);
//        }
//        if (status == ServiceCommonState.SERVICE_REFUND_FINISH || status == ServiceCommonState.SERVICE_EXCHANGE_FINISH) {
//            View v_status_sale_service = findViewById(R.id.v_status_sale_service);
//            v_status_sale_service.setBackgroundColor(0xffffffff);
//            ImageView img_status_sale_service = (ImageView) findViewById(R.id.img_status_sale_service);
//            img_status_sale_service.setImageResource(R.drawable.img_details_as_steps_04_sel);
//        }
//    }



