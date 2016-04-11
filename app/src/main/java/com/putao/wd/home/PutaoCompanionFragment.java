package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.RedDotReceiver;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.home.adapter.CompanionAdapter;
import com.putao.wd.model.Companion;
import com.putao.wd.pt_companion.GameDetailListActivity;
import com.putao.wd.qrcode.CaptureActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 陪伴
 * Created by zhanghao on 2016/04/05.
 */
public class PutaoCompanionFragment extends PTWDFragment implements OnItemClickListener<Companion>, View.OnClickListener {
    private CompanionAdapter mCompanionAdapter;
    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.ll_companion_empty)
    LinearLayout ll_companion_empty;

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
        if (/*GlobalApplication.isBindDevice*/true) {
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
        Bundle bundle = new Bundle();
        companion.setNum(0);
        mCompanionAdapter.notifyItemChanged(position);
        bundle.putString(AccountConstants.Bundle.BUNDLE_SERVICE_ID, companion.getService_id());
        startActivity(GameDetailListActivity.class, bundle);
    }

    @OnClick(R.id.btn_relevance_device)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_relevance_device:
                startActivity(CaptureActivity.class);
                break;
        }
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        startActivity(CaptureActivity.class);
    }

    @Subcriber(tag = RedDotReceiver.APPPRODUCT_ID)
    private void refreshCompanion(String refresh) {
        checkDevice();
    }

}


