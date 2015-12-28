package com.putao.wd.store.invoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Cart;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.CleanableEditText;
import com.sunnybear.library.view.SwitchButton;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 发票
 * Created by wangou on 2015/12/08.
 */
public class InvoiceInfoActivity extends PTWDActivity implements View.OnClickListener {
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
    SwitchButton btn_invoice_info;//需要明细
    @Bind(R.id.btn_electronic_product)
    SwitchButton btn_electronic_product;//电子产品
    @Bind(R.id.btn_toy)
    SwitchButton btn_toy;//玩具

    public static final String NEED_INVOICE = "need_invoice";//发票抬头信息
    public static final String INVOICE_TYPE = "invoice_type";//发票抬头类型
    public static final String INVOICE_TITLE = "invoice_title";//发票抬头
    public static final String INVOICE_CONTENT = "invoice_content";//发票类型
    private String need_invoice = "";
    private String invoice_type = "";
    private String invoice_title = "";
    private String invoice_content = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invoice_info;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        cancelBtn();
    }

    private void initInfo() {
        need_invoice = 0+"";
    }

    private void cancelBtn() {
        btn_noneed_invoice.setClickable(false);
        btn_need_invoice.setClickable(false);
        btn_person.setClickable(false);
        btn_company.setClickable(false);
        btn_invoice_info.setClickable(false);
        btn_electronic_product.setClickable(false);
        btn_toy.setClickable(false);
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
                need_invoice = 0+"";
                break;
            case R.id.ll_need_invoice:
                ll_need_invoice_detail.setVisibility(View.VISIBLE);
                btn_need_invoice.setState(true);
                btn_noneed_invoice.setState(false);
                need_invoice = 1+"";
                break;
            case R.id.ll_person:
                btn_person.setState(true);
                btn_company.setState(false);
                et_company.setVisibility(View.INVISIBLE);
                invoice_type = 1+"";
                break;
            case R.id.ll_company:
                btn_person.setState(false);
                btn_company.setState(true);
                et_company.setVisibility(View.VISIBLE);
                invoice_type = 2+"";
                invoice_title = et_company.getText().toString();
                break;
            case R.id.ll_info:
                btn_invoice_info.setState(true);
                btn_electronic_product.setState(false);
                btn_toy.setState(false);
                invoice_content = 1+"";
                break;
            case R.id.ll_electronic_product:
                btn_electronic_product.setState(true);
                btn_invoice_info.setState(false);
                btn_toy.setState(false);
                invoice_content = 2+"";
                break;
            case R.id.ll_toy:
                btn_toy.setState(true);
                btn_invoice_info.setState(false);
                btn_electronic_product.setState(false);
                invoice_content = 3+"";
                break;
        }
    }

    /**
     * 点击保存返回数据
     */
    @Override
    public void onRightAction() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString(NEED_INVOICE, need_invoice);
        bundle.putString(INVOICE_TYPE, invoice_type);
        bundle.putString(INVOICE_TITLE, invoice_title);
        bundle.putString(INVOICE_CONTENT, invoice_content);
        setResult(0, intent);
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
