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
 * 广告位
 * Created by guchenkai on 2015/11/25.
 */
public class BannerLayout extends RelativeLayout {
    private ViewPager mBannerView;
    private CircleIndicator mIndicator;

    public BannerLayout(Context context) {
        this(context, null, 0);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        init();
    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.widget_banner, null);
        mBannerView = (ViewPager) root.findViewById(R.id.vp_banner);
        mIndicator = (CircleIndicator) root.findViewById(R.id.ci_indicator);
    }

    private void init() {
        mIndicator.setViewPager(mBannerView);
    }

    public void setAdapter(PagerAdapter adapter) {
        mBannerView.setAdapter(adapter);
    }
}
