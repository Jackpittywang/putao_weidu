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
import com.putao.wd.model.Participation;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;

/**
 * 我的参与的适配器
 * Created by Administrator on 2016/4/5.
 */
public class ParticipationAdapter extends LoadMoreAdapter<Participation, ParticipationAdapter.ParticipationViewHolder> {
    public ParticipationAdapter(Context context, List<Participation> participation) {
        super(context, participation);

    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_participation_collection;
    }

    @Override
    public ParticipationViewHolder getViewHolder(View itemView, int viewType) {
        return new ParticipationViewHolder(itemView);
    }

    @Override
    public void onBindItem(final ParticipationViewHolder holder, final Participation participation, int position) {
        holder.tv_title.setText("参与" + position);
    }


    /**
     * 视图
     */
    static class ParticipationViewHolder extends BasicViewHolder {

        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.iv_icon)
        ImageDraweeView iv_icon;

        public ParticipationViewHolder(View itemView) {
            super(itemView);
        }
    }

}
