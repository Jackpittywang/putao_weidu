package com.putao.wd.companion.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ManagementDevice;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import org.json.simple.JSONObject;

import java.util.List;

import butterknife.Bind;


/**
 * ”受控设备“列表适配器
 * Created by wango on 2015/12/2.
 */
public class ControlledEquipmentAdapter extends BasicAdapter<ManagementDevice, ControlledEquipmentAdapter.ControlledEquipmentViewHolder> {
    public static final String MANAGER = "manager";
    List<ManagementDevice> managementDevices;
    Context context;

    public ControlledEquipmentAdapter(Context context, List<ManagementDevice> managementDevices) {
        super(context, managementDevices);
        this.managementDevices = managementDevices;
        this.context = context;
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
                if ("1".equals(item.getStatus())) {
                    EventBusHelper.post(item, MANAGER);
                    holder.iv_select_icon.setBackgroundResource(R.drawable.switch_close);
                    ToastUtils.showToast(context, "取消保存", 0);
                    item.setStatus("0");
                } else {
                    EventBusHelper.post(item, MANAGER);
                    holder.iv_select_icon.setBackgroundResource(R.drawable.switch_open);
                    ToastUtils.showToast(context, "保存成功", 0);
                    item.setStatus("1");
                }
            }
        });
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

