package com.putao.wd.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.view.image.FastBlur;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.image.processor.ProcessorInterface;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/5.
 */
public class DiscoveryAdapter extends LoadMoreAdapter<DisCovery, DiscoveryAdapter.DiscoveryViewHolder> {
    private HashMap<Integer, Bitmap> mBitMap = new HashMap<>();

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
    public void replaceAll(List<DisCovery> disCoveries) {
//        mBitMap.clear();
        super.replaceAll(disCoveries);
    }

    @Override
    public void onBindItem(final DiscoveryViewHolder holder, final DisCovery disCoveries, final int position) {
        holder.iv_discovery.resize(600, 300);
//        holder.iv_discovery.setImageURL("");
//        holder.iv_blur.setPlaceholderImage(0);
        holder.iv_blur.setVisibility(View.GONE);
        holder.tv_title.setText(disCoveries.getTitle());
        holder.tv_subtitle.setText(disCoveries.getSubtitle());
//        holder.tv_subtitle.setText("这个就是视频介绍这个就是视频介绍这个就是视频介绍");
        if (!disCoveries.getVideo_img().equals("")) {
            holder.iv_player.setVisibility(View.VISIBLE);
            holder.iv_blur.setVisibility(View.VISIBLE);
            //添加后加载器方法
            holder.iv_discovery.addProcessor(new ProcessorInterface() {
                @Override
                public void process(Context context, Bitmap bitmap) {
                }

                @Override
                public void process(Context context, Bitmap bitmap, int position) {
//                    holder.iv_blur.setDefaultImage(FastBlur.doBlur(Bitmap.createBitmap(Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2, 0, bitmap.getWidth() / 2, bitmap.getHeight(), null, false)), 20, false));
//                    holder.iv_blur.setImageURL(disCoveries.getVideo_img());
                    boolean isRefreshItem = null == mBitMap.get(position);
                    mBitMap.put(position, FastBlur.doBlur(Bitmap.createBitmap(Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2, 0, bitmap.getWidth() / 2, bitmap.getHeight(), null, false)), 10, false));
                    if (isRefreshItem) notifyItemChanged(position);
                }
            }, position);
            holder.iv_discovery.setImageURL(disCoveries.getVideo_img(), true);
            if (null != mBitMap.get(position)) {
                holder.iv_blur.setDefaultImage(mBitMap.get(position));
                holder.iv_blur.setVisibility(View.VISIBLE);
            }
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
        @Bind(R.id.iv_blur)
        ImageDraweeView iv_blur;

        public DiscoveryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
