package com.putao.wd.pt_companion.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ManagementProduct;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * ”受控产品“列表适配器
 * Created by wango on 2015/12/2.
 */
public class ControlledProductAdapter extends BasicAdapter<ManagementProduct, ControlledProductAdapter.ControlledProductViewHolder> {

    public ControlledProductAdapter(Context context, List<ManagementProduct> managementProducts) {
        super(context, managementProducts);
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
    public void onBindItem(final ControlledProductViewHolder holder, ManagementProduct item, int position) {
        holder.iv_product_icon.setImageURL(item.getProduct_icon());
        holder.tv_product_name.setText(item.getProduct_name());
        holder.iv_select_icon.setVisibility(item.getStatus() == 1 ? View.VISIBLE : View.GONE);
//        switch (position) {
//            case 0:
//                holder.iv_product_icon.setImageResource(R.drawable.icon_40_08);
//                break;
//            case 1:
//                holder.iv_product_icon.setImageResource(R.drawable.icon_40_09);
//                break;
//            case 2:
//                holder.iv_product_icon.setImageResource(R.drawable.icon_40_10);
//                break;
//            case 3:
//                holder.iv_product_icon.setImageResource(R.drawable.icon_40_11);
//                break;
//        }
    }

    /**
     *
     */
    static class ControlledProductViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_product_icon)
        ImageDraweeView iv_product_icon;
        @Bind(R.id.tv_product_name)
        TextView tv_product_name;
        @Bind(R.id.iv_select_icon)
        ImageView iv_select_icon;

        public ControlledProductViewHolder(View itemView) {
            super(itemView);
        }
    }
}

