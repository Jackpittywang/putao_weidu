package com.putao.wd.me.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.order.adapter.OrderListAdapter;
import com.putao.wd.model.Order;
import com.putao.wd.store.pay.PayActivity;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;
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

    private String TAG = OrderListActivity.class.getName();
    private OrderListAdapter adapter;

    private int currentItem;//当前选中项目
    private int currentPage = 1;
    private String currentType = TYPE_ALL;

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
                            rl_no_order.setVisibility(View.GONE);
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

    @Subcriber(tag = OrderListAdapter.EVENT_CANCEL_ORDER)
    public void eventCancelOrder(String mId) {
        networkRequest(OrderApi.orderCancel(mId), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                loading.show();
                adapter.clear();
                currentPage = 1;
                getOrderLists(currentType, String.valueOf(currentPage));
                loading.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.clear();
    }

    /*
    @Subcriber(tag = OrderListAdapter.EVENT_AOPPLY_REFUND)
    public void eventAopplyRefund(String mId) {
        networkRequest(OrderApi.orderCancel(mId), new SimpleFastJsonCallback<ArrayList<Order>>(Order.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Order> result) {
                loading.dismiss();
            }
        });
    }

    @Subcriber(tag = OrderListAdapter.EVENT_LOOK_LOGISTICS)
    public void eventLookLogistics(String mId) {
        networkRequest(OrderApi.orderCancel(mId), new SimpleFastJsonCallback<ArrayList<Order>>(Order.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Order> result) {
                loading.dismiss();
            }
        });
    }

    @Subcriber(tag = OrderListAdapter.EVENT_SALE_SERVICE)
    public void eventSaleService(String mId) {
        networkRequest(OrderApi.orderCancel(mId), new SimpleFastJsonCallback<ArrayList<Order>>(Order.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Order> result) {
                loading.dismiss();
            }
        });
    }
*/
    @Subcriber(tag = OrderListAdapter.EVENT_PAY)
    public void eventPay(Order order) {
        Bundle bundle = new Bundle();
        bundle.putString(PayActivity.BUNDLE_ORDER_ID, order.getId());
        bundle.putString(PayActivity.BUNDLE_ORDER_SN, order.getOrder_sn());
        bundle.putString(PayActivity.BUNDLE_ORDER_PRICE, order.getTotal_amount());
        bundle.putString(PayActivity.BUNDLE_ORDER_DATE, order.getCreate_time());
        startActivity(PayActivity.class, bundle);
    }
}
