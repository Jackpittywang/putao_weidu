package com.putao.wd.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.ptx.push.core.GPushCallback;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.UserApi;
import com.putao.wd.model.UserInfo;
import com.putao.wd.pt_me.address.AddressListActivity;
import com.putao.wd.pt_me.child.ChildInfoActivity;
import com.putao.wd.pt_me.collection.CollectionActivity;
import com.putao.wd.pt_me.message.MessageCenterActivity;
import com.putao.wd.pt_me.order.OrderListActivity;
import com.putao.wd.pt_me.participation.ParticipationActivity;
import com.putao.wd.pt_me.setting.SettingActivity;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.start.putaozi.GrapestoneActivity;
import com.putao.wd.user.CompleteActivity;
import com.putao.wd.user.LoginActivity;
import com.putao.wd.util.RedDotUtils;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.view.SettingItem;
import com.sunnybear.library.view.image.FastBlur;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.image.processor.ProcessorInterface;
import com.sunnybear.library.view.scroll.SupportScrollView;

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
    SupportScrollView sv_me;
//    @Bind(R.id.btn_pay)
//    IndicatorButton btn_pay;//待付款
//    @Bind(R.id.btn_deliver)
//    IndicatorButton btn_deliver;//待发货
//    @Bind(R.id.btn_take_deliver)
//    IndicatorButton btn_take_deliver;//待收货
//    @Bind(R.id.btn_after_sale)
//    IndicatorButton btn_after_sale;//售后
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
    private boolean isScroll;


    private int mHeadHeight;
    private ViewGroup.LayoutParams mHeadLayoutParams;

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
        sv_me.setOnTouchListener(this);
        ll_me.getParent().requestDisallowInterceptTouchEvent(false);
        getUserInfo();
//        getOrderCount();
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        setDefaultBlur();
        isPrepared = true;
        sv_me.setOnScrollListener(new SupportScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                isScroll = scrollY > 0;
            }
        });

        //添加后加载器方法
        iv_user_icon.addProcessor(new ProcessorInterface() {
            @Override
            public void process(Context context, Bitmap bitmap) {
                EventBusHelper.post(FastBlur.doBlur(bitmap, 50, false), ME_BLUR);
            }
        });
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
//            getOrderCount();
            getUserInfo();
        }
        refreshMeDot("");
    }

//    /**
//     * 订单上数字没有登录则不显示
//     */
//    private void hideNum() {
//        btn_pay.hide();
//        btn_deliver.hide();
//        btn_take_deliver.hide();
//        btn_after_sale.hide();
//    }

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
                        EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
                        tv_user_nickname.setText(result.getNick_name());
                        if (mImg.equals(result.getHead_img())) {
                            if (TextUtils.isEmpty(result.getHead_img())) setDefaultBlur();
                            loading.dismiss();
                            return;
                        }
                        mImg = result.getHead_img();
                        iv_user_icon.setImageURL(ImageUtils.getImageSizeUrl(result.getHead_img(), ImageUtils.ImageSizeURL.SIZE_120x120), true);
                        if (TextUtils.isEmpty(mImg)) {
                            setDefaultBlur();
                            return;
                        }
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

    private int payCount = 0;
    private int payDeliver = 0;
    private int payTakeDeliver = 0;
    private int payAfterSale = 0;

//    /**
//     * 获得订单数量
//     */
//    private void getOrderCount() {
//        if (TextUtils.isEmpty(AccountHelper.getCurrentUid())) return;
//        networkRequest(OrderApi.getOrderCount(), new SimpleFastJsonCallback<OrderCount>(OrderCount.class, loading) {
//            @Override
//            public void onSuccess(String url, OrderCount result) {
//                Logger.d(result.toString());
//                setOrderEmpty(payCount, result.getUnpaid().getNum(), btn_pay);
//                setOrderEmpty(payDeliver, result.getUndelivery().getNum(), btn_deliver);
//                setOrderEmpty(payTakeDeliver, result.getUnCheck().getNum(), btn_take_deliver);
//                setOrderEmpty(payAfterSale, result.getService().getNum(), btn_after_sale);
//                /*payCount = result.getUnpaid().getNum();
//                payDeliver = result.getUndelivery().getNum();
//                payTakeDeliver = result.getUnCheck().getNum();
//                payAfterSale = result.getService().getNum();*/
//                loading.dismiss();
//            }
//
//        }, false);
//    }

