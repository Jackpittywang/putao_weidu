package com.putao.wd.start.comment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Comment;
import com.putao.wd.start.comment.adapter.CommentAdapter;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ListUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.emoji.Emoji;
import com.sunnybear.library.view.emoji.EmojiEditText;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 评论页
 * Created by yanghx on 2015/12/7.
 */
public class CommentActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
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

    private CommentAdapter adapter;
    private Map<String, String> emojiMap;
    private List<Emoji> emojis;
    private String action_id;
    private boolean isShowEmoji = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        adapter = new CommentAdapter(this, null);
        rv_content.setAdapter(adapter);
        refresh();
        getCommentList();
        addListener();

        emojiMap = GlobalApplication.getEmojis();
        emojis = new ArrayList<>();
        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            emojis.add(new Emoji(entry.getKey(), entry.getValue()));
        }

        vp_emojis.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                int start = position * 20;
                int end = position * 20 + 20 > emojis.size() ? emojis.size() : position * 20 + 20;
                List<Emoji> list = ListUtils.cutOutList(emojis, start, end);
                list.add(new Emoji("end"));
                return new EmojiFragment(list, R.drawable.btn_emoji_del_select);
            }

            @Override
            public int getCount() {
                return emojis.size() / 20 + 1;
            }
        });
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptl_refresh.refreshComplete();
                    }
                }, 3 * 1000);
            }
        });
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getCommentList();
                    }
                }, 3 * 1000);
            }
        });
    }

    private void getCommentList() {
        Bundle bundle = getIntent().getExtras();
        String action_id = bundle.getString("action_id");
        networkRequest(StartApi.getCommentList(action_id), new SimpleFastJsonCallback<ArrayList<Comment>>(Comment.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Comment> result) {
                Logger.i("活动评论列表请求成功");
                if (null != result) {
                    adapter.replaceAll(result);
                } else {
                    rv_content.noMoreLoading();
                }
                loading.dismiss();
            }
        });
    }

    @OnClick({R.id.tv_emojis, R.id.tv_send})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_emojis:
                isShowEmoji = isShowEmoji ? false : true;
                vp_emojis.setVisibility(isShowEmoji ? View.VISIBLE : View.GONE);
                break;
//            case R.id.tv_send:
//                tv_emoji.setText(et_msg.getText().toString());
//                break;
        }
    }

    //评论编辑及处理
    @Subcriber(tag = CommentAdapter.EVENT_COMMENT_EDIT)
    public void eventUseTime(int currPosition) {
        ll_comment_edit.setVisibility(View.VISIBLE);
    }

    @Subcriber(tag = EmojiFragment.EVENT_CLICK_EMOJI)
    public void eventClickEmoji(Emoji emoji) {
        et_msg.append(emoji.getName());
    }

    @Subcriber(tag = EmojiFragment.EVENT_DELETE_EMOJI)
    public void eventDeleteEmoji(Emoji emoji) {
        et_msg.delete();
    }

//    private List<CommentItem> getTestData() {
//        List<CommentItem> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            CommentItem commentItem = new CommentItem();
//            commentItem.setId(i + 1 + "");
//            commentItem.setStatus("0");
//            commentItem.setUsername("用户" + i);
//            commentItem.setTime("12:00");
//            commentItem.setComment("第" + i + "条评论" +
//                    "评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论");
//            commentItem.setComment_count("1234");
//            commentItem.setSupport_count("1000");
//            list.add(commentItem);
//        }
//        return list;
//    }

//    /**
//     * model暂无
//     *
//     * 评论与回复提交
//     * by yanghx
//     * @param user_id    用户ID
//     * @param action_id  活动ID
//     * @param msg        评论内容
//     * @param type       评论的类型
//     * @param comment_id 当评论类型为REPLY时comment_id是必须要传的
//     */
//    private void commentAdd(String user_id, String action_id, String msg, CommentType type, String comment_id) {
//        networkRequest(StartApi.commentAdd(user_id, action_id, msg, type, comment_id), new SimpleFastJsonCallback<ArrayList<String>>(String.class, loading) {
//            @Override
//            public void onSuccess(String url, ArrayList<String> result) {
//                Log.i("pt", "评论与回复提交成功");
//            }
//        });
//    }
//
//    /**
//     * model暂无
//     *
//     * 赞
//     * by yanghx
//     * @param user_id    用户ID
//     * @param action_id  活动ID
//     * @param type       赞的类型
//     * @param comment_id 当赞类型为COMMENT时comment_id 是必须要传的
//     */
//    private void coolAdd(String user_id, String action_id, CoolType type, String comment_id) {
//        networkRequest(StartApi.coolAdd(user_id, action_id, type, comment_id), new SimpleFastJsonCallback<ArrayList<String>>(String.class, loading) {
//            @Override
//            public void onSuccess(String url, ArrayList<String> result) {
//                Log.i("pt", "点赞成功");
//            }
//        });
//    }

}
