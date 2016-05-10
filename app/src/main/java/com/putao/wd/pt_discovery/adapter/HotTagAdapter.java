package com.putao.wd.pt_discovery.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ResourceTag;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/5.
 */
public class HotTagAdapter extends BasicAdapter<ResourceTag,HotTagAdapter.HotHolder> {


    public HotTagAdapter(Context context, List<ResourceTag> tags) {
        super(context, tags);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.discovery_hottag_item;
    }

    @Override
    public HotHolder getViewHolder(View itemView, int viewType) {
        return new HotTagAdapter.HotHolder(itemView);
    }

    @Override
    public void onBindItem(HotHolder holder, ResourceTag tag, int position) {
        holder.idv_tag.setImageURL(tag.getTag_icon());
        //holder.tv_hot_tag.setText(StringUtils.equals(tag.getDisplay_type(),"0") ? tag.getTag_name():"活动");
        holder.tv_hot_tag.setText(tag.getTag_name());
    }

    static class HotHolder extends BasicViewHolder{
        @Bind(R.id.idv_tag)
        ImageDraweeView idv_tag;
        @Bind(R.id.tv_hot_tag)
        TextView tv_hot_tag;
        public HotHolder(View itemView) {
            super(itemView);
        }
    }
}

