package com.putao.wd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.putao.wd.home.MeFragment;
import com.putao.wd.home.PutaoExploreFragment;
import com.putao.wd.home.PutaoStartCircleFragment;
import com.putao.wd.home.PutaoStoreFragment;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.UnScrollableViewPager;
import com.sunnybear.library.view.tab.TabBar;
import com.sunnybear.library.view.tab.TabItem;

import butterknife.Bind;

public class MainActivity extends BasicFragmentActivity implements TabBar.TabItemSelectedListener {
    @Bind(R.id.tb_tab)
    TabBar tb_tab;
    @Bind(R.id.ti_start_circle)
    TabItem ti_start_circle;
    @Bind(R.id.ti_explore)
    TabItem ti_explore;
    @Bind(R.id.ti_store)
    TabItem ti_store;
    @Bind(R.id.ti_me)
    TabItem ti_me;
    @Bind(R.id.vp_content)
    UnScrollableViewPager vp_content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        vp_content.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = Fragment.instantiate(mContext, PutaoStartCircleFragment.class.getName());
                        break;
                    case 1:
                        fragment = Fragment.instantiate(mContext, PutaoExploreFragment.class.getName());
                        break;
                    case 2:
                        fragment = Fragment.instantiate(mContext, PutaoStoreFragment.class.getName());
                        break;
                    case 3:
                        fragment = Fragment.instantiate(mContext, MeFragment.class.getName());
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return tb_tab.getPosition();
            }
        });
//        ti_start_circle.show();
//        ti_explore.show();
//        ti_store.show();
//        ti_me.show();
        addListener();
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        tb_tab.setTabItemSelectedListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * TabBar点击事件
     *
     * @param item item
     */
    @Override
    public void onTabItemSelected(TabItem item, int position) {
        switch (item.getId()) {
            case R.id.ti_start_circle://葡星圈

                break;
            case R.id.ti_explore://探索号

                break;
            case R.id.ti_store://葡商城

                break;
            case R.id.ti_me://我

                break;
        }
        Logger.d("点击了第" + position + "个");
        vp_content.setCurrentItem(position);
    }
}