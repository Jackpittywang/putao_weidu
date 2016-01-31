package com.putao.wd.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.model.ExploreIndex;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.comment.CommentActivity;
import com.putao.wd.video.VideoPlayerActivity;
import com.putao.wd.video.YoukuVideoPlayerActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索--详情
 * Created by yanghx on 2016/1/13.
 */
public class ExploreDetailFragment extends BasicFragment implements View.OnClickListener {

    @Bind(R.id.iv_top)
    ImageDraweeView iv_top;
    @Bind(R.id.iv_player)
    ImageView iv_player;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.wb_explore_detail)
    BasicWebView wb_explore_detail;
    @Bind(R.id.iv_close)
    ImageView iv_close;
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
    private float mWidth;
    private float mHeight;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        mWidth = DensityUtil.px2dp(mActivity, mActivity.getWindowManager().getDefaultDisplay().getWidth() - 200);
        mHeight = (mWidth * 9) / 16 + 2;
        mExploreIndex = (ExploreIndex) args.getSerializable(ExploreCommonFragment.INDEX_DATA_PAGE);
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
        wb_explore_detail.loadDataWithBaseURL("about:blank", setImageWidth("<iframe class=\"video_iframe\"([^>]*)", setImageWidth("<img([^>]*)", mExploreIndex.getExplanation(), false), true), "text/html", "utf-8", null);
    }

    private String setImageWidth(String reg, String explanation, boolean isVideo) {
        Pattern p = Pattern.compile(reg);
        String replaceAll = explanation;
        Matcher m = p.matcher(explanation);// 开始编译
        while (m.find()) {
            String group = m.group(1);
            group = addWidHei(group, isVideo);
            group = addStyle(group);
            replaceAll = replaceAll.replace(m.group(1), group);
            System.out.println(replaceAll);
            if (isVideo) {
                Pattern pVideo = Pattern.compile(" src=\"([^\"]*)");
                Matcher mVideo = pVideo.matcher(group);// 开始编译
                while (mVideo.find()) {
                    String video = replaceHTML("width=([^&]*)", mVideo.group(1), "width=" + mWidth + "&");
                    video = replaceHTML("height=([^&]*)", video, "height=" + mHeight + "&");
                    replaceAll = replaceAll.replace(mVideo.group(1), video);
                }
            }
        }
        Logger.d(replaceAll);
        return replaceAll;
    }

    private String addWidHei(String group, boolean isVideo) {
        group = replaceHTML("width=\"([^\"]*)", group, " width=\"" + mWidth + "\"");
        if (isVideo)
            group = replaceHTML("height=\"([^\"]*)", group, " height=\"" + mHeight + "\"");
        return group;
    }

    private String replaceHTML(String reg, String group, String replace) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(group);
        if (m.find()) {
            group = group.replace(m.group(), replace);
        } else {
            group = replace + group;
        }
        return group;
    }

    private String addStyle(String group) {
        Pattern p1 = Pattern.compile("style=\"([^>]*)");
        Matcher m1 = p1.matcher(group);
        if (m1.find()) {
            group = replaceHTML("width:([^;]*)", group, " width=\"" + mWidth + "\"");
//            group = replaceHTML("height:([^;]*)", group, mWidth + "", " height:" + mWidth);
        } else {
            group = " style=\"width:" + mWidth + ";\"" + group;
        }
        return group;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void addListener() {
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ShareTools.wechatWebShare(getActivity(), true, "创造详情", "和孩子一起打造创造空间", "http://mall.file.putaocdn.com/file/a007d1dad9979d9caa70c3988e2bb2cab70679f3.jpg", "http://www.putao.com/product/11");
            }

            @Override
            public void onWechatFriend() {
                ShareTools.wechatWebShare(getActivity(), false, "创造详情", "和孩子一起打造创造空间", "http://mall.file.putaocdn.com/file/a007d1dad9979d9caa70c3988e2bb2cab70679f3.jpg", "http://www.putao.com/product/11");
            }
        });
    }

    @OnClick({R.id.iv_player, R.id.iv_close, R.id.ll_cool, R.id.ll_comment, R.id.ll_share})
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_player:
                bundle.putString(YoukuVideoPlayerActivity.BUNDLE_VID, mExploreIndex.getBanner().get(0).getUrl());
                startActivity(YoukuVideoPlayerActivity.class, bundle);
                break;
            case R.id.iv_close:
                mActivity.finish();
                mActivity.overridePendingTransition(R.anim.in_from_down, R.anim.out_from_top);
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
                            }
                        });
//                startActivity(PraiseListActivity.class, bundle);
                break;
            case R.id.ll_comment:
                bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, mExploreIndex.getArticle_id());
                startActivity(CommentActivity.class, bundle);
                break;
            case R.id.ll_share:
                mSharePopupWindow.show(ll_share);
                break;
        }
    }

    @Override
    public void startActivity(Class targetClass, Bundle args) {
        Intent intent = new Intent(getActivity(), targetClass);
        if (args != null)
            intent.putExtras(args);
        startActivity(intent);
    }
}
