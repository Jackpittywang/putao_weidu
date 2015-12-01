package com.sunnybear.library.view.viewpager;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by guchenkai on 2015/12/1.
 */
public abstract class AutoScrollPagerAdapter extends RecyclingPagerAdapter {
    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        return null;
    }

    @Override
    /**
     * Note: use getItemCount instead*/
    public final int getCount() {
        return getItemCount() * AutoScrollViewPager.FakePositionHelper.MULTIPLIER;
    }

    @Deprecated

    protected View getViewInternal(int position, View convertView, ViewGroup container) {
        if(getItemCount()==0)
            return null;
        return getView(position % getItemCount(), convertView, container);
    }

    public abstract int getItemCount();
}
