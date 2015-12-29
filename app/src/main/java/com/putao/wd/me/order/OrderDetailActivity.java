package com.putao.wd.me.order;

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
import com.putao.wd.me.order.adapter.OrderListAdapter;
import com.putao.wd.me.order.view.OrderGoodsItem;
import com.putao.wd.model.Order;
import com.putao.wd.model.OrderDetail;
import com.putao.wd.model.OrderProduct;
import com.putao.wd.store.order.adapter.OrdersAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 订单详情
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderDetailActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    public static final String KEY_ORDER = "order";
    public static final String KEY_ORDER_ID = "order_id";
    private static final String HINT1 = "请在24小时之内支付订单，逾期未付款，订单将自动关闭";
    private static final String HINT2 = "您可以在签收后15天内申请售后";
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
    @Bind(R.id.tv_order_no_text)
    TextView tv_order_no_text;
    @Bind(R.id.tv_order_no)
    TextView tv_order_no;
    @Bind(R.id.tv_order_purchase_time)
    TextView tv_order_purchase_time;
    @Bind(R.id.tv_order_status)
    TextView tv_order_status;
    @Bind(R.id.tv_order_cost)
    TextView tv_order_cost;
    @Bind(R.id.tv_order_cost_text)
    TextView tv_order_cost_text;
    @Bind(R.id.img_info_icon)
    ImageView img_info_icon;
    @Bind(R.id.tv_order_info)
    TextView tv_order_info;
    @Bind(R.id.rl_order_info)
    RelativeLayout rl_order_info;
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
    @Bind(R.id.ll_order_info)
    LinearLayout ll_order_info;
    @Bind(R.id.tv_goods_title)
    TextView tv_goods_title;
    @Bind(R.id.rv_goods)
    BasicRecyclerView rv_goods;
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
    @Bind(R.id.btn_order_cancel)
    Button btn_order_cancel;
    @Bind(R.id.btn_order_operation)
    Button btn_order_operation;
    @Bind(R.id.rl_bottom)
    RelativeLayout rl_bottom;
    @Bind(R.id.ll_receipt)
    LinearLayout ll_receipt;

    private Order orderDto;
    private OrdersAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        adapter = new OrdersAdapter(mContext, null);
        //获取order数据
        Intent intent = getIntent();
        if (intent != null) {
            orderDto = (Order) intent.getSerializableExtra(KEY_ORDER);
        }
        //初始化布局对象
        initComponent();
        getOrderDetail();
        //刷新界面
        refreshView();
    }


    /**
     * 订单详情
     */
    private void getOrderDetail() {
        networkRequest(OrderApi.getOrderDetail("367"), new SimpleFastJsonCallback<ArrayList<OrderDetail>>(OrderDetail.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<OrderDetail> result) {
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
        bundle.putString(OrderShipmentDetailActivity.KEY_ORDER_UUID, orderDto.getOrder_sn());
        startActivity(OrderShipmentDetailActivity.class, bundle);
    }

    private void initComponent() {

    }

    private void refreshView() {
        if (orderDto == null) return;
        //---------详情头----------//
        tv_order_no.setText(orderDto.getOrder_sn());//设置订单号
        tv_order_purchase_time.setText(DateUtils.secondToDate(Integer.parseInt(orderDto.getCreate_time()), "yyyy-MM-dd HH:mm:ss" + ""));//设置时间
        tv_order_status.setText(OrderCommonState.getOrderStatusShowString(orderDto.getOrder_status()));//设置订单状态
        tv_order_cost.setText("¥" + orderDto.getTotal_amount());//设置订单金额
        //---------收货信息----------//
        tv_customer_name.setText(orderDto.getConsignee());  //设置收货人
        tv_customer_address.setText(orderDto.getAddress());//设置收货地址
        tv_customer_phone.setText(orderDto.getMobile());//设置电话号码
        //---------支付与配送方式----------//
        tv_pay_method.setText("支付方式：" + orderDto.getPay_type());//设置支付方式
        tv_shipment_method.setText("配送方式：" + orderDto.getDeliver_type());//设置配送方式
        //---------发票区域----------//
        ll_receipt.setVisibility(orderDto.getNeed_invoice() == 1 ? View.VISIBLE : View.GONE);
        tv_receipt_type.setText("发票类型："+orderDto.getInvoice_type());//设置发票类型
        tv_receipt_head.setText("发票抬头：" +orderDto.getInvoice_title());//设置发票抬头
        tv_receipt_content.setText("发票内容："+ orderDto.getInvoice_content());//设置发票内容
        //---------商品数量与费用----------//
        tv_goods_total_number.setText(orderDto.getTotal_quantity());//设置商品数量
        tv_cost.setText("¥" + orderDto.getProduct_money()); //设置货物费用
        tv_shipment_fee.setText("¥" + orderDto.getExpress_money());//设置运费
        tv_total_cost.setText("¥" + orderDto.getTotal_amount());//设置总金额
        rv_goods.setAdapter(adapter);
        adapter.addAll(orderDto.getProduct());


/*        List<OrderProduct> goodsList = orderDto.getProduct();
        ll_goods.removeAllViews();
        int goodsTotalNumber = 0;
        int goodsNumber = goodsList.size();
        for (int i = 0; i < goodsNumber; i++) {
            OrderGoodsItem goodsItem = new OrderGoodsItem(this, goodsList.get(i));
            ll_goods.addView(goodsItem);
//            goodsTotalNumber = goodsTotalNumber + goodsList.get(i).getQuantity();
        }*/
        setOrderStatus(orderDto.getOrderStatusID());
    }

    /**
     * 设置订单状态的文字和顶部进度条显示
     *
     * @param status
     */
    private void setOrderStatus(int status) {
        String statusStr = OrderCommonState.getOrderStatusShowString(status);
        TextView tv_order_status = (TextView) findViewById(R.id.tv_order_status);
        tv_order_status.setText(statusStr);
        switch (status) {
            case OrderCommonState.ORDER_PAY_WAIT:
                setProgress1();
                tv_order_info.setText(HINT1);//设置商品信息
                break;
            case OrderCommonState.ORDER_WAITING_SHIPMENT:
                setProgress2();
                tv_order_info.setText(HINT2);//设置商品信息
                break;
            case OrderCommonState.ORDER_GOOD_PREPARE:
                setProgress2();
                tv_order_info.setText(HINT2);//设置商品信息
                break;
            case OrderCommonState.ORDER_WAITING_SIGN:
                setProgress3();
                tv_order_info.setText(HINT2);//设置商品信息
                break;
            case OrderCommonState.ORDER_SALE_SERVICE:
                setProgress4();
                tv_order_info.setText(HINT2);//设置商品信息
                break;
            case OrderCommonState.ORDER_FINISH:
                setProgress4();
                tv_order_info.setText(HINT2);//设置商品信息
                break;
            case OrderCommonState.ORDER_CANCLED:
                setProgress1();
                rl_order_info.setVisibility(View.GONE);
                rl_bottom.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_AFTER_SALE:
                rl_order_info.setVisibility(View.GONE);
                rl_bottom.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_REFUND_FINISH:
                tv_order_info.setVisibility(View.GONE);
                rl_order_info.setVisibility(View.GONE);
                rl_bottom.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_EXCEPTION:
                rl_order_info.setVisibility(View.GONE);
                rl_bottom.setVisibility(View.GONE);
                break;

        }
    }

    private void setProgress4() {
        setProgress3();
        View v_status_sale_service = findViewById(R.id.v_status_sale_service);
        v_status_sale_service.setBackgroundColor(0xffffffff);
        ImageView img_status_sale_service = (ImageView) findViewById(R.id.img_status_sale_service);
        img_status_sale_service.setImageResource(R.drawable.img_details_order_steps_04_sel);
    }

    private void setProgress3() {
        setProgress2();
        View v_status_waiting_sign = findViewById(R.id.v_status_waiting_sign);
        v_status_waiting_sign.setBackgroundColor(0xffffffff);
        ImageView img_status_waiting_sign = (ImageView) findViewById(R.id.img_status_waiting_sign);
        img_status_waiting_sign.setImageResource(R.drawable.img_details_order_steps_03_sel);
    }

    private void setProgress2() {
        setProgress1();
        View v_status_waiting_shipment = findViewById(R.id.v_status_waiting_shipment);
        v_status_waiting_shipment.setBackgroundColor(0xffffffff);
        ImageView img_status_waiting_shipment = (ImageView) findViewById(R.id.img_status_waiting_shipment);
        img_status_waiting_shipment.setImageResource(R.drawable.img_details_order_steps_02_sel);
    }

    private void setProgress1() {
        View v_status_waiting_pay = findViewById(R.id.v_status_waiting_pay);
        v_status_waiting_pay.setBackgroundColor(0xffffffff);
        ImageView img_status_waiting_pay = (ImageView) findViewById(R.id.img_status_waiting_pay);
        img_status_waiting_pay.setImageResource(R.drawable.img_details_order_steps_01_sel);
    }
}


