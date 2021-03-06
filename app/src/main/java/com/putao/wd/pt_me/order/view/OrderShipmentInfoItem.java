package com.putao.wd.pt_me.order.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.OrderShipmentInfoItemDto;


/**
 * 物流详情里面显示物流详细状态的item
 * Created by yanguoqiang on 15/12/3.
 */
@Deprecated
public class OrderShipmentInfoItem extends LinearLayout {

    private Context context;

    private TextView tv_shipment_info;

    private ImageView img_status_icon;

    private View v_top_line;

    private View v_top_line_cover;

    private View v_bottom_line;

    public OrderShipmentInfoItem(Context context, OrderShipmentInfoItemDto orderShipmentInfoItemDto) {
        super(context);

        this.context = context;
        LinearLayout.inflate(context, R.layout.activity_order_shipment_info_item, this);
        v_top_line = this.findViewById(R.id.v_top_line);
        v_top_line_cover = this.findViewById(R.id.v_top_line_cover);
        v_bottom_line = this.findViewById(R.id.v_bottom_line);
        img_status_icon = (ImageView) this.findViewById(R.id.img_status_icon);
        tv_shipment_info = (TextView) this.findViewById(R.id.tv_shipment_info);

        refreshView(orderShipmentInfoItemDto);
    }

    /**
     * 刷新界面
     *
     * @param orderShipmentInfoItemDto
     */
    private void refreshView(OrderShipmentInfoItemDto orderShipmentInfoItemDto) {
        if (orderShipmentInfoItemDto.isShowHighLightImage())
            img_status_icon.setImageResource(R.drawable.icon_logistics_flow_latest);
        else img_status_icon.setImageResource(R.drawable.icon_logistics_flow_old);

        if (orderShipmentInfoItemDto.isShowTopLine() == true && orderShipmentInfoItemDto.isShowBottomLine()== false) {
            // 底部只显示上面的线条
            v_top_line.setVisibility(View.VISIBLE);
            v_top_line_cover.setVisibility(View.INVISIBLE);
            v_bottom_line.setVisibility(View.INVISIBLE);
            tv_shipment_info.setTextColor(0x64313131);
        } else if (orderShipmentInfoItemDto.isShowTopLine() == true && orderShipmentInfoItemDto.isShowBottomLine()==true){
            // 中间显示全部线条
            v_top_line.setVisibility(View.VISIBLE);
            v_top_line_cover.setVisibility(View.INVISIBLE);
            v_bottom_line.setVisibility(View.VISIBLE);
            tv_shipment_info.setTextColor(0xA0313131);
        }else if (orderShipmentInfoItemDto.isShowTopLine() == false && orderShipmentInfoItemDto.isShowBottomLine()==true){
            // 顶部只显示下面的线条
            v_top_line.setVisibility(View.INVISIBLE);
            v_top_line_cover.setVisibility(View.VISIBLE);
            v_bottom_line.setVisibility(View.VISIBLE);
            tv_shipment_info.setTextColor(0xff313131);
        }
        else{
            v_top_line.setVisibility(View.INVISIBLE);
            v_top_line_cover.setVisibility(View.VISIBLE);
            v_bottom_line.setVisibility(View.INVISIBLE);
        }

        tv_shipment_info.setText(orderShipmentInfoItemDto.getShipmentInfo());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        // 更新bottom_line的高度，否则高度会是0
        ViewGroup.LayoutParams params = v_bottom_line.getLayoutParams();
        params.height = b - t;
        v_bottom_line.setLayoutParams(params);
    }
}
