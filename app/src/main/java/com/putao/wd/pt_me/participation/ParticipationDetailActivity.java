package com.putao.wd.pt_me.participation;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragmentActivity;

/**
 * 我的参与详情
 * Created by Administrator on 2016/4/5.
 */
public class ParticipationDetailActivity extends BasicFragmentActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_scroll_viewpager_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
