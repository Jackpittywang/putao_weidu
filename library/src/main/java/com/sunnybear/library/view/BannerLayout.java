package com.sunnybear.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.sunnybear.library.R;
import com.sunnybear.library.view.indicator.CirclePageIndicator;
import com.sunnybear.library.view.viewpager.AutoScrollPagerAdapter;
import com.sunnybear.library.view.viewpager.AutoScrollViewPager;

/**
 * 广告展示控件
 * Created by guchenkai on 2015/12/7.
 */
public class BannerLayout extends RelativeLayout {
    private View mRootView;
    private AutoScrollViewPager mAutoScrollViewPager;
    private CirclePageIndicator mCirclePageIndicator;

    private long mIntervalTime;
    private int mFillColor;
    private int mPageColor;

    private AutoScrollPagerAdapter.OnClickItemListener mOnClickItemListener;

    public void setOnClickItemListener(AutoScrollPagerAdapter.OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
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
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout);
        mIntervalTime = array.getInt(R.styleable.BannerLayout_banner_interval_time, 3) * 1000;
        mFillColor = array.getColor(R.styleable.BannerLayout_banner_fill_color, -1);
        mPageColor = array.getColor(R.styleable.BannerLayout_banner_page_color, -1);
        array.recycle();
    }

    private void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_banner, this);
        mAutoScrollViewPager = (AutoScrollViewPager) mRootView.findViewById(R.id.vp_banner);
        mCirclePageIndicator = (CirclePageIndicator) mRootView.findViewById(R.id.ci_indicator);

        mAutoScrollViewPager.startAutoScroll(mIntervalTime);
        if (mFillColor != -1)
            mCirclePageIndicator.setFillColor(mFillColor);
        if (mPageColor != -1)
            mCirclePageIndicator.setPageColor(mPageColor);
    }

    public void setAdapter(AutoScrollPagerAdapter adapter) {
        mAutoScrollViewPager.setAdapter(adapter);
        mCirclePageIndicator.setViewPager(mAutoScrollViewPager);
    }

    public void stopAutoScroll() {
        mAutoScrollViewPager.stopAutoScroll();
    }
}
