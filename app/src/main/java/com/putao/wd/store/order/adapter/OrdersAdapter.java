package com.putao.wd.store.order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.OrderListItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wango on 2015/12/7.
 */
public class OrdersAdapter extends BasicAdapter<OrderListItem, OrdersAdapter.OrderListViewHolder> {

    public OrdersAdapter(Context context, List<OrderListItem> orderListItems) {
        super(context, orderListItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_writeorder_item;
    }

    @Override
    public OrderListViewHolder getViewHolder(View itemView, int viewType) {
        return new OrderListViewHolder(itemView);
    }

    @Override
    public void onBindItem(OrderListViewHolder holder, final OrderListItem orderListItem, final int position) {
        if (position != 0) {//和上一条信息比较
            OrderListItem previCar = getItem(position - 1);
            if (previCar.isNull() == orderListItem.isNull()) {
                //holder.tv_big_title.setVisibility(View.GONE);
                holder.divider.setVisibility(View.GONE);
            }
        } else {
            //holder.tv_big_title.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.GONE);
        }
        holder.iv_car_icon.setImageURL(orderListItem.getImgUrl());
        holder.tv_title.setText(orderListItem.getTitle());
        holder.tv_color.setText(orderListItem.getColor());
        holder.tv_size.setText(orderListItem.getSize());
        holder.tv_money.setText(orderListItem.getMoney());
        holder.tv_count.setText(orderListItem.getCount());

    }

    /**
     * 全部选中
     */
    public void selAll(boolean isSelect) {
        List<OrderListItem> cars = getItems();
        for (OrderListItem car : cars) {
            int index = cars.indexOf(car);
            car.setIsSelect(isSelect);
            replace(index, car);
        }
    }

    /**
     * 购物车视图
     */
    static class OrderListViewHolder extends BasicViewHolder {
        @Bind(R.id.divider)
        View divider;
//        @Bind(R.id.tv_big_title)
//        TextView tv_big_title;
        @Bind(R.id.iv_car_icon)
        ImageDraweeView iv_car_icon;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_color)
        TextView tv_color;
        @Bind(R.id.tv_size)
        TextView tv_size;
        @Bind(R.id.tv_money)
        TextView tv_money;
        @Bind(R.id.tv_count)
        TextView tv_count;
//        @Bind(R.id.btn_sel)
//        SwitchButton btn_sel;

        public OrderListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
