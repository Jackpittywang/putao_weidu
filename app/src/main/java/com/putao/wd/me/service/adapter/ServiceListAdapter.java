package com.putao.wd.me.service.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Service;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 售后列表
 * Created by wangou on 15/11/29.
 */
public class ServiceListAdapter extends BasicAdapter<Service, ServiceListAdapter.ServiceListViewHolder> {

    public static final String ORDER_ID = "order_id";
    private final int SERVICE_BTN_CHECK = 1;//取消申请
    private final int SERVICE_BTN_OVER = 2;//申请售后
    private final int SERVICE_BTN_AGREE = 3;//填写单号
    private final int SERVICE_BTN_SEND = 4;//不显示按钮

//    private String TAG = ServiceListAdapter.class.getName();
//    private String order_id;
//    private ServiceDto mServiceDto;
//    private Context context;
//    private Map<String, Integer> mButtonState = new HashMap<String, Integer>();

    public ServiceListAdapter(Context context, List<Service> services) {
        super(context, services);
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
    public void onBindItem(ServiceListViewHolder holder, Service service, final int position) {
//        holder.tv_service_no.setText(serviceDto.getServiceNo());
//        holder.tv_service_purchase_time.setText("2015-10-25 12:55:03");
//        holder.ll_goods.removeAllViews();
//
//        List<ServiceGoodsDto> goodsList = serviceDto.getGoodsList();
//        int goodsNumber = 0;
//        if (goodsList != null) {
//            goodsNumber = goodsList.size();
//            for (int i = 0; i < goodsNumber; i++) {
//                ServiceGoodsItem goodsItem = new ServiceGoodsItem(context, goodsList.get(i));
//                holder.ll_goods.addView(goodsItem);
//            }
//        }
//        holder.tv_service_status.setText(ServiceCommonState.getServiceStatusShowString(serviceDto.getServiceStatus()));
//
//        holder.tv_service_status.setTextColor(0xff313131);
//
//        holder.tv_total_cost.setText(goodsNumber + "件商品 合计：¥" + serviceDto.getTotalCost());
//        holder.tv_shipment_fee.setText("含运费：" + serviceDto.getShipmentFee());
//        //取消订单
//        /*holder.btn_service_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Logger.d(mOrderIds.get(position));
//                ServiceListActivity.mActivity.networkRequest(OrderApi.orderCancel(mOrderIds.get(position)),
//                        new SimpleFastJsonCallback<String>(String.class, ServiceListActivity.mLoading) {
//                            @Override
//                            public void onSuccess(String url, final String result) {
//                                if (result.equals("true")) {
//                                    ToastUtils.showToastLong(context, "取消成功");
//                                    ServiceListActivity.notifyData();
//                                } else {
//                                    ToastUtils.showToastLong(context, "取消失败");
//                                }
//                            }
//                        });
//            }
//        });*/
//        if (serviceDto.getServiceStatus() == ServiceCommonState.SERVICE_EXCHANGE_AGREE || serviceDto.getServiceStatus() == ServiceCommonState.SERVICE_REFUND_AGREE) {
//            mButtonState.put(serviceDto.getServiceNo(), SERVICE_BTN_CHECK);
//        } else if (serviceDto.getServiceStatus() == ServiceCommonState.SERVICE_REFUND_CHECK || serviceDto.getServiceStatus() == ServiceCommonState.SERVICE_EXCHANGE_CHECK ||
//                serviceDto.getServiceStatus() == ServiceCommonState.SERVICE_DRAWBACK_CHECK) {
//            mButtonState.put(serviceDto.getServiceNo(), SERVICE_BTN_OVER);
//
//        } else if (serviceDto.getServiceStatus() == ServiceCommonState.SERVICE_REFUND_OVER || serviceDto.getServiceStatus() == ServiceCommonState.SERVICE_EXCHANGE_OVER) {
//            mButtonState.put(serviceDto.getServiceNo(), SERVICE_BTN_AGREE);
//        } else {
//            mButtonState.put(serviceDto.getServiceNo(), SERVICE_BTN_SEND);
//        }
//        switch (mButtonState.get(getItem(position).getServiceNo())) {
//            case SERVICE_BTN_CHECK:
//                holder.btn_service_operation.setBackgroundResource(R.drawable.btn_order_express_selector);
//                holder.btn_service_operation.setTextColor(Color.WHITE);
//                holder.btn_service_operation.setText("填写快递单号");
//                break;
//            case SERVICE_BTN_OVER:
//                holder.btn_service_operation.setBackgroundResource(R.drawable.btn_order_state_selector);
//                holder.btn_service_operation.setTextColor(0xff985cc9);
//                holder.btn_service_operation.setText("取消申请");
//                break;
//            case SERVICE_BTN_AGREE:
//                holder.btn_service_operation.setBackgroundResource(R.drawable.btn_order_state_selector);
//                holder.btn_service_operation.setTextColor(0xff985cc9);
//                holder.btn_service_operation.setText("申请售后");
//                break;
//            case SERVICE_BTN_SEND:
//                holder.rl_foot.setVisibility(View.GONE);
//                break;
//        }
//        //订单操作
//        holder.btn_service_operation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                Intent intent = new Intent(context, OrderShipmentDetailActivity.class);
//                context.startActivity(intent);
//            }
//        });
    }


    /**
     * 收货人地址布局
     */
    static class ServiceListViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_service_no)
        TextView tv_service_no;
        @Bind(R.id.ll_goods)
        LinearLayout ll_goods;
        @Bind(R.id.rl_foot)
        RelativeLayout rl_foot;
        @Bind(R.id.tv_total_cost)
        TextView tv_total_cost;
        @Bind(R.id.tv_shipment_fee)
        TextView tv_shipment_fee;
        @Bind(R.id.tv_service_purchase_time)
        TextView tv_service_purchase_time;
        @Bind(R.id.tv_service_status)
        TextView tv_service_status;
        /*//取消订单按钮
        @Bind(R.id.btn_service_cancel)
        Button btn_service_cancel;*/
        //订单操作按钮
        @Bind(R.id.btn_service_operation)
        Button btn_service_operation;

        public ServiceListViewHolder(View itemView) {
            super(itemView);
        }
    }

}
