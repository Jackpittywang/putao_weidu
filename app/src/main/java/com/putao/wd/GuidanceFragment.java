package com.putao.wd;

import android.os.Bundle;
import android.widget.ImageView;

import com.sunnybear.library.controller.BasicFragment;

import butterknife.Bind;

/**
 * 引导页
 * Created by guchenkai on 2015/12/23.
 */
public class GuidanceFragment extends BasicFragment {
    @Bind(R.id.iv_guide_icon)
    ImageView iv_guide_icon;

    private int iconResId;

    public GuidanceFragment(int iconResId) {
        this.iconResId = iconResId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guidance;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        iv_guide_icon.setImageResource(iconResId);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
