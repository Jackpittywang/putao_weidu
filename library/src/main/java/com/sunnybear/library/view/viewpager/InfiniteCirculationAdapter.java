package com.sunnybear.library.view.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 无限循环ViewPager适配器
 * Created by guchenkai on 2015/12/16.
 */
public abstract class InfiniteCirculationAdapter extends PagerAdapter {
    private int mCount;//数据条目
    private BannerViewPager.OnPagerClickListenr mOnPagerClickListenr;

    public void setOnPagerClickListenr(BannerViewPager.OnPagerClickListenr onPagerClickListenr) {
        mOnPagerClickListenr = onPagerClickListenr;
    }

    public InfiniteCirculationAdapter(int mCount) {
        this.mCount = mCount;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View mView = getView(position % mCount);
        container.addView(mView);
        return mView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 设置View
     *
     * @param position position
     * @return View
     */
    public abstract View getView(int position);
}
