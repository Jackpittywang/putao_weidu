package com.sunnybear.library.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.sunnybear.library.R;

/**
 * 广告栏控件
 * Created by guchenkai on 2015/11/27.
 */
public class BannerLayout extends RelativeLayout {
    private View mRootView;
    private ViewPager mViewPager;

    private boolean is_carousel;//是否轮播
    private int carousel_interval;//轮播间隔时间

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
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout);
//        is_carousel = array.getBoolean(R.styleable.BannerLayout_is_carousel, false);
//        carousel_interval = array.getInt(R.styleable.BannerLayout_carousel_interval, 0);
//        array.recycle();
    }

    /**
     * 初始化布局
     *
     * @param context context
     */
    private void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_banner, null);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.vp_banner);
        addView(mRootView);
    }

    public void setAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
    }
}
