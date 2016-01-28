package com.putao.wd.created;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.api.CreateApi;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Create;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.CircleTextView;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.scroll.SupportScrollView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 创造详情的父类
 * Created by zhanghao on 2016/1/15.
 */
public class CreateBasicDetailActivity extends PTWDActivity implements View.OnClickListener {

    @Bind(R.id.wv_content)
    BasicWebView wv_content;
    @Bind(R.id.iv_sign)
    ImageDraweeView iv_sign;
    @Bind(R.id.iv_user_icon)
    ImageDraweeView iv_user_icon;
    @Bind(R.id.fl_progress)
    FrameLayout fl_progress;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.v_fancy)
    View v_fancy;
    @Bind(R.id.tv_digest)
    TextView tv_digest;
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
    @Bind(R.id.ll_cool)
    LinearLayout ll_cool;
    @Bind(R.id.sb_cool_icon)
    SwitchButton sb_cool_icon;
    @Bind(R.id.tv_count_cool)
    TextView tv_count_cool;

    private int mSpace;
    private int mMargin;
    private int mTextWidth;
    private Handler mHandler;
    private int mTime = 1000;
    public static final String CREATE = "CREATE";
    public static final String SHOW_PROGRESS = "show_progress";
    private Create mCreate;
    private boolean isShowProgress;

    private int mWidthPixels;
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
        mWidthPixels = mContext.getResources().getDisplayMetrics().widthPixels / 2;
        rl_support.setClickable(false);
        rl_no_support.setClickable(false);
        mCreate = (Create) args.getSerializable(CREATE);
        isShowProgress = args.getBoolean(SHOW_PROGRESS);
        initView();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void initView() {
        sb_cool_icon.setState(mCreate.getFollow_status() == 1 ? true : false);
        sb_cool_icon.setClickable(false);
        iv_sign.setImageURL(mCreate.getCover());
        tv_title.setText(mCreate.getTitle());
        iv_user_icon.setImageURL(mCreate.getAvatar());
        tv_digest.setText(mCreate.getNickname());
        wv_content.loadDataWithBaseURL("about:blank", mCreate.getContent(), "text/html", "utf-8", null);
        if (isShowProgress) {
            initProgress();
            fl_progress.setVisibility(View.VISIBLE);
            rl_progress.setVisibility(View.VISIBLE);
        } else {
            v_fancy.setVisibility(View.VISIBLE);
        }
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
        ll_cool.setOnClickListener(this);
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
    @OnClick({R.id.rl_support, R.id.rl_no_support, R.id.ll_cool})
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
                    networkRequest(CreateApi.setCreateAction(mCreate.getId(), 1),
                            new SimpleFastJsonCallback<String>(String.class, loading) {
                                @Override
                                public void onSuccess(String url, String result) {
                                }
                            });
                    break;
                case R.id.rl_no_support:
                    supportOfFloat = ObjectAnimator.ofFloat(rl_no_support, "translationX", 0, -middle);
                    rl_support.setVisibility(View.GONE);
                    tv_no_support_result.setVisibility(View.VISIBLE);
                    showView(tv_no_support_result);
                    hindView(tv_no_support);
                    networkRequest(CreateApi.setCreateAction(mCreate.getId(), 2),
                            new SimpleFastJsonCallback<String>(String.class, loading) {
                                @Override
                                public void onSuccess(String url, String result) {
                                }
                            });
                    break;
            }
            supportOfFloat.setDuration(1000);
            supportOfFloat.start();
        }
        switch (v.getId()) {
            case R.id.ll_cool:
                Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.anim_cool);
                sb_cool_icon.startAnimation(anim);
                if (mCreate.getFollow_status() == 1)
                    break;
                sb_cool_icon.setState(true);
                networkRequest(CreateApi.setCreateAction(mCreate.getId(), 3),
                        new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                mCreate.setFollow_status(1);
                                tv_count_cool.setText("已关注");
                            }
                        });

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


    private void initProgress() {
        //文字左边margin px值
        mMargin = DensityUtil.dp2px(mContext, 15);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextWidth = tv_step1.getWidth();
                mSpace = (tv_step2.getWidth() - mTextWidth) / 4;
                startAnim(mCreate.getSchedule_curr());
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
