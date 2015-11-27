package com.sunnybear.library.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.sunnybear.library.R;
import com.sunnybear.library.view.indicator.CircleIndicator;

/**
 * 广告栏控件
 * Created by guchenkai on 2015/11/27.
 */
public class BannerLayout extends RelativeLayout {
    private ViewPager mViewPager;
    private CircleIndicator mIndicator;

    private View mRootView;

    public BannerLayout(Context context) {
        this(context, null, 0);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化布局
     *
     * @param context context
     */
    private void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_banner, null);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.vp_banner);
        mIndicator = (CircleIndicator) mRootView.findViewById(R.id.ci_indicator);
        addView(mRootView);
    }

    public void setAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
    }
}
