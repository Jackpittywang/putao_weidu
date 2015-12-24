package com.putao.wd.start.question.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.CoolList;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 点赞列表适配器
 * Created by wango on 2015/12/4.
 */
public class QuestionAdapter extends LoadMoreAdapter<CoolList, QuestionAdapter.PraiseListViewHolder> {
    public QuestionAdapter(Context context, List<CoolList> coolLists) {
        super(context, coolLists);
    }

    public int getLayoutId(int viewType) {
        return R.layout.layout_apply_list_item;
    }

    public QuestionAdapter.PraiseListViewHolder getViewHolder(View itemView, int viewType) {
        return new QuestionAdapter.PraiseListViewHolder(itemView);
    }

    public void onBindItem(QuestionAdapter.PraiseListViewHolder holder, CoolList coolList, int position) {
        holder.iv_user_icon.setImageURL(coolList.getUser_profile_photo());
        holder.tv_nickname.setText(coolList.getUser_name());

    }

    static class PraiseListViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_user_icon)
        ImageDraweeView iv_user_icon;//用户头像
        @Bind(R.id.tv_nickname)
        TextView tv_nickname;//用户昵称

        public PraiseListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
