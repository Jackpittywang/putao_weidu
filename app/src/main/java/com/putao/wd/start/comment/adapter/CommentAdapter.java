package com.putao.wd.start.comment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.CommentItem;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 评论适配器
 * Created by yanghx on 2015/12/8.
 */
public class CommentAdapter extends LoadMoreAdapter<CommentItem, CommentAdapter.CommentViewHolder> {


    public CommentAdapter(Context context, List<CommentItem> commentItems) {
        super(context, commentItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_comment_item;
    }

    @Override
    public CommentViewHolder getViewHolder(View itemView, int viewType) {
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindItem(CommentViewHolder holder, CommentItem commentItem, int position) {
        holder.iv_comment_icon.setImageURL(commentItem.getIconUrl());
        holder.tv_username.setText(commentItem.getUsername());
        holder.tv_comment_time.setText(commentItem.getTime());
        holder.tv_comment_content.setText(commentItem.getComment());
        holder.tv_comment.setText(commentItem.getComment_count());
        holder.tv_support.setText(commentItem.getSupport_count());

        holder.sb_support_icon.setOnSwitchClickListener(new SwitchButton.OnSwitchClickListener() {
            @Override
            public void onSwitchClick(View v, boolean isSelect) {

            }
        });
    }


    static class CommentViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_comment_icon)
        ImageDraweeView iv_comment_icon;
        @Bind(R.id.tv_username)
        TextView tv_username;
        @Bind(R.id.tv_comment_time)
        TextView tv_comment_time;
        @Bind(R.id.tv_comment_content)
        TextView tv_comment_content;
        @Bind(R.id.tv_support)
        TextView tv_support;
        @Bind(R.id.sb_support_icon)
        SwitchButton sb_support_icon;
        @Bind(R.id.tv_comment)
        TextView tv_comment;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }

}




