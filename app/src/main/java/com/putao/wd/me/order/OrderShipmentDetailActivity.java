package com.putao.wd.me.order;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.order.adapter.ShipmentDetailAdapter;
import com.putao.wd.model.Express;
import com.putao.wd.model.ExpressContent;
import com.putao.wd.model.Product;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.view.image.ImageDraweeView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 物流信息
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderShipmentDetailActivity extends PTWDActivity<GlobalApplication> implements RadioGroup.OnCheckedChangeListener {

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
    @Bind(R.id.rg_title_bar)
    RadioGroup rg_title_bar;
    @Bind(R.id.sc_shipment)
    ScrollView sc_shipment;
    @Bind(R.id.rl_no_shipment)
    RelativeLayout rl_no_shipment;
    @Bind(R.id.rv_shipment_detail)
    RecyclerView rv_shipment_detail;

    private Express express;
    private ArrayList<Express> expresses;
    private int packageCount;
    private int packageIndex;
    private int id = 2356890;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_shipment_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        expresses = (ArrayList<Express>) args.getSerializable(EXPRESS);
        if (null == expresses || expresses.size() == 0) return;
        rg_title_bar.removeAllViews();
        int width = (int) DensityUtil.px2dp(mContext, this.getWindowManager().getDefaultDisplay().getWidth());
        for (int i = 0; i < 3; i++) {
            RadioButton radioButton = new RadioButton(mContext);
            width = width / expresses.size();
            radioButton.getLayoutParams().width = width;
            rg_title_bar.addView(radioButton);
            radioButton.setText("包裹" + (i + 1));
        }
        rg_title_bar.setOnCheckedChangeListener(this);
//        ininDate();
//        refreshView(express);
    }

    private void ininDate() {
        packageIndex = args.getInt(PACKAGINDEX);
        express = expresses.get(packageIndex);
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        RadioButton tempButton = (RadioButton) findViewById(checkedId);
        refreshView(expresses.get(checkedId));
    }

    private void refreshView(Express express) {
        ArrayList<Product> products = express.getProduct();
        Product product = products.get(0);
        ArrayList<ExpressContent> real_content = express.getReal_content();
        if (expresses == null || expresses.size() == 0) {
            rl_product.setVisibility(View.GONE);
        } else {
            img_goods.setImageURL(product.getReal_icon());
            tv_name.setText(product.getProduct_name());
            tv_number.setText("共 " + product.getQuantity() + " 件");
            tv_package_status.setText(ShipmentState.getExpressStatusShowString(express.getState()));
        }
        if (real_content == null || real_content.size() == 0) {
            rl_product.setVisibility(View.GONE);
            rl_no_shipment.setVisibility(View.VISIBLE);
        } else {
            rl_no_shipment.setVisibility(View.GONE);
            ShipmentDetailAdapter shipmentDetailAdapter = new ShipmentDetailAdapter(mContext, real_content);
            rv_shipment_detail.setAdapter(shipmentDetailAdapter);
        }
    }
}
