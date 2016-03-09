package com.putao.wd.created;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.CreateApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.SelectPopupWindow;
import com.putao.wd.created.adapter.CreateCommentAdapter;
import com.putao.wd.model.CreateComment;
import com.putao.wd.model.CreateComments;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.comment.EmojiFragment;
import com.putao.wd.start.comment.adapter.CommentAdapter;
import com.putao.wd.start.comment.adapter.EmojiFragmentAdapter;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.emoji.Emoji;
import com.sunnybear.library.view.emoji.EmojiEditText;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 评论页
 * Created by yanghx on 2015/12/7.
 */
public class CreateCommentActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    public static final String EVENT_COUNT_COMMENT = "event_count_comment";
    public static final String EVENT_COUNT_COOL = "event_count_cool";
    public static final String EVENT_ADD_CREAT_COMMENT = "event_add_creat_comment";
    public static final String EVENT_DELETE_CREAT_COMMENT = "event_delete_creat_comment";

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
    private CreateCommentAdapter adapter;
    private Map<String, String> emojiMap;
    private List<Emoji> emojis;
    private String action_id;
    private boolean isShowEmoji = false;
    private int mPosition;
    private int mSuperPosition;
    private int mMinLenght;
    private boolean isReply;
    private boolean hasComment;
    private int page = 1;
    public final static String COOL = "CommentCool";//是否赞过
    public final static String POSITION = "position";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mMinLenght = 0;
        adapter = new CreateCommentAdapter(this, null);
        rv_content.setAdapter(adapter);
        action_id = args.getString(ActionsDetailActivity.BUNDLE_ACTION_ID);
        mSuperPosition = args.getInt(CreateBasicDetailActivity.POSITION);
        refreshCommentList();
        addListener();

        emojiMap = mApp.getEmojis();
        emojis = new ArrayList<>();
        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            emojis.add(new Emoji(entry.getKey(), entry.getValue()));
        }
        vp_emojis.setAdapter(new EmojiFragmentAdapter(getSupportFragmentManager(), emojis, 20));

        mSelectPopupWindow = new SelectPopupWindow(mContext) {
            @Override
            public void onFirstClick(View v) {
                final CreateComment item = adapter.getItem(mPosition);
                if (AccountHelper.getCurrentUid().equals(item.getUid())) {
                    //删除评论
                    String comment_id = item.getId();
                    networkRequest(CreateApi.deleteComment(comment_id), new SimpleFastJsonCallback<String>(String.class, loading) {

                        @Override
                        public void onSuccess(String url, String result) {
//                            adapter.delete(item);
                            refreshCommentList();
                            EventBusHelper.post(mSuperPosition, EVENT_DELETE_CREAT_COMMENT);
                        }
                    });
                } else {
                    ToastUtils.showToastShort(mContext, "感谢您的举报，我们会尽快处理");
                }
            }

            @Override
            public void onSecondClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reply();
                    }
                }, 200);
            }
        };
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
        rv_content.setOnItemClickListener(new OnItemClickListener<CreateComment>() {
            @Override
            public void onItemClick(CreateComment comment, int position) {
                mPosition = adapter.getItems().indexOf(comment);
                if (AccountHelper.getCurrentUid().equals(comment.getUid())) {
                    mSelectPopupWindow.tv_first.setText("删除");
                    mSelectPopupWindow.tv_first.setTextColor(0xff6666CC);
                } else {
                    mSelectPopupWindow.tv_first.setText("举报");
                    mSelectPopupWindow.tv_first.setTextColor(0xffcc0000);
                }
                mSelectPopupWindow.show(rl_main);
            }
        });
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
    }

    private void reply() {
        CreateComment comment = adapter.getItem(mPosition);
        String username = comment.getUsername() + ": ";
        SpannableString ss = new SpannableString("回复 " + username);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_color_gray)), 0, username.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mMinLenght = ss.length();
        et_msg.setText(ss);
        et_msg.setSelection(mMinLenght);
        et_msg.setFocusableInTouchMode(true);
        et_msg.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) et_msg.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(et_msg, 0);
        isReply = true;
    }


    @OnClick({R.id.tv_emojis, R.id.tv_send, R.id.et_msg})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_emojis://点击表情栏
                isShowEmoji = isShowEmoji ? false : true;
                vp_emojis.setVisibility(isShowEmoji ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_send://点击发送
                if (isReply) {
                    CreateComment comment = adapter.getItem(mPosition);
                    String msg = et_msg.getText().toString();
                    msg = msg.substring(msg.lastIndexOf(":") + 2);
                    networkRequest(CreateApi.addComment(msg, action_id, comment.getId(), comment.getUid()),
                            new SimpleFastJsonCallback<String>(String.class, loading) {
                                @Override
                                public void onSuccess(String url, String result) {
                                    Logger.i("评论与回复提交成功");
                                    refreshCommentList();
                                    EventBusHelper.post(mSuperPosition, EVENT_ADD_CREAT_COMMENT);
                                }
                            });
                } else {
                    String msg = et_msg.getText().toString();
                    networkRequest(CreateApi.addComment(msg, action_id),
                            new SimpleFastJsonCallback<String>(String.class, loading) {
                                @Override
                                public void onSuccess(String url, String result) {
                                    Logger.i("评论与回复提交成功");
                                    refreshCommentList();
                                    EventBusHelper.post(mSuperPosition, EVENT_ADD_CREAT_COMMENT);
                                }
                            });
                }
                isReply = false;
                et_msg.setText("");
                mMinLenght = 0;
                vp_emojis.setVisibility(View.GONE);
                break;
            case R.id.et_msg://点击文本输入框
                isShowEmoji = false;
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
        networkRequest(CreateApi.getCommentList(page, action_id), new SimpleFastJsonCallback<CreateComments>(CreateComments.class, loading) {
            @Override
            public void onSuccess(String url, CreateComments result) {
                if (result.getData().size() > 0) {
                    List<CreateComment> comments = result.getData();
                    if (comments != null && comments.size() > 0) {
                        checkLiked(comments);
                        adapter.replaceAll(comments);
                    }
                    hasComment = true;
                    rv_content.loadMoreComplete();
                    page++;
                }
                if (result.getCurrentPage() >= result.getTotalPage()) {
                    rv_content.noMoreLoading();
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
        networkRequest(CreateApi.getCommentList(page, action_id), new SimpleFastJsonCallback<CreateComments>(CreateComments.class, loading) {
            @Override
            public void onSuccess(String url, CreateComments result) {
                Logger.i("活动评论列表请求成功");
                Logger.i(url);
                List<CreateComment> comments = result.getData();
                if (comments != null && comments.size() > 0) {
                    checkLiked(comments);
                    adapter.addAll(comments);
                }
                if (result.getCurrentPage() < result.getTotalPage()) {
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

    private void checkLiked(List<CreateComment> comments) {
        int i = 0;
        for (CreateComment comment : comments) {
            if (Boolean.parseBoolean(mDiskFileCacheHelper.getAsString(COOL + comment.getId())))
                comments.get(i).setLike_status(true);
            i++;
        }
    }

    @Subcriber(tag = CommentAdapter.EVENT_COMMENT_EDIT)
    public void eventClickComment(int currPosition) {
        mPosition = currPosition;
        reply();
    }

    //点赞提交
    @Subcriber(tag = CommentAdapter.EVENT_COMMIT_COOL)
    public void eventClickCool(final int currPosition) {
        final CreateComment comment = adapter.getItem(currPosition);
        networkRequest(CreateApi.addCommentLike(action_id, comment.getId()),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
//                        adapter.notifyItemChanged(currPosition);
                        mDiskFileCacheHelper.put(COOL + comment.getId(), "true");
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
