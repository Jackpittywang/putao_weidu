package com.putao.wd.me.order.view;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.OrderGoodsDto;
import com.sunnybear.library.view.image.ImageDraweeView;

import org.w3c.dom.Text;

/**
 * Created by yanguoqiang on 15/11/30.
 */
public class OrderGoodsItem extends LinearLayout {

    private Context context;

    private TextView tv_price;

    private TextView tv_number;

    private TextView tv_name;

    private TextView tv_specification;

    private ImageDraweeView img_goods;

    public OrderGoodsItem(Context context, OrderGoodsDto orderGoodsDto) {
        super(context);

        this.context = context;
        LinearLayout.inflate(context, R.layout.activity_order_list_item_goods, this);

        img_goods = (ImageDraweeView) this.findViewById(R.id.img_goods);
        tv_name = (TextView) this.findViewById(R.id.tv_name);
        tv_number = (TextView) this.findViewById(R.id.tv_number);
        tv_price = (TextView) this.findViewById(R.id.tv_price);
        tv_specification = (TextView) this.findViewById(R.id.tv_specification);

        refreshView(orderGoodsDto);
    }

    /**
     * 刷新界面
     * @param orderGoodsDto
     */
    private void refreshView(OrderGoodsDto orderGoodsDto) {
        img_goods.setImageURL(orderGoodsDto.getImageUrl());
        if (orderGoodsDto.isPreSale())
            tv_name.setText(Html.fromHtml("<font color=0xed5564>[预售]</font>" + orderGoodsDto.getName()));
        else tv_name.setText(orderGoodsDto.getName());
        tv_number.setText("x " + orderGoodsDto.getNumber() + "");
        tv_price.setText("¥ " + orderGoodsDto.getPrice() + "");
        tv_specification.setText(orderGoodsDto.getSpecification());
    }


}
