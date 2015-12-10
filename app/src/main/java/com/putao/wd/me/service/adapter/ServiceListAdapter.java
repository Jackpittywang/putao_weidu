package com.putao.wd.me.service.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.OrderDto;
import com.putao.wd.dto.OrderGoodsDto;
import com.putao.wd.me.order.OrderCommon;
import com.putao.wd.me.order.view.OrderGoodsItem;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 售后列表
 * Created by wangou on 15/11/29.
 */
public class ServiceListAdapter extends BasicAdapter<OrderDto, ServiceListAdapter.OrderListViewHolder> {

    private String TAG = ServiceListAdapter.class.getName();

    private Context context;

    public ServiceListAdapter(Context context, List<OrderDto> orderDtoList) {
        super(context, orderDtoList);
        this.context = context;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_order_list_item;
    }

    @Override
    public OrderListViewHolder getViewHolder(View itemView, int viewType) {
        return new OrderListViewHolder(itemView);
    }

    @Override
    public void onBindItem(OrderListViewHolder holder, OrderDto orderDto, int position) {
        holder.tv_order_no.setText(orderDto.getOrderNo());
        holder.tv_order_purchase_time.setText("2015-10-25 12:55:03");
        holder.ll_goods.removeAllViews();

        List<OrderGoodsDto> goodsList = orderDto.getGoodsList();
        int goodsNumber = 0;
        if (goodsList != null) {
            goodsNumber = goodsList.size();
            for (int i = 0; i < goodsNumber; i++) {
                OrderGoodsItem goodsItem = new OrderGoodsItem(context, goodsList.get(i));
                holder.ll_goods.addView(goodsItem);
            }
        }
        holder.tv_order_status.setText(OrderCommon.getOrderStatusShowString(orderDto.getOrderStatus()));
        if(orderDto.getOrderStatus() == OrderCommon.ORDER_WAITING_PAY){
            holder.tv_order_status.setTextColor(0xffed5564);
        }
        else{
            holder.tv_order_status.setTextColor(0xff313131);
        }
        holder.tv_total_cost.setText(goodsNumber + "件商品 合计：¥" + orderDto.getTotalCost());
        holder.tv_shipment_fee.setText("含运费：" + orderDto.getShipmentFee());



        /*boolean isDefault = OrderDto.getIsDefault();
        if (isDefault)
            holder.iv_default.setVisibility(View.VISIBLE);
        else
            holder.iv_default.setVisibility(View.GONE);
        holder.tv_address.setText(getAddress(orderDto));
        holder.tv_mobile.setText(orderDto.getMobile());*/
    }

    /**
     * 获得收货地址
     *
     * @param addressDB shippingAddressDB
     * @return 收货地址
     */
    /*private String getAddress(AddressDB addressDB) {
        String province = addressDB.getProvince();//省
        String city = addressDB.getCity();//市
        String district = addressDB.getDistrict();//区;
        String street = addressDB.getStreet();//街道
        return province + province + city + district + street;
    }*/

    /**
     * 收货人地址布局
     */
    static class OrderListViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_order_no)
        TextView tv_order_no;
        @Bind(R.id.ll_goods)
        LinearLayout ll_goods;
        @Bind(R.id.tv_total_cost)
        TextView tv_total_cost;
        @Bind(R.id.tv_shipment_fee)
        TextView tv_shipment_fee;
        @Bind(R.id.tv_order_purchase_time)
        TextView tv_order_purchase_time;
        @Bind(R.id.tv_order_status)
        TextView tv_order_status;


        public OrderListViewHolder(View itemView) {
            super(itemView);
        }
    }


}
