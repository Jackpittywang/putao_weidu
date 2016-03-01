package com.putao.wd.me.message;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.me.message.adapter.ReplyAdapter;
import com.putao.wd.model.Reply;
import com.putao.wd.model.ReplyDetail;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * 回复--消息中心
 * Created by yanghx on 2015/12/24.
 */
public class ReplyFragment extends BasicFragment {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;//回复列表
    @Bind(R.id.tv_message_empty)
    TextView tv_message_empty;

    @Bind(R.id.rl_no_message)
    RelativeLayout rl_no_message;

    private ReplyAdapter adapter;

    private int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        Logger.d("MessageCenterActivity","ReplyFragment启动");
        tv_message_empty.setText("还没有回复");
        adapter = new ReplyAdapter(mActivity, null);
        rv_content.setAdapter(adapter);
        addListener();

        getNotifyList();
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getNotifyList();
            }
        });
    }

    /**
     * 获取赞
     */
    private void getNotifyList() {
        networkRequest(StartApi.getReplyList(String.valueOf(currentPage)),
                new SimpleFastJsonCallback<Reply>(Reply.class, loading) {
                    @Override
                    public void onSuccess(String url, Reply result) {
                        List<ReplyDetail> details = result.getReply();
                        if (details != null && details.size() > 0 && rl_no_message.getVisibility() == View.VISIBLE) {
                            rl_no_message.setVisibility(View.GONE);
                            rv_content.setVisibility(View.VISIBLE);
                            adapter.addAll(details);
                        }else{
                            rl_no_message.setVisibility(View.VISIBLE);
                            rv_content.setVisibility(View.GONE);
                        }
                        if (result.getCurrent_page() != result.getTotal_page() && result.getTotal_page() != 0) {
                            currentPage++;
                            rv_content.loadMoreComplete();
                        } else rv_content.noMoreLoading();
                        loading.dismiss();
                    }
                });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
