package com.putao.wd.pt_companion;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.ClipboardManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ActionDetail;
import com.putao.wd.model.ArticleDetail;
import com.putao.wd.model.Comment;
import com.putao.wd.model.CommentReply;
import com.putao.wd.model.CompanionCommentDetail;
import com.putao.wd.model.RegUser;
import com.putao.wd.model.ReplyHeaderInfo;
import com.putao.wd.model.ReplyLists;
import com.putao.wd.pt_companion.adapter.CommentReplyAdapter;
import com.putao.wd.pt_companion.adapter.ReplyListsAdapter;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.start.comment.CommentActivity;
import com.putao.wd.start.comment.adapter.CommentAdapter;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.KeyboardUtils;
import com.sunnybear.library.util.ListUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.emoji.EmojiEditText;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;
import com.sunnybear.library.view.sticky.StickyHeaderLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/11.
 */
public class ArticlesDetailActivity extends PTWDActivity {
    public static final String EVENT_COUNT_COOL = "event_count_cool";

    private String action_id = "1";
    private int page = 1;
    private boolean hasComment;
    private boolean isNoMore;

    @Bind(R.id.rl_comment_second)
    RelativeLayout rl_comment_second;
    @Bind(R.id.iv_author_icon)
    ImageDraweeView iv_author_icon;
    @Bind(R.id.tv_author_name)
    TextView tv_author_name;
    @Bind(R.id.tv_author_time)
    TextView tv_author_time;

    @Bind(R.id.rv_others_comment)
    BasicRecyclerView rv_others_comment;


    @Bind(R.id.et_msg)
    EmojiEditText et_msg;
    @Bind(R.id.vp_emojis)
    ViewPager vp_emojis;

    private String mWd_mid;
    private String mSid;
    private String mPcid;
    private int mPage = 1;


    public final static String COOL = "CommentCool";//是否赞过

    CommentReplyAdapter mCommentReplyAdapter;
    ReplyListsAdapter mReplyListsAdapter;
    private boolean isShowEmoji = false;
    private int mPosition;
    private int mMinLenght;
    private boolean isReply;
    private SharePopupWindow mSharePopupWindow;//分享弹框

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mSharePopupWindow = new SharePopupWindow(mContext);
        mMinLenght = 0;

        Bundle data = getIntent().getExtras();
        if (data != null && data.containsKey("wd_mid")) {
            mWd_mid = data.getString("wd_mid");
        }
        if (data != null && data.containsKey("sid")) {
            mSid = data.getString("sid");
        }
        if (data != null && data.containsKey("pcid")) {
            mPcid = data.getString("pcid");
        }
        if (StringUtils.isEmpty(mWd_mid) || StringUtils.isEmpty(mSid) || StringUtils.isEmpty(mPcid)) {
            finish();
            return;
        }

