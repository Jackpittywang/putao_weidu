package com.putao.wd.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.DiaryApp;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 探索号适配器
 * Created by yanghx on 2015/12/9.
 */
public class ProductsAdapter extends BasicAdapter<DiaryApp, ProductsAdapter.ProductsViewHolder> {


    public ProductsAdapter(Context context, List<DiaryApp> diaryApps) {
        super(context, diaryApps);
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
    public void onBindItem(ProductsAdapter.ProductsViewHolder holder, DiaryApp diaryApp, int position) {
        if (!TextUtils.isEmpty(diaryApp.getProduct_icon())) {
            holder.iv_product.setImageURL(diaryApp.getProduct_icon());
            holder.tv_product.setText(diaryApp.getProduct_name());
            if (diaryApp.isShowRedDot()) holder.red_dot.setVisibility(View.VISIBLE);
            else holder.red_dot.setVisibility(View.GONE);
        }
    }

    static class ProductsViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_product)
        ImageDraweeView iv_product;
        @Bind(R.id.tv_product)
        TextView tv_product;
        @Bind(R.id.red_dot)
        View red_dot;

        public ProductsViewHolder(View itemView) {
            super(itemView);
        }
    }
}





