package com.putao.wd.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ProductItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 商城产品列表适配器
 * Created by guchenkai on 2015/11/30.
 */
public class ProductAdapter extends LoadMoreAdapter<ProductItem,ProductAdapter.ProductViewHolder> {

    public ProductAdapter(Context context, List<ProductItem> productItems) {
        super(context, productItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_store_item;
    }

    @Override
    public ProductViewHolder getViewHolder(View itemView, int viewType) {
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindItem(ProductViewHolder holder, ProductItem productItem, int position) {
        holder.iv_product_icon.setImageURL(productItem.getIconUrl());
        holder.tv_product_title.setText(productItem.getTitle());
        holder.tv_product_intro.setText(productItem.getIntro());
        holder.tv_product_price.setText(productItem.getPrice());
    }

    /**
     *
     */
    static class ProductViewHolder extends BasicViewHolder{
        @Bind(R.id.iv_product_icon)
        ImageDraweeView iv_product_icon;
        @Bind(R.id.tv_product_title)
        TextView tv_product_title;
        @Bind(R.id.tv_product_intro)
        TextView tv_product_intro;
        @Bind(R.id.tv_product_price)
        TextView tv_product_price;

        public ProductViewHolder(View itemView) {
            super(itemView);
        }
    }
}
