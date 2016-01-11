package com.putao.wd.explore.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 创想适配器
 * Created by guchenkai on 2016/1/11.
 */
public class MarketingAdapter extends LoadMoreAdapter<Marketing, MarketingAdapter.MarketingHolder> {

    public MarketingAdapter(Context context, List<Marketing> marketings) {
        super(context, marketings);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_marketing_item;
    }

    @Override
    public MarketingHolder getViewHolder(View itemView, int viewType) {
        return new MarketingHolder(itemView);
    }

    @Override
    public void onBindItem(MarketingHolder holder, Marketing marketing, int position) {

    }

    static class MarketingHolder extends BasicViewHolder {
        @Bind(R.id.tv_sign)
        TextView tv_sign;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.tv_count_comment)
        TextView tv_count_comment;
        @Bind(R.id.tv_count_cool)
        TextView tv_count_cool;
        @Bind(R.id.iv_news_icon)
        ImageView iv_news_icon;

        public MarketingHolder(View itemView) {
            super(itemView);
        }
    }
}