        getNewCommentData();
        addListener();
    }

    private void getNewCommentData() {
        //
        networkRequest(CompanionApi.getCompanyArticleComment(mWd_mid, mSid, mPcid, String.valueOf(mPage)), new SimpleFastJsonCallback<CompanionCommentDetail>(CompanionCommentDetail.class, loading) {
            @Override
            public void onSuccess(String url, CompanionCommentDetail result) {
                iv_author_icon.setImageURL(result.getComment().getHead_img());
                tv_author_name.setText(result.getComment().getNick_name());
                tv_author_time.setText(DateUtils.secondToDate(Integer.valueOf(result.getComment().getRelease_time()), "yyyy/MM/dd HH:mm"));
//                tv_praise_count.setVisibility(result.getComment().getCount_likes() != 0 ? View.VISIBLE : View.GONE);
//                if (result.getComment().getCount_likes() != 0)
//                    tv_praise_count.setText(String.valueOf(result.getComment().getCount_likes()));

                //设置布局管理器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ArticlesDetailActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mCommentReplyAdapter = new CommentReplyAdapter(mContext, result.getComment().getLike_lists());
//                rv_articlesdetail_applyusers.setAdapter(mCommentReplyAdapter);
//                rv_articlesdetail_applyusers.setLayoutManager(linearLayoutManager);

//                ll_praise_count.setVisibility(result.getComment().getIs_like() == 0 ? View.GONE : View.VISIBLE);
//                if (result.getComment().getIs_like() != 0)
//                    tv_praise_count.setText(String.valueOf(result.getComment().getCount_likes()));

                ReplyHeaderInfo replyHeaderInfo = new ReplyHeaderInfo();
                replyHeaderInfo.setContent(result.getComment().getContent());
                replyHeaderInfo.setCount_comments(result.getComment().getCount_comments());
                replyHeaderInfo.setCount_likes(result.getComment().getCount_likes());
                if (result.getComment().getPics() != null && result.getComment().getPics().size() > 0)
                    replyHeaderInfo.setPic(result.getComment().getPics().get(0));
                else
                    replyHeaderInfo.setPic("");
                replyHeaderInfo.setIs_like(result.getComment().getIs_like() != 0 ? true : false);
                List<ReplyLists> reply_lists = result.getReply_lists();
                if (reply_lists == null)
                    reply_lists = new ArrayList<ReplyLists>();
                reply_lists.add(0, new ReplyLists());
                mReplyListsAdapter = new ReplyListsAdapter(mContext, reply_lists, replyHeaderInfo);
                rv_others_comment.setAdapter(mReplyListsAdapter);

            }
        });
    }

    private void addListener() {

//        ll_praise_count.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //
//                netSetParise();
//            }
//        });
    }

    private void netSetParise() {
        networkRequest(CompanionApi.addCommentPraise("117", "6000", "1"), new SimpleFastJsonCallback<String>(String.class, loading) {

            @Override
            public void onSuccess(String url, String result) {
                getNewCommentData();
            }
        });
    }

//    /**
//     * 添加监听器
//     */
//    private void addListener() {
//
//        rv_others_comment.setOnItemClickListener(new OnItemClickListener<CommentReply>() {
//            @Override
//            public void onItemClick(CommentReply commentReply, int position) {
//                mPosition = mReplyListsAdapter.getItems().indexOf(commentReply);
//
//
//                mSelectPopupWindow.show(rl_main);
//                isShowEmoji = false;//关闭表情包
//                vp_emojis.setVisibility(View.GONE);
//                KeyboardUtils.closeKeyboard(mContext, et_msg);//关闭软键盘
//            }
//        });
//
//        et_msg.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_DEL && mMinLenght > et_msg.length()) {
//                    et_msg.setText("");
//                    isReply = false;
//                    mMinLenght = 0;
//                    return true;
//                }
//                return false;
//            }
//        });
//    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }

    /**
     * 分享
     */
    @Override
    public void onRightAction() {
        super.onRightAction();
        if (null != mSharePopupWindow)
            mSharePopupWindow.show(rl_comment_second);
    }

    //    private void reply() {
//        CommentReply comment = mReplyListsAdapter.getItem(mPosition);
//        String username = comment.getNick_name() + ": ";
//        SpannableString ss = new SpannableString("回复 " + username);
//        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_color_gray)), 0, username.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mMinLenght = ss.length();
//        et_msg.setText(ss);
//        et_msg.setSelection(mMinLenght);
//        et_msg.setFocusableInTouchMode(true);
//        et_msg.requestFocus();
//        InputMethodManager inputManager =
//                (InputMethodManager) et_msg.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.showSoftInput(et_msg, 0);
//        isReply = true;
//    }


