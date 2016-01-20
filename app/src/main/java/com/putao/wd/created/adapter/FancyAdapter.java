package com.putao.wd.created.adapter;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;


/**
 * Created by Administrator on 2016/1/13.
 */
public class FancyAdapter extends LoadMoreAdapter<Marketing, FancyAdapter.FancyHolder> {

    public FancyAdapter(Context context, List<Marketing> marketings) {
        super(context, marketings);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_fancy_item;
    }

    @Override
    public FancyHolder getViewHolder(View itemView, int viewType) {
        return new FancyHolder(itemView);
    }

    @Override
    public void onBindItem(FancyHolder holder, Marketing marketing, int position) {
    }

    static class FancyHolder extends BasicViewHolder {

        public FancyHolder(View itemView) {
            super(itemView);
        }
    }
}
