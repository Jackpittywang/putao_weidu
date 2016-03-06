package com.putao.wd.me.order;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.me.order.adapter.ShipmentDetailAdapter;
import com.putao.wd.model.Express;
import com.putao.wd.model.ExpressContent;
import com.putao.wd.model.Product;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.image.ImageDraweeView;

import java.util.ArrayList;

import butterknife.Bind;


public class OrderShipmentDetailFragment extends BasicFragment {
    private static final String KEY_CONTENT = "OrderShipmentDetailFragment:Content";


    public static final String EXPRESS = "express";
    public static final String PACKAGECOUNT = "packageCount";
    public static final String PACKAGINDEX = "packageIndex";
      /*@Bind(R.id.hsv_package_list)
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


    @Bind(R.id.sc_shipment)
    ScrollView sc_shipment;

    @Bind(R.id.rv_shipment_detail)
    RecyclerView rv_shipment_detail;
    private Express express;
    private ArrayList<Express> expresses;
    private int id = 2356890;

    public static OrderShipmentDetailFragment newInstance(Express express) {
        OrderShipmentDetailFragment fragment = new OrderShipmentDetailFragment();

        fragment.mContent = express;

        return fragment;
    }

    private Express mContent ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
          //  mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString(KEY_CONTENT, mContent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ordershipmentdetail;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        refreshView(mContent);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

     private void refreshView(Express express) {
        ArrayList<Product> products = express.getProduct();
         ArrayList<ExpressContent> real_content = express.getReal_content();
         if(null==express.getProduct()){
             rl_product.setVisibility(View.GONE);
         }else{
             Product product = products.get(0);
            img_goods.setImageURL(product.getReal_icon());
            tv_name.setText(product.getProduct_name());
            tv_number.setText("共 " + product.getQuantity() + " 件");
            tv_package_status.setText(ShipmentState.getExpressStatusShowString(express.getState()));
        }
        if (real_content == null || real_content.size() == 0) {
           // rl_product.setVisibility(View.GONE);
           // rl_no_shipment.setVisibility(View.VISIBLE);
        } else {
           // rl_no_shipment.setVisibility(View.GONE);
            ShipmentDetailAdapter shipmentDetailAdapter = new ShipmentDetailAdapter(getActivity(), real_content);
            rv_shipment_detail.setAdapter(shipmentDetailAdapter);
        }
    }


}
