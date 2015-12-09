package com.sunnybear.library.view.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 广告栏适配器
 * Created by guchenkai on 2015/12/9.
 */
public abstract class BannerAdapter extends PagerAdapter {
    private BannerViewPager.OnPagerClickListenr mOnPagerClickListenr;

    public void setOnPagerClickListenr(BannerViewPager.OnPagerClickListenr onPagerClickListenr) {
        mOnPagerClickListenr = onPagerClickListenr;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = getView(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPagerClickListenr != null)
                    mOnPagerClickListenr.onPagerClick(position);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public abstract View getView(int position);
}
