package com.putao.wd.me.message;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;

import butterknife.Bind;

/**
 * 消息中心
 * Created by wangou on 2015/12/1.
 */
public class MessageCenterActivity extends PTWDActivity implements TitleBar.OnTitleItemSelectedListener {
    @Bind(R.id.rv_messages)
    LoadMoreRecyclerView rv_messages;//消息列表
    @Bind(R.id.ll_title_bar)
    TitleBar ll_title_bar;//顶部导航菜单

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        addListener();
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        rv_messages.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });
        ll_title_bar.setOnTitleItemSelectedListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        switch (item.getId()) {
            case R.id.ll_all://通知
                //ToastUtils.showToastLong(this, "通知");
                break;
            case R.id.ll_ing://回复
                //ToastUtils.showToastLong(this, "回复");
                break;
            case R.id.ll_finish://赞
                //ToastUtils.showToastLong(this, "赞");
                break;
        }
    }
}
