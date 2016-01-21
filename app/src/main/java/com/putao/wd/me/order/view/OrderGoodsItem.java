package com.putao.wd.me.order.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.OrderProduct;
import com.sunnybear.library.view.image.ImageDraweeView;

/**
 * Created by yanguoqiang on 15/11/30.
 */
@Deprecated
public class OrderGoodsItem extends LinearLayout {

    private Context context;

    private TextView tv_price;

    private TextView tv_number;

    private TextView tv_name;

    private TextView tv_specification;

    private ImageDraweeView img_goods;

    public OrderGoodsItem(Context context, OrderProduct orderProduct) {
        super(context);

        this.context = context;
        LinearLayout.inflate(context, R.layout.activity_order_list_item_goods, this);

        img_goods = (ImageDraweeView) this.findViewById(R.id.img_goods);
        tv_name = (TextView) this.findViewById(R.id.tv_name);
        tv_number = (TextView) this.findViewById(R.id.tv_number);
        tv_price = (TextView) this.findViewById(R.id.tv_price);
        tv_specification = (TextView) this.findViewById(R.id.tv_specification);

        refreshView(orderProduct);
    }

    /**
     * 刷新界面
     * @param orderProduct
     */
    private void refreshView(OrderProduct orderProduct) {
        img_goods.setImageURL(orderProduct.getIcon());
//        if (orderProduct.isPreSale())
//            tv_name.setText(Html.fromHtml("<font color=0xed5564>[预售]</font>" + orderProduct.getProduct_name()));
//        else
        tv_name.setText(orderProduct.getProduct_name());
        tv_number.setText("x " + orderProduct.getProduct_number() + "");
        tv_price.setText("¥ " + orderProduct.getPrice() + "");
        //tv_specification.setText(orderProduct.getSpecification());
    }


}
