package com.putao.wd.explore.adapter;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.model.HomeExplore;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 探索--更多适配器
 * Created by yanghx on 2016/1/11.
 */
public class ExploreMoreAdapter extends BasicAdapter<HomeExplore, ExploreMoreAdapter.MoreViewHolder> {

    private static final int TYPE_LEFT = 1;
    private static final int TYPE_RIGHT = 2;
    private boolean type;

    public ExploreMoreAdapter(Context context, List<HomeExplore> homeExplores) {
        super(context, homeExplores);
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2 == 0) {
            return TYPE_LEFT;
        }
        return TYPE_RIGHT;
    }

    @Override
    public int getLayoutId(int viewType) {
       switch (viewType) {
           case TYPE_LEFT :
               return R.layout.fragment_nexplore_more_item1;
           case TYPE_RIGHT :
               return R.layout.fragment_nexplore_more_item2;
       }
        return 0;
    }

    @Override
    public MoreViewHolder getViewHolder(View itemView, int viewType) {
        return new MoreViewHolder(itemView);
    }

    @Override
    public void onBindItem(MoreViewHolder holder, HomeExplore homeExplore, int position) {
        holder.tv_icon.setImageResource(homeExplore.getImageUrl());
    }

    static class MoreViewHolder extends BasicViewHolder {

        @Bind(R.id.iv_icon)
        ImageDraweeView tv_icon;

        public MoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
