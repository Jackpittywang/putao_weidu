package com.putao.wd.store.product.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.viewpager.BannerAdapter;
import com.sunnybear.library.view.viewpager.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情广告页
 * Created by guchenkai on 2015/12/18.
 */
public class ProductBannerAdapter extends BannerAdapter {
    private Context context;
    private List<String> banners;
    private BannerViewPager.OnPagerClickListenr onPagerClickListenr;

    public ProductBannerAdapter(Context context, List<String> banners, BannerViewPager.OnPagerClickListenr onPagerClickListenr) {
        this.context = context;
        setBanners(banners);
        setOnPagerClickListenr(onPagerClickListenr);
    }

    public void setBanners(List<String> banners) {
        this.banners = banners != null && banners.size() > 0 ? banners : new ArrayList<String>();
    }

    @Override
    public View getView(int position) {
        ImageDraweeView imageView = new ImageDraweeView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) DensityUtil.px2dp(context, 200));
        imageView.setLayoutParams(params);
        imageView.setScaleType(ScalingUtils.ScaleType.FIT_XY);
        String banner = banners.get(position);
        imageView.setImageURL(banner);
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
    public void updateView(List<String> banners) {
        setBanners(banners);
        notifyDataSetChanged();
    }
}
