package com.putao.wd.pt_me.service.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ServiceProduct;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 售后列表中显示商品适配器
 *
 * Created by yanghx on 2015/12/30.
 */
public class ServiceAdapter extends BasicAdapter<ServiceProduct, ServiceAdapter.ServiceViewHolder> {


    public ServiceAdapter(Context context, List<ServiceProduct> serviceProducts) {
        super(context, serviceProducts);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_write_order_item;
    }

    @Override
    public ServiceViewHolder getViewHolder(View itemView, int viewType) {
        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindItem(ServiceViewHolder holder, ServiceProduct serviceProduct, int position) {
        holder.iv_car_icon.setImageURL(serviceProduct.getReal_sale_icon());
        holder.tv_title.setText(serviceProduct.getSale_product_name());
        holder.tv_money.setText(serviceProduct.getSale_price());
        holder.tv_count.setText(serviceProduct.getSale_quantity());
        holder.tv_color.setText(serviceProduct.getSale_sku());
    }

    static class ServiceViewHolder extends BasicViewHolder {
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

        public ServiceViewHolder(View itemView) {
            super(itemView);
        }
    }
}
