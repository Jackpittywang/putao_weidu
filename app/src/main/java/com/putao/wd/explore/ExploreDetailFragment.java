package com.putao.wd.explore;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;

import butterknife.Bind;

/**
 * 探索--详情
 * Created by yanghx on 2016/1/13.
 */
public class ExploreDetailFragment extends BasicFragment {
//    @Bind(R.id.iv_close)
//    ImageView iv_close;
//    @Bind(R.id.iv_top)
//    ImageView iv_top;
//    @Bind(R.id.iv_user_icon)
//    ImageView iv_user_icon;
//    @Bind(R.id.tv_title)
//    TextView tv_title;
//    @Bind(R.id.tv_digest)
//    TextView tv_digest;
//    @Bind(R.id.web)
//    WebView web;
//    @Bind(R.id.ll_cool)
//    LinearLayout ll_cool;
//    @Bind(R.id.ll_comment)
//    LinearLayout ll_comment;
//    @Bind(R.id.ll_share)
//    LinearLayout ll_share;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
