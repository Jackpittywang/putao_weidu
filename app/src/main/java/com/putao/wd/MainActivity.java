package com.putao.wd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.KeyEvent;

import com.putao.wd.db.CityDBManager;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.db.ProvinceDBManager;
import com.putao.wd.home.MeFragment;
import com.putao.wd.home.PutaoExploreFragment;
import com.putao.wd.home.PutaoStartCircleFragment;
import com.putao.wd.home.PutaoStoreFragment;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.FragmentSwitchAdapter;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.select.TabBar;
import com.sunnybear.library.view.select.TabItem;
import com.sunnybear.library.view.viewpager.UnScrollableViewPager;

import butterknife.Bind;

/**
 * 主页
 * Created by guchenkai on 2015/11/26.
 */
public class MainActivity extends BasicFragmentActivity<GlobalApplication> implements TabBar.TabItemSelectedListener {
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
        vp_content.setPageTransformer(false, null);

        ti_start_circle.show(4);
        ti_explore.show(4);
        ti_store.show(4);
        ti_me.show(4);
        addListener();

        Logger.d(mApp.getDataBaseManager(ProvinceDBManager.class).getQueryBuilder().count() + "");
        Logger.d(mApp.getDataBaseManager(CityDBManager.class).getQueryBuilder().count() + "");
        Logger.d(mApp.getDataBaseManager(DistrictDBManager.class).getQueryBuilder().count() + "");
//        Logger.d(CityDBManager.getInstance(mApp).getCityNameByProvinceId("330000").toString());
//        Logger.d(DistrictDBManager.getInstance(mApp).getDistrictNameByCityId("330100").toString());

//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_container, mFragmentArray.get(R.id.ti_start_circle)).commit();
    }

    private void addFragment() {
        mFragmentArray = new SparseArray<>();
        mFragmentArray.put(0, Fragment.instantiate(mContext, PutaoStartCircleFragment.class.getName()));
        mFragmentArray.put(1, Fragment.instantiate(mContext, PutaoExploreFragment.class.getName()));
        mFragmentArray.put(2, Fragment.instantiate(mContext, PutaoStoreFragment.class.getName()));
        mFragmentArray.put(3, Fragment.instantiate(mContext, MeFragment.class.getName()));
    }

//    /**
//     * 切换当前页
//     *
//     * @param resId
//     */
//    private void setCurrentItem(int resId) {
//        Fragment fragment = mFragmentArray.get(resId);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, fragment)
//                .addToBackStack(String.valueOf(resId)).commit();
//    }

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
//                startService(new Intent(this, CityService.class));
                break;
        }
        Logger.d("点击了第" + position + "个");
        vp_content.setCurrentItem(position);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return exit();
        return super.onKeyDown(keyCode, event);
    }
}