package com.putao.wd.pt_me.order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.pt_me.order.ShipmentState;
import com.putao.wd.model.Express;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/12/29.
 */
public class ShipmentAdapter extends BasicAdapter<Express, ShipmentAdapter.ShipmentViewHolder> {

    public ShipmentAdapter(Context context, List<Express> expresses) {
        super(context, expresses);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_order_shipment_list_item;
    }

    @Override
    public ShipmentViewHolder getViewHolder(View itemView, int viewType) {
        return new ShipmentViewHolder(itemView);
    }


    @Override
    public void onBindItem(ShipmentViewHolder holder, Express express, int position) {
        holder.tv_shipment_title.setText("包裹"+(position+1));
        holder.tv_shipment_status.setText(ShipmentState.getExpressStatusShowString(express.getState()));
    }


    /**
     * 视图
     */
    static class ShipmentViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_shipment_title)
        TextView tv_shipment_title;// 包裹名
        @Bind(R.id.tv_shipment_status)
        TextView tv_shipment_status;//物流状态
        public ShipmentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
