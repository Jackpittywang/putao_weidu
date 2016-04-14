package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ArticleDetailComment;
import com.putao.wd.model.Comment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.ImageUtils;
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
 */
public class ArticleCommentAdapter extends LoadMoreAdapter<ArticleDetailComment, ArticleCommentAdapter.CommentViewHolder> {
    public static final String EVENT_COMMENT_EDIT = "event_comment_edit";
    public static final String EVENT_COMMIT_COOL = "event_commit_cool";
    //    private final String DATE_PATTERN = "yyyy-MM-dd";//格式化时间戳规则
    private Context mContext;

    public ArticleCommentAdapter(Context context, List<ArticleDetailComment> comments) {
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
    public void onBindItem(final CommentViewHolder holder, final ArticleDetailComment comment, final int position) {

        if (!StringUtils.isEmpty(comment.getHead_img()))
            holder.iv_comment_icon.setImageURL(comment.getHead_img());
        else {
            holder.iv_comment_icon.setImageURL(Uri.parse("res://putao/" + R.drawable.img_head_default).toString());
        }


        if (!StringUtils.isEmpty(comment.getNick_name()))
            holder.tv_username.setText(comment.getNick_name());

        if (!StringUtils.isEmpty(comment.getRelease_time()))
            holder.tv_comment_time.setText(DateUtils.timeCalculate(Integer.parseInt(comment.getRelease_time())));

        if (comment.getCount_likes() != 0) {
            holder.tv_count_cool.setText(comment.getCount_likes() + "");
        } else {
            holder.tv_count_cool.setText("赞");
        }

        holder.tv_comment_content.setText(comment.getContent() + "");

        holder.sb_cool_icon.setClickable(false);
        holder.sb_cool_icon.setState(comment.is_like());


        if (comment.getCount_comments() == 0)
            holder.tv_count_comment.setText("评论");
        else
            holder.tv_count_comment.setText(comment.getCount_comments() + "");

        holder.rl_cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!comment.is_like()) {
                    //使用EventBus提交点赞
                    EventBusHelper.post(position, EVENT_COMMIT_COOL);
                    holder.sb_cool_icon.setState(true);
                    comment.setIs_like(true);
                    holder.tv_count_cool.setText(comment.getCount_likes() + 1 + "");
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

        if (comment.getPics() != null && comment.getPics().size() > 0 && !StringUtils.isEmpty(comment.getPics().get(0))) {
            String pic = ImageUtils.getImageSizeUrl(comment.getPics().get(0), ImageUtils.ImageSizeURL.SIZE_240x240);
            if (ImageUtils.isImage(pic) && !StringUtils.isEmpty(pic)) {
                holder.iv_comment_pic.setImageURL(pic);
                holder.iv_comment_pic.setVisibility(View.VISIBLE);
            } else {
                holder.iv_comment_pic.setVisibility(View.GONE);
            }
        } else {
            holder.iv_comment_pic.setVisibility(View.GONE);
        }
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
        @Bind(R.id.iv_comment_pic)
        ImageDraweeView iv_comment_pic;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }
}




