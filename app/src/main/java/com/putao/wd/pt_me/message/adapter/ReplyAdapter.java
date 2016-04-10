package com.putao.wd.pt_me.message.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.model.Reply;
import com.putao.wd.model.ReplyDetail;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.emoji.EmojiTextView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 消息中心：“回复”适配器
 * Created by wango on 2015/12/2.
 */
public class ReplyAdapter extends LoadMoreAdapter<Reply, ReplyAdapter.ReplyViewHolder> {
//    public ReplyAdapter(Context context, List<ReplyDetail> messageReplyItems) {
//        super(context, messageReplyItems);
//    }
//
//    @Override
//    public int getLayoutId(int viewType) {
//        return R.layout.fragment_message_reply_item;
//    }
//
//    @Override
//    public ReplyViewHolder getViewHolder(View itemView, int viewType) {
//        return new ReplyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindItem(ReplyViewHolder holder, ReplyDetail replyDetail, int position) {
//        if (!StringUtils.isEmpty(replyDetail.getHead_img()))
//            holder.iv_head_icon.setImageURL(replyDetail.getHead_img());
//        else {
//            holder.iv_head_icon.setImageURL(Uri.parse("res://putao/" + R.drawable.img_head_default).toString());
////            holder.iv_comment_icon.setDefaultImage(R.drawable.img_head_default);
//        }
//        holder.tv_nickname.setText(replyDetail.getNick_name());
//        holder.tv_reply_date.setText(DateUtils.timeCalculate(Integer.parseInt(replyDetail.getModified_time())));
//        holder.tv_reply_content.setText(replyDetail.getReplay_content());
//        holder.tv_replied_content.setText(AccountHelper.getCurrentUserInfo().getNick_name() + ":" + replyDetail.getParent_content());
//    }


    public ReplyAdapter(Context context, List<Reply> messageReplyItems) {
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
    public void onBindItem(ReplyViewHolder holder, Reply replyDetail, int position) {
        if (!StringUtils.isEmpty(replyDetail.getHead_img()))
            holder.iv_head_icon.setImageURL(replyDetail.getHead_img());
        else {
            holder.iv_head_icon.setImageURL(Uri.parse("res://putao/" + R.drawable.img_head_default).toString());
//            holder.iv_comment_icon.setDefaultImage(R.drawable.img_head_default);
        }
        holder.tv_nickname.setText(replyDetail.getNick_name());
        holder.tv_reply_date.setText(DateUtils.timeCalculate(Integer.parseInt(replyDetail.getRelease_time())));

//        SpannableString ss = new SpannableString("回复 您");
//        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_color_gray)), 0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mMinLenght = ss.length();
//        et_msg.setText(ss);

        //ForegroundColorSpan 文本颜色（前景色）

        SpannableString spanText = new SpannableString("回复 您:"+replyDetail.getContent());

        spanText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_color_gray)), 0, 5,

                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

      //  mTVText.append("\n");

        holder.tv_reply_content.setText(spanText);

       // holder.tv_reply_content.setText(Html.fromHtml("回复 您:"+replyDetail.getContent());

        if (replyDetail.getParent_content().getComment_id() == 1) {
            holder.tv_replied_content.setText(AccountHelper.getCurrentUserInfo().getNick_name() + ":" + replyDetail.getParent_content().getContent());
        } else if (replyDetail.getParent_content().getOpus_id() == 3) {
            holder.tv_replied_content.setText(AccountHelper.getCurrentUserInfo().getNick_name() + ":" + replyDetail.getParent_content().getContent());
        } else {
            holder.tv_replied_content.setText(AccountHelper.getCurrentUserInfo().getNick_name() + ":" + replyDetail.getParent_content().getContent());
        }
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

