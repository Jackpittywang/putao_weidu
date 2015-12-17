package com.sunnybear.library.view.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.lang.reflect.Field;

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

    private FixedSpeedScroller mFixedSpeedScroller;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int index = currentItem % mSize;
//                Logger.d(index + "");
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
        setPageTransformer(true, new PageTransformer() {
            private static final float MIN_SCALE = 0.85f;
            private static final float MIN_ALPHA = 0.5f;

            @Override
            public void transformPage(View page, float position) {
                int pageWidth = page.getWidth();
                int pageHeight = page.getHeight();

                if (position < -1) {
                    page.setAlpha(0);
                } else if (position <= 1) {
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0)
                        page.setTranslationX(horzMargin - vertMargin / 2);
                    else
                        page.setTranslationX(-horzMargin + vertMargin / 2);
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                    page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                            / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
                } else {
                    page.setAlpha(0);
                }
            }
        });
        controllViewPagerSpeed(context);
    }

    /**
     * 控制ViewPager滚动速度
     */
    private void controllViewPagerSpeed(Context context) {
        try {
            Field field = this.getClass().getSuperclass().getDeclaredField("mScroller");
            field.setAccessible(true);
            mFixedSpeedScroller = new FixedSpeedScroller(context, new AccelerateDecelerateInterpolator());
            field.set(this, mFixedSpeedScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
