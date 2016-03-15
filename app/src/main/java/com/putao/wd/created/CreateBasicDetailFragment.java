package com.putao.wd.created;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.ClipboardManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.mtlib.util.HTMLUtil;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.CreateApi;
import com.putao.wd.home.PutaoCreatedSecondFragment;
import com.putao.wd.me.order.OrderListActivity;
import com.putao.wd.model.Create;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
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
public class CreateBasicDetailFragment extends BasicFragment implements View.OnClickListener {

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
    @Bind(R.id.ll_comment)
    LinearLayout ll_comment;
    @Bind(R.id.tv_count_comment)
    TextView tv_count_comment;
    @Bind(R.id.ll_share)
    LinearLayout ll_share;
    @Bind(R.id.iv_close)
    ImageView iv_close;

    private int mSpace;
    private int mMargin;
    private int mTextWidth;
    private Handler mHandler;
    private int mTime = 1000;
    public static final String CREATE = "CREATE";
    public static final String EVENT_CONCERNS_REFRESH = "event_concerns_refresh";
    public static final String EVENT_EXPLORER_ID = "event_explorer_id";
    public static final String EVENT_IS_REMIND = "event_is_remind";
    private boolean isConcernsInit;
    private boolean isConcernsRefresh = false;
    public static final String POSITION = "position";
    public static final String SHOW_PROGRESS = "show_progress";
    private Create mCreate;
    private boolean isShowProgress = true;
    private boolean isDid;
    private SharePopupWindow mSharePopupWindow;//分享弹框

    private int mWidthPixels;
    private int mPosition;
    private boolean btnState = false;
    private ObjectAnimator showAnim;
    private ObjectAnimator hindAnim;
    private int mCommentCount;
    private boolean isEnd;


