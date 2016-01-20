package com.putao.wd.start.praise.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.CoolList;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 点赞列表适配器
 * Created by wango on 2015/12/4.
 */
public class PraiseListAdapter  extends LoadMoreAdapter<CoolList, PraiseListAdapter.PraiseListViewHolder> {
    public PraiseListAdapter(Context context, List<CoolList> coolLists) {
        super(context, coolLists);
    }

    public int getLayoutId(int viewType) {
        return R.layout.activity_apply_list_item;
    }

    public PraiseListAdapter.PraiseListViewHolder getViewHolder(View itemView, int viewType) {
        return new PraiseListAdapter.PraiseListViewHolder(itemView);
    }

    public void onBindItem(PraiseListAdapter.PraiseListViewHolder holder, CoolList coolList, int position) {
        holder.iv_user_icon.setImageURL(coolList.getUser_profile_photo());
        holder.tv_nickname.setText(coolList.getUser_name());

//        holder.iv_praise_headericon.setImageURL(praiseListItem.getUserIconUrl());
//        holder.tv_praise_usernickname.setText(praiseListItem.getUsername());
//        if("游客".equals(praiseListItem.getUserattr())){
//            holder.tv_praise_count.setVisibility(View.VISIBLE);
//            holder.tv_praise_count.setText(praiseListItem.getPraiseCount()+"个赞");
//        }else if("普通用户".equals(praiseListItem.getUserattr())){
//            holder.iv_praise_userdetail.setVisibility(View.VISIBLE);
//            holder.iv_praise_userdetail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //跳转到对应的用户主页
//                }
//            });
//        }
    }

    static class PraiseListViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_user_icon)
        ImageDraweeView iv_user_icon;//用户头像
        @Bind(R.id.tv_nickname)
        TextView tv_nickname;//用户昵称
//        @Bind(R.id.tv_praise_count)
//        TextView tv_praise_count;//点赞数
//        @Bind(R.id.iv_praise_userdetail)
//        ImageView iv_praise_userdetail;//用户主页链接

        public PraiseListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
