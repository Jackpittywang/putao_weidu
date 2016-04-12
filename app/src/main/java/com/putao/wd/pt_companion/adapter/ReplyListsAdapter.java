package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ReplyHeaderInfo;
import com.putao.wd.model.ReplyLists;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.emoji.EmojiTextView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/11.
 */
public class ReplyListsAdapter extends BasicAdapter<ReplyLists, BasicViewHolder> {
    private final int VIEW_HEADER = 0xFF;
    private final int VIEW_ITEM = 0xFE;
    private Context mContext;
    private ReplyHeaderInfo headerInfo;

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
            holder.iv_articlesdetail_header.setImageURL(headerInfo.getPic());
            holder.tv_articlesdetail_resume.setText(headerInfo.getContent());
            holder.tv_amount_comment.setText(headerInfo.getCommentCount() + "   条评论");
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

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

}
