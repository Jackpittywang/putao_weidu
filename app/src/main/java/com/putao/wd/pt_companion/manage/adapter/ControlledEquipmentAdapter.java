package com.putao.wd.pt_companion.manage.adapter;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ManagementDevice;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;


import java.util.List;

import butterknife.Bind;


/**
 * ”受控设备“列表适配器
 * Created by wango on 2015/12/2.
 */
public class ControlledEquipmentAdapter extends BasicAdapter<ManagementDevice, ControlledEquipmentAdapter.ControlledEquipmentViewHolder> {
    public static final String MANAGER = "manager";
    public static final String EVENT_ITEM_DELETE = "event_item_delete";
    List<ManagementDevice> managementDevices;
    Context context;
    private boolean mShowDelete;

    public ControlledEquipmentAdapter(Context context, List<ManagementDevice> managementDevices, boolean showDelete) {
        super(context, managementDevices);
        this.managementDevices = managementDevices;
        this.context = context;
        mShowDelete = showDelete;
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
    public void onBindItem(final ControlledEquipmentViewHolder holder, final ManagementDevice item, final int position) {
        holder.tv_equipment_name.setText(item.getSlave_device_name());

        //如果是1就关闭改成0，反之则改成1.
        if ("1".equals(item.getStatus())) {
            holder.iv_select_icon.setBackgroundResource(R.drawable.switch_open);
        } else {
            holder.iv_select_icon.setBackgroundResource(R.drawable.switch_close);
        }
        holder.iv_select_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShowDelete) return;
                if ("1".equals(item.getStatus())) {
                    EventBusHelper.post(item, MANAGER);
                    holder.iv_select_icon.setBackgroundResource(R.drawable.companion_close);
                    item.setStatus("0");
                } else {
                    EventBusHelper.post(item, MANAGER);
                    holder.iv_select_icon.setBackgroundResource(R.drawable.companion_open);
                    item.setStatus("1");
                }
            }
        });
        if (mShowDelete) {
            holder.iv_delete.setVisibility(View.VISIBLE);
            PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translationX", -20, 0);
            PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            ObjectAnimator.ofPropertyValuesHolder(holder.iv_delete, pvh1, pvh2).setDuration(200).start();
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusHelper.post(item, EVENT_ITEM_DELETE);
                }
            });
        } else {
            holder.iv_delete.setVisibility(View.GONE);
        }
    }

    /**
     *
     */
    static class ControlledEquipmentViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_equipment_name)
        TextView tv_equipment_name;
        @Bind(R.id.iv_select_icon)
        ImageView iv_select_icon;
        @Bind(R.id.iv_delete)
        ImageView iv_delete;

        public ControlledEquipmentViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setShowDelete(boolean showDelete) {
        mShowDelete = showDelete;
        notifyDataSetChanged();
    }
}

