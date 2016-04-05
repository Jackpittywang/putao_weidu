package com.putao.wd.pt_companion;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * 关注成功
 * Created by guchenkai on 2015/12/3.
 */
public class AttentionSuccessActivity extends PTWDActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_attention_success;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        finish();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
