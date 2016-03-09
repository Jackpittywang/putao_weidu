package com.putao.wd.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.OrderApi;
import com.putao.wd.api.UserApi;
import com.putao.wd.me.address.AddressListActivity;
import com.putao.wd.me.attention.ConcernsActivity;
import com.putao.wd.me.child.ChildInfoActivity;
import com.putao.wd.me.message.MessageCenterActivity;
import com.putao.wd.me.order.OrderListActivity;
import com.putao.wd.me.service.ServiceListActivity;
import com.putao.wd.me.setting.SettingActivity;
import com.putao.wd.model.OrderCount;
import com.putao.wd.model.UserInfo;
import com.putao.wd.start.putaozi.GrapestoneActivity;
import com.putao.wd.user.CompleteActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.SettingItem;
import com.sunnybear.library.view.image.FastBlur;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.select.IndicatorButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我
 * Created by guchenkai on 2015/11/25.
 */
public class MeFragment extends BasicFragment implements View.OnClickListener, View.OnTouchListener {
    public static final String ME_BLUR = "me_blur";
    private static final String EVENT_EDIT_USER_INFO = "edit_user_info";
    public static boolean ONREFRESH = true;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    @Bind(R.id.si_message)
    SettingItem si_message;
    @Bind(R.id.ll_me)
    LinearLayout ll_me;
    @Bind(R.id.sv_me)
    ScrollView sv_me;
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
    @Bind(R.id.iv_user_icon_background)
    ImageDraweeView iv_user_icon_background;
    @Bind(R.id.tv_user_nickname)
    TextView tv_user_nickname;
    @Bind(R.id.rl_user_head_icon)
    RelativeLayout rl_user_head_icon;

    private HandlerThread mHandlerThread;
    private String mImg = "";
    private Handler mHandler;
    private int y;
    int oldY = 0;
    int newY;


