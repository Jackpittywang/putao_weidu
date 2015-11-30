package com.putao.wd.product;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import butterknife.Bind;

/**
 * 测试
 * Created by guchenkai on 2015/11/30.
 */
public class TestActivity extends BasicFragmentActivity {
    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;

    @Override
    protected int getLayoutId() {
        return R.layout.test;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
