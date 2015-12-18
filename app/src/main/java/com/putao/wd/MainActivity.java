package com.putao.wd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.KeyEvent;

import com.putao.wd.account.AccountHelper;
import com.putao.wd.home.MeFragment;
import com.putao.wd.home.PutaoExploreFragment;
import com.putao.wd.home.PutaoStartCircleFragment;
import com.putao.wd.home.PutaoStoreFragment;
import com.putao.wd.me.setting.SettingActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.eventbus.Subcriber;
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

    private PutaoStartCircleFragment mPutaoStartCircleFragment;//葡星圈
    private PutaoExploreFragment mPutaoExploreFragment;//探索号
    private PutaoStoreFragment mPutaoStoreFragment;//葡商城
    private MeFragment mMeFragment;//我

    private SparseArray<Class<? extends Fragment>> mFragments;
    private int currentItemId;//当前的itemId

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addHome();
        mFragments = new SparseArray<>();
        addFragment();
//        ti_start_circle.show(4);//显示指示数字
//        ti_explore.show(4);//显示指示数字
//        ti_store.show(4);//显示指示数字
//        ti_me.show(4);//显示指示数字
        addListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 设置主页
     */
    private void addHome() {
        mPutaoStartCircleFragment = (PutaoStartCircleFragment) Fragment.instantiate(mContext, PutaoStartCircleFragment.class.getName());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mPutaoStartCircleFragment).commit();
    }

    private void addFragment() {
        mFragments.put(R.id.ti_start_circle, PutaoStartCircleFragment.class);
        mFragments.put(R.id.ti_explore, PutaoExploreFragment.class);
        mFragments.put(R.id.ti_store, PutaoStoreFragment.class);
        mFragments.put(R.id.ti_me, MeFragment.class);
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
        currentItemId = item.getId();
        switch (currentItemId) {
            case R.id.ti_start_circle://葡星圈
                ti_start_circle.hide();//关闭指示数字
                setCurrentItem(currentItemId);
                break;
            case R.id.ti_explore://探索号
                ti_explore.hide();//关闭指示数字
                if (!AccountHelper.isLogin())
                    startActivity(LoginActivity.class);
                else
                    setCurrentItem(currentItemId);
                break;
            case R.id.ti_store://葡商城
                ti_store.hide();//关闭指示数字
                if (!AccountHelper.isLogin())
                    startActivity(LoginActivity.class);
                else
                    setCurrentItem(currentItemId);
                break;
            case R.id.ti_me://我
                ti_me.hide();//关闭指示数字
                if (!AccountHelper.isLogin())
                    startActivity(LoginActivity.class);
                else
                    setCurrentItem(currentItemId);
                break;
        }
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
     * 选择当前Item
     *
     * @param resId
     */
    private void setCurrentItem(int resId) {
        Class<? extends Fragment> fragmentClass = mFragments.get(resId);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        if (PutaoStartCircleFragment.class.equals(fragmentClass))
            mPutaoStartCircleFragment = (PutaoStartCircleFragment) showFragment(fragmentTransaction, mPutaoStartCircleFragment, PutaoStartCircleFragment.class);
        else if (PutaoExploreFragment.class.equals(fragmentClass))
            mPutaoExploreFragment = (PutaoExploreFragment) showFragment(fragmentTransaction, mPutaoExploreFragment, PutaoExploreFragment.class);
        else if (PutaoStoreFragment.class.equals(fragmentClass))
            mPutaoStoreFragment = (PutaoStoreFragment) showFragment(fragmentTransaction, mPutaoStoreFragment, PutaoStoreFragment.class);
        else if (MeFragment.class.equals(fragmentClass))
            mMeFragment = (MeFragment) showFragment(fragmentTransaction, mMeFragment, MeFragment.class);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 显示fragment
     *
     * @param fragment 目标fragment
     */
    private Fragment showFragment(FragmentTransaction fragmentTransaction, Fragment fragment, Class<? extends Fragment> fragmentClass) {
        if (fragment == null) {
            fragment = Fragment.instantiate(mContext, fragmentClass.getName());
            fragmentTransaction.add(R.id.fragment_container, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        return fragment;
    }

    @Subcriber(tag = LoginActivity.EVENT_LOGIN)
    public void eventLogin(String tag) {
        setCurrentItem(currentItemId);
    }

    @Subcriber(tag = SettingActivity.EVENT_LOGOUT)
    public void eventLogout(String tag) {
        setCurrentItem(R.id.ti_start_circle);
        tb_tab.setTabItemSelected(R.id.ti_start_circle);
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