package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.CommentReply;
import com.putao.wd.model.ReplyLists;
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
 * Created by Administrator on 2016/4/11.
 */
public class ReplyListsAdapter extends LoadMoreAdapter<ReplyLists, ReplyListsAdapter.ReplyListsHolder>{

    private Context mContext;


    public ReplyListsAdapter(Context context, List<ReplyLists> commentReplies) {
        super(context, commentReplies);
        this.mContext = context;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_comment_item;
    }

    @Override
    public ReplyListsHolder getViewHolder(View itemView, int viewType) {
        return new ReplyListsHolder(itemView);
    }

    @Override
    public void onBindItem(final ReplyListsHolder holder, final ReplyLists commentReply, final int position) {
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

       // if (0 != commentReply.getIs_essence()) {
        //    holder.tv_comment_content.setText("回复 " + /*commentReply.getReply().getUser_name() +*/ ": " + commentReply.getContent());
     //   } else {
            holder.tv_comment_content.setText(commentReply.getContent());
       // }



        holder.sb_cool_icon.setClickable(false);
        holder.rl_cool.setVisibility(View.GONE);
        holder.tv_count_comment.setVisibility(View.GONE);

//        holder.tv_count_comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //使用EventBus控制表情栏弹出
//                EventBusHelper.post(position, EVENT_COMMENT_EDIT);
//            }
//        });

//        holder.rl_cool.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (commentReply.getIs_like() == 0) {
//                    //使用EventBus提交点赞
//                    EventBusHelper.post(position, EVENT_COMMIT_COOL);
//                    holder.sb_cool_icon.setState(true);
//                    commentReply.setIs_like(1);
//                    commentReply.setCount_likes( commentReply.getCount_likes() == 0 ? 1 : commentReply.getCount_likes() + 1);
//                    holder.tv_count_cool.setText(commentReply.getCount_likes());
//                } else ToastUtils.showToastShort(mContext, "您已经点过赞了哦");
//            }
//        });

    }

    static class ReplyListsHolder extends BasicViewHolder{

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

}
