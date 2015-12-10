package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.explore.product.ControlledProductActivity;
import com.putao.wd.explore.usetime.UseTimeEveryTimeActivity;
import com.putao.wd.me.actions.MyActionsActivity;
import com.putao.wd.me.address.AddressListActivity;
import com.putao.wd.me.order.OrderListActivity;
import com.putao.wd.me.setting.SettingActivity;
import com.putao.wd.store.order.WriteOrderActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.SettingItem;
import com.sunnybear.library.view.select.IndicatorButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我
 * Created by guchenkai on 2015/11/25.
 */
public class MeFragment extends BasicFragment implements View.OnClickListener {
    @Bind(R.id.si_message)
    SettingItem si_message;
    @Bind(R.id.btn_pay)
    IndicatorButton btn_pay;
    @Bind(R.id.btn_deliver)
    IndicatorButton btn_deliver;
    @Bind(R.id.btn_take_deliver)
    IndicatorButton btn_take_deliver;
    @Bind(R.id.btn_after_sale)
    IndicatorButton btn_after_sale;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        si_message.show(18);

        btn_pay.show(9);
        btn_deliver.show(10);
        btn_take_deliver.show(11);
        btn_after_sale.show(12);
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.iv_setting, R.id.si_order, R.id.si_address, R.id.si_action, R.id.si_question//,R.id.iv_user_icon
            , R.id.si_message, R.id.btn_pay, R.id.btn_deliver, R.id.btn_take_deliver, R.id.btn_after_sale})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_setting://设置
                startActivity(SettingActivity.class);
                break;
            case R.id.si_order://我的订单
                startActivity(OrderListActivity.class);
                break;
            case R.id.si_address://收货地址
                startActivity(AddressListActivity.class);
                break;
            case R.id.si_action://我参与的活动
                startActivity(MyActionsActivity.class);
                break;
            case R.id.si_question://我的提问
                startActivity(WriteOrderActivity.class);
                break;
//            case R.id.iv_user_icon://个人信息
//                startActivity(PersonalInformationActivity.class);
//                break;
            case R.id.si_message://消息中心
//                startActivity(MessageCenterActivity.class);
                si_message.hide();
                break;
            case R.id.btn_pay://待付款
                btn_pay.hide();
                break;
            case R.id.btn_deliver://待发货
                btn_deliver.hide();
                break;
            case R.id.btn_take_deliver://待收货
                btn_take_deliver.hide();
                break;
            case R.id.btn_after_sale://售后
                btn_after_sale.hide();
                break;
        }
    }
}
