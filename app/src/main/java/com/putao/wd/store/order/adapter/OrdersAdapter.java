package com.putao.wd.store.order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.OrderProduct;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 填写订单的订单列表适配器
 * Created by wango on 2015/12/7.
 */
public class OrdersAdapter extends BasicAdapter<OrderProduct, OrdersAdapter.OrderListViewHolder> {

    public OrdersAdapter(Context context, List<OrderProduct> orderProducts) {
        super(context, orderProducts);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_write_order_item;
    }

    @Override
    public OrderListViewHolder getViewHolder(View itemView, int viewType) {
        return new OrderListViewHolder(itemView);
    }


    @Override
    public void onBindItem(OrderListViewHolder holder, OrderProduct orderProduct, final int position) {
        holder.iv_car_icon.setImageURL(orderProduct.getReal_icon());
        holder.tv_title.setText(orderProduct.getProduct_name());
        holder.tv_money.setText(orderProduct.getPrice());
        holder.tv_count.setText(orderProduct.getQuantity());
        holder.tv_color.setText(orderProduct.getSku());
    }

    /**
     * 订单视图
     */
    static class OrderListViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_car_icon)
        ImageDraweeView iv_car_icon;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_money)
        TextView tv_money;
        @Bind(R.id.tv_count)
        TextView tv_count;
        @Bind(R.id.tv_color)
        TextView tv_color;

        public OrderListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
