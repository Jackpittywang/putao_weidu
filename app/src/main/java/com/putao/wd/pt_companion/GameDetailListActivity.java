package com.putao.wd.pt_companion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

    private boolean isLoadMore = false;
    private GameDetailAdapter mGameDetailAdapter;
    private int mPosition;
    private int mPage;
    private ArrayList<String> list = new ArrayList<>();
    private List<ServiceSendData> mServiceSendData;
    private Companion mCompanion;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_detail_list;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        mCompanion = (Companion) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION);
        setMainTitle(mCompanion.getService_name());
        mGameDetailAdapter = new GameDetailAdapter(mContext, null);
        rv_content.setAdapter(mGameDetailAdapter);
        initData();
        initBottomMenu();
        addListener();
    }

    /**
     * 下拉刷新 以及 最初的初始化
     */
    private void initData() {
        CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
        List<CompanionDB> downloadArticles = dataBaseManager.getDownloadArticles();
        if (null != downloadArticles)
            mGameDetailAdapter.replaceAll(JSONArray.parseArray(JSON.toJSONString(downloadArticles), ServiceMessageList.class));
        mPage = 1;
        ArrayList<String> notDownloadIds = mCompanion.getNotDownloadIds();
        List<ServiceSendData> serviceSendDatas = listToServiceListData(notDownloadIds);
        networkRequest(CompanionApi.getServiceLists(JSONObject.toJSONString(serviceSendDatas), mCompanion.getService_id()),
                new SimpleFastJsonCallback<ServiceMessage>(ServiceMessage.class, loading) {
                    @Override
                    public void onSuccess(String url, ServiceMessage result) {
                        isLoadMore = false;
                        ArrayList<ServiceMessageList> lists = result.getLists();
                        CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
                        for (ServiceMessageList serviceMessageList : lists) {
                            dataBaseManager.updataDownloadFinish(mCompanion.getService_id(), serviceMessageList);
                        }
                        EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
                        mCompanion.setNotDownloadIds(null);
                        lists = setIsSameDate(lists);
                        mGameDetailAdapter.addAll(0, lists);
                        ptl_refresh.refreshComplete();
                        checkLoadMoreComplete(lists);
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
    private ArrayList<ServiceMessageList> setIsSameDate(ArrayList<ServiceMessageList> result) {

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
    }

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
                bundle.putString(AccountConstants.Bundle.BUNDLE_SERVICE_ID, mCompanion.getService_id());
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
        bundle.putString(AccountConstants.Bundle.BUNDLE_SERVICE_ID, mCompanion.getService_id());
        startActivity(OfficialAccountsActivity.class);
    }


    private void initBottomMenu() {
        final TextView[] menuViews = {tv_menu_first, tv_menu_second, tv_menu_third};
        networkRequest(CompanionApi.getServicemenu(mCompanion.getService_id()),
                new SimpleFastJsonCallback<ArrayList<ServiceMenu>>(ServiceMenu.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<ServiceMenu> result) {
                        if (result != null && result.size() > 0) {
                            ll_bottom_menus.setVisibility(View.VISIBLE);

                            tv_menu_first.setVisibility(View.GONE);
                            tv_menu_second.setVisibility(View.GONE);
                            tv_menu_third.setVisibility(View.GONE);
                            for (int i = 0; i < result.size(); i++) {
                                ServiceMenu menu = result.get(i);
                                menuViews[i].setText(menu.getName() + "");
                                menuViews[i].setTag(menu);
                                menuViews[i].setVisibility(View.VISIBLE);
                                addMenuListener(menuViews[i]);
                            }
                        } else {
                            ll_bottom_menus.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {

                    }
                }, false);
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
                    startActivity(intent);
                } else if (ServiceMenu.TYPE_CLICK.equals(menu.getType())) {
                    //TODO
                }
            }
        });
    }
}


