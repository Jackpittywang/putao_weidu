package com.putao.wd;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;

import com.putao.wd.home.MeFragment;
import com.putao.wd.home.PutaoCompanionFragment;
import com.putao.wd.home.PutaoCreatedFragment;
import com.putao.wd.home.PutaoCreatedSecondFragment;
import com.putao.wd.home.PutaoExploreFragment;
import com.putao.wd.home.PutaoStoreFragment;
import com.putao.wd.store.pay.PaySuccessActivity;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.select.TabBar;
import com.sunnybear.library.view.select.TabItem;
import com.sunnybear.library.view.viewpager.UnScrollableViewPager;

import butterknife.Bind;

/**
 * 新首页
 * Created by guchenkai on 2016/1/13.
 */
public class IndexActivity extends BasicFragmentActivity {
    public static boolean isNotRefreshUserInfo = false;
    public final static String PAY_ALL = "pay_all";

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
    @Bind(R.id.iv_blur)
    ImageDraweeView iv_blur;
    @Bind(R.id.v_line_horizontal)
    View v_line_horizontal;
    @Bind(R.id.tb_index_tab)
    TabBar tb_index_tab;
    @Bind(R.id.ti_index_create)
    TabItem ti_index_create;
    @Bind(R.id.ti_index_store)
    TabItem ti_index_store;
    @Bind(R.id.ti_index_companion)
    TabItem ti_index_companion;
    @Bind(R.id.ti_index_explore)
    TabItem ti_index_explore;

    private PutaoExploreFragment mPutaoExploreFragment;
    private PutaoCreatedSecondFragment mPutaoCreatedFragment;
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
        vp_content.setOffscreenPageLimit(4);
    }

    /**
     * 添加Fragment
     */
    private void addFragments() {
        mFragments = new SparseArray<>();
        mFragments.put(0, Fragment.instantiate(mContext, PutaoExploreFragment.class.getName()));
        mFragments.put(1, Fragment.instantiate(mContext, PutaoCreatedFragment.class.getName()));
        mFragments.put(2, Fragment.instantiate(mContext, PutaoCompanionFragment.class.getName()));
        mFragments.put(3, Fragment.instantiate(mContext, MeFragment.class.getName()));
    }

    private void addListener() {
        tb_tab.setOnTabItemSelectedListener(new TabBar.OnTabItemSelectedListener() {
            @Override
            public void onTabItemSelected(TabItem item, int position) {
                if (position == 0) {
                    tb_tab.setVisibility(View.GONE);
                    tb_index_tab.setVisibility(View.VISIBLE);
                    v_line_horizontal.setVisibility(View.INVISIBLE);
                    tb_index_tab.setTabItemSelected(R.id.ti_index_explore);
                    return;
                }
                vp_content.setCurrentItem(position, false);
            }
        });
        tb_index_tab.setOnTabItemSelectedListener(new TabBar.OnTabItemSelectedListener() {
            @Override
            public void onTabItemSelected(TabItem item, int position) {
                if (position != 0) {
                    tb_tab.setVisibility(View.VISIBLE);
                    tb_index_tab.setVisibility(View.GONE);
                    v_line_horizontal.setVisibility(View.VISIBLE);
                    switch (position) {
                        case 1:
                            tb_tab.setTabItemSelected(R.id.ti_create);
                            return;
                        case 2:
                            tb_tab.setTabItemSelected(R.id.ti_store);
                            return;
                        case 3:
                            tb_tab.setTabItemSelected(R.id.ti_companion);
                            return;
                    }
                }
                vp_content.setCurrentItem(position, false);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
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

    @Subcriber(tag = PutaoExploreFragment.BLUR)
    private void setBlur(Bitmap bitmap) {
        iv_blur.setDefaultImage(bitmap);
    }

    @Subcriber(tag = PaySuccessActivity.PAY_FINISH)
    private void setPay(String pay) {
        tb_tab.setTabItemSelected(R.id.ti_create);
        ActivityManager.getInstance().popOtherActivity(IndexActivity.class);
    }
}
