package com.putao.wd.explore.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ControllItem;
import com.putao.wd.model.ManagementDevice;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * ”受控设备“列表适配器
 * Created by wango on 2015/12/2.
 */
public class ControlledEquipmentAdapter extends BasicAdapter<ManagementDevice, ControlledEquipmentAdapter.ControlledEquipmentViewHolder> {

    public ControlledEquipmentAdapter(Context context, List<ManagementDevice> managementDevices) {
        super(context, managementDevices);
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
    public void onBindItem(final ControlledEquipmentViewHolder holder, ManagementDevice item, int position) {
        holder.tv_equipment_name.setText(item.getSlave_device_name());
        holder.iv_select_icon.setVisibility("1".equals(item.getStatus()) ? View.VISIBLE : View.GONE);
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

