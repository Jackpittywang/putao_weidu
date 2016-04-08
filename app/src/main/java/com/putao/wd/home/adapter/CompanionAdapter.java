package com.putao.wd.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Companion;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;
import java.util.Random;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/1/13.
 */
public class CompanionAdapter extends BasicAdapter<Companion, CompanionAdapter.CompanionHolder> {

    public CompanionAdapter(Context context, List<Companion> companions) {
        super(context, companions);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_companion_item;
    }

    @Override
    public CompanionAdapter.CompanionHolder getViewHolder(View itemView, int viewType) {
        return new CompanionHolder(itemView);
    }

    @Override
    public void onBindItem(CompanionAdapter.CompanionHolder holder, Companion companion, int position) {
        if (0 == position) {
            holder.tv_title.setText("葡萄活动");
        } else {
            holder.iv_icon.setImageURL(companion.getGame_icon());
            holder.tv_title.setText(companion.getGame_title());
            holder.tv_intro.setText(companion.getGame_subtitle());
            holder.tv_time.setText(DateUtils.timeCalculate(companion.getTime() * 1000L));
        }
        holder.tv_number.setText(new Random().nextInt(10) + 1);
    }

    static class CompanionHolder extends BasicViewHolder {
        @Bind(R.id.iv_icon)
        ImageDraweeView iv_icon;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.tv_intro)
        TextView tv_intro;
        @Bind(R.id.tv_number)
        TextView tv_number;

        public CompanionHolder(View itemView) {
            super(itemView);
        }
    }
}

