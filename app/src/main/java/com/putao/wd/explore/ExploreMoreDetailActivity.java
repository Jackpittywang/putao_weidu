package com.putao.wd.explore;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.mtlib.util.HTMLUtil;
import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.model.ExploreIndex;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.comment.CommentActivity;
import com.putao.wd.video.YoukuVideoPlayerActivity;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
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
 * 更多内容详情
 * Created by yanghx on 2016/1/12.
 */
public class ExploreMoreDetailActivity extends BasicFragmentActivity implements View.OnClickListener {

    @Bind(R.id.iv_top)
    ImageDraweeView iv_top;
    @Bind(R.id.iv_player)
    ImageView iv_player;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_count_cool)
    TextView tv_count_cool;
    @Bind(R.id.wb_explore_detail)
    BasicWebView wb_explore_detail;
    @Bind(R.id.iv_close)
    ImageView iv_close;
    @Bind(R.id.ll_cool)
    LinearLayout ll_cool;
    @Bind(R.id.sb_cool_icon)
    SwitchButton sb_cool_icon;
    @Bind(R.id.ll_comment)
    LinearLayout ll_comment;
    @Bind(R.id.tv_count_comment)
    TextView tv_count_comment;
    @Bind(R.id.ll_share)
    LinearLayout ll_share;

    private SharePopupWindow mSharePopupWindow;//分享弹框
    private ExploreIndex mExploreIndex;
    private String mArticleId;
    private boolean isCool;//是否赞过
    public final static String COOL = "Cool";//是否赞过
    public final static String ARTICLE_ID = "article_id";//是否赞过
    public static final String EVENT_ADD_MORE_DETAIL_COOL = "event_add_more_detail_cool";
    public final static String POSITION = "position";
    private int mPosition;
    private float mWidth;
    private float mHeight;
    private int mCount_comments;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        mArticleId = args.getString(ARTICLE_ID);
        mPosition = args.getInt(POSITION);
        mWidth = DensityUtil.px2dp(mContext, this.getWindowManager().getDefaultDisplay().getWidth() - 200);
        mHeight = (mWidth * 9) / 16 + 2;
        initData();

    }

    private void initData() {
        networkRequest(ExploreApi.getDetail(mArticleId),
                new SimpleFastJsonCallback<ExploreIndex>(ExploreIndex.class, loading) {
                    @Override
                    public void onSuccess(String url, ExploreIndex result) {
                        if (null != result) {
                            mExploreIndex = result;
                            mCount_comments = mExploreIndex.getCount_comments();
                            initView();
                        }
                        loading.dismiss();
                    }
                });
    }


    private void initView() {
        sb_cool_icon.setClickable(false);
        tv_title.setText(mExploreIndex.getTitle());
        tv_count_comment.setText(mCount_comments == 0 ? "评论" : mCount_comments + "");
        iv_top.setImageURL(mExploreIndex.getBanner().get(0).getCover_pic());
        if ("VIDEO".equals(mExploreIndex.getBanner().get(0).getType()))
            iv_player.setVisibility(View.VISIBLE);
        wb_explore_detail.loadDataWithBaseURL("about:blank", HTMLUtil.setWidth(DensityUtil.px2dp(mContext, getWindowManager().getDefaultDisplay().getWidth() - 200), mExploreIndex.getExplanation()), "text/html", "utf-8", null);
        mSharePopupWindow = new SharePopupWindow(this);
        isCool = null != mDiskFileCacheHelper.getAsString(COOL + mExploreIndex.getArticle_id());
        sb_cool_icon.setState(isCool || mExploreIndex.is_like());
        tv_count_cool.setText(mExploreIndex.getCount_likes() + "");
//        wb_explore_detail.setInitialScale(80);

        WebSettings webSettings = wb_explore_detail.getSettings();
//        webSettings.setMinimumFontSize(64);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setUseWideViewPort(false);
//        webSettings.setLoadWithOverviewMode(true);
        addListener();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void addListener() {
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ShareTools.wechatWebShare(mContext, true, mExploreIndex.getTitle(), mExploreIndex.getDescription(), mExploreIndex.getBanner().get(0).getCover_pic(), "http://h5.putao.com/weidu/share/exploration.html?id=" + mExploreIndex.getArticle_id());
            }

            @Override
            public void onWechatFriend() {
                ShareTools.wechatWebShare(mContext, false, mExploreIndex.getTitle(), mExploreIndex.getDescription(), mExploreIndex.getBanner().get(0).getCover_pic(), "http://h5.putao.com/weidu/share/exploration.html?id=" + mExploreIndex.getArticle_id());
            }

            @Override
            public void onQQFriend() {

            }

            @Override
            public void onQQZone() {

            }

            @Override
            public void onSinaWeibo() {

            }
        });
    }

    @OnClick({R.id.iv_player, R.id.iv_close, R.id.ll_cool, R.id.ll_comment, R.id.ll_share})
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_player:
                bundle.putString(YoukuVideoPlayerActivity.BUNDLE_VIDEO_URL, mExploreIndex.getBanner().get(0).getUrl());
                startActivity(YoukuVideoPlayerActivity.class, bundle);
                break;
            case R.id.iv_close:
                finish();
                break;
            case R.id.ll_cool:
                bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, "1");
                Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.anim_cool);
                sb_cool_icon.startAnimation(anim);
                if (isCool) break;
                tv_count_cool.setText(mExploreIndex.getCount_likes() + 1 + "");
                isCool = true;
                EventBusHelper.post(mPosition, EVENT_ADD_MORE_DETAIL_COOL);
                sb_cool_icon.setState(true);
                networkRequest(ExploreApi.addLike(mExploreIndex.getArticle_id()),
                        new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                mDiskFileCacheHelper.put(COOL + mExploreIndex.getArticle_id(), true);
                                loading.dismiss();
                            }
                        });
//                startActivity(PraiseListActivity.class, bundle);
                break;
            case R.id.ll_comment:
                bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, mExploreIndex.getArticle_id());
                bundle.putInt(POSITION, mPosition);
                startActivity(CommentActivity.class, bundle);
                break;
            case R.id.ll_share:
                mSharePopupWindow.show(ll_share);
                break;
        }
    }

    @Subcriber(tag = CommentActivity.EVENT_ADD_CREAT_COMMENT)
    public void eventAddCommentCount(int position) {
        tv_count_comment.setText(++mCount_comments + "");
    }

    @Subcriber(tag = CommentActivity.EVENT_DELETE_CREAT_COMMENT)
    public void evenDeleteCommentCount(int position) {
        tv_count_comment.setText(--mCount_comments + "");
    }
}

