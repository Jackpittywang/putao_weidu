package com.putao.wd.me.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.ServiceDto;
import com.putao.wd.dto.ServiceGoodsDto;
import com.putao.wd.dto.ServiceShipmentListItemDto;
import com.putao.wd.me.service.view.ServiceGoodsItem;
import com.putao.wd.me.service.view.ServiceShipmentListItem;
import com.putao.wd.model.Service;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 订单详情
 * Created by wangou on 15/11/29.
 */
public class ServiceDetailActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    public static final String KEY_ORDER = "service";
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
    @Bind(R.id.tv_service_no_text)
    TextView tv_service_no_text;
    @Bind(R.id.tv_service_no)
    TextView tv_service_no;
    @Bind(R.id.tv_service_purchase_time)
    TextView tv_service_purchase_time;
    @Bind(R.id.tv_service_status)
    TextView tv_service_status;
    @Bind(R.id.tv_service_cost)
    TextView tv_service_cost;
    @Bind(R.id.tv_service_cost_text)
    TextView tv_service_cost_text;
    @Bind(R.id.img_info_icon)
    ImageView img_info_icon;
    @Bind(R.id.tv_service_info)
    TextView tv_service_info;
    @Bind(R.id.rl_service_info)
    RelativeLayout rl_service_info;
    @Bind(R.id.tv_no_shipment)
    TextView tv_no_shipment;
    @Bind(R.id.ll_shipment)
    LinearLayout ll_shipment;
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
    @Bind(R.id.ll_service_info)
    LinearLayout ll_service_info;
    @Bind(R.id.tv_goods_title)
    TextView tv_goods_title;
    @Bind(R.id.ll_goods)
    LinearLayout ll_goods;
    @Bind(R.id.tv_goods_total_number)
    TextView tv_goods_total_number;
    @Bind(R.id.tv_cost_text)
    TextView tv_cost_text;
    @Bind(R.id.tv_cost)
    TextView tv_cost;
    @Bind(R.id.tv_shipment_fee_text)
    TextView tv_shipment_fee_text;
    @Bind(R.id.tv_shipment_fee)
    TextView tv_shipment_fee;
    @Bind(R.id.tv_total_cost_text)
    TextView tv_total_cost_text;
    @Bind(R.id.tv_total_cost)
    TextView tv_total_cost;
    @Bind(R.id.rl_fee_info)
    RelativeLayout rl_fee_info;
    @Bind(R.id.ll_goods_info)
    LinearLayout ll_goods_info;
    @Bind(R.id.sv_main)
    ScrollView sv_main;
    @Bind(R.id.btn_service_cancel)
    Button btn_service_cancel;
    @Bind(R.id.btn_service_operation)
    Button btn_service_operation;
    @Bind(R.id.rl_bottom)
    RelativeLayout rl_bottom;

    private ServiceDto serviceDto;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        //获取service数据
        Intent intent = getIntent();
        if (intent != null) {
            serviceDto = (ServiceDto) intent.getSerializableExtra(KEY_ORDER);
        }

        //初始化布局对象
        initComponent();
        //刷新界面
        refreshView();
    }


    /**
     * 售后详情
     */
    private void getServiceDetail() {
        networkRequest(OrderApi.getServiceDetail(), new SimpleFastJsonCallback<Service>(Service.class, loading) {
            @Override
            public void onSuccess(String url, Service result) {
                Logger.d(result.toString());
            }

        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.ll_shipment)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_shipment:// 全部
                openShipmentDetailActivity();
                break;
        }
    }


    /**
     * 打开包裹详情页面
     */
    private void openShipmentDetailActivity() {
        Bundle bundle = new Bundle();
        bundle.putString(ServiceShipmentDetailActivity.KEY_ORDER_UUID, serviceDto.getServiceNo());
        startActivity(ServiceShipmentDetailActivity.class, bundle);
    }

    private void initComponent() {

    }

    private void refreshView() {
        if (serviceDto == null) return;

        tv_service_no.setText(serviceDto.getServiceNo());
        tv_service_purchase_time.setText(serviceDto.getPurchaseTime() + "");
        tv_service_status.setText(ServiceCommon.getServiceStatusShowString(serviceDto.getServiceStatus()));
        tv_service_cost.setText("¥" + serviceDto.getTotalCost());
        tv_service_info.setText("商品信息");
        tv_customer_name.setText(serviceDto.getCustomerName());
        tv_customer_address.setText(serviceDto.getCustomerAddress());
        tv_customer_phone.setText(serviceDto.getCustomerPhone());
        tv_shipment_fee.setText("¥" + serviceDto.getShipmentFee());
        tv_total_cost.setText("¥" + serviceDto.getTotalCost());

        List<ServiceShipmentListItemDto> shipmentList = serviceDto.getShipmentList();
        Logger.i("ServiceDetailActivity", "package size is:" + shipmentList.size());
        if (shipmentList.size() > 0) {
            ll_shipment.removeAllViews();
            for (int i = 0; i < shipmentList.size(); i++) {
                ServiceShipmentListItem serviceShipmentListItem = new ServiceShipmentListItem(this, shipmentList.get(i));
                ll_shipment.addView(serviceShipmentListItem);
            }
        }

        List<ServiceGoodsDto> goodsList = serviceDto.getGoodsList();
        ll_goods.removeAllViews();
        int goodsTotalNumber = 0;
        int goodsNumber = goodsList.size();
        for (int i = 0; i < goodsNumber; i++) {
            ServiceGoodsItem goodsItem = new ServiceGoodsItem(this, goodsList.get(i));
            ll_goods.addView(goodsItem);
            goodsTotalNumber = goodsTotalNumber + goodsList.get(i).getNumber();
        }
        tv_goods_total_number.setText(goodsTotalNumber + "");
        setServiceStatus(serviceDto.getServiceStatus());


    }

    /**
     * 设置订单状态的文字和顶部进度条显示
     *
     * @param status
     */
    private void setServiceStatus(int status) {
        String statusStr = ServiceCommon.getServiceStatusShowString(status);
        TextView tv_service_status = (TextView) findViewById(R.id.tv_service_status);
        tv_service_status.setText(statusStr);
        if (status == ServiceCommon.ORDER_WAITING_PAY || status == ServiceCommon.ORDER_WAITING_SHIPMENT || status == ServiceCommon.ORDER_WAITING_SIGN || status == ServiceCommon.ORDER_SALE_SERVICE) {
            View v_status_waiting_pay = findViewById(R.id.v_status_waiting_pay);
            v_status_waiting_pay.setBackgroundColor(0xffffffff);
            ImageView img_status_waiting_pay = (ImageView) findViewById(R.id.img_status_waiting_pay);
            img_status_waiting_pay.setImageResource(R.drawable.img_details_order_steps_01_sel);
        }
        if (status == ServiceCommon.ORDER_WAITING_SHIPMENT || status == ServiceCommon.ORDER_WAITING_SIGN || status == ServiceCommon.ORDER_SALE_SERVICE) {
            View v_status_waiting_shipment = findViewById(R.id.v_status_waiting_shipment);
            v_status_waiting_shipment.setBackgroundColor(0xffffffff);
            ImageView img_status_waiting_shipment = (ImageView) findViewById(R.id.img_status_waiting_shipment);
            img_status_waiting_shipment.setImageResource(R.drawable.img_details_order_steps_02_sel);
        }
        if (status == ServiceCommon.ORDER_WAITING_SIGN || status == ServiceCommon.ORDER_SALE_SERVICE) {
            View v_status_waiting_sign = findViewById(R.id.v_status_waiting_sign);
            v_status_waiting_sign.setBackgroundColor(0xffffffff);
            ImageView img_status_waiting_sign = (ImageView) findViewById(R.id.img_status_waiting_sign);
            img_status_waiting_sign.setImageResource(R.drawable.img_details_order_steps_03_sel);
        }
        if (status == ServiceCommon.ORDER_SALE_SERVICE) {
            View v_status_sale_service = findViewById(R.id.v_status_sale_service);
            v_status_sale_service.setBackgroundColor(0xffffffff);
            ImageView img_status_sale_service = (ImageView) findViewById(R.id.img_status_sale_service);
            img_status_sale_service.setImageResource(R.drawable.img_details_order_steps_04_sel);
        }
    }

}


