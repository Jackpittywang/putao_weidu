package com.putao.wd.me.order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.OrderGoodsDto;
import com.putao.wd.me.order.OrderCommon;
import com.putao.wd.me.order.view.OrderGoodsItem;
import com.putao.wd.model.Order;
import com.putao.wd.model.OrderProduct;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderListAdapter extends BasicAdapter<Order, OrderListAdapter.OrderListViewHolder> {

    private String TAG = OrderListAdapter.class.getName();

    private Context context;

    public OrderListAdapter(Context context, List<Order> orderList) {
        super(context, orderList);
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
    public void onBindItem(OrderListViewHolder holder, final Order order, int position) {
        holder.tv_order_no.setText(order.getOrder_sn());
        holder.tv_order_purchase_time.setText("2015-10-25 12:55:03");
        holder.ll_goods.removeAllViews();

        List<OrderProduct> goodsList = order.getProduct();
        int goodsNumber = 0;
        if (goodsList != null) {
            goodsNumber = goodsList.size();
            for (int i = 0; i < goodsNumber; i++) {
                OrderGoodsItem goodsItem = new OrderGoodsItem(context, goodsList.get(i));
                holder.ll_goods.addView(goodsItem);
            }
        }
        holder.tv_order_status.setText(OrderCommon.getOrderStatusShowString(order.getOrderStatusID()));
        if(order.getOrderStatusID() == OrderCommon.ORDER_WAITING_PAY){
            holder.tv_order_status.setTextColor(0xffed5564);
        }
        else{
            holder.tv_order_status.setTextColor(0xff313131);
        }
        holder.tv_total_cost.setText(goodsNumber + "件商品 合计：¥" + order.getTotal_amount());
        holder.tv_shipment_fee.setText("含运费：" + order.getExpress_money());

        holder.btn_order_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCancelOrder.CancelOrder(order.getId());
            }
        });
        holder.btn_order_operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnPayOperation.PayOperation("",order.getId());
            }
        });

        /*boolean isDefault = Order.getIsDefault();
        if (isDefault)
            holder.iv_default.setVisibility(View.VISIBLE);
        else
            holder.iv_default.setVisibility(View.GONE);
        holder.tv_address.setText(getAddress(order));
        holder.tv_mobile.setText(order.getMobile());*/
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
        @Bind(R.id.btn_order_cancel)
        Button btn_order_cancel;//取消
        @Bind(R.id.btn_order_operation)
        Button btn_order_operation;//订单操作

        public OrderListViewHolder(View itemView) {
            super(itemView);
        }
    }

    //取消订单回调
    public interface OnCancelOrder{
        void CancelOrder(int order_id);
    }
    private OnCancelOrder mOnCancelOrder;
    public void setOnCancelOrder(OnCancelOrder onCancelOrder){
        this.mOnCancelOrder=onCancelOrder;
    }
    //支付操作
    public interface OnPayOperation{
        void PayOperation(String oper,int order_id);
    }
    private OnPayOperation mOnPayOperation;
    public void setOnPayOperation(OnPayOperation onPayOperation){
        this.mOnPayOperation=onPayOperation;
    }
}
