package com.putao.wd.store.order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.putao.wd.R;
import com.putao.wd.dto.OrderListItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 填写订单的订单列表适配器
 * Created by wango on 2015/12/7.
 */
public class OrdersAdapter extends BasicAdapter<OrderListItem, BasicViewHolder> {
    private int size;
    private static final int TYPE_ORDER = 1;
    private static final int TYPE_SUM = 2;

    public OrdersAdapter(Context context, List<OrderListItem> orderListItems) {
        super(context, orderListItems);
        size = orderListItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == size - 1)
            return TYPE_SUM;
        return TYPE_ORDER;
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType){
            case TYPE_ORDER:
                return R.layout.layout_writeorder_item;
            case TYPE_SUM :
                return R.layout.layout_write_order_sum;
        }
        return 0;
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {
        switch (viewType){
            case TYPE_ORDER:
                return new OrderListViewHolder(itemView);
            case TYPE_SUM :
                return new SumOrderListViewHolder(itemView);
        }
        return null;
    }


    @Override
    public void onBindItem(BasicViewHolder holder, final OrderListItem orderListItem, final int position) {
        if(holder instanceof OrderListViewHolder) {
            OrderListViewHolder viewHolder= (OrderListViewHolder) holder;
            viewHolder.iv_car_icon.setImageURL(orderListItem.getImgUrl());
            viewHolder.tv_title.setText(orderListItem.getTitle());
            viewHolder.tv_color.setText(orderListItem.getColor());
            viewHolder.tv_size.setText(orderListItem.getSize());
            viewHolder.tv_money.setText(orderListItem.getMoney());
            viewHolder.tv_count.setText(orderListItem.getCount());
        }else if(holder instanceof SumOrderListViewHolder){
            SumOrderListViewHolder sumviewHolder= (SumOrderListViewHolder) holder;
            sumviewHolder.tv_order_sumcount.setText(orderListItem.getSum_count());
            sumviewHolder.tv_product_summoney.setText(orderListItem.getSum_price());
            sumviewHolder.tv_carriage.setText(orderListItem.getCarriage());
            sumviewHolder.tv_sum_noney.setText(orderListItem.getSum());
        }

    }

    /**
     * 订单视图
     */
    static class OrderListViewHolder extends BasicViewHolder {

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


        public OrderListViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 订单汇总
     */
    static class SumOrderListViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_order_sumcount)
        TextView tv_order_sumcount;//产品总数
        @Bind(R.id.tv_product_summoney)
        TextView tv_product_summoney;//产品总额
        @Bind(R.id.tv_carriage)
        TextView tv_carriage;//运费
        @Bind(R.id.tv_sum_noney)
        TextView tv_sum_noney;//总计费用

        public SumOrderListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
