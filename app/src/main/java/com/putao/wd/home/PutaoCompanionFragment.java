package com.putao.wd.home;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.companion.DiaryActivity;
import com.putao.wd.companion.manage.ManageActivity;
import com.putao.wd.explore.SmartActivity;
import com.putao.wd.home.adapter.ProductsAdapter;
import com.putao.wd.model.DiaryApp;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;
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
                            mProductsAdapter.replaceAll(result);
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
        /*if (v.getId() == R.id.tv_background) {
            startActivity(SmartActivity.class);
            getActivity().overridePendingTransition(R.anim.in_from_down, R.anim.companion_in_from_down);
            return;
        }*/
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
                startActivity(ManageActivity.class);
                break;
            case R.id.iv_title_bar_right2:
                startActivity(CaptureActivity.class);
                break;
            case R.id.btn_explore_empty:
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
        Bundle bundle = new Bundle();
        bundle.putSerializable(DiaryActivity.DIARY_APP, diaryApp);
        startActivity(DiaryActivity.class, bundle);
    }
}