    public CreateBasicDetailFragment(Bundle bundle){
        mPosition = bundle.getInt(POSITION);
        mCreate = (Create) bundle.getSerializable(CREATE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_created_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        rl_support.setClickable(false);
        rl_no_support.setClickable(false);
        mWidthPixels = mActivity.getResources().getDisplayMetrics().widthPixels / 2;
        mSharePopupWindow = new SharePopupWindow(mActivity);
        isDid = false;
        addListener();
        initView();
    }

    public void setData(Bundle bundle) {
        rl_support.setClickable(false);
        rl_no_support.setClickable(false);
        mWidthPixels = mActivity.getResources().getDisplayMetrics().widthPixels / 2;
        mSharePopupWindow = new SharePopupWindow(mActivity);
        isDid = false;
        addListener();
        mCreate = (Create) bundle.getSerializable(CREATE);
        mPosition = bundle.getInt(POSITION);
//        isShowProgress = args.getBoolean(SHOW_PROGRESS);
        initView();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void initView() {
        if (mCreate.getFollow_status() == 1) {
            sb_cool_icon.setState(true);
            tv_count_cool.setText("已关注");
        }
        mCommentCount = mCreate.getComment().getCount();
        isConcernsInit = mCreate.getFollow_status() == 1 ? true : false;
        sb_cool_icon.setState(isConcernsInit);
        sb_cool_icon.setClickable(false);
        iv_sign.setImageURL(mCreate.getCover());
        tv_title.setText(mCreate.getTitle());
        iv_user_icon.setImageURL(mCreate.getReal_avatar());
        tv_count_comment.setText(mCommentCount == 0 ? "评论" : mCommentCount + "");
        tv_digest.setText(mCreate.getNickname());
        wv_content.loadDataWithBaseURL("about:blank", HTMLUtil.setWidth(DensityUtil.px2dp(mActivity, mActivity.getWindowManager().getDefaultDisplay().getWidth() - 200), mCreate.getContent()), "text/html", "utf-8", null);
        if (isShowProgress) {
            initProgress();
            fl_progress.setVisibility(View.VISIBLE);
            rl_progress.setVisibility(View.VISIBLE);
        } else
            v_fancy.setVisibility(View.VISIBLE);


        if (mCreate.getVote_status() != 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl_support.getLayoutParams();
            layoutParams = new RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            isDid = true;
            switch (mCreate.getVote_status()) {
                case 1:
                    rl_no_support.setVisibility(View.GONE);
                    tv_support.setVisibility(View.GONE);
                    rl_support.setLayoutParams(layoutParams);
                    tv_support_result.setVisibility(View.VISIBLE);
                    rl_support.setEnabled(false);
                    break;
                case 2:
                    rl_support.setVisibility(View.GONE);
                    tv_no_support.setVisibility(View.GONE);
                    rl_no_support.setLayoutParams(layoutParams);
                    tv_no_support_result.setVisibility(View.VISIBLE);
                    rl_no_support.setEnabled(false);
                    break;
            }
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
                View contentView = sv_detail.getChildAt(0);

                boolean isEnd = contentView.getMeasuredHeight() <= sv_detail.getHeight();
                if (isDid) {
                    showDidBtn(isEnd);
                } else
                    showBtn(isEnd);
            }
        });
        sv_detail.setOnScrollListener(new SupportScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                View contentView = sv_detail.getChildAt(0);
                isEnd = contentView.getMeasuredHeight() <= scrollY + sv_detail.getHeight();
                if (isDid) {
                    showDidBtn(isEnd);
                } else
                    showBtn(isEnd);
            }
        });
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ShareTools.wechatWebShare(mActivity, true, mCreate.getTitle(), mCreate.getDescrip(), mCreate.getCover(), mCreate.getShare_links());
            }

            @Override
            public void onWechatFriend() {
                ShareTools.wechatWebShare(mActivity, false, mCreate.getTitle(), mCreate.getDescrip(), mCreate.getCover(), mCreate.getShare_links());
            }

            @Override
            public void onQQFriend() {
//                ShareTools.newInstance(QQ.NAME).setTitle(mCreate.getTitle()).setText(mCreate.getDescrip()).setImageUrl(mCreate.getCover()).setUrl("http://h5.putao.com/weidu/share/creation.html?id=%" + mCreate.getId()).execute(mContext);
            }

            @Override
            public void onQQZone() {

            }

            @Override
            public void onCopyUrl() {
                ClipboardManager copy = (ClipboardManager) mActivity
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText(mCreate.getShare_links());
                ToastUtils.showToastShort(mActivity, "复制成功");
            }
        });
    }

    private void showDidBtn(boolean isEnd) {
        if (isEnd && !btnState) {
            rl_btn.setVisibility(View.VISIBLE);
            showView(rl_btn);
            btnState = true;
        } else if (!isEnd && btnState) {
            hindView(rl_btn);
            btnState = false;
        }
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
    @OnClick({R.id.rl_support, R.id.rl_no_support, R.id.ll_cool, R.id.ll_share, R.id.ll_comment, R.id.iv_close})
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
                    EventBusHelper.post(mPosition, isShowProgress ? PutaoCreatedSecondFragment.EVENT_ADD_CREAT_COOL : FancyFragment.EVENT_ADD_FANCY_COOL);
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
                    EventBusHelper.post(mPosition, isShowProgress ? PutaoCreatedSecondFragment.EVENT_ADD_CREAT_NOT_COOL : FancyFragment.EVENT_ADD_FANCY_NOT_COOL);
                    networkRequest(CreateApi.setCreateAction(mCreate.getId(), 2),
                            new SimpleFastJsonCallback<String>(String.class, loading) {
                                @Override
                                public void onSuccess(String url, String result) {

                                }
                            });
                    break;
            }
            if (null != supportOfFloat) {
                supportOfFloat.setDuration(1000);
                supportOfFloat.start();
            }
        }
        switch (v.getId()) {
            case R.id.ll_cool:
                if (!AccountHelper.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CreateBasicDetailFragment.class);
                    bundle.putSerializable(CREATE, mCreate);
                    startActivity(LoginActivity.class, bundle);
                    return;
                }
                ll_cool.setClickable(false);
                Animation anim = AnimationUtils.loadAnimation(mActivity, R.anim.anim_cool);
                sb_cool_icon.startAnimation(anim);
                if (mCreate.getFollow_status() == 1) {
                    sb_cool_icon.setState(false);
                    tv_count_cool.setText("关注");
                    isConcernsRefresh = true;
                    networkRequest(CreateApi.setCreateAction(mCreate.getId(), 4),
                            new SimpleFastJsonCallback<String>(String.class, loading) {
                                @Override
                                public void onSuccess(String url, String result) {
                                    mCreate.setFollow_status(0);
                                    ll_cool.setClickable(true);
                                }
                            });
                } else {
                    sb_cool_icon.setState(true);
                    tv_count_cool.setText("已关注");
                    isConcernsRefresh = false;
                    networkRequest(CreateApi.setCreateAction(mCreate.getId(), 3),
                            new SimpleFastJsonCallback<String>(String.class, loading) {
                                @Override
                                public void onSuccess(String url, String result) {
                                    mCreate.setFollow_status(1);
                                    ll_cool.setClickable(true);
                                }
                            });
                }
                EventBusHelper.post(mPosition, isShowProgress ? PutaoCreatedSecondFragment.EVENT_CREAT_CONCERNS_CHANGE : FancyFragment.EVENT_FANCY_CONCERNS_CHANGE);
                break;
            case R.id.ll_comment:
                if (!AccountHelper.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CreateCommentActivity.class);
                    bundle.putString(OrderListActivity.TYPE_INDEX, OrderListActivity.TYPE_WAITING_PAY);
                    bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, mCreate.getId());
                    startActivity(LoginActivity.class, bundle);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, mCreate.getId());
                bundle.putInt(CreateBasicDetailFragment.POSITION, mPosition);
                startActivity(CreateCommentActivity.class, bundle);
                break;
            case R.id.ll_share:
                mSharePopupWindow.show(ll_share);
                break;
            case R.id.iv_close:
                break;

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
        mMargin = DensityUtil.dp2px(mActivity, 15);
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
        if (step == 0) return;
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

    /*@Override
    public void finish() {
        super.finish();
        if (isConcernsInit && isConcernsRefresh) {
            EventBusHelper.post(EVENT_CONCERNS_REFRESH, EVENT_CONCERNS_REFRESH);
        }
    }*/

    @Subcriber(tag = CreateCommentActivity.EVENT_ADD_CREAT_COMMENT)
    public void eventAddCommentCount(int position) {
        tv_count_comment.setText(++mCommentCount + "");
    }


    @Subcriber(tag = CreateCommentActivity.EVENT_DELETE_CREAT_COMMENT)
    public void evenDeleteCommentCount(int position) {
        tv_count_comment.setText(--mCommentCount + "");
    }

    public void getRemindCreate(String id) {
        networkRequest(CreateApi.getCreateDetail(id),
                new SimpleFastJsonCallback<Create>(Create.class, loading) {
                    @Override
                    public void onSuccess(String url, Create result) {
                        mCreate = result;
                        isShowProgress = "1".equals(mCreate.getType());
                        initView();
                        loading.dismiss();
                    }
                });
    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
//        wv_content.onPause();
        wv_content.loadUrl("");
    }*/

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (wv_content != null) {
                wv_content.getClass().getMethod("onPause").invoke(wv_content, (Object[]) null);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (wv_content != null) {
                wv_content.getClass().getMethod("onResume").invoke(wv_content, (Object[]) null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        wv_content.destroy();
        super.onDestroy();
    }
}
