package com.putao.wd.explore.product.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ControlProductItem;
import com.putao.wd.dto.EquipmentItem;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * ”受控设备“列表适配器
 * Created by wango on 2015/12/2.
 */
public class ControlledProductAdatper extends BasicAdapter<ControlProductItem,ControlledProductAdatper.ControlledProductViewHolder> {

    public ControlledProductAdatper(Context context, List<ControlProductItem> controlProductItems) {
        super(context, controlProductItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_equipment_item;
    }

    @Override
    public ControlledProductViewHolder getViewHolder(View itemView, int viewType) {
        return new ControlledProductViewHolder(itemView);
    }

    @Override
    public void onBindItem(ControlledProductViewHolder holder, ControlProductItem controlProductItem, int position) {
        holder.tv_equipment_name.setText(controlProductItem.getName());
    }

    /**
     *
     */
    static class ControlledProductViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_equipment_name)
        TextView tv_equipment_name;
        public ControlledProductViewHolder(View itemView) {
            super(itemView);
        }
    }
}

