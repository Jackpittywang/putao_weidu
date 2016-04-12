package com.putao.wd.pt_companion;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.CompanionCommentDetail;
import com.putao.wd.model.ReplyHeaderInfo;
import com.putao.wd.model.ReplyLists;
import com.putao.wd.pt_companion.adapter.CommentReplyAdapter;
import com.putao.wd.pt_companion.adapter.ReplyListsAdapter;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.start.comment.EmojiFragment;
import com.putao.wd.start.comment.adapter.EmojiFragmentAdapter;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.KeyboardUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.emoji.Emoji;
import com.sunnybear.library.view.emoji.EmojiEditText;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/11.
 */
public class ArticlesDetailActivity extends PTWDActivity implements View.OnClickListener {
    public static final String EVENT_COUNT_COOL = "event_count_cool";

    private String action_id = "1";
    private int page = 1;
    private boolean hasComment;
    private boolean isNoMore;

    @Bind(R.id.rl_comment_second)
    LinearLayout rl_comment_second;
    @Bind(R.id.iv_author_icon)
    ImageDraweeView iv_author_icon;
    @Bind(R.id.tv_author_name)
    TextView tv_author_name;
    @Bind(R.id.tv_author_time)
    TextView tv_author_time;

    @Bind(R.id.rv_others_comment)
    BasicRecyclerView rv_others_comment;

    @Bind(R.id.ll_comment_edit)
    LinearLayout ll_comment_edit;
    @Bind(R.id.iv_upload_pic)
    ImageDraweeView iv_upload_pic;
    @Bind(R.id.vp_emojis)
    ViewPager vp_emojis;
    @Bind(R.id.et_msg)
    EmojiEditText et_msg;
    @Bind(R.id.tv_send)
    TextView tv_send;

    private String mWd_mid;
    private String mSid;
    private String mComment_id;
    private int mPage = 1;


    public final static String COOL = "CommentCool";//是否赞过

    CommentReplyAdapter mCommentReplyAdapter;
    ReplyListsAdapter mReplyListsAdapter;
    private boolean isShowEmoji = false;
    private int mPosition;
    private int mMinLenght;
    private boolean isReply;
    private SharePopupWindow mSharePopupWindow;//分享弹框

    private boolean is_pic = false;// 是否可以发表图片
    private boolean is_comment = false;// 是否可以评论
    private boolean is_becommented = false;//是否可以对评论进行回复

