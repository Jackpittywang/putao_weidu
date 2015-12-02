package com.putao.wd.explore.equipment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.EquipmentItem;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * ”受控设备“列表适配器
 * Created by wango on 2015/12/2.
 */
public class ControlledEquipmentAdatper extends BasicAdapter<EquipmentItem,ControlledEquipmentAdatper.ControlledEquipmentViewHolder> {

    public ControlledEquipmentAdatper(Context context, List<EquipmentItem> equipmentItems) {
        super(context, equipmentItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_equipment_item;
    }

    @Override
    public ControlledEquipmentViewHolder getViewHolder(View itemView, int viewType) {
        return new ControlledEquipmentViewHolder(itemView);
    }

    @Override
    public void onBindItem(ControlledEquipmentViewHolder holder, EquipmentItem equipmentItem, int position) {
        holder.tv_equipment_name.setText(equipmentItem.getName());
    }

    /**
     *
     */
    static class ControlledEquipmentViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_equipment_name)
        TextView tv_equipment_name;
        public ControlledEquipmentViewHolder(View itemView) {
            super(itemView);
        }
    }
}

