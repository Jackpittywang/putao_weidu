package com.putao.wd.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索(首页)
 * Created by guchenkai on 2016/1/11.
 */
public class PutaoExploreFragment extends BasicFragment implements View.OnClickListener {

    @Bind(R.id.iv_thinking)
    ImageView iv_thinking;
    @Bind(R.id.iv_scan)
    ImageView iv_scan;
    @Bind(R.id.vp_content)
    ViewPager vp_content;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.iv_thinking, R.id.iv_scan})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_thinking:
                startActivity(LoginActivity.class);
                break;
            case R.id.iv_scan:
                startActivity(CaptureActivity.class);
                break;
        }
    }

}
