package com.putao.wd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.KeyEvent;

import com.putao.wd.home.PutaoCompanionFragment;
import com.putao.wd.home.PutaoCreatedFragment;
import com.putao.wd.home.PutaoExploreFragment;
import com.putao.wd.home.PutaoStoreFragment;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.view.select.TabBar;
import com.sunnybear.library.view.select.TabItem;
import com.sunnybear.library.view.viewpager.UnScrollableViewPager;

import butterknife.Bind;

/**
 * 新首页
 * Created by guchenkai on 2016/1/13.
 */
public class IndexActivity extends BasicFragmentActivity implements TabBar.OnTabItemSelectedListener {
    public static boolean isNotRefreshUserInfo = false;

    @Bind(R.id.vp_content)
    UnScrollableViewPager vp_content;
    @Bind(R.id.tb_tab)
    TabBar tb_tab;
    @Bind(R.id.ti_explore)
    TabItem ti_explore;//探索
    @Bind(R.id.ti_create)
    TabItem ti_create;//创造
    @Bind(R.id.ti_store)
    TabItem ti_store;//精选
    @Bind(R.id.ti_companion)
    TabItem ti_companion;//陪伴

    private PutaoExploreFragment mPutaoExploreFragment;
    private PutaoCreatedFragment mPutaoCreatedFragment;
    private PutaoStoreFragment mPutaoStoreFragment;
    private PutaoCompanionFragment mPutaoCompanionFragment;

    private SparseArray<Fragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addFragments();
        addListener();
        vp_content.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
        vp_content.setOffscreenPageLimit(1);
        vp_content.setPageTransformer(true, null);
    }

    /**
     * 添加Fragment
     */
    private void addFragments() {
        mFragments = new SparseArray<>();
        mFragments.put(0, Fragment.instantiate(mContext, PutaoExploreFragment.class.getName()));
        mFragments.put(1, Fragment.instantiate(mContext, PutaoCreatedFragment.class.getName()));
        mFragments.put(2, Fragment.instantiate(mContext, PutaoStoreFragment.class.getName()));
        mFragments.put(3, Fragment.instantiate(mContext, PutaoCompanionFragment.class.getName()));
    }

    private void addListener() {
        tb_tab.setOnTabItemSelectedListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onTabItemSelected(TabItem item, int position) {
        switch (item.getId()) {
            case R.id.ti_explore:

                break;
            case R.id.ti_create:

                break;
            case R.id.ti_store:

                break;
            case R.id.ti_companion:

                break;
        }
        vp_content.setCurrentItem(position);
    }

    /**
     * 返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            stopService(new Intent(GlobalApplication.ACTION_PUSH_SERVICE));
            return exit();
        }
        return super.onKeyDown(keyCode, event);
    }
}
