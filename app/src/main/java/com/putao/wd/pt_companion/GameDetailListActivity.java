package com.putao.wd.pt_companion;

import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ServiceMessage;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.model.ServiceSendData;
import com.putao.wd.pt_companion.adapter.GameDetailAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 游戏详情页
 * Created by zhanghao on 2016/04/05.
 */
public class GameDetailListActivity extends PTWDActivity {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    private boolean isLoadMore = false;
    private GameDetailAdapter mGameDetailAdapter;
    private int mPosition;
    private int mPage;
    private ArrayList<String> list = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_detail_list;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        String service_id = args.getString(AccountConstants.Bundle.BUNDLE_SERVICE_ID);

        mGameDetailAdapter = new GameDetailAdapter(mContext, null);
        rv_content.setAdapter(mGameDetailAdapter);
        initData();
        addListener();
    }

    /**
     * 下拉刷新 以及 最初的初始化
     */
    private void initData() {
        mPage = 1;
        List<ServiceSendData> serviceSendDatas = new ArrayList<>();
        serviceSendDatas.add(new ServiceSendData("124"));
        serviceSendDatas.add(new ServiceSendData("125"));
        networkRequest(CompanionApi.getServiceLists(JSONObject.toJSONString(serviceSendDatas), args.getString(AccountConstants.Bundle.BUNDLE_SERVICE_ID)),
                new SimpleFastJsonCallback<ServiceMessage>(ServiceMessage.class, loading) {
                    @Override
                    public void onSuccess(String url, ServiceMessage result) {
                        isLoadMore = false;
                        ArrayList<ServiceMessageList> newResult = setIsSameDate(result.getLists());
                        mGameDetailAdapter.replaceAll(result.getLists());
                        ptl_refresh.refreshComplete();
                        checkLoadMoreComplete(newResult);
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
        rv_content.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Serializable serializable, int position) {
                startActivity(ArticleDetailForActivitiesActivity.class);
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
        startActivity(OfficialAccountsActivity.class);
    }
}


