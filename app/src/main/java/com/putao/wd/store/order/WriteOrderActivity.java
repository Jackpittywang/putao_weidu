package com.putao.wd.store.order;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

public class WriteOrderActivity extends PTWDActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_order;
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
