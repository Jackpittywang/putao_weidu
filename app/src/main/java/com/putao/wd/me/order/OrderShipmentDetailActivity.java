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
import com.putao.wd.model.Express;
import com.putao.wd.model.Product;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 物流信息
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderShipmentDetailActivity extends PTWDActivity<GlobalApplication> {

    public static final String EXPRESS = "express";
    @Bind(R.id.hsv_package_list)
    HorizontalScrollView hsv_package_list;// 包裹列表的horizontalscrollview
    @Bind(R.id.tv_order_goods_text)
    TextView tv_order_goods_text;
    @Bind(R.id.img_goods)
    ImageDraweeView img_goods;//图片
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_number)
    TextView tv_number;
    @Bind(R.id.tv_shipment_text)
    TextView tv_shipment_text;
    @Bind(R.id.tv_package_status)
    TextView tv_package_status;
    @Bind(R.id.rv_shipment_detail)
    BasicRecyclerView rv_shipment_detail;//物流列表详细

    private Express express;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_shipment_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        //开始请求包裹信息;
        express = (Express) args.getSerializable(EXPRESS);
        if (express == null) finish();
        ArrayList<Product> products = express.getProduct();
        Product product = products.get(0);
        img_goods.setImageURL(product.getReal_icon());
        tv_name.setText(product.getProduct_name());
        tv_number.setText("共 "+product.getQuantity()+" 件");
        tv_package_status.setText(ShipmentState.getExpressStatusShowString(express.getState()));



    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void refreshView(OrderShipmentDto orderShipmentDto) {


    }

}
