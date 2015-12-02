package com.putao.wd.me.message;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.MessageNotifyItem;
import com.putao.wd.me.message.adapter.MsgNotifyAdapter;
import com.putao.wd.me.message.adapter.MsgReplyAdapter;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.ll_msgreply)
    LinearLayout ll_msgreply;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_reply;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

        if (initDataTest().size() != 0){//“赞”列表有数据
            ll_msgreply.setVisibility(View.VISIBLE);
            MsgReplyAdapter msgReplyAdapter = new MsgReplyAdapter(mActivity, initDataTest());
            brv_msgreply.setAdapter(msgReplyAdapter);
        }
        else
            tv_nomore.setVisibility(View.VISIBLE);
        }
    private List<String> initDataTest(){
        List<String> list=new ArrayList<>();
        for(int i=0;i<3;i++) {
            list.add("title"+i);
        }
        return list;
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