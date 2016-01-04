package com.putao.wd.store.order;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.ColorConstant;
import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.address.AddressListActivity;
import com.putao.wd.model.Address;
import com.putao.wd.model.OrderConfirm;
import com.putao.wd.model.OrderConfirmProduct;
import com.putao.wd.model.OrderSubmitReturn;
import com.putao.wd.store.invoice.InvoiceInfoActivity;
import com.putao.wd.store.order.adapter.WriteOrderAdapter;
import com.putao.wd.store.pay.PayActivity;
import com.putao.wd.store.shopping.ShoppingCarActivity;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.MathUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
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
    @Bind(R.id.ll_submit)
    LinearLayout ll_submit;
    @Bind(R.id.ll_receiving_address)
    LinearLayout ll_receiving_address;//有收货地址时
    @Bind(R.id.ll_no_receiving_address)
    LinearLayout ll_no_receiving_address;//没有收货地址时


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
        ll_submit.setClickable(false);
        sticky_layout.canScrollView();

        Bundle bundle = getIntent().getExtras();
        pid = bundle.getString(ShoppingCarActivity.PRODUCT_ID);
        Logger.w("pid = " + pid);
        getDefaultAddress();
        networkRequest(StoreApi.orderConfirm("2", pid, ""),
                new SimpleFastJsonCallback<OrderConfirm>(OrderConfirm.class, loading) {
                    @Override
                    public void onSuccess(String url, OrderConfirm result) {
                        Logger.w("填写订单 = " + result.toString());
                        if (result != null) {
                            adapter = new WriteOrderAdapter(mContext, getLastItem(result));
                            rv_orders.setAdapter(adapter);
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
     * 获取默认地址
     */
    private void getDefaultAddress() {
        networkRequest(StoreApi.getDefaultAddress(), new SimpleFastJsonCallback<Address>(Address.class, loading) {
            @Override
            public void onSuccess(String url, Address result) {
                Logger.d(result.toString());
                if (result != null) {
                    ll_receiving_address.setVisibility(View.VISIBLE);
                    ll_no_receiving_address.setVisibility(View.GONE);
                    setAddress(result);
                    ll_submit.setBackgroundColor(ColorConstant.MAIN_COLOR_NOR);
                    ll_submit.setClickable(true);
                }
                loading.dismiss();
            }
        });
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

    @OnClick({R.id.fl_address, R.id.ll_need_invoice, R.id.ll_submit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_address://选择地址
                Bundle bundle = new Bundle();
                bundle.putBoolean(AddressListActivity.BUNDLE_IS_CLOSE, true);
                startActivity(AddressListActivity.class, bundle);
                break;
            case R.id.ll_need_invoice://发票信息
                startActivity(InvoiceInfoActivity.class);
                break;
            case R.id.ll_submit://去结算
                if (StringUtils.isEmpty(addressId)) {
                    ToastUtils.showToastShort(mContext, "请填写收货地址");
                    return;
                }
                networkRequest(StoreApi.orderSubmit("2", pid, "", addressId, need_invoice, invoice_type, invoice_title, invoice_content, consignee, mobile, tel),
                        new SimpleFastJsonCallback<OrderSubmitReturn>(OrderSubmitReturn.class, loading) {
                            @Override
                            public void onSuccess(String url, OrderSubmitReturn result) {
                                if (result != null) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(PayActivity.BUNDLE_ORDER_INFO, result);
                                    startActivity(PayActivity.class, bundle);
                                }
                            }
                        });
                break;
        }
    }

    @Subcriber(tag = AddressListActivity.EVENT_SELECT_ADDRESS)
    public void eventSelectAddress(Address address) {
        ll_receiving_address.setVisibility(View.VISIBLE);
        ll_no_receiving_address.setVisibility(View.GONE);
        setAddress(address);
        ll_submit.setBackgroundColor(ColorConstant.MAIN_COLOR_NOR);
        ll_submit.setClickable(true);
    }

    /**
     * 设置地址信息
     */
    private void setAddress(Address address) {
        tv_name.setText(address.getRealname());
        tv_address.setText(setAddressName(address));
        tv_phone.setText(address.getMobile());
        addressId = address.getId();
        consignee = address.getRealname();
        mobile = address.getMobile();
    }

    /**
     * 设置地址
     *
     * @param address
     */
    private String setAddressName(Address address) {
        JSONObject object = JSON.parseObject(address.getAddressName());
        String addr = object.getString(address.getProvince_id()) +
                object.getString(address.getCity_id()) +
                object.getString(address.getArea_id()) +
                address.getAddress();
        return addr;
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
