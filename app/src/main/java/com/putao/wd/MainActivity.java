package com.putao.wd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.KeyEvent;

import com.putao.wd.home.MeFragment;
import com.putao.wd.home.PutaoExploreFragment;
import com.putao.wd.home.PutaoStartCircleFragment;
import com.putao.wd.home.PutaoStoreFragment;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.view.select.TabBar;
import com.sunnybear.library.view.select.TabItem;

import butterknife.Bind;

/**
 * 主页
 * Created by guchenkai on 2015/11/26.
 */
public class MainActivity extends BasicFragmentActivity<GlobalApplication> implements TabBar.TabItemSelectedListener {
    public static final String TAG = MainActivity.class.getSimpleName();
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

    private PutaoStartCircleFragment mPutaoStartCircleFragment;//葡星圈
    private PutaoExploreFragment mPutaoExploreFragment;//探索号
    private PutaoStoreFragment mPutaoStoreFragment;//葡商城
    private MeFragment mMeFragment;//我

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addHome();
        ti_start_circle.show(4);//显示指示数字
        ti_explore.show(4);//显示指示数字
        ti_store.show(4);//显示指示数字
        ti_me.show(4);//显示指示数字
        addListener();
    }

    /**
     * 设置主页
     */
    private void addHome() {
        mPutaoStartCircleFragment = (PutaoStartCircleFragment) Fragment.instantiate(mContext, PutaoStartCircleFragment.class.getName());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mPutaoStartCircleFragment).commit();
    }

    /**
     * 隐藏fragment
     */
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mPutaoStartCircleFragment != null)
            fragmentTransaction.hide(mPutaoStartCircleFragment);
        if (mPutaoExploreFragment != null)
            fragmentTransaction.hide(mPutaoExploreFragment);
        if (mPutaoStoreFragment != null)
            fragmentTransaction.hide(mPutaoStoreFragment);
        if (mMeFragment != null)
            fragmentTransaction.hide(mMeFragment);
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
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(mFragmentTransaction);
        switch (item.getId()) {
            case R.id.ti_start_circle://葡星圈
                ti_start_circle.hide();//关闭指示数字
                if (mPutaoStartCircleFragment == null) {
                    mPutaoStartCircleFragment = (PutaoStartCircleFragment) Fragment.instantiate(mContext, PutaoStartCircleFragment.class.getName());
                    mFragmentTransaction.add(R.id.fragment_container, mPutaoStartCircleFragment);
                } else {
                    mFragmentTransaction.show(mPutaoStartCircleFragment);
                }
                break;
            case R.id.ti_explore://探索号
                ti_explore.hide();//关闭指示数字
                if (mPutaoExploreFragment == null) {
                    mPutaoExploreFragment = (PutaoExploreFragment) Fragment.instantiate(mContext, PutaoExploreFragment.class.getName());
                    mFragmentTransaction.add(R.id.fragment_container, mPutaoExploreFragment);
                } else {
                    mFragmentTransaction.show(mPutaoExploreFragment);
                }
                break;
            case R.id.ti_store://葡商城
                ti_store.hide();//关闭指示数字
                if (mPutaoStoreFragment == null) {
                    mPutaoStoreFragment = (PutaoStoreFragment) Fragment.instantiate(mContext, PutaoStoreFragment.class.getName());
                    mFragmentTransaction.add(R.id.fragment_container, mPutaoStoreFragment);
                } else {
                    mFragmentTransaction.show(mPutaoStoreFragment);
                }
                break;
            case R.id.ti_me://我
                ti_me.hide();//关闭指示数字
                if (mMeFragment == null) {
                    mMeFragment = (MeFragment) Fragment.instantiate(mContext, MeFragment.class.getName());
                    mFragmentTransaction.add(R.id.fragment_container, mMeFragment);
                } else {
                    mFragmentTransaction.show(mMeFragment);
                }

                //startService(EmojiService.class);
                //startActivity(TestActivity.class);
                break;
        }
        mFragmentTransaction.commit();
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
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return exit();
        return super.onKeyDown(keyCode, event);
    }
}