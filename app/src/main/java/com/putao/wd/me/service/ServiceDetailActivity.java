package com.putao.wd.me.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.OrderDto;
import com.putao.wd.dto.OrderGoodsDto;
import com.putao.wd.dto.OrderShipmentListItemDto;
import com.putao.wd.me.order.OrderCommon;
import com.putao.wd.me.order.OrderShipmentDetailActivity;
import com.putao.wd.me.order.view.OrderGoodsItem;
import com.putao.wd.me.order.view.OrderShipmentListItem;
import com.putao.wd.model.OrderDetail;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;

import java.util.List;

import butterknife.OnClick;

/**
 * 售后详情
 * Created by wangou on 15/11/29.
 */
public class ServiceDetailActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    public static final String KEY_ORDER = "order";

    private OrderDto orderDto;
    private TextView tv_order_no;
    private TextView tv_order_purchase_time;
    private TextView tv_order_status;
    private TextView tv_order_cost;
    private TextView tv_order_info;
    private TextView tv_customer_name;
    private TextView tv_customer_address;
    private TextView tv_customer_phone;
    private TextView tv_shipment_fee;
    private TextView tv_total_cost;
    private LinearLayout ll_goods;
    private TextView tv_goods_total_number;
    private LinearLayout ll_shipment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        //获取order数据
        Intent intent = getIntent();
        if (intent != null) {
            orderDto = (OrderDto) intent.getSerializableExtra(KEY_ORDER);
        }

        //初始化布局对象
        initComponent();
        //刷新界面
        refreshView();
    }


    /**
     * 订单详情
     */
    private void getOrderDetail(){
        networkRequest(OrderApi.getOrderDetail(""), new SimpleFastJsonCallback<OrderDetail>(OrderDetail.class, loading) {
            @Override
            public void onSuccess(String url, OrderDetail result) {
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
        bundle.putString(OrderShipmentDetailActivity.KEY_ORDER_UUID, orderDto.getOrderNo());
        startActivity(OrderShipmentDetailActivity.class, bundle);
    }

    private void initComponent() {
        tv_order_no = (TextView) findViewById(R.id.tv_order_no);
        tv_order_purchase_time = (TextView) findViewById(R.id.tv_order_purchase_time);
        tv_order_status = (TextView) findViewById(R.id.tv_order_status);
        tv_order_cost = (TextView) findViewById(R.id.tv_order_cost);
        tv_order_info = (TextView) findViewById(R.id.tv_order_info);
        tv_customer_name = (TextView) findViewById(R.id.tv_customer_name);
        tv_customer_address = (TextView) findViewById(R.id.tv_customer_address);
        tv_customer_phone = (TextView) findViewById(R.id.tv_customer_phone);
        tv_shipment_fee = (TextView) findViewById(R.id.tv_shipment_fee);
        tv_total_cost = (TextView) findViewById(R.id.tv_total_cost);
        ll_goods = (LinearLayout) findViewById(R.id.ll_goods);
        tv_goods_total_number = (TextView) findViewById(R.id.tv_goods_total_number);
        ll_shipment = (LinearLayout) findViewById(R.id.ll_shipment);
    }

    private void refreshView() {
        if (orderDto == null) return;

        tv_order_no.setText(orderDto.getOrderNo());
        tv_order_purchase_time.setText(orderDto.getPurchaseTime() + "");
        tv_order_status.setText(OrderCommon.getOrderStatusShowString(orderDto.getOrderStatus()));
        tv_order_cost.setText("¥" + orderDto.getTotalCost());
        tv_order_info.setText("商品信息");
        tv_customer_name.setText(orderDto.getCustomerName());
        tv_customer_address.setText(orderDto.getCustomerAddress());
        tv_customer_phone.setText(orderDto.getCustomerPhone());
        tv_shipment_fee.setText("¥" + orderDto.getShipmentFee());
        tv_total_cost.setText("¥" + orderDto.getTotalCost());

        List<OrderShipmentListItemDto> shipmentList = orderDto.getShipmentList();
        Logger.i("OrderDetailActivity", "package size is:" + shipmentList.size());
        if (shipmentList.size() > 0) {
            ll_shipment.removeAllViews();
            for (int i = 0; i < shipmentList.size(); i++) {
                OrderShipmentListItem orderShipmentListItem = new OrderShipmentListItem(this, shipmentList.get(i));
                ll_shipment.addView(orderShipmentListItem);
            }
        }

        List<OrderGoodsDto> goodsList = orderDto.getGoodsList();
        ll_goods.removeAllViews();
        int goodsTotalNumber = 0;
        int goodsNumber = goodsList.size();
        for (int i = 0; i < goodsNumber; i++) {
            OrderGoodsItem goodsItem = new OrderGoodsItem(this, goodsList.get(i));
            ll_goods.addView(goodsItem);
            goodsTotalNumber = goodsTotalNumber + goodsList.get(i).getNumber();
        }
        tv_goods_total_number.setText(goodsTotalNumber + "");
        setOrderStatus(orderDto.getOrderStatus());


    }

    /**
     * 设置订单状态的文字和顶部进度条显示
     *
     * @param status
     */
    private void setOrderStatus(int status) {
        String statusStr = OrderCommon.getOrderStatusShowString(status);
        TextView tv_order_status = (TextView) findViewById(R.id.tv_order_status);
        tv_order_status.setText(statusStr);
        if (status == OrderCommon.ORDER_WAITING_PAY || status == OrderCommon.ORDER_WAITING_SHIPMENT || status == OrderCommon.ORDER_WAITING_SIGN || status == OrderCommon.ORDER_SALE_SERVICE) {
            View v_status_waiting_pay = findViewById(R.id.v_status_waiting_pay);
            v_status_waiting_pay.setBackgroundColor(0xffffffff);
            ImageView img_status_waiting_pay = (ImageView) findViewById(R.id.img_status_waiting_pay);
            img_status_waiting_pay.setImageResource(R.drawable.img_details_order_steps_01_sel);
        }
        if (status == OrderCommon.ORDER_WAITING_SHIPMENT || status == OrderCommon.ORDER_WAITING_SIGN || status == OrderCommon.ORDER_SALE_SERVICE) {
            View v_status_waiting_shipment = findViewById(R.id.v_status_waiting_shipment);
            v_status_waiting_shipment.setBackgroundColor(0xffffffff);
            ImageView img_status_waiting_shipment = (ImageView) findViewById(R.id.img_status_waiting_shipment);
            img_status_waiting_shipment.setImageResource(R.drawable.img_details_order_steps_02_sel);
        }
        if (status == OrderCommon.ORDER_WAITING_SIGN || status == OrderCommon.ORDER_SALE_SERVICE) {
            View v_status_waiting_sign = findViewById(R.id.v_status_waiting_sign);
            v_status_waiting_sign.setBackgroundColor(0xffffffff);
            ImageView img_status_waiting_sign = (ImageView) findViewById(R.id.img_status_waiting_sign);
            img_status_waiting_sign.setImageResource(R.drawable.img_details_order_steps_03_sel);
        }
        if (status == OrderCommon.ORDER_SALE_SERVICE) {
            View v_status_sale_service = findViewById(R.id.v_status_sale_service);
            v_status_sale_service.setBackgroundColor(0xffffffff);
            ImageView img_status_sale_service = (ImageView) findViewById(R.id.img_status_sale_service);
            img_status_sale_service.setImageResource(R.drawable.img_details_order_steps_04_sel);
        }
    }

}


