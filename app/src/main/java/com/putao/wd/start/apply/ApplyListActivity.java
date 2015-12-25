package com.putao.wd.start.apply;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ActionEnrollmentList;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.apply.adapter.ApplyListAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import butterknife.Bind;

/**
 * 报名列表
 * Created by wango on 2015/12/4.
 */
public class ApplyListActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;//报名列表
    @Bind(R.id.tv_nomore)
    TextView tv_nomore;//没有更多
    @Bind(R.id.ll_applylist)
    LinearLayout ll_applylist;//报名列表layout区域

    private ApplyListAdapter adapter;
    private String action_id;
    private int page;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        adapter = new ApplyListAdapter(mContext, null);
        rv_content.setAdapter(adapter);
        action_id = args.getString(ActionsDetailActivity.BUNDLE_ACTION_ID);

        getEnrollment();
        addListener();
    }

    private void addListener() {
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            public void onLoadMore() {
                getEnrollment();
            }
        });
    }

    /**
     * 获取报名列表
     */
    private void getEnrollment() {
        networkRequest(StartApi.getEnrollment(String.valueOf(page), action_id),
                new SimpleFastJsonCallback<ActionEnrollmentList>(ActionEnrollmentList.class, loading) {
            @Override
            public void onSuccess(String url, ActionEnrollmentList result) {
                Logger.i("报名列表请求成功");
                adapter.addAll(result.getComment());
                if (result.getCurrent_page() != result.getTotal_page()) {
                    rv_content.loadMoreComplete();
                    page++;
                } else {
                    rv_content.noMoreLoading();
                }
                loading.dismiss();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
