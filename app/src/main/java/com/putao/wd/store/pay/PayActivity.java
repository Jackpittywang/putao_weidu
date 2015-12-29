package com.putao.wd.store.pay;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.OrderSubmitReturn;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 收银台
 * Created by wangou on 2015/12/08.
 */
public class PayActivity extends PTWDActivity implements View.OnClickListener {
    public static final String BUNDLE_ORDER_ID = "order_id";
    public static final String BUNDLE_ORDER_SN = "order_sn";
    public static final String BUNDLE_ORDER_DATE = "order_date";
    public static final String BUNDLE_ORDER_PRICE = "order_price";

    private final int SDK_PAY_FLAG = 1;
    private final int SDK_CHECK_FLAG = 2;

    @Bind(R.id.tv_order_sn)
    TextView tv_order_sn;//订单号
    @Bind(R.id.tv_order_date)
    TextView tv_order_date;//订单日期
    @Bind(R.id.tv_cash_pay_summoney)
    TextView tv_cash_pay_summoney;//订单金额

    private String order_id;
    private String order_sn;
    private String order_date;
    private String order_price;

    private String orderInfo;
    private Handler mHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        order_id = args.getString(BUNDLE_ORDER_ID);
        order_sn = args.getString(BUNDLE_ORDER_SN);
        order_date = args.getString(BUNDLE_ORDER_DATE);
        order_price = args.getString(BUNDLE_ORDER_PRICE);

        initView();
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
                        if (TextUtils.equals(resultStatus, "9000"))
                            ToastUtils.showToastShort(mContext, "支付成功");
                        else
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000"))
                                ToastUtils.showToastShort(mContext, "支付结果确认中");
                            else
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                ToastUtils.showToastShort(mContext, "支付失败");
                        break;
                    case SDK_CHECK_FLAG:
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        ToastUtils.showToastShort(mContext, "检查结果为：" + msg.obj);
                        break;
                }
            }
        };
        pay(order_id);
    }

    /**
     * 初始化页面
     */
    private void initView() {
        tv_order_sn.setText(order_sn);
        tv_order_date.setText(order_date);
        tv_cash_pay_summoney.setText(order_price);
    }

    /**
     * 支付
     *
     * @param order_id
     */
    private void pay(final String order_id) {
        networkRequest(StoreApi.pay(order_id), new SimpleFastJsonCallback<String>(String.class, loading) {
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

    @Override
    protected String[] getRequestUrls() {
        return new String[]{StoreApi.URL_PAY};
    }

    @OnClick(R.id.tv_pay)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay://马上支付
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask((Activity) mContext);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(orderInfo);
                        mHandler.sendMessage(Message.obtain(mHandler, SDK_PAY_FLAG, result));
                    }
                }).start();
                break;
        }
    }
}
