package com.putao.wd.me.service;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.ServiceDto;
import com.putao.wd.dto.ServiceGoodsDto;
import com.putao.wd.dto.ServiceShipmentListItemDto;
import com.putao.wd.me.service.ServiceCommonState;
import com.putao.wd.me.service.ServiceShipmentDetailActivity;
import com.putao.wd.me.service.view.ServiceGoodsItem;
import com.putao.wd.me.service.view.ServiceShipmentListItem;
import com.putao.wd.model.Order;
import com.putao.wd.model.OrderProduct;
import com.putao.wd.model.ProductData;
import com.putao.wd.model.Service;
import com.putao.wd.store.order.adapter.OrdersAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.io.Serializable;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 订单详情
 * Created by wangou on 15/11/29.
 */
public class ServiceRefundActivity extends PTWDActivity<GlobalApplication> {

    public static final String KEY_ORDER = "service";
    public static final String ORDER_ID = "orderId";
    private Order mOrder;
    private String mOrderId;
    private String[] mItems;

    @Bind(R.id.img_status1)
    ImageView img_status1;
    @Bind(R.id.img_status2)
    ImageView img_status2;
    @Bind(R.id.img_status3)
    ImageView img_status3;
    @Bind(R.id.service_spinner)
    Spinner service_spinner;
    @Bind(R.id.rv_goods)
    BasicRecyclerView rv_goods;
    @Bind(R.id.tv_service_status)
    TextView tv_service_status;
    @Bind(R.id.v_status_waiting_pay)
    View v_status_waiting_pay;

    private ServiceDto serviceDto;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_refund_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        initData();
    }

    private void initView() {
        tv_service_status.setText("￥" + mOrder.getTotal_amount());
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItems);
        service_spinner.setAdapter(stringArrayAdapter);
        OrdersAdapter ordersAdapter = new OrdersAdapter(mContext, mOrder.getProduct());
        rv_goods.setAdapter(ordersAdapter);

    }

    private void initData() {
        mOrderId = args.getString(ORDER_ID);
        mItems = getResources().getStringArray(R.array.refund_spinnername);
        networkRequest(OrderApi.orderAfterSale(mOrderId),
                new SimpleFastJsonCallback<Order>(Order.class, loading) {
                    @Override
                    public void onSuccess(String url, Order result) {
                        Logger.d(result.toString());
                        mOrder = result;
                        initView();
                    }
                });
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        List<OrderProduct> products = mOrder.getProduct();
        String allProductId = "";
        Map<String, ProductData> productDataMap = new HashMap<>();
        int productReason = service_spinner.getSelectedItemPosition()+1;//理由
        for (OrderProduct product : products) {
            allProductId = allProductId + "," + product.getProduct_id();
            ProductData productData = new ProductData();
            productData.setProduct_id(product.getProduct_id());
            productData.setReason(productReason);
            productData.setDescription("");
            productData.setImage("");
            productData.setQuantity(product.getQuantity());
            productDataMap.put(product.getProduct_id()+"",productData);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(productDataMap);
        String str = jsonObject.toJSONString();

        networkRequest(OrderApi.orderSubmitAfterSale(mOrder.getId(), "3", "", allProductId.substring(1),jsonObject.toJSONString()),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        Logger.d(result.toString());
                        img_status1.setImageResource(R.drawable.img_details_refund_steps_01_sel);
                        v_status_waiting_pay.setBackgroundColor(Color.WHITE);
                        ToastUtils.showToastShort(mContext,"申请提交成功,请等待审核");
                        loading.dismiss();
                    }
                });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}