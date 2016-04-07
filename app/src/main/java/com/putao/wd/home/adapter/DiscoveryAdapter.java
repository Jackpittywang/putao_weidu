package com.putao.wd.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.DisCovery;
import com.putao.wd.model.StoreProduct;
import com.putao.wd.video.YoukuVideoPlayerActivity;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/5.
 */
public class DiscoveryAdapter extends LoadMoreAdapter<DisCovery, DiscoveryAdapter.DiscoveryViewHolder> {

    public DiscoveryAdapter(Context context, List<DisCovery> disCoveries) {
        super(context, disCoveries);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_discovery_item;
    }

    @Override
    public DiscoveryViewHolder getViewHolder(View itemView, int viewType) {
        return new DiscoveryViewHolder(itemView);
    }

    @Override
    public void onBindItem(final DiscoveryViewHolder holder, DisCovery disCoveries, int position) {
        holder.tv_title.setText(disCoveries.getTitle());
        holder.iv_discovery_player.setImageURL(disCoveries.getVideo_img());
//        holder.tv_style.setText(disCoveries.getTag());
//        holder.tv_time.setText(disCoveries.getTime());
    }

    /**
     * 发现视频视图
     */
    static class DiscoveryViewHolder extends BasicViewHolder {

        @Bind(R.id.iv_discovery_player)
        ImageDraweeView iv_discovery_player;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_style)
        TextView tv_style;
        @Bind(R.id.tv_time)
        TextView tv_time;

        public DiscoveryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
