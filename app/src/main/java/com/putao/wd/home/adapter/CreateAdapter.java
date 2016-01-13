package com.putao.wd.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.explore.adapter.MarketingAdapter;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/1/13.
 */
public class CreateAdapter extends LoadMoreAdapter<Marketing, CreateAdapter.CreateHolder> {

    public CreateAdapter(Context context, List<Marketing> marketings) {
        super(context, marketings);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_created_item;
    }

    @Override
    public CreateHolder getViewHolder(View itemView, int viewType) {
        return new CreateHolder(itemView);
    }

    @Override
    public void onBindItem(CreateHolder holder, Marketing marketing, int position) {

    }

    static class CreateHolder extends BasicViewHolder {
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

        public CreateHolder(View itemView) {
            super(itemView);
        }
    }
}
