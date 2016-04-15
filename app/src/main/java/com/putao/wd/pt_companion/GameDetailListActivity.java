package com.putao.wd.pt_companion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.putao.wd.model.ServiceMenu;
import com.putao.wd.model.ServiceMessage;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.model.ServiceSendData;
import com.putao.wd.pt_companion.adapter.GameDetailAdapter;
import com.putao.wd.webview.BaseWebViewActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 游戏详情页
 * Created by zhanghao on 2016/04/05.
 */
public class GameDetailListActivity extends PTWDActivity<GlobalApplication> {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;

    @Bind(R.id.ll_bottom_menus)
    LinearLayout ll_bottom_menus;
    @Bind(R.id.tv_menu_first)
    TextView tv_menu_first;
    @Bind(R.id.tv_menu_second)
    TextView tv_menu_second;
    @Bind(R.id.tv_menu_third)
    TextView tv_menu_third;
    @Bind(R.id.vLineLeft)
    View vLineLeft;
    @Bind(R.id.vLineRight)
    View vLineRight;

    private boolean isLoadMore = false;
    private GameDetailAdapter mGameDetailAdapter;
    private int mPosition;
    private int mPage;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<ServiceSendData> mServiceSendData;
    private Companion mCompanion;
    private String mServiceId;
    private ArrayList<ServiceMessageList> lists;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_detail_list;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        mCompanion = (Companion) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION);
        if (null != mCompanion) {
            setMainTitle(mCompanion.getService_name());
            mServiceId = mCompanion.getService_id();
        } else {
            mServiceId = args.getString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE);
            navigation_bar.setRightClickable(false);
            setMainTitleFromNetwork();
        }
        mGameDetailAdapter = new GameDetailAdapter(mContext, null);
        rv_content.setAdapter(mGameDetailAdapter);
        lists = new ArrayList<>();
        initData();
        initBottomMenu();
        addListener();
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
            mGameDetailAdapter.replaceAll(lists);
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
                            CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
                            for (ServiceMessageList serviceMessageList : lists) {
                                dataBaseManager.updataDownloadFinish(mServiceId, serviceMessageList);
                            }
                            EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
//                            lists = setIsSameDate(lists);
                            mGameDetailAdapter.addAll(lists);
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

    /**
     * 设置是否为同一天
     *
     * @param result
     * @return
     */
   /* private ArrayList<ServiceMessageList> setIsSameDate(ArrayList<ServiceMessageList> result) {

        // 如果是 下拉刷新或重新进入则将 list（存用户的临时集合） 集合清空
        if (!isLoadMore) {
            list.clear();
        }

        for (int position = 0; position < result.size(); position++) {
            ServiceMessageList blackboard = result.get(position);

            if (!list.contains(getTimeDate(blackboard))) {
                blackboard.setIsShowData(true);
                list.add(getTimeDate(blackboard));
            } else {
                blackboard.setIsShowData(false);
            }
        }
        return result;
    }*/

    /**
     * 取将时间装换为天数
     */
    private String getTimeDate(ServiceMessageList serviceMessageList) {
        long time = Integer.valueOf(serviceMessageList.getRelease_time()) * 1000L;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.format(time);
    }

    private void checkLoadMoreComplete(ArrayList<ServiceMessageList> result) {
        if (null == result)
            rv_content.noMoreLoading();
        else
            mPage++;
    }

    private void addListener() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ptl_refresh.refreshComplete();
            }
        });
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rv_content.loadMoreComplete();
                rv_content.noMoreLoading();
            }
        });
        rv_content.setOnItemClickListener(new OnItemClickListener<ServiceMessageList>() {
            @Override
            public void onItemClick(ServiceMessageList serviceMessageList, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_SERVICE_MESSAGE_LIST, serviceMessageList);
                bundle.putString(AccountConstants.Bundle.BUNDLE_SERVICE_ID, mServiceId);
                startActivity(ArticleDetailForActivitiesActivity.class, bundle);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

  /*  @OnClick({R.id.tv_game_step, R.id.tv_game_service})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_game_step:
                startActivity(GameStepListActivity.class);
                break;
            case R.id.tv_game_service:
                startActivity(GameServiceActivity.class);
                break;
        }
    }*/

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, mCompanion);
        startActivity(OfficialAccountsActivity.class, bundle);
    }


    private void initBottomMenu() {
        final TextView[] menuViews = {tv_menu_first, tv_menu_second, tv_menu_third};
        networkRequestCache(CompanionApi.getServicemenu(mServiceId),
                new SimpleFastJsonCallback<ArrayList<ServiceMenu>>(ServiceMenu.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<ServiceMenu> result) {
                        if (result != null && result.size() > 0) {
                            cacheData(CompanionApi.getServicemenu(mServiceId).urlString() + mServiceId, result);
                            ll_bottom_menus.setVisibility(View.VISIBLE);
                            tv_menu_first.setVisibility(View.GONE);
                            vLineLeft.setVisibility(View.GONE);
                            tv_menu_second.setVisibility(View.GONE);
                            vLineRight.setVisibility(View.GONE);
                            tv_menu_third.setVisibility(View.GONE);
                            for (int i = 0; i < result.size(); i++) {
                                ServiceMenu menu = result.get(i);
                                menuViews[i].setText(menu.getName() + "");
                                menuViews[i].setTag(menu);
                                menuViews[i].setVisibility(View.VISIBLE);
                                addMenuListener(menuViews[i]);
                            }

                            if (result.size() == 2) {
                                vLineLeft.setVisibility(View.VISIBLE);
                            } else if (result.size() == 3) {
                                vLineLeft.setVisibility(View.VISIBLE);
                                vLineRight.setVisibility(View.VISIBLE);
                            }


                        } else {
                            ll_bottom_menus.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {

                    }
                }, CompanionApi.getServicemenu(mServiceId).urlString() + mServiceId, 60 * 1000);
    }

    private void addMenuListener(TextView menuView) {
        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceMenu menu = (ServiceMenu) v.getTag();
                if (ServiceMenu.TYPE_VIEW.equals(menu.getType()) && !StringUtils.isEmpty(menu.getUrl())) {
                    //跳转web
                    Intent intent = new Intent(mContext, BaseWebViewActivity.class);
                    intent.putExtra(BaseWebViewActivity.TITLE, menu.getName());
                    intent.putExtra(BaseWebViewActivity.URL, menu.getUrl());
                    intent.putExtra(BaseWebViewActivity.SERVICE_ID, mCompanion.getService_id());
                    startActivity(intent);
                } else if (ServiceMenu.TYPE_CLICK.equals(menu.getType())) {
                    //TODO
                }
            }
        });
    }
}


