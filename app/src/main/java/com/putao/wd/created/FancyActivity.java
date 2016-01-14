package com.putao.wd.created;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.created.adapter.FancyAdapter;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import butterknife.Bind;

/**
 * 创想详情
 * Created by guchenkai on 2016/1/11.
 */
public class FancyActivity extends PTWDActivity {
    @Bind(R.id.rv_fancy)
    LoadMoreRecyclerView rv_fancy;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_fancy;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        FancyAdapter fancyAdapter = new FancyAdapter(mContext, null);
        fancyAdapter.add(new Marketing());
        fancyAdapter.add(new Marketing());
        fancyAdapter.add(new Marketing());
        rv_fancy.noMoreLoading();
        rv_fancy.setAdapter(fancyAdapter);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
