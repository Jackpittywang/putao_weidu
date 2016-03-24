package com.putao.wd.explore.adapter;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.explore.ExploreMoreActivity;
import com.putao.wd.model.ExploreIndex;
import com.putao.wd.model.HomeExploreMore;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 更多内容适配器
 * Created by yanghx on 2016/1/12.
 */
public class MoreAdapter extends LoadMoreAdapter<ExploreIndex, MoreAdapter.MoreContentViewHolder> {

    private Context mContext;
    private Animation mAnim;

    public MoreAdapter(Context context, List<ExploreIndex> homeExploreMores) {
        super(context, homeExploreMores);
        mContext = context;
        mAnim = AnimationUtils.loadAnimation(mContext, R.anim.anim_cool);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_more_item;
    }

    @Override
    public MoreContentViewHolder getViewHolder(View itemView, int viewType) {
        return new MoreContentViewHolder(itemView);
    }

    @Override
    public void onBindItem(final MoreContentViewHolder holder, final ExploreIndex homeExploreMore, int position) {
        holder.iv_icon.setImageURL(homeExploreMore.getCover_pic());
        holder.tv_title.setText(homeExploreMore.getTitle());
        holder.tv_content.setText(homeExploreMore.getDescription());
        holder.tv_count_comment.setText(homeExploreMore.getCount_comments() + "");
        holder.tv_count_cool.setText(homeExploreMore.getCount_likes() + "");
        holder.sb_cool_icon.setClickable(false);
        holder.sb_cool_icon.setState(homeExploreMore.is_like());
        holder.ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusHelper.post(homeExploreMore.getArticle_id(), ExploreMoreActivity.EVENT_COMMENT);
            }
        });
        holder.ll_cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.sb_cool_icon.startAnimation(mAnim);
                if (homeExploreMore.is_like())
                    return;
                EventBusHelper.post(homeExploreMore.getArticle_id(), ExploreMoreActivity.EVENT_COOL);
                holder.sb_cool_icon.setState(true);
                holder.tv_count_cool.setText(Integer.parseInt(holder.tv_count_cool.getText().toString()) + 1 + "");
                homeExploreMore.setIs_like(true);
            }
        });
    }


    /**
     * 视图
     */
    static class MoreContentViewHolder extends BasicViewHolder {

        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.tv_count_comment)
        TextView tv_count_comment;
        @Bind(R.id.tv_count_cool)
        TextView tv_count_cool;
        @Bind(R.id.sb_cool_icon)
        SwitchButton sb_cool_icon;
        @Bind(R.id.iv_icon)
        ImageDraweeView iv_icon;
        @Bind(R.id.ll_comment)
        LinearLayout ll_comment;
        @Bind(R.id.ll_cool)
        LinearLayout ll_cool;

        public MoreContentViewHolder(View itemView) {
            super(itemView);
        }
    }

}
