package com.putao.wd.store.invoice;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Cart;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;
import com.sunnybear.library.view.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 发票
 * Created by wangou on 2015/12/08.
 */
public class InvoiceInfoActivity extends PTWDActivity implements View.OnClickListener {
    public static final String EVENT_INVOICE = "Event_Invoice";

    public static final String INVOICE_NEEDNOT = "不需要发票";
    public static final String INVOICE_NEED = "需要发票";
    public static final String INVOICE_PERSONAL = "个人";
    public static final String INVOICE_COMPANY = "单位";
    public static final String INVOICE_DETAIL = "商品明细";
    public static final String INVOICE_ELECTRONIC = "电子产品";
    public static final String INVOICE_PLAY = "玩具";

    @Bind(R.id.ll_need_invoice_detail)
    LinearLayout ll_need_invoice_detail;//需要发票信息区域（默认不可见）
    @Bind(R.id.ll_need_invoice)
    LinearLayout ll_need_invoice;//不需要发票条目
    @Bind(R.id.ll_noneed_invoice)
    LinearLayout ll_noneed_invoice;//需要发票条目
    @Bind(R.id.ll_person)
    LinearLayout ll_person;//个人条目
    @Bind(R.id.ll_company)
    LinearLayout ll_company;//单位条目
    @Bind(R.id.ll_info)
    LinearLayout ll_info;//需要明细
    @Bind(R.id.ll_electronic_product)
    LinearLayout ll_electronic_product;//电子产品
    @Bind(R.id.ll_toy)
    LinearLayout ll_toy;//玩具

    @Bind(R.id.btn_noneed_invoice)
    SwitchButton btn_noneed_invoice;//不需要发票
    //是否需要发票
    @Bind(R.id.btn_need_invoice)
    SwitchButton btn_need_invoice;//需要发票
    //发票抬头
    @Bind(R.id.btn_person)
    SwitchButton btn_person;//个人
    @Bind(R.id.btn_company)
    SwitchButton btn_company;//单位
    @Bind(R.id.et_company)
    CleanableEditText et_company;
    //发票内容
    @Bind(R.id.btn_invoice_info)
    SwitchButton btn_invoice_info;//商品明细
    @Bind(R.id.btn_electronic_product)
    SwitchButton btn_electronic_product;//电子产品
    @Bind(R.id.btn_toy)
    SwitchButton btn_toy;//玩具

    private String need_invoice = INVOICE_NEEDNOT;//发票抬头信息
    private String invoice_type = INVOICE_PERSONAL;//发票抬头
    private String invoice_content = INVOICE_DETAIL;//发票内容

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invoice_info;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        cancelBtn();
    }

    private void cancelBtn() {
        btn_noneed_invoice.setClickable(false);
        btn_need_invoice.setClickable(false);
        btn_person.setClickable(false);
        btn_company.setClickable(false);
        btn_invoice_info.setClickable(false);
        btn_electronic_product.setClickable(false);
        btn_toy.setClickable(false);
        et_company.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                invoice_type = et_company.getText().toString();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.ll_noneed_invoice, R.id.ll_need_invoice, R.id.ll_person, R.id.ll_company, R.id.ll_info, R.id.ll_electronic_product, R.id.ll_toy})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_noneed_invoice:
                ll_need_invoice_detail.setVisibility(View.GONE);
                btn_noneed_invoice.setState(true);
                btn_need_invoice.setState(false);
                need_invoice = INVOICE_NEEDNOT;
                break;
            case R.id.ll_need_invoice:
                ll_need_invoice_detail.setVisibility(View.VISIBLE);
                btn_need_invoice.setState(true);
                btn_noneed_invoice.setState(false);
                need_invoice = INVOICE_NEED;
                break;
            case R.id.ll_person:
                btn_person.setState(true);
                btn_company.setState(false);
                et_company.setVisibility(View.INVISIBLE);
                invoice_type = INVOICE_PERSONAL;
                break;
            case R.id.ll_company:
                btn_person.setState(false);
                btn_company.setState(true);
                et_company.setVisibility(View.VISIBLE);
                invoice_type = "";
                break;
            case R.id.ll_info:
                btn_invoice_info.setState(true);
                btn_electronic_product.setState(false);
                btn_toy.setState(false);
                invoice_content = INVOICE_DETAIL;
                break;
            case R.id.ll_electronic_product:
                btn_electronic_product.setState(true);
                btn_invoice_info.setState(false);
                btn_toy.setState(false);
                invoice_content = INVOICE_ELECTRONIC;
                break;
            case R.id.ll_toy:
                btn_toy.setState(true);
                btn_invoice_info.setState(false);
                btn_electronic_product.setState(false);
                invoice_content = INVOICE_PLAY;
                break;
        }
    }

    /**
     * 点击保存返回数据
     */
    @Override
    public void onRightAction() {
        if (StringUtils.isEmpty(invoice_type)) {
            ToastUtils.showToastLong(mContext, "发票抬头不能为空");
            return;
        }
        List<String> invoiceInfo = new ArrayList<>();
        invoiceInfo.add(need_invoice);
        invoiceInfo.add(invoice_type);
        invoiceInfo.add(invoice_content);
        EventBusHelper.post(invoiceInfo, EVENT_INVOICE);
        finish();
    }

    /**
     * 编辑发票
     */
    private void editInvoice(String invoice_id, String invoice_type, String invoice_content, String invoice_title) {
        networkRequest(StoreApi.editInvoice(invoice_id, invoice_type, invoice_content, invoice_title), new SimpleFastJsonCallback<ArrayList<Cart>>(Cart.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Cart> result) {
                Logger.d(result.toString());
            }
        });
    }
}
