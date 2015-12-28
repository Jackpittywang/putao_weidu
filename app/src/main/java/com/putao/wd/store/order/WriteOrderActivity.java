package com.putao.wd.store.order;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.MathUtils;
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
    @Bind(R.id.tv_Invoice_type)
    TextView tv_Invoice_type;
    @Bind(R.id.tv_Invoice_content)
    TextView tv_Invoice_content;
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
        networkRequest(StoreApi.orderConfirm(bundle.getString(ShoppingCarActivity.BUY_TYPE), "11|13"),
                new SimpleFastJsonCallback<OrderConfirm>(OrderConfirm.class, loading) {
                    @Override
                    public void onSuccess(String url, OrderConfirm result) {
                        Logger.w("填写订单 = " + result.toString());
                        adapter = new WriteOrderAdapter(mContext, getLastItem(result));
                        rv_orders.setAdapter(adapter);
                        loading.dismiss();
                    }
                });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 向适配器添加最后一个item，包含商品总价等信息
     */
    @NonNull
    private List<OrderConfirmProduct> getLastItem(OrderConfirm result) {
        List<OrderConfirmProduct> products = result.getProduct();
        OrderConfirmProduct product = new OrderConfirmProduct();
        String totalPrice = "0";
        int totalQt = 0;
        for (OrderConfirmProduct confirmProduct : products) {
            totalPrice = MathUtils.add(totalPrice, MathUtils.multiplication(confirmProduct.getPrice(), confirmProduct.getQt()));
            totalQt += Integer.parseInt(confirmProduct.getQt());
        }
        product.setTotalPrice(totalPrice);
        product.setTotalQt(totalQt);
        product.setTotalFee(result.getShipping_fee());
        products.add(product);
        return products;
    }

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

    @Subcriber(tag = InvoiceInfoActivity.EVENT_INVOICE)
    public void eventInvoice(List<String> invoiceInfo) {
        if (!invoiceInfo.get(0).equals(InvoiceInfoActivity.INVOICE_NEEDNOT)) {
            tv_Invoice_type.setText(invoiceInfo.get(1));
            tv_Invoice_content.setText(invoiceInfo.get(2));
            tv_Invoice_content.setVisibility(View.VISIBLE);
        }else {
            tv_Invoice_type.setText(InvoiceInfoActivity.INVOICE_NEEDNOT);
            tv_Invoice_content.setVisibility(View.GONE);
        }
    }
}
