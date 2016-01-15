package com.putao.wd.created;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * 奇思妙想详情
 * Created by zhanghao on 2016/1/15.
 */
public class FancyDetailActivity extends PTWDActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fancy_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
