package com.putao.wd.start.comment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.CommentItem;
import com.putao.wd.model.Comment;
import com.putao.wd.model.CommentType;
import com.putao.wd.model.CoolType;
import com.putao.wd.model.MapInfo;
import com.putao.wd.start.comment.adapter.CommentAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 评论页
 * Created by yanghx on 2015/12/7.
 */
public class CommentActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;

    private CommentAdapter adapter;
    private String action_id;

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

    @Override
    public void onClick(View v) {
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

    /**
     * model暂无
     *
     * 评论与回复提交
     * by yanghx
     * @param user_id    用户ID
     * @param action_id  活动ID
     * @param msg        评论内容
     * @param type       评论的类型
     * @param comment_id 当评论类型为REPLY时comment_id是必须要传的
     */
    private void commentAdd(String user_id, String action_id, String msg, CommentType type, String comment_id) {
        networkRequest(StartApi.commentAdd(user_id, action_id, msg, type, comment_id), new SimpleFastJsonCallback<ArrayList<String>>(String.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<String> result) {
                Log.i("pt", "评论与回复提交成功");
            }
        });
    }

    /**
     * model暂无
     *
     * 赞
     * by yanghx
     * @param user_id    用户ID
     * @param action_id  活动ID
     * @param type       赞的类型
     * @param comment_id 当赞类型为COMMENT时comment_id 是必须要传的
     */
    private void coolAdd(String user_id, String action_id, CoolType type, String comment_id) {
        networkRequest(StartApi.coolAdd(user_id, action_id, type, comment_id), new SimpleFastJsonCallback<ArrayList<String>>(String.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<String> result) {
                Log.i("pt", "点赞成功");
            }
        });
    }

}
