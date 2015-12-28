package com.putao.wd.store.order;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.address.AddressListActivity;
import com.putao.wd.model.OrderConfirm;
import com.putao.wd.model.OrderConfirmProduct;
import com.putao.wd.store.cashier.CashierActivity;
import com.putao.wd.store.invoice.InvoiceInfoActivity;
import com.putao.wd.store.order.adapter.WriteOrderAdapter;
import com.putao.wd.store.shopping.ShoppingCarActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.sticky.StickyHeaderLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 填写订单
 * Created by wangou on 2015/12/08.
 */
public class WriteOrderActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.sticky_layout)
    StickyHeaderLayout sticky_layout;
    @Bind(R.id.iv_reapte_picbar)
    ImageView iv_reapte_picbar;//分割图片
    @Bind(R.id.stickyHeaderLayout_scrollable)
    BasicRecyclerView rv_orders;//订单列表
    @Bind(R.id.tv_sum)
    TextView tv_sum;//总金额

    private WriteOrderAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_order;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        sticky_layout.canScrollView();
        ImageUtils.fillXInImageView(mContext, iv_reapte_picbar, BitmapFactory.decodeResource(getResources(), R.drawable.img_cart_lace_stuff));

        Bundle bundle = getIntent().getExtras();
        networkRequest(StoreApi.orderConfirm(bundle.getString(ShoppingCarActivity.BUY_TYPE), "9|11|12|13|14"),
                new SimpleFastJsonCallback<OrderConfirm>(OrderConfirm.class, loading) {
            @Override
            public void onSuccess(String url, OrderConfirm result) {
                Logger.w("填写订单 = " + result.toString());
                List<OrderConfirmProduct> products = result.getProduct();
                products.add(new OrderConfirmProduct());
                adapter = new WriteOrderAdapter(mContext, products);
                rv_orders.setAdapter(adapter);
                loading.dismiss();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

//    /**
//     * 取消订单
//     */
//    private void orderCancel() {
//        networkRequest(OrderApi.orderCancel(""), new SimpleFastJsonCallback<String>(String.class, loading) {
//            @Override
//            public void onSuccess(String url, String result) {
//                Logger.d(result.toString());
//            }
//        });
//    }
//
//    public static List<ShoppingCar> getTestData() {
//        List<ShoppingCar> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            ShoppingCar order = new ShoppingCar();
//            order.setTitle(i + "葡萄探索号.虚拟+现实儿童科技益智玩具");
//            order.setColor("塔塔紫");
//            order.setSize("均码");
//            order.setMoney("399.00");
//            order.setCount("2");
//            list.add(order);
//        }
//        ShoppingCar orderfooter = new ShoppingCar();
//        orderfooter.setSum_count("3");
//        orderfooter.setCarriage("399.00");
//        orderfooter.setSum("3666.00");
//        orderfooter.setSum_price("8888");
//        list.add(orderfooter);
//        return list;
//    }

    @OnClick({R.id.ll_receiving_address, R.id.ll_need_invoice, R.id.tv_submit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_receiving_address:
                startActivity(AddressListActivity.class);
                break;
            case R.id.ll_need_invoice:
                startActivity(InvoiceInfoActivity.class);
                break;
            case R.id.tv_submit:
                startActivity(CashierActivity.class);
                break;
        }
    }

//    /**
//     * 计算总金额
//     */
//    private void getTotalMoney() {
//        List<Order> items = adapter.getItems();
//
//        for (Order order : items) {
//
//        }
//
//    }

//    public void eventTotalMoney(String tag) {
//        tv_sum.setText("");
//    }

}
