package com.putao.wd.home.apply.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ApplyListItem;
import com.putao.wd.dto.PraiseListItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wango on 2015/12/6.
 */
public class ApplyListAdapter extends LoadMoreAdapter<ApplyListItem, ApplyListAdapter.ApplyListViewHolder> {
    public ApplyListAdapter(Context context, List<ApplyListItem> praiseListItems) {
        super(context, praiseListItems);
    }

    public int getLayoutId(int viewType) {
        return R.layout.layout_praiselist_item;
    }

    public ApplyListAdapter.ApplyListViewHolder getViewHolder(View itemView, int viewType) {
        return new ApplyListAdapter.ApplyListViewHolder(itemView);
    }

    public void onBindItem(ApplyListAdapter.ApplyListViewHolder holder, ApplyListItem applyListItem, int position) {
        holder.iv_praise_headericon.setImageURL(applyListItem.getUserIconUrl());
        holder.tv_praise_usernickname.setText(applyListItem.getUsername());

        if("普通用户".equals(applyListItem.getUserattr())){
            holder.iv_praise_userdetail.setVisibility(View.VISIBLE);
            holder.iv_praise_userdetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到对应的用户主页
                }
            });
        }

    }

    static class ApplyListViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_praise_headericon)
        ImageDraweeView iv_praise_headericon;//用户头像
        @Bind(R.id.tv_praise_usernickname)
        TextView tv_praise_usernickname;//用户昵称
        @Bind(R.id.tv_praise_count)
        TextView tv_praise_count;//点赞数
        @Bind(R.id.iv_praise_userdetail)
        ImageView iv_praise_userdetail;//用户主页链接
        public ApplyListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
