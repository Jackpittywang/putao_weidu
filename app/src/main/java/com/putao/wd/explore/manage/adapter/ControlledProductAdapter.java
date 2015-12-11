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
 * ”受控产品“列表适配器
 * Created by wango on 2015/12/2.
 */
public class ControlledProductAdapter extends BasicAdapter<ControllItem, ControlledProductAdapter.ControlledProductViewHolder> {

    public ControlledProductAdapter(Context context, List<ControllItem> controlProductItems) {
        super(context, controlProductItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_controlled_product_item;
    }

    @Override
    public ControlledProductViewHolder getViewHolder(View itemView, int viewType) {
        return new ControlledProductViewHolder(itemView);
    }

    @Override
    public void onBindItem(final ControlledProductViewHolder holder, ControllItem item, int position) {
        holder.tv_equipment_name.setText(item.getName());
        holder.iv_select_icon.setVisibility(item.isSelect() ? View.VISIBLE : View.GONE);
        switch (position) {
            case 0:
                holder.iv_product_icon.setImageResource(R.drawable.icon_40_08);
                break;
            case 1:
                holder.iv_product_icon.setImageResource(R.drawable.icon_40_09);
                break;
            case 2:
                holder.iv_product_icon.setImageResource(R.drawable.icon_40_10);
                break;
            case 3:
                holder.iv_product_icon.setImageResource(R.drawable.icon_40_11);
                break;
        }
    }

    /**
     *
     */
    static class ControlledProductViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_product_icon)
        ImageView iv_product_icon;
        @Bind(R.id.tv_equipment_name)
        TextView tv_equipment_name;
        @Bind(R.id.iv_select_icon)
        ImageView iv_select_icon;

        public ControlledProductViewHolder(View itemView) {
            super(itemView);
        }
    }
}

