package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Companion;
import com.putao.wd.model.SubscribeList;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 订阅号列表适配器
 * Created by Administrator on 2016/5/5.
 */
public class SubscriptionNumberAdapter extends LoadMoreAdapter<SubscribeList, SubscriptionNumberAdapter.SubscriptionViewHolder> {


    public SubscriptionNumberAdapter(Context context, List<SubscribeList> subscribeLists) {
        super(context, subscribeLists);
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
    public void onBindItem(SubscriptionViewHolder holder, SubscribeList subscribeLists, int position) {
        holder.tv_title.setText(subscribeLists.getService_name());
        holder.iv_icon.setImageURL(subscribeLists.getService_icon());
        holder.tv_content.setText(subscribeLists.getService_description());
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
