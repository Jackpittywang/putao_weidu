package com.putao.wd.created;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.created.adapter.FancyDetailAdapter;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import butterknife.Bind;

/**
 * 奇思妙想详情
 * Created by zhanghao on 2016/1/15.
 */
public class FancyDetailActivity extends PTWDActivity {
    @Bind(R.id.wv_content)
    BasicWebView wv_content;
    @Bind(R.id.rv_created_correlation)
    BasicRecyclerView rv_created_correlation;
    private FancyDetailAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fancy_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        wv_content.loadUrl("http://3g.qq.com");
        adapter = new FancyDetailAdapter(mContext, null);
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
