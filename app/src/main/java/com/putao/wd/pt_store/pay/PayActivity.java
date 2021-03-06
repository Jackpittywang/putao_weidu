package com.putao.wd.pt_store.pay;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.pt_me.order.OrderDetailActivity;
import com.putao.wd.model.OrderSubmitReturn;
import com.putao.wd.model.WeixPayResult;
import com.putao.wd.util.AlipayHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.SwitchButton;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 收银台
 * Created by wangou on 2015/12/08.
 */
public class PayActivity extends PTWDActivity implements View.OnClickListener {
    public static final String BUNDLE_ORDER_INFO = "order_info";

    public static final String EVENT_ORDER_ID = "event_order_id";
    public static final String BUNDLE_ORDER_SN = "order_sn";
    public static final String BUNDLE_ORDER_DATE = "order_date";
    public static final String BUNDLE_ORDER_PRICE = "order_price";

//    private final int SDK_PAY_FLAG = 1;
//    private final int SDK_CHECK_FLAG = 2;

    private final int ALI_PAY = 1;
    private final int WEIX_PAY = 2;

    @Bind(R.id.tv_order_sn)
    TextView tv_order_sn;//订单号
    @Bind(R.id.tv_order_date)
    TextView tv_order_date;//订单日期
    @Bind(R.id.tv_cash_pay_summoney)
    TextView tv_cash_pay_summoney;//订单金额
    @Bind(R.id.sb_alipay_point)
    SwitchButton sb_alipay_point;
    @Bind(R.id.sb_weixinpay_point)
    SwitchButton sb_weixinpay_point;

    private String order_id;
    private String order_sn;
    private String order_date;
    private String order_price;
    private int pay_way;
    private WeixPayResult mWeixPayResult;

    private OrderSubmitReturn mSubmitReturn;

    private String orderInfo;
    private Handler mHandler;

    private AlipayHelper mAlipayHelper;
    private IWXAPI mMsgApi;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mSubmitReturn = (OrderSubmitReturn) args.getSerializable(BUNDLE_ORDER_INFO);
        order_id = args.getString(OrderDetailActivity.KEY_ORDER);
        order_sn = args.getString(BUNDLE_ORDER_SN);
        order_date = args.getString(BUNDLE_ORDER_DATE);
        order_price = args.getString(BUNDLE_ORDER_PRICE);
        pay_way = ALI_PAY;
        initView();
        mAlipayHelper = new AlipayHelper();
        mAlipayHelper.setOnAlipayCallback(
                new AlipayHelper.OnAlipayCallback() {
                    @Override
                    public void onPayResult(boolean isSuccess, String msg) {
                        if (isSuccess) {
                            Bundle bundle = new Bundle();
                            bundle.putString(OrderDetailActivity.KEY_ORDER, order_id);
                            startActivity(PaySuccessActivity.class, bundle);
                            IndexActivity.isNotRefreshUserInfo = false;
                        } else {
                            ToastUtils.showToastShort(mContext, "支付失败");
                        }
                    }

                    @Override
                    public void onPayVerify(String msg) {
                        ToastUtils.showToastShort(mContext, msg);
                    }

                    @Override
                    public void onPayCancel(String msg) {
                        ToastUtils.showToastShort(mContext, "检查结果为：" + msg);
                    }
                }

        );
        mMsgApi = WXAPIFactory.createWXAPI(this, null);
        //获取支付宝的预支付订单id
        aliPay(mSubmitReturn != null ? mSubmitReturn.getOrder_id() : order_id);
        //获取微信支付的预支付订单id
        weixPay(mSubmitReturn != null ? mSubmitReturn.getOrder_id() : order_id);
    }

    /**
     * 初始化页面
     */

    private void initView() {
        sb_alipay_point.setEnabled(false);
        sb_weixinpay_point.setEnabled(false);
        if (mSubmitReturn != null) {
            tv_order_sn.setText(mSubmitReturn.getOrder_sn());
            tv_order_date.setText(mSubmitReturn.getTime());
            tv_cash_pay_summoney.setText(mSubmitReturn.getPrice());
        } else {
            tv_order_sn.setText(order_sn);
            tv_order_date.setText(order_date);
            tv_cash_pay_summoney.setText(order_price);
        }
    }

    /**
     * 支付宝支付
     *
     * @param order_id
     */
    private void aliPay(final String order_id) {
        networkRequest(StoreApi.aliPay(order_id), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                if (!StringUtils.isEmpty(result)) {
                    JSONObject object = JSON.parseObject(result);
                    orderInfo = object.getString("code");
                } else {
                    ToastUtils.showToastLong(mContext, "无法支付");
                }
                loading.dismiss();
            }
        });
    }

    /**
     * 微信支付
     *
     * @param order_id
     */
    private void weixPay(final String order_id) {
        networkRequest(StoreApi.weixPay(order_id), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                if (!StringUtils.isEmpty(result)) {
                    JSONObject object = JSON.parseObject(result);
                    mWeixPayResult = JSON.parseObject(object.getString("code"), WeixPayResult.class);
                } else {
                    ToastUtils.showToastLong(mContext, "无法支付");
                }
                loading.dismiss();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[]{StoreApi.URL_ALIPAY};
    }

    @OnClick({R.id.tv_pay, R.id.ll_alipay, R.id.ll_weixpay})
    @Override
    public void onClick(View v) {
        PayReq request = null;
        switch (v.getId()) {
            case R.id.tv_pay://马上支付
                if (StringUtils.isEmpty(orderInfo)) {
                    ToastUtils.showToastShort(mContext, "支付失败");
                    return;
                }
                if (ALI_PAY == pay_way)
                    mAlipayHelper.pay((Activity) mContext, orderInfo);
                else if (WEIX_PAY == pay_way) {
                    GlobalApplication.orderId = order_id;
                    if (null == mWeixPayResult) {
                        ToastUtils.showToastShort(mContext, "微信订单生成失败");
                        return;
                    }
                    if (null == request) {
                        request = new PayReq();
                        request.appId = mWeixPayResult.getAppid();
                        request.partnerId = mWeixPayResult.getPartnerid();
                        request.prepayId = mWeixPayResult.getPrepayid();
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr = mWeixPayResult.getNoncestr();
                        request.timeStamp = mWeixPayResult.getTimestamp();
                        request.sign = mWeixPayResult.getSign();
                    }
                    mMsgApi.sendReq(request);
                    weixPay(mSubmitReturn != null ? mSubmitReturn.getOrder_id() : order_id);
                }
                break;
            case R.id.ll_alipay://选择支付宝支付
                sb_weixinpay_point.setState(false);
                sb_alipay_point.setState(true);
                pay_way = ALI_PAY;
                break;
            case R.id.ll_weixpay://选择微信支付
                sb_weixinpay_point.setState(true);
                sb_alipay_point.setState(false);
                pay_way = WEIX_PAY;
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
//            startActivity(IndexActivity.class);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onLeftAction() {
        YouMengHelper.onEvent(mContext, YouMengHelper.Cashier_cancel);
        super.onLeftAction();
//        startActivity(IndexActivity.class);
        finish();
    }
}
