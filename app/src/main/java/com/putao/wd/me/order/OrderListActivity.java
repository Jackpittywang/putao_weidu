package com.putao.wd.me.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.order.adapter.OrderListAdapter;
import com.putao.wd.me.service.ServiceChooseActivity;
import com.putao.wd.model.Express;
import com.putao.wd.model.Order;
import com.putao.wd.model.OrderDetail;
import com.putao.wd.store.pay.PaySuccessActivity;
import com.putao.wd.util.AlipayHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;

import java.util.ArrayList;

import butterknife.Bind;


/**
 * 订单列表
 * Created by yanguoqiang on 15/11/27.
 */
public class OrderListActivity extends PTWDActivity implements TitleBar.OnTitleItemSelectedListener {
    public static final String TYPE_INDEX = "current_index";

    public static final String TYPE_ALL = "0";//全部
    public static final String TYPE_WAITING_PAY = "1";//待付款
    public static final String TYPE_WAITING_SHIPMENT = "2";//待发货
    public static final String TYPE_WAITING_SIGN = "3";//等待签收

    @Bind(R.id.rv_order)
    LoadMoreRecyclerView rv_order;
    @Bind(R.id.rl_no_order)
    RelativeLayout rl_no_order;//没有order时的布局

    @Bind(R.id.ll_title_bar)
    TitleBar ll_title;

    private OrderListAdapter adapter;

    private int currentItem;//当前选中项目
    private int currentPage = 1;
    private String currentType = TYPE_ALL;

