package com.putao.wd.me.service.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ServiceDto;
import com.putao.wd.dto.ServiceGoodsDto;
import com.putao.wd.me.service.ServiceCommon;
import com.putao.wd.me.service.view.ServiceGoodsItem;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 售后列表
 * Created by wangou on 15/11/29.
 */
public class ServiceListAdapter extends BasicAdapter<ServiceDto, ServiceListAdapter.ServiceListViewHolder> {

    private String TAG = ServiceListAdapter.class.getName();

    private Context context;

    public ServiceListAdapter(Context context, List<ServiceDto> serviceDtoList) {
        super(context, serviceDtoList);
        this.context = context;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_service_list_item;
    }

    @Override
    public ServiceListViewHolder getViewHolder(View itemView, int viewType) {
        return new ServiceListViewHolder(itemView);
    }

    @Override
    public void onBindItem(ServiceListViewHolder holder, ServiceDto serviceDto, int position) {
        holder.tv_service_no.setText(serviceDto.getServiceNo());
        holder.tv_service_purchase_time.setText("2015-10-25 12:55:03");
        holder.ll_goods.removeAllViews();

        List<ServiceGoodsDto> goodsList = serviceDto.getGoodsList();
        int goodsNumber = 0;
        if (goodsList != null) {
            goodsNumber = goodsList.size();
            for (int i = 0; i < goodsNumber; i++) {
                ServiceGoodsItem goodsItem = new ServiceGoodsItem(context, goodsList.get(i));
                holder.ll_goods.addView(goodsItem);
            }
        }
        holder.tv_service_status.setText(ServiceCommon.getServiceStatusShowString(serviceDto.getServiceStatus()));
        if(serviceDto.getServiceStatus() == ServiceCommon.ORDER_WAITING_PAY){
            holder.tv_service_status.setTextColor(0xffed5564);
        }
        else{
            holder.tv_service_status.setTextColor(0xff313131);
        }
        holder.tv_total_cost.setText(goodsNumber + "件商品 合计：¥" + serviceDto.getTotalCost());
        holder.tv_shipment_fee.setText("含运费：" + serviceDto.getShipmentFee());


    }


    /**
     * 收货人地址布局
     */
    static class ServiceListViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_service_no)
        TextView tv_service_no;
        @Bind(R.id.ll_goods)
        LinearLayout ll_goods;
        @Bind(R.id.tv_total_cost)
        TextView tv_total_cost;
        @Bind(R.id.tv_shipment_fee)
        TextView tv_shipment_fee;
        @Bind(R.id.tv_service_purchase_time)
        TextView tv_service_purchase_time;
        @Bind(R.id.tv_service_status)
        TextView tv_service_status;


        public ServiceListViewHolder(View itemView) {
            super(itemView);
        }
    }


}
