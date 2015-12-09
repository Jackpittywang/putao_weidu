package com.sunnybear.library.view.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.sunnybear.library.util.Logger;

/**
 * 广告ViewPager
 * Created by guchenkai on 2015/12/9.
 */
public class BannerViewPager extends ViewPager {
    private long mIntervalTime;

    private int mFillColor;
    private int mPageColor;

    private int mSize;//有几个页面

    private int currentItem = 0;
    private Handler mHandler;
    private Runnable mAutoScrollRunnable;

    private OnPagerClickListenr mOnPagerClickListenr;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int index = currentItem % mSize;
                Logger.d(index + "");
                if (index == 0)
                    setCurrentItem(index, false);
                else
                    setCurrentItem(index, true);
            }
        };
        mAutoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                currentItem++;
                mHandler.sendEmptyMessage(1);
                mHandler.postDelayed(this, mIntervalTime);
            }
        };
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public boolean startAutoScroll() {
        mHandler.postDelayed(mAutoScrollRunnable, mIntervalTime);
        return false;
    }

    public boolean startAutoScroll(long intervalTime) {
        mIntervalTime = intervalTime;
        mHandler.postDelayed(mAutoScrollRunnable, intervalTime);
        return false;
    }

    public boolean stopAutoScroll() {
        mHandler.removeCallbacks(mAutoScrollRunnable);
        return true;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        mSize = adapter.getCount();
    }

    /**
     * pager item点击监听
     */
    public interface OnPagerClickListenr {

        void onPagerClick(int position);
    }
}
