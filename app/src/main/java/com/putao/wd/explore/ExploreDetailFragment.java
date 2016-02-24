package com.putao.wd.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.mtlib.util.HTMLUtil;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.model.ExploreIndex;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.comment.CommentActivity;
import com.putao.wd.user.LoginActivity;
import com.putao.wd.video.YoukuVideoPlayerActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索--详情
 * Created by yanghx on 2016/1/13.
 */
public class ExploreDetailFragment extends BasicFragment implements View.OnClickListener {
    public static final String EVENT_ADD_COOL = "event_add_cool";

    @Bind(R.id.iv_top)
    ImageDraweeView iv_top;
    @Bind(R.id.iv_player)
    ImageView iv_player;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.wb_explore_detail)
    BasicWebView wb_explore_detail;
    @Bind(R.id.tv_count_cool)
    TextView tv_count_cool;
    @Bind(R.id.ll_cool)
    LinearLayout ll_cool;
    @Bind(R.id.sb_cool_icon)
    SwitchButton sb_cool_icon;
    @Bind(R.id.ll_comment)
    LinearLayout ll_comment;
    @Bind(R.id.ll_share)
    LinearLayout ll_share;

    private SharePopupWindow mSharePopupWindow;//分享弹框
    private ExploreIndex mExploreIndex;
    private boolean isCool;//是否赞过
    public final static String COOL = "Cool";//是否赞过
    private int mPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        mPosition = args.getInt(ExploreCommonFragment.INDEX_DATA_PAGE);
        mExploreIndex = (ExploreIndex) args.getSerializable(ExploreCommonFragment.INDEX_DATA);
        mSharePopupWindow = new SharePopupWindow(getActivity());
        isCool = null != mDiskFileCacheHelper.getAsString(COOL + mExploreIndex.getArticle_id());
        sb_cool_icon.setState(isCool);
//        wb_explore_detail.setInitialScale(80);
        tv_count_cool.setText(mExploreIndex.getCount_likes() + "");

//        WebSettings webSettings = wb_explore_detail.getSettings();
//        webSettings.setUseWideViewPort(true);//关键点
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setMinimumFontSize(64);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setUseWideViewPort(false);
//        webSettings.setLoadWithOverviewMode(true);
        initView();
        addListener();
    }


    private void initView() {
        sb_cool_icon.setClickable(false);
        tv_title.setText(mExploreIndex.getTitle());
        iv_top.setImageURL(mExploreIndex.getBanner().get(0).getCover_pic());
        if ("VIDEO".equals(mExploreIndex.getBanner().get(0).getType()))
            iv_player.setVisibility(View.VISIBLE);
        wb_explore_detail.loadDataWithBaseURL("about:blank", HTMLUtil.setWidth(DensityUtil.px2dp(mActivity, mActivity.getWindowManager().getDefaultDisplay().getWidth() - 200), mExploreIndex.getExplanation()), "text/html", "utf-8", null);
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void addListener() {
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ShareTools.wechatWebShare(getActivity(), true, mExploreIndex.getTitle(), mExploreIndex.getDescription(), mExploreIndex.getBanner().get(0).getCover_pic(), "http://h5.putao.com/weidu/share/exploration.html?id=" + mExploreIndex.getArticle_id());
            }

            @Override
            public void onWechatFriend() {
                ShareTools.wechatWebShare(getActivity(), true, mExploreIndex.getTitle(), mExploreIndex.getDescription(), mExploreIndex.getBanner().get(0).getCover_pic(), "http://h5.putao.com/weidu/share/exploration.html?id=" + mExploreIndex.getArticle_id());
            }
        });
    }

    @OnClick({R.id.iv_player, R.id.ll_cool, R.id.ll_comment, R.id.ll_share})
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_player:
                bundle.putString(YoukuVideoPlayerActivity.BUNDLE_VID, mExploreIndex.getBanner().get(0).getUrl());
                startActivity(YoukuVideoPlayerActivity.class, bundle);
                break;
            case R.id.ll_cool:
                bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, "1");
                Animation anim = AnimationUtils.loadAnimation(mActivity, R.anim.anim_cool);
                sb_cool_icon.startAnimation(anim);
                if (isCool) break;
                tv_count_cool.setText(mExploreIndex.getCount_likes() + 1 + "");
                networkRequest(ExploreApi.addLike(mExploreIndex.getArticle_id()),
                        new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                mDiskFileCacheHelper.put(COOL + mExploreIndex.getArticle_id(), true);
                                isCool = true;
                                loading.dismiss();
                                sb_cool_icon.setState(true);
                                EventBusHelper.post(mPosition, EVENT_ADD_COOL);
                            }
                        });

//                startActivity(PraiseListActivity.class, bundle);
                break;
            case R.id.ll_comment:
                if (!AccountHelper.isLogin()) {
                    login();
                    return;
                }
                bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, mExploreIndex.getArticle_id());
                startActivity(CommentActivity.class, bundle);
                break;
            case R.id.ll_share:
                mSharePopupWindow.show(ll_share);
                break;
        }
    }

    private void login() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CommentActivity.class);
        bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, mExploreIndex.getArticle_id());
        startActivity(LoginActivity.class, bundle);
    }
    @Override
    public void startActivity(Class targetClass, Bundle args) {
        Intent intent = new Intent(getActivity(), targetClass);
        if (args != null)
            intent.putExtras(args);
        startActivity(intent);
    }
}
