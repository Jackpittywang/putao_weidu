package com.putao.wd.home.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Companion;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/1/13.
 */
public class CompanionAdapter extends BasicAdapter<Companion, CompanionAdapter.CompanionHolder> {
    private Context mContext;

    public CompanionAdapter(Context context, List<Companion> companions) {
        super(context, companions);
        mContext = context;
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
        if (companion.getSecond_level_lists() == null && companion.getService_type() == 0) {
            holder.rl_compain_main.setVisibility(View.GONE);
        } else {
            if (TextUtils.isEmpty(companion.getService_icon())) {
                if (0 == companion.getService_type())
                    holder.iv_icon.setImageURL("res://putao/" + R.drawable.img_head_subscribe_default);
                else
                    holder.iv_icon.setImageURL("file:///");
            } else
                holder.iv_icon.setImageURL(companion.getService_icon());
            holder.rl_compain_main.setVisibility(View.VISIBLE);
            holder.tv_title.setText(companion.getService_name());
            holder.tv_intro.setText(companion.getSubstr());
            if (1 == companion.getIs_relation()) {
                long receiver_time = companion.getReceiver_time();
                holder.tv_time.setText(DateUtils.timeCalculate(receiver_time > 0 ? receiver_time : companion.getRelation_time() * 1000L));
                holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.text_color_gray));
                // holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.color_313131));
                holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.browse_background));
            } else {
                holder.tv_time.setText("未关联");
                holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.text_main_color_nor));
                //   holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.text_color_gray));
                holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.browse_background));
            }
            int size = companion.getNotDownloadCount();
            holder.tv_dot.setVisibility(View.GONE);
            if ((0 == size || !companion.isShowRed()) && 0 != companion.getService_type()) {
                holder.tv_number.setVisibility(View.GONE);
            } else {
                holder.tv_number.setVisibility(View.VISIBLE);
                holder.tv_number.setText(size > 99 ? 99 + "" : size + "");
                if (0 == companion.getService_type()) {
                    if (companion.isShowRed()) {
                        holder.tv_number.setVisibility(View.GONE);
                        holder.tv_dot.setVisibility(View.VISIBLE);
                    } else {
                        holder.tv_number.setVisibility(View.GONE);
                        holder.tv_dot.setVisibility(View.GONE);
                    }
                }
            }
        }

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
        @Bind(R.id.tv_dot)
        TextView tv_dot;
        @Bind(R.id.rl_compain_main)
        RelativeLayout rl_compain_main;

        public CompanionHolder(View itemView) {
            super(itemView);
        }
    }
}

