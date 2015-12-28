package com.putao.wd.me.order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Order;
import com.putao.wd.store.order.adapter.OrdersAdapter;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 订单列表适配器
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderListAdapter extends LoadMoreAdapter<Order, OrderListAdapter.OrderListViewHolder> {
    private OrdersAdapter adapter;

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
    public void onBindItem(OrderListViewHolder holder, Order order, int position) {
        holder.tv_order_no.setText(order.getOrder_sn());
        holder.tv_order_purchase_time.setText(DateUtils.secondToDate(Integer.parseInt(order.getCreate_time()), "yyyy-MM-dd HH:mm:ss"));
        holder.tv_order_sum_count.setText(order.getTotalQuantity() + "");
        holder.tv_sum_noney.setText(order.getTotalPrice() + "");
        adapter = new OrdersAdapter(context, order.getProduct());
        holder.rv_orders.setAdapter(adapter);
    }

    private void changeStyle(int orderStatus) {
//        switch (orderStatus) {
//            case OrderCommonState.ORDER_WAITING_PAY:
//                mHolder.tv_order_status.setText("待支付");
//                mHolder.tv_order_status.setTextColor(Color.RED);
//                mHolder.rl_comfirm.setVisibility(View.VISIBLE);
//                mHolder.tv_order_hint.setVisibility(View.GONE);
//                mHolder.btn_order_operation.setVisibility(View.VISIBLE);
//                mHolder.btn_order_operation.setText("马上支付");
//                break;
//            case OrderCommonState.ORDER_CANCLED:
//                mHolder.tv_order_status.setText("已取消");
//                mHolder.tv_order_status.setTextColor(0xFF313131);
//                mHolder.rl_comfirm.setVisibility(View.GONE);
//                break;
//            case OrderCommonState.ORDER_WAITING_SHIPMENT:
//                mHolder.tv_order_status.setText("待发货");
//                mHolder.tv_order_status.setTextColor(0xFF313131);
//                mHolder.rl_comfirm.setVisibility(View.VISIBLE);
//                mHolder.btn_order_cancel.setText("申请退款");
//                mHolder.tv_order_hint.setVisibility(View.GONE);
//                mHolder.btn_order_operation.setVisibility(View.GONE);
//                break;
//            case OrderCommonState.ORDER_WAITING_SIGN:
//                mHolder.tv_order_status.setText("已发货");
//                mHolder.tv_order_status.setTextColor(0xFF313131);
//                mHolder.rl_comfirm.setVisibility(View.VISIBLE);
//                mHolder.btn_order_cancel.setText("查看物流");
//                mHolder.tv_order_hint.setVisibility(View.GONE);
//                mHolder.btn_order_operation.setVisibility(View.GONE);
//                break;
//            case OrderCommonState.ORDER_SALE_SERVICE:
//                mHolder.tv_order_status.setText("已完成");
//                mHolder.tv_order_status.setTextColor(0xFF313131);
//                mHolder.rl_comfirm.setVisibility(View.VISIBLE);
//                mHolder.btn_order_cancel.setText("申请售后");
//                mHolder.tv_order_hint.setVisibility(View.VISIBLE);
//                mHolder.tv_order_hint.setText("签收15日内可申请售后");
//                mHolder.btn_order_operation.setVisibility(View.GONE);
//                break;
//            case OrderCommonState.ORDER_NO_SIGN:
//                mHolder.tv_order_status.setText("退签");
//                mHolder.tv_order_status.setTextColor(0xFF313131);
//                mHolder.rl_comfirm.setVisibility(View.VISIBLE);
//                mHolder.btn_order_cancel.setText("申请售后");
//                mHolder.tv_order_hint.setVisibility(View.VISIBLE);
//                mHolder.tv_order_hint.setText("退签7日内可申请售后");
//                mHolder.btn_order_operation.setVisibility(View.GONE);
//                break;
//        }
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
        @Bind(R.id.tv_sum_noney)
        TextView tv_sum_noney;//合计金额
        @Bind(R.id.tv_sum_carriage)
        TextView tv_sum_carriage;//合计运费
        @Bind(R.id.tv_order_hint)
        TextView tv_order_hint; //取消按钮左侧的提示信息
        @Bind(R.id.btn_order_cancel)
        Button btn_order_cancel;//取消
        @Bind(R.id.btn_order_operation)
        Button btn_order_operation;//订单操作

        public OrderListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
