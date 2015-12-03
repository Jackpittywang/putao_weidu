package com.putao.wd.me.message.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.MessageNotifyItem;
import com.putao.wd.dto.MessageReplyItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wango on 2015/12/2.
 */
public class MsgReplyAdapter  extends BasicAdapter<MessageReplyItem,MsgReplyAdapter.MsgReplyViewHolder> {

    public MsgReplyAdapter(Context context, List<MessageReplyItem> messageReplyItems) {
        super(context, messageReplyItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_msgreply_item;
    }

    @Override
    public MsgReplyViewHolder getViewHolder(View itemView, int viewType) {
        return new MsgReplyViewHolder(itemView);
    }

    @Override
    public void onBindItem(MsgReplyViewHolder holder, MessageReplyItem messageReplyItem, int position) {
        holder.iv_reply_headericon.setImageURL(messageReplyItem.getHeadIconUrl());
        holder.tv_reply_usernickname.setText(messageReplyItem.getReplyUserNickname());
        holder.tv_reply_date.setText(messageReplyItem.getDate());
        Spanned sstr=Html.fromHtml("<font color=#959595>回复 您：</font>"+"<font color=#313131>"+messageReplyItem.getComment()+"</font>");
        holder.tv_reply_content.setText(sstr);
        //holder.tv_replied_username.setText(messageReplyItem.getRepliedUserName());
        holder.tv_replied_content.setText(messageReplyItem.getRepliedUserName()+"："+messageReplyItem.getRepliedcontent());
    }

    /**
     *
     */
    static class MsgReplyViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_reply_headericon)
        ImageDraweeView iv_reply_headericon;
        @Bind(R.id.tv_reply_usernickname)
        TextView tv_reply_usernickname;
        @Bind(R.id.tv_reply_date)
        TextView tv_reply_date;
        @Bind(R.id.tv_reply_content)
        TextView tv_reply_content;
//        @Bind(R.id.tv_replied_username)
//        TextView tv_replied_username;
        @Bind(R.id.tv_replied_content)
        TextView tv_replied_content;

        public MsgReplyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

