package com.putao.wd.pt_me.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;
import com.sunnybear.library.view.viewpager.UnScrollableViewPager;

import butterknife.Bind;


/**
 * 订单列表
 * Created by yanguoqiang on 15/11/27.
 */
public class OrderListActivity extends PTWDActivity implements TitleBar.OnTitleItemSelectedListener {
    public static final String TYPE_INDEX = "current_index";

    public static final String TYPE_ALL = "0";//全部
    public static final String TYPE_WAITING_PAY = "1";//待付款
    public static final String TYPE_WAITING_SHIPMENT = "2";//待发货
    public static final String TYPE_WAITING_SIGN = "3";//等待签收
    public static final String TYPE_AFTER_SALES = "4";//售后服务

    @Bind(R.id.ll_title_bar)
    TitleBar ll_title;
    @Bind(R.id.view_pager)
    UnScrollableViewPager viewPager;

    private String currentType = TYPE_ALL;
    private int[] fragmentIds = new int[5];

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        loading.show();
        addNavigation();
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(fragmentIds[position]);
                if (fragment == null) {
                    if (position == 4) {//售后服务
                        fragment = OrderAfterServiceListFragment.newInstance();
                    } else {
                        fragment = OrderStateListFragment.newInstance(args);
                    }
                }
                fragmentIds[position] = fragment.getId();
                return fragment;
            }

            @Override
            public int getCount() {
                return fragmentIds.length;
            }
        });
        setCurrentItem();
    }

    /**
     * 设置当前选中项
     */
    private void setCurrentItem() {
        switch (currentType) {
            case TYPE_ALL:
                ll_title.selectTitleItem(R.id.ll_all);
                break;
            case TYPE_WAITING_PAY:
                ll_title.selectTitleItem(R.id.ll_waiting_pay);
                break;
            case TYPE_WAITING_SHIPMENT:
                ll_title.selectTitleItem(R.id.ll_waiting_shipment);
                break;
            case TYPE_WAITING_SIGN:
                ll_title.selectTitleItem(R.id.ll_waiting_sign);
                break;
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 处理上方分类点击事件
     *
     * @param item
     * @param position
     */
    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        switch (item.getId()) {
            case R.id.ll_all://全部
                currentType = TYPE_ALL;
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_waiting_pay://待付款
                currentType = TYPE_WAITING_PAY;
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_waiting_shipment://待发货
                currentType = TYPE_WAITING_SHIPMENT;
                viewPager.setCurrentItem(2);
                break;
            case R.id.ll_waiting_sign://等待签收
                currentType = TYPE_WAITING_SIGN;
                viewPager.setCurrentItem(3);
                break;
            case R.id.ll_after_sale://售后
                currentType = TYPE_AFTER_SALES;
                viewPager.setCurrentItem(4);
                break;
        }
    }
}
