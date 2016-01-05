package com.putao.wd.me.service;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.service.adapter.ServiceChooseAdapter;
import com.putao.wd.me.service.view.ServiceChangeActivity;
import com.putao.wd.model.Order;
import com.putao.wd.model.OrderProduct;
import com.putao.wd.store.order.adapter.OrdersAdapter;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * 售后列表
 * Created by wangou on 15/12/7.
 */
public class ServiceChooseActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener{

    @Bind(R.id.rv_service_able)
    BasicRecyclerView rv_service_able;//可以售后列表
    @Bind(R.id.rv_service_unable)
    BasicRecyclerView rv_service_unable;//不可售后列表
    @Bind(R.id.btn_service_refund)
    Button btn_service_refund;//退款按钮
    @Bind(R.id.btn_service_back)
    Button btn_service_back;//退货按钮
    @Bind(R.id.btn_service_change)
    Button btn_service_change;//换货按钮

    public static final String SERVICE_PRODUCT = "service_product";

    private ServiceChooseAdapter adapterAble;
    private OrdersAdapter adapterUnable;
    private String mOrderId;
    private Order mOrder;
    private List<OrderProduct> mAbleProduct;
    private List<OrderProduct> mFocusProducts;
    public static final String ORDER_ID = "orderId";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_choose;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        initData();
        addListener();
    }

    private void addListener() {
        btn_service_refund.setOnClickListener(this);
        btn_service_change.setOnClickListener(this);
        btn_service_back.setOnClickListener(this);
    }

    private void initView() {
        adapterUnable = new OrdersAdapter(mContext, null);
        adapterAble = new ServiceChooseAdapter(mContext, null);
        rv_service_unable.setAdapter(adapterUnable);
        rv_service_able.setAdapter(adapterAble);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mAbleProduct = new ArrayList<>();
        mFocusProducts = new ArrayList<>();
        mOrderId = args.getString(ORDER_ID);
        networkRequest(OrderApi.orderAfterSale(mOrderId),
                new SimpleFastJsonCallback<Order>(Order.class, loading) {
                    @Override
                    public void onSuccess(String url, Order result) {
                        Logger.d(result.toString());
                        initView();
                        mOrder = result;
                        adapterAble.clear();
                        adapterUnable.clear();
                        chooseData();
                        loading.dismiss();
                    }
                });
    }

    private void chooseData() {
        for (OrderProduct product : mOrder.getProduct()) {
            int allowService = product.getAllowService();
            int allowServiceQuantity = product.getAllowServiceQuantity();
            if (allowServiceQuantity < 1 || allowService < 1 || allowService > 7) {
                adapterUnable.add(product);
                continue;
            }
            mAbleProduct.add(product);
            adapterAble.add(product);
        }
    }


    /**
     * 申请售后
     */
    @Subcriber(tag = ServiceChooseAdapter.PRODUCT_CHOOSE)
    public void changeBtn(Bundle bundle) {
        boolean refund = true;
        boolean back = true;
        boolean change = true;
        int position = bundle.getInt(ServiceChooseAdapter.PRODUCT_CHOOSE_POSITION);
        boolean isFocus = bundle.getBoolean(ServiceChooseAdapter.PRODUCT_CHOOSE_ISFOCUS);
        if (isFocus) {
            mFocusProducts.add(mAbleProduct.get(position));
        } else {
            mFocusProducts.remove(mAbleProduct.get(position));
        }
        if (mFocusProducts.size()==0){
            btn_service_refund.setEnabled(false);
            btn_service_back.setEnabled(false);
            btn_service_change.setEnabled(false);
            return;
        }
        for (OrderProduct product : mFocusProducts) {
            int allowService = product.getAllowService();
            switch (allowService){
                case 1:
                    change = change && true;
                    back = back && false;
                    refund = refund && false;
                    break;
                case 2:
                    change = change && false;
                    back = back && true;
                    refund = refund && false;
                    break;
                case 3:
                    change = change && true;
                    back = back && true;
                    refund = refund && false;
                    break;
                case 4:
                    refund = refund && true;
                    change = change && false;
                    back = back && false;
                    break;
                case 5:
                    back = back && false;
                    refund = refund && true;
                    change = change && true;
                    break;
                case 6:
                    change = change && false;
                    back = back && true;
                    refund = refund && true;
                    break;
                case 7:
                    refund = refund && true;
                    back = back && true;
                    change = change && true;
                    break;
            }
        }
        btn_service_refund.setEnabled(refund);
        btn_service_back.setEnabled(back);
        btn_service_change.setEnabled(change);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString(ServiceRefundActivity.ORDER_ID,mOrderId);
        bundle.putSerializable(SERVICE_PRODUCT, (Serializable)mFocusProducts);
        switch (v.getId()){
            case R.id.btn_service_refund:
                startActivity(ServiceRefundActivity.class,bundle);
                break;
            case R.id.btn_service_back:
                startActivity(ServiceBackActivity.class,bundle);
                break;
            case R.id.btn_service_change:
                startActivity(ServiceChangeActivity.class,bundle);
                break;
        }
    }
}
