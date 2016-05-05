package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Companion;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.Date;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/5.
 */
public class SubribeAdapter extends BasicAdapter<Companion, SubribeAdapter.SburibeViewholder> {


    public SubribeAdapter(Context context, List<Companion> companions) {
        super(context, companions);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_companion_item;
    }

    @Override
    public SburibeViewholder getViewHolder(View itemView, int viewType) {
        return new SburibeViewholder(itemView);
    }

    @Override
    public void onBindItem(SburibeViewholder holder, Companion companion, int position) {
        holder.tv_title.setText("幼升小全方位" + position);
        holder.tv_time.setText("刚刚");
    }

    static class SburibeViewholder extends BasicViewHolder {
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

        public SburibeViewholder(View itemView) {
            super(itemView);
        }
    }
}
