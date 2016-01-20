package com.putao.wd.explore.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.HomeExploreMore;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 更多内容适配器
 * Created by yanghx on 2016/1/12.
 */
public class MoreAdapter extends LoadMoreAdapter<HomeExploreMore, MoreAdapter.MoreContentViewHolder> {


    public MoreAdapter(Context context, List<HomeExploreMore> homeExploreMores) {
        super(context, homeExploreMores);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_more_item;
    }

    @Override
    public MoreContentViewHolder getViewHolder(View itemView, int viewType) {
        return new MoreContentViewHolder(itemView);
    }

    @Override
    public void onBindItem(MoreContentViewHolder holder, HomeExploreMore homeExploreMore, int position) {
        holder.iv_news_icon.setBackgroundResource(homeExploreMore.getImageUrl());
        holder.tv_title.setText(homeExploreMore.getTitle());
        holder.tv_content.setText(homeExploreMore.getContent());
        holder.tv_count_comment.setText(homeExploreMore.getComment());
        holder.tv_count_cool.setText(homeExploreMore.getCool());
    }



    /**
     * 视图
     */
    static class MoreContentViewHolder extends BasicViewHolder {

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
        @Bind(R.id.iv_news_icon)
        ImageDraweeView iv_news_icon;

        public MoreContentViewHolder(View itemView) {
            super(itemView);
        }
    }

}
