package com.putao.wd.created;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.home.adapter.CreateAdapter;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import butterknife.Bind;

/**
 * 创想详情
 * Created by guchenkai on 2016/1/11.
 */
public class CreatedDetailActivity extends PTWDActivity {
    @Bind(R.id.wv_content)
    BasicWebView wv_content;
    @Bind(R.id.rv_created_correlation)
    BasicRecyclerView rv_created_correlation;
    private CreateAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_created_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        wv_content.loadUrl("http://3g.qq.com");
        adapter = new CreateAdapter(mContext, null);
        rv_created_correlation.setAdapter(adapter);
        adapter.add(new Marketing());
        adapter.add(new Marketing());
        adapter.add(new Marketing());
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
