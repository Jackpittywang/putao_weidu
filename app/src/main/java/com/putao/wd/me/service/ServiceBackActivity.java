package com.putao.wd.me.service;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.SelectPopupWindow;
import com.putao.wd.me.order.adapter.OrderListAdapter;
import com.putao.wd.me.service.ServiceRefundActivity;
import com.putao.wd.me.service.adapter.BackListAdapter;
import com.putao.wd.me.service.adapter.ServiceChooseAdapter;
import com.putao.wd.model.Order;
import com.putao.wd.model.OrderProduct;
import com.putao.wd.store.order.adapter.OrdersAdapter;
import com.putao.wd.store.pay.PayActivity;
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
public class ServiceBackActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    @Bind(R.id.tv_service_money)
    TextView tv_service_money;//退货金额
    @Bind(R.id.rv_service_back_list)
    BasicRecyclerView rv_service_back_list;//售后列表
    @Bind(R.id.rl_back_detail)
    RelativeLayout rl_back_detail;

    private final int ALBUM_REQCODE = 1;
    private final int CAMERA_REQCODE = 2;
    private SelectPopupWindow mSelectPopupWindow;
    private BackListAdapter adapter;
    private List<OrderProduct> mFocusProducts;
    public static final String ORDER_ID = "orderId";
    private Order mOrder;
    private String mOrderId;
    ArrayList<OrderProduct> mProducts;
    private String[] mItems;

    private double totalPrice;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_back_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        initData();
        addListener();
    }

    private void addListener() {
    }

    private void initView() {
        tv_service_money.setText("￥" + totalPrice);
        BackListAdapter backListAdapter = new BackListAdapter(mContext, mProducts);
        rv_service_back_list.setAdapter(backListAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mOrderId = args.getString(ORDER_ID);
        mProducts = (ArrayList<OrderProduct>) args.getSerializable(ServiceChooseActivity.SERVICE_PRODUCT);
        mItems = getResources().getStringArray(R.array.back_spinnername);
        totalPrice = 0;
        for (OrderProduct product : mProducts) {
            double price = Double.parseDouble(product.getPrice());
            int quantity = Integer.parseInt(product.getQuantity());
            totalPrice += price * quantity;
        }
        initView();
    }

    private void chooseData() {
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Override
    public void onClick(View v) {

    }

    @Subcriber(tag = BackListAdapter.CHOOSE_IMAGE)
    public void chooseImage(String str) {
        mSelectPopupWindow = new SelectPopupWindow(mContext) {
            @Override
            public void onFirstClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQCODE);
            }
            @Override
            public void onSecondClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, ALBUM_REQCODE);
            }
        };
        mSelectPopupWindow.show(rl_back_detail);
    }
}
