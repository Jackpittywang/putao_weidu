package com.putao.wd.pt_me.collection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.putao.wd.R;
import com.putao.wd.model.Collection;
import com.putao.wd.model.Create;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.putao.wd.R.id.iv_icon;

/**
 * 我的收藏的适配器
 * Created by Administrator on 2016/4/6.
 */
public class CollectionAdapter extends LoadMoreAdapter<Collection, CollectionAdapter.CollectionViewHodler> {

    public CollectionAdapter(Context context, List<Collection> collections) {
        super(context, collections);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_participation_collection;
    }

    @Override
    public CollectionViewHodler getViewHolder(View itemView, int viewType) {
        return new CollectionViewHodler(itemView);
    }

    @Override
    public void onBindItem(final CollectionViewHodler holder, Collection collection, int position) {
        holder.tv_title.setText(collection.getTitle());
        holder.tv_content.setText(collection.getSubtitle());
        holder.iv_icon.setImageURL(ImageUtils.getImageSizeUrl(collection.getHead_img(), ImageUtils.ImageSizeURL.SIZE_240x240));
    }

    static class CollectionViewHodler extends BasicViewHolder {

        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.iv_icon)
        ImageDraweeView iv_icon;

        public CollectionViewHodler(View itemView) {
            super(itemView);
        }
    }
}
