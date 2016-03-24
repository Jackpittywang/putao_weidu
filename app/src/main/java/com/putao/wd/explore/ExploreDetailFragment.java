package com.putao.wd.explore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.mtlib.util.HTMLUtil;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.model.ExploreIndex;
import com.putao.wd.model.Marketing;
import com.putao.wd.model.PicClickResult;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.browse.PictrueBrowseActivity;
import com.putao.wd.start.comment.CommentActivity;
import com.putao.wd.user.LoginActivity;
import com.putao.wd.video.YoukuVideoPlayerActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.umeng.analytics.MobclickAgent;

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
    @Bind(R.id.sb_cool_icon)
    SwitchButton sb_cool_icon;
    @Bind(R.id.tv_count_comment)
    TextView tv_count_comment;
    @Bind(R.id.ll_share)
    LinearLayout ll_share;
    @Bind(R.id.iv_close)
    ImageView iv_close;

    private SharePopupWindow mSharePopupWindow;//分享弹框
    private ExploreIndex mExploreIndex;
    private boolean isCool;//是否赞过
    public final static String COOL = "Cool";//是否赞过
    public final static String COOL_COUNT = "cool_count";//赞数量
    public final static String COMMENT_COUNT = "comment_count";//评论数量
    private int mPosition;
    private int mCount_comments;
    private String loadData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        mPosition = args.getInt(ExploreCommonFragment.INDEX_DATA_PAGE);
        mExploreIndex = (ExploreIndex) args.getSerializable(ExploreCommonFragment.INDEX_DATA);
        mCount_comments = mExploreIndex.getCount_comments();
        mSharePopupWindow = new SharePopupWindow(getActivity());
        isCool = !TextUtils.isEmpty(AccountHelper.getCurrentUid()) ? mExploreIndex.is_like() : null != mDiskFileCacheHelper.getAsString(ExploreDetailFragment.COOL + mExploreIndex.getArticle_id());
        sb_cool_icon.setState(isCool);
        iv_close.setVisibility(View.GONE);
        initView();
        addListener();
    }


    private boolean isMore;
    private String mPic;

    private void initView() {
        sb_cool_icon.setClickable(false);
        tv_title.setText(mExploreIndex.getTitle());
        isMore = null == mExploreIndex.getBanner();
        mPic = isMore ? mExploreIndex.getCover_pic() : mExploreIndex.getBanner().get(0).getUrl();
        iv_top.setImageURL(mPic);
        if (isMore ? "VIDEO".equals(mExploreIndex.getType()) : "VIDEO".equals(mExploreIndex.getBanner().get(0).getType()))
            iv_player.setVisibility(View.VISIBLE);
        String cache_count = mDiskFileCacheHelper.getAsString(COOL_COUNT + mExploreIndex.getArticle_id());
        mExploreIndex.setCount_likes(TextUtils.isEmpty(cache_count) ? mExploreIndex.getCount_likes() : Integer.parseInt(cache_count));

        String cache_commentcount = mDiskFileCacheHelper.getAsString(COMMENT_COUNT + mExploreIndex.getArticle_id());
        mCount_comments = TextUtils.isEmpty(cache_commentcount) ? mCount_comments : Integer.parseInt(cache_commentcount);
        mExploreIndex.setCount_comments(mCount_comments);

        tv_count_cool.setText(mExploreIndex.getCount_likes() == 0 ? "赞" : mExploreIndex.getCount_likes() + "");
        tv_count_comment.setText(mCount_comments == 0 ? "评论" : mCount_comments + "");
        loadData = HTMLUtil.setWidth(DensityUtil.px2dp(mActivity, mActivity.getWindowManager().getDefaultDisplay().getWidth() - 200), mExploreIndex.getExplanation());
        wb_explore_detail.loadDataWithBaseURL("about:blank", loadData, "text/html", "utf-8", null);
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void addListener() {
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ShareTools.wechatWebShare(mActivity, true, mExploreIndex.getTitle(), mExploreIndex.getDescription(), mPic, mExploreIndex.getShare_url());
                MobclickAgent.onEvent(mActivity, YouMengHelper.ChoiceHome_detail_share, "微信好友");
            }

            @Override
            public void onWechatFriend() {
                ShareTools.wechatWebShare(mActivity, false, mExploreIndex.getTitle(), mExploreIndex.getDescription(), mPic, mExploreIndex.getShare_url());
                MobclickAgent.onEvent(mActivity, YouMengHelper.ChoiceHome_detail_share, "微信朋友圈");
            }

            @Override
            public void onQQFriend() {
                ShareTools.OnQQZShare(mActivity, true, mExploreIndex.getTitle(), mExploreIndex.getDescription(), mPic, mExploreIndex.getShare_url());
                MobclickAgent.onEvent(mActivity, YouMengHelper.ChoiceHome_detail_share, "QQ好友");
            }

            @Override
            public void onQQZone() {
                ShareTools.OnQQZShare(mActivity, true, mExploreIndex.getTitle(), mExploreIndex.getDescription(), mExploreIndex.getBanner().get(0).getCover_pic(), mExploreIndex.getShare_url());
                MobclickAgent.onEvent(mActivity, YouMengHelper.ChoiceHome_detail_share, "QQ空间");
            }

            public void onSinaWeibo() {
                ShareTools.OnWeiboShare(mActivity, mExploreIndex.getTitle(), mExploreIndex.getShare_url());
                MobclickAgent.onEvent(mActivity, YouMengHelper.ChoiceHome_detail_share, "新浪微博");
            }

            @Override
            public void onCopyUrl() {
                ClipboardManager copy = (ClipboardManager) mActivity
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText(mExploreIndex.getShare_url());
                ToastUtils.showToastShort(mActivity, "复制成功");
            }
        });
        wb_explore_detail.setOnWebViewLoadUrlCallback(new BasicWebView.OnWebViewLoadUrlCallback() {
            @Override
            public void onParsePutaoUrl(String scheme, JSONObject result) {
                PicClickResult picClickResult = JSONObject.parseObject(result.toJSONString(), PicClickResult.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PictrueBrowseActivity.IMAGE_URL, picClickResult);
                startActivity(PictrueBrowseActivity.class, bundle);
            }

            @Override
            public void onWebPageLoaderFinish(String url) {

            }
        });
    }

    @OnClick({R.id.iv_player, R.id.ll_cool, R.id.ll_comment, R.id.ll_share})
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_player:
                bundle.putString(YoukuVideoPlayerActivity.BUNDLE_VID, isMore ? mExploreIndex.getUrl() : mExploreIndex.getBanner().get(0).getUrl());
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
                                mDiskFileCacheHelper.put(COOL_COUNT + mExploreIndex.getArticle_id(), mExploreIndex.getCount_likes() + "");
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
                bundle.putInt(CommentActivity.POSITION, mPosition);
                MobclickAgent.onEvent(mActivity, YouMengHelper.CreatorHome_originate_detail_comment);
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

    @Subcriber(tag = CommentActivity.EVENT_ADD_CREAT_COMMENT)
    public void eventAddCommentCount(int position) {
        if (mPosition == position) {
            tv_count_comment.setText(++mCount_comments + "");
            mDiskFileCacheHelper.put(COMMENT_COUNT + mExploreIndex.getArticle_id(), mCount_comments + "", 60 * 1000);
        }
    }

    @Subcriber(tag = CommentActivity.EVENT_DELETE_CREAT_COMMENT)
    public void evenDeleteCommentCount(int position) {
        if (mPosition == position) {
            tv_count_comment.setText(--mCount_comments + "");
            mDiskFileCacheHelper.put(COMMENT_COUNT + mExploreIndex.getArticle_id(), mCount_comments + "", 60 * 1000);
        }
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        setVideoPause();
    }

    @Override
    public void onPause() {
        super.onPause();
        setVideoPause();
    }

    @Override
    public void onDestroy() {
        wb_explore_detail.destroy();
        super.onDestroy();
    }

    private void setVideoPause() {
        if (null != wb_explore_detail) {
            wb_explore_detail.loadUrl("");
            wb_explore_detail.loadDataWithBaseURL("about:blank", loadData, "text/html", "utf-8", null);
        }
    }

}
    /*@Override
    public void onDestroy() {
        super.onDestroy();
        wb_explore_detail.loadUrl("");
    }*/




