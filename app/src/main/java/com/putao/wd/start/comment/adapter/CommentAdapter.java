package com.putao.wd.start.comment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Comment;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.emoji.EmojiTextView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 评论适配器
 * Created by yanghx on 2015/12/8.
 */
public class CommentAdapter extends LoadMoreAdapter<Comment, CommentAdapter.CommentViewHolder> {
    public static final String EVENT_COMMENT_EDIT = "event_comment_edit";
    private final String DATE_PATTERN = "yyyy-MM-dd";//格式化时间戳规则

    public CommentAdapter(Context context, List<Comment> comments) {
        super(context, comments);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_comment_item;
    }

    @Override
    public CommentViewHolder getViewHolder(View itemView, int viewType) {
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindItem(final CommentViewHolder holder, final Comment comment, final int position) {
        holder.iv_comment_icon.setImageURL(comment.getUser_profile_photo());
        holder.tv_username.setText(comment.getUser_name());
        String create_time = DateUtils.secondToDate(Integer.parseInt(comment.getCreate_time()), DATE_PATTERN);
        holder.tv_comment_time.setText(create_time);
        holder.tv_comment_content.setText(comment.getContent());
        holder.tv_count_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用EventBus控制表情栏弹出
                EventBusHelper.post(position, EVENT_COMMENT_EDIT);
            }
        });
//        holder.tv_count_comment.setText("1234");
        if (!"0".equals(comment.getCount_cool())) {
            holder.tv_count_cool.setText(comment.getCount_cool());
        } else {
            holder.tv_count_cool.setText("赞");
        }
        holder.sb_cool_icon.setOnSwitchClickListener(new SwitchButton.OnSwitchClickListener() {
            @Override
            public void onSwitchClick(View v, boolean isSelect) {
                String cool = holder.tv_count_cool.getText().toString();
                int count_cool = 0;
                if ("赞".equals(cool)) {
                    count_cool = 0;
                }else {
                    count_cool = Integer.parseInt(cool);
                }
                if (isSelect) {
                    holder.tv_count_cool.setText(count_cool+1 + "");
                } else {
                    if (count_cool - 1 <= 0) {
                        holder.tv_count_cool.setText("赞");
                    } else {
                        holder.tv_count_cool.setText(count_cool - 1 + "");
                    }
                }
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
        EmojiTextView tv_comment_content;
        @Bind(R.id.tv_count_cool)
        TextView tv_count_cool;
        @Bind(R.id.sb_cool_icon)
        SwitchButton sb_cool_icon;
        @Bind(R.id.tv_count_comment)
        TextView tv_count_comment;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }

}




