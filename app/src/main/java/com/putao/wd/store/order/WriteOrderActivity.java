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
import com.putao.wd.me.address.adapter.AddressAdapter;
import com.putao.wd.model.OrderConfirm;
import com.putao.wd.model.OrderConfirmProduct;
import com.putao.wd.model.OrderSubmitReturn;
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
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_address)
    TextView tv_address;
    @Bind(R.id.tv_phone)
    TextView tv_phone;
    @Bind(R.id.tv_Invoice_type)
    TextView tv_Invoice_type;
    @Bind(R.id.tv_Invoice_content)
    TextView tv_Invoice_content;
    @Bind(R.id.stickyHeaderLayout_scrollable)
    BasicRecyclerView rv_orders;//订单列表
    @Bind(R.id.tv_sum)
    TextView tv_sum;//总金额


    private WriteOrderAdapter adapter;
    private String pid;
    private String addressId;
    private String consignee = "";
    private String mobile = "";
    private String tel = "";
    private String need_invoice = "";
    private String invoice_type = "";
    private String invoice_title = "";
    private String invoice_content = "";


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
        pid = bundle.getString(ShoppingCarActivity.PRODUCT_ID);
        Logger.w("pid = " + pid);
        networkRequest(StoreApi.orderConfirm("2", pid, ""),
                new SimpleFastJsonCallback<OrderConfirm>(OrderConfirm.class, loading) {
                    @Override
                    public void onSuccess(String url, OrderConfirm result) {
                        Logger.w("填写订单 = " + result.toString());
                        if (null != result) {
                            adapter = new WriteOrderAdapter(mContext, getLastItem(result));
                            rv_orders.setAdapter(adapter);
                            addressId = result.getAddress().getId();
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
     * 向适配器添加最后一个item，包含商品总价等信息
     */
    private List<OrderConfirmProduct> getLastItem(OrderConfirm result) {
        List<OrderConfirmProduct> products = result.getProduct();
        OrderConfirmProduct product = new OrderConfirmProduct();
        String totalPrice = "0";
        int totalQt = 0;
        if (products.size() == 1) {
            totalPrice = MathUtils.multiplication(products.get(0).getPrice(), products.get(0).getQt());
            totalQt = Integer.parseInt(products.get(0).getQt());
        } else {
            for (OrderConfirmProduct confirmProduct : products) {
                totalPrice = MathUtils.add(totalPrice, MathUtils.multiplication(confirmProduct.getPrice(), confirmProduct.getQt()));
                totalQt += Integer.parseInt(confirmProduct.getQt());
            }
        }
        product.setTotalPrice(totalPrice);
        product.setTotalQt(totalQt);
        product.setTotalFee(result.getShipping_fee());
        products.add(product);
        tv_sum.setText(product.getTotalPrice());
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
//                String type, String pid, String address_id,String need_invoice, String invoice_type,
//                String invoice_title,String invoice_content, String consignee, String mobile, String tel
                networkRequest(StoreApi.orderSubmit("2", pid, "", addressId, need_invoice, invoice_type, invoice_title, invoice_content, consignee, mobile, tel),
                        new SimpleFastJsonCallback<OrderSubmitReturn>(OrderSubmitReturn.class, loading) {
                            @Override
                            public void onSuccess(String url, OrderSubmitReturn result) {
                                startActivity(CashierActivity.class);
                            }
                        });
                break;
        }
    }

    @Subcriber(tag = AddressAdapter.EVENT_ADDRESS)
    public void eventAddress(String addressInfo) {
        String[] split = addressInfo.split("/");
        tv_name.setText(split[0]);
        tv_address.setText(split[1]);
        tv_phone.setText(split[2]);
        consignee = split[0];
        mobile = split[2];
    }

    @Subcriber(tag = InvoiceInfoActivity.EVENT_INVOICE)
    public void eventInvoice(List<String> invoiceInfo) {
        if (!invoiceInfo.get(0).equals(InvoiceInfoActivity.INVOICE_NEEDNOT)) {
            tv_Invoice_type.setText(invoiceInfo.get(1));
            tv_Invoice_content.setText(invoiceInfo.get(2));
            tv_Invoice_content.setVisibility(View.VISIBLE);
            need_invoice = "1";
            if (invoiceInfo.get(1).equals(InvoiceInfoActivity.INVOICE_PERSONAL)) {
                invoice_type = "1";
                invoice_title = InvoiceInfoActivity.INVOICE_PERSONAL;
            } else {
                invoice_type = "2";
                invoice_title = invoiceInfo.get(1);
            }
            invoice_content = invoiceInfo.get(2);
        } else {
            tv_Invoice_type.setText(InvoiceInfoActivity.INVOICE_NEEDNOT);
            tv_Invoice_content.setVisibility(View.GONE);
            need_invoice = "0";
            invoice_type = "";
            invoice_title = "";
            invoice_content = "";
        }
    }
}
