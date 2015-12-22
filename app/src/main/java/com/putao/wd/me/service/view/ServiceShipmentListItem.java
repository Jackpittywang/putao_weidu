package com.putao.wd.me.service.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ServiceShipmentListItemDto;
import com.sunnybear.library.util.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 售后订单详情页面所有包裹列表的按钮，点击进入包裹详情
 * Created by yanguoqiang on 15/12/7.
 */
public class ServiceShipmentListItem extends LinearLayout {

    @Bind(R.id.tv_shipment_title)
    TextView tv_shipment_title;
    @Bind(R.id.tv_shipment_status)
    TextView tv_shipment_status;
    @Bind(R.id.v_bottom_line)
    View v_bottom_line;
    private String TAG = ServiceShipmentListItem.class.getName();

//    private TextView tv_shipment_title;
//
//    private TextView tv_shipment_status;

    private ServiceShipmentListItemDto serviceShipmentListItemDto;

    public ServiceShipmentListItem(Context context, final ServiceShipmentListItemDto serviceShipmentListItemDto) {
        super(context);

        View view = LinearLayout.inflate(context, R.layout.activity_service_shipment_list_item, this);
        ButterKnife.bind(view, this);

//        tv_shipment_status = (TextView) findViewById(R.id.tv_shipment_status);

        this.serviceShipmentListItemDto = serviceShipmentListItemDto;
        refreshView(serviceShipmentListItemDto);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                Logger.i(TAG, "shipment item click:" + serviceShipmentListItemDto.getTitle());


            }
        });

    }


    private void refreshView(ServiceShipmentListItemDto serviceShipmentListItemDto) {

        tv_shipment_title.setText(serviceShipmentListItemDto.getTitle());
        tv_shipment_status.setText(serviceShipmentListItemDto.getStatusString());

    }
}
