package com.putao.wd.explore.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.HomeExplore;
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
public class ExploreCommonAdapter extends BasicAdapter<HomeExplore, ExploreCommonAdapter.CommonViewHolder>{


    public ExploreCommonAdapter(Context context, List<HomeExplore> homeExplores) {
        super(context, homeExplores);
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
    public void onBindItem(CommonViewHolder holder, HomeExplore homeExplore, int position) {
        holder.iv_video.setImageResource(homeExplore.getImageUrl());
        holder.tv_title.setText(homeExplore.getTitle());
        holder.tv_content.setText(homeExplore.getContent());
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
