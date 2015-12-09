package com.putao.wd.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ExploreItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;

/**
 * 探索号适配器
 * Created by yanghx on 2015/12/9.
 */
public class ExploreAdapter extends BasicAdapter<ExploreItem, BasicViewHolder> {


    public ExploreAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_explore_item;
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {
        return new ExploerViewHolder(itemView);
    }

    @Override
    public void onBindItem(BasicViewHolder holder, ExploreItem exploreItem, int position) {
        if (holder instanceof ExploerViewHolder){
            ExploerViewHolder viewHolder = (ExploerViewHolder) holder;
            viewHolder.tv_skill_name.setText(exploreItem.getSkill_name());        }
    }

    /**
     * 全图片
     */
    static class ExploerViewHolder extends BasicViewHolder{
        @Bind(R.id.iv_skill_icon)
        ImageDraweeView iv_skill_icon;
        @Bind(R.id.tv_skill_name)
        TextView tv_skill_name;

        public ExploerViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 图文混排
     */
    static class ExploerWithTextViewHolder extends BasicViewHolder{
        public ExploerWithTextViewHolder(View itemView) {
            super(itemView);
        }
    }

}





