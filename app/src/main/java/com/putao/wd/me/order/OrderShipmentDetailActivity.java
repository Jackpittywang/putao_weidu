package com.putao.wd.me.order;

import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.OrderShipmentDto;
import com.putao.wd.dto.OrderShipmentInfoItemDto;
import com.putao.wd.me.order.view.OrderShipmentInfoItem;
import com.sunnybear.library.view.image.ImageDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderShipmentDetailActivity extends PTWDActivity<GlobalApplication> {

    public static final String KEY_ORDER_UUID = "orderUuid";

    private String orderUuid = "";

    private ImageDraweeView img_goods;

    private TextView tv_name;

    private TextView tv_number;

    private TextView tv_package_status;

    private LinearLayout ll_package_shipment;

    // 包裹列表的horizontalscrollview
    private HorizontalScrollView hsv_package_list;
    // 放包裹列表的布局
    private LinearLayout ll_package_list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_shipment_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

        addNavigation();

        orderUuid = args.getString(KEY_ORDER_UUID);
        if (orderUuid == null) orderUuid = "";

        initComponent();
        //开始请求包裹信息;


        // 测试数据
        OrderShipmentDto orderShipmentDto = new OrderShipmentDto();
        orderShipmentDto.setIconUrl("http://bbs.putao.com/windid/attachment/avatar/000/66/22/662295_small.jpg");
        orderShipmentDto.setName("葡萄探索号包裹，快递一天到达");
        orderShipmentDto.setNumber(3);
        List<String> infoArr = new ArrayList<String>();
        infoArr.add("再上海宝山区发货再上海宝山区发货再上海宝山区发货再上海宝山区发货再上海宝山区发货再上海宝山区发货再上海宝山区发货再上海宝山区发货");
        infoArr.add("再上海宝山区发货22222再上海宝山区发货22222再上海宝山区发货22222再上海宝山区发货22222再上海宝山区发货22222再上海宝山区发货22222再上海宝山区发货22222");
        infoArr.add("再上海宝山区发货33333333");
        infoArr.add("再上海宝山区发货444444");
        orderShipmentDto.setShipmentInfoList(infoArr);

        //刷新界面
        refreshView(orderShipmentDto);

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void initComponent() {
        img_goods = (ImageDraweeView) findViewById(R.id.img_goods);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_package_status = (TextView) findViewById(R.id.tv_package_status);
        ll_package_shipment = (LinearLayout) findViewById(R.id.ll_package_shipment);
    }

    private void refreshView(OrderShipmentDto orderShipmentDto) {

        img_goods.setImageURL(orderShipmentDto.getIconUrl());
        tv_name.setText(orderShipmentDto.getName());
        tv_number.setText("共" + orderShipmentDto.getNumber() + "件");

        List<String> infoArr = orderShipmentDto.getShipmentInfoList();
        if (infoArr == null || infoArr.size() < 1) {
            tv_package_status.setText("还没有物流信息");
            tv_package_status.setTextColor(0xff959595);
            ll_package_shipment.setVisibility(View.INVISIBLE);
            return;
        }
        tv_package_status.setText("有物流信息");
        tv_package_status.setTextColor(0xff985ec9);
        ll_package_shipment.setVisibility(View.VISIBLE);
        ll_package_shipment.removeAllViews();
        int infoSize = infoArr.size();
        for (int i = 0; i < infoSize; i++) {
            OrderShipmentInfoItemDto orderShipmentInfoItemDto = new OrderShipmentInfoItemDto();
            if (i == 0) orderShipmentInfoItemDto.setShowHighLightImage(true);
            else
                orderShipmentInfoItemDto.setShowHighLightImage(false);

            if (i == 0) { // 第一个
                orderShipmentInfoItemDto.setShowTopLine(false);
                orderShipmentInfoItemDto.setShowBottomLine(true);
            } else if (infoSize > 1 && i == infoSize - 1) { //最后一个
                orderShipmentInfoItemDto.setShowTopLine(true);
                orderShipmentInfoItemDto.setShowBottomLine(false);
            } else { //中间的
                orderShipmentInfoItemDto.setShowTopLine(true);
                orderShipmentInfoItemDto.setShowBottomLine(true);
            }
            orderShipmentInfoItemDto.setShipmentInfo(infoArr.get(i));
            OrderShipmentInfoItem orderShipmentInfoItem = new OrderShipmentInfoItem(this, orderShipmentInfoItemDto);
            ll_package_shipment.addView(orderShipmentInfoItem);
        }


    }

}
