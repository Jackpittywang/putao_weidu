package com.putao.wd.explore;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.controller.BasicFragmentActivity;

import java.util.List;

import butterknife.Bind;

/**
 * 首页详情
 * Created by guchenkai on 2016/1/11.
 */
public class ExploreDetailActivity extends BasicFragmentActivity {
    @Bind(R.id.iv_close)
    ImageView iv_close;
    @Bind(R.id.iv_top)
    ImageView iv_top;
    @Bind(R.id.iv_user_icon)
    ImageView iv_user_icon;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_digest)
    TextView tv_digest;
    @Bind(R.id.web)
    WebView web;
    @Bind(R.id.ll_cool)
    LinearLayout ll_cool;
    @Bind(R.id.ll_comment)
    LinearLayout ll_comment;
    @Bind(R.id.ll_share)
    LinearLayout ll_share;


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
