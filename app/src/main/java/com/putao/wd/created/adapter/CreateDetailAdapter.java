package com.putao.wd.created.adapter;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class CreateDetailAdapter extends BasicAdapter<Marketing, CreateDetailAdapter.CreateDetailHolder> {

    public CreateDetailAdapter(Context context, List<Marketing> marketings) {
        super(context, marketings);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_marketing_item;
    }

    @Override
    public CreateDetailHolder getViewHolder(View itemView, int viewType) {
        return new CreateDetailHolder(itemView);
    }

    @Override
    public void onBindItem(CreateDetailHolder holder, Marketing marketing, int position) {
    }

    static class CreateDetailHolder extends BasicViewHolder {

        public CreateDetailHolder(View itemView) {
            super(itemView);
        }
    }
}
