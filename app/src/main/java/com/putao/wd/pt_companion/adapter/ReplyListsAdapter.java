package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ReplyHeaderInfo;
import com.putao.wd.model.ReplyLists;
import com.putao.wd.start.browse.PictrueBrowseActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.emoji.EmojiTextView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/11.
 */
public class ReplyListsAdapter extends BasicAdapter<ReplyLists, BasicViewHolder> {
    public static final String EVENT_COMMIT_COOL = "event_commit_cool";
    private final int VIEW_HEADER = 0xFF;
    private final int VIEW_ITEM = 0xFE;
    private Context mContext;
    private ReplyHeaderInfo headerInfo;
    CommentReplyAdapter mCommentReplyAdapter;

    public ReplyListsAdapter(Context context, List<ReplyLists> commentRepliesm, ReplyHeaderInfo headerInfo) {
        super(context, commentRepliesm);
        this.headerInfo = headerInfo;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == VIEW_HEADER)
            return R.layout.activity_article_detail_header;
        else
            return R.layout.activity_comment_item;

    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {
        if (viewType == VIEW_HEADER)
            return new HeaderHolder(itemView);
        else
            return new ReplyListsHolder(itemView);
    }

    @Override
    public void onBindItem(final BasicViewHolder basicHolder, final ReplyLists commentReply, final int index) {
        if (index == 0) {
            HeaderHolder holder = (HeaderHolder) basicHolder;
            if (!StringUtils.isEmpty(headerInfo.getPic()))
                holder.iv_articlesdetail_header.setImageURL(headerInfo.getPic());
            else
                holder.iv_articlesdetail_header.setVisibility(View.GONE);
            holder.tv_articlesdetail_resume.setText(headerInfo.getContent());
            holder.tv_amount_comment.setText(headerInfo.getCount_comments() + "   条评论");
            holder.tv_praise_count.setText(headerInfo.getCount_likes() + "");
            if (headerInfo.getCount_likes() != 0) {
                //设置布局管理器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mCommentReplyAdapter = new CommentReplyAdapter(mContext, headerInfo.getLikes_icon());
                holder.rv_articlesdetail_applyusers.setAdapter(mCommentReplyAdapter);
                holder.rv_articlesdetail_applyusers.setLayoutManager(linearLayoutManager);
            }
            if (headerInfo.getIs_like())
                holder.ivPraise.setImageResource(R.drawable.icon_30_17);
            else
                holder.ivPraise.setImageResource(R.drawable.icon_30_13);

            holder.ivPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!headerInfo.getIs_like())
                        EventBusHelper.post("", EVENT_COMMIT_COOL);
                }
            });
            /**
             * 点击跳转放大图片
             * */
            holder.iv_articlesdetail_header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PictrueBrowseActivity.class);
                    intent.putExtra(PictrueBrowseActivity.IMAGE_URL, headerInfo.getPic());
                    mContext.startActivity(intent);
                }
            });
        } else {
            int position = index;
            ReplyListsHolder holder = (ReplyListsHolder) basicHolder;
            if (!StringUtils.isEmpty(commentReply.getHead_img()))
                holder.iv_comment_icon.setImageURL(commentReply.getHead_img());
            else {
                holder.iv_comment_icon.setImageURL(Uri.parse("res://putao/" + R.drawable.img_head_default).toString());
            }
            if (!StringUtils.isEmpty(commentReply.getNick_name()))
                holder.tv_username.setText(commentReply.getNick_name());

            if (StringUtils.isEmpty(commentReply.getUid()))
                holder.tv_username.setText(commentReply.getUid());

            String create_time = DateUtils.timeCalculate(Integer.valueOf(commentReply.getRelease_time()));
            holder.tv_comment_time.setText(create_time);

            holder.tv_comment_content.setText(commentReply.getContent());

            holder.sb_cool_icon.setClickable(false);
            holder.rl_cool.setVisibility(View.GONE);
            holder.tv_count_comment.setVisibility(View.GONE);


        }


    }

    @Override
    public int getItemViewType(int position) {
        //头部
        if (position == 0)
            return VIEW_HEADER;
        else
            return VIEW_ITEM;
    }

    static class ReplyListsHolder extends BasicViewHolder {

        @Bind(R.id.iv_comment_icon)
        ImageDraweeView iv_comment_icon;
        @Bind(R.id.tv_username)
        TextView tv_username;
        @Bind(R.id.tv_comment_time)
        TextView tv_comment_time;
        @Bind(R.id.tv_comment_content)
        EmojiTextView tv_comment_content;
        @Bind(R.id.tv_count_cool)
        TextView tv_count_cool;
        @Bind(R.id.sb_cool_icon)
        SwitchButton sb_cool_icon;
        @Bind(R.id.tv_count_comment)
        TextView tv_count_comment;
        @Bind(R.id.rl_cool)
        RelativeLayout rl_cool;


        public ReplyListsHolder(View itemView) {
            super(itemView);
        }
    }

    static class HeaderHolder extends BasicViewHolder {

        @Bind(R.id.iv_articlesdetail_header)
        ImageDraweeView iv_articlesdetail_header;
        @Bind(R.id.tv_articlesdetail_resume)
        TextView tv_articlesdetail_resume;
        @Bind(R.id.tv_amount_comment)
        TextView tv_amount_comment;
        @Bind(R.id.tv_praise_count)
        TextView tv_praise_count;
        @Bind(R.id.rv_articlesdetail_applyusers)
        BasicRecyclerView rv_articlesdetail_applyusers;
        @Bind(R.id.ivPraise)
        ImageView ivPraise;

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

}
