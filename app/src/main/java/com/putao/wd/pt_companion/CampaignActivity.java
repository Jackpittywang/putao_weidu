
package com.putao.wd.pt_companion;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.CompanionCampaign;
import com.putao.wd.pt_companion.manage.adapter.CampaignAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.Bind;


/**
 * 葡萄活动
 * Created by zhanghao on 2016/04/05.
 */

public class CampaignActivity extends PTWDActivity implements OnItemClickListener<CompanionCampaign> {

    private static int mPage = 1;
    private boolean isLoadMore = false;
    private static long ONEDAY = 24 * 60 * 60 * 1000;
    private static int MILLISECOND = 1000;

    private CampaignAdapter mCampaignAdapter;
    @Bind(R.id.rv_collection)
    LoadMoreRecyclerView rv_collection;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_companion_campaign;
    }

    private ArrayList<String> list = new ArrayList<>();

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        mCampaignAdapter = new CampaignAdapter(mContext, null);
        rv_collection.setAdapter(mCampaignAdapter);
        initData();
        addListener();
    }

    private void addListener() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        rv_collection.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                networkRequest(CompanionApi.getCompanyBlackboard(mPage),
                        new SimpleFastJsonCallback<ArrayList<CompanionCampaign>>(CompanionCampaign.class, loading) {
                            @Override
                            public void onSuccess(String url, ArrayList<CompanionCampaign> result) {
                                isLoadMore = true;

                                ArrayList<CompanionCampaign> newResult = setIsSameDate(result);
                                mCampaignAdapter.addAll(newResult);
                                rv_collection.loadMoreComplete();
                                checkLoadMoreComplete(newResult);
                                loading.dismiss();
                            }
                        });
            }
        });
        mCampaignAdapter.setOnItemClickListener(this);
    }

    /**
     * 下拉刷新 以及 最初的初始化
     */
    private void initData() {
        ArrayList<CompanionCampaign> companionCampaigns = new ArrayList<>();
        companionCampaigns.add(new CompanionCampaign());
        mCampaignAdapter.replaceAll(companionCampaigns);
       /* mPage = 1;
        networkRequest(CompanionApi.getCompanyBlackboard(mPage),
                new SimpleFastJsonCallback<ArrayList<CompanionCampaign>>(CompanionCampaign.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<CompanionCampaign> result) {

                        isLoadMore = false;
                        ArrayList<CompanionCampaign> newResult = setIsSameDate(result);
                        mCampaignAdapter.replaceAll(newResult);
                        ptl_refresh.refreshComplete();
                        checkLoadMoreComplete(newResult);
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ptl_refresh.refreshComplete();
                    }
                }, false);*/
    }

    /**
     * 设置是否为同一天
     *
     * @param result
     * @return
     */
    private ArrayList<CompanionCampaign> setIsSameDate(ArrayList<CompanionCampaign> result) {

        // 如果是 下拉刷新或重新进入则将 list（存用户的临时集合） 集合清空
        if (!isLoadMore) {
            list.clear();
        }

        for (int position = 0; position < result.size(); position++) {
            CompanionCampaign blackboard = result.get(position);

            if (!list.contains(getTimeDate(blackboard))) {
                blackboard.setShowDate(true);
                list.add(getTimeDate(blackboard));
            } else {
                blackboard.setShowDate(false);
            }
        }
        return result;
    }

    /**
     * 取将时间装换为天数
     */
    private String getTimeDate(CompanionCampaign blackboard) {
        long time = Integer.valueOf(blackboard.getTime()) * MILLISECOND;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.format(time);
    }

    private void checkLoadMoreComplete(ArrayList<CompanionCampaign> result) {
        if (null == result)
            rv_collection.noMoreLoading();
        else
            mPage++;
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
    public void onItemClick(CompanionCampaign companionCampaign, int position) {
        if (companionCampaign.getId() == 1) {
            startActivity(ArticleDetailForActivitiesActivity.class);
        } else {
            startActivity(TopicDetailsActivity.class);
        }
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        startActivity(OfficialAccountsActivity.class);
    }
}



