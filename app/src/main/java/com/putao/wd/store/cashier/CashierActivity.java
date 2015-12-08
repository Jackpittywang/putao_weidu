package com.putao.wd.store.cashier;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * 收银台
 * Created by wangou on 2015/12/08.
 */
public class CashierActivity extends PTWDActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cashier;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
