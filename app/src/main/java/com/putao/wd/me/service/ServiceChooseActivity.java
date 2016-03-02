package com.putao.wd.me.service;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.service.adapter.ServiceChooseAdapter;
import com.putao.wd.model.Order;
import com.putao.wd.model.OrderProduct;
import com.putao.wd.store.order.adapter.OrdersAdapter;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * 售后列表
 * Created by wangou on 15/12/7.
 */
public class ServiceChooseActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener, OnItemClickListener<OrderProduct> {

    public final String PRODUCT_CHOOSE = "product_choose";
    public String PRODUCT_CHOOSE_POSITION = "product_choose_position";
    public String PRODUCT_CHOOSE_ISFOCUS = "product_choose_isfocus";

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
    @Bind(R.id.v_margin)
    View v_margin;//间距
    @Bind(R.id.rl_service_able_icon)
    RelativeLayout rl_service_able_icon;
    @Bind(R.id.rl_service_unable_icon)
    RelativeLayout rl_service_unable_icon;

    public static final String SERVICE_PRODUCT = "service_product";

    private ServiceChooseAdapter adapterAble;
    private OrdersAdapter adapterUnable;
    private String mOrderId;
    private Order mOrder;
    private List<OrderProduct> mAbleProduct;
    private List<OrderProduct> mFocusProducts;
    public static final String ORDER_ID = "orderId";
    public static final String SERVICE_WAY = "service_way";


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
        btn_service_refund.setClickable(false);
        btn_service_back.setClickable(false);
        btn_service_change.setClickable(false);
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
        if (0 != adapterAble.getItemCount()) {
            rl_service_able_icon.setVisibility(View.VISIBLE);
            v_margin.setVisibility(View.VISIBLE);
        }
        if (0 != adapterUnable.getItemCount()) {
            rl_service_unable_icon.setVisibility(View.VISIBLE);
        }
        rv_service_able.setOnItemClickListener(this);
    }


    /**
     * 申请售后
     */
    @Subcriber(tag = PRODUCT_CHOOSE)
    public void changeBtn(Bundle bundle) {
        boolean refund = true;
        boolean back = true;
        boolean change = true;
        int position = bundle.getInt(PRODUCT_CHOOSE_POSITION);
        boolean isFocus = bundle.getBoolean(PRODUCT_CHOOSE_ISFOCUS);
        if (isFocus) {
            mFocusProducts.add(mAbleProduct.get(position));
        } else {
            mFocusProducts.remove(mAbleProduct.get(position));
        }
        if (mFocusProducts.size() == 0) {
            btn_service_refund.setClickable(false);
            btn_service_refund.setBackgroundResource(R.drawable.text_userinfo_limit_shape);
            btn_service_back.setClickable(false);
            btn_service_back.setBackgroundResource(R.drawable.text_userinfo_limit_shape);
            btn_service_change.setClickable(false);
            btn_service_change.setBackgroundResource(R.drawable.text_userinfo_limit_shape);
            return;
        }
        for (OrderProduct product : mFocusProducts) {
            int allowService = product.getAllowService();
            switch (allowService) {
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
        btn_service_refund.setClickable(refund);
        btn_service_refund.setBackgroundResource(refund ? R.drawable.btn_order_express_selector : R.drawable.text_userinfo_limit_shape);
        btn_service_back.setEnabled(back);
        btn_service_back.setBackgroundResource(back ? R.drawable.btn_order_express_selector : R.drawable.text_userinfo_limit_shape);
        btn_service_change.setEnabled(change);
        btn_service_change.setBackgroundResource(change ? R.drawable.btn_order_express_selector : R.drawable.text_userinfo_limit_shape);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString(ServiceRefundActivity.ORDER_ID, mOrderId);
        bundle.putSerializable(SERVICE_PRODUCT, (Serializable) mFocusProducts);
        switch (v.getId()) {
            case R.id.btn_service_refund:
                bundle.putString(SERVICE_WAY, 3 + "");
                startActivity(ServiceRefundActivity.class, bundle);
                break;
            case R.id.btn_service_back:
                bundle.putString(SERVICE_WAY, 2 + "");
                startActivity(ServiceChangeBackActivity.class, bundle);
                break;
            case R.id.btn_service_change:
                bundle.putString(SERVICE_WAY, 1 + "");
                startActivity(ServiceChangeBackActivity.class, bundle);
                break;
        }
    }


    @Override
    public void onItemClick(OrderProduct orderProduct, int position) {
        orderProduct.setIsSelect(!orderProduct.isSelect());
        adapterAble.notifyItemChanged(position);
        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_CHOOSE_POSITION, position);
        bundle.putBoolean(PRODUCT_CHOOSE_ISFOCUS, orderProduct.isSelect());
        EventBusHelper.post(bundle, PRODUCT_CHOOSE);
    }
}
