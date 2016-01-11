package com.putao.wd.start.comment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.SelectPopupWindow;
import com.putao.wd.model.Comment;
import com.putao.wd.model.CommentList;
import com.putao.wd.model.UserInfo;
import com.putao.wd.start.comment.adapter.CommentAdapter;
import com.putao.wd.start.comment.adapter.EmojiFragmentAdapter;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.emoji.Emoji;
import com.sunnybear.library.view.emoji.EmojiEditText;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 评论页
 * Created by yanghx on 2015/12/7.
 */
public class CommentActivity extends PTWDActivity implements View.OnClickListener {
    public static final String EVENT_COUNT_COMMENT = "event_count_comment";
    public static final String EVENT_COUNT_COOL = "event_count_cool";

    @Bind(R.id.rl_main)
    RelativeLayout rl_main;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.ll_comment_edit)
    LinearLayout ll_comment_edit;
    @Bind(R.id.vp_emojis)
    ViewPager vp_emojis;
    @Bind(R.id.et_msg)
    EmojiEditText et_msg;

    private SelectPopupWindow mSelectPopupWindow;
    private CommentAdapter adapter;
    private Map<String, String> emojiMap;
    private List<Emoji> emojis;
    private String action_id;
    private boolean isShowEmoji = false;
    private int position;
    private boolean isReply;
    private boolean hasComment;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        adapter = new CommentAdapter(this, null);
        rv_content.setAdapter(adapter);
        Bundle bundle = getIntent().getExtras();
        action_id = bundle.getString("action_id");
        getCommentList();
        addListener();

        emojiMap = GlobalApplication.getEmojis();
        emojis = new ArrayList<>();
        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            emojis.add(new Emoji(entry.getKey(), entry.getValue()));
        }
        vp_emojis.setAdapter(new EmojiFragmentAdapter(getSupportFragmentManager(), emojis, 20));

        mSelectPopupWindow = new SelectPopupWindow(mContext) {
            @Override
            public void onFirstClick(View v) {
                //删除评论
                String comment_id = adapter.getItem(position).getComment_id();
                networkRequest(StartApi.commentRemove(comment_id), new SimpleFastJsonCallback<String>(String.class, loading) {

                    @Override
                    public void onSuccess(String url, String result) {
                        getCommentList();
                        EventBusHelper.post(false, EVENT_COUNT_COMMENT);
                    }
                });
            }

            @Override
            public void onSecondClick(View v) {
                et_msg.setText("");
            }
        };
        mSelectPopupWindow.tv_first.setText("删除");
        mSelectPopupWindow.tv_second.setText("回复");
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 刷新方法
     */
    private void refresh() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCommentList();
            }
        });
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        refresh();

        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getCommentList();
            }
        });
        rv_content.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Serializable serializable, int position) {
                mSelectPopupWindow.show(rl_main);

            }
        });
    }

    @OnClick({R.id.tv_emojis, R.id.tv_send})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_emojis://点击表情栏
                isShowEmoji = isShowEmoji ? false : true;
                vp_emojis.setVisibility(isShowEmoji ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_send://点击发送
                if (isReply) {
                    Comment comment = adapter.getItem(position);
                    String msg = et_msg.getText().toString();
                    networkRequest(StartApi.commentAdd(action_id, comment.getUser_name(), msg, "REPLY", comment.getComment_id(), comment.getUser_profile_photo()),
                            new SimpleFastJsonCallback<String>(String.class, loading) {
                                @Override
                                public void onSuccess(String url, String result) {
                                    Logger.i("评论与回复提交成功");
                                    refreshCommentList();
                                    EventBusHelper.post(true, EVENT_COUNT_COMMENT);
                                }
                            });
                } else {
                    String msg = et_msg.getText().toString();
                    UserInfo userInfo = AccountHelper.getCurrentUserInfo();
                    networkRequest(StartApi.commentAdd(action_id, userInfo.getNick_name(), msg, "COMMENT", AccountHelper.getCurrentUid(), userInfo.getHead_img()),
                            new SimpleFastJsonCallback<String>(String.class, loading) {
                                @Override
                                public void onSuccess(String url, String result) {
                                    Logger.i("评论与回复提交成功");
                                    refreshCommentList();
                                    EventBusHelper.post(true, EVENT_COUNT_COMMENT);
                                }
                            });
                }
                isReply = false;
                et_msg.setText("");
                vp_emojis.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 刷新评论列表
     */
    private void refreshCommentList() {
        page = 1;
        rv_content.reset();
        networkRequest(StartApi.getCommentList(String.valueOf(page), action_id), new SimpleFastJsonCallback<CommentList>(CommentList.class, loading) {
            @Override
            public void onSuccess(String url, CommentList result) {
                if (result.getTotal_page() == 1 || result.getCurrent_page() != result.getTotal_page()) {
                    adapter.replaceAll(result.getComment());
                    hasComment = true;
                    rv_content.loadMoreComplete();
                    page++;
                } else {
                    rv_content.noMoreLoading();
                    hasComment = false;
                }
                loading.dismiss();
                ptl_refresh.refreshComplete();
            }
        });
    }

    /**
     * 获取评论列表
     */
    private void getCommentList() {
        networkRequest(StartApi.getCommentList(String.valueOf(page), action_id), new SimpleFastJsonCallback<CommentList>(CommentList.class, loading) {
            @Override
            public void onSuccess(String url, CommentList result) {
                Logger.i("活动评论列表请求成功");
                if (result.getComment() != null && result.getComment().size() > 0)
                    adapter.addAll(result.getComment());
                if (result.getCurrent_page() != result.getTotal_page()) {
                    hasComment = true;
                    rv_content.loadMoreComplete();
                    page++;
                } else {
                    rv_content.noMoreLoading();
                    hasComment = false;
                }
                loading.dismiss();
            }
        });
    }

    @Subcriber(tag = CommentAdapter.EVENT_COMMENT_EDIT)
    public void eventClickComment(int currPosition) {
        position = currPosition;
        Comment comment = adapter.getItem(position);
        String username = comment.getUser_name() + ": ";
        SpannableString ss = new SpannableString("回复 " + username);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_color_gray)), 0, username.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        et_msg.setText(ss);
        isReply = true;
    }

    //点赞提交
    @Subcriber(tag = CommentAdapter.EVENT_COMMIT_COOL)
    public void eventClickCool(int currPosition) {
        Comment comment = adapter.getItem(currPosition);
        networkRequest(StartApi.coolAdd(action_id, comment.getUser_name(), "COMMENT", comment.getComment_id(), comment.getUser_profile_photo()),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        getCommentList();
                        EventBusHelper.post(true, EVENT_COUNT_COOL);
                    }
                });
    }

    @Subcriber(tag = EmojiFragment.EVENT_CLICK_EMOJI)
    public void eventClickEmoji(Emoji emoji) {
        et_msg.append(emoji.getName());
    }

    @Subcriber(tag = EmojiFragment.EVENT_DELETE_EMOJI)
    public void eventDeleteEmoji(Emoji emoji) {
        et_msg.delete();
    }

}
