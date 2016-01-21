package com.putao.wd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.PreferenceUtils;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 引导页
 * Created by guchenkai on 2015/12/23.
 */
public class GuidanceActivity extends BasicFragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private static final int[] icons = new int[]{
            R.drawable.img_wt_01,
            R.drawable.img_wt_02,
            R.drawable.img_wt_03,
            R.drawable.img_wt_04
    };

    @Bind(R.id.vp_guidance)
    ViewPager vp_guidance;
    @Bind(R.id.btn_start)
    Button btn_start;
    @Bind(R.id.ci_indicator)
    CirclePageIndicator ci_indicator;

    private FragmentPagerAdapter adapter;
    private AlphaAnimation animation;//渐现动画

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guidance;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initAnimation();
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new GuidanceFragment(icons[position]);
            }

            @Override
            public int getCount() {
                return icons.length;
            }
        };
        vp_guidance.setAdapter(adapter);
        vp_guidance.setOffscreenPageLimit(icons.length);
        ci_indicator.setViewPager(vp_guidance);
        vp_guidance.addOnPageChangeListener(this);
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        animation = new AlphaAnimation(0.0F, 1.0F);
        animation.setDuration(1000);
        animation.setFillAfter(true);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.btn_start)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                PreferenceUtils.save(GlobalApplication.PREFERENCE_KEY_IS_FIRST, true);
                startActivity(IndexActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (position == icons.length - 1) {
            btn_start.setVisibility(View.VISIBLE);
            btn_start.startAnimation(animation);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            startActivity(MainActivity.class);
//            finish();
            return exit();
        }
        return super.dispatchKeyEvent(event);
    }
}
