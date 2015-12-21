package com.putao.wd.start.apply;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.ApplyListItem;
import com.putao.wd.model.ActionEnrollment;
import com.putao.wd.model.ActionEnrollmentList;
import com.putao.wd.start.apply.adapter.ApplyListAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 报名列表
 * Created by wango on 2015/12/4.
 */
public class ApplyListActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.brv_applylist)
    LoadMoreRecyclerView brv_applylist;//报名列表
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
        brv_applylist.setAdapter(adapter);
        Bundle bundle = getIntent().getExtras();
        action_id = bundle.getString("action_id");

        getEnrollment();
        addListener();
    }

    private void addListener() {
        this.brv_applylist.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            public void onLoadMore() {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        getEnrollment();
                    }
                }, 3000L);
            }
        });
    }

    /**
     * 获取报名列表
     */
    private void getEnrollment() {
        networkRequest(StartApi.getEnrollment(action_id), new SimpleFastJsonCallback<ActionEnrollmentList>(ActionEnrollmentList.class, loading) {
            @Override
            public void onSuccess(String url, ActionEnrollmentList result) {
                Logger.i("报名列表请求成功");
                List<ActionEnrollment> actionEnrollmentList = result.getComment();
                if (actionEnrollmentList.size() != 0) {
                    adapter.replaceAll(actionEnrollmentList);
                } else {
                    brv_applylist.noMoreLoading();
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

//    //初始化报名列表数据
//    private void initApplyList(){
//        if(this.initApplyListData().size() != 0) {
//            this.ll_applylist.setVisibility(View.VISIBLE);
//            this.tv_nomore.setVisibility(View.GONE);
//            ApplyListAdapter applyListAdapter = new ApplyListAdapter(mContext, this.initApplyListData());
//            this.brv_applylist.setAdapter(applyListAdapter);
//        } else {
//            this.ll_applylist.setVisibility(View.GONE);
//            this.tv_nomore.setVisibility(View.VISIBLE);
//        }
//    }

}
