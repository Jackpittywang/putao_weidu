package com.putao.wd.me.message;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;

/*
**消息-回复
**create by wangou
 */
public class MessageReplyFragment extends BasicFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_reply;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

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