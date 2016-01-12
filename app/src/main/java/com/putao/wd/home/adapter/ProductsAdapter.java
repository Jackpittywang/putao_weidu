package com.putao.wd.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.companion.PlotPreviewDialog;
import com.putao.wd.model.ExploreProduct;
import com.putao.wd.model.ExploreProductDetail;
import com.putao.wd.model.ExploreProductPlot;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 探索号适配器
 * Created by yanghx on 2015/12/9.
 */
public class ProductsAdapter extends BasicAdapter<String, ProductsAdapter.ProductsViewHolder> {


    public ProductsAdapter(Context context, List<String> strings) {
        super(context, strings);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_company_products_item;
    }

    @Override
    public ProductsAdapter.ProductsViewHolder getViewHolder(View itemView, int viewType) {
        return new ProductsViewHolder(itemView);
    }

    @Override
    public void onBindItem(ProductsAdapter.ProductsViewHolder holder, String s, int position) {
        if (TextUtils.isEmpty(s))
            holder.iv_product.setDefaultImage(R.color.background_F5F5F5);
        else
            holder.iv_product.setImageURL(s);
    }

    static class ProductsViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_product)
        ImageDraweeView iv_product;

        public ProductsViewHolder(View itemView) {
            super(itemView);
        }
    }
}





