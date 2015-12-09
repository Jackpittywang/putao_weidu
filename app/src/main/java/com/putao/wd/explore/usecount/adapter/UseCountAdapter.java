package com.putao.wd.explore.usecount.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.UseCountItem;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * “每天使用次数”适配器
 * Created by wango on 2015/12/9.
 */
public class UseCountAdapter extends BasicAdapter<UseCountItem,UseCountAdapter.UseCountViewHolder> {
    private int selected_id=-1;//上一个选择过的列表tag
    private int selecting_id=-1;//当前选择过的列表tag

    public UseCountAdapter(Context context, List<UseCountItem> UseCountItems) {
        super(context, UseCountItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_equipment_item;
    }

    @Override
    public UseCountViewHolder getViewHolder(View itemView, int viewType) {
        return new UseCountViewHolder(itemView);
    }

    @Override
    public void onBindItem(final UseCountViewHolder holder, UseCountItem UseCountItem, int position) {
        holder.tv_equipment_name.setText(UseCountItem.getName());
        holder.iv_select_icon.setVisibility(((int) holder.itemView.getTag()) == selecting_id ? View.VISIBLE : View.GONE);
        holder.tv_equipment_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecting_id = (int) holder.itemView.getTag();
                notifyItemRangeChanged(selecting_id, 1);//刷新当前选择的状态
                if (selected_id != selecting_id && selected_id != -1) {
                    notifyItemRangeChanged(selected_id, 1);//取消之前选择过的状态
                }
                selected_id = selecting_id;//把"当前选择"的tag标记为"选择过的“
            }
        });
    }

    /**
     *
     */
    static class UseCountViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_equipment_name)
        TextView tv_equipment_name;
        @Bind(R.id.iv_select_icon)
        ImageView iv_select_icon;
        public UseCountViewHolder(View itemView) {
            super(itemView);
        }
    }
}

