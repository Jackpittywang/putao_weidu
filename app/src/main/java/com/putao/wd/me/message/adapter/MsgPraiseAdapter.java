package com.putao.wd.me.message.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.MessagePraiseItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 通知：赞
 * Created by wango on 2015/12/3.
 */
public class MsgPraiseAdapter  extends BasicAdapter<MessagePraiseItem,MsgPraiseAdapter.MsgPraiseViewHolder> {

    public MsgPraiseAdapter(Context context, List<MessagePraiseItem> messagePraiseItems) {
        super(context, messagePraiseItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_msgpraise_item;
    }

    @Override
    public MsgPraiseViewHolder getViewHolder(View itemView, int viewType) {
        return new MsgPraiseViewHolder(itemView);
    }

    @Override
    public void onBindItem(MsgPraiseViewHolder holder, MessagePraiseItem messagePraiseItem, int position) {
        holder.iv_praise_headericon.setImageURL(messagePraiseItem.getHeadIconUrl());
        holder.tv_praise_usernickname.setText(messagePraiseItem.getPraiseUserNickname());
        holder.tv_praise_date.setText(messagePraiseItem.getDate());
        //holder.tv_reply_content.setText(messagePraiseItem.getComment());
        //holder.tv_praised_username.setText(messagePraiseItem.getPraisedUserName());
        holder.tv_praised_content.setText(messagePraiseItem.getPraisedUserName()+"："+messagePraiseItem.getPraisedcontent());
    }

    /**
     *
     */
    static class MsgPraiseViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_praise_headericon)
        ImageDraweeView iv_praise_headericon;
        @Bind(R.id.tv_praise_usernickname)
        TextView tv_praise_usernickname;
        @Bind(R.id.tv_praise_date)
        TextView tv_praise_date;
//        @Bind(R.id.tv_praised_content)
////        TextView tv_reply_content;
//        @Bind(R.id.tv_praised_username)
//        TextView tv_praised_username;
        @Bind(R.id.tv_praised_content)
        TextView tv_praised_content;

        public MsgPraiseViewHolder(View itemView) {
            super(itemView);
        }
    }
}

