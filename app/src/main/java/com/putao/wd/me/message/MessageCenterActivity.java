package com.putao.wd.me.message;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.MessageNotifyItem;
import com.putao.wd.dto.MessagePraiseItem;
import com.putao.wd.dto.MessageReplyItem;
import com.putao.wd.me.message.adapter.MsgNotifyAdapter;
import com.putao.wd.me.message.adapter.MsgPraiseAdapter;
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

    //初始化"通知“列表
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
    //-初始化"回复“列表
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

    //初始化"赞“列表
    private void initPraiseList(){
        if (initPraiseData().size() != 0){//“赞”列表有数据
            ll_msgnotify.setVisibility(View.VISIBLE);
            tv_nomore.setVisibility(View.GONE);
            MsgPraiseAdapter msgPraiseAdapter = new MsgPraiseAdapter(this, initPraiseData());
            brv_messagelist.setAdapter(msgPraiseAdapter);
        }else{
            ll_msgnotify.setVisibility(View.GONE);
            tv_nomore.setVisibility(View.VISIBLE);
        }

    }
    //-------------------------------------初始化测试数据------------------------------------
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
    private List<MessageReplyItem> initReplyData(){
        List<MessageReplyItem> list=new ArrayList<>();
        MessageReplyItem messageReplyItem;
        messageReplyItem=new MessageReplyItem();
//            messageReplyItem.setHeadIconUrl();
        messageReplyItem.setDate("天前");
        messageReplyItem.setComment("这里显示用户发表的评论，字数限制为两百个汉字支持app内置表情发送支持回复");
        messageReplyItem.setRepliedUserName("RepliedUserName");
        messageReplyItem.setReplyUserNickname("ReplyUserNickname");
        list.add(messageReplyItem);
        for(int i=0;i<13;i++) {
            messageReplyItem=new MessageReplyItem();
//            messageReplyItem.setHeadIconUrl();
            messageReplyItem.setDate(i + "天前");
            messageReplyItem.setComment(i + "这里显示用户发表的评论，字数限制为两百个汉字支持app内置表情发送支持回复");
            messageReplyItem.setRepliedUserName(i + "RepliedUserName");
            messageReplyItem.setReplyUserNickname(i + "ReplyUserNickname");
            messageReplyItem.setRepliedcontent(i + "这里显示用户发表的评论，字数限制为两百个汉字支持app内置表情发送支持回复");
            list.add(messageReplyItem);
        }
        return list;
    }
    private List<MessagePraiseItem> initPraiseData(){
        List<MessagePraiseItem> list=new ArrayList<>();
        MessagePraiseItem messagePraiseItem;
        messagePraiseItem=new MessagePraiseItem();
//            messageReplyItem.setHeadIconUrl();
        messagePraiseItem.setDate("天前");
        //messagePraiseItem.setComment("这里显示用户发表的评论，字数限制为两百个汉字支持app内置表情发送支持回复");
        messagePraiseItem.setPraisedUserName("RepliedUserName");
        messagePraiseItem.setPraiseUserNickname("ReplyUserNickname");
        list.add(messagePraiseItem);
        for(int i=0;i<13;i++) {
            messagePraiseItem=new MessagePraiseItem();
//            messageReplyItem.setHeadIconUrl();
            messagePraiseItem.setDate(i + "天前");
            //messagePraiseItem.setComment(i + "这里显示用户发表的评论，字数限制为两百个汉字支持app内置表情发送支持回复");
            messagePraiseItem.setPraisedUserName(i + "RepliedUserName");
            messagePraiseItem.setPraiseUserNickname(i + "ReplyUserNickname");
            messagePraiseItem.setPraisedcontent(i + "这里显示用户发表的评论，字数限制为两百个汉字支持app内置表情发送支持回复");
            list.add(messagePraiseItem);
        }
        return list;
    }
}
