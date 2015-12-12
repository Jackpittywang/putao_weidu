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
        networkRequest(StartApi.getActionDetail("1"), new SimpleFastJsonCallback<ActionDetail>(ActionDetail.class, loading) {
            @Override
            public void onSuccess(String url, ActionDetail result) {
                Log.i("pt", "动详情列表请求成功");
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
