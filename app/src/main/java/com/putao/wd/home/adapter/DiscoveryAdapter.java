package com.putao.wd.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
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
    public void onBindItem(final DiscoveryViewHolder holder, final DisCovery disCoveries, final int position) {
        holder.iv_discovery.resize(600, 300);
        holder.tv_title.setText(disCoveries.getTitle());
//        holder.tv_subtitle.setText(disCoveries.getSubtitle());
        holder.tv_subtitle.setText("这个就是视频介绍这个就是视频介绍这个就是视频介绍");
        if (!disCoveries.getVideo_img().equals("")) {
            holder.iv_player.setVisibility(View.VISIBLE);
            holder.iv_discovery.setImageURL(disCoveries.getVideo_img());
        } else {
            holder.iv_player.setVisibility(View.GONE);
        }

        holder.iv_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YouMengHelper.onEvent(context, YouMengHelper.DiscoverHome_watch);
                Bundle bundle = new Bundle();
                bundle.putSerializable(YoukuVideoPlayerActivity.BUNDLE_VID, disCoveries.getVideo_url());
                context.startActivity(YoukuVideoPlayerActivity.class, bundle);
            }
        });
    }

    /**
     * 发现视频视图
     */
    static class DiscoveryViewHolder extends BasicViewHolder {

        @Bind(R.id.iv_discovery)
        ImageDraweeView iv_discovery;
        @Bind(R.id.iv_player)
        ImageView iv_player;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_subtitle)
        TextView tv_subtitle;

        public DiscoveryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
