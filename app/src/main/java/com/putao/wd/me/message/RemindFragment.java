package com.putao.wd.me.message;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.me.message.adapter.PraiseAdapter;
import com.putao.wd.me.message.adapter.RemindAdapter;
import com.putao.wd.model.Praise;
import com.putao.wd.model.PraiseDetail;
import com.putao.wd.model.Remind;
import com.putao.wd.model.RemindDetail;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * 赞--消息中心
 * Created by yanghx on 2015/12/24.
 */
public class RemindFragment extends BasicFragment {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;//赞列表
    @Bind(R.id.rl_no_message)
    RelativeLayout rl_no_message;
    @Bind(R.id.tv_message_empty)
    TextView tv_message_empty;

    private RemindAdapter adapter;

    private int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        Logger.d("MessageCenterActivity", "PraiseFragment启动");
        tv_message_empty.setText("还没有提醒");
        adapter = new RemindAdapter(mActivity, null);
        rv_content.setAdapter(adapter);
        addListener();
        getRemindList();
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getRemindMore();
            }
        });
    }

    /**
     * 获取通知
     */
    private void getRemindList() {
        currentPage = 1;
        networkRequest(StartApi.getRemindList(String.valueOf(currentPage)),
                new SimpleFastJsonCallback<Remind>(Remind.class, loading) {
                    @Override
                    public void onSuccess(String url, Remind result) {
                        List<RemindDetail> details = result.getList();
                        if (details != null && details.size() > 0) {
                            rv_content.setVisibility(View.VISIBLE);
                            rl_no_message.setVisibility(View.GONE);
                            adapter.replaceAll(details);
                        } else {
                            rv_content.setVisibility(View.GONE);
                            rl_no_message.setVisibility(View.VISIBLE);
                        }
                        if (result.getCurrent_page() != result.getTotal_page() && result.getTotal_page() != 0) {
                            currentPage++;
                            rv_content.loadMoreComplete();
                        } else rv_content.noMoreLoading();
                        loading.dismiss();
                    }
                });
    }

    /**
     * 获取通知
     */
    private void getRemindMore() {
        networkRequest(StartApi.getRemindList(String.valueOf(currentPage)),
                new SimpleFastJsonCallback<Remind>(Remind.class, loading) {
                    @Override
                    public void onSuccess(String url, Remind result) {
                        List<RemindDetail> details = result.getList();
                        if (details != null && details.size() > 0) {
                            adapter.addAll(details);
                        }
                        rv_content.loadMoreComplete();
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
