package com.putao.wd.explore;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.explore.adapter.MarketingAdapter;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * 创想
 * Created by guchenkai on 2016/1/11.
 */
public class MarketingDetailActivity extends PTWDActivity {
    @Bind(R.id.iv_close)
    ImageView iv_close;
    @Bind(R.id.iv_top)
    ImageView iv_top;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_digest)
    TextView tv_digest;
    @Bind(R.id.web)
    WebView web;


    private List<Marketing> markets;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_marketing_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        initData();
    }

    private void initView() {
    }

    private void initData() {
        initView();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
