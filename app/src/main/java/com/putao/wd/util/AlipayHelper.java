package com.putao.wd.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.putao.wd.pt_store.pay.PayResult;

/**
 * Created by guchenkai on 2016/1/7.
 */
public class AlipayHelper {
    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_CHECK_FLAG = 2;

    private Handler mHandler;

    public OnAlipayCallback mOnAlipayCallback;

    public void setOnAlipayCallback(OnAlipayCallback onAlipayCallback) {
        mOnAlipayCallback = onAlipayCallback;
    }

    public AlipayHelper() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG:
                        PayResult payResult = new PayResult((String) msg.obj);
                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                        String resultInfo = payResult.getResult();
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000") && mOnAlipayCallback != null)
                            mOnAlipayCallback.onPayResult(true, null);
                        else
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000") && mOnAlipayCallback != null)
                                mOnAlipayCallback.onPayVerify("支付结果确认中");
                            else
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                mOnAlipayCallback.onPayResult(false, "支付失败");
                        break;
                    case SDK_CHECK_FLAG:
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        mOnAlipayCallback.onPayCancel((String) msg.obj);
                        break;
                }
            }
        };
    }

    /**
     * 支付
     *
     * @param activity
     * @param orderInfo
     */
    public void pay(final Activity activity, final String orderInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(orderInfo);
                mHandler.sendMessage(Message.obtain(mHandler, SDK_PAY_FLAG, result));
            }
        }).start();
    }

    /**
     * 支付宝支付回调
     */
    public interface OnAlipayCallback {

        void onPayResult(boolean isSuccess, String msg);

        void onPayVerify(String msg);

        void onPayCancel(String msg);
    }
}
