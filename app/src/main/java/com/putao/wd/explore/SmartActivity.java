package com.putao.wd.explore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;

import butterknife.Bind;

/**
 * 陪伴多元智能
 * Created by guchenkai on 2016/1/11.
 */
public class SmartActivity extends PTWDActivity {
    @Bind(R.id.iv_rotate)
    ImageView iv_rotate;

    public final static String SMART_LIST = "SMART_LIST";
    SmartListFragment mSmartListFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smart;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        mSmartListFragment = new SmartListFragment();
        Animation operatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.smart_rotate);
        operatingAnim.setInterpolator(new LinearInterpolator());
        if (operatingAnim != null)
            iv_rotate.startAnimation(operatingAnim);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_smart, mSmartListFragment, SMART_LIST).commit();
    }


    @Override
    public void onRightAction() {
        super.onRightAction();
        onBack();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Subcriber(tag = SMART_LIST)
    public void eventDisplay(Bundle bundle) {
        SmartDetailFragment smartDetailFragment = new SmartDetailFragment();
        smartDetailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_smart, smartDetailFragment).commit();
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    private void onBack() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SMART_LIST);
        if (null != fragment && fragment.isVisible()) {
            finish();
            overridePendingTransition(R.anim.companion_in_from_down, R.anim.out_from_down);
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_smart, mSmartListFragment, SMART_LIST).commit();
        }
    }


}
