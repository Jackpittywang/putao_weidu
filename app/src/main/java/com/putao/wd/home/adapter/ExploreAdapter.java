package com.putao.wd.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ExploreItem;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 探索号适配器
 * Created by yanghx on 2015/12/9.
 */
public class ExploreAdapter extends BasicAdapter<ExploreItem, BasicViewHolder> {
    private static final int TYPE_USE = 1;
    private static final int TYPE_INSTRUCTION = 2;
    private final int TYPE_PICTURE_ONE = 1;
    private final int TYPE_PICTURE_FOUR = 4;
    private final int TYPE_PICTURE_NINE = 9;

    public ExploreAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int getItemViewType(int position) {
        ExploreItem exploreItem = getItem(position);
        if (exploreItem.isMixed()) {
            return TYPE_INSTRUCTION;
        } else {
            return TYPE_USE;
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            case TYPE_USE:
                return R.layout.fragment_explore_item;
            case TYPE_INSTRUCTION:
                return R.layout.fragment_explore_mixed_item;
            default:
                return 0;
        }
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case TYPE_USE:
                return new ExploerViewHolder(itemView);
            case TYPE_INSTRUCTION:
                return new ExploerMixedViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindItem(BasicViewHolder holder, ExploreItem exploreItem, int position) {
        if (holder instanceof ExploerViewHolder) {
            ExploerViewHolder viewHolder = (ExploerViewHolder) holder;
            if (position != 0) {
                doCompare(viewHolder, exploreItem, position);
            } else {
                viewHolder.ll_explore_top.setVisibility(View.VISIBLE);
                viewHolder.tv_date.setText(exploreItem.getDate());
            }
            viewHolder.tv_skill_name.setText(exploreItem.getSkill_name());
        } else if (holder instanceof ExploerMixedViewHolder) {
            ExploerMixedViewHolder viewHolder = (ExploerMixedViewHolder) holder;
            if (position != 0) {
                doCompare(viewHolder, exploreItem, position);
            } else {
                viewHolder.ll_explore_top.setVisibility(View.VISIBLE);
                viewHolder.tv_date.setText(exploreItem.getDate());
            }
            viewHolder.tv_skill_name.setText(exploreItem.getSkill_name());
            viewHolder.tv_content.setText(exploreItem.getContent());
            int iconNum = exploreItem.getIconNum();
            showPiture(viewHolder, iconNum);
        }
    }

    /**
     * 日期比较,控制头部显示
     */
    private void doCompare(BasicViewHolder holder, ExploreItem exploreItem, int position) {
        ExploreItem preExplore = getItem(position - 1);
        int abs = DateUtils.getDaysUnAbs(preExplore.getDate(), exploreItem.getDate());
        if (abs == 0) {
            if (holder instanceof ExploerViewHolder) {
                ((ExploerViewHolder) holder).ll_explore_top.setVisibility(View.GONE);
            } else if (holder instanceof ExploerMixedViewHolder) {
                ((ExploerMixedViewHolder) holder).ll_explore_top.setVisibility(View.GONE);
            }
        } else {
            if (holder instanceof ExploerViewHolder) {
                ((ExploerViewHolder) holder).ll_explore_top.setVisibility(View.VISIBLE);
                ((ExploerViewHolder) holder).tv_date.setText(exploreItem.getDate());
            } else if (holder instanceof ExploerMixedViewHolder) {
                ((ExploerMixedViewHolder) holder).ll_explore_top.setVisibility(View.VISIBLE);
                ((ExploerMixedViewHolder) holder).tv_date.setText(exploreItem.getDate());
            }
        }
    }

    /**
     * 根据图片张数显示图片
     *
     * @param holder     ExploerMixedViewHolder类型ViewHolder
     * @param pictureNum 图片张数
     */
    private void showPiture(ExploerMixedViewHolder holder, int pictureNum) {
        switch (pictureNum) {
            case TYPE_PICTURE_ONE:
                holder.ll_picture78.setVisibility(View.GONE);
                holder.ll_picture369.setVisibility(View.INVISIBLE);
                holder.iv_picture9.setVisibility(View.GONE);
                holder.iv_one_picture.setVisibility(View.VISIBLE);
                holder.ll_picture1245.setVisibility(View.INVISIBLE);
                break;
            case TYPE_PICTURE_FOUR:
                holder.iv_one_picture.setVisibility(View.GONE);
                holder.ll_picture1245.setVisibility(View.VISIBLE);
                holder.ll_picture78.setVisibility(View.GONE);
                holder.ll_picture369.setVisibility(View.INVISIBLE);
                holder.iv_picture9.setVisibility(View.GONE);
                break;
            case TYPE_PICTURE_NINE:
                holder.iv_one_picture.setVisibility(View.GONE);
                holder.ll_picture78.setVisibility(View.VISIBLE);
                holder.ll_picture369.setVisibility(View.VISIBLE);
                holder.ll_picture1245.setVisibility(View.VISIBLE);
                holder.iv_picture9.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * 全图片
     */
    static class ExploerViewHolder extends BasicViewHolder {
        @Bind(R.id.ll_explore_top)
        LinearLayout ll_explore_top;
        @Bind(R.id.tv_date)
        TextView tv_date;
        @Bind(R.id.iv_skill_icon)
        ImageDraweeView iv_skill_icon;
        @Bind(R.id.tv_skill_name)
        TextView tv_skill_name;

        public ExploerViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 图文混排
     */
    static class ExploerMixedViewHolder extends BasicViewHolder {
        @Bind(R.id.ll_explore_top)
        LinearLayout ll_explore_top;
        @Bind(R.id.tv_date)
        TextView tv_date;
        @Bind(R.id.iv_skill_icon)
        ImageDraweeView iv_skill_icon;
        @Bind(R.id.iv_picture1)
        ImageDraweeView iv_picture1;
        @Bind(R.id.iv_picture2)
        ImageDraweeView iv_picture2;
        @Bind(R.id.iv_picture3)
        ImageDraweeView iv_picture3;
        @Bind(R.id.iv_picture4)
        ImageDraweeView iv_picture4;
        @Bind(R.id.iv_picture5)
        ImageDraweeView iv_picture5;
        @Bind(R.id.iv_picture6)
        ImageDraweeView iv_picture6;
        @Bind(R.id.iv_picture7)
        ImageDraweeView iv_picture7;
        @Bind(R.id.iv_picture8)
        ImageDraweeView iv_picture8;
        @Bind(R.id.iv_picture9)
        ImageDraweeView iv_picture9;
        @Bind(R.id.iv_one_picture)
        ImageDraweeView iv_one_picture;//第10张图特殊处理
        @Bind(R.id.tv_skill_name)
        TextView tv_skill_name;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.ll_picture1245)
        LinearLayout ll_picture1245;
        @Bind(R.id.ll_picture78)
        LinearLayout ll_picture78;
        @Bind(R.id.ll_picture369)
        LinearLayout ll_picture369;

        public ExploerMixedViewHolder(View itemView) {
            super(itemView);
        }
    }
}





