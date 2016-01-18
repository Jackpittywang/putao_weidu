package com.putao.wd;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.QuantityView;
import com.sunnybear.library.view.SmallBang;
import com.sunnybear.library.view.SwitchButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by guchenkai on 2016/1/18.
 */
@Deprecated
public class TestAnimationActivity extends BasicFragmentActivity implements View.OnClickListener {
    @Bind(R.id.iv_img)
    ImageView iv_img;

    private Animation alphaAnimation;
    private Animation scaleAnimation;

    private AnimationSet animationSet;

    @Bind(R.id.btn_switch)
    SwitchButton btn_switch;
    private SmallBang smallBang;
    @Bind(R.id.qv_quantity)
    QuantityView qv_quantity;

    @Override
    protected int getLayoutId() {
        return R.layout.test_activity_animation;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        scaleAnimation = new ScaleAnimation(3.0f, 1.0f, 3.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);

        animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillAfter(true);
        animationSet.setFillEnabled(true);

        iv_img.setAnimation(animationSet);
        animationSet.startNow();

        smallBang = SmallBang.attach2Window(this);

        qv_quantity.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int newQuantity, boolean programmatically) {
                ToastUtils.showToastLong(mContext, newQuantity + "");
            }

            @Override
            public void onLimitReached() {

            }
        });
    }

    @OnClick(R.id.btn_switch)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_switch:
                btn_switch.setState(true);
                smallBang.bang(btn_switch);
                break;
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
