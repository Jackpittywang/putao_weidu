package com.putao.wd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.putao.wd.home.MeFragment;
import com.putao.wd.home.PutaoExploreFragment;
import com.putao.wd.home.PutaoStartCircleFragment;
import com.putao.wd.home.PutaoStoreFragment;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.FragmentSwitchAdapter;
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

    private SparseArray<Fragment> mFragmentArray;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addFragment();
        vp_content.setAdapter(new FragmentSwitchAdapter(getSupportFragmentManager(), mFragmentArray));
        vp_content.setOffscreenPageLimit(4);
//        ti_start_circle.show();
//        ti_explore.show();
//        ti_store.show();
//        ti_me.show();
        addListener();
    }

    private void addFragment() {
        mFragmentArray = new SparseArray<>();
        mFragmentArray.put(0, Fragment.instantiate(mContext, PutaoStartCircleFragment.class.getName()));
        mFragmentArray.put(1, Fragment.instantiate(mContext, PutaoExploreFragment.class.getName()));
        mFragmentArray.put(2, Fragment.instantiate(mContext, PutaoStoreFragment.class.getName()));
        mFragmentArray.put(3, Fragment.instantiate(mContext, MeFragment.class.getName()));
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