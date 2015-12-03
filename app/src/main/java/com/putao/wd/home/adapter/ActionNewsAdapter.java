package com.putao.wd.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ActionNewsItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 葡星圈活动和新闻适配器
 * Created by guchenkai on 2015/12/2.
 */
public class ActionNewsAdapter extends LoadMoreAdapter<ActionNewsItem, BasicViewHolder> {
    private static final int TYPE_ACTION = 1;
    private static final int TYPE_NEWS = 2;


    public ActionNewsAdapter(Context context, List<ActionNewsItem> actionNewsItems) {
        super(context, actionNewsItems);
    }

    @Override
    public int getMultiItemViewType(int position) {
        ActionNewsItem actionNewsItem = getItem(position);
        if (actionNewsItem.isAction())
            return TYPE_ACTION;
        else
            return TYPE_NEWS;
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            case TYPE_ACTION:
                return R.layout.fragment_start_circle_action;
            case TYPE_NEWS:
                return R.layout.fragment_start_circle_news;
        }
        return 0;
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case TYPE_ACTION:
                return new ActionViewHolder(itemView);
            case TYPE_NEWS:
                return new NewsViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindItem(BasicViewHolder holder, ActionNewsItem actionNewsItem, int position) {
        if (holder instanceof ActionViewHolder) {
            ActionViewHolder viewHolder = (ActionViewHolder) holder;
            viewHolder.tv_action_type.setText(actionNewsItem.getType());
            viewHolder.tv_action_title.setText(actionNewsItem.getTitle());
            viewHolder.tv_action_sub_title.setText(actionNewsItem.getIntro());
            viewHolder.tv_address.setText(actionNewsItem.getAddress());
            viewHolder.tv_time.setText(actionNewsItem.getTime());
            viewHolder.tv_people_count.setText(actionNewsItem.getJoinCount());
            viewHolder.iv_action_icon.setImageURL(actionNewsItem.getImgUrl());
        } else if (holder instanceof NewsViewHolder) {
            NewsViewHolder viewHolder = (NewsViewHolder) holder;
            viewHolder.tv_news_title.setText(actionNewsItem.getTitle());
            viewHolder.tv_news_sub_title.setText(actionNewsItem.getIntro());
            viewHolder.tv_news_type.setText(actionNewsItem.getType());
            viewHolder.iv_news_icon.setImageURL(actionNewsItem.getImgUrl());
        }
    }

    /**
     * 活动视图
     */
    static class ActionViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_action_type)
        TextView tv_action_type;
        @Bind(R.id.tv_action_title)
        TextView tv_action_title;
        @Bind(R.id.tv_action_sub_title)
        TextView tv_action_sub_title;
        @Bind(R.id.tv_address)
        TextView tv_address;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.tv_people_count)
        TextView tv_people_count;
        @Bind(R.id.iv_action_icon)
        ImageDraweeView iv_action_icon;

        public ActionViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 新闻视图
     */
    static class NewsViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_news_title)
        TextView tv_news_title;
        @Bind(R.id.tv_news_sub_title)
        TextView tv_news_sub_title;
        @Bind(R.id.tv_news_type)
        TextView tv_news_type;
        @Bind(R.id.iv_news_icon)
        ImageDraweeView iv_news_icon;

        public NewsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
