package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/11.
 */

public class CommentReplyAdapter extends BasicAdapter<String, CommentReplyAdapter.CommentReplyHolder> {


    public CommentReplyAdapter(Context context, List<String> pics) {
        super(context, pics);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_comment_participate_item;
    }

    @Override
    public CommentReplyHolder getViewHolder(View itemView, int viewType) {
        return new CommentReplyHolder(itemView);
    }

    @Override
    public void onBindItem(CommentReplyHolder holder, String pic, int position) {
        holder.iv_user_icon.setImageURL(pic);
    }

    static class CommentReplyHolder extends BasicViewHolder {

        @Bind(R.id.iv_user_icon)
        ImageDraweeView iv_user_icon;

        public CommentReplyHolder(View itemView) {
            super(itemView);
        }
    }
}