package com.putao.wd.store.invoice;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.SwitchButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 填写订单
 * Created by wangou on 2015/12/08.
 */
public class InvoiceInfoActivity extends PTWDActivity implements View.OnClickListener, SwitchButton.OnSwitchClickListener {
    @Bind(R.id.ll_need_invoice_detail)
    LinearLayout ll_need_invoice_detail;//需要发票信息区域（默认不可见）
    //是否需要发票
    @Bind(R.id.btn_need_invoice)
    SwitchButton btn_need_invoice;//需要发票
    @Bind(R.id.btn_noneed_invoice)
    SwitchButton btn_noneed_invoice;//不需要发布
    //发票抬头
    @Bind(R.id.btn_person)
    SwitchButton btn_person;//个人
    @Bind(R.id.btn_company)
    SwitchButton btn_company;//单位
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
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
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
    public void onSwitchClick(View v, boolean isSelect) {
        switch (v.getId()){
            case R.id.btn_need_invoice:

                break;
            case R.id.btn_noneed_invoice:

                break;
            case R.id.btn_person:

                break;
            case R.id.btn_company:

                break;
            case R.id.btn_invoice_info:

                break;
            case R.id.btn_electronic_product:

                break;
            case R.id.btn_toy:

                break;
        }
    }
}
