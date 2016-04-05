package com.putao.wd.pt_me.message;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.pt_me.message.adapter.NotifyAdapter;
import com.putao.wd.model.Notify;
import com.putao.wd.model.NotifyDetail;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * 通知--消息中心
 * Created by yanghx on 2015/12/24.
 */
public class NotifyFragment extends BasicFragment {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;//通知列表
    @Bind(R.id.rl_no_message)
    RelativeLayout rl_no_message;
    @Bind(R.id.tv_message_empty)
    TextView tv_message_empty;

    private NotifyAdapter adapter;

    private int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        Logger.d("MessageCenterActivity", "NotifyFragment启动");
        tv_message_empty.setText("还没有消息通知");
        adapter = new NotifyAdapter(mActivity, null);
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
                getNotifyMore();
            }
        });
    }

    /**
     * 获取用户列表
     */
    private void getNotifyList() {
        loading.show();
        networkRequest(StartApi.getNotifyList(String.valueOf(currentPage)),
                new SimpleFastJsonCallback<Notify>(Notify.class, loading) {
                    @Override
                    public void onSuccess(String url, Notify result) {
                        List<NotifyDetail> details = result.getData();
                        if (details != null && details.size() > 0 && rl_no_message.getVisibility() == View.VISIBLE) {
                            rl_no_message.setVisibility(View.GONE);
                            rv_content.setVisibility(View.VISIBLE);
                            adapter.replaceAll(details);
                        } else {
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
    /**
     * 获取用户列表
     */
    private void getNotifyMore() {
        networkRequest(StartApi.getNotifyList(String.valueOf(currentPage)),
                new SimpleFastJsonCallback<Notify>(Notify.class, loading) {
                    @Override
                    public void onSuccess(String url, Notify result) {
                        List<NotifyDetail> details = result.getData();
                        if (details != null && details.size() > 0) {
                            adapter.addAll(details);
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


//    @Subcriber(tag=NotifyAdapter.NOTIF_REMIND)
//    private void setNotif(NotifyDetail notifyDetail){
//        String type=notifyDetail.getType();
//        Bundle bundle=new Bundle();
//        switch (type){
//            case "9"://订单详情
//                startActivity(OrderDetailActivity.class);
//                break;
//        }
//    }
}
