package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.RedDotReceiver;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.home.adapter.CompanionAdapter;
import com.putao.wd.model.Companion;
import com.putao.wd.pt_companion.GameDetailListActivity;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.PreferenceUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 陪伴
 * Created by zhanghao on 2016/04/05.
 */
public class PutaoCompanionFragment extends PTWDFragment<GlobalApplication> implements OnItemClickListener<Companion>, View.OnClickListener {
    private CompanionAdapter mCompanionAdapter;
    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.ll_companion_empty)
    LinearLayout ll_companion_empty;

    private ArrayList<Companion> mCompanion;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_companion;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        checkDevice();
    }

    private void checkDevice() {
        if (PreferenceUtils.getValue(GlobalApplication.IS_DEVICE_BIND, false) && AccountHelper.isLogin()) {
            ll_companion_empty.setVisibility(View.GONE);
            navigation_bar.setVisibility(View.VISIBLE);
            ptl_refresh.setVisibility(View.VISIBLE);
            if (null == mCompanionAdapter)
                mCompanionAdapter = new CompanionAdapter(mActivity, null);
            rv_content.setAdapter(mCompanionAdapter);
            addListener();
            initData();
        } else {
            ll_companion_empty.setVisibility(View.VISIBLE);
            navigation_bar.setVisibility(View.GONE);
            ptl_refresh.setVisibility(View.GONE);
        }
    }

    private void initData() {
        networkRequest(CompanionApi.getServiceUserRelation(),
                new SimpleFastJsonCallback<ArrayList<Companion>>(Companion.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Companion> result) {
                        mCompanion = result;
                        CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
                        for (Companion companion : result) {
                            ArrayList<String> notDownloadIds = dataBaseManager.getNotDownloadIds(companion.getService_id());
                            companion.setNotDownloadIds(notDownloadIds);
                        }
                        mCompanionAdapter.replaceAll(result);
                        ptl_refresh.refreshComplete();
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                    }
                }, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        checkDevice();
    }

    private void addListener() {
        mCompanionAdapter.setOnItemClickListener(this);
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onItemClick(Companion companion, int position) {
        if (1 == companion.getIs_relation()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, companion);
            startActivity(GameDetailListActivity.class, bundle);
        } else {
            startActivity(CaptureActivity.class);
        }
    }

    @OnClick(R.id.btn_relevance_device)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_relevance_device:
                if (!AccountHelper.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CaptureActivity.class);
                    startActivity(LoginActivity.class, bundle);
                } else
                    startActivity(CaptureActivity.class);
                break;
        }
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        startActivity(CaptureActivity.class);
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_COMPANION)
    private void refresh_companion(String str) {
        checkDevice();
    }

    @Subcriber(tag = RedDotReceiver.COMPANION_TABBAR)
    private void setCompanionDot(JSONArray accompanyNumber) {
        for (Object object : accompanyNumber) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            String service_id = jsonObject.getString(RedDotReceiver.SERVICE_ID);
            String id = jsonObject.getString(RedDotReceiver.ID);
            for (Companion companion : mCompanion) {
                if (companion.getService_id().equals(service_id)) {
                    ArrayList<String> notDownloadIds = companion.getNotDownloadIds();
                    notDownloadIds.add(id);
                    companion.setNotDownloadIds(notDownloadIds);
                    mCompanionAdapter.notifyItemChanged(mCompanion.indexOf(companion));
                }
            }
        }

    }
}


