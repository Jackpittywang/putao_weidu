package com.putao.wd.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

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
    public void onBindItem(final CreateHolder holder, Marketing marketing, int position) {
        holder.sb_cool_icon.setClickable(false);
        holder.sb_not_cool_icon.setClickable(false);
        holder.ll_cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_count_cool.setTextColor(0xff48cfae);
                holder.sb_cool_icon.setState(true);
            }
        });
        holder.ll_not_cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_count_not_cool.setTextColor(0xffed5564);
                holder.sb_not_cool_icon.setState(true);
            }
        });
    }

    static class CreateHolder extends BasicViewHolder {
        @Bind(R.id.iv_sign)
        ImageDraweeView iv_sign;
        @Bind(R.id.sb_cool_icon)
        SwitchButton sb_cool_icon;
        @Bind(R.id.sb_not_cool_icon)
        SwitchButton sb_not_cool_icon;
        @Bind(R.id.ll_cool)
        LinearLayout ll_cool;
        @Bind(R.id.ll_not_cool)
        LinearLayout ll_not_cool;
        @Bind(R.id.tv_count_cool)
        TextView tv_count_cool;
        @Bind(R.id.tv_count_not_cool)
        TextView tv_count_not_cool;

        public CreateHolder(View itemView) {
            super(itemView);
        }
    }
}
