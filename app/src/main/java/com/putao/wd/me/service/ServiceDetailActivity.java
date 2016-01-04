package com.putao.wd.me.service;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Size;
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
import com.putao.wd.model.Express;
import com.putao.wd.model.ServiceList;
import com.putao.wd.model.ServiceOrderInfo;
import com.putao.wd.model.ServiceProduct;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.BasicRecyclerView;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

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
    private List<Express> express;
    private String serviceStatus;
    private String serviceId;


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
        serviceId = bundle.getString(KEY_SERVICE_ID);
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
        express = serviceList.getExpress();
        checkServiceType(serviceList.getService_type() + serviceList.getStatus());
        tv_service_no.setText(order_info.getOrder_sn());
        tv_order_purchase_time.setText(DateUtils.secondToDate(Integer.parseInt(serviceList.getCreate_time()), "yyyy-MM-dd HH:mm:ss"));
        tv_service_info.setText(serviceList.getDepiction());
        tv_service_order_status.setText(serviceStatus);
        if (null != express && express.size() > 0) {
            tv_no_shipment.setText(express.get(0).getExpress_message());
        }
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
    private void checkServiceType(String status) {
        switch (status) {
            case "11"://1换货
                selectStepDeal();
                btn_execute.setText("填写快递单号");
                break;
            case "12":
            case "14":
            case "15":
            case "16":
            case "17":
                selectStepExpress();
                break;
            case "13":
            case "18":
                selectStepComplete();
                break;
            case "21"://2退货
                selectStepDeal();
                btn_execute.setText("填写快递单号");
                break;
            case "22":
            case "24":
            case "25":
                selectStepExpress();
                break;
            case "23":
            case "28":
                selectStepComplete();
                break;
            case "31"://3退款
                setBackMoneyStep();
                selectStepDeal();
                btn_execute.setText("填写快递单号");
                break;
            case "33":
            case "38":
                setBackMoneyStepComplete();
                break;
        }
//        btn_execute.setVisibility(View.VISIBLE);
    }

    /**
     * 设置"处理中"选中
     */
    private void selectStepDeal() {
        v_status_waiting_shipment.setBackgroundResource(R.color.text_color_F5F5F5);
        img_status_waiting_shipment.setImageResource(R.drawable.img_details_as_steps_02_sel);
    }

    /**
     * 设置"物流"选中
     */
    private void selectStepExpress() {
        selectStepDeal();
        v_status_waiting_sign.setBackgroundResource(R.color.text_color_F5F5F5);
        img_status_waiting_sign.setImageResource(R.drawable.img_details_as_steps_03_sel);
        btn_execute.setVisibility(View.GONE);
    }

    /**
     * 设置"完成"选中
     */
    private void selectStepComplete() {
        selectStepExpress();
        v_status_sale_service.setBackgroundResource(R.color.text_color_F5F5F5);
        img_status_sale_service.setImageResource(R.drawable.img_details_as_steps_04_sel);
    }

    /**
     * 设置"退款"进度显示
     */
    private void setBackMoneyStep() {
        rl_fill_order_no.setVisibility(View.GONE);
        img_status_sale_service.setImageResource(R.drawable.img_details_refund_steps_03_nor);
    }

    /**
     * 设置"退款"进度完成
     */
    private void setBackMoneyStepComplete() {
        setBackMoneyStep();
        selectStepDeal();
        v_status_sale_service.setBackgroundResource(R.color.text_color_F5F5F5);
        img_status_sale_service.setImageResource(R.drawable.img_details_refund_steps_03_sel);
        btn_execute.setVisibility(View.GONE);
    }

    @OnClick({R.id.ll_shipment, R.id.btn_execute})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_shipment:// 查看物流信息
                Bundle bundle = new Bundle();
                bundle.putSerializable(ServiceShipmentDetailActivity.KEY_EXPRESS_INFO, (Serializable) express);
                startActivity(ServiceShipmentDetailActivity.class, bundle);
                break;
            case R.id.btn_execute:
                if ("取消申请".equals(btn_execute.getText())) {
                    showDialog();
                }else {
                    startActivity(ServiceExpressNumberActivity.class);
                }
                break;
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("确定取消")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        networkRequest(OrderApi.cancelService(serviceId), new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                ToastUtils.showToastShort(mContext, "取消售后");
                                startActivity(ServiceListActivity.class);
                                ActivityManager.getInstance().removeCurrentActivity();
                                loading.dismiss();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

}






