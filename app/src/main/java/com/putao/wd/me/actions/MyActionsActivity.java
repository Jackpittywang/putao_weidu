package com.putao.wd.me.actions;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.actions.adapter.MyActionsAdapter;
import com.putao.wd.model.MeActions;
import com.putao.wd.model.UserInfo;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView.OnLoadMoreListener;

import butterknife.Bind;

/**
 * 我参加的活动
 * Created by wangou on 2015/12/4.
 */
public class MyActionsActivity extends PTWDActivity {
    @Bind(R.id.rv_acitions)
    LoadMoreRecyclerView rv_acitions;
    @Bind(R.id.rl_no_action)
    RelativeLayout rl_no_action;

    private MyActionsAdapter adapter;
    private UserInfo userInfo;

    private int currentPage = 1;//当前页

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_actions;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        adapter = new MyActionsAdapter(mContext, null);
        rv_acitions.setAdapter(adapter);
        addListener();

        userInfo = AccountHelper.getCurrentUserInfo();
        getAcitons(userInfo);
    }

    private void addListener() {
        rv_acitions.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                getAcitons(userInfo);
            }
        });
    }

    /**
     * 获得我参与的活动列表
     *
     * @param userInfo
     */
    private void getAcitons(UserInfo userInfo) {
        networkRequest(UserApi.getMeActions(userInfo.getNick_name(), userInfo.getHead_img(), String.valueOf(currentPage)),
                new SimpleFastJsonCallback<MeActions>(MeActions.class, loading) {
                    @Override
                    public void onSuccess(String url, MeActions result) {
                        if (result.getTotal_page() == 0) {
                            rl_no_action.setVisibility(View.VISIBLE);
                            return;
                        }
                        if (result.getCurrent_page() != result.getTotal_page()) {
                            currentPage++;
                            rv_acitions.loadMoreComplete();
                        } else rv_acitions.noMoreLoading();
                    }
                });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[]{UserApi.URL_GET_ME_ACTIONS};
    }
}
