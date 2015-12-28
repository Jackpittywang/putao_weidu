package com.putao.wd.me.message.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.MessageReplyItem;
import com.putao.wd.model.ReplyDetail;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.emoji.EmojiTextView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 消息中心：“回复”适配器
 * Created by wango on 2015/12/2.
 */
public class ReplyAdapter extends LoadMoreAdapter<ReplyDetail,ReplyAdapter.ReplyViewHolder> {

    public ReplyAdapter(Context context, List<ReplyDetail> messageReplyItems) {
        super(context, messageReplyItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_message_reply_item;
    }

    @Override
    public ReplyViewHolder getViewHolder(View itemView, int viewType) {
        return new ReplyViewHolder(itemView);
    }

    @Override
    public void onBindItem(ReplyViewHolder holder, ReplyDetail replyDetail, int position) {


        holder.iv_head_icon.setImageURL(replyDetail.getUser_profile_photo());
        holder.tv_nickname.setText(replyDetail.getUser_name());
        holder.tv_reply_date.setText(DateUtils.timeCalculate(Integer.parseInt(replyDetail.getModified_time())));
        holder.tv_reply_content.setText(replyDetail.getContent());
//        holder.tv_replied_content.setText(messageReplyItem.getRepliedUserName() + "：" + messageReplyItem.getRepliedcontent());
    }

    /**
     *
     */
    static class ReplyViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_head_icon)
        ImageDraweeView iv_head_icon;//用户头像
        @Bind(R.id.tv_nickname)
        TextView tv_nickname;//用户昵称
        @Bind(R.id.tv_reply_date)
        TextView tv_reply_date;//回复时间
        @Bind(R.id.tv_reply_content)
        EmojiTextView tv_reply_content;//回复内容
        @Bind(R.id.tv_replied_content)
        TextView tv_replied_content;//被回复的内容

        public ReplyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
