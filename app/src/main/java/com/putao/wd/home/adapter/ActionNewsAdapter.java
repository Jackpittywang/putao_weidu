package com.putao.wd.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.map.MapActivity;
import com.putao.wd.model.ActionNews;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 葡星圈活动和新闻适配器
 * Created by guchenkai on 2015/12/2.
 */
@Deprecated
public class ActionNewsAdapter extends LoadMoreAdapter<ActionNews, BasicViewHolder> {
    private static final int TYPE_ACTION = 1;
    private static final int TYPE_NEWS = 2;

    private BasicFragmentActivity mActivity;

    public ActionNewsAdapter(Context context, List<ActionNews> actionNewsItems) {
        super(context, actionNewsItems);
        mActivity = (BasicFragmentActivity) context;
    }

    @Override
    public int getMultiItemViewType(int position) {
        ActionNews actionNewsList = getItem(position);
        switch (actionNewsList.getType()) {
            case "TEXT":
                return TYPE_ACTION;
            case "NEWS":
                return TYPE_NEWS;
        }
//        if (actionNewsList.isAction())
//            return TYPE_ACTION;
//        else
//            return TYPE_NEWS;
        return -1;
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            case TYPE_ACTION:
                return R.layout.fragment_start_circle_action_item;
            case TYPE_NEWS:
                return R.layout.fragment_start_circle_news_item;
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
    public void onBindItem(BasicViewHolder holder, final ActionNews actionNews, int position) {
        /*if (holder instanceof ActionViewHolder) {
            ActionViewHolder viewHolder = (ActionViewHolder) holder;
            viewHolder.iv_action_icon.setImageURL(actionNews.getBanner_url());
            viewHolder.tv_action_title.setText(actionNews.getTitle());
            viewHolder.tv_action_description.setText(actionNews.getDescription());
            viewHolder.tv_address.setText(actionNews.getLocation());
            viewHolder.tv_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(MapActivity.KEY_ACTION_ID, actionNews.getId());
                    mActivity.startActivity(MapActivity.class, bundle);
                }
            });
            viewHolder.tv_time.setText(DateUtils.secondToDate(Integer.parseInt(actionNews.getStart_time()), "yyyy.MM.dd"));
            viewHolder.tv_people_count.setText(actionNews.getRegistration_number());
            viewHolder.tv_action_label.setText(actionNews.getLabel());
            switch (actionNews.getStatus()) {
                case "ONGOING":
                    viewHolder.tv_action_status.setText("进行中");
                    break;
                case "CLOSE":
                    viewHolder.tv_action_status.setText("已结束");
                    break;
                case "LOOKBACK":
                    viewHolder.tv_action_status.setText("回顾");
                    break;
            }
        } else if (holder instanceof NewsViewHolder) {
            NewsViewHolder viewHolder = (NewsViewHolder) holder;
            viewHolder.tv_news_title.setText(actionNews.getTitle());
            viewHolder.tv_news_description.setText(actionNews.getDescription());
            viewHolder.tv_news_label.setText(actionNews.getLabel());
            viewHolder.iv_news_icon.setImageURL(actionNews.getBanner_url());
        }*/
//        if (holder instanceof ActionViewHolder) {
//            ActionViewHolder viewHolder = (ActionViewHolder) holder;
//            viewHolder.tv_action_type.setText(actionNewsItem.getType());
//            viewHolder.tv_action_title.setText(actionNewsItem.getTitle());
//            viewHolder.tv_action_sub_title.setText(actionNewsItem.getIntro());
//            viewHolder.tv_address.setText(actionNewsItem.getAddress());
//            viewHolder.tv_time.setText(actionNewsItem.getTime());
//            viewHolder.tv_people_count.setText(actionNewsItem.getJoinCount());
//            viewHolder.iv_action_icon.setImageURL(actionNewsItem.getImgUrl());
//        } else if (holder instanceof NewsViewHolder) {
//            NewsViewHolder viewHolder = (NewsViewHolder) holder;
//            viewHolder.tv_news_title.setText(actionNewsItem.getTitle());
//            viewHolder.tv_news_sub_title.setText(actionNewsItem.getIntro());
//            viewHolder.tv_news_type.setText(actionNewsItem.getType());
//            viewHolder.iv_news_icon.setImageURL(actionNewsItem.getImgUrl());
//        }
    }

    /**
     * 活动视图
     */
    static class ActionViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_action_label)
        TextView tv_action_label;
        @Bind(R.id.tv_action_status)
        TextView tv_action_status;
        @Bind(R.id.tv_action_title)
        TextView tv_action_title;
        @Bind(R.id.tv_action_description)
        TextView tv_action_description;
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
        @Bind(R.id.tv_news_description)
        TextView tv_news_description;
        @Bind(R.id.tv_news_label)
        TextView tv_news_label;
        @Bind(R.id.iv_news_icon)
        ImageDraweeView iv_news_icon;

        public NewsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
