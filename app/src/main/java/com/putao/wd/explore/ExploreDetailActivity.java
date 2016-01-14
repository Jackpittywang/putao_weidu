package com.putao.wd.explore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.sunnybear.library.view.SwipeBackLayout;

/**
 * 首页详情
 * Created by guchenkai on 2016/1/11.
 */
public class ExploreDetailActivity extends AppCompatActivity implements SwipeBackLayout.SwipeBackListener {
    //    @Bind(R.id.vp_container)
    ViewPager vp_container;

    private SwipeBackLayout mSwipeBackLayout;
    private ImageView ivShadow;

    private SparseArray<Fragment> mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContainer());
        View view = LayoutInflater.from(this).inflate(R.layout.activity_nexplore_detail, null);
        mSwipeBackLayout.addView(view);

        vp_container = (ViewPager) findViewById(R.id.vp_container);
        addFragment();
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
        };
        vp_container.setAdapter(adapter);
        vp_container.setCurrentItem(0);

        mSwipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.TOP);
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        mSwipeBackLayout = new SwipeBackLayout(this);
        mSwipeBackLayout.setOnSwipeBackListener(this);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.popup_background));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(mSwipeBackLayout);
        return container;
    }

    /**
     * 初始化Fragment
     */
    private void addFragment() {
        mFragments = new SparseArray<>();
        mFragments.put(0, Fragment.instantiate(this, ExploreDetailFragment.class.getName()));
        mFragments.put(1, Fragment.instantiate(this, ExploreDetailFragment.class.getName()));
        mFragments.put(2, Fragment.instantiate(this, ExploreDetailFragment.class.getName()));
        mFragments.put(3, Fragment.instantiate(this, ExploreDetailFragment.class.getName()));
        mFragments.put(4, Fragment.instantiate(this, ExploreDetailFragment.class.getName()));
        mFragments.put(5, Fragment.instantiate(this, ExploreDetailFragment.class.getName()));
        mFragments.put(6, Fragment.instantiate(this, ExploreDetailFragment.class.getName()));
        mFragments.put(7, Fragment.instantiate(this, ExploreDetailFragment.class.getName()));
    }

    @Override
    public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
        ivShadow.setAlpha(1 - fractionScreen);
        if (fractionScreen == 1)
            finish();
    }
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_nexplore_detail;
//    }
//
//    @Override
//    protected void onViewCreatedFinish(Bundle saveInstanceState) {
//        addFragment();
//        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public int getCount() {
//                return mFragments.size();
//            }
//
//            @Override
//            public Fragment getItem(int position) {
//                return mFragments.get(position);
//            }
//        };
//        vp_container.setAdapter(adapter);
//        vp_container.setCurrentItem(0);
//    }
//
//    @Override
//    protected String[] getRequestUrls() {
//        return new String[0];
//    }
}
