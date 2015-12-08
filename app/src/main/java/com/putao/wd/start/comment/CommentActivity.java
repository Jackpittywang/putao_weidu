package com.putao.wd.start.comment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.CommentItem;
import com.putao.wd.start.comment.adapter.CommentAdapter;
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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        adapter = new CommentAdapter(this, getTestData());
        rv_content.setAdapter(adapter);
        refresh();
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
                        rv_content.noMoreLoading();
                    }
                }, 3 * 1000);
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    private List<CommentItem> getTestData() {
        List<CommentItem> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CommentItem commentItem = new CommentItem();
            commentItem.setId(i + 1 + "");
            commentItem.setStatus("0");
            commentItem.setUsername("用户" + i);
            commentItem.setTime("12:00");
            commentItem.setComment("第" + i + "条评论" +
                    "评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论");
            commentItem.setComment_count("1234");
            commentItem.setSupport_count("1000");
            list.add(commentItem);
        }
        return list;
    }


}
