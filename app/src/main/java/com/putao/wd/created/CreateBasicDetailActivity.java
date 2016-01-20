package com.putao.wd.created;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.CircleTextView;
import com.sunnybear.library.view.scroll.SupportScrollView;

import butterknife.Bind;

/**
 * 创造详情的父类
 * Created by zhanghao on 2016/1/15.
 */
public abstract class CreateBasicDetailActivity extends PTWDActivity implements View.OnClickListener {

    @Bind(R.id.wv_content)
    BasicWebView wv_content;
    @Bind(R.id.rl_btn)
    RelativeLayout rl_btn;
    @Bind(R.id.rl_support)
    RelativeLayout rl_support;
    @Bind(R.id.rl_no_support)
    RelativeLayout rl_no_support;
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
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        addListener();
        mWidthPixels = mContext.getResources().getDisplayMetrics().widthPixels / 2;
        rl_support.setClickable(false);
        rl_no_support.setClickable(false);
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
        rl_support.setOnClickListener(this);
        rl_no_support.setOnClickListener(this);
    }

    /**
     * 显示隐藏按钮
     */
    private void showBtn(Boolean isShow) {
        if (!btnState && isShow) {
            rl_support.setClickable(true);
            rl_no_support.setClickable(true);
            rl_btn.setVisibility(View.VISIBLE);
            rl_btn.setEnabled(true);
            showView(rl_btn);
            btnState = true;
        } else if (btnState && !isShow) {
            rl_support.setClickable(false);
            rl_no_support.setClickable(false);
            rl_btn.setEnabled(false);
            hindView(rl_btn);
            btnState = false;
        }
    }

    @Override
    public void onClick(final View v) {
        if (R.id.rl_support == v.getId() || R.id.rl_no_support == v.getId()) {
            v.setEnabled(false);
            ObjectAnimator supportOfFloat = null;
            int middle = mWidthPixels - (rl_support.getRight() + rl_support.getLeft()) / 2;
            switch (v.getId()) {
                case R.id.rl_support:
                    supportOfFloat = ObjectAnimator.ofFloat(rl_support, "translationX", 0, middle);
                    rl_no_support.setVisibility(View.GONE);
                    tv_support_result.setVisibility(View.VISIBLE);
                    showView(tv_support_result);
                    hindView(tv_support);
                    break;
                case R.id.rl_no_support:
                    supportOfFloat = ObjectAnimator.ofFloat(rl_no_support, "translationX", 0, -middle);
                    rl_support.setVisibility(View.GONE);
                    tv_no_support_result.setVisibility(View.VISIBLE);
                    showView(tv_no_support_result);
                    hindView(tv_no_support);
                    break;
            }
            supportOfFloat.setDuration(1000);
            supportOfFloat.start();
        }
    }

    private void showView(View view) {
        showAnim = ObjectAnimator.ofFloat(view, "alpha", 0, 0.9f);
        showAnim.setDuration(1000);
        showAnim.start();
    }

    private void hindView(View view) {
        hindAnim = ObjectAnimator.ofFloat(view, "alpha", 0.9f, 0);
        hindAnim.setDuration(1000);
        hindAnim.start();
    }
}
