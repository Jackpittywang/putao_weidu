package com.putao.wd.me.message;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import butterknife.Bind;

/*
**消息-回复
**create by wangou
 */
public class MessageReplyFragment extends BasicFragment {
    @Bind(R.id.tv_nomore)
    TextView tv_nomore;//没有更多
    @Bind(R.id.brv_msgreply)
    BasicRecyclerView brv_msgreply;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_reply;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        if(1==0)//“赞”列表有数据
            brv_msgreply.setVisibility(View.VISIBLE);
        else
            tv_nomore.setVisibility(View.VISIBLE);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}