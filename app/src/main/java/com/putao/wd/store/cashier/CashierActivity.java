package com.putao.wd.store.cashier;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.image.ImageDraweeView;

import butterknife.Bind;

/**
 * 收银台
 * Created by wangou on 2015/12/08.
 */
public class CashierActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.tv_cashier_date)
    TextView tv_cashier_date;//订单日期
    @Bind(R.id.tv_cashier_orderid)
    TextView tv_cashier_orderid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cashier;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
