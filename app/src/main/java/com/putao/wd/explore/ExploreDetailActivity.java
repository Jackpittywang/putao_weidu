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



    private List<Marketing> markets;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nexplore_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
