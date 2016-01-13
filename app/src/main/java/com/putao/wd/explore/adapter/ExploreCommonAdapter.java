package com.putao.wd.explore.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.PagerExplore;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 探索--首页--前7项适配器
 * Created by yanghx on 2016/1/11.
 */
@Deprecated
public class ExploreCommonAdapter extends BasicAdapter<PagerExplore, ExploreCommonAdapter.CommonViewHolder>{


    public ExploreCommonAdapter(Context context, List<PagerExplore> pagerExplores) {
        super(context, pagerExplores);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_nexplore_common;
    }

    @Override
    public CommonViewHolder getViewHolder(View itemView, int viewType) {
        return new CommonViewHolder(itemView);
    }

    @Override
    public void onBindItem(CommonViewHolder holder, PagerExplore pagerExplore, int position) {
        holder.iv_video.setImageResource(pagerExplore.getImageUrl());
        holder.tv_title.setText(pagerExplore.getTitle());
        holder.tv_content.setText(pagerExplore.getContent());
    }

    static class CommonViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_video)
        ImageDraweeView iv_video;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;

        public CommonViewHolder(View itemView) {
            super(itemView);
        }
    }
}
