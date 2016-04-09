package com.putao.wd.pt_me.message;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.created.CreateBasicDetailActivity;
import com.putao.wd.explore.ExploreMoreDetailActivity;
import com.putao.wd.model.Reply;
import com.putao.wd.pt_me.message.adapter.RemindAdapter;
import com.putao.wd.model.Remind;
import com.putao.wd.model.RemindDetail;
import com.putao.wd.pt_store.product.ProductDetailActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;
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

    private int mPage;

    private RemindAdapter adapter;
    public static final String BUNDLE_REMIND_PRODUCTID = "bundle_remind_productid";

    private int currentPage = 1;

    private boolean isPrepared;

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible){
            return;
        }

        isPrepared = false;

        getRemindList();
    }

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

        isPrepared = true;
        addListener();
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
        loading.show();
        mPage=1;
        networkRequest(StartApi.getRemindList(String.valueOf(currentPage)),
                new SimpleFastJsonCallback<ArrayList<Remind>>(Remind.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Remind> result) {
                        if (result != null && result.size() > 0) {
                            rv_content.setVisibility(View.VISIBLE);
                            rl_no_message.setVisibility(View.GONE);
                            adapter.replaceAll(result);
                        } else {
                            rv_content.setVisibility(View.GONE);
                            rl_no_message.setVisibility(View.VISIBLE);
                        }
//                        if (result.getCurrent_page() != result.getTotal_page() && result.getTotal_page() != 0) {
//                            currentPage++;
//                            rv_content.loadMoreComplete();
//                        } else rv_content.noMoreLoading();

                        checkLoadMoreComplete(result);
                        loading.dismiss();
                    }
                });
    }

    /**
     * 获取通知
     */
    private void getRemindMore() {
        networkRequest(StartApi.getRemindList(String.valueOf(mPage)),
                new SimpleFastJsonCallback<ArrayList<Remind>>(Remind.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Remind> result) {
                        if (result != null && result.size() > 0) {
                            adapter.addAll(result);
                        }
                        rv_content.loadMoreComplete();
//                        if (result != null) {
//                            currentPage++;
//                            rv_content.loadMoreComplete();
//                        } else rv_content.noMoreLoading();
                        checkLoadMoreComplete(result);
                        loading.dismiss();
                    }
                });
    }

    private void checkLoadMoreComplete(ArrayList<Remind> result) {
        if (result == null)
            rv_content.noMoreLoading();
        else mPage++;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Subcriber(tag = RemindAdapter.EVENT_REMIND)
    private void setBlur(RemindDetail remindDetail) {
        String link_type = remindDetail.getLink_type();
        Bundle bundle = new Bundle();
        switch (link_type) {
            case "product"://精选商品
                bundle.putSerializable(BUNDLE_REMIND_PRODUCTID, remindDetail.getUrl());
                bundle.putBoolean(ProductDetailActivity.BUNDLE_IS_REMIND, true);
                startActivity(ProductDetailActivity.class, bundle);
                break;
            case "explore"://探索文章
                bundle.putString(ExploreMoreDetailActivity.ARTICLE_ID, remindDetail.getUrl());
                startActivity(ExploreMoreDetailActivity.class, bundle);
                break;
            case "idea"://创想
                bundle.putString(CreateBasicDetailActivity.EVENT_EXPLORER_ID, remindDetail.getUrl());
                bundle.putBoolean(CreateBasicDetailActivity.EVENT_IS_REMIND, true);
                startActivity(CreateBasicDetailActivity.class, bundle);
                break;
        }

    }
}


