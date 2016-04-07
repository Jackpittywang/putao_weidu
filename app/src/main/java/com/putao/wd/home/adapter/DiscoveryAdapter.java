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
//        holder.iv_discovery_player/*.addProcessor(new BlurProcessor(8))*/.setImageURL(product.getImage());
        holder.tv_title.setText("发现" + position);
        System.out.println("===============" + position);
//        final Bundle bundle = new Bundle();
//        holder.iv_discovery_player.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                bundle.putString(YoukuVideoPlayerActivity.BUNDLE_VID, isMore ? mExploreIndex.getUrl() : mExploreIndex.getBanner().get(0).getUrl());
//                context.startActivity(YoukuVideoPlayerActivity.class, bundle);
//                System.out.println("看视频");
//            }
//        });
//
//        holder.tv_title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("同样看视频");
//            }
//        });
    }

    /**
     * 发现视频视图
     */
    static class DiscoveryViewHolder extends BasicViewHolder {

        @Bind(R.id.iv_discovery_player)
        ImageView iv_discovery_player;
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
