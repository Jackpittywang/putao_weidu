package com.putao.wd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.putao.wd.home.MeFragment;
import com.putao.wd.home.PutaoExploreFragment;
import com.putao.wd.home.PutaoStartCircleFragment;
import com.putao.wd.home.PutaoStoreFragment;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.tab.TabBar;
import com.sunnybear.library.view.tab.TabItem;

import butterknife.Bind;

/**
 * 主页
 * Created by guchenkai on 2015/11/26.
 */
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

    private SparseArray<Fragment> mFragmentArray;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addFragment();
        ti_start_circle.show(4);
        ti_explore.show(4);
        ti_store.show(4);
        ti_me.show(4);
        addListener();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mFragmentArray.get(R.id.ti_start_circle)).commit();
    }

    private void addFragment() {
        mFragmentArray = new SparseArray<>();
        mFragmentArray.put(R.id.ti_start_circle, Fragment.instantiate(mContext, PutaoStartCircleFragment.class.getName()));
        mFragmentArray.put(R.id.ti_explore, Fragment.instantiate(mContext, PutaoExploreFragment.class.getName()));
        mFragmentArray.put(R.id.ti_store, Fragment.instantiate(mContext, PutaoStoreFragment.class.getName()));
        mFragmentArray.put(R.id.ti_me, Fragment.instantiate(mContext, MeFragment.class.getName()));
    }

    /**
     * 切换当前页
     *
     * @param resId
     */
    private void setCurrentItem(int resId) {
        Fragment fragment = mFragmentArray.get(resId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
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
        int id = item.getId();
        switch (item.getId()) {
            case R.id.ti_start_circle://葡星圈
                ti_start_circle.hide();
                break;
            case R.id.ti_explore://探索号
                ti_explore.hide();
                break;
            case R.id.ti_store://葡商城
                ti_store.hide();
                break;
            case R.id.ti_me://我
                ti_me.hide();
                break;
        }
        Logger.d("点击了第" + position + "个");
        setCurrentItem(id);
    }
}