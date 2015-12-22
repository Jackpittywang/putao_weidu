package com.putao.wd.me.order;

import android.content.Intent;
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
public class OrderListActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener, TitleBar.TitleItemSelectedListener,OrderListAdapter.OnCancelOrder {
    private static final String PAGE_INDEX="page_index";
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
    private int currentIndex = 0;
    private List<Button> buttonList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
//        adapter.setOnCancelOrder(this);
//        adapter.setOnPayOperation(this);
        Intent intent=getIntent();
        if(intent!=null)
            currentIndex=intent.getIntExtra("current_Index",0);
        //getOrderLists(currentIndex+"", "1");
        initCurrentItem(currentIndex);
        //initTestData();
    }

    /**
     * 订单列表
     */
    private void getOrderLists(String type, String page){
        networkRequest(OrderApi.getOrderLists(type, page), new SimpleFastJsonCallback<ArrayList<Order>>(Order.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Order> result) {
                // Logger.d(result.toString());
                // 测试数据结束
                if (result == null || result.size() == 0) {
                    rl_no_order.setVisibility(View.VISIBLE);
                    rv_order.setVisibility(View.INVISIBLE);
                    return;
                } else {
                    rl_no_order.setVisibility(View.INVISIBLE);
                    rv_order.setVisibility(View.VISIBLE);
                }

                adapter = new OrderListAdapter(mContext, result);
                adapter.setOnCancelOrder(OrderListActivity.this);
                rv_order.setAdapter(adapter);
                //点击item
                rv_order.setOnItemClickListener(new OnItemClickListener<Order>() {

                    @Override
                    public void onItemClick(Order order, int position) {

                        Bundle bundle = new Bundle();
                        bundle.putSerializable(OrderDetailActivity.KEY_ORDER_ID, order.getId());
                        startActivity(OrderDetailActivity.class, bundle);
                    }
                });

                ll_title.setTitleItemSelectedListener(OrderListActivity.this);
            }

        });
    }

    /**
     * 取消订单
     */
    private void orderCancel(int order_id){
        networkRequest(OrderApi.orderCancel(order_id+""), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                Logger.d(result.toString());
                ToastUtils.showToastShort(mContext,result.toString());
            }

        });
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
                initCurrentItem(0);
                break;
            case R.id.ll_waiting_pay://待付款
                initCurrentItem(1);
                break;
            case R.id.ll_waiting_shipment://待发货
                initCurrentItem(2);
                break;
            case R.id.ll_waiting_sign://等待签收
                initCurrentItem(3);
                break;
        }
    }

    private void initCurrentItem(int position){
        switch (position) {
            case 0://全部
                getOrderLists("0","1");
                refreshViewByType(0);
//                ToastUtils.showToastLong(this, "全部");
                break;
            case 1://待付款
                getOrderLists("1","1");
                refreshViewByType(1);
//                ToastUtils.showToastLong(this, OrderCommon.getOrderStatusShowString(1));
                break;
            case 2://待发货
                getOrderLists("2","1");
                refreshViewByType(2);
//                ToastUtils.showToastLong(this, OrderCommon.getOrderStatusShowString(2));
                break;
            case 3://等待签收
                getOrderLists("3","1");
                refreshViewByType(3);
//                ToastUtils.showToastLong(this, OrderCommon.getOrderStatusShowString(3));
                break;
        }
    }


    /**
     * 根据类型刷新界面
     *
     * @param type
     */
    private void refreshViewByType(int type) {
        Logger.i(TAG, "type clicked :" + type);
    }


    @Override
    public void CancelOrder(int order_id) {
        adapter.setOnCancelOrder(this);
        orderCancel(order_id);

    }

}
