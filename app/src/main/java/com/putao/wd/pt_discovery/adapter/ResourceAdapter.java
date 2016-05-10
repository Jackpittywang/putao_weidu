package com.putao.wd.pt_discovery.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.model.DiscoveryResource;
import com.putao.wd.model.FindResource;
import com.putao.wd.model.ResourceBanner;
import com.putao.wd.model.ResourceBannerAndTag;
import com.putao.wd.model.ResourceTag;
import com.putao.wd.pt_discovery.CampaignActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.util.ResourcesUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;
import com.sunnybear.library.view.viewpager.banner.ConvenientBanner;
import com.sunnybear.library.view.viewpager.banner.holder.CBViewHolderCreator;
import com.sunnybear.library.view.viewpager.banner.holder.Holder;
import com.sunnybear.library.view.viewpager.banner.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ResourceAdapter extends LoadMoreAdapter<FindResource, BasicViewHolder> {
    private static final int KEY_HEAD = 0XFF;// 头部 轮播图

    private static final int KEY_STICK = 0XFD;// 置顶文章
    private static final int KEY_RESOURCE = 0XFC;//文章条目
    private static final int KEY_HOT_TAG = 0XFA;//文章标签

    private List<ResourceBanner> banners;
    private List<ResourceTag> hotTags;
    private ImageHolderView mImageHolderView;

    public ResourceAdapter(Context context, List<FindResource> resous) {
        super(context, resous);
    }

    @Override
    public int getMultiItemViewType(int position) {
        if (position == 0) {
            return KEY_HEAD;
        } else if (position == 1) {
            return KEY_HOT_TAG;
        } else if (mItems.get(position).is_recommend()) {
            return KEY_STICK;
        } else {
            return KEY_RESOURCE;
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        if (KEY_HEAD == viewType) {
            return R.layout.discovery_header;
        } else if (KEY_HOT_TAG == viewType) {
            return R.layout.discovery_hottag;
        } else if (KEY_STICK == viewType) {
            return R.layout.discovery_stick;
        } else {
            return R.layout.discovery_resource;
        }
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {

        if (KEY_HEAD == viewType) {
            return new ResourceAdapter.HeaderHolder(itemView);
        } else if (KEY_HOT_TAG == viewType) {
            return new ResourceAdapter.HotTagHolder(itemView);
        } else {
            return new ResourceAdapter.ResourceHolder(itemView);
        }
    }

    @Override
    public void onBindItem(BasicViewHolder holder, FindResource resou, int position) {

        if (position == 0) {
            final HeaderHolder headerHolder = (HeaderHolder) holder;
            if (null == mImageHolderView) {
                mImageHolderView = new ImageHolderView();

                headerHolder.cb_banner.setPages(new CBViewHolderCreator<ImageHolderView>() {
                    @Override
                    public ImageHolderView createHolder() {
                        return new ImageHolderView();
                    }
                }, banners);
            }
            headerHolder.cb_banner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    EventBusHelper.post(headerHolder.cb_banner.getCurrentItem(), AccountConstants.EventBus.EVENT_DISCOVERY_CAROUSEL);
                }
            });
        } else if (position == 1) {
            HotTagHolder hotTagHolder = (HotTagHolder) holder;

            HotTagAdapter hotTagAdapter = new HotTagAdapter(context, hotTags);
            hotTagHolder.rv_discovery_hot_tag.setAdapter(hotTagAdapter);
            hotTagAdapter.setOnItemClickListener(new com.sunnybear.library.view.recycler.listener.OnItemClickListener<ResourceTag>() {

                @Override
                public void onItemClick(ResourceTag tag, int position) {
                    EventBusHelper.post(tag, AccountConstants.EventBus.EVENT_DISCOVERY_HOT_TAG);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable(CampaignActivity.TAGID, hotTags.get(position).getId());
//                    context.startActivity(CampaignActivity.class, bundle);
                }
            });
        } else {
            ResourceHolder resHolder = (ResourceHolder) holder;
            resHolder.iv_discovery_pic.setImageURL(resou.getIcon());
            resHolder.tv_discovery_title.setText(resou.getTitle());
            resHolder.tv_show_top.setVisibility(resou.is_top() ? View.VISIBLE : View.GONE);
            resHolder.view_gray.setVisibility(resou.is_show_view() ? View.VISIBLE : View.GONE);
            String[] tags = new String[]{};
            if (resou.getTag() != null) {
                tags = resou.getTag().split("#");
            }
            switch (tags.length) {
                case 0:
                case 1:
                    resHolder.tv_tag1.setVisibility(View.GONE);
                    resHolder.tv_tag2.setVisibility(View.GONE);
                    resHolder.tv_tag3.setVisibility(View.GONE);
                    break;
                case 2:
                    resHolder.tv_tag1.setVisibility(View.VISIBLE);
                    resHolder.tv_tag2.setVisibility(View.GONE);
                    resHolder.tv_tag3.setVisibility(View.GONE);
                    resHolder.tv_tag1.setText(tags[1]);
                    break;
                case 3:
                    resHolder.tv_tag1.setVisibility(View.VISIBLE);
                    resHolder.tv_tag2.setVisibility(View.VISIBLE);
                    resHolder.tv_tag3.setVisibility(View.GONE);
                    resHolder.tv_tag1.setText(tags[1]);
                    resHolder.tv_tag2.setText(tags[2]);
                    break;
                case 4:
                    resHolder.tv_tag1.setVisibility(View.VISIBLE);
                    resHolder.tv_tag2.setVisibility(View.VISIBLE);
                    resHolder.tv_tag3.setVisibility(View.VISIBLE);
                    resHolder.tv_tag1.setText(tags[1]);
                    resHolder.tv_tag2.setText(tags[2]);
                    resHolder.tv_tag3.setText(tags[3]);
                    break;
            }
        }
    }


    public static class HotTagHolder extends BasicViewHolder {
        @Bind(R.id.rv_discovery_hot_tag)
        BasicRecyclerView rv_discovery_hot_tag;

        public HotTagHolder(View itemView) {
            super(itemView);
        }
    }

    public static class HeaderHolder extends BasicViewHolder {

        @Bind(R.id.cb_banner)
        ConvenientBanner<ResourceBanner> cb_banner;

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ResourceHolder extends BasicViewHolder {

        @Bind(R.id.ll_resource)
        LinearLayout ll_resource;
        @Bind(R.id.iv_discovery_pic)
        ImageDraweeView iv_discovery_pic;
        @Bind(R.id.tv_discovery_title)
        TextView tv_discovery_title;
        @Bind(R.id.tv_show_top)
        TextView tv_show_top;
        @Bind(R.id.tv_tag1)
        TextView tv_tag1;
        @Bind(R.id.tv_tag2)
        TextView tv_tag2;
        @Bind(R.id.tv_tag3)
        TextView tv_tag3;
        @Bind(R.id.view_gray)
        View view_gray;

        public ResourceHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 广告页适配器
     */
    static class ImageHolderView implements Holder<ResourceBanner> {
        private ImageDraweeView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageDraweeView(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) DensityUtil.px2dp(context, 200));
            imageView.setLayoutParams(params);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, ResourceBanner data) {
            imageView.setImageURL(data.getBanner());
        }
    }

    public void setBannerAndTag(ResourceBannerAndTag bannerAndTag) {
        this.banners = bannerAndTag.getBanner();
        this.hotTags = bannerAndTag.getTag();
        notifyDataSetChanged();
    }
}
