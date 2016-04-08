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

    public static final String BUNDLE_ICON_RES_ID = "bundle_icon_res_id";
    private int iconResId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guidance;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        iconResId = args.getInt(BUNDLE_ICON_RES_ID);
        iv_guide_icon.setImageResource(iconResId);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
