package com.putao.wd.store.shopping;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.ShoppingCar;
import com.putao.wd.store.adapter.ShoppingCarAdapter;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 购物车
 * Created by guchenkai on 2015/12/4.
 */
public class ShoppingCarActivity extends PTWDActivity implements View.OnClickListener, SwitchButton.OnSwitchClickListener {
    //    @Bind(R.id.rv_cars_info)
//    BasicRecyclerView rv_cars_info;
//    @Bind(R.id.rv_cars_null)
//    BasicRecyclerView rv_cars_null;
    @Bind(R.id.rv_cars)
    BasicRecyclerView rv_cars;
    @Bind(R.id.btn_sel_all)
    SwitchButton btn_sel_all;
    @Bind(R.id.tv_money)
    TextView tv_money;

    private ShoppingCarAdapter adapter;
    private boolean isSelectAll = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_car;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        addListener();

        List<ShoppingCar> cars = sort(getTestData());
        adapter = new ShoppingCarAdapter(mContext, cars);
        rv_cars.setAdapter(adapter);
    }

    private void addListener() {
        btn_sel_all.setOnSwitchClickListener(this);
    }

    private List<ShoppingCar> getTestData() {
        List<ShoppingCar> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ShoppingCar car = new ShoppingCar();
            car.setTitle("葡萄探索号.虚拟+现实儿童科技益智玩具");
            car.setColor("塔塔紫");
            car.setSize("均码");
            car.setMoney("399.00");
            car.setCount("2");
            car.setIsNull(i % 2 == 1);
            list.add(car);
        }
        return list;
    }

    private List<ShoppingCar> sort(List<ShoppingCar> cars) {
        List<ShoppingCar> list = new ArrayList<>();
        List<ShoppingCar> trueList = new ArrayList<>();
        List<ShoppingCar> falseList = new ArrayList<>();
        for (ShoppingCar car : cars) {
            if (!car.isNull())
                trueList.add(car);
            else
                falseList.add(car);
        }
        list.addAll(trueList);
        list.addAll(falseList);
        return list;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.ll_closing, R.id.ll_all})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_all://全选
                isSelectAll = isSelectAll ? false : true;
                btn_sel_all.setState(isSelectAll);
                adapter.selAll(isSelectAll);
                break;
            case R.id.ll_closing://去结算

                break;
        }
    }

    /**
     * 全选监听器
     */
    @Override
    public void onSwitchClick(View v, boolean isSelect) {
        adapter.selAll(isSelect);
    }
}
