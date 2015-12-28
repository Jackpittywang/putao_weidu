package com.putao.wd.me.order;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.OrderDto;
import com.putao.wd.dto.OrderGoodsDto;
import com.putao.wd.dto.OrderShipmentListItemDto;
import com.putao.wd.me.order.adapter.OrderListAdapter;
import com.putao.wd.me.service.ServiceCommonState;
import com.putao.wd.model.Order;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * 订单列表
 * Created by yanguoqiang on 15/11/27.
 */
public class OrderListActivity extends PTWDActivity<GlobalApplication> implements TitleBar.OnTitleItemSelectedListener {
    public static final String TYPE_INDEX = "current_index";

    public static final String TYPE_ALL = "0";//全部
    public static final String TYPE_WAITING_PAY = "1";//待付款
    public static final String TYPE_WAITING_SHIPMENT = "2";//待发货
    public static final String TYPE_WAITING_SIGN = "3";//等待签收

    @Bind(R.id.rv_order)
    BasicRecyclerView rv_order;
    @Bind(R.id.rl_no_order)
    RelativeLayout rl_no_order;//没有order时的布局

    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar ll_title;

    private String TAG = OrderListActivity.class.getName();
    private OrderListAdapter adapter;
    private List<OrderDto> orderList;

    private int currentItem;//当前选中项目
    private int currentPage = 1;
    private String currentType = TYPE_ALL;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        currentType = args.getString(TYPE_INDEX, TYPE_ALL);


        addListener();

//        initCurrentItem(currentIndex);
        setCurrentItem();
        getOrderLists(currentType, String.valueOf(currentPage));


        // 测试数据
        /*orderList = new ArrayList<OrderDto>();

        for (int i = 0; i < 10; i++) {
            OrderDto order = new OrderDto();
            order.setOrderNo(new String(Math.random() + "").substring(3));
            order.setPurchaseTime(System.currentTimeMillis());
            order.setTotalCost(569);
            order.setCustomerName("葡萄科技");
            order.setCustomerAddress("宜山路218号");
            order.setCustomerPhone("13622222222");
            if (i == 0) order.setOrderStatus(OrderCommonState.ORDER_WAITING_PAY);
            else if (i == 1) order.setOrderStatus(OrderCommonState.ORDER_WAITING_SHIPMENT);
            else if (i == 2) order.setOrderStatus(OrderCommonState.ORDER_WAITING_SIGN);
            else if (i == 3) order.setOrderStatus(OrderCommonState.ORDER_SALE_SERVICE);
            else if (i == 4) order.setOrderStatus(OrderCommonState.ORDER_CLOSED);
            else if (i == 5) order.setOrderStatus(OrderCommonState.ORDER_CANCLED);
            else order.setOrderStatus(OrderCommonState.ORDER_NO_SIGN);

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
                OrderShipmentListItemDto serviceShipmentListItemDto = new OrderShipmentListItemDto();
                serviceShipmentListItemDto.setTitle("包裹" + j);
                serviceShipmentListItemDto.setStatus((int) (Math.random() * 7));
                serviceShipmentListItemDto.setShipmentId(System.currentTimeMillis() + "");
                shipmentList.add(serviceShipmentListItemDto);
            }
            order.setShipmentList(shipmentList);

            orderList.add(order);

        }
        if (orderList == null || orderList.size() == 0) {
            rl_no_order.setVisibility(View.VISIBLE);
            rv_order.setVisibility(View.INVISIBLE);
            return;
        } else {
            rl_no_order.setVisibility(View.INVISIBLE);
            rv_order.setVisibility(View.VISIBLE);
        }
        adapter = new OrderListAdapter(mContext, orderList);
        rv_order.setAdapter(adapter);*/

    }



    /**
     * 设置当前选中项
     */
    private void setCurrentItem() {
        switch (currentType) {
            case TYPE_ALL:
                ll_title.selectTitleItem(R.id.ll_all);
                break;
            case TYPE_WAITING_PAY:
                ll_title.selectTitleItem(R.id.ll_waiting_pay);
                break;
            case TYPE_WAITING_SHIPMENT:
                ll_title.selectTitleItem(R.id.ll_waiting_shipment);
                break;
            case TYPE_WAITING_SIGN:
                ll_title.selectTitleItem(R.id.ll_waiting_sign);
                break;
        }
    }

    private void addListener() {
        /*rv_order.setOnItemClickListener(new OnItemClickListener<Order>() {
            @Override
            public void onItemClick(Order order, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(OrderDetailActivity.KEY_ORDER_ID, order.getId());
                startActivity(OrderDetailActivity.class, bundle);
            }
        });*/
        ll_title.setOnTitleItemSelectedListener(this);
    }

    /**
     * 订单列表
     */
    private void getOrderLists(String type, String page) {
        networkRequest(OrderApi.getOrderLists(type, page),
                new SimpleFastJsonCallback<ArrayList<Order>>(Order.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Order> result) {
                        if (result != null && result.size() > 0) {
                            rl_no_order.setVisibility(View.GONE);
                            //adapter.addAll(result);
                        }
                        loading.dismiss();
                    }
                });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 处理上方分类点击事件
     *
     * @param item
     * @param position
     */
    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        currentPage = 1;
        switch (item.getId()) {
            case R.id.ll_all://全部
                currentType = TYPE_ALL;
                break;
            case R.id.ll_waiting_pay://待付款
                currentType = TYPE_WAITING_PAY;
                break;
            case R.id.ll_waiting_shipment://待发货
                currentType = TYPE_WAITING_SHIPMENT;
                break;
            case R.id.ll_waiting_sign://等待签收
                currentType = TYPE_WAITING_SIGN;
                break;
        }
        getOrderLists(currentType, String.valueOf(currentPage));
    }
}
