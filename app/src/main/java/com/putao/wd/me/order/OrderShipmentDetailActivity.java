package com.putao.wd.me.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Express;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 物流信息
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderShipmentDetailActivity extends PTWDActivity<GlobalApplication> {

    public static final String EXPRESS = "express";
    public static final String PACKAGECOUNT = "packageCount";
    public static final String PACKAGINDEX = "packageIndex";
    private Express express;
    private ArrayList<Express> expresses;
    private ArrayList<String> titles;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_shipment_detail2;
        // return R.layout.activity_order_shipment_detail;
    }

    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.indicator)
    TabPageIndicator indicator;
    private int current;

    //private static final String[] CONTENT = new String[]{"包裹1", "包裹2", "包裹3", "包裹4", "包裹5", "包裹6"};

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        titles = new ArrayList<>();
        expresses = (ArrayList<Express>) args.getSerializable(EXPRESS);
        current = args.getInt(PACKAGINDEX);


        if (null == expresses || expresses.size() == 0) {
            titles.add("包裹1");
            titles.add("包裹1");
            titles.add("包裹1");
            titles.add("包裹1");
            titles.add("包裹1");
            titles.add("包裹1");
            titles.add("包裹1");
            Express defail = new Express();
            expresses = new ArrayList<>();
            expresses.add(defail);
            expresses.add(defail);
            expresses.add(defail);
            expresses.add(defail);
            expresses.add(defail);
            expresses.add(defail);
            expresses.add(defail);
            /*TextView text = new TextView(getApplicationContext());
            text.setGravity(Gravity.CENTER);
            text.setText("沒有包裹");*/
            //pager.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < expresses.size(); i++) {
                titles.add("包裹" + i);
            }
        }
        FragmentPagerAdapter adapter = new OrderShipmentDetail(getSupportFragmentManager());
        pager.setAdapter(adapter);

        indicator.setViewPager(pager);
        indicator.setCurrentItem(current);
    }

    class OrderShipmentDetail extends FragmentPagerAdapter {

        public OrderShipmentDetail(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //设置fragment   CONTENT[position % CONTENT.length]
            return OrderShipmentDetailFragment.newInstance(expresses.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // return CONTENT[position % CONTENT.length].toUpperCase();
            return titles.get(position);
        }

        @Override
        public int getCount() {
            return titles.size();
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

   /* @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        RadioButton tempButton = (RadioButton) findViewById(checkedId);
        group.check(checkedId);
//        refreshView(expresses.get(checkedId));
    }*/


   /* private void refreshView(Express express) {
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
    }*/
}
