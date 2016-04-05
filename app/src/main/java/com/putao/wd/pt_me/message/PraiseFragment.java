package com.putao.wd.pt_me.message;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.pt_me.message.adapter.PraiseAdapter;
import com.putao.wd.model.Praise;
import com.putao.wd.model.PraiseDetail;
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
public class PraiseFragment extends BasicFragment {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;//赞列表
    @Bind(R.id.rl_no_message)
    RelativeLayout rl_no_message;
    @Bind(R.id.tv_message_empty)
    TextView tv_message_empty;

    private PraiseAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        Logger.d("MessageCenterActivity", "PraiseFragment启动");
        tv_message_empty.setText("还没有赞");
        adapter = new PraiseAdapter(mActivity, null);
        rv_content.setAdapter(adapter);
        getNotifyList();

        addListener();
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //getNotifyList();
                networkRequest(StartApi.getPraiseList(String.valueOf(mPage)),
                        new SimpleFastJsonCallback<Praise>(Praise.class, loading) {
                            @Override
                            public void onSuccess(String url, Praise result) {
                                adapter.addAll(result.getLike());
                                rv_content.loadMoreComplete();
                                checkLoadMoreComplete(result.getCurrent_page(), result.getTotal_page());
                                loading.dismiss();
                            }
                        });
            }
        });
    }
    private int mPage;
    /**
     * 获取赞
     */
    private void getNotifyList() {
        mPage = 1;
        loading.show();
        //String.valueOf(currentPage)
        networkRequest(StartApi.getPraiseList(String.valueOf(mPage)),
                new SimpleFastJsonCallback<Praise>(Praise.class, loading) {
                    @Override
                    public void onSuccess(String url, Praise result) {
                        List<PraiseDetail> details = result.getLike();
                        if (details != null && details.size() > 0 && rl_no_message.getVisibility() == View.VISIBLE) {
                            rl_no_message.setVisibility(View.GONE);
                            rv_content.setVisibility(View.VISIBLE);
                            adapter.replaceAll(details);
                        } else {
                            rl_no_message.setVisibility(View.VISIBLE);
                            rv_content.setVisibility(View.GONE);
                        }
                        /*if (result.getTotal_page() != result.getTotal_page() && result.getTotal_page() != 0) {
                            currentPage++;
                            rv_content.loadMoreComplete();
                        } else rv_content.noMoreLoading();*/
                        checkLoadMoreComplete(result.getCurrent_page(), result.getTotal_page());
                        loading.dismiss();
                    }
                });
    }


    private void checkLoadMoreComplete(int currentPage, int totalPage) {
        if (currentPage == totalPage)
            rv_content.noMoreLoading();
        else mPage++;
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

}
