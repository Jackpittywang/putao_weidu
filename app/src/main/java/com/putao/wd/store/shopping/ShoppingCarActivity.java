package com.putao.wd.store.shopping;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.ShoppingCar;
import com.putao.wd.model.Cart;
import com.putao.wd.model.ProductNorms;
import com.putao.wd.model.ShopCarItem;
import com.putao.wd.store.adapter.ShoppingCarAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
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

        getCart();

        //cartEdit();
        //cartDelete();
    }

    /**
     * 查看购物车
     */
    private void getCart(){
        networkRequest(StoreApi.getCart(), new SimpleFastJsonCallback<ShopCarItem>(ShopCarItem.class, loading) {
            @Override
            public void onSuccess(String url, ShopCarItem result) {
                Logger.d(result.toString());
                List<Cart> cars = sort(result.getUse());
                adapter = new ShoppingCarAdapter(mContext, cars);
                rv_cars.setAdapter(adapter);
            }

        });
    }



    /**
     * 编辑购物车
     */
    private void cartEdit(){
        networkRequest(StoreApi.cartEdit("", ""), new SimpleFastJsonCallback<ArrayList<Cart>>(Cart.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Cart> result) {
                Logger.d(result.toString());
            }

        });
    }

    /**
     * 更改商品规格购物车
     */
    private void cartChange(){
        networkRequest(StoreApi.cartChange("", ""), new SimpleFastJsonCallback<ArrayList<Cart>>(Cart.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Cart> result) {
                Logger.d(result.toString());
            }

        });
    }

    /**
     * 删除购物车
     */
    private void cartDelete(){
        networkRequest(StoreApi.cartDelete("", ""), new SimpleFastJsonCallback<ArrayList<Cart>>(Cart.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Cart> result) {
                Logger.d(result.toString());
            }

        });
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

    private List<Cart> sort(List<Cart> cars) {
        List<Cart> list = new ArrayList<>();
        List<Cart> trueList = new ArrayList<>();
        List<Cart> falseList = new ArrayList<>();
        for (Cart car : cars) {
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

    /**
     * 标题右边按钮点击事件
     */
    @Override
    public void onRightAction() {
        if (adapter.getItemState()) {
            ToastUtils.showToastShort(this, "点击编辑");
            adapter.updateItem();
        }
    }
}
