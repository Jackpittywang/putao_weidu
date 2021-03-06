package com.putao.wd.pt_me.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.OrderApi;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ProductStatus;
import com.putao.wd.pt_me.order.adapter.ShipmentAdapter;
import com.putao.wd.pt_me.service.ServiceChooseActivity;
import com.putao.wd.model.Express;
import com.putao.wd.model.OrderDetail;
import com.putao.wd.model.OrderProduct;
import com.putao.wd.pt_store.order.adapter.OrdersAdapter;
import com.putao.wd.pt_store.product.ProductDetailActivity;
import com.putao.wd.pt_store.product.ProductDetailV2Activity;
import com.putao.wd.util.AlipayHelper;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 订单详情
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderDetailActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    public static final String KEY_ORDER = "order";
    public static final String KEY_ORDER_ID = "order_id";
    public static final String CANCEL_ORDER = "cancel_order";
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
    @Bind(R.id.btn_order_left)
    Button btn_order_left;
    @Bind(R.id.btn_order_right)
    Button btn_order_right;
    @Bind(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @Bind(R.id.ll_receipt)
    LinearLayout ll_receipt;
    @Bind(R.id.rv_goods)
    BasicRecyclerView rv_goods;
    @Bind(R.id.rv_shipment)
    BasicRecyclerView rv_shipment;

    private OrderDetail mOrderDetail;
    private OrdersAdapter mAdapter;
    private ShipmentAdapter mShipmentAdapter;
    String mOrderId;
    public static final String ORDER_DETAIL = "order_detail";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mAdapter = new OrdersAdapter(mContext, null);
        mShipmentAdapter = new ShipmentAdapter(mContext, null);
        //获取order数据
        Intent intent = getIntent();
        mOrderId = args.getString(KEY_ORDER);
        //初始化布局对象
        initComponent();
        getOrderDetail();
    }


    /**
     * 订单详情
     */
    private void getOrderDetail() {
        networkRequest(OrderApi.getOrderDetail(mOrderId), new SimpleFastJsonCallback<OrderDetail>(OrderDetail.class, loading) {
            @Override
            public void onSuccess(String url, OrderDetail result) {
                Logger.d(result.toString());
                mOrderDetail = result;
                refreshView();
                loading.dismiss();
            }
        });
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    private void initComponent() {

    }

    private void refreshView() {
        if (mOrderDetail == null) return;
        //---------详情头----------//
        tv_order_no.setText(mOrderDetail.getOrder_sn());//设置订单号
        tv_order_purchase_time.setText(DateUtils.secondToDate(Integer.parseInt(mOrderDetail.getCreate_time()), "yyyy-MM-dd HH:mm:ss" + ""));//设置时间
        tv_order_status.setText(OrderCommonState.getOrderStatusShowString(mOrderDetail.getOrder_status()));//设置订单状态
        tv_order_cost.setText("¥" + mOrderDetail.getTotal_amount());//设置订单金额
        //---------收货信息----------//
        tv_customer_name.setText(mOrderDetail.getConsignee());  //设置收货人
        tv_customer_address.setText(mOrderDetail.getProvince() + " " + mOrderDetail.getCity() + " " + mOrderDetail.getAddress());//设置收货地址
        tv_customer_phone.setText(mOrderDetail.getMobile());//设置电话号码
        //---------支付与配送方式----------//
        tv_pay_method.setText("支付方式：" + mOrderDetail.getPay_type());//设置支付方式
        tv_shipment_method.setText("配送方式：" + mOrderDetail.getDeliver_type());//设置配送方式
        //---------发票区域----------//
        ll_receipt.setVisibility(mOrderDetail.getNeed_invoice() == 1 ? View.VISIBLE : View.GONE);
        tv_receipt_type.setText("发票类型：" + mOrderDetail.getInvoice_type());//设置发票类型
        tv_receipt_head.setText("发票抬头：" + mOrderDetail.getInvoice_title());//设置发票抬头
        tv_receipt_content.setText("发票内容：" + mOrderDetail.getInvoice_content());//设置发票内容
        //---------商品数量与费用----------//
        tv_goods_total_number.setText(mOrderDetail.getTotal_quantity());//设置商品数量
        tv_cost.setText("¥" + (!TextUtils.isEmpty(mOrderDetail.getProduct_money()) ? mOrderDetail.getProduct_money() : "0.00")); //设置货物费用
        tv_shipment_fee.setText("¥" + (!TextUtils.isEmpty(mOrderDetail.getExpress_money()) ? mOrderDetail.getExpress_money() : "0.00"));//设置运费
        tv_total_cost.setText("¥" + (!TextUtils.isEmpty(mOrderDetail.getTotal_amount()) ? mOrderDetail.getTotal_amount() : "0.00"));//设置总金额
        //设置物流信息
        if (mOrderDetail.getExpress() != null && mOrderDetail.getExpress().size() > 0) {
            tv_no_shipment.setVisibility(View.GONE);
            rv_shipment.setAdapter(mShipmentAdapter);
            mShipmentAdapter.addAll(mOrderDetail.getExpress());
            //详情页
            rv_shipment.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(Serializable serializable, int position) {
                    checkShipment(position);
                }
            });
        }
        rv_goods.setAdapter(mAdapter);
        rv_goods.setOnItemClickListener(new OnItemClickListener<OrderProduct>() {
            @Override
            public void onItemClick(OrderProduct product, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(ProductDetailActivity.BUNDLE_PRODUCT, product);
//                bundle.putSerializable(ProductDetailActivity.BUNDLE_IS_DETAIL, true);
//                startActivity(ProductDetailActivity.class, bundle);
                IsProductDetail(product.getProduct_id(), product);
            }
        });
        mAdapter.addAll(mOrderDetail.getProduct());
        setOrderStatus(mOrderDetail.getOrderStatusID());
    }

    /**
     * 判断是否下架以及是否是精品页面
     */
    public void IsProductDetail(final String product_id, final OrderProduct product) {
        final Bundle bundle = new Bundle();
        networkRequest(StoreApi.getProductStatus(product_id), new SimpleFastJsonCallback<ProductStatus>(ProductStatus.class, loading) {
            @Override
            public void onSuccess(String url, ProductStatus result) {
                int status = result.getStatus();
                int has_special = result.getHas_special();
                if (result.equals("") && result == null || status == 0) {
                    bundle.putSerializable(AccountConstants.Bundle.BUNDLE_STORE_STATUS, status);
                    startActivity(ProductDetailV2Activity.class, bundle);
                } else {
                    //判断是否是精品(1.精品，0.非精品)
                    if (has_special == 1) {//精品页面：ProductDetailActivity
                        YouMengHelper.onEvent(mContext, YouMengHelper.CreatorHome_mall_detail);
                        bundle.putSerializable(ProductDetailV2Activity.BUNDLE_PRODUCT, product);
                        bundle.putSerializable(ProductDetailV2Activity.BUNDLE_IS_DETAIL, true);
                        bundle.putSerializable(AccountConstants.Bundle.BUNDLE_STORE_STATUS, status);
                        bundle.putSerializable(ProductDetailV2Activity.BUNDLE_PRODUCT_NUM, "order");
                        startActivity(ProductDetailV2Activity.class, bundle);
                    } else if (has_special == 0) {//显示h5(非精品页面：ProductDetailV2Activity)
                        YouMengHelper.onEvent(mContext, YouMengHelper.CreatorHome_mall_detail);
                        bundle.putSerializable(ProductDetailActivity.BUNDLE_PRODUCT_NUM, "order");
                        bundle.putSerializable(ProductDetailActivity.PRODUCT_ID, product_id);
                        startActivity(ProductDetailActivity.class, bundle);
                    }
                }
            }

            @Override
            public void onFinish(String url, boolean isSuccess, String msg) {
                super.onFinish(url, isSuccess, msg);
                if (msg.equals("商品不存在")) {
                    bundle.putSerializable(AccountConstants.Bundle.BUNDLE_STORE_ISHAVE, true);
                    startActivity(ProductDetailV2Activity.class, bundle);
                }
            }
        });
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
            case OrderCommonState.ORDER_PAY_WAIT://待支付
                setProgress1();
                ll_bottom.setVisibility(View.VISIBLE);
                btn_order_left.setVisibility(View.VISIBLE);
                btn_order_right.setVisibility(View.VISIBLE);
                tv_order_info.setText(HINT1);//设置商品信息
                btn_order_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(mOrderDetail.getId());
                    }
                });
                btn_order_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Bundle bundle = new Bundle();
                        bundle.putString(PayActivity.BUNDLE_ORDER_ID, mOrderDetail.getId());
                        bundle.putString(PayActivity.BUNDLE_ORDER_SN, mOrderDetail.getOrder_sn());
                        bundle.putString(PayActivity.BUNDLE_ORDER_PRICE, mOrderDetail.getTotal_amount());
                        bundle.putString(PayActivity.BUNDLE_ORDER_DATE, mOrderDetail.getCreate_time());
                        startActivity(PayActivity.class, bundle);*/
                        final AlipayHelper alipayHelper = new AlipayHelper();
                        alipayHelper.setOnAlipayCallback(new AlipayHelper.OnAlipayCallback() {
                            @Override
                            public void onPayResult(boolean isSuccess, String msg) {
                                if (isSuccess) {

                                } else {
                                    ToastUtils.showToastShort(mContext, "支付失败");
                                }
                            }

                            @Override
                            public void onPayVerify(String msg) {
                                ToastUtils.showToastShort(mContext, msg);
                            }

                            @Override
                            public void onPayCancel(String msg) {
                                ToastUtils.showToastShort(mContext, "检查结果为：" + msg);
                            }
                        });
                        networkRequest(StoreApi.aliPay(mOrderDetail.getId()), new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                if (!StringUtils.isEmpty(result)) {
                                    JSONObject object = JSON.parseObject(result);
                                    String orderInfo = object.getString("code");
                                    if (StringUtils.isEmpty(orderInfo)) {
                                        ToastUtils.showToastShort(mContext, "支付失败");
                                        return;
                                    }
                                    alipayHelper.pay((Activity) mContext, orderInfo);
                                } else {
                                    ToastUtils.showToastLong(mContext, "无法支付");
                                }
                                loading.dismiss();
                            }
                        });
                    }
                });
                break;
            case OrderCommonState.ORDER_WAITING_SHIPMENT://待发货
                setProgress2();
                btn_order_left.setText("申请退款");
                ll_bottom.setVisibility(View.VISIBLE);
                btn_order_left.setVisibility(View.VISIBLE);
                tv_order_info.setText(HINT2);//设置商品信息
                btn_order_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ServiceChooseActivity.ORDER_ID, mOrderDetail.getId());
                        startActivity(ServiceChooseActivity.class, bundle);
                    }
                });
                break;
            case OrderCommonState.ORDER_GOOD_PREPARE:
                setProgress2();
                tv_order_info.setText(HINT2);//设置商品信息
                break;
            case OrderCommonState.ORDER_WAITING_SIGN://已发货
                setProgress3();
                ll_bottom.setVisibility(View.VISIBLE);
                btn_order_left.setVisibility(View.VISIBLE);
                btn_order_left.setText("查看物流");
                tv_order_info.setText(HINT2);//设置商品信息
                btn_order_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkShipment(0);
                    }
                });
                break;
            case OrderCommonState.ORDER_SALE_SERVICE:
                setProgress4();
                tv_order_info.setText(HINT2);//设置商品信息
                break;


            case OrderCommonState.ORDER_FINISH://已完成
                setProgress4();
                ll_bottom.setVisibility(View.VISIBLE);
                btn_order_left.setVisibility(View.VISIBLE);
                tv_order_info.setText(HINT2);//设置商品信息
                btn_order_left.setText("申请售后");
                btn_order_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ServiceChooseActivity.ORDER_ID, mOrderDetail.getId());
                        startActivity(ServiceChooseActivity.class, bundle);
                    }
                });
                break;

            case OrderCommonState.ORDER_CANCLED://已取消
                rl_order_info.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_AFTER_SALE://正在申请售后
                rl_order_info.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_REFUND_FINISH:
                tv_order_info.setVisibility(View.GONE);
                rl_order_info.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_EXCEPTION:
                rl_order_info.setVisibility(View.GONE);
                break;
        }
        if (3 == mOrderDetail.getShipping_status()) {
            tv_order_status.setText("退签");
            btn_order_left.setText("申请售后");
        }
    }

    @OnClick({R.id.ll_shipment})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_shipment:// 查看物流信息
                checkShipment(0);
                break;
        }
    }

    private void checkShipment(int position) {
        if (null == mOrderDetail.getExpress() || mOrderDetail.getExpress().size() == 0) {
            new AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setMessage("没有物流信息")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loading.dismiss();
                        }
                    })
                    .show();
        } else {
            Bundle bundle = new Bundle();
            ArrayList<Express> expresses = mOrderDetail.getExpress();
            bundle.putSerializable(OrderShipmentDetailActivity.EXPRESS, expresses);
            bundle.putInt(OrderShipmentDetailActivity.PACKAGINDEX, position);
            startActivity(OrderShipmentDetailActivity.class, bundle);
        }
    }


    /**
     * 取消订单dialog
     */
    private void showDialog(final String orderId) {
        new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("确定取消")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        networkRequest(OrderApi.orderCancel(orderId), new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                EventBusHelper.post(mOrderDetail, CANCEL_ORDER);
                                IndexActivity.isNotRefreshUserInfo = false;
                                finish();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
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

