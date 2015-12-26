package com.putao.wd.store.invoice;

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
public class InvoiceInfoActivity extends PTWDActivity implements View.OnClickListener, SwitchButton.OnSwitchClickListener {
    @Bind(R.id.ll_need_invoice_detail)
    LinearLayout ll_need_invoice_detail;//需要发票信息区域（默认不可见）
    //是否需要发票
    @Bind(R.id.btn_need_invoice)
    SwitchButton btn_need_invoice;//需要发票
    @Bind(R.id.btn_noneed_invoice)
    SwitchButton btn_noneed_invoice;//不需要发票
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
    @Override
    protected int getLayoutId() {
        return R.layout.activity_invoice_info;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        addListener();
    }

    private void addListener() {
        //是否需要发票
        btn_need_invoice.setOnSwitchClickListener(this);
        btn_noneed_invoice.setOnSwitchClickListener(this);
        //发票抬头
        btn_person.setOnSwitchClickListener(this);
        btn_company.setOnSwitchClickListener(this);
        //发票内容
        btn_invoice_info.setOnSwitchClickListener(this);
        btn_electronic_product.setOnSwitchClickListener(this);
        btn_toy.setOnSwitchClickListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.ll_need_invoice_detail})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_need_invoice_detail:
        }
    }

    @Override
    public void onRightAction() {

    }

    /**
     * 编辑发票
     */
    private void editInvoice(String invoice_id,String invoice_type,String invoice_content,String invoice_title){
        networkRequest(StoreApi.editInvoice(invoice_id,invoice_type,invoice_content,invoice_title), new SimpleFastJsonCallback<ArrayList<Cart>>(Cart.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Cart> result) {
                Logger.d(result.toString());
            }
        });
    }
    @Override
    public void onSwitchClick(View v, boolean isSelect) {
        switch (v.getId()){
            case R.id.btn_need_invoice://需要发票
                ll_need_invoice_detail.setVisibility(View.VISIBLE);
                btn_need_invoice.setState(true);
                btn_noneed_invoice.setState(false);
                break;
            case R.id.btn_noneed_invoice://不需要发票
                ll_need_invoice_detail.setVisibility(View.GONE);
                btn_noneed_invoice.setState(true);
                btn_need_invoice.setState(false);
                break;
            case R.id.btn_person://个人
                btn_person.setState(true);
                btn_company.setState(false);
                et_company.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_company://单位
                btn_person.setState(true);
                btn_person.setState(false);
                et_company.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_invoice_info://发票明细
                btn_invoice_info.setState(true);
                btn_electronic_product.setState(false);
                btn_toy.setState(false);
                break;
            case R.id.btn_electronic_product://电子产品
                btn_electronic_product.setState(true);
                btn_invoice_info.setState(false);
                btn_toy.setState(false);
                break;
            case R.id.btn_toy://玩具
                btn_toy.setState(true);
                btn_invoice_info.setState(false);
                btn_electronic_product.setState(false);
                break;
        }
    }
}
