package com.putao.wd.me.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;

/*
**消息-赞
**create by wangou
 */
public class MessagePraiseFragment extends BasicFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_praise;
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