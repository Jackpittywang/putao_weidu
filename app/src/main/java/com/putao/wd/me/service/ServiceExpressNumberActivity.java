package com.putao.wd.me.service;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

import butterknife.Bind;

/**
 * 售后--填写快递单号
 * Created by yanghx on 2016/1/4.
 */
public class ServiceExpressNumberActivity extends PTWDActivity<GlobalApplication> implements OnClickListener {

    @Bind(R.id.ll_select_company)
    LinearLayout ll_select_company;
    @Bind(R.id.et_express_number)
    TextView et_express_number;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_express_number;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {


    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
