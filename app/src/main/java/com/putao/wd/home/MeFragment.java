package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.MainActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.OrderApi;
import com.putao.wd.api.UserApi;
import com.putao.wd.me.actions.MyActionsActivity;
import com.putao.wd.me.address.AddressListActivity;
import com.putao.wd.me.child.ChildInfoActivity;
import com.putao.wd.me.message.MessageCenterActivity;
import com.putao.wd.me.order.OrderListActivity;
import com.putao.wd.me.service.ServiceListActivity;
import com.putao.wd.me.setting.SettingActivity;
import com.putao.wd.model.OrderCount;
import com.putao.wd.model.UserInfo;
import com.putao.wd.start.question.QuestionActivity;
import com.putao.wd.user.CompleteActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
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
    IndicatorButton btn_pay;//待付款
    @Bind(R.id.btn_deliver)
    IndicatorButton btn_deliver;//待发货
    @Bind(R.id.btn_take_deliver)
    IndicatorButton btn_take_deliver;//待收货
    @Bind(R.id.btn_after_sale)
    IndicatorButton btn_after_sale;//售后
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
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!AccountHelper.isLogin()) {
            hideNum();
        } else if (!MainActivity.isNotRefreshUserInfo && AccountHelper.isLogin()) {
            hideNum();
            getOrderCount();
            getUserInfo();
        }
    }

    /**
     * 订单上数字没有登录则不显示
     */
    private void hideNum() {
        btn_pay.hide();
        btn_deliver.hide();
        btn_take_deliver.hide();
        btn_after_sale.hide();
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

    /**
     * 获得订单数量
     */
    private void getOrderCount() {
        networkRequest(OrderApi.getOrderCount(), new SimpleFastJsonCallback<OrderCount>(OrderCount.class, loading) {
            @Override
            public void onSuccess(String url, OrderCount result) {
                Logger.d(result.toString());
                btn_pay.show(result.getUnpaid().getNum());
                btn_deliver.show(result.getUndelivery().getNum());
                btn_take_deliver.show(result.getUnCheck().getNum());
                btn_after_sale.show(result.getService().getNum());
                loading.dismiss();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Subcriber(tag = EVENT_EDIT_USER_INFO)
    public void eventEditUserInfo(String tag) {
        getUserInfo();
    }

    @Subcriber(tag = LoginActivity.EVENT_LOGIN)
    public void eventLogin(String tag) {
        getUserInfo();
        getOrderCount();
    }

    @Subcriber(tag = SettingActivity.EVENT_LOGOUT)
    public void eventLogout(String tag) {
        iv_user_icon.setDefaultImage(R.drawable.img_head_default);
        tv_user_nickname.setText("葡星人");
    }

    @OnClick({R.id.iv_setting, R.id.si_order, R.id.si_address, R.id.si_action, R.id.si_question, R.id.iv_user_icon
            , R.id.si_child_info, R.id.si_message, R.id.btn_pay, R.id.btn_deliver, R.id.btn_take_deliver, R.id.btn_after_sale})
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        if (!AccountHelper.isLogin()) {
            toLoginActivity(v, bundle);
            return;
        }
        switch (v.getId()) {
            case R.id.iv_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.si_order://我的订单
                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_ALL);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.si_child_info://孩子信息
                startActivity(ChildInfoActivity.class);
                break;
            case R.id.si_address://收货地址
                startActivity(AddressListActivity.class);
                break;
            case R.id.si_action://我参与的活动
                startActivity(MyActionsActivity.class);
//                startActivity(WriteOrderActivity.class);
                break;
            case R.id.si_question://我的提问
                startActivity(QuestionActivity.class);
                break;
            case R.id.iv_user_icon://完善用户信息
                startActivity(CompleteActivity.class);
                break;
            case R.id.si_message://消息中心
                si_message.hide();
                startActivity(MessageCenterActivity.class);
                break;
            case R.id.btn_pay://待付款
//                btn_pay.hide();
                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_PAY);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.btn_deliver://待发货\
//                btn_deliver.hide();
                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_SHIPMENT);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.btn_take_deliver://待收货
//                btn_take_deliver.hide();
                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_SIGN);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.btn_after_sale://售后
//                btn_after_sale.hide();
                startActivity(ServiceListActivity.class, bundle);
                break;
        }
    }

    /**
     * put进目标activity供登录后跳转
     */
    private void toLoginActivity(View v, Bundle bundle) {
        switch (v.getId()) {
            case R.id.iv_setting:
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, SettingActivity.class);
                break;
            case R.id.si_order://我的订单
                bundle.putInt(OrderListActivity.TYPE_INDEX, 0);
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, OrderListActivity.class);
                break;
            case R.id.si_child_info://孩子信息
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, ChildInfoActivity.class);
                break;
            case R.id.si_address://收货地址
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, AddressListActivity.class);
                break;
            case R.id.si_action://我参与的活动
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, MyActionsActivity.class);
                break;
            case R.id.si_question://我的提问
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, QuestionActivity.class);
                break;
            case R.id.iv_user_icon://完善用户信息
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CompleteActivity.class);
                break;
            case R.id.si_message://消息中心
                si_message.hide();
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, MessageCenterActivity.class);
                break;
            case R.id.btn_pay://待付款
                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_PAY);
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, OrderListActivity.class);
                break;
            case R.id.btn_deliver://待发货
                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_SHIPMENT);
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, OrderListActivity.class);
                break;
            case R.id.btn_take_deliver://待收货
                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_SIGN);
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, OrderListActivity.class);
                break;
            case R.id.btn_after_sale://售后
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, ServiceListActivity.class);
                break;
        }
        startActivity(LoginActivity.class, bundle);
    }
}
