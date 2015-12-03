package com.putao.wd.me.message;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.MessageNotifyItem;
import com.putao.wd.me.message.adapter.MsgNotifyAdapter;
import com.putao.wd.me.message.adapter.MsgReplyAdapter;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.tab.TitleBar;
import com.sunnybear.library.view.tab.TitleItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MessageCenterActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener,TitleBar.TitleItemSelectedListener {
    @Bind(R.id.brv_messagelist)
    BasicRecyclerView brv_messagelist;//消息列表
    @Bind(R.id.ll_msgnotify)
    LinearLayout ll_msgnotify;
    @Bind(R.id.tv_nomore)
    TextView tv_nomore;               //没有更多
    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar ll_title;                //顶部导航菜单

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        addListener();
        initNotifyList();
    }



    /**
     * 添加监听器
     */
    private void addListener() {
        ll_title.setTitleItemSelectedListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        switch (item.getId()) {
            case R.id.ll_all://通知
                initNotifyList();
                ToastUtils.showToastLong(this, "通知");
                break;
            case R.id.ll_ing://回复
                initReplyList();
                ToastUtils.showToastLong(this, "回复");
                break;
            case R.id.ll_finish://赞
                initPraiseList();
                ToastUtils.showToastLong(this, "赞");
                break;
        }
    }

    //-------------------------------------初始化"通知“列表------------------------------------
    //初始化通知
    private void initNotifyList(){
        //默认初始化“通知”列表
        if(initNotifyData().size()!=0) {
            ll_msgnotify.setVisibility(View.VISIBLE);
            tv_nomore.setVisibility(View.GONE);
            MsgNotifyAdapter msgNotifyAdapter = new MsgNotifyAdapter(this, initNotifyData());
            brv_messagelist.setAdapter(msgNotifyAdapter);
        }else{
            ll_msgnotify.setVisibility(View.GONE);
            tv_nomore.setVisibility(View.VISIBLE);
        }
    }
    //测试用的“通知”列表数据
    private List<MessageNotifyItem> initNotifyData(){
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
    //-------------------------------------初始化"回复“列表------------------------------------
    private void initReplyList(){
        if (initReplyData().size() != 0){//“赞”列表有数据
            ll_msgnotify.setVisibility(View.VISIBLE);
            tv_nomore.setVisibility(View.GONE);
            MsgReplyAdapter msgReplyAdapter = new MsgReplyAdapter(this, initReplyData());
            brv_messagelist.setAdapter(msgReplyAdapter);
        }else{
            ll_msgnotify.setVisibility(View.GONE);
            tv_nomore.setVisibility(View.VISIBLE);
        }

    }
    private List<String> initReplyData(){
        List<String> list=new ArrayList<>();
        for(int i=0;i<3;i++) {
            list.add("title"+i);
        }
        return list;
    }
    //-------------------------------------初始化"赞“列表------------------------------------
    private void initPraiseList(){
        if (initPraiseData().size() != 0){//“赞”列表有数据
            ll_msgnotify.setVisibility(View.VISIBLE);
            MsgReplyAdapter msgReplyAdapter = new MsgReplyAdapter(this, initPraiseData());
            brv_messagelist.setAdapter(msgReplyAdapter);
        }else{
            ll_msgnotify.setVisibility(View.GONE);
            tv_nomore.setVisibility(View.VISIBLE);
        }
    }
    private List<String> initPraiseData(){
        List<String> list=new ArrayList<>();

        return list;
    }
}
