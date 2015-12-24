package com.putao.wd.me.actions;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.actions.adapter.MyActionsAdapter;
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

    private MyActionsAdapter adapter;

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
    }

    private void addListener() {
        rv_acitions.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {

            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
