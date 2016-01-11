package com.putao.wd.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.putao.wd.model.StoreBanner;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.viewpager.BannerAdapter;
import com.sunnybear.library.view.viewpager.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城广告栏
 * Created by wango on 2015/12/11.
 */
@Deprecated
public class StoreBannerAdapter extends BannerAdapter {
    private Context context;
    private List<StoreBanner> banners;
    private BannerViewPager.OnPagerClickListenr onPagerClickListenr;

    public StoreBannerAdapter(Context context, List<StoreBanner> banners, BannerViewPager.OnPagerClickListenr onPagerClickListenr) {
        this.context = context;
        setBanners(banners);
        setOnPagerClickListenr(onPagerClickListenr);
    }

    public void setBanners(List<StoreBanner> banners) {
        this.banners = banners != null && banners.size() > 0 ? banners : new ArrayList<StoreBanner>();
    }

    @Override
    public View getView(int position) {
        ImageDraweeView imageView = new ImageDraweeView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) DensityUtil.px2dp(context, 200));
        imageView.setLayoutParams(params);
        StoreBanner banner = banners.get(position);
        imageView.setImageURL(banner.getImage());
        return imageView;
    }

    @Override
    public int getCount() {
        return banners.size();
    }

    /**
     * 更新界面
     *
     * @param banners
     */
    public void updateView(List<StoreBanner> banners) {
        setBanners(banners);
        notifyDataSetChanged();
    }
}
