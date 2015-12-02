package com.putao.wd.me.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import butterknife.Bind;

/*
**消息-赞
**create by wangou
 */
public class MessagePraiseFragment extends BasicFragment {
    @Bind(R.id.tv_nomore)
    TextView tv_nomore;//没有更多
    @Bind(R.id.brv_msgpraise)
    BasicRecyclerView brv_msgpraise;
    @Bind(R.id.ll_msgpraise)
    LinearLayout ll_msgpraise;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_praise;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        if(1==0)//“赞”列表有数据
            ll_msgpraise.setVisibility(View.VISIBLE);
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