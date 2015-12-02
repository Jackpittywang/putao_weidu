package com.putao.wd.me.message;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    @Bind(R.id.tv_nomore)
    TextView tv_nomore;
    @Bind(R.id.ll_msgnotify)
    LinearLayout ll_msgnotify;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_notify;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        if(initDataTest().size()!=0) {
            ll_msgnotify.setVisibility(View.VISIBLE);
            MsgNotifyAdapter msgNotifyAdapter = new MsgNotifyAdapter(mActivity, initDataTest());
            brv_msgnotify.setAdapter(msgNotifyAdapter);
        }else{
            tv_nomore.setVisibility(View.VISIBLE);
        }

    }

    private List<MessageNotifyItem> initDataTest(){
        List<MessageNotifyItem> list=new ArrayList<>();
        for(int i=0;i<10;i++) {
            MessageNotifyItem msgitem=new MessageNotifyItem();
            msgitem.setTitle("title"+i);
            msgitem.setDate("刚刚而已");
            msgitem.setIntro("这里显示用系统发来的通知，字数没有限制，没有详情页。未读信息为黑色字");
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