    private AlipayHelper mAlipayHelper;
    private String order_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }


    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        loading.show();
        addNavigation();
        currentType = args.getString(TYPE_INDEX, TYPE_ALL);
        adapter = new OrderListAdapter(mContext, null);
        rv_order.setAdapter(adapter);
        addListener();
        setCurrentItem();
        getOrderLists(currentType, String.valueOf(currentPage));

        mAlipayHelper = new AlipayHelper();
        mAlipayHelper.setOnAlipayCallback(new AlipayHelper.OnAlipayCallback() {
            @Override
            public void onPayResult(boolean isSuccess, String msg) {
                if (isSuccess) {
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderDetailActivity.KEY_ORDER, order_id);
                    startActivity(PaySuccessActivity.class, bundle);
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
        });
    }

    /**
     * 设置当前选中项
     */
    private void setCurrentItem() {
        switch (currentType) {
            case TYPE_ALL:
                ll_title.selectTitleItem(R.id.ll_all);
                break;
            case TYPE_WAITING_PAY:
                ll_title.selectTitleItem(R.id.ll_waiting_pay);
                break;
            case TYPE_WAITING_SHIPMENT:
                ll_title.selectTitleItem(R.id.ll_waiting_shipment);
                break;
            case TYPE_WAITING_SIGN:
                ll_title.selectTitleItem(R.id.ll_waiting_sign);
                break;
        }
    }

    private void addListener() {
        rv_order.setOnItemClickListener(new OnItemClickListener<Order>() {
            @Override
            public void onItemClick(Order order, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(OrderDetailActivity.KEY_ORDER, order.getId());
                startActivity(OrderDetailActivity.class, bundle);
            }
        });
        rv_order.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getOrderLists(currentType, String.valueOf(currentPage));
            }
        });
        ll_title.setOnTitleItemSelectedListener(this);
    }

    /**
     * 订单列表
     */
    private void getOrderLists(String type, String page) {
        networkRequest(OrderApi.getOrderLists(type, page),
                new SimpleFastJsonCallback<ArrayList<Order>>(Order.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Order> result) {
                        if (result != null && result.size() > 0) {
                            adapter.clear();
                            rl_no_order.setVisibility(View.GONE);
                            adapter.addAll(result);
                            rv_order.loadMoreComplete();
                            currentPage++;
                        } else if (currentPage > 1) {
                            rv_order.loadMoreComplete();
                            rv_order.noMoreLoading();
                        } else {
                            rv_order.loadMoreComplete();
                            rl_no_order.setVisibility(View.VISIBLE);
                        }
                        loading.dismiss();
                    }
                });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 处理上方分类点击事件
     *
     * @param item
     * @param position
     */
    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        currentPage = 1;
        switch (item.getId()) {
            case R.id.ll_all://全部
                currentType = TYPE_ALL;
                break;
            case R.id.ll_waiting_pay://待付款
                currentType = TYPE_WAITING_PAY;
                break;
            case R.id.ll_waiting_shipment://待发货
                currentType = TYPE_WAITING_SHIPMENT;
                break;
            case R.id.ll_waiting_sign://等待签收
                currentType = TYPE_WAITING_SIGN;
                break;
        }
        networkRequest(OrderApi.getOrderLists(currentType, String.valueOf(currentPage)),
                new SimpleFastJsonCallback<ArrayList<Order>>(Order.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Order> result) {
                        adapter.replaceAll(result);
                        if (result != null && result.size() > 0)
                            rl_no_order.setVisibility(View.GONE);
                        else
                            rl_no_order.setVisibility(View.VISIBLE);
                        loading.dismiss();
                    }
                });
    }

    /**
     * 取消订单
     */
    @Subcriber(tag = OrderListAdapter.EVENT_CANCEL_ORDER)
    public void eventCancelOrder(Order order) {
        showDialog(order);
    }


    /**
     * 申请售后
     */
    @Subcriber(tag = OrderListAdapter.EVENT_SALE_SERVICE)
    public void eventSaleService(String orderId) {
        IndexActivity.isNotRefreshUserInfo = false;
        Bundle bundle = new Bundle();
        bundle.putString(ServiceChooseActivity.ORDER_ID, orderId);
        startActivity(ServiceChooseActivity.class, bundle);
    }

    /**
     * 查看物流
     */
    @Subcriber(tag = OrderListAdapter.EVENT_AOPPLY_REFUND)
    public void quereRefund(Order order) {
        Bundle bundle = new Bundle();
        ArrayList<Express> expresses = order.getExpress();
        bundle.putSerializable(OrderShipmentDetailActivity.EXPRESS, expresses);
//        bundle.putInt(OrderShipmentDetailActivity.PACKAGECOUNT, expresses.size());
        bundle.putInt(OrderShipmentDetailActivity.PACKAGINDEX, 0);
        startActivity(OrderShipmentDetailActivity.class, bundle);
    }

    /**
     * 立即支付
     *
     * @param order
     */
    @Subcriber(tag = OrderListAdapter.EVENT_PAY)
    public void eventPay(Order order) {
        IndexActivity.isNotRefreshUserInfo = false;
        order_id = order.getId();
        networkRequest(StoreApi.pay(order_id), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                if (!StringUtils.isEmpty(result)) {
                    JSONObject object = JSON.parseObject(result);
                    String orderInfo = object.getString("code");
                    if (StringUtils.isEmpty(orderInfo)) {
                        ToastUtils.showToastShort(mContext, "支付失败");
                        return;
                    }
                    mAlipayHelper.pay((Activity) mContext, orderInfo);
                } else {
                    ToastUtils.showToastLong(mContext, "无法支付");
                }
                loading.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.clear();
    }

    /**
     * 取消订单dialog
     */
    private void showDialog(final Order order) {
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("确定取消")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        networkRequest(OrderApi.orderCancel(order.getId()), new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                IndexActivity.isNotRefreshUserInfo = false;
                                switch (currentType) {
                                    case TYPE_ALL:
                                        Order newOrder = order;
                                        order.setOrderStatusID(OrderCommonState.ORDER_CANCLED);
                                        adapter.replace(order, newOrder);
                                        break;
                                    case TYPE_WAITING_PAY:
                                        adapter.delete(order);
                                        if (adapter.getItemCount()==1)
                                            rl_no_order.setVisibility(View.GONE);
                                        break;
                                }
                                currentPage = 1;
                                loading.dismiss();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    @Subcriber(tag = OrderDetailActivity.CANCEL_ORDER)
    public void detailCancel(OrderDetail orderid) {
        adapter.clear();
        getOrderLists(currentType, String.valueOf(currentPage));
    }


}
