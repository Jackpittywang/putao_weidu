package com.putao.wd.pt_discovery.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.putao.wd.R;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/5.
 */
public class HotTagAdapter extends BasicAdapter<String,HotTagAdapter.HotHolder> {


    public HotTagAdapter(Context context, List<String> strings) {
        super(context, strings);
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
    public void onBindItem(HotHolder holder, String str, int position) {
        holder.idv_tag.setImageURL(str);
    }

    static class HotHolder extends BasicViewHolder{
        @Bind(R.id.idv_tag)
        ImageDraweeView idv_tag;
        public HotHolder(View itemView) {
            super(itemView);
        }
    }
}

