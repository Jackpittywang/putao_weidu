package com.putao.wd.created.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.CreateComment;
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
public class CreateCommentAdapter extends LoadMoreAdapter<CreateComment, CreateCommentAdapter.CommentViewHolder> {
    public static final String EVENT_COMMENT_EDIT = "event_comment_edit";
    public static final String EVENT_COMMIT_COOL = "event_commit_cool";
    //    private final String DATE_PATTERN = "yyyy-MM-dd";//格式化时间戳规则
    private Context mContext;

    public CreateCommentAdapter(Context context, List<CreateComment> comments) {
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
    public void onBindItem(final CommentViewHolder holder, final CreateComment comment, final int position) {
        if (!StringUtils.isEmpty(comment.getReal_avatar()))
            holder.iv_comment_icon.setImageURL(comment.getAvatar());
        if (!StringUtils.isEmpty(comment.getUsername()))
            holder.tv_username.setText(comment.getUsername());
        String create_time = DateUtils.timeCalculate(comment.getCreated_at());
        holder.tv_comment_time.setText(create_time);
        if (null != comment.getReply().getUsername()) {
            holder.tv_comment_content.setText("回复 " + comment.getReply().getUsername() + ": " + comment.getContent());
        } else {
            holder.tv_comment_content.setText(comment.getContent());
        }
        if (0 != comment.getLike_count()) {
            holder.tv_count_cool.setText(comment.getLike_count() + "");
        } else {
            holder.tv_count_cool.setText("赞");
        }
        holder.sb_cool_icon.setClickable(false);
        holder.sb_cool_icon.setState(comment.isLike_status());
        holder.tv_count_comment.setText(comment.getComment_reply_count() == 0 ? "评论" : comment.getComment_reply_count() + "");
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
                if (!comment.isLike_status()) {
                    //使用EventBus提交点赞
                    EventBusHelper.post(position, EVENT_COMMIT_COOL);
                    holder.sb_cool_icon.setState(true);
                    comment.setLike_status(true);
                    comment.setLike_count(comment.getComment_reply_count() + 1);
                    holder.tv_count_cool.setText(comment.getComment_reply_count() + 1 + "");
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




