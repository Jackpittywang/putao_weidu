package com.putao.wd.created;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.created.adapter.FancyDetailAdapter;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.CircleTextView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.scroll.SupportScrollView;

import butterknife.Bind;

/**
 * 奇思妙想详情
 * Created by zhanghao on 2016/1/15.
 */
public class FancyDetailActivity extends PTWDActivity implements View.OnClickListener {

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

    private int mWidthPixels;
    private boolean btnState = false;
    private ObjectAnimator showAnim;
    private ObjectAnimator hindAnim;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fancy_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        addListener();
        mWidthPixels = mContext.getResources().getDisplayMetrics().widthPixels / 2;
        wv_content.loadUrl("http://wap.baidu.com");
        tv_support.setClickable(false);
        tv_no_support.setClickable(false);
    }

    private void addListener() {
        wv_content.setOnWebViewLoadUrlCallback(new BasicWebView.OnWebViewLoadUrlCallback() {
            @Override
            public void onParsePutaoUrl(String scheme, JSONObject result) {

            }

            @Override
            public void onWebPageLoaderFinish(String url) {
                Logger.d("网页加载完成");
                sv_detail.setOnScrollListener(new SupportScrollView.OnScrollListener() {
                    @Override
                    public void onScroll(int scrollY) {
                        View contentView = sv_detail.getChildAt(0);
                        showBtn(contentView.getMeasuredHeight() <= scrollY + sv_detail.getHeight());
                    }
                });
            }
        });
        tv_no_support.setOnClickListener(this);
        tv_support.setOnClickListener(this);
    }

    /**
     * 显示隐藏按钮
     */
    private void showBtn(Boolean isShow) {
        if (!btnState && isShow) {
            tv_support.setClickable(true);
            tv_no_support.setClickable(true);
            rl_btn.setVisibility(View.VISIBLE);
            rl_btn.setEnabled(true);
            showView(rl_btn);
            btnState = true;
        } else if (btnState && !isShow) {
            tv_support.setClickable(false);
            tv_no_support.setClickable(false);
            rl_btn.setEnabled(false);
            hindView(rl_btn);
            btnState = false;
        }
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
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
