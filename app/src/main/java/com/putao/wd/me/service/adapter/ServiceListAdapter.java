package com.putao.wd.me.service.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Service;
import com.putao.wd.store.order.adapter.OrdersAdapter;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
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

    private OrdersAdapter adapter;

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


//        adapter = new OrdersAdapter(context, order.getProduct());
//        holder.rv_service.setAdapter(adapter);
    }


    /**
     * 收货人地址布局
     */
    static class ServiceListViewHolder extends BasicViewHolder {
        @Bind(R.id.rv_service)
        BasicRecyclerView rv_service;
//        @Bind(R.id.ll_goods)
//        LinearLayout ll_goods;
//        @Bind(R.id.rl_foot)
//        RelativeLayout rl_foot;
//        @Bind(R.id.tv_total_cost)
//        TextView tv_total_cost;
//        @Bind(R.id.tv_shipment_fee)
//        TextView tv_shipment_fee;
//        @Bind(R.id.tv_service_purchase_time)
//        TextView tv_service_purchase_time;
//        @Bind(R.id.tv_service_status)
//        TextView tv_service_status;
//        @Bind(R.id.btn_service_operation)
//        Button btn_service_operation;

        public ServiceListViewHolder(View itemView) {
            super(itemView);
        }
    }

}
