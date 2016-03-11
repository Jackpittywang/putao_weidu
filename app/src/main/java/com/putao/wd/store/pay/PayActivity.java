package com.putao.wd.store.pay;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.order.OrderDetailActivity;
import com.putao.wd.model.OrderSubmitReturn;
import com.putao.wd.util.AlipayHelper;
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
    public static final String BUNDLE_ORDER_INFO = "order_info";

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

    private OrderSubmitReturn mSubmitReturn;

    private String orderInfo;
    private Handler mHandler;

    private AlipayHelper mAlipayHelper;

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
        pay(mSubmitReturn != null ? mSubmitReturn.getOrder_id() : order_id);
    }

    /**
     * 初始化页面
     */

    private void initView() {
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
                if (StringUtils.isEmpty(orderInfo)) {
                    ToastUtils.showToastShort(mContext, "支付失败");
                    return;
                }
                mAlipayHelper.pay((Activity) mContext, orderInfo);
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
        super.onLeftAction();
//        startActivity(IndexActivity.class);
        finish();
    }
}
