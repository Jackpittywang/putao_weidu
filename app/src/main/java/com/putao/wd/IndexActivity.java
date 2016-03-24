package com.putao.wd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.putao.wd.account.AccountHelper;
import com.putao.wd.created.CreateBasicDetailActivity;
import com.putao.wd.explore.ExploreMoreDetailActivity;
import com.putao.wd.home.MeFragment;
import com.putao.wd.home.PutaoCompanionFragment;
import com.putao.wd.home.PutaoCreatedFragment;
import com.putao.wd.home.PutaoCreatedSecondFragment;
import com.putao.wd.home.PutaoExploreFragment;
import com.putao.wd.home.PutaoStoreFragment;
import com.putao.wd.jpush.JPushReceiver;
import com.putao.wd.me.order.OrderDetailActivity;
import com.putao.wd.me.service.ServiceListActivity;
import com.putao.wd.store.pay.PaySuccessActivity;
import com.putao.wd.store.product.ProductDetailActivity;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.select.TabBar;
import com.sunnybear.library.view.select.TabItem;
import com.sunnybear.library.view.viewpager.UnScrollableViewPager;
import com.umeng.update.UmengUpdateAgent;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;

/**
 * 新首页
 * Created by guchenkai on 2016/1/13.
 */
public class IndexActivity extends BasicFragmentActivity {
    public static boolean isNotRefreshUserInfo = false;
    public final static String PAY_ALL = "pay_all";
    @Bind(R.id.ll_loading)
    LinearLayout ll_loading;
    @Bind(R.id.v_shelter)
    View v_shelter;
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
        //透明状态栏
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        UmengUpdateAgent.setDefault();
        UmengUpdateAgent.update(IndexActivity.this);
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
        //红点显示
        if (!TextUtils.isEmpty(mDiskFileCacheHelper.getAsString(RedDotReceiver.ME_TABBAR + AccountHelper.getCurrentUid()))) {
            ti_index_companion.show(-1);
            ti_companion.show(-1);
        }
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
                } else if (position == 3) {
                    hideRedDot();
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
                            hideRedDot();
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
        PutaoExploreFragment.BACKGROUND_CAN_CHANGGE = true;
    }

    @Subcriber(tag = PaySuccessActivity.PAY_FINISH)
    private void setPay(String pay) {
        tb_tab.setTabItemSelected(R.id.ti_create);
        ActivityManager.getInstance().popOtherActivity(IndexActivity.class);
    }

    public void hideLoading() {
        v_shelter.setVisibility(View.VISIBLE);
        ll_loading.setVisibility(View.GONE);
    }

    public void showLoading() {
        v_shelter.setVisibility(View.GONE);
        ll_loading.setVisibility(View.VISIBLE);
    }

    private void hideRedDot() {
        ti_index_companion.hide();
        ti_companion.hide();
        mDiskFileCacheHelper.remove(RedDotReceiver.ME_TABBAR + AccountHelper.getCurrentUid());
    }


    //-------------------------------以下是红点信息缓存-------------------------//
    @Subcriber(tag = RedDotReceiver.ME_TABBAR)
    private void setMeDot(String me_tabbar) {
        ti_index_companion.show(-1);
        ti_companion.show(-1);
        mDiskFileCacheHelper.put(RedDotReceiver.ME_TABBAR + AccountHelper.getCurrentUid(), me_tabbar);
    }

    @Subcriber(tag = RedDotReceiver.ME_MESSAGECENTER)
    private void setMeMessageCenterDot(String me_messagecenter) {
        mDiskFileCacheHelper.put(RedDotReceiver.ME_MESSAGECENTER + AccountHelper.getCurrentUid(), me_messagecenter);
    }


    @Subcriber(tag = RedDotReceiver.MESSAGECENTER)
    private void setMessageCenterDot(String messagecenter) {
        HashMap<String, String> redDotMap = new HashMap<>();
        redDotMap.put(messagecenter, messagecenter);
        mDiskFileCacheHelper.put(RedDotReceiver.MESSAGECENTER + AccountHelper.getCurrentUid(), redDotMap);
    }

    @Subcriber(tag = RedDotReceiver.APPPRODUCT_ID)
    private void setCompanionDot(String appproduct_id) {
        HashMap<String, String> redDotMap = new HashMap<>();
        redDotMap.put(appproduct_id, appproduct_id);
        mDiskFileCacheHelper.put(RedDotReceiver.APPPRODUCT_ID + AccountHelper.getCurrentUid(), redDotMap);
    }
}
