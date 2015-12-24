package com.putao.wd.me.message;

import android.os.Bundle;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import butterknife.Bind;

/**
 * 赞fragment
 * Created by yanghx on 2015/12/24.
 */
public class MsgPraiseFragment extends BasicFragment {
    @Bind(R.id.rv_messages)
    LoadMoreRecyclerView rv_praise;//赞列表
    @Bind(R.id.tv_text)
    TextView tv_text;
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        tv_text.setText("赞");
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        rv_praise.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });
    }


}
