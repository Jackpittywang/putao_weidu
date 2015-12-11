package com.putao.wd.explore.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ControllItem;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * ”受控设备“列表适配器
 * Created by wango on 2015/12/2.
 */
public class ControlledEquipmentAdapter extends BasicAdapter<ControllItem, ControlledEquipmentAdapter.ControlledEquipmentViewHolder> {

    public ControlledEquipmentAdapter(Context context, List<ControllItem> equipmentItems) {
        super(context, equipmentItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_controlled_equipment_item;
    }

    @Override
    public ControlledEquipmentViewHolder getViewHolder(View itemView, int viewType) {
        return new ControlledEquipmentViewHolder(itemView);
    }

    @Override
    public void onBindItem(final ControlledEquipmentViewHolder holder, ControllItem item, int position) {
        holder.tv_equipment_name.setText(item.getName());
        holder.iv_select_icon.setVisibility(item.isSelect() ? View.VISIBLE : View.GONE);
    }

    /**
     *
     */
    static class ControlledEquipmentViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_equipment_name)
        TextView tv_equipment_name;
        @Bind(R.id.iv_select_icon)
        ImageView iv_select_icon;

        public ControlledEquipmentViewHolder(View itemView) {
            super(itemView);
        }
    }
}

