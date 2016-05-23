package com.putao.wd.pt_me.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.model.Express;
import com.putao.wd.model.Order;
import com.putao.wd.model.OrderDetail;
import com.putao.wd.model.OrderSubmitReturn;
import com.putao.wd.pt_me.order.adapter.OrderListAdapter;
import com.putao.wd.pt_me.service.ServiceChooseActivity;
import com.putao.wd.pt_store.pay.PayActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by riven_chris on 16/5/23.
 * 我的订单-全部、待付款、待发货、待收货
 */
public class OrderStateListFragment extends BasicFragment<GlobalApplication> {

    @Bind(R.id.rv_order)
    LoadMoreRecyclerView rv_order;
    @Bind(R.id.rl_no_order)
    RelativeLayout rl_no_order;//没有order时的布局

    private OrderListAdapter adapter;

    private int currentPage = 1;
    private String currentType = OrderListActivity.TYPE_ALL;

    //    private AlipayHelper mAlipayHelper;
    private String order_id;

    public static OrderStateListFragment newInstance(Bundle bundle) {
        OrderStateListFragment fragment = new OrderStateListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (args != null)
            currentType = args.getString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_ALL);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_state_list_fragment;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        loading.show();
        adapter = new OrderListAdapter(getActivity(), null);
        rv_order.setAdapter(adapter);
        addListener();

       /* mAlipayHelper = new AlipayHelper();
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
        });*/
    }

    @Override
    protected void onVisible() {
        currentPage = 1;
        if (OrderListActivity.TYPE_ALL.equals(currentType))
            getOrderLists(currentType, String.valueOf(currentPage));
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
                getMoreList();
            }
        });
    }

    /**
     * 订单列表
     */
    private void getOrderLists(String type, String page) {
        networkRequest(OrderApi.getOrderLists(type, page),
                new SimpleFastJsonCallback<ArrayList<Order>>(Order.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Order> result) {
                        rv_order.loadMoreComplete();
                        if (result != null && result.size() > 0) {
                            rl_no_order.setVisibility(View.GONE);
                            adapter.replaceAll(result);
                            currentPage++;
                        } else {
                            if (currentPage <= 1)
                                rl_no_order.setVisibility(View.VISIBLE);
                            rv_order.loadMoreComplete();
                            rv_order.noMoreLoading();
                        }
                        loading.dismiss();
                    }
                });

    }

    private void getMoreList() {
        networkRequest(OrderApi.getOrderLists(currentType, currentPage + ""),
                new SimpleFastJsonCallback<ArrayList<Order>>(Order.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Order> result) {
                        rv_order.loadMoreComplete();
                        if (result != null && result.size() > 0) {
                            adapter.addAll(result);
                            currentPage++;
                        } else {
                            rv_order.noMoreLoading();
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
    public void queryRefund(Order order) {
        checkShipment(order);
    }

    private void checkShipment(Order order) {
        if (null == order.getExpress() || order.getExpress().size() == 0) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("提示")
                    .setMessage("没有物流信息")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loading.dismiss();
                        }
                    })
                    .show();
        } else {
            Bundle bundle = new Bundle();
            ArrayList<Express> expresses = order.getExpress();
            bundle.putSerializable(OrderShipmentDetailActivity.EXPRESS, expresses);
            bundle.putInt(OrderShipmentDetailActivity.PACKAGINDEX, 0);
            startActivity(OrderShipmentDetailActivity.class, bundle);
        }
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
        Bundle bundle = new Bundle();
        OrderSubmitReturn orderSubmitReturn = new OrderSubmitReturn();
        orderSubmitReturn.setPrice(order.getTotal_amount());
        orderSubmitReturn.setOrder_id(order_id);
        orderSubmitReturn.setOrder_sn(order.getOrder_sn());
        orderSubmitReturn.setTime(DateUtils.secondToDate(Integer.parseInt(order.getCreate_time()), "yyyy-MM-dd HH:mm:ss"));
        bundle.putString(OrderDetailActivity.KEY_ORDER, order_id);
        bundle.putSerializable(PayActivity.BUNDLE_ORDER_INFO, orderSubmitReturn);
        startActivity(PayActivity.class, bundle);
       /* networkRequest(StoreApi.aliPay(order_id), new SimpleFastJsonCallback<String>(String.class, loading) {
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
        });*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.clear();
    }

    /**
     * 取消订单dialog
     */
    private void showDialog(final Order order) {
        new AlertDialog.Builder(getActivity())
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
                                    case OrderListActivity.TYPE_ALL:
                                        Order newOrder = order;
                                        order.setOrderStatusID(OrderCommonState.ORDER_CANCLED);
                                        adapter.replace(order, newOrder);
                                        break;
                                    case OrderListActivity.TYPE_WAITING_PAY:
                                        adapter.delete(order);
                                        if (adapter.getItemCount() == 1)
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
    public void detailCancel(OrderDetail orderId) {
        adapter.clear();
        getOrderLists(currentType, String.valueOf(currentPage));
    }
}
