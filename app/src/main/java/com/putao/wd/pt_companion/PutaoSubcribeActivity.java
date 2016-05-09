package com.putao.wd.pt_companion;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.db.entity.CompanionDB;
import com.putao.wd.model.Companion;
import com.putao.wd.model.ServiceMessage;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.model.ServiceSendData;
import com.putao.wd.pt_companion.adapter.SubribeAdapter;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
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


    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;

    private SubribeAdapter mServiceAdapter;
    private Companion mCompanion;
    protected String mServiceId;
    private ArrayList<ServiceMessageList> lists;
    private boolean isLoadMore = false;
    private ArrayList<ServiceSendData> mServiceSendData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_companion_subribe;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mCompanion = (Companion) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION);
        if (null != mCompanion) {
            setMainTitle(mCompanion.getService_name());
            mServiceId = mCompanion.getService_id();
        } else {
            mServiceId = args.getString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE);
            setMainTitleFromNetwork();
        }

        mServiceAdapter = new SubribeAdapter(mContext, null);
        rv_content.setAdapter(mServiceAdapter);

//        initData();
        addListener();
    }

    private void addListener() {
        rv_content.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Serializable serializable, int position) {

            }
        });
    }

    private void setMainTitleFromNetwork() {
        networkRequest(CompanionApi.getServiceInfo(mServiceId),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        JSONObject jsonObject = JSON.parseObject(result);
                        setMainTitle(jsonObject.getString("service_name"));
                        mCompanion = new Companion();
                        mCompanion.setService_id(jsonObject.getString("service_id"));
                        mCompanion.setService_name(jsonObject.getString("service_name"));
                        mCompanion.setService_icon(jsonObject.getString("service_icon"));
                        mCompanion.setIs_relation(jsonObject.getInteger("is_relation"));
                        mCompanion.setIs_unbunding(jsonObject.getBoolean("is_unbunding"));
                        mCompanion.setService_description(jsonObject.getString("service_description"));
                        navigation_bar.setRightClickable(true);
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ptl_refresh.refreshComplete();
                    }
                }, false);
    }

    /**
     * 下拉刷新 以及 最初的初始化
     */
    private void initData() {
        CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
        List<CompanionDB> downloadArticles = dataBaseManager.getDownloadArticles(mServiceId);
        if (null != downloadArticles) {
            for (CompanionDB companionDB : downloadArticles) {
                ServiceMessageList serviceMessageList = new ServiceMessageList();
                serviceMessageList.setType(companionDB.getType());
//                serviceMessageList.setIsShowData(true);
                serviceMessageList.setContent_lists(JSON.parseArray(companionDB.getContent_lists(), ServiceMessageContent.class));
                serviceMessageList.setRelease_time(Integer.parseInt(companionDB.getRelease_time()));
                lists.add(serviceMessageList);
            }
            mServiceAdapter.replaceAll(lists);
            rv_content.scrollToPosition(lists.size() - 1);
//            mGameDetailAdapter.replaceAll(JSONArray.parseArray(JSONArray.toJSONString(downloadArticles), ServiceMessageList.class));
        }
//        mPage = 1;
        if (null == mCompanion)
            return;
        ArrayList<String> notDownloadIds = dataBaseManager.getNotDownloadIds(mServiceId);
        List<ServiceSendData> serviceSendDatas = listToServiceListData(notDownloadIds);
        mCompanion.setNotDownloadIds(null);
        if (null != serviceSendDatas && serviceSendDatas.size() > 0)
            networkRequest(CompanionApi.getServiceLists(JSONObject.toJSONString(serviceSendDatas), mServiceId),
                    new SimpleFastJsonCallback<ServiceMessage>(ServiceMessage.class, loading) {
                        @Override
                        public void onSuccess(String url, ServiceMessage result) {
                            isLoadMore = false;
                            lists = result.getLists();
                            if (null != lists && lists.size() > 0) {
                                CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
                                for (ServiceMessageList serviceMessageList : lists) {
                                    dataBaseManager.updataDownloadFinish(serviceMessageList);
                                }
                                EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
//                            lists = setIsSameDate(lists);
                                mServiceAdapter.addAll(lists);
                                rv_content.scrollToPosition(mServiceAdapter.getItemCount() - 1);
                            }
                            ptl_refresh.refreshComplete();
//                            checkLoadMoreComplete(lists);
                            loading.dismiss();
                        }

                        @Override
                        public void onFailure(String url, int statusCode, String msg) {
                            super.onFailure(url, statusCode, msg);
                            ptl_refresh.refreshComplete();
                        }
                    }, false);
    }


    private List<ServiceSendData> listToServiceListData(ArrayList<String> notDownloadIds) {
        if (null == mServiceSendData)
            mServiceSendData = new ArrayList<ServiceSendData>();
        if (null != notDownloadIds)
            for (String str : notDownloadIds) {
                mServiceSendData.add(new ServiceSendData(str));
            }
        return mServiceSendData;
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
    public void onRightAction() {
        super.onRightAction();
        startActivity(SubscriptionNumberActivity.class);
    }
}
