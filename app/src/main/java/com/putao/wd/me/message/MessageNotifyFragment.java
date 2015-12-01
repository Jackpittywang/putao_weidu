package com.putao.wd.me.message;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.dto.MessageNotifyItem;
import com.putao.wd.me.message.adapter.MsgNotifyAdapter;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/*
**消息-通知
**create by wangou
 */
public class MessageNotifyFragment extends BasicFragment {
    @Bind(R.id.brv_msgnotify)
    BasicRecyclerView brv_msgnotify;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_notify;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        MsgNotifyAdapter msgNotifyAdapter=new MsgNotifyAdapter(mActivity,initDataTest());
        brv_msgnotify.setAdapter(msgNotifyAdapter);

    }

    private List<MessageNotifyItem> initDataTest(){
        List<MessageNotifyItem> list=new ArrayList<>();
        for(int i=0;i<10;i++) {
            MessageNotifyItem msgitem=new MessageNotifyItem();
            msgitem.setTitle("title"+i);
            list.add(msgitem);
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