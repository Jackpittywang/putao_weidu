package com.putao.wd.created;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.util.DensityUtil;

import butterknife.Bind;

/**
 * 创造详情
 * Created by guchenkai on 2016/1/11.
 */
@Deprecated
public class CreatedDetailActivity extends CreateBasicDetailActivity {

    @Bind(R.id.v_progress)
    View v_progress;
    @Bind(R.id.tv_step1)
    TextView tv_step1;
    @Bind(R.id.tv_step2)
    TextView tv_step2;
    @Bind(R.id.tv_step3)
    TextView tv_step3;
    @Bind(R.id.tv_step4)
    TextView tv_step4;
    @Bind(R.id.tv_step5)
    TextView tv_step5;

    private int mSpace;
    private int mMargin;
    private int mTextWidth;
    private Handler mHandler;
    private int mTime = 1000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_created_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        super.onViewCreatedFinish(saveInstanceState);
        initProgress();
        wv_content.loadUrl("http://wap.baidu.com");
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void initProgress() {
        //文字左边margin px值
        mMargin = DensityUtil.dp2px(mContext,15);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextWidth = tv_step1.getWidth();
                mSpace = (tv_step2.getWidth() - mTextWidth) / 4;
                startAnim(4);
            }
        }, 200);
    }

    private int addMargin(int pointX) {
        return pointX + mMargin;
    }

    /**
     * 设置进度条动画
     *
     * @param step
     */
    private void startAnim(int step) {
        ObjectAnimator ofFloat = null;
        switch (step) {
            case 1:
                setStepText(tv_step1, 1);
                ofFloat = ObjectAnimator.ofFloat(v_progress,
                        "translationX",
                        addMargin(tv_step1.getLeft()), addMargin(tv_step1.getRight()), addMargin(tv_step1.getRight() + mSpace));
                break;
            case 2:
                setStepText(tv_step1, 1);
                setStepText(tv_step2, 2);
                ofFloat = ObjectAnimator.ofFloat(v_progress,
                        "translationX",
                        addMargin(tv_step1.getLeft()), addMargin(tv_step1.getRight()), addMargin(tv_step2.getLeft() + 2 * mSpace), addMargin(tv_step2.getRight() - 2 * mSpace), addMargin(tv_step2.getRight() - mSpace));
                break;
            case 3:
                setStepText(tv_step1, 1);
                setStepText(tv_step2, 2);
                setStepText(tv_step3, 3);
                ofFloat = ObjectAnimator.ofFloat(v_progress,
                        "translationX",
                        addMargin(tv_step1.getLeft()), addMargin(tv_step1.getRight()), addMargin(tv_step2.getLeft() + 2 * mSpace), addMargin(tv_step2.getRight() - 2 * mSpace), addMargin(tv_step2.getRight()),
                        addMargin(tv_step3.getRight()), addMargin(tv_step4.getLeft() + mSpace));
                break;
            case 4:
                setStepText(tv_step1, 1);
                setStepText(tv_step2, 2);
                setStepText(tv_step3, 3);
                setStepText(tv_step4, 4);
                ofFloat = ObjectAnimator.ofFloat(v_progress,
                        "translationX",
                        addMargin(tv_step1.getLeft()), addMargin(tv_step1.getRight()), addMargin(tv_step2.getLeft() + 2 * mSpace), addMargin(tv_step2.getRight() - 2 * mSpace), addMargin(tv_step2.getRight()),
                        addMargin(tv_step3.getRight()), addMargin(tv_step4.getLeft() + 2 * mSpace), addMargin(tv_step4.getRight() - 2 * mSpace), addMargin(tv_step4.getRight() - mSpace));
                break;
            case 5:
                setStepText(tv_step1, 1);
                setStepText(tv_step2, 2);
                setStepText(tv_step3, 3);
                setStepText(tv_step4, 4);
                setStepText(tv_step5, 5);
                ofFloat = ObjectAnimator.ofFloat(v_progress,
                        "translationX",
                        addMargin(tv_step1.getLeft()), addMargin(tv_step1.getRight()), addMargin(tv_step2.getLeft() + 2 * mSpace), addMargin(tv_step2.getRight() - 2 * mSpace), addMargin(tv_step2.getRight()),
                        addMargin(tv_step3.getRight()), addMargin(tv_step4.getLeft() + 2 * mSpace), addMargin(tv_step4.getRight() - 2 * mSpace), addMargin(tv_step4.getRight()), addMargin(tv_step5.getRight()));
                break;
        }
        ofFloat.setDuration(mTime * step);
        ofFloat.start();
    }

    /**
     * 设置文字进度变化
     *
     * @param tv
     * @param step
     */
    private void setStepText(final TextView tv, int step) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setTextColor(0xff48cfae);
            }
        }, step * (mTime - 250));
    }

}
