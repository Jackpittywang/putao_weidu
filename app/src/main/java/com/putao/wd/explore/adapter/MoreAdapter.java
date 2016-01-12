package com.putao.wd.explore.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.PagerExploreMore;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 更多内容适配器
 * Created by yanghx on 2016/1/12.
 */
public class MoreAdapter extends BasicAdapter<PagerExploreMore, ExploreMoreAdapter.MoreViewHolder> {


    public MoreAdapter(Context context, List<PagerExploreMore> pagerExploreMores) {
        super(context, pagerExploreMores);
    }

    @Override
    public int getLayoutId(int viewType) {
        return 0;
    }

    @Override
    public ExploreMoreAdapter.MoreViewHolder getViewHolder(View itemView, int viewType) {
        return null;
    }

    @Override
    public void onBindItem(ExploreMoreAdapter.MoreViewHolder holder, PagerExploreMore pagerExploreMore, int position) {

    }

    /**
     * 视图
     */
    static class MoreViewHolder extends BasicViewHolder {

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

        public MoreViewHolder(View itemView) {
            super(itemView);
        }
    }

}
