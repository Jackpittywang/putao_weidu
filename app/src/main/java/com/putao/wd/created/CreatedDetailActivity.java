package com.putao.wd.created;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.created.adapter.CreateDetailAdapter;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.CircleTextView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.scroll.SupportScrollView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

/**
 * 创造详情
 * Created by guchenkai on 2016/1/11.
 */
public class CreatedDetailActivity extends PTWDActivity implements View.OnClickListener {

    @Bind(R.id.wv_content)
    BasicWebView wv_content;
    @Bind(R.id.rl_btn)
    RelativeLayout rl_btn;
    @Bind(R.id.tv_support)
    CircleTextView tv_support;
    @Bind(R.id.tv_no_support)
    CircleTextView tv_no_support;
    @Bind(R.id.tv_support_result)
    CircleTextView tv_support_result;
    @Bind(R.id.tv_no_support_result)
    CircleTextView tv_no_support_result;
    @Bind(R.id.sv_detail)
    SupportScrollView sv_detail;
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

    private int mWidthPixels;
    private int mSpace;
    private int mMargin;
    private int mTextWidth;
    private Handler mHandler;
    private int mTime = 1000;
    private boolean btnState = false;
    private ObjectAnimator showAnim;
    private ObjectAnimator hindAnim;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_created_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        addListener();
        wv_content.loadUrl("http://wap.baidu.com");
        //文字左边margin px值
        mMargin = (int) (15 * mContext.getResources().getDisplayMetrics().density + 0.5f);
        mWidthPixels = mContext.getResources().getDisplayMetrics().widthPixels / 2;
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextWidth = tv_step1.getWidth();
                mSpace = (tv_step2.getWidth() - mTextWidth) / 4;
                startAnim(4);
            }
        }, 500);
    }

    private void addListener() {
        wv_content.setOnWebViewLoadUrlCallback(new BasicWebView.OnWebViewLoadUrlCallback() {
            @Override
            public void onParsePutaoUrl(String scheme, JSONObject result) {

            }

            @Override
            public void onWebPageLoaderFinish(String url) {
                Logger.d("网页加载完成");
            }
        });
        tv_no_support.setOnClickListener(this);
        tv_support.setOnClickListener(this);
        wv_content.getParent().requestDisallowInterceptTouchEvent(true);
        sv_detail.setOnScrollListener(new SupportScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                View contentView = sv_detail.getChildAt(0);
                showBtn(contentView.getMeasuredHeight() <= scrollY + sv_detail.getHeight());
            }
        });
    }

    /**
     * 显示隐藏按钮
     */
    private void showBtn(Boolean isShow) {
        if (!btnState && isShow) {
            rl_btn.setVisibility(View.VISIBLE);
            rl_btn.setClickable(true);
            showView(rl_btn);
            btnState = true;
        } else if (btnState && !isShow) {
            rl_btn.setClickable(false);
            hindView(rl_btn);
            btnState = false;
        }
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
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

    private int addMargin(int pointX) {
        return pointX + mMargin;
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
        }, step * mTime - 700);
    }


    @Override
    public void onClick(final View v) {
        v.setClickable(false);
        ObjectAnimator supportOfFloat = null;
        int middle = mWidthPixels - (tv_support.getRight() + tv_support.getLeft()) / 2;
        switch (v.getId()) {
            case R.id.tv_support:
                supportOfFloat = ObjectAnimator.ofFloat(tv_support, "translationX", 0, middle);
                tv_no_support.setVisibility(View.GONE);
                break;
            case R.id.tv_no_support:
                supportOfFloat = ObjectAnimator.ofFloat(tv_no_support, "translationX", 0, -middle);
                tv_support.setVisibility(View.GONE);
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_16_13);
                break;
        }
        supportOfFloat.setDuration(2000);
        supportOfFloat.start();
        supportOfFloat.addListener(new Animator.AnimatorListener() {
                                       @Override
                                       public void onAnimationStart(Animator animation) {
                                       }

                                       @Override
                                       public void onAnimationEnd(Animator animation) {
                                           switch (v.getId()) {
                                               case R.id.tv_support:
                                                   tv_support_result.setVisibility(View.VISIBLE);
                                                   showView(tv_support_result);
                                                   break;
                                               case R.id.tv_no_support:
                                                   tv_no_support_result.setVisibility(View.VISIBLE);
                                                   showView(tv_no_support_result);
                                                   break;
                                           }
                                       }

                                       @Override
                                       public void onAnimationCancel(Animator animation) {

                                       }

                                       @Override
                                       public void onAnimationRepeat(Animator animation) {

                                       }
                                   }

        );
    }

    private void showView(View view) {
        showAnim = ObjectAnimator.ofFloat(view, "alpha", 0, 1.0f);
        showAnim.setDuration(1000);
        showAnim.start();
    }

    private void hindView(View view) {
        hindAnim = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0);
        hindAnim.setDuration(1000);
        hindAnim.start();
    }
}
