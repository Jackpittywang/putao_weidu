package com.putao.wd.me.service.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Service;
import com.putao.wd.model.ServiceList;
import com.putao.wd.store.order.adapter.OrdersAdapter;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 售后列表
 * Created by wangou on 15/11/29.
 */
public class ServiceListAdapter extends LoadMoreAdapter<ServiceList, ServiceListAdapter.ServiceListViewHolder> {

//    public static final String ORDER_ID = "order_id";
//    private final int SERVICE_BTN_CHECK = 1;//取消申请
//    private final int SERVICE_BTN_OVER = 2;//申请售后
//    private final int SERVICE_BTN_AGREE = 3;//填写单号
//    private final int SERVICE_BTN_SEND = 4;//不显示按钮

    private ServiceAdapter adapter;

    public ServiceListAdapter(Context context, List<ServiceList> serviceLists) {
        super(context, serviceLists);
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
    public void onBindItem(ServiceListViewHolder holder, ServiceList serviceList, final int position) {
//        holder.tv_service_no




        adapter = new ServiceAdapter(context, serviceList.getProduct());
        holder.rv_service.setAdapter(adapter);
    }


    static class ServiceListViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_service_no)
        TextView tv_service_no;
        @Bind(R.id.tv_order_purchase_time)
        TextView tv_order_purchase_time;
        @Bind(R.id.tv_service_order_status)
        TextView tv_service_order_status;
        @Bind(R.id.tv_order_sum_count)
        TextView tv_order_sum_count;
        @Bind(R.id.tv_sum_money)
        TextView tv_sum_money;
        @Bind(R.id.tv_sum_carriage)
        TextView tv_sum_carriage;
        @Bind(R.id.btn_service_right)
        Button btn_service_right;
        @Bind(R.id.rv_service)
        BasicRecyclerView rv_service;

        public ServiceListViewHolder(View itemView) {
            super(itemView);
        }
    }

}
