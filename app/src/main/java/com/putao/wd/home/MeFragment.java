package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.UserApi;
import com.putao.wd.me.actions.MyActionsActivity;
import com.putao.wd.me.address.AddressListActivity;
import com.putao.wd.me.message.MessageCenterActivity;
import com.putao.wd.me.order.OrderListActivity;
import com.putao.wd.me.service.ServiceListActivity;
import com.putao.wd.me.setting.SettingActivity;
import com.putao.wd.model.UserInfo;
import com.putao.wd.store.order.WriteOrderActivity;
import com.putao.wd.user.CompleteActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.SettingItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.select.IndicatorButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我
 * Created by guchenkai on 2015/11/25.
 */
public class MeFragment extends BasicFragment implements View.OnClickListener {
    public static final String EVENT_EDIT_USER_INFO = "edit_user_info";

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
    @Bind(R.id.iv_user_icon)
    ImageDraweeView iv_user_icon;
    @Bind(R.id.tv_user_nickname)
    TextView tv_user_nickname;
    @Bind(R.id.rl_user_head_icon)
    RelativeLayout rl_user_head_icon;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        //Logger.d(MainActivity.TAG, "MeFragment启动");
//        si_message.show(18);
//        btn_pay.show(9);
//        btn_deliver.show(10);
//        btn_take_deliver.show(11);
//        btn_after_sale.show(12);
        getUserInfo();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        networkRequest(UserApi.getUserInfo(), new SimpleFastJsonCallback<UserInfo>(UserInfo.class, loading) {
            @Override
            public void onSuccess(String url, UserInfo result) {
                iv_user_icon.setImageURL(result.getHead_img());
                tv_user_nickname.setText(result.getNick_name());
                loading.dismiss();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Subcriber(tag = EVENT_EDIT_USER_INFO)
    public void EventEditUserInfo(String tag) {
        getUserInfo();
    }

    @OnClick({R.id.iv_setting, R.id.si_order, R.id.si_address, R.id.si_action, R.id.si_question, R.id.iv_user_icon
            , R.id.si_child_info, R.id.si_message, R.id.btn_pay, R.id.btn_deliver, R.id.btn_take_deliver, R.id.btn_after_sale})
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.si_order://我的订单
                bundle.putInt("current_Index", 0);
                startActivity(OrderListActivity.class);
                break;
            case R.id.si_child_info://孩子信息

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
            case R.id.iv_user_icon://完善用户信息
                startActivity(CompleteActivity.class);
                break;
            case R.id.si_message://消息中心
                si_message.hide();
                startActivity(MessageCenterActivity.class);
                break;
            case R.id.btn_pay://待付款
                btn_pay.hide();
                bundle.putInt("current_Index", 1);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.btn_deliver://待发货
                btn_deliver.hide();
                bundle.putInt("current_Index", 2);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.btn_take_deliver://待收货
                btn_take_deliver.hide();
                bundle.putInt("current_Index", 3);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.btn_after_sale://售后
                btn_after_sale.hide();
                startActivity(ServiceListActivity.class, bundle);
                break;
        }
    }
}
