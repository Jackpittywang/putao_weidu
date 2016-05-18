package com.putao.wd.pt_companion;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.db.entity.CompanionDB;
import com.putao.wd.home.adapter.CompanionAdapter;
import com.putao.wd.model.Collection;
import com.putao.wd.model.Companion;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.model.ServiceMessageListReply;
import com.putao.wd.model.ServiceSendData;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.util.PreferenceUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        refresh_data(mCompanion);

//        initData();
        addListener();
    }

    private void addListener() {
        rv_content.setOnItemClickListener(new OnItemClickListener<Companion>() {
            @Override
            public void onItemClick(Companion companion, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, companion);
                PreferenceUtils.save(companion.getService_id() + AccountHelper.getCurrentUid(), 0);
                startActivity(GameDetailListActivity.class, bundle);
                companion.setIsShowRed(false);
                companion.setNotDownloadCount(0);
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
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ptl_refresh.refreshComplete();
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

    @Override
    public void onStart() {
        super.onStart();
        for (Companion companion : mCompanions) {
            checkResult(companion);
        }
        Collections.sort(mCompanions, new StepComparator());
//        mCompanionAdapter.notifyDataSetChanged();
        mCompanionAdapter.replaceAll(mCompanions);
    }

    private void checkResult(Companion companion) {
        try {
            CompanionDB companionDB = mDataBaseManager.getNearestItem(companion.getService_id());
            String receiver_time = companionDB.getReceiver_time();
            if (!TextUtils.isEmpty(receiver_time)) {
                companion.setReceiver_time(Long.parseLong(receiver_time));
            }
            if (companionDB != null) {
                switch (companionDB.getType()) {
                    case "text":
                        companion.setSubstr(companionDB.getMessage());
                        break;
                    case "image":
                        companion.setSubstr("[图片]");
                        break;
                    case "reply":
                        ServiceMessageListReply serviceMessageListReply = JSON.parseObject(companionDB.getReply(), ServiceMessageListReply.class);
                        companion.setSubstr(serviceMessageListReply.getAnswer());
                        break;
                    case "upload_text":
                        companion.setSubstr(companionDB.getMessage());
                        break;
                    case "upload_image":
                        companion.setSubstr("[图片]");
                        break;
                }
                if (companionDB != null && !TextUtils.isEmpty(companionDB.getContent_lists())) {
                    List<ServiceMessageContent> content_lists = JSON.parseArray(companionDB.getContent_lists(), ServiceMessageContent.class);
                    if ("article".equals(companionDB.getType()) && null != content_lists && content_lists.size() >= 0)
                        companion.setSubstr(content_lists.get(0).getTitle());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_REFRESH_COMPANION)
    private void refresh_data(Companion companion) {
        mCompanions = companion.getSecond_level_lists();
        if (null == mCompanionAdapter)
            mCompanionAdapter = new CompanionAdapter(mContext, null);
        rv_content.setAdapter(mCompanionAdapter);
        mCompanionAdapter.replaceAll(mCompanions);
    }

    public class StepComparator implements Comparator<Companion> {


        @Override
        public int compare(Companion lhs, Companion rhs) {
            if (lhs.getReceiver_time() > rhs.getReceiver_time())
                return -1;
            return 1;
        }
    }
}
