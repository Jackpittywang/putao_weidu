package com.putao.wd.me.order.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.OrderDto;
import com.putao.wd.me.order.OrderCommonState;
import com.putao.wd.store.order.WriteOrderActivity;
import com.putao.wd.store.order.adapter.OrdersAdapter;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 订单列表适配器
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderListAdapter extends LoadMoreAdapter<OrderDto, OrderListAdapter.OrderListViewHolder> {

    private Context mContext;
    private OrdersAdapter mOrderAdapter;
    private OrderListViewHolder mHolder;
    private String mOrderId;
    List<OrderDto> mItems;
    private int mOrderState;

    public OrderListAdapter(Context context, List<OrderDto> orderList) {
        super(context, orderList);
        this.mContext = context;
        mOrderAdapter = new OrdersAdapter(mContext, WriteOrderActivity.getTestData());
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_order_list_item;
    }

    @Override
    public OrderListViewHolder getViewHolder(View itemView, int viewType) {
        return new OrderListViewHolder(itemView);
    }

    @Override
    public void onBindItem(OrderListViewHolder holder, final OrderDto order, final int position) {
        mHolder = holder;
        mOrderState = order.getOrderStatus();
        mOrderId = order.getOrderNo();
        mItems = getItems();
        setUiStyle(mItems.get(position).getOrderStatus());
        holder.ll_goods.setAdapter(mOrderAdapter);
        holder.btn_order_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftClick(mItems.get(position).getOrderStatus());
            }
        });
        holder.btn_order_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightClick(mItems.get(position).getOrderStatus());
            }
        });
    }


    /**
     * 右边按钮点击事件
     * @param orderStatus
     */
    private void rightClick(int orderStatus) {
        switch (mOrderState) {
            case OrderCommonState.ORDER_WAITING_PAY:
                pay(mOrderId);
                break;
        }
    }

    /**
     * 马上支付
     * @param mOrderId 订单号
     */
    private void pay(String mOrderId) {
        ToastUtils.showToastShort(mContext, "马上支付");
    }

    /**
     * 左边按钮点击事件
     * @param mOrderState
     */
    private void leftClick(int mOrderState) {
        switch (mOrderState) {
            case OrderCommonState.ORDER_WAITING_PAY:
                orderCancel(mOrderId);
                break;
            case OrderCommonState.ORDER_WAITING_SHIPMENT:
                refund(mOrderId);
                break;
            case OrderCommonState.ORDER_WAITING_SIGN:
                express(mOrderId);
                break;
            case OrderCommonState.ORDER_SALE_SERVICE:
                afterSale(mOrderId);
                break;
            case OrderCommonState.ORDER_NO_SIGN:
                afterSale(mOrderId);
                break;
        }
    }


    /**
     * 设置订单不同状态下的显示
     *
     * @param orderStatus 订单状态
     */
    private void setUiStyle(int orderStatus) {
        switch (orderStatus) {
            case OrderCommonState.ORDER_WAITING_PAY:
                mHolder.tv_order_status.setText("待支付");
                mHolder.tv_order_status.setTextColor(Color.RED);
                mHolder.rl_comfirm.setVisibility(View.VISIBLE);
                mHolder.tv_order_hint.setVisibility(View.GONE);
                mHolder.btn_order_left.setText("取消订单");
                mHolder.btn_order_right.setVisibility(View.VISIBLE);
                mHolder.btn_order_right.setText("马上支付");
                break;
            case OrderCommonState.ORDER_CANCLED:
                mHolder.tv_order_status.setText("已取消");
                mHolder.tv_order_status.setTextColor(0xFF313131);
                mHolder.rl_comfirm.setVisibility(View.GONE);
                break;

            case OrderCommonState.ORDER_WAITING_SHIPMENT:
                mHolder.tv_order_status.setText("待发货");
                mHolder.tv_order_status.setTextColor(0xFF313131);
                mHolder.rl_comfirm.setVisibility(View.VISIBLE);
                mHolder.btn_order_left.setText("申请退款");
                mHolder.tv_order_hint.setVisibility(View.GONE);
                mHolder.btn_order_right.setVisibility(View.GONE);
                break;

            case OrderCommonState.ORDER_WAITING_SIGN:
                mHolder.tv_order_status.setText("已发货");
                mHolder.tv_order_status.setTextColor(0xFF313131);
                mHolder.rl_comfirm.setVisibility(View.VISIBLE);
                mHolder.btn_order_left.setText("查看物流");
                mHolder.tv_order_hint.setVisibility(View.GONE);
                mHolder.btn_order_right.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_SALE_SERVICE:
                mHolder.tv_order_status.setText("已完成");
                mHolder.tv_order_status.setTextColor(0xFF313131);
                mHolder.rl_comfirm.setVisibility(View.VISIBLE);
                mHolder.btn_order_left.setText("申请售后");
                mHolder.tv_order_hint.setVisibility(View.VISIBLE);
                mHolder.tv_order_hint.setText("签收15日内可申请售后");
                mHolder.btn_order_right.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_NO_SIGN:
                mHolder.tv_order_status.setText("退签");
                mHolder.tv_order_status.setTextColor(0xFF313131);
                mHolder.rl_comfirm.setVisibility(View.VISIBLE);
                mHolder.btn_order_left.setText("申请售后");
                mHolder.tv_order_hint.setVisibility(View.VISIBLE);
                mHolder.tv_order_hint.setText("退签7日内可申请售后");
                mHolder.btn_order_right.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 申请售后
     */
    private void afterSale(String mOrderId) {
        ToastUtils.showToastShort(mContext, "申请售后");

    }

    /**
     * 查看物流
     *
     * @param mOrderId
     */
    private void express(String mOrderId) {
        ToastUtils.showToastShort(mContext, "查看物流");
    }


    /**
     * 申请退款
     *
     * @param orderId
     */
    private void refund(String orderId) {
        ToastUtils.showToastShort(mContext, "申请退款");
    }

    /**
     * 取消订单
     *
     * @param orderId
     */
    private void orderCancel(String orderId) {
        ToastUtils.showToastShort(mContext, "取消订单");
    }

    /**
     * 收货人地址布局
     */
    static class OrderListViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_order_no)
        TextView tv_order_no;
        @Bind(R.id.tv_order_purchase_time)
        TextView tv_order_purchase_time;
        @Bind(R.id.tv_order_status)
        TextView tv_order_status;//订单状态
        @Bind(R.id.stickyHeaderLayout_scrollable)
        BasicRecyclerView ll_goods;
        @Bind(R.id.rl_comfirm)
        LinearLayout rl_comfirm; //操作栏布局
        @Bind(R.id.tv_order_hint)
        TextView tv_order_hint; //左边按钮左侧的提示信息
        @Bind(R.id.btn_order_left)
        Button btn_order_left;//左边按钮
        @Bind(R.id.btn_order_right)
        Button btn_order_right;//右边按钮

        public OrderListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
