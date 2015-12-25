package com.putao.wd.me.message;

import android.os.Bundle;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import butterknife.Bind;

/**
 * 回复fragment
 * Created by yanghx on 2015/12/24.
 */
public class MsgReplyFragment extends BasicFragment {
    @Bind(R.id.rv_messages)
    LoadMoreRecyclerView rv_reply;//回复列表
    @Bind(R.id.tv_text)
    TextView tv_text;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        tv_text.setText("回复");
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
