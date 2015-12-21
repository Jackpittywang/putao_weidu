package com.putao.wd.store.shopping;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.ShoppingCar;
import com.putao.wd.explore.manage.UseCountEveryDayFragment;
import com.putao.wd.model.Cart;
import com.putao.wd.model.EditShopCart;
import com.putao.wd.model.ProductNormsSku;
import com.putao.wd.model.ShopCarItem;
import com.putao.wd.store.cashier.CashierActivity;
import com.putao.wd.store.product.ShoppingCarPopupWindow;
import com.putao.wd.store.product.adapter.NormsSelectAdapter;
import com.putao.wd.store.shopping.adapter.ShoppingCarAdapter;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.MathUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 购物车
 * Created by guchenkai on 2015/12/4.
 */
public class ShoppingCarActivity extends PTWDActivity implements View.OnClickListener, SwitchButton.OnSwitchClickListener,ShoppingCarAdapter.OnUpdateNorms{
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
    @Bind(R.id.ll_money)
    LinearLayout ll_money;//金额区域
    @Bind(R.id.ll_closing)
    LinearLayout ll_closing;//结算区域
    @Bind(R.id.tv_closing)
    TextView tv_closing;//结算
    @Bind(R.id.rl_shopping_car)
    RelativeLayout rl_shopping_car;//购物车布局

    private ShoppingCarAdapter adapter;
    private boolean isSelectAll = false;
    private boolean isEditable=true;
    private ShoppingCarPopupWindow mShoppingCarPopupWindow;//购物车弹窗
    private int update_position=-1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_car;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        addListener();
        getCart();

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
                adapter.setOnUpdateNorms(ShoppingCarActivity.this);
                adapter.notifyDataSetChanged();
                tv_money.setText(caculateSumMoney());
            }

        });
    }

    /**
     * 编辑购物车
     */
    private void multiManage(List products){
        networkRequest(StoreApi.multiManage(products), new SimpleFastJsonCallback<ShopCarItem>(ShopCarItem.class, loading) {
            @Override
            public void onSuccess(String url, ShopCarItem result) {
                Logger.d(result.toString());
            }

        });
    }

    private String caculateSumMoney(){
        String sum="0";
        for(Cart cart:adapter.ShoppingCarts){
            sum=MathUtils.add(sum,MathUtils.multiplication(cart.getPrice(), cart.getQt()));
        }
        return sum;
    }



    /**
     * 删除购物车
     */
    private void cartDelete(String pid){
        networkRequest(StoreApi.cartDelete(pid), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                Logger.d(result.toString());
                getCart();

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
            case R.id.ll_closing://去结算/删除
                if(isEditable) {
                    startActivity(CashierActivity.class);
                }
                else{
                    Iterator iter = adapter.map.keySet().iterator();
                    while (iter.hasNext()) {
                        Object key = iter.next();
                        Cart cart = adapter.map.get(key);
                        cartDelete(cart.getPid());
                        tv_money.setText(caculateSumMoney());
                    }
                    setRightTitle("编辑");
                    isEditable=true;

                }

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
            if(isEditable && adapter.map.size()!=0) {//编辑
                ToastUtils.showToastShort(this, "点击编辑");
                adapter.updateItem();//变成可编辑
                setRightTitle("完成");
                isEditable=false;
                ll_money.setVisibility(View.INVISIBLE);
                tv_closing.setText("删除");
            }else {//完成
                adapter.selectedmap=adapter.map;
                Iterator iter = adapter.map.keySet().iterator();
                List<EditShopCart> products=new ArrayList<>();
                EditShopCart editShopCart;
                while (iter.hasNext()) {
                    Object key = iter.next();
                    editShopCart=new EditShopCart();
                    //Cart cart = adapter.map.get(key);
                    editShopCart.setPid((adapter.ShoppingCarts.get((int)key).getPid()));
                    editShopCart.setQt((adapter.ShoppingCarts.get((int)key).getQt()));
                    products.add(editShopCart);
                }
                multiManage(products);
                tv_money.setText(caculateSumMoney());
                //getCart();
                isEditable=true;
                setRightTitle("编辑");
                adapter.recoverItem();//变成不可编辑

                ll_money.setVisibility(View.VISIBLE);
                tv_closing.setText("结算");
            }
        }
    }

    //修改规格参数
    @Override
    public void updateNorms(String pid,int position) {
        this.update_position=position;
        mShoppingCarPopupWindow = new ShoppingCarPopupWindow(mContext,pid,"update");
        mShoppingCarPopupWindow.show(rl_shopping_car);
    }

    @Subcriber(tag = ShoppingCarPopupWindow.EVENT_UPDATE_NORMS)
    public void eventUpdateNorms(ProductNormsSku sku) {
        adapter.updateUINorm(update_position,sku);
    }
}
