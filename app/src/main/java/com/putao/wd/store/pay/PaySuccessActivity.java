package com.putao.wd.store.pay;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.MainActivity;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.order.OrderDetailActivity;
import com.sunnybear.library.controller.ActivityManager;

import butterknife.OnClick;

/**
 * 支付成功
 * Created by guchenkai on 2015/12/29.
 */
public class PaySuccessActivity extends PTWDActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.btn_back_store, R.id.tv_look_order})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_store://返回葡商城
                ActivityManager.getInstance().popOtherActivity(MainActivity.class);
                finish();
                break;
            case R.id.tv_look_order://查看订单
                ActivityManager.getInstance().popOtherActivity(MainActivity.class);
                startActivity(OrderDetailActivity.class, args);
                finish();
                break;
        }
    }
}