//
//    private void setOrderEmpty(int payCount, int newCount, IndicatorButton btn_pay) {
//        /*if (newCount == 0) {
//            btn_pay.hide();
//            return;
//        }
//        if (payCount != newCount) {
//        }*/
////        btn_pay.hide();
//        btn_pay.show(newCount);
//
//    }

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
//        getOrderCount();
    }

    @Subcriber(tag = SettingActivity.EVENT_LOGOUT)
    public void eventLogout(String tag) {
        mImg = "";
//        hideNum();
        iv_user_icon.setDefaultImage(R.drawable.img_head_default);
        tv_user_nickname.setText("葡星人");
    }

    @OnClick({R.id.iv_setting, R.id.iv_capture, R.id.si_order, R.id.si_address, /*R.id.si_action,*/ R.id.si_question, R.id.iv_user_icon
            , R.id.si_child_info, R.id.si_message, /*R.id.btn_pay, R.id.btn_deliver, R.id.btn_take_deliver, R.id.btn_after_sale,*/ R.id.si_participation, R.id.si_collection})
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        if (!AccountHelper.isLogin()) {
            toLoginActivity(v, bundle);
            return;
        }
        switch (v.getId()) {
            case R.id.iv_capture://扫一扫
                YouMengHelper.onEvent(mActivity, YouMengHelper.UserHome_cell_item, "扫一扫");
                startActivity(CaptureActivity.class);
                break;
            case R.id.iv_setting:
                YouMengHelper.onEvent(mActivity, YouMengHelper.UserHome_cell_item, "设置");
                startActivity(SettingActivity.class);
                break;
            case R.id.si_order://我的订单
                YouMengHelper.onEvent(mActivity, YouMengHelper.UserHome_cell_item, "我的订单");
                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_ALL);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.si_child_info://孩子信息
                YouMengHelper.onEvent(mActivity, YouMengHelper.UserHome_cell_item, "孩子信息");
                startActivity(ChildInfoActivity.class);
                break;
            case R.id.si_address://收货地址
                YouMengHelper.onEvent(mActivity, YouMengHelper.UserHome_cell_item, "收货地址");
                startActivity(AddressListActivity.class);
                break;
            /*case R.id.si_action://我参与的活动
                startActivity(MyActionsActivity.class);
//                startActivity(WriteOrderActivity.class);
                break;*/
            case R.id.si_question://葡萄籽
//                startActivity(QuestionActivity.class);
                YouMengHelper.onEvent(mActivity, YouMengHelper.UserHome_cell_item, "葡萄籽");
                startActivity(GrapestoneActivity.class);
                break;
            case R.id.iv_user_icon://完善用户信息
                startActivity(CompleteActivity.class);
                break;
            case R.id.si_participation://我的参与
                YouMengHelper.onEvent(mActivity, YouMengHelper.UserHome_cell_item, "我的参与");
                startActivity(ParticipationActivity.class);
                break;
            case R.id.si_collection:
                YouMengHelper.onEvent(mActivity, YouMengHelper.UserHome_cell_item, "我的收藏");
                startActivity(CollectionActivity.class);
                break;
            case R.id.si_message://消息中心
                YouMengHelper.onEvent(mActivity, YouMengHelper.UserHome_cell_item, "消息中心");
                startActivity(MessageCenterActivity.class);
                break;
//            case R.id.btn_pay://待付款
//                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_PAY);
//                startActivity(OrderListActivity.class, bundle);
//                break;
//            case R.id.btn_deliver://待发货\
//                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_SHIPMENT);
//                startActivity(OrderListActivity.class, bundle);
//                break;
//            case R.id.btn_take_deliver://待收货
//                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_SIGN);
//                startActivity(OrderListActivity.class, bundle);
//                break;
//            case R.id.btn_after_sale://售后
//                YouMengHelper.onEvent(mActivity, YouMengHelper.UserHome_cell_item, "售后");
//                startActivity(ServiceListActivity.class, bundle);
//                break;
        }
    }

    /**
     * put进目标activity供登录后跳转
     */
    private void toLoginActivity(View v, Bundle bundle) {
        switch (v.getId()) {
            case R.id.iv_capture:
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CaptureActivity.class);
                break;
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
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, IndexActivity.class);
                break;
         /*   case R.id.si_concerns://我的关注
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, ConcernsActivity.class);
                break;*/
            case R.id.si_participation://我的参与
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, ParticipationActivity.class);
                break;
            case R.id.si_collection://我的收藏
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CollectionActivity.class);
                break;
            case R.id.si_message://消息中心
                si_message.hide();
//                mDiskFileCacheHelper.remove(RedDotReceiver.ME_MESSAGECENTER + AccountHelper.getCurrentUid());
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, MessageCenterActivity.class);
                break;
//            case R.id.btn_pay://待付款
//                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_PAY);
//                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, OrderListActivity.class);
//                break;
//            case R.id.btn_deliver://待发货
//                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_SHIPMENT);
//                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, OrderListActivity.class);
//                break;
//            case R.id.btn_take_deliver://待收货
//                bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_SIGN);
//                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, OrderListActivity.class);
//                break;
//            case R.id.btn_after_sale://售后
//                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, ServiceListActivity.class);
//                break;
        }
        startActivity(LoginActivity.class, bundle);
    }

    @Subcriber(tag = ME_BLUR)
    private void setBlur(Bitmap bitmap) {
        iv_user_icon_background.setDefaultImage(bitmap);
    }

    private boolean isClick = true;
    int height;

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
                if (isScroll) return false;
                height = mHeadLayoutParams.height + (newY - y) / 3;
//                rl_user_head_icon.getLocationOnScreen(position);
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
//                    getOrderCount();
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
//                    getOrderCount();
                }
                /*if (null != AccountHelper.getCurrentUserInfo()) {
                    try {
                        new NettyClientBootstrap();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }*/
                break;
        }
        return true;
    }

    @Subcriber(tag = GPushCallback.MESSAGECENTER)
    private void setDot(String me_tabbar) {
        si_message.show();
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_ME_TAB)
    private void refreshMeDot(String tab) {
        if (RedDotUtils.showMessageCenterDot())
            si_message.show();
        else
            si_message.hide();
    }
}
