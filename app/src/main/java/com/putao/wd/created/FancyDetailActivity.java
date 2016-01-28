package com.putao.wd.created;

import android.os.Bundle;

import com.putao.wd.R;

/**
 * 奇思妙想详情
 * Created by zhanghao on 2016/1/15.
 */
public class FancyDetailActivity extends CreateBasicDetailActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fancy_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        super.onViewCreatedFinish(saveInstanceState);

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
