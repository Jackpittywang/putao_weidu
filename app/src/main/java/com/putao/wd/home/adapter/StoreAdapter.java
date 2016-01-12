package com.putao.wd.home.adapter;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.model.StoreProduct;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 精选首页适配器
 * Created by yanghx on 2016/1/11.
 */
public class StoreAdapter extends LoadMoreAdapter<StoreProduct, StoreAdapter.StoreViewHolder> {

    public StoreAdapter(Context context, List<StoreProduct> products) {
        super(context, products);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_store_item;
    }

    @Override
    public StoreViewHolder getViewHolder(View itemView, int viewType) {
        return new StoreViewHolder(itemView);
    }

    @Override
    public void onBindItem(StoreViewHolder holder, StoreProduct product, int position) {
        holder.iv_product/*.addProcessor(new BlurProcessor(8))*/.setImageURL(product.getImage());
    }

    /**
     * 精选首页视图
     */
    static class StoreViewHolder extends BasicViewHolder {

        @Bind(R.id.iv_product)
        ImageDraweeView iv_product;

        public StoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