    private Map<String, String> emojiMap;
    private List<Emoji> emojis;

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
            mComment_id = data.getString("pcid");
        }
        if (StringUtils.isEmpty(mWd_mid) || StringUtils.isEmpty(mSid) || StringUtils.isEmpty(mComment_id)) {
            finish();
            return;
        }

        emojiMap = mApp.getEmojis();
        emojis = new ArrayList<>();
        if (emojiMap == null)
            emojiMap = new HashMap<>();
        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            emojis.add(new Emoji(entry.getKey(), entry.getValue()));
        }
        vp_emojis.setAdapter(new EmojiFragmentAdapter(getSupportFragmentManager(), emojis, 20));

        getNewCommentData();
        addListener();
    }

    private void getNewCommentData() {
        //
        networkRequest(CompanionApi.getCompanyArticleComment(mWd_mid, mSid, mComment_id, String.valueOf(mPage)), new SimpleFastJsonCallback<CompanionCommentDetail>(CompanionCommentDetail.class, loading) {
            @Override
            public void onSuccess(String url, CompanionCommentDetail result) {
                is_pic = result.is_pic();
                is_comment = result.is_comment();
                is_becommented = result.is_becommented();
                refreshView();

                iv_author_icon.setImageURL(result.getComment().getHead_img());
                tv_author_name.setText(result.getComment().getNick_name());
                tv_author_time.setText(DateUtils.secondToDate(Integer.valueOf(result.getComment().getRelease_time()), "yyyy/MM/dd HH:mm"));

                //设置布局管理器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ArticlesDetailActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mCommentReplyAdapter = new CommentReplyAdapter(mContext, result.getComment().getLike_lists());

                ReplyHeaderInfo replyHeaderInfo = new ReplyHeaderInfo();
                replyHeaderInfo.setContent(result.getComment().getContent());
                replyHeaderInfo.setCount_comments(result.getComment().getCount_comments());
                replyHeaderInfo.setCount_likes(result.getComment().getCount_likes());
                if (result.getComment().getLike_lists() != null && result.getComment().getLike_lists().size() > 0) {
                    replyHeaderInfo.setLikes_icon(result.getComment().getLike_lists());
                } else {
                    replyHeaderInfo.setLikes_icon(null);
                }
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

    private void refreshView() {
        if (is_comment)
            ll_comment_edit.setVisibility(View.VISIBLE);
        else
            ll_comment_edit.setVisibility(View.GONE);
        if (is_pic)
            iv_upload_pic.setVisibility(View.VISIBLE);
        else
            iv_upload_pic.setVisibility(View.GONE);
        if (is_becommented) ;
    }

    private void addListener() {
        et_msg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && mMinLenght > et_msg.length()) {
                    et_msg.setText("");
                    isReply = false;
                    mMinLenght = 0;
                    return true;
                }
                return false;
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = et_msg.getText().toString();
                if (msg.trim().isEmpty()) {
                    ToastUtils.showToastShort(mContext, "评论不能为空");
                    return;
                }
                networkRequest(ExploreApi.addSecondComment(mWd_mid, msg, mSid, mComment_id),
                        new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                resetMsg();
                                Logger.i("评论与回复提交成功");
                                getNewCommentData();
                            }

                            @Override
                            public void onFailure(String url, int statusCode, String msg) {
                                super.onFailure(url, statusCode, msg);
                                ToastUtils.showToastShort(mContext, "评论发送失败，请检查您的网络");
                            }
                        });
            }
        });
    }

    private void resetMsg() {
        isReply = false;
        et_msg.setText("");
        mMinLenght = 0;
        vp_emojis.setVisibility(View.GONE);
    }


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
                    mReplyListsAdapter.addAll(comments);
                }
                if (result != null) { //result.getCurrent_page() != result.getTotal_page()
                    hasComment = true;
                    page++;
                } else {
                    hasComment = false;
                }
                loading.dismiss();
            }
        });
    }


    //点赞提交
    @Subcriber(tag = ReplyListsAdapter.EVENT_COMMIT_COOL)
    public void eventClickCool(final String str) {
        networkRequest(CompanionApi.addCommentPraise(mWd_mid, mSid, mComment_id), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                getNewCommentData();
            }
        });
    }

//    //点赞提交
//    @Subcriber(tag = CommentAdapter.EVENT_COMMIT_COOL)
//    public void eventClickCool(final int currPosition) {
//        final ArticleDetailComment comment = adapter.getItem(currPosition);
//        networkRequest(ExploreApi.addArticleLike(wd_mid, comment.getComment_id(), sid),
//                new SimpleFastJsonCallback<String>(String.class, loading) {
//                    @Override
//                    public void onSuccess(String url, String result) {
////                        adapter.notifyItemChanged(currPosition);
//                        mDiskFileCacheHelper.put(COOL + comment.getComment_id(), "true");
//                        EventBusHelper.post(true, EVENT_COUNT_COOL);
//                    }
//                });
//    }

    @Subcriber(tag = EmojiFragment.EVENT_CLICK_EMOJI)
    public void eventClickEmoji(Emoji emoji) {
        et_msg.append(emoji.getName());
    }

    @Subcriber(tag = EmojiFragment.EVENT_DELETE_EMOJI)
    public void eventDeleteEmoji(Emoji emoji) {
        et_msg.delete();
    }

    @OnClick({R.id.tv_emojis, R.id.et_msg})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_emojis://点击表情栏
                KeyboardUtils.closeKeyboard(mContext, et_msg);
                isShowEmoji = isShowEmoji ? false : true;
                vp_emojis.setVisibility(isShowEmoji ? View.VISIBLE : View.GONE);
                break;
            case R.id.et_msg://点击文本输入框
                isShowEmoji = false;
                vp_emojis.setVisibility(View.GONE);
                break;

        }
    }

}
