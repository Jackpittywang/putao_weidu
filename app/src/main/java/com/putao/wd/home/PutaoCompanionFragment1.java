package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.pt_companion.DisPlayActivity;
import com.putao.wd.pt_companion.PlotActivity;
import com.putao.wd.pt_companion.manage.ManageActivity;
import com.putao.wd.home.adapter.ExploreAdapter;
import com.putao.wd.model.ExploreProduct;
import com.putao.wd.model.ExploreProductPlot;
import com.putao.wd.model.Management;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 陪伴
 * Created by guchenkai on 2015/11/25.
 */
@Deprecated
public class PutaoCompanionFragment1 extends PTWDFragment implements View.OnClickListener {
    @Bind(R.id.fl_main)
    FrameLayout fl_main;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.rl_explor_empty)
    RelativeLayout rl_explor_empty;
    @Bind(R.id.ll_date_empty)
    LinearLayout ll_date_empty;

    private ExploreAdapter adapter;
    private int page = 1;

    private SharePopupWindow mSharePopupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        setMainTitleColor(Color.WHITE);
        setRightTitleColor(Color.WHITE);

        adapter = new ExploreAdapter(mActivity, null);
        rv_content.setAdapter(adapter);
        mSharePopupWindow = new SharePopupWindow(mActivity);

    }

    @Override
    public void onStart() {
        super.onStart();
//        if (!MainActivity.isNotRefreshUserInfo && AccountHelper.isLogin()) {
//            rl_explor_empty.setVisibility(View.VISIBLE);
//            jump_loading.setVisibility(View.VISIBLE);
//            getDiaryIndex();
//            addListener();
//            checkDevices();
//        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onLeftAction() {
        startActivity(CaptureActivity.class);
    }

    @Override
    public void onRightAction() {
        startActivity(ManageActivity.class);
    }

    /**
     * 添加监听器
     */
   /* private void addListener() {
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                networkRequest(ExploreApi.getDiaryIndex(String.valueOf(page)),
                        new SimpleFastJsonCallback<Explore>(Explore.class, loading) {
                            @Override
                            public void onSuccess(String url, Explore result) {
                                Logger.i("探索号请求结果 = " + result.toString());
                                if (result.getTotal_page() == 1 || result.getCurrent_page() != result.getTotal_page()) {
                                    adapter.addAll(result.getData());
                                    rv_content.loadMoreComplete();
                                    page++;
                                } else {
                                    rv_content.noMoreLoading();
                                }
                                loading.dismiss();
                            }
                        });
            }
        });
    }*/

    /**
     * 获得陪伴成长日志
     */
/*    private void getDiaryIndex() {
        networkRequest(ExploreApi.getDiaryIndex(String.valueOf(page)),
                new SimpleFastJsonCallback<Explore>(Explore.class, loading) {
                    @Override
                    public void onSuccess(String url, Explore result) {
                        if (result.getData() != null && result.getData().size() > 0) {
                            adapter.addAll(result.getData());
                            ll_date_empty.setVisibility(View.GONE);
                            rv_content.setVisibility(View.VISIBLE);
                        } else {
                            ll_date_empty.setVisibility(View.VISIBLE);
                            rv_content.setVisibility(View.GONE);
                        }
                        if (result.getCurrent_page() != result.getTotal_page() && result.getTotal_page() != 0) {
                            rv_content.loadMoreComplete();
                            page++;
                        } else rv_content.noMoreLoading();
                        loading.dismiss();
                    }
                });
    }*/

    /**
     * 是否已经添加设备
     */
    private void checkDevices() {
        networkRequest(ExploreApi.getManagement(),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        if (!"".equals(result)) {
                            Management management = JSON.parseObject(result, Management.class);
                            if (management.getSlave_device_list() != null && management.getSlave_device_list().size() > 0) {
                                rl_explor_empty.setVisibility(View.GONE);
                            } else {
                                rl_explor_empty.setVisibility(View.VISIBLE);
                            }
                        }
                        loading.dismiss();
                    }
                });
    }

    @OnClick({R.id.btn_explore_empty})
    @Override
    public void onClick(View v) {
        if (!AccountHelper.isLogin()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CaptureActivity.class);
            startActivity(LoginActivity.class, bundle);
            return;
        }
        switch (v.getId()) {
            case R.id.btn_explore_empty://扫描二维码
                startActivity(CaptureActivity.class);
                break;
        }
    }

    @Subcriber(tag = ExploreAdapter.EVENT_DISPLAY)
    public void eventDisplay(ExploreProduct exploreProduct) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DisPlayActivity.BUNDLE_DISPLAY_DETAILS, exploreProduct);
        startActivity(DisPlayActivity.class, bundle);
    }

    @Subcriber(tag = ExploreAdapter.EVENT_DISPLAY)
    public void eventDisplay(ExploreProductPlot plot) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PlotActivity.BUNDLE_DISPLAY_PLOT, plot);
        startActivity(PlotActivity.class, bundle);
    }

}
