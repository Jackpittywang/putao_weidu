package com.putao.wd.me.address.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 城市地区选择适配器
 * Created by guchenkai on 2015/12/7.
 */
public class CitySelectAdapter extends BasicAdapter<String, CitySelectAdapter.CitySelectViewHolder> {

    public CitySelectAdapter(Context context, List<String> strings) {
        super(context, strings);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_city_item;
    }

    @Override
    public CitySelectViewHolder getViewHolder(View itemView, int viewType) {
        return new CitySelectViewHolder(itemView);
    }

    @Override
    public void onBindItem(CitySelectViewHolder holder, String s, int position) {
        holder.tv_city_name.setText(s);
    }

    /**
     * 城市地区选择视图
     */
    static class CitySelectViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_city_name)
        TextView tv_city_name;

        public CitySelectViewHolder(View itemView) {
            super(itemView);
        }
    }
}
