package com.putao.wd.explore;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * 关注成功
 * Created by guchenkai on 2015/12/3.
 */
public class AttrentionSuccessActivity extends PTWDActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_attention_success;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
