package com.putao.wd.created.adapter;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class FancyDetailAdapter extends BasicAdapter<Marketing, FancyDetailAdapter.FancyDetailDetailHolder> {

    public FancyDetailAdapter(Context context, List<Marketing> marketings) {
        super(context, marketings);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_fancy_item;
    }

    @Override
    public FancyDetailDetailHolder getViewHolder(View itemView, int viewType) {
        return new FancyDetailDetailHolder(itemView);
    }

    @Override
    public void onBindItem(FancyDetailDetailHolder holder, Marketing marketing, int position) {
    }

    static class FancyDetailDetailHolder extends BasicViewHolder {

        public FancyDetailDetailHolder(View itemView) {
            super(itemView);
        }
    }
}
