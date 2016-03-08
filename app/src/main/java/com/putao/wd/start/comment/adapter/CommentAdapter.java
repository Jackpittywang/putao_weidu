package com.putao.wd.start.comment.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Comment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.emoji.EmojiTextView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 评论适配器
 * Created by yanghx on 2015/12/8.
 */
public class CommentAdapter extends LoadMoreAdapter<Comment, CommentAdapter.CommentViewHolder> {
    public static final String EVENT_COMMENT_EDIT = "event_comment_edit";
    public static final String EVENT_COMMIT_COOL = "event_commit_cool";
    //    private final String DATE_PATTERN = "yyyy-MM-dd";//格式化时间戳规则
    private Context mContext;

    public CommentAdapter(Context context, List<Comment> comments) {
        super(context, comments);
        this.mContext = context;
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
        if (!StringUtils.isEmpty(comment.getHead_img()))
            holder.iv_comment_icon.setImageURL(comment.getHead_img());
        if (!StringUtils.isEmpty(comment.getUser_name()))
            holder.tv_username.setText(comment.getUser_name());
        if (StringUtils.isEmpty(comment.getUser_id()))
            holder.tv_username.setText(comment.getUser_name());
        String create_time = DateUtils.timeCalculate(comment.getModified_time());
        holder.tv_comment_time.setText(create_time);
        if (null != comment.getReply()) {
            holder.tv_comment_content.setText("回复 " + comment.getReply().getUser_name() + ": " + comment.getContent());
        } else {
            holder.tv_comment_content.setText(comment.getContent());
        }
        if (!"0".equals(comment.getCount_likes())) {
            holder.tv_count_cool.setText(comment.getCount_likes());
        } else {
            holder.tv_count_cool.setText("赞");
        }

        holder.sb_cool_icon.setClickable(false);
        holder.sb_cool_icon.setState(comment.is_like());
        holder.tv_count_comment.setText(comment.getCount_comments() == 0 ? "评论" : comment.getCount_comments() + "");
        holder.tv_count_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用EventBus控制表情栏弹出
                EventBusHelper.post(position, EVENT_COMMENT_EDIT);
            }
        });
        holder.rl_cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!comment.is_like()) {
                    //使用EventBus提交点赞
                    EventBusHelper.post(position, EVENT_COMMIT_COOL);
                    holder.sb_cool_icon.setState(true);
                    comment.setIs_like(true);
                    String count_likes = comment.getCount_likes();
                    comment.setCount_likes(TextUtils.equals("赞", count_likes) ? "1" : Integer.parseInt(count_likes) + 1 + "");
                    holder.tv_count_cool.setText(Integer.parseInt(comment.getCount_likes()) + 1 + "");
                } else ToastUtils.showToastShort(mContext, "您已经点过赞了哦");
            }
        });
        holder.sb_cool_icon.setOnSwitchClickListener(new SwitchButton.OnSwitchClickListener() {
            @Override
            public void onSwitchClick(View v, boolean isSelect) {
                if (isSelect) {
                    //使用EventBus提交点赞
                    EventBusHelper.post(position, EVENT_COMMIT_COOL);
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
        @Bind(R.id.rl_cool)
        RelativeLayout rl_cool;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }
}




