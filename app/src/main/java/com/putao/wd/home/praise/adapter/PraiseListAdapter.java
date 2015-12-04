package com.putao.wd.home.praise.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.PraiseListItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 点赞列表适配器
 * Created by wango on 2015/12/4.
 */
public class PraiseListAdapter  extends LoadMoreAdapter<PraiseListItem, PraiseListAdapter.PraiseListViewHolder> {
    public PraiseListAdapter(Context context, List<PraiseListItem> praiseListItems) {
        super(context, praiseListItems);
    }

    public int getLayoutId(int viewType) {
        return R.layout.layout_praiselist_item;
    }

    public PraiseListAdapter.PraiseListViewHolder getViewHolder(View itemView, int viewType) {
        return new PraiseListAdapter.PraiseListViewHolder(itemView);
    }

    public void onBindItem(PraiseListAdapter.PraiseListViewHolder holder, PraiseListItem praiseListItem, int position) {
        holder.iv_praise_headericon.setImageURL(praiseListItem.getUserIconUrl());
    }

    static class PraiseListViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_praise_headericon)
        ImageDraweeView iv_praise_headericon;//用户头像
        @Bind(R.id.tv_praise_usernickname)
        TextView tv_praise_usernickname;//用户昵称
        @Bind(R.id.tv_praise_count)
        TextView tv_praise_count;//点赞数
        @Bind(R.id.iv_praise_userdetail)
        ImageView iv_praise_userdetail;//用户主页链接
        public PraiseListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
