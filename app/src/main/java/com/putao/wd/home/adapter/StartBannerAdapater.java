package com.putao.wd.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.putao.wd.model.Banner;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.viewpager.BannerAdapter;
import com.sunnybear.library.view.viewpager.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 葡星圈广告适配器
 * Created by guchenkai on 2015/12/10.
 */
public class StartBannerAdapater extends BannerAdapter {
    private Context context;
    private List<Banner> banners;
    private BannerViewPager.OnPagerClickListenr onPagerClickListenr;

    public StartBannerAdapater(Context context, List<Banner> banners, BannerViewPager.OnPagerClickListenr onPagerClickListenr) {
        this.context = context;
        setBanners(banners);
        setOnPagerClickListenr(onPagerClickListenr);
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners != null && banners.size() > 0 ? banners : new ArrayList<Banner>();
    }

    @Override
    public View getView(int position) {
        ImageDraweeView imageView = new ImageDraweeView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) DensityUtil.px2dp(context, 200));
        imageView.setLayoutParams(params);
        Banner banner = banners.get(position);
        imageView.setImageURL(banner.getImg_url());
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
    public void updateView(List<Banner> banners) {
        setBanners(banners);
        notifyDataSetChanged();
    }
}