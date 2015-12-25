package com.putao.wd.me.message;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.me.message.adapter.ReplyAdapter;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import butterknife.Bind;

/**
 * 回复--消息中心
 * Created by yanghx on 2015/12/24.
 */
public class ReplyFragment extends BasicFragment {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;//回复列表
    @Bind(R.id.rl_no_message)
    RelativeLayout rl_no_message;

    private ReplyAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        adapter = new ReplyAdapter(mActivity, null);
        rv_content.setAdapter(adapter);
        addListener();
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
