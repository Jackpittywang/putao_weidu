package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Companion;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/5.
 */
public class SubscriptionNumberAdapter extends LoadMoreAdapter<Companion, SubscriptionNumberAdapter.SubscriptionViewHolder> {


    public SubscriptionNumberAdapter(Context context, List<Companion> companions) {
        super(context, companions);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_companion_subribe_listitem;
    }

    @Override
    public SubscriptionViewHolder getViewHolder(View itemView, int viewType) {
        return new SubscriptionViewHolder(itemView);
    }

    @Override
    public void onBindItem(SubscriptionViewHolder holder, Companion companion, int position) {
        holder.tv_title.setText("订阅号名称限制一行" + position);
    }

    static class SubscriptionViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_icon)
        ImageDraweeView iv_icon;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;

        public SubscriptionViewHolder(View itemView) {
            super(itemView);
        }
    }
}
