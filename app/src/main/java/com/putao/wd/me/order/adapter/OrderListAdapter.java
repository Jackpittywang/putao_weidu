package com.putao.wd.me.order.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.ColorConstant;
import com.putao.wd.R;
import com.putao.wd.me.order.OrderCommonState;
import com.putao.wd.model.Order;
import com.putao.wd.model.OrderProduct;
import com.putao.wd.store.order.adapter.OrdersAdapter;
import com.putao.wd.store.product.ProductDetailActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.List;

import butterknife.Bind;

/**
 * 订单列表适配器
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderListAdapter extends LoadMoreAdapter<Order, OrderListAdapter.OrderListViewHolder> {
    public static final String EVENT_CANCEL_ORDER = "cancel_order";
    public static final String EVENT_AOPPLY_REFUND = "aopply_refund";
    public static final String EVENT_LOOK_LOGISTICS = "look_logistics";
    public static final String EVENT_SALE_SERVICE = "sale_service";
    public static final String EVENT_PAY = "pay";

    private OrdersAdapter adapter;
    private int mOrderStatus;

    public OrderListAdapter(Context context, List<Order> orders) {
        super(context, orders);
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
    public void onBindItem(OrderListViewHolder holder, final Order order, final int position) {
        mOrderStatus = order.getOrderStatusID();
        checkOrder(holder, mOrderStatus);
        if (3 == order.getShipping_status()) {
            holder.tv_order_status.setText("退签");
            holder.tv_order_status.setTextColor(ColorConstant.TEXT_COLOR);
            setBtn(holder, "退签7日可申请售后", "申请售后", "");
        }
        holder.btn_order_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftClick(order);
            }
        });
        holder.btn_order_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightClick(order);
            }
        });
        holder.tv_order_no.setText(order.getOrder_sn());
        holder.tv_order_purchase_time.setText(DateUtils.secondToDate(Integer.parseInt(order.getCreate_time()), "yyyy-MM-dd HH:mm:ss"));
        holder.tv_order_sum_count.setText(order.getTotal_quantity() + "");
        holder.tv_sum_money.setText(order.getTotal_amount() + "");
        holder.tv_sum_carriage.setText(order.getExpress_money());
        adapter = new OrdersAdapter(context, order.getProduct());
        holder.rv_orders.setVisibility(View.VISIBLE);
        holder.rv_orders.setAdapter(adapter);
        holder.rv_orders.setFocusable(false);
        holder.rv_orders.setClickable(false);
        /*holder.rv_orders.setOnItemClickListener(new OnItemClickListener<OrderProduct>() {
            @Override
            public void onItemClick(OrderProduct product, int position) {
                Bundle bundle = new Bundle();
//                bundle.putString(ProductDetailActivity.BUNDLE_PRODUCT_ID, product.getProduct_id());
                bundle.putSerializable(ProductDetailActivity.BUNDLE_PRODUCT, product);
                context.startActivity(ProductDetailActivity.class, bundle);
            }
        });*/
    }

    private void rightClick(Order order) {
        int orderStatus = order.getOrderStatusID();
        switch (orderStatus) {
            case OrderCommonState.ORDER_PAY_WAIT:
//                ToastUtils.showToastShort(mContext, "马上支付");
                EventBusHelper.post(order, EVENT_PAY);
                break;
        }
    }

    /**
     * 左边按钮点击事件
     *
     * @param order 订单
     */
    private void leftClick(Order order) {
        int orderStatus = order.getOrderStatusID();
        switch (orderStatus) {//取消订单按钮
            case OrderCommonState.ORDER_PAY_WAIT://待支付
                EventBusHelper.post(order, EVENT_CANCEL_ORDER);
                break;
            case OrderCommonState.ORDER_WAITING_SHIPMENT:
//                EventBusHelper.post(order.getId(), EVENT_AOPPLY_REFUND);
                EventBusHelper.post(order.getId(), EVENT_SALE_SERVICE);
//                ToastUtils.showToastShort(mContext, "申请退款");
                break;
            case OrderCommonState.ORDER_WAITING_SIGN://已发货
                EventBusHelper.post(order, EVENT_AOPPLY_REFUND);
//                ToastUtils.showToastShort(mContext, "查看物流");
                break;
            case OrderCommonState.ORDER_SALE_SERVICE://已收货
                EventBusHelper.post(order, EVENT_AOPPLY_REFUND);
                break;
        }
    }

    private void checkOrder(OrderListViewHolder holder, int orderStatus) {
        switch (orderStatus) {
            case OrderCommonState.ORDER_PAY_WAIT://待支付
                holder.tv_order_status.setText("待支付");
                holder.tv_order_status.setTextColor(Color.RED);
                setBtn(holder, "", "取消订单", "马上支付");
                break;
            case OrderCommonState.ORDER_WAITING_SHIPMENT://待发货
                holder.tv_order_status.setText("待发货");
                holder.tv_order_status.setTextColor(ColorConstant.TEXT_COLOR);
                setBtn(holder, "", "申请退款", "");
                break;
            case OrderCommonState.ORDER_GOOD_PREPARE://正在准备商品中
                holder.tv_order_status.setText("正在准备商品中");
                holder.tv_order_status.setTextColor(ColorConstant.TEXT_COLOR);
                holder.rl_comfirm.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_WAITING_SIGN://待签收
                holder.tv_order_status.setText("已发货");
                holder.tv_order_status.setTextColor(ColorConstant.TEXT_COLOR);
                setBtn(holder, "您可以在签收后15天内申请售后", "查看物流", "");
                break;
            case OrderCommonState.ORDER_SALE_SERVICE://已收货
                holder.tv_order_status.setText("已签收");
                holder.tv_order_status.setTextColor(ColorConstant.TEXT_COLOR);
                setBtn(holder, "您可以在签收后15天内申请售后", "申请售后", "");
                break;
            case OrderCommonState.ORDER_FINISH://已完成
                holder.tv_order_status.setText("已完成");
                holder.tv_order_status.setTextColor(ColorConstant.TEXT_COLOR);
                holder.rl_comfirm.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_CANCLED://订单取消
                holder.tv_order_status.setText("已取消");
                holder.tv_order_status.setTextColor(ColorConstant.TEXT_COLOR);
                holder.rl_comfirm.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_AFTER_SALE:
                holder.tv_order_status.setText("此订单正在申请售后");
                holder.tv_order_status.setTextColor(ColorConstant.TEXT_COLOR);
                holder.rl_comfirm.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_REFUND_FINISH:
                holder.tv_order_status.setText("退款成功");
                holder.tv_order_status.setTextColor(ColorConstant.TEXT_COLOR);
                holder.rl_comfirm.setVisibility(View.GONE);
                break;
            case OrderCommonState.ORDER_EXCEPTION:
                holder.tv_order_status.setText("异常订单");
                holder.tv_order_status.setTextColor(ColorConstant.TEXT_COLOR);
                holder.rl_comfirm.setVisibility(View.GONE);
                break;
            default:
                holder.rl_comfirm.setVisibility(View.GONE);
                holder.tv_order_status.setTextColor(ColorConstant.TEXT_COLOR);
                holder.tv_order_status.setText("异常订单");
                break;
        }
    }

    private void setBtn(OrderListViewHolder holder, String hint, String left, String right) {
        holder.rl_comfirm.setVisibility(View.VISIBLE);
        holder.tv_order_hint.setVisibility(TextUtils.isEmpty(hint) ? View.GONE : View.VISIBLE);
        holder.tv_order_hint.setText(hint);
        holder.btn_order_left.setVisibility(TextUtils.isEmpty(left) ? View.GONE : View.VISIBLE);
        holder.btn_order_left.setText(left);
        holder.btn_order_right.setVisibility(TextUtils.isEmpty(right) ? View.GONE : View.VISIBLE);
        holder.btn_order_right.setText(right);
    }

    /**
     * 收货人地址布局
     */
    static class OrderListViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_order_no)
        TextView tv_order_no;//订单号
        @Bind(R.id.tv_order_purchase_time)
        TextView tv_order_purchase_time;//订单时间
        @Bind(R.id.tv_order_status)
        TextView tv_order_status;//订单状态
        @Bind(R.id.rv_orders)
        BasicRecyclerView rv_orders;//订单列表
        @Bind(R.id.tv_order_sum_count)
        TextView tv_order_sum_count;//合计件数
        @Bind(R.id.tv_sum_money)
        TextView tv_sum_money;//合计金额
        @Bind(R.id.tv_sum_carriage)
        TextView tv_sum_carriage;//合计运费
        @Bind(R.id.rl_comfirm)
        LinearLayout rl_comfirm; //操作栏布局
        @Bind(R.id.tv_order_hint)
        TextView tv_order_hint; //取消按钮左侧的提示信息
        @Bind(R.id.btn_order_left)
        Button btn_order_left;//取消
        @Bind(R.id.btn_order_right)
        Button btn_order_right;//订单操作

        public OrderListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
