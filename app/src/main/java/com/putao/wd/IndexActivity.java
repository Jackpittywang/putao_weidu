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
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.UserApi;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.home.MeFragment;
import com.putao.wd.home.PutaoCompanionFragment;
import com.putao.wd.home.PutaoDiscoveryFragment;
import com.putao.wd.home.PutaoStoreFragment;
import com.putao.wd.pt_store.pay.PaySuccessActivity;
import com.putao.wd.util.RedDotUtils;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.PreferenceUtils;
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
    @Bind(R.id.ti_index_companion)
    TabItem ti_index_companion;
    @Bind(R.id.ti_index_discovery)
    TabItem ti_index_discovery;
    @Bind(R.id.ti_index_store)
    TabItem ti_index_store;
    @Bind(R.id.ti_index_me)
    TabItem ti_index_me;

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
        Boolean is_device_bind = PreferenceUtils.getValue(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), false);
        tb_index_tab.setTabItemSelected(is_device_bind && AccountHelper.isLogin() ? R.id.ti_index_companion : R.id.ti_index_discovery);
        vp_content.setCurrentItem(is_device_bind && AccountHelper.isLogin() ? 0 : 1);
        if (AccountHelper.isLogin()) {
            //红点显示
            if (RedDotUtils.showMessageCenterDot()) {
                ti_index_me.show(-1);
            }
            //红点显示
            if (PreferenceUtils.getValue(RedDotReceiver.COMPANION_TABBAR + AccountHelper.getCurrentUid(), false)) {
                ti_index_companion.hide();
            }
            checkInquiryBind(AccountHelper.getCurrentUid());
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
                switch (position) {
                    case 0:
                        YouMengHelper.onEvent(mContext, YouMengHelper.Tabbar_pressed, "陪伴");
                        break;
                    case 1:
                        YouMengHelper.onEvent(mContext, YouMengHelper.Tabbar_pressed, "发现");
                        break;
                    case 2:
                        YouMengHelper.onEvent(mContext, YouMengHelper.Tabbar_pressed, "精选");
                        break;
                    case 3:
                        YouMengHelper.onEvent(mContext, YouMengHelper.Tabbar_pressed, "我");
                        break;
                }
                vp_content.setCurrentItem(position, false);
//                if (3 == position) hideMeRedDot();
//                if (0 == position) hideCompanionRedDot();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    protected void onStart() {
        super.onStart();
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

/*    private void hideMeRedDot() {
        ti_index_me.hide();
    }

    private void hideCompanionRedDot() {
        ti_index_companion.hide();
        PreferenceUtils.remove(RedDotReceiver.COMPANION_TABBAR + AccountHelper.getCurrentUid());
    }*/

    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_COMPANION)
    private void refresh_companion(String str) {
        checkInquiryBind(AccountHelper.getCurrentUid());
    }


    //-------------------------------以下是红点信息缓存-------------------------//
    @Subcriber(tag = RedDotReceiver.COMPANION_TABBAR)
    private void setCompanionDot(JSONArray accompanyNumber) {
        ti_index_companion.show(-1);
        PreferenceUtils.save(RedDotReceiver.COMPANION_TABBAR + AccountHelper.getCurrentUid(), true);
        CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
        for (Object object : accompanyNumber) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            dataBaseManager.insertFixDownload(jsonObject.getString(RedDotReceiver.SERVICE_ID), jsonObject.getString(RedDotReceiver.ID));
        }
    }

    @Subcriber(tag = RedDotReceiver.MESSAGECENTER)
    private void setDot(String messagecenter) {
        ti_index_me.show(-1);
        RedDotUtils.saveMessageCenterDot(messagecenter);
    }


    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_COMPANION_TAB)
    private void cancleCompanionDot(String tab) {
        ti_index_companion.hide();
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_ME_TAB)
    private void cancleMeDot(String tab) {
        ti_index_me.hide();
    }

    /**
     * 是不是绑定过设备
     *
     * @param currentUid uid
     */
    private void checkInquiryBind(String currentUid) {
        networkRequest(UserApi.checkInquiryBind(currentUid),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        Boolean is_relation = JSONObject.parseObject(result).getBoolean("is_relation");
                        PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), is_relation);
                    }

                    @Override
                    public void onFinish(String url, boolean isSuccess, String msg) {
                        super.onFinish(url, isSuccess, msg);
                    }
                }, false);
    }

}