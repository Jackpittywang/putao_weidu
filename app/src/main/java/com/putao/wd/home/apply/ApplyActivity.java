package com.putao.wd.home.apply;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * 我要参加
 * Created by wango on 2015/12/4.
 *
 */
public class ApplyActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
            addNavgation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
