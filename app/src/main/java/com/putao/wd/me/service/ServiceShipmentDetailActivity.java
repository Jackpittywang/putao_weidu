package com.putao.wd.me.service;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.order.adapter.ShipmentDetailAdapter;
import com.putao.wd.model.Express;
import com.putao.wd.model.ExpressContent;
import com.sunnybear.library.view.recycler.BasicRecyclerView;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 售后物流信息
 * Created by wangou on 15/11/29.
 */
public class ServiceShipmentDetailActivity extends PTWDActivity<GlobalApplication> {

    public static final String KEY_EXPRESS_INFO = "express_info";

    @Bind(R.id.tv_company_name)
    TextView tv_company_name;
    @Bind(R.id.tv_express_number)
    TextView tv_express_number;
    @Bind(R.id.tv_express_status)
    TextView tv_express_status;
    @Bind(R.id.rv_shipment_detail)
    BasicRecyclerView rv_shipment_detail;


    private List<Express> express;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_shipment_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        express = (List<Express>)args.get(KEY_EXPRESS_INFO);
        if (null != express && express.size() > 0) {
            initContent(express.get(0));
        }

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 初始化text显示
     */
    private void initContent(Express express) {
        tv_company_name.setText(express.getExpress_name());
        tv_express_number.setText(express.getExpress_code());
        tv_express_status.setText(express.getExpress_message());
        //填充物流信息
        ArrayList<ExpressContent> real_content = express.getReal_content();
        ShipmentDetailAdapter shipmentDetailAdapter = new ShipmentDetailAdapter(mContext, real_content);
        rv_shipment_detail.setAdapter(shipmentDetailAdapter);
    }

//  售后物流接口文档已弃用
//        networkRequest(OrderApi.getExpressService(orderId), new SimpleFastJsonCallback<String>(String.class, loading) {
//            @Override
//            public void onSuccess(String url, String result) {
//                Logger.d("售后物流 " + result);
//                loading.dismiss();
//            }
//        });

}


