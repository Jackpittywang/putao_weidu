package com.putao.wd.explore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragmentActivity;


import butterknife.Bind;

/**
 * 首页详情
 * Created by guchenkai on 2016/1/11.
 */
public class ExploreDetailActivity extends BasicFragmentActivity {

    @Bind(R.id.vp_container)
    ViewPager vp_container;

    private SparseArray<Fragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nexplore_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
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
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 初始化Fragment
     */
    private void addFragment() {
        mFragments = new SparseArray<>();
        mFragments.put(0, Fragment.instantiate(mContext, ExploreDetailFragment.class.getName()));
        mFragments.put(1, Fragment.instantiate(mContext, ExploreDetailFragment.class.getName()));
        mFragments.put(2, Fragment.instantiate(mContext, ExploreDetailFragment.class.getName()));
        mFragments.put(3, Fragment.instantiate(mContext, ExploreDetailFragment.class.getName()));
        mFragments.put(4, Fragment.instantiate(mContext, ExploreDetailFragment.class.getName()));
        mFragments.put(5, Fragment.instantiate(mContext, ExploreDetailFragment.class.getName()));
        mFragments.put(6, Fragment.instantiate(mContext, ExploreDetailFragment.class.getName()));
    }

}
