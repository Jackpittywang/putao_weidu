package com.putao.wd.me.order;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.OrderGoodsDto;
import com.putao.wd.dto.OrderShipmentListItemDto;
import com.putao.wd.me.order.adapter.OrderListAdapter;
import com.putao.wd.dto.OrderDto;
import com.putao.wd.model.Order;
import com.putao.wd.model.Service;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * 订单列表
 * Created by yanguoqiang on 15/11/27.
 */
public class OrderListActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener, TitleBar.TitleItemSelectedListener {

    @Bind(R.id.rv_order)
    BasicRecyclerView rv_order;
    @Bind(R.id.rl_no_order)
    RelativeLayout rl_no_order;//没有order时的布局

    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar ll_title;

    private String TAG = OrderListActivity.class.getName();
    private OrderListAdapter adapter;
    private List<OrderDto> orderList;

    // 当前第几个被选中
    private int currentIndex = -1;
    private List<Button> buttonList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        // 测试数据
        orderList = new ArrayList<OrderDto>();

        for (int i = 0; i < 10; i++) {
            OrderDto order = new OrderDto();
            order.setOrderNo(Math.random() + "");
            order.setPurchaseTime(System.currentTimeMillis());
            order.setTotalCost(569);
            order.setCustomerName("葡萄科技");
            order.setCustomerAddress("宜山路218号");
            order.setCustomerPhone("13622222222");
            if (i == 0) order.setOrderStatus(OrderCommon.ORDER_WAITING_PAY);
            else if (i == 1) order.setOrderStatus(OrderCommon.ORDER_WAITING_SHIPMENT);
            else if (i == 2) order.setOrderStatus(OrderCommon.ORDER_WAITING_SIGN);
            else if (i == 3) order.setOrderStatus(OrderCommon.ORDER_SALE_SERVICE);
            else if (i == 4) order.setOrderStatus(OrderCommon.ORDER_CANCLED);
            else if (i == 5) order.setOrderStatus(OrderCommon.ORDER_CLOSED);
            else order.setOrderStatus(OrderCommon.ORDER_WAITING_PAY);

            int num = (int) (Math.random() * 3);
            List<OrderGoodsDto> goodsList = new ArrayList<OrderGoodsDto>();
            for (int j = 0; j < num; j++) {
                OrderGoodsDto goods = new OrderGoodsDto();
                goods.setImageUrl("http://bbs.putao.com/windid/attachment/avatar/000/66/22/662295_small.jpg");
                goods.setNumber((int) Math.round(Math.random() * 10) + 1);
                goods.setPrice((int) Math.round(Math.random() * 100) + 1);
                goods.setName("葡萄探索号" + j);
                goods.setSpecification("颜色：紫色");
                if (j == 1) goods.setIsPreSale(true);
                goodsList.add(goods);

            }
            order.setGoodsList(goodsList);

            List<OrderShipmentListItemDto> shipmentList = new ArrayList<OrderShipmentListItemDto>();
            num = (int) (Math.random() * 3) + 1;
            Logger.i(TAG, "package num is:" + num);
            for (int j = 0; j < num; j++) {
                OrderShipmentListItemDto orderShipmentListItemDto = new OrderShipmentListItemDto();
                orderShipmentListItemDto.setTitle("包裹" + j);
                orderShipmentListItemDto.setStatus((int) (Math.random() * 7));
                orderShipmentListItemDto.setShipmentId(System.currentTimeMillis() + "");
                shipmentList.add(orderShipmentListItemDto);
            }
            order.setShipmentList(shipmentList);

            orderList.add(order);

        }
        // 测试数据结束
        if (orderList == null || orderList.size() == 0) {
            rl_no_order.setVisibility(View.VISIBLE);
            rv_order.setVisibility(View.INVISIBLE);
            return;
        } else {
            rl_no_order.setVisibility(View.INVISIBLE);
            rv_order.setVisibility(View.VISIBLE);
        }

        adapter = new OrderListAdapter(mContext, orderList);
        rv_order.setAdapter(adapter);
        //点击item
        rv_order.setOnItemClickListener(new OnItemClickListener<OrderDto>() {

            @Override
            public void onItemClick(OrderDto orderDto, int position) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(OrderDetailActivity.KEY_ORDER, orderDto);
                startActivity(OrderDetailActivity.class, bundle);
            }
        });

        ll_title.setTitleItemSelectedListener(this);

        refreshViewByType(0);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    // @OnClick({R.id.btn_order_all, R.id.btn_order_waiting_pay, R.id.btn_order_waiting_ship, R.id.btn_order_waiting_sign, R.id.btn_order_sale_service})

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.btn_order_all:// 全部

            //   break;
        }
    }


    /**
     * 处理上方分类点击事件
     *
     * @param item
     * @param position
     */
    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        switch (item.getId()) {
            case R.id.ll_all://全部
                refreshViewByType(0);
                ToastUtils.showToastLong(this, "全部");
                break;
            case R.id.ll_waiting_pay://待付款
                refreshViewByType(1);
                ToastUtils.showToastLong(this, OrderCommon.getOrderStatusShowString(1));
                break;
            case R.id.ll_waiting_shipment://待发货
                refreshViewByType(2);
                ToastUtils.showToastLong(this, OrderCommon.getOrderStatusShowString(2));
                break;
            case R.id.ll_waiting_sign://等待签收
                refreshViewByType(3);
                ToastUtils.showToastLong(this, OrderCommon.getOrderStatusShowString(3));
                break;
        }
    }

    /**
     * 订单列表
     */
    private void getOrderLists(){
        networkRequest(OrderApi.getOrderLists("", ""), new SimpleFastJsonCallback<ArrayList<Order>>(Order.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Order> result) {
                Logger.d(result.toString());
            }

        });
    }

    /**
     * 售后列表
     */
    private void getServiceLists(){
        networkRequest(OrderApi.getServiceLists(""), new SimpleFastJsonCallback<ArrayList<Service>>(Service.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Service> result) {
                Logger.d(result.toString());
            }

        });
    }

    /**
     * 根据类型刷新界面
     *
     * @param type
     */
    private void refreshViewByType(int type) {
        Logger.i(TAG, "type clicked :" + type);
    }


}
