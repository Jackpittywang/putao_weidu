package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.me.address.AddressListActivity;
import com.putao.wd.me.message.MessageCenterActivity;
import com.putao.wd.me.setting.SettingActivity;
import com.sunnybear.library.controller.BasicFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我
 * Created by guchenkai on 2015/11/25.
 */
public class MeFragment extends BasicFragment implements View.OnClickListener {
    @Bind(R.id.ll_main)
    LinearLayout ll_main;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.iv_setting, R.id.si_order, R.id.si_address, R.id.si_action, R.id.si_question, R.id.si_message})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_setting://设置
                startActivity(SettingActivity.class);
                break;
            case R.id.si_order://我的订单

                break;
            case R.id.si_address://收货地址
                startActivity(AddressListActivity.class);
                break;
            case R.id.si_action://我参与的活动

                break;
            case R.id.si_question://我的提问

                break;
            case R.id.si_message://消息中心
                startActivity(MessageCenterActivity.class);
                break;
        }
    }
}
