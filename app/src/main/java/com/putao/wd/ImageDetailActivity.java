package com.putao.wd;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragmentActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/17.
 */
public class ImageDetailActivity extends BasicFragmentActivity {

    public  static final String IMAGE_URL = "image_url";
    @Bind(R.id.explore_image_pager)
    ViewPager image_Pager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_explore_imagedetail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