//    @OnClick({R.id.tv_emojis, R.id.tv_send, R.id.et_msg})
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_emojis://点击表情栏
//                KeyboardUtils.closeKeyboard(mContext, et_msg);
//                isShowEmoji = isShowEmoji ? false : true;
//                vp_emojis.setVisibility(isShowEmoji ? View.VISIBLE : View.GONE);
//                break;
//            case R.id.tv_send://点击发送
//                if (isReply) {
//                    ReplyLists comment = mReplyListsAdapter.getItem(mPosition);
//                    String msg = et_msg.getText().toString();
//                    msg = msg.substring(msg.lastIndexOf(":") + 2);
//                    if (msg.trim().isEmpty()) {
//                        ToastUtils.showToastShort(mContext, "评论不能为空");
//                        return;
//                    }
//
//
//                    networkRequest(ExploreApi.addComment(msg, action_id, comment.getComment_id()),
//                            new SimpleFastJsonCallback<String>(String.class, loading) {
//                                @Override
//                                public void onSuccess(String url, String result) {
//                                    Logger.i("评论与回复提交成功");
//                                    resetMsg();
//                                    refreshCommentList();
//                                    EventBusHelper.post(mSuperPosition, EVENT_ADD_CREAT_COMMENT);
//                                }
//
//                                @Override
//                                public void onFailure(String url, int statusCode, String msg) {
//                                    super.onFailure(url, statusCode, msg);
//                                    ToastUtils.showToastShort(mContext, "评论发送失败，请检查您的网络");
//                                }
//                            });
//                } else {
//                    String msg = et_msg.getText().toString();
//                    if (msg.trim().isEmpty()) {
//                        ToastUtils.showToastShort(mContext, "评论不能为空");
//                        return;
//                    }
//                    networkRequest(ExploreApi.addComment(msg, action_id),
//                            new SimpleFastJsonCallback<String>(String.class, loading) {
//                                @Override
//                                public void onSuccess(String url, String result) {
//                                    Logger.i("评论与回复提交成功");
//                                    resetMsg();
//                                    refreshCommentList();
//                                    EventBusHelper.post(mSuperPosition, EVENT_ADD_CREAT_COMMENT);
//                                }
//
//                                @Override
//                                public void onFailure(String url, int statusCode, String msg) {
//                                    super.onFailure(url, statusCode, msg);
//                                    ToastUtils.showToastShort(mContext, "评论发送失败，请检查您的网络");
//                                }
//                            });
//                }
//                break;
//            case R.id.et_msg://点击文本输入框
//                isShowEmoji = false;
//                vp_emojis.setVisibility(View.GONE);
//                break;
//        }
//    }
//
//    private void resetMsg() {
//        isReply = false;
//        et_msg.setText("");
//        mMinLenght = 0;
//        vp_emojis.setVisibility(View.GONE);
//    }


//    /**
//     * 刷新评论列表
//     */
//    private void refreshCommentList() {
//        page = 1;
//        rv_others_comment.reset();
//        networkRequest(ExploreApi.getCommentList(String.valueOf(page), action_id), new SimpleFastJsonCallback<ArrayList<ReplyLists>>(ReplyLists.class, loading) {
//            @Override
//            public void onSuccess(String url, ArrayList<ReplyLists> result) {
//                if (result != null) {// result.getTotal_page() == 1 || result.getCurrent_page() != result.getTotal_page()
//
//                    if (result != null && result.size() > 0) {
//                        checkLiked(result);
//                        rv_others_comment.replaceAll(result);
//                    } else adapter.clear();
//                    hasComment = true;
//                    rv_others_comment.loadMoreComplete();
//                    page++;
//                } else {
//                    rv_content.noMoreLoading();
//                    hasComment = false;
//                }
//                loading.dismiss();
//            }
//        });
//    }

    /**
     * 重置
     */
    public void reset() {
        isNoMore = false;
    }

    /**
     * 获取评论列表
     */
    private void getCommentList() {
        networkRequest(ExploreApi.getCommentList(String.valueOf(page), action_id), new SimpleFastJsonCallback<ArrayList<ReplyLists>>(ReplyLists.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<ReplyLists> result) {
                Logger.i("活动评论列表请求成功");
                Logger.i(url);
                List<ReplyLists> comments = result;
                if (comments != null && comments.size() > 0) {
//                    checkLiked(comments);
                    mReplyListsAdapter.addAll(comments);
                }
                if (result != null) { //result.getCurrent_page() != result.getTotal_page()
                    hasComment = true;
//                    rv_others_comment.loadMoreComplete();
                    page++;
                } else {
                    //         rv_content.noMoreLoading();
                    hasComment = false;
                }
                loading.dismiss();
            }
        });
    }

//    //赞或取消赞时更新此页显示
//    @Subcriber(tag = CommentActivity.EVENT_COUNT_COOL)
//    public void eventClickComment(boolean isCool) {
//        ll_praise_count.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //
//                netSetParise();
//            }
//        });
//    }


//    private void checkLiked(List<ReplyLists> comments) {
//        int i = 0;
//        for (ReplyLists comment : comments) {
//            if (Boolean.parseBoolean(mDiskFileCacheHelper.getAsString(COOL + comment.getComment_id())))
//                comments.get(i).setIs_like(true);
//            i++;
//        }
//    }
}
