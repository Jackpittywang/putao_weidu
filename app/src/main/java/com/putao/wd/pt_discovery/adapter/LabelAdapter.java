package com.putao.wd.pt_discovery.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.DisCovery;
import com.putao.wd.model.ResourceTag;
import com.putao.wd.model.Resources;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 标签列表适配器
 * Created by Administrator on 2016/5/6.
 */
public class LabelAdapter extends LoadMoreAdapter<Resources, LabelAdapter.LabelViewHolder> {

    public LabelAdapter(Context context, List<Resources> resources) {
        super(context, resources);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_discovery_label_special_item;
    }

    @Override
    public LabelViewHolder getViewHolder(View itemView, int viewType) {
        return new LabelViewHolder(itemView);
    }

    @Override
    public void onBindItem(LabelViewHolder holder, Resources resources, int position) {
        holder.tv_title.setText(resources.getTitle());
        holder.iv_icon.setImageURL(resources.getIcon());
        String[] tags = new String[]{};
        if (resources.getTag() != null) {
            tags = resources.getTag().split("#");
        }
        switch (tags.length) {
            case 0:
            case 1:
                holder.tv_labelOne.setVisibility(View.GONE);
                holder.tv_labelSecond.setVisibility(View.GONE);
                holder.tv_labelThird.setVisibility(View.GONE);
                break;
            case 2:
                holder.tv_labelOne.setVisibility(View.VISIBLE);
                holder.tv_labelSecond.setVisibility(View.GONE);
                holder.tv_labelThird.setVisibility(View.GONE);
                holder.tv_labelOne.setText(tags[1]);
                break;
            case 3:
                holder.tv_labelOne.setVisibility(View.VISIBLE);
                holder.tv_labelSecond.setVisibility(View.VISIBLE);
                holder.tv_labelThird.setVisibility(View.GONE);
                holder.tv_labelOne.setText(tags[1]);
                holder.tv_labelSecond.setText(tags[2]);
                break;
            case 4:
                holder.tv_labelOne.setVisibility(View.VISIBLE);
                holder.tv_labelSecond.setVisibility(View.VISIBLE);
                holder.tv_labelThird.setVisibility(View.VISIBLE);
                holder.tv_labelOne.setText(tags[1]);
                holder.tv_labelSecond.setText(tags[2]);
                holder.tv_labelThird.setText(tags[3]);
                break;
        }
    }

    static class LabelViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_labelOne)
        TextView tv_labelOne;
        @Bind(R.id.tv_labelSecond)
        TextView tv_labelSecond;
        @Bind(R.id.tv_labelThird)
        TextView tv_labelThird;
        @Bind(R.id.iv_icon)
        ImageDraweeView iv_icon;


        public LabelViewHolder(View itemView) {
            super(itemView);
        }
    }
}
