package com.putao.wd.home;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.RedDotReceiver;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.pt_companion.DiaryActivity;
import com.putao.wd.pt_companion.manage.ManageActivity;
import com.putao.wd.explore.SmartActivity;
import com.putao.wd.home.adapter.ProductsAdapter;
import com.putao.wd.model.DiaryApp;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * 陪伴
 * Created by guchenkai on 2016/1/13.
 */
public class PutaoCompanionFragment extends BasicFragment implements View.OnClickListener, OnItemClickListener<DiaryApp> {
    @Bind(R.id.iv_title_bar_right1)
    ImageView iv_title_bar_right1;
    @Bind(R.id.iv_title_bar_right2)
    ImageView iv_title_bar_right2;
    @Bind(R.id.cv_empty)
    CardView cv_empty;
    @Bind(R.id.cv_no_empty)
    CardView cv_no_empty;
    @Bind(R.id.btn_explore_empty)
    Button btn_explore_empty;
    @Bind(R.id.rv_products)
    BasicRecyclerView rv_products;
    @Bind(R.id.iv_smart)
    ImageView iv_smart;
    @Bind(R.id.rl_background)
    RelativeLayout rl_background;
    @Bind(R.id.tv_background)
    ImageView tv_background;

    ProductsAdapter mProductsAdapter;
    private List<DiaryApp> mDiaryApps;
    //    private RelativeLayout.LayoutParams mRight2LayoutParams;
    private RelativeLayout.LayoutParams mSmartLayoutParams;
    private boolean isLoginChange = false;
    // 标志位，标志已经初始化完成。
    public static boolean isPrepared;
    private HashMap<String, String> mRedDotMap;
    private static final String PRO_601 = "601";
    private static final String PRO_602 = "602";
    private static final String PRO_603 = "603";
    private static final String PRO_7000 = "7000";
    private static final String PRO_8000 = "8000";
    private static final String PRO_8001 = "8001";
    private static final String PRO_8002 = "8002";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_companion;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        Logger.d("PutaoCompanionFragment启动");
        mProductsAdapter = new ProductsAdapter(mActivity, null);
        rv_products.setAdapter(mProductsAdapter);
        rv_products.setOnItemClickListener(this);
        isPrepared = true;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isPrepared = false;
        if (AccountHelper.isLogin()) {
            checkDevices();
        }
    }

    private void addClickListener() {
//        iv_user_icon.setOnClickListener(this);
        iv_title_bar_right1.setOnClickListener(this);
        iv_title_bar_right2.setOnClickListener(this);
        btn_explore_empty.setOnClickListener(this);
        tv_background.setOnClickListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 是否已经添加设备
     */
    private void checkDevices() {
        networkRequest(ExploreApi.getDiaryApp(),
                new SimpleFastJsonCallback<ArrayList<DiaryApp>>(DiaryApp.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<DiaryApp> result) {
                        if (null != result && result.size() > 0) {
                            cv_empty.setVisibility(View.GONE);
                            cv_no_empty.setVisibility(View.VISIBLE);
                            btn_explore_empty.setVisibility(View.GONE);
                            mProductsAdapter.replaceAll(setRedDot(result));
//                            mRight2LayoutParams.addRule(RelativeLayout.LEFT_OF, R.id.iv_title_bar_right1);
//                            mRight2LayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
//                            mRight2LayoutParams.setMargins(0, 0, DensityUtil.dp2px(mActivity, 20), 0);
//                            iv_title_bar_right2.setLayoutParams(mRight2LayoutParams);
                            RelativeLayout.LayoutParams smartLayoutParams = mSmartLayoutParams;
                            mSmartLayoutParams.addRule(RelativeLayout.ABOVE, R.id.cv_no_empty);
                            mSmartLayoutParams.setMargins(0, 0, 0, DensityUtil.dp2px(mActivity, 110));
                            iv_smart.setLayoutParams(smartLayoutParams);
                            mDiaryApps = result;
                        }
                        loading.dismiss();
                    }
                });
    }

    /**
     * 设置缓存红点
     */
    private ArrayList<DiaryApp> setRedDot(ArrayList<DiaryApp> result) {
        //红点显示
        mRedDotMap = (HashMap<String, String>) mDiskFileCacheHelper.getAsSerializable(RedDotReceiver.APPPRODUCT_ID + AccountHelper.getCurrentUid());
        if (null != mRedDotMap) {
            if (!TextUtils.isEmpty(mRedDotMap.get(PRO_601)))
                result.get(0).setShowRedDot(true);
            if (!TextUtils.isEmpty(mRedDotMap.get(PRO_602)))
                result.get(1).setShowRedDot(true);
            if (!TextUtils.isEmpty(mRedDotMap.get(PRO_603)))
                result.get(2).setShowRedDot(true);
            if (!TextUtils.isEmpty(mRedDotMap.get(PRO_7000)))
                result.get(3).setShowRedDot(true);
            if (!TextUtils.isEmpty(mRedDotMap.get(PRO_8000)))
                result.get(4).setShowRedDot(true);
            if (!TextUtils.isEmpty(mRedDotMap.get(PRO_8001)))
                result.get(5).setShowRedDot(true);
            if (!TextUtils.isEmpty(mRedDotMap.get(PRO_8002)))
                result.get(6).setShowRedDot(true);
        } else mRedDotMap = new HashMap<>();
        return result;
    }

    /**
     * 没有登录或没有绑定设备时的界面
     */
    private void empty() {
        cv_empty.setVisibility(View.VISIBLE);
        btn_explore_empty.setVisibility(View.VISIBLE);
        cv_no_empty.setVisibility(View.GONE);
        mSmartLayoutParams.addRule(RelativeLayout.ABOVE, R.id.btn_explore_empty);
        mSmartLayoutParams.setMargins(0, 0, 0, DensityUtil.dp2px(mActivity, 45));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_background) {
            startActivity(SmartActivity.class);
            getActivity().overridePendingTransition(R.anim.in_from_down, R.anim.companion_in_from_down);
            return;
        }
        if (!AccountHelper.isLogin()) {
            Bundle bundle = new Bundle();
            toLoginActivity(v, bundle);
            startActivity(LoginActivity.class, bundle);
            return;
        }
        switch (v.getId()) {/*
            case R.id.iv_user_icon:
                startActivity(CompleteActivity.class);
                break;*/
            case R.id.iv_title_bar_right1:
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_control);
                startActivity(ManageActivity.class);
                break;
            case R.id.iv_title_bar_right2:
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_scan);
                startActivity(CaptureActivity.class);
                break;
            case R.id.btn_explore_empty:
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_scan);
                startActivity(CaptureActivity.class);
                break;
        }
    }

    private void toLoginActivity(View v, Bundle bundle) {
        switch (v.getId()) {
       /*     case R.id.iv_user_icon:
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CompleteActivity.class);
                break;*/
            case R.id.iv_title_bar_right1:
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, ManageActivity.class);
                break;
            case R.id.iv_title_bar_right2:
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CaptureActivity.class);
                break;
            case R.id.btn_explore_empty:
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CaptureActivity.class);
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mSmartLayoutParams = (RelativeLayout.LayoutParams) iv_smart.getLayoutParams();
        addClickListener();
        if (isLoginChange && !AccountHelper.isLogin()) empty();
        if (!IndexActivity.isNotRefreshUserInfo && !isLoginChange && AccountHelper.isLogin() && !isPrepared) {
            checkDevices();
        }
        isLoginChange = AccountHelper.isLogin();
    }

    @Override
    public void onItemClick(DiaryApp diaryApp, int position) {
        switch (position) {
            case 0:
                mRedDotMap.remove(PRO_601);
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "淘淘向右走");
                break;
            case 1:
                mRedDotMap.remove(PRO_602);
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "班得瑞的奇幻花园");
                break;
            case 2:
                mRedDotMap.remove(PRO_603);
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "旋转吧魔方");
                break;
            case 3:
                mRedDotMap.remove(PRO_7000);
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "麦斯丝");
                break;
            case 4:
                mRedDotMap.remove(PRO_8000);
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "Hello编程");
                break;
            case 5:
                mRedDotMap.remove(PRO_8001);
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "哈尼海洋");
                break;
            case 6:
                mRedDotMap.remove(PRO_8002);
                YouMengHelper.onEvent(mActivity, YouMengHelper.AccompanyHome_app_game, "涂涂世界");
                break;
        }
        mProductsAdapter.getItem(position).setShowRedDot(false);
        mProductsAdapter.notifyItemChanged(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DiaryActivity.DIARY_APP, diaryApp);
        bundle.putSerializable("position", position);
        mDiskFileCacheHelper.put(RedDotReceiver.APPPRODUCT_ID + AccountHelper.getCurrentUid(), mRedDotMap);
        startActivity(DiaryActivity.class, bundle);
    }

    @Subcriber(tag = RedDotReceiver.APPPRODUCT_ID)
    private void setDot(String appproduct_id) {
        int position = -1;
        switch (appproduct_id) {
            case PRO_601:
                position = 0;
                break;
            case PRO_602:
                position = 1;
                break;
            case PRO_603:
                position = 2;
                break;
            case PRO_7000:
                position = 3;
                break;
            case PRO_8000:
                position = 4;
                break;
            case PRO_8001:
                position = 5;
                break;
            case PRO_8002:
                position = 6;
                break;
        }
        if (position != -1) {
            mProductsAdapter.getItem(position).setShowRedDot(true);
            mProductsAdapter.notifyItemChanged(position);
        }
    }
}
