package com.putao.wd.pt_me.participation;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.explore.ExploreMoreActivity;
import com.putao.wd.model.ExploreIndex;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ParticipationAdapter extends LoadMoreAdapter<ExploreIndex, ParticipationAdapter.ParticipationViewHolder> {
    
    public ParticipationAdapter(Context context, List<ExploreIndex> homeExploreMores) {
        super(context, homeExploreMores);

    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_more_item;
    }

    @Override
    public ParticipationViewHolder getViewHolder(View itemView, int viewType) {
        return new ParticipationViewHolder(itemView);
    }

    @Override
    public void onBindItem(final ParticipationViewHolder holder, final ExploreIndex homeExploreMore, int position) {
//        holder.iv_icon.setImageURL(homeExploreMore.getCover_pic());
//        holder.tv_title.setText(homeExploreMore.getTitle());
//        holder.tv_content.setText(homeExploreMore.getDescription());
//        holder.tv_count_comment.setText(homeExploreMore.getCount_comments() + "");
//        holder.tv_count_cool.setText(homeExploreMore.getCount_likes() + "");
//        holder.sb_cool_icon.setClickable(false);
//        holder.sb_cool_icon.setState(homeExploreMore.is_like());
    }

    /**
     * 视图
     */
    static class ParticipationViewHolder extends BasicViewHolder

    {
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.tv_count_comment)
        TextView tv_count_comment;
        @Bind(R.id.tv_count_cool)
        TextView tv_count_cool;
        @Bind(R.id.sb_cool_icon)
        SwitchButton sb_cool_icon;
        @Bind(R.id.iv_icon)
        ImageDraweeView iv_icon;
        @Bind(R.id.ll_comment)
        LinearLayout ll_comment;
        @Bind(R.id.ll_cool)
        LinearLayout ll_cool;

        public ParticipationViewHolder(View itemView) {
            super(itemView);
        }
    }

}
