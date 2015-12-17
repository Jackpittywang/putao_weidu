package com.sunnybear.library.view.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.sunnybear.library.R;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * 广告栏
 * Created by guchenkai on 2015/12/9.
 */
public class BannerLayout extends RelativeLayout {
    private View mRootView;
    private BannerViewPager mViewPager;
    private CirclePageIndicator mIndicator;

    private long mIntervalTime;
    private boolean isAutoScroll;
    private int mFillColor;
    private int mPageColor;

    private BannerViewPager.OnPagerClickListenr mOnPagerClickListenr;
    private FixedSpeedScroller mFixedSpeedScroller;

    public void setOnPagerClickListenr(BannerViewPager.OnPagerClickListenr onPagerClickListenr) {
        BannerAdapter adapter = (BannerAdapter) mViewPager.getAdapter();
        adapter.setOnPagerClickListenr(onPagerClickListenr);
    }

    public BannerLayout(Context context) {
        this(context, null, 0);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyleable(context, attrs);
        initView(context);
        init();
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout);
        mIntervalTime = array.getInt(R.styleable.BannerLayout_intervalTime, 3) * 1000;
        isAutoScroll = array.getBoolean(R.styleable.BannerLayout_isAutoScroll, true);
        mFillColor = array.getColor(R.styleable.BannerLayout_fill_color, -1);
        mPageColor = array.getColor(R.styleable.BannerLayout_page_color, -1);
        array.recycle();
    }

    private void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_banner, this);
        mViewPager = (BannerViewPager) mRootView.findViewById(R.id.vp_banner);
        mIndicator = (CirclePageIndicator) mRootView.findViewById(R.id.ci_indicator);
    }

    private void init() {
        if (mFillColor != -1)
            mIndicator.setFillColor(mFillColor);
        if (mPageColor != -1)
            mIndicator.setPageColor(mPageColor);
    }

    public void setAdapter(PagerAdapter adapter) {
        if (isAutoScroll) mViewPager.startAutoScroll(mIntervalTime);
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
    }

    public boolean startAutoScroll() {
        return mViewPager.startAutoScroll();
    }

    public boolean startAutoScroll(long intervalTime) {
        return mViewPager.startAutoScroll(intervalTime);
    }

    public boolean stopAutoScroll() {
        return mViewPager.stopAutoScroll();
    }

    /**
     * 设置缓存页面数
     *
     * @param limit 缓存页面数
     */
    public void setOffscreenPageLimit(int limit) {
        mViewPager.setOffscreenPageLimit(limit);
    }
}
