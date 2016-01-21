package com.putao.wd.me.order.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.OrderShipmentListItemDto;
import com.sunnybear.library.util.Logger;


/**
 * 订单详情页面所有包裹列表的按钮，点击进入包裹详情
 * Created by yanguoqiang on 15/12/7.
 */
@Deprecated
public class OrderShipmentListItem extends LinearLayout {

    private String TAG = OrderShipmentListItem.class.getName();

    private TextView tv_shipment_title;

    private TextView tv_shipment_status;

    private OrderShipmentListItemDto orderShipmentListItemDto;

    public OrderShipmentListItem(Context context, final OrderShipmentListItemDto orderShipmentListItemDto) {
        super(context);

        LinearLayout.inflate(context, R.layout.activity_order_shipment_list_item, this);

        tv_shipment_title = (TextView) findViewById(R.id.tv_shipment_title);
        tv_shipment_status = (TextView) findViewById(R.id.tv_shipment_status);

        this.orderShipmentListItemDto = orderShipmentListItemDto;
        refreshView(orderShipmentListItemDto);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                Logger.i(TAG, "shipment item click:" + orderShipmentListItemDto.getTitle());


            }
        });

    }


    private void refreshView(OrderShipmentListItemDto orderShipmentListItemDto) {

        tv_shipment_title.setText(orderShipmentListItemDto.getTitle());
        tv_shipment_status.setText(orderShipmentListItemDto.getStatusString());

    }
}
