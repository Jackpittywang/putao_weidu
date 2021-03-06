package com.putao.wd.pt_me.service.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.OrderProduct;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/12/31.
 */
public class ServiceChooseAdapter extends BasicAdapter<OrderProduct, ServiceChooseAdapter.ServiceChooseViewHolder> {


    public ServiceChooseAdapter(Context context, List<OrderProduct> orderProducts) {
        super(context, orderProducts);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_service_choose_able_item;
    }

    @Override
    public ServiceChooseViewHolder getViewHolder(View itemView, int viewType) {
        return new ServiceChooseViewHolder(itemView);
    }


    @Override
    public void onBindItem(ServiceChooseViewHolder holder, OrderProduct orderProduct, int position) {
        holder.iv_car_icon.setImageURL(orderProduct.getReal_icon());
        holder.tv_title.setText(orderProduct.getProduct_name());
        holder.tv_money.setText(orderProduct.getPrice());
        holder.tv_count.setText(orderProduct.getQuantity() + "");
        holder.tv_color.setText(orderProduct.getSku());
        holder.btn_sel.setState(orderProduct.isSelect());
        holder.btn_sel.setClickable(false);
    }

    /**
     * 订单视图
     */
    static class ServiceChooseViewHolder extends BasicViewHolder {
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
        @Bind(R.id.btn_sel)
        SwitchButton btn_sel;

        public ServiceChooseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
