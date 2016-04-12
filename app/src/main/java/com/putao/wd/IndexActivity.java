package com.putao.wd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.db.entity.CompanionDB;
import com.putao.wd.home.MeFragment;
import com.putao.wd.home.PutaoCompanionFragment;
import com.putao.wd.home.PutaoDiscoveryFragment;
import com.putao.wd.home.PutaoStoreFragment;
import com.putao.wd.pt_store.pay.PaySuccessActivity;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.view.select.TabBar;
import com.sunnybear.library.view.select.TabItem;
import com.sunnybear.library.view.viewpager.UnScrollableViewPager;
import com.umeng.update.UmengUpdateAgent;

import java.util.HashMap;

import butterknife.Bind;

/**
 * 新首页
 * Created by guchenkai on 2016/1/13.
 */
public class IndexActivity extends BasicFragmentActivity<GlobalApplication> {
    public static boolean isNotRefreshUserInfo = false;
    public final static String PAY_ALL = "pay_all";
    @Bind(R.id.vp_content)
    UnScrollableViewPager vp_content;
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

    private SparseArray<Fragment> mFragments;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        //透明状态栏
/*        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
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
        vp_content.setCurrentItem(GlobalApplication.isBindDevice ? 0 : 1);
        //红点显示
        if (!TextUtils.isEmpty(mDiskFileCacheHelper.getAsString(RedDotReceiver.ME_TABBAR + AccountHelper.getCurrentUid()))) {
            ti_index_companion.show(-1);
        }
    }


    /**
     * 添加Fragment
     */
    private void addFragments() {
        mFragments = new SparseArray<>();
        mFragments.put(0, Fragment.instantiate(mContext, PutaoCompanionFragment.class.getName()));
        mFragments.put(1, Fragment.instantiate(mContext, PutaoDiscoveryFragment.class.getName()));
        mFragments.put(2, Fragment.instantiate(mContext, PutaoStoreFragment.class.getName()));
        mFragments.put(3, Fragment.instantiate(mContext, MeFragment.class.getName()));
    }

    private void addListener() {
        tb_index_tab.setOnTabItemSelectedListener(new TabBar.OnTabItemSelectedListener() {
            @Override
            public void onTabItemSelected(TabItem item, int position) {
                vp_content.setCurrentItem(position, false);
                if (3 == position) ti_index_companion.hide();
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

    @Subcriber(tag = PaySuccessActivity.PAY_FINISH)
    private void setPay(String pay) {
        tb_index_tab.setTabItemSelected(R.id.ti_index_store);
        ActivityManager.getInstance().popOtherActivity(IndexActivity.class);
    }

    private void hideRedDot() {
        ti_index_companion.hide();
        mDiskFileCacheHelper.remove(RedDotReceiver.ME_TABBAR + AccountHelper.getCurrentUid());
    }


    //-------------------------------以下是红点信息缓存-------------------------//
    @Subcriber(tag = RedDotReceiver.COMPANION_TABBAR)
    private void setCompanionDot(JSONArray accompanyNumber) {
        ti_index_companion.show(-1);
        CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
        for (Object object : accompanyNumber) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            dataBaseManager.insertFixDownload(jsonObject.getString(RedDotReceiver.SERVICE_ID), jsonObject.getString(RedDotReceiver.ID));
        }
//        mDiskFileCacheHelper.put(RedDotReceiver.ME_TABBAR + AccountHelper.getCurrentUid(), me_tabbar);
    }

    @Subcriber(tag = RedDotReceiver.ME_TABBAR)
    private void setMeDot(String me_tabbar) {
        ti_index_companion.show(-1);
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

/*    @Subcriber(tag = RedDotReceiver.APPPRODUCT_ID)
    private void setCompanionDot(String appproduct_id) {
        HashMap<String, String> redDotMap = new HashMap<>();
        redDotMap.put(appproduct_id, appproduct_id);
        mDiskFileCacheHelper.put(RedDotReceiver.APPPRODUCT_ID + AccountHelper.getCurrentUid(), redDotMap);
    }*/
}