    private int mHeadHeight;
    private ViewGroup.LayoutParams mHeadLayoutParams;
    private int mStatusBarHeight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isPrepared = false;
        //填充各控件的数据
        mHeadLayoutParams = rl_user_head_icon.getLayoutParams();
        mHeadHeight = mHeadLayoutParams.height;
        mStatusBarHeight = getStatusBarHeight();
        sv_me.setOnTouchListener(this);
        ll_me.getParent().requestDisallowInterceptTouchEvent(false);
        getUserInfo();
        getOrderCount();
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        mHandlerThread = new HandlerThread("blurThread");
        mHandlerThread.start();
        setDefaultBlur();
        Looper looper = mHandlerThread.getLooper();
        mHandler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    try {
                        if ("".equals(msg.obj.toString()) || null == msg.obj.toString()) {
                            setDefaultBlur();
                            return;
                        }
                        Bitmap map;
                        URL url = new URL(msg.obj.toString());
                        URLConnection conn = url.openConnection();
                        conn.connect();
                        InputStream in;
                        in = conn.getInputStream();
                        map = BitmapFactory.decodeStream(in);
                        Bitmap apply = FastBlur.doBlur(map, 50, false);
                        EventBusHelper.post(apply, ME_BLUR);
                    } catch (Exception e) {
                        e.printStackTrace();
                        setDefaultBlur();
                    }
                } else if (msg.what == 2) {
                    setDefaultBlur();
                }
            }
        };
        isPrepared = true;
    }

    private void setDefaultBlur() {
        Bitmap apply = FastBlur.doBlur(BitmapFactory.decodeResource(getResources(), R.drawable.img_head_default), 50, false);
        EventBusHelper.post(apply, ME_BLUR);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!AccountHelper.isLogin()) {
            setDefaultBlur();
        } else if (!IndexActivity.isNotRefreshUserInfo && AccountHelper.isLogin() && !isPrepared) {
            hideNum();
            getOrderCount();
        }
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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
        ONREFRESH = false;
        if (TextUtils.isEmpty(AccountHelper.getCurrentUid())) return;
        networkRequest(UserApi.getUserInfo(),
                new SimpleFastJsonCallback<UserInfo>(UserInfo.class, loading) {
                    @Override
                    public void onSuccess(String url, final UserInfo result) {
                        ONREFRESH = true;
                        //Message message = new Message();
                        AccountHelper.setUserInfo(result);
                        tv_user_nickname.setText(result.getNick_name());
                        if (mImg.equals(result.getHead_img())) {
                            if (TextUtils.isEmpty(result.getHead_img())) {
                                Message message = new Message();
                                message.what = 2;
                                mHandler.sendMessage(message);
                            }
                            loading.dismiss();
                            return;
                        }
                        mImg = result.getHead_img();
                        iv_user_icon.setImageURL(result.getHead_img());
                        Message message = new Message();
                        message.obj = mImg;
                        message.what = 1;
                        mHandler.sendMessage(message);
                        //message.obj = result.getHead_img();
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ONREFRESH = true;
//                        ToastUtils.showToastLong(mActivity, "登录失败请重新登录");
                    }
                });
    }

    /**
     * 获得订单数量
     */
    private void getOrderCount() {
        if (TextUtils.isEmpty(AccountHelper.getCurrentUid())) return;
        networkRequest(OrderApi.getOrderCount(), new SimpleFastJsonCallback<OrderCount>(OrderCount.class, loading) {
            @Override
            public void onSuccess(String url, OrderCount result) {
                Logger.d(result.toString());
                hideNum();
                btn_pay.show(result.getUnpaid().getNum());
                btn_deliver.show(result.getUndelivery().getNum());
                btn_take_deliver.show(result.getUnCheck().getNum());
                btn_after_sale.show(result.getService().getNum());
                loading.dismiss();
            }
        }, false);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Subcriber(tag = EVENT_EDIT_USER_INFO)
    public void eventEditUserInfo(String tag) {
        if (ONREFRESH) {
            getUserInfo();
        }
    }

    @Subcriber(tag = LoginActivity.EVENT_LOGIN)
    public void eventLogin(String tag) {
        mImg = "";
        getUserInfo();
        getOrderCount();
    }

    @Subcriber(tag = SettingActivity.EVENT_LOGOUT)
    public void eventLogout(String tag) {
        mImg = "";
        hideNum();
        iv_user_icon.setDefaultImage(R.drawable.img_head_default);
        tv_user_nickname.setText("葡星人");
    }

    @OnClick({R.id.iv_setting, R.id.si_order, R.id.si_address, /*R.id.si_action,*/ R.id.si_question, R.id.iv_user_icon
            , R.id.si_child_info, R.id.si_message, R.id.btn_pay, R.id.btn_deliver, R.id.btn_take_deliver, R.id.btn_after_sale, R.id.si_concerns})
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
            /*case R.id.si_action://我参与的活动
                startActivity(MyActionsActivity.class);
//                startActivity(WriteOrderActivity.class);
                break;*/
            case R.id.si_question://葡萄籽
//                startActivity(QuestionActivity.class);
                startActivity(GrapestoneActivity.class);
                break;
            case R.id.iv_user_icon://完善用户信息
                startActivity(CompleteActivity.class);
                break;
            case R.id.si_concerns://我的关注
                startActivity(ConcernsActivity.class);
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
            /*case R.id.si_action://我参与的活动
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, MyActionsActivity.class);
                break;*/
            /*case R.id.si_question://我的提问
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, QuestionActivity.class);
                break;*/
            case R.id.iv_user_icon://完善用户信息
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CompleteActivity.class);
                break;
            case R.id.si_concerns://完善用户信息
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, ConcernsActivity.class);
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

    @Subcriber(tag = ME_BLUR)
    private void setBlur(Bitmap bitmap) {
        iv_user_icon_background.setDefaultImage(bitmap);
    }

    private boolean isClick = true;
    int height;
    int[] position = new int[2];

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isClick = false;
                oldY = y = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                newY = (int) event.getY();
                if (isClick) {
                    oldY = y = newY;
                    isClick = false;
                }
                height = mHeadLayoutParams.height + (newY - y) / 3;
                rl_user_head_icon.getLocationOnScreen(position);
                if (position[1] < mStatusBarHeight) return false;
                if (height < mHeadHeight) {
                    mHeadLayoutParams.height = mHeadHeight;
                    rl_user_head_icon.setLayoutParams(mHeadLayoutParams);
                    return false;
                }
                mHeadLayoutParams.height = height;
                rl_user_head_icon.setLayoutParams(mHeadLayoutParams);
                y = newY;
                break;
            case MotionEvent.ACTION_CANCEL:
                isClick = true;
                mHeadLayoutParams.height = mHeadHeight;
                rl_user_head_icon.setLayoutParams(mHeadLayoutParams);
                if (y - oldY < 300 || oldY == 0) break;
                oldY = 0;
                if (ONREFRESH) {
                    getUserInfo();
                    getOrderCount();
                }

                break;
            case MotionEvent.ACTION_UP:
//                float flo = (float)mHeadHeight / mHeadLayoutParams.height;
//                iv_user_icon_background.getLayoutParams().height = mHeadLayoutParams.height;
//                ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv_user_icon_background, "scaleY", 1.0f, flo);
//                animator1.setDuration(500).start();
                isClick = true;
                mHeadLayoutParams.height = mHeadHeight;
                rl_user_head_icon.setLayoutParams(mHeadLayoutParams);
                if (y - oldY < 300 || oldY == 0) break;
                oldY = 0;
                if (ONREFRESH) {
                    getUserInfo();
                    getOrderCount();
                }

                break;
        }
        return true;
    }
}
