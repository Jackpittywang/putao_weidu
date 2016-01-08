package com.putao.wd.me.order;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.order.adapter.ShipmentDetailAdapter;
import com.putao.wd.model.Express;
import com.putao.wd.model.ExpressContent;
import com.putao.wd.model.Product;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 物流信息
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderShipmentDetailActivity extends PTWDActivity<GlobalApplication> implements TitleBar.OnTitleItemSelectedListener {

    public static final String EXPRESS = "express";
    public static final String PACKAGECOUNT = "packageCount";
    public static final String PACKAGINDEX = "packageIndex";
    /*  @Bind(R.id.hsv_package_list)
      HorizontalScrollView hsv_package_list;// 包裹列表的horizontalscrollview*/
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
    @Bind(R.id.rl_product)
    RelativeLayout rl_product;
    @Bind(R.id.tv_package_status)
    TextView tv_package_status;
    @Bind(R.id.ll_package_list)
    LinearLayout ll_package_list;
    @Bind(R.id.ll_title_bar)
    TitleBar ll_title_bar;
    @Bind(R.id.ll_package1)
    TitleItem ll_package1;
    @Bind(R.id.ll_package2)
    TitleItem ll_package2;
    @Bind(R.id.rv_shipment_detail)
    BasicRecyclerView rv_shipment_detail;//物流列表详细

    private Express express;
    private ArrayList<Express> expresses;
    private int packageCount;
    private int packageIndex;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_shipment_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        expresses = (ArrayList<Express>) args.getSerializable(EXPRESS);
        if (expresses == null || expresses.size() == 0) {
            rl_product.setVisibility(View.GONE);
            return;
        }
        ininDate();
        addListener();
        refreshView(express);
    }

    private void ininDate() {
        packageIndex = args.getInt(PACKAGINDEX);
        express = expresses.get(packageIndex);
    }

    private void addListener() {
        ll_title_bar.setOnTitleItemSelectedListener(this);
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void refreshView(Express express) {
        ArrayList<Product> products = express.getProduct();
        Product product = products.get(0);
        img_goods.setImageURL(product.getReal_icon());
        tv_name.setText(product.getProduct_name());
        tv_number.setText("共 " + product.getQuantity() + " 件");
        tv_package_status.setText(ShipmentState.getExpressStatusShowString(express.getState()));
        switch (packageIndex) {
            case 0:
                ll_title_bar.selectTitleItem(R.id.ll_package1);
                break;
            case 1:
                ll_title_bar.selectTitleItem(R.id.ll_package2);
                break;
            default:
                ll_title_bar.selectTitleItem(R.id.ll_package2);
                break;
        }
        ArrayList<ExpressContent> real_content = express.getReal_content();
        ShipmentDetailAdapter shipmentDetailAdapter = new ShipmentDetailAdapter(mContext, real_content);
        rv_shipment_detail.setAdapter(shipmentDetailAdapter);
    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        packageIndex = position;
        refreshView(expresses.get(position));
    }
}
