package com.putao.wd.me.service.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ServiceList;
import com.putao.wd.model.ServiceProduct;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.MathUtils;
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

    private ServiceAdapter adapter;

    public ServiceListAdapter(Context context, List<ServiceList> serviceLists) {
        super(context, serviceLists);
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
        holder.tv_service_no.setText(serviceList.getOrder_info().getOrder_sn());
        holder.tv_order_purchase_time.setText(DateUtils.secondToDate(Integer.parseInt(serviceList.getCreate_time()), "yyyy-MM-dd HH:mm:ss"));
        String statusText = checkServiceType(serviceList.getService_type(), serviceList.getStatus(), holder);
        holder.tv_service_order_status.setText(statusText);
        serviceList.setStatusText(statusText);

        List<ServiceProduct> products = serviceList.getProduct();
        String totalPrice = "0";
        int totalQt = 0;
        if (products.size() == 1) {
            totalPrice = MathUtils.multiplication(products.get(0).getSale_price(), products.get(0).getSale_quantity());
            totalQt = Integer.parseInt(products.get(0).getSale_quantity());
        } else {
            for (ServiceProduct sproduct : products) {
                totalPrice = MathUtils.add(totalPrice, MathUtils.multiplication(sproduct.getSale_price(), sproduct.getSale_quantity()));
                totalQt += Integer.parseInt(sproduct.getSale_quantity());
            }
        }
        holder.tv_order_sum_count.setText(totalQt + "");
        holder.tv_sum_money.setText(totalPrice);

        adapter = new ServiceAdapter(context, products);
        holder.rv_service_inner.setAdapter(adapter);
    }

    /**
     * 匹配售后类型
     */
    private String checkServiceType(String service_type, String status, ServiceListViewHolder holder) {
        switch (service_type) {
            case "1"://1换货
                return checkStatusReplace(service_type, status, holder);
            case "2"://2退货
                return checkStatusBackProduct(service_type, status, holder);
            case "3"://3退款
                return checkStatusBackMoney(service_type, status, holder);
        }
        return "";
    }

    /**
     * 匹配售后状态--换货
     */
    private String checkStatusReplace(String service_type, String status, ServiceListViewHolder holder) {
        switch (status) {
            case "1":
                holder.ll_operate.setVisibility(View.VISIBLE);
                holder.btn_service_right.setText("取消申请");
                return "换货请求审核中";//申请售后
            case "2":
                holder.ll_operate.setVisibility(View.VISIBLE);
                holder.btn_service_right.setText("填写快递单号");
                return "同意请求";//同意售后
            case "3":
                holder.ll_operate.setVisibility(View.GONE);
                return "换货已结束";//售后已结束
            case "4":
                holder.ll_operate.setVisibility(View.GONE);
                return "您已发货";//买家已发货
            case "5":
                holder.ll_operate.setVisibility(View.GONE);
                return "换货商品已收到";//系统已收到货
            case "6":
                holder.ll_operate.setVisibility(View.GONE);
                return "换货寄回中";//系统已发货
            case "7":
                holder.ll_operate.setVisibility(View.GONE);
                return "换货买家已收到";//买家已收货
            case "8":
                holder.ll_operate.setVisibility(View.GONE);
                return "换货已完成";//售后已完成
        }
        return "";
    }

    /**
     * 匹配售后状态--退货
     */
    private String checkStatusBackProduct(String service_type, String status, ServiceListViewHolder holder) {
        switch (status) {
            case "1":
                holder.ll_operate.setVisibility(View.VISIBLE);
                holder.btn_service_right.setText("取消申请");
                return "退货请求审核中";//申请售后
            case "2":
                holder.ll_operate.setVisibility(View.VISIBLE);
                holder.btn_service_right.setText("填写快递单号");
                return "同意请求";//同意售后
            case "3":
                holder.ll_operate.setVisibility(View.GONE);
                return "退货已结束";//售后已结束
            case "4":
                holder.ll_operate.setVisibility(View.GONE);
                return "您已发货";//买家已发货
            case "5":
                holder.ll_operate.setVisibility(View.GONE);
                return "退货已收到";//系统已收到货
            case "8":
                holder.ll_operate.setVisibility(View.GONE);
                return "退货已完成";//售后已完成
        }
        return "";
    }

    /**
     * 匹配售后状态--退款
     */
    private String checkStatusBackMoney(String service_type, String status, ServiceListViewHolder holder) {
        switch (status) {
            case "1":
                holder.ll_operate.setVisibility(View.VISIBLE);
                holder.btn_service_right.setText("取消申请");
                return "退款请求审核中";//申请售后
            case "2":
                holder.ll_operate.setVisibility(View.GONE);
                return "同意退款";//同意售后
            case "3":
                holder.ll_operate.setVisibility(View.GONE);
                return "退款已结束";//售后已结束
            case "8":
                holder.ll_operate.setVisibility(View.GONE);
                return "退款已成功";//售后已完成
        }
        return "";
    }


    /**
     * 售后视图
     */
    static class ServiceListViewHolder extends BasicViewHolder {
        @Bind(R.id.ll_operate)
        LinearLayout ll_operate;
        @Bind(R.id.rv_service_inner)
        BasicRecyclerView rv_service_inner;
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

        public ServiceListViewHolder(View itemView) {
            super(itemView);
        }
    }

}
