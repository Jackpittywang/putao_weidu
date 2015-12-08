package com.putao.wd.store.order;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.OrderListItem;
import com.putao.wd.store.order.adapter.OrdersAdapter;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.sticky.StickyHeaderLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 填写订单
 * Created by wangou on 2015/12/08.
 */
public class WriteOrderActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.sticky_layout)
    StickyHeaderLayout sticky_layout;
    @Bind(R.id.iv_reapte_picbar)
    ImageView iv_reapte_picbar;//分割图片
    @Bind(R.id.stickyHeaderLayout_scrollable)
    BasicRecyclerView brv_write_orderlist;//订单列表

    private OrdersAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_order;
    }


    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        sticky_layout.canScrollView();
        ImageUtils.fillXInImageView(mContext, iv_reapte_picbar, BitmapFactory.decodeResource(getResources(), R.drawable.img_cart_lace_stuff));

        //初始化列表数据
        List<OrderListItem> cars = getTestData();
        adapter = new OrdersAdapter(mContext, cars);
        brv_write_orderlist.setAdapter(adapter);

    }

    private List<OrderListItem> getTestData() {
        List<OrderListItem> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderListItem car = new OrderListItem();
            car.setTitle("葡萄探索号.虚拟+现实儿童科技益智玩具");
            car.setColor("塔塔紫");
            car.setSize("均码");
            car.setMoney("399.00");
            car.setCount("2");
            car.setIsNull(i % 2 == 1);
            list.add(car);
        }
        return list;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
