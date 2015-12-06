package com.putao.wd.store.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ShoppingCar;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 购物车适配器
 * Created by guchenkai on 2015/12/4.
 */
public class ShoppingCarAdapter extends BasicAdapter<ShoppingCar, ShoppingCarAdapter.ShoppingCarViewHolder> {

    public ShoppingCarAdapter(Context context, List<ShoppingCar> shoppingCars) {
        super(context, shoppingCars);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_shopping_car_item;
    }

    @Override
    public ShoppingCarViewHolder getViewHolder(View itemView, int viewType) {
        return new ShoppingCarViewHolder(itemView);
    }

    @Override
    public void onBindItem(ShoppingCarViewHolder holder, ShoppingCar shoppingCar, int position) {
        holder.tv_big_title.setText(!shoppingCar.isNull() ? "商品信息" : "无效商品");
        if (position != 0) {//和上一条信息比较
            ShoppingCar previCar = getItem(position - 1);
            if (previCar.isNull() == shoppingCar.isNull()) {
                holder.tv_big_title.setVisibility(View.GONE);
                holder.divider.setVisibility(View.GONE);
            }
        } else {
            holder.tv_big_title.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.GONE);
        }
        holder.iv_car_icon.setImageURL(shoppingCar.getImgUrl());
        holder.tv_title.setText(shoppingCar.getTitle());
        holder.tv_color.setText(shoppingCar.getColor());
        holder.tv_size.setText(shoppingCar.getSize());
        holder.tv_money.setText(shoppingCar.getMoney());
        holder.tv_count.setText(shoppingCar.getCount());
        holder.btn_sel.setState(shoppingCar.isSelect());
    }

    /**
     * 全部选中
     */
    public void selAll(boolean isSelect) {
        List<ShoppingCar> cars = getItems();
        for (ShoppingCar car : cars) {
            car.setIsSelect(isSelect);
        }
        refresh();
    }

    /**
     * 购物车视图
     */
    static class ShoppingCarViewHolder extends BasicViewHolder {
        @Bind(R.id.divider)
        View divider;
        @Bind(R.id.tv_big_title)
        TextView tv_big_title;
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
        @Bind(R.id.btn_sel)
        SwitchButton btn_sel;

        public ShoppingCarViewHolder(View itemView) {
            super(itemView);
        }
    }
}
