package com.putao.wd.explore.product.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ControlProductItem;
import com.putao.wd.dto.EquipmentItem;
import com.putao.wd.dto.ProductItem;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * ”受控设备“列表适配器
 * Created by wango on 2015/12/2.
 */
public class ControlledProductAdatper extends BasicAdapter<ControlProductItem,ControlledProductAdatper.ControlledProductViewHolder> {
    private int selected_id=-1;//上一个选择过的列表tag
    private int selecting_id=-1;//当前选择过的列表tag
    public ControlledProductAdatper(Context context, List<ControlProductItem> controlProductItems) {
        super(context, controlProductItems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_controlled_productitem;
    }

    @Override
    public ControlledProductViewHolder getViewHolder(View itemView, int viewType) {
        return new ControlledProductViewHolder(itemView);
    }

    @Override
    public void onBindItem(final ControlledProductViewHolder holder, ControlProductItem controlProductItem, int position) {
        holder.iv_select_icon.setVisibility(((int) holder.itemView.getTag()) == selecting_id ? View.VISIBLE : View.GONE);
        holder.iv_product_icon.setImageURL(controlProductItem.getUri());
        holder.tv_equipment_name.setText(controlProductItem.getName());
        holder.tv_equipment_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.iv_select_icon.getVisibility()==View.VISIBLE){
                    holder.iv_select_icon.setVisibility(View.GONE);
                }else {
                    holder.iv_select_icon.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     *
     */
    static class ControlledProductViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_product_icon)
        ImageDraweeView iv_product_icon;
        @Bind(R.id.tv_equipment_name)
        TextView tv_equipment_name;
        @Bind(R.id.iv_select_icon)
        ImageView iv_select_icon;

        public ControlledProductViewHolder(View itemView) {
            super(itemView);
        }
    }
}

