package com.putao.wd.me.message;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.me.message.adapter.PraiseAdapter;
import com.putao.wd.model.Praise;
import com.putao.wd.model.PraiseDetail;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * 赞--消息中心
 * Created by yanghx on 2015/12/24.
 */
public class PraiseFragment extends BasicFragment {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;//赞列表
    @Bind(R.id.rl_no_message)
    RelativeLayout rl_no_message;

    private PraiseAdapter adapter;

    private int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        adapter = new PraiseAdapter(mActivity, null);
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
        networkRequest(StartApi.getPraiseList(String.valueOf(currentPage)),
                new SimpleFastJsonCallback<Praise>(Praise.class, loading) {
                    @Override
                    public void onSuccess(String url, Praise result) {
                        List<PraiseDetail> details = result.getLikedList();
                        if (details != null && details.size() > 0 && rl_no_message.getVisibility() == View.VISIBLE)
                            rl_no_message.setVisibility(View.GONE);
                        adapter.addAll(details);
                        if (result.getTotal_page() != result.getTotal_page() && result.getTotal_page() != 0) {
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