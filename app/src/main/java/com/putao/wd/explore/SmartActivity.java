package com.putao.wd.explore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.util.Logger;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 陪伴多元智能
 * Created by guchenkai on 2016/1/11.
 */
public class SmartActivity extends PTWDActivity implements View.OnClickListener{
    @Bind(R.id.iv_rotate)
    ImageView iv_rotate;/*
    @Bind(R.id.iv_rotate_point1)
    ImageView iv_rotate_point1;
    @Bind(R.id.iv_rotate_point2)
    ImageView iv_rotate_point2;*/

    public final static String SMART_LIST = "SMART_LIST";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smart;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        iv_rotate.startAnimation(loadAnim(R.anim.smart_rotate, linearInterpolator));
/*        iv_rotate_point1.startAnimation(loadAnim(R.anim.smart_rotate_point1, linearInterpolator));
        iv_rotate_point2.startAnimation(loadAnim(R.anim.smart_rotate_point2, linearInterpolator));*/
        showList();
    }

    private Animation loadAnim(int res,Interpolator interpolator) {
        Animation operatingAnim = AnimationUtils.loadAnimation(mContext, res);
        operatingAnim.setInterpolator(interpolator);
        return operatingAnim;
    }


    @Override
    public void onRightAction() {
        Logger.d("back");
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
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.smart_alpha_in, R.anim.smart_alpha_out).replace(R.id.fl_smart, smartDetailFragment).commit();
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
            showList();
        }
    }

    private void showList() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.smart_alpha_in, R.anim.smart_alpha_out)
                .replace(R.id.fl_smart,  new SmartListFragment(), SMART_LIST).commit();
    }

    @OnClick({R.id.fl_smart})
    @Override
    public void onClick(View v){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SMART_LIST);
        if (null == fragment||!fragment.isVisible())
            showList();
    }
}
