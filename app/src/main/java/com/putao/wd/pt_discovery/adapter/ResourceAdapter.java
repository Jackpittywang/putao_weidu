package com.putao.wd.pt_discovery.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Resou;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;
import com.sunnybear.library.view.recycler.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.sunnybear.library.view.viewpager.banner.ConvenientBanner;
import com.sunnybear.library.view.viewpager.banner.holder.CBViewHolderCreator;
import com.sunnybear.library.view.viewpager.banner.holder.Holder;
import com.sunnybear.library.view.viewpager.transformer.CardsTransformer;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ResourceAdapter extends LoadMoreAdapter<Resou, BasicViewHolder> implements StickyRecyclerHeadersAdapter<ResourceAdapter.HotTagHolder> {
    private static final int KEY_HEAD = 0XFF;// 头部 轮播图

    private static final int KEY_STICK = 0XFD;// 置顶文章
    private static final int KEY_RESOURCE = 0XFC;//文章条目

    private List<String> drawables;
    private List<String> hotTags;

    public ResourceAdapter(Context context, List<Resou> resous, List<String> drawables, List<String> hotTags) {
        super(context, resous);
        this.drawables = drawables;
        this.hotTags = hotTags;
    }

    @Override
    public int getMultiItemViewType(int position) {
        if (position == 0) {
            return KEY_HEAD;
        } else if (mItems.get(position).isBig) {
            return KEY_STICK;
        } else {
            return KEY_RESOURCE;
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        if (KEY_HEAD == viewType) {
            return R.layout.discovery_header;
        } else if (KEY_STICK == viewType) {
            return R.layout.discovery_stick;
        } else {
            return R.layout.discovery_resource;
        }
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {

        if (KEY_HEAD == viewType){
            return new ResourceAdapter.HeaderHolder(itemView);
        }
        else{
            return new ResourceAdapter.ResourceHolder(itemView);
        }
    }

    @Override
    public void onBindItem(BasicViewHolder holder, Resou resou, int position) {

        if (position == 0) {

            HeaderHolder headerHolder = (HeaderHolder) holder;
            // EventBusHelper.post(headerHolder, AccountConstants.EventBus.EVENT_DISCOVERY_CAROUSEL);

            headerHolder.cb_banner.setPages(new CBViewHolderCreator<ImageHolderView>() {
                @Override
                public ImageHolderView createHolder() {
                    return new ImageHolderView();
                }
            }, drawables).setPageTransformer(new CardsTransformer());

        } else {
            ResourceHolder resHolder = (ResourceHolder) holder;

            resHolder.iv_discovery_pic.setImageURL(resou.pic);
            resHolder.tv_discovery_title.setText(resou.title);
            resHolder.tv_show_top.setVisibility(resou.isTop ? View.VISIBLE : View.GONE);

            int length = resou.tags.size();

            switch (length) {
                case 0:
                    resHolder.tv_tag1.setVisibility(View.GONE);
                    resHolder.tv_tag2.setVisibility(View.GONE);
                    resHolder.tv_tag3.setVisibility(View.GONE);
                    break;
                case 1:
                    resHolder.tv_tag1.setVisibility(View.VISIBLE);
                    resHolder.tv_tag2.setVisibility(View.GONE);
                    resHolder.tv_tag3.setVisibility(View.GONE);
                    resHolder.tv_tag1.setText(resou.tags.get(0));
                    break;
                case 2:
                    resHolder.tv_tag1.setVisibility(View.VISIBLE);
                    resHolder.tv_tag2.setVisibility(View.VISIBLE);
                    resHolder.tv_tag3.setVisibility(View.GONE);
                    resHolder.tv_tag1.setText(resou.tags.get(0));
                    resHolder.tv_tag2.setText(resou.tags.get(1));
                    break;
                case 3:
                    resHolder.tv_tag1.setVisibility(View.VISIBLE);
                    resHolder.tv_tag2.setVisibility(View.VISIBLE);
                    resHolder.tv_tag3.setVisibility(View.VISIBLE);
                    resHolder.tv_tag1.setText(resou.tags.get(0));
                    resHolder.tv_tag2.setText(resou.tags.get(1));
                    resHolder.tv_tag3.setText(resou.tags.get(2));
                    break;
            }
        }
    }

    @Override
    public long getHeaderId(int position) {
        return position > 0 ? 0 : -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discovery_hottag, parent, false);
        return new ResourceAdapter.HotTagHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HotTagHolder holder, int position) {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        HotTagAdapter hotTagAdapter = new HotTagAdapter(context, hotTags);
        holder.rv_discovery_hot_tag.setAdapter(hotTagAdapter);

        holder.rv_discovery_hot_tag.setLayoutManager(linearLayoutManager);
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
        ConvenientBanner<String> cb_banner;

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ResourceHolder extends BasicViewHolder {

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

        public ResourceHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 广告页适配器
     */
    static class ImageHolderView implements Holder<String> {
        private ImageDraweeView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageDraweeView(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) DensityUtil.px2dp(context, 200));
            imageView.setLayoutParams(params);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageURL(data);
        }
    }
}
