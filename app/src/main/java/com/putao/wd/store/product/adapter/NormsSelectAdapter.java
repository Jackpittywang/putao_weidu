package com.putao.wd.store.product.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 规格选择适配器
 * Created by guchenkai on 2015/11/30.
 */
public class NormsSelectAdapter extends BasicAdapter<String, NormsSelectAdapter.ColorSelectViewHolder> {

    public NormsSelectAdapter(Context context, List<String> strings) {
        super(context, strings);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.popup_shopping_car_item;
    }

    @Override
    public ColorSelectViewHolder getViewHolder(View itemView, int viewType) {
        return new ColorSelectViewHolder(itemView);
    }

    @Override
    public void onBindItem(ColorSelectViewHolder holder, String s, int position) {
        holder.tv_color.setText(s);
    }

    /**
     *
     */
    static class ColorSelectViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_color)
        TextView tv_color;

        public ColorSelectViewHolder(View itemView) {
            super(itemView);
        }
    }
}
