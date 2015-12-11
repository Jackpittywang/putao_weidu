package com.putao.wd.start.action;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ActionDetail;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;

import java.util.ArrayList;

/**
 * 活动详情
 * Created by wango on 2015/12/4.
 *
 */
public class ActionsDetailActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_actions_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 获取活动详情列表
     * by yanghx
     * @param action_id 活动id
     */
    private void getActionDetail(String action_id) {
        networkRequest(StartApi.getActionDetail(action_id), new SimpleFastJsonCallback<ArrayList<ActionDetail>>(ActionDetail.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<ActionDetail> result) {
                Log.i("pt", "动详情列表请求成功");
            }
        });
    }

}
