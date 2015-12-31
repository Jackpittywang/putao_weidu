package com.putao.wd.me.service;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.service.adapter.ServiceAdapter;
import com.putao.wd.me.service.adapter.ServiceListAdapter;
import com.putao.wd.model.Express;
import com.putao.wd.model.Service;
import com.putao.wd.model.ServiceList;
import com.putao.wd.model.ServiceOrderInfo;
import com.putao.wd.model.ServiceProduct;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 订单详情
 * Created by wangou on 15/11/29.
 */
public class ServiceDetailActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    public static final String KEY_SERVICE_ID = "serviceId";
    public static final String KEY_SERVICE_STATUS = "serviceStatus";

    @Bind(R.id.v_status_waiting_pay)
    View v_status_waiting_pay;
    @Bind(R.id.img_status_waiting_pay)
    ImageView img_status_waiting_pay;
    @Bind(R.id.v_status_waiting_shipment)
    View v_status_waiting_shipment;
    @Bind(R.id.img_status_waiting_shipment)
    ImageView img_status_waiting_shipment;
    @Bind(R.id.v_status_waiting_sign)
    View v_status_waiting_sign;
    @Bind(R.id.img_status_waiting_sign)
    ImageView img_status_waiting_sign;
    @Bind(R.id.v_status_sale_service)
    View v_status_sale_service;
    @Bind(R.id.img_status_sale_service)
    ImageView img_status_sale_service;
    @Bind(R.id.rl_fill_order_no)
    RelativeLayout rl_fill_order_no;

    @Bind(R.id.tv_service_no)
    TextView tv_service_no;
    @Bind(R.id.tv_order_purchase_time)
    TextView tv_order_purchase_time;
    @Bind(R.id.tv_service_order_status)
    TextView tv_service_order_status;
    @Bind(R.id.tv_service_info)
    TextView tv_service_info;
    @Bind(R.id.tv_no_shipment)
    TextView tv_no_shipment;
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
    @Bind(R.id.tv_shipment_fee)
    TextView tv_shipment_fee;
    @Bind(R.id.tv_total_cost)
    TextView tv_total_cost;
    @Bind(R.id.btn_execute)
    Button btn_execute;
    @Bind(R.id.ll_invoice)
    LinearLayout ll_invoice;


    private ServiceAdapter adapter;
    private String serviceStatus;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        adapter = new ServiceAdapter(mContext, null);
        rv_service_detail.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        String serviceId = bundle.getString(KEY_SERVICE_ID);
        serviceStatus = bundle.getString(KEY_SERVICE_STATUS);
        networkRequest(OrderApi.getServiceDetail(serviceId), new SimpleFastJsonCallback<ArrayList<ServiceList>>(ServiceList.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<ServiceList> result) {
                Logger.d("售后详情 = " + result.toString());
                setContent(result.get(0));
                List<ServiceProduct> products = result.get(0).getProduct();
                if (products.size() != 0) {
                    adapter.addAll(products);
                }
                loading.dismiss();
            }
        });

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 设置界面显示内容
     */
    private void setContent(ServiceList serviceList) {
        ServiceOrderInfo order_info = serviceList.getOrder_info();
        List<Express> express = serviceList.getExpress();
        checkServiceType(serviceList.getService_type());
        tv_service_no.setText(order_info.getOrder_sn());
        tv_order_purchase_time.setText(DateUtils.secondToDate(Integer.parseInt(serviceList.getCreate_time()), "yyyy-MM-dd HH:mm:ss"));
        tv_service_info.setText(serviceList.getDepiction());
        tv_service_order_status.setText(serviceStatus);
//        tv_no_shipment.setText();
        tv_customer_name.setText(order_info.getConsignee());
        tv_customer_address.setText(order_info.getAddress());
        tv_customer_phone.setText(order_info.getMobile());
        tv_pay_method.setText("支付方式：" + order_info.getPay_type());
        tv_shipment_method.setText("配送方式：" + order_info.getDeliver_type());
        if ("1".equals(order_info.getNeed_invoice())) {
            tv_receipt_type.setText("发票类型：" + order_info.getInvoice_title());
            tv_receipt_head.setText("发票抬头：" + order_info.getInvoice_type());
            tv_receipt_content.setText("发票内容：" + order_info.getInvoice_content());
        } else {
            ll_invoice.setVisibility(View.GONE);
        }
        tv_goods_total_number.setText(serviceList.getSaleTotalQuantity() + "");
        tv_cost.setText(order_info.getProduct_money());
        tv_shipment_fee.setText("0.00");
        tv_total_cost.setText(serviceList.getSaleTotalPrice() + "");
    }

    /**
     * 根据售后类型设置进度显示
     */
    private void checkServiceType(String service_type) {
        switch (service_type) {
            case "1"://1换货

                break;
            case "2"://2退货

                break;
            case "3"://3退款

                rl_fill_order_no.setVisibility(View.GONE);
                img_status_sale_service.setImageResource(R.drawable.img_details_refund_steps_03_nor);
                break;
        }

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

//    /**
//     * 打开包裹详情页面
//     */
//    private void openShipmentDetailActivity() {
//        Bundle bundle = new Bundle();
//        bundle.putString(ServiceShipmentDetailActivity.KEY_ORDER_UUID, serviceDto.getServiceNo());
//        startActivity(ServiceShipmentDetailActivity.class, bundle);
//    }
//

}






