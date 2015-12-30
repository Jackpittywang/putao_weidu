package com.putao.wd.me.order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.me.order.ShipmentState;
import com.putao.wd.model.Express;
import com.putao.wd.model.ExpressContent;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/12/29.
 */
public class ShipmentDetailAdapter extends BasicAdapter<ExpressContent, ShipmentDetailAdapter.ShipmentViewHolder> {

    public ShipmentDetailAdapter(Context context, ArrayList<ExpressContent> realContents) {
        super(context, realContents);
    }



    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_order_shipment_info_item;
    }

    @Override
    public ShipmentViewHolder getViewHolder(View itemView, int viewType) {
        return new ShipmentViewHolder(itemView);
    }


    @Override
    public void onBindItem(ShipmentViewHolder holder, ExpressContent realContent, int position) {
        holder.tv_shipment_date.setText(realContent.getTime());
        holder.tv_shipment_info.setText(realContent.getContext());
        holder.img_status_icon.setBackgroundResource(R.drawable.icon_logistics_flow_old);
        if(position == 0){
            holder.tv_shipment_date.setTextColor(0xff313131);
            holder.tv_shipment_info.setTextColor(0xff313131);
            holder.img_status_icon.setBackgroundResource(R.drawable.icon_logistics_flow_latest);
            holder.v_top_line_cover.setVisibility(View.GONE);
        }else if(position == 1){
            holder.tv_shipment_date.setTextColor(0xbb313131);
            holder.tv_shipment_info.setTextColor(0xbb313131);
        }else if(position == 2){
            holder.tv_shipment_date.setTextColor(0x88313131);
            holder.tv_shipment_info.setTextColor(0x88313131);
        }else{
            holder.tv_shipment_date.setTextColor(0x55313131);
            holder.tv_shipment_info.setTextColor(0x55313131);
        }
    }


    /**
     * 视图
     */
    static class ShipmentViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_shipment_date)
        TextView tv_shipment_date;// 包裹名
        @Bind(R.id.tv_shipment_info)
        TextView tv_shipment_info;//物流状态
        @Bind(R.id.img_status_icon)
        View img_status_icon;//点
        @Bind(R.id.v_top_line_cover)
        View v_top_line_cover;//上部分线
        public ShipmentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
