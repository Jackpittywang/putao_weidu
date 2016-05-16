package com.putao.wd.pt_companion;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GPushMessageReceiver;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.db.entity.CompanionDB;
import com.putao.wd.home.adapter.CompanionAdapter;
import com.putao.wd.model.Companion;
import com.putao.wd.model.GpushMessageAccNumber;
import com.putao.wd.model.ServiceMessage;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.model.ServiceSendData;
import com.putao.wd.pt_companion.adapter.SubribeAdapter;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/5.
 */
public class PutaoSubcribeActivity extends PTWDActivity<GlobalApplication> {

    private CompanionAdapter mCompanionAdapter;

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;

    private Companion mCompanion;
    private ArrayList<Companion> mCompanions;

    protected String mServiceId;
    private ArrayList<ServiceMessageList> lists;
    private boolean isLoadMore = false;
    private CompanionDBManager mDataBaseManager;
    private ArrayList<ServiceSendData> mServiceSendData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_companion_subribe;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mDataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
        mCompanion = (Companion) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION);
        mCompanions = mCompanion.getSecond_level_lists();
        if (null == mCompanionAdapter)
            mCompanionAdapter = new CompanionAdapter(mContext, mCompanions);
        rv_content.setAdapter(mCompanionAdapter);

//        initData();
        addListener();
    }

    private void addListener() {
        rv_content.setOnItemClickListener(new OnItemClickListener<Companion>() {
            @Override
            public void onItemClick(Companion companion, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, companion);
                startActivity(GameDetailListActivity.class, bundle);
                mDataBaseManager.removeEmptyData(companion.getService_id());
                companion.setIsShowRed(false);
                companion.setNotDownloadIds(new ArrayList<String>());
                boolean isShowTabDot = false;
                for (Companion compan : mCompanions) {
                    isShowTabDot = compan.isShowRed() || isShowTabDot;
                    if (isShowTabDot) break;
                }
                if (!isShowTabDot)
                    EventBusHelper.post(mCompanion, AccountConstants.EventBus.EVENT_REFRESH_SUBSCRIBE);
                mCompanionAdapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }

    @Subcriber(tag = GPushMessageReceiver.COMPANION_DOT)
    private void setCompanionDot(ArrayList<GpushMessageAccNumber> accompanyNumber) {
        for (GpushMessageAccNumber gpushMessageAccNumber : accompanyNumber) {
            String service_id = gpushMessageAccNumber.getService_id();
            String id = gpushMessageAccNumber.getId();
            for (Companion companion : mCompanions) {
                if (companion.getService_id().equals(service_id)) {
                    ArrayList<String> notDownloadIds = companion.getNotDownloadIds();
                    notDownloadIds.add(id);
                    companion.setIsShowRed(true);
                    companion.setNotDownloadIds(notDownloadIds);
                    mCompanionAdapter.notifyItemChanged(mCompanions.indexOf(companion));
                }
            }
        }

    }
}
