package com.putao.wd.me.order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Order;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 订单列表适配器
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderListAdapter extends BasicAdapter<Order, OrderListAdapter.OrderListViewHolder> {

    public OrderListAdapter(Context context, List<Order> orderList) {
        super(context, orderList);
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
    public void onBindItem(OrderListViewHolder holder, final Order order, int position) {

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
        TextView tv_order_status;
        @Bind(R.id.stickyHeaderLayout_scrollable)
        BasicRecyclerView ll_goods;
        @Bind(R.id.btn_order_cancel)
        Button btn_order_cancel;//取消
        @Bind(R.id.btn_order_operation)
        Button btn_order_operation;//订单操作

        public OrderListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
