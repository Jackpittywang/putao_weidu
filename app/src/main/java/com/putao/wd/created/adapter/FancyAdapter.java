package com.putao.wd.created.adapter;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/1/13.
 */
public class FancyAdapter extends LoadMoreAdapter<Marketing, FancyAdapter.CreateHolder> {

    public FancyAdapter(Context context, List<Marketing> marketings) {
        super(context, marketings);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_fancy_item;
    }

    @Override
    public CreateHolder getViewHolder(View itemView, int viewType) {
        return new CreateHolder(itemView);
    }

    @Override
    public void onBindItem(CreateHolder holder, Marketing marketing, int position) {
    }

    static class CreateHolder extends BasicViewHolder {

        public CreateHolder(View itemView) {
            super(itemView);
        }
    }
}
