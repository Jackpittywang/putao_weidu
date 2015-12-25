package com.putao.wd.store.shopping;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Cart;
import com.putao.wd.model.CartEdit;
import com.putao.wd.model.ShopCarItem;
import com.putao.wd.store.order.WriteOrderActivity;
import com.putao.wd.store.product.EditShoppingCarPopupWindow;
import com.putao.wd.store.shopping.adapter.ShoppingCarAdapter;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.MathUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 编辑购物车规格
 * Created by wangou on 2015/12/4.
 */
public class ShoppingCarActivity extends PTWDActivity implements View.OnClickListener, SwitchButton.OnSwitchClickListener {
    private final String PAY = "去结算";
    private final String DELETE = "删除";
    private final String SAVE = "保存";
    private final String EDIT = "编辑";
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
    @Bind(R.id.rl_empty)
    RelativeLayout rl_empty;//空页面

    private ShoppingCarAdapter adapter;
    private boolean isSelectAll = false;
    private boolean saveable = false;//保存按钮标志
    private boolean isEditable = true;
    private EditShoppingCarPopupWindow mEditShoppingCarPopupWindow;//购物车弹窗
    private int update_position = -1;

    private int currentPosition;//当前修改的位置
    private Cart mCart;//当前修改的位置

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_car;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        navigation_bar.setRightAction(false);
        setRightTitleColor(R.color.text_color_gray);

        adapter = new ShoppingCarAdapter(mContext, null);
        rv_cars.setAdapter(adapter);
        addListener();
        getCart();
    }

    /**
     * 查看购物车
     */
    private void getCart() {
        networkRequest(StoreApi.getCart(), new SimpleFastJsonCallback<ShopCarItem>(ShopCarItem.class, loading) {
            @Override
            public void onSuccess(String url, ShopCarItem result) {
//                List<Cart> cars = sort(result.getUse());
                adapter.addAll(result.getUse());
                tv_money.setText(caculateSumMoney(result.getUse()));
                loading.dismiss();
            }
        });
    }

    /**
     * 编辑购物车
     */
    private void multiManage(List products) {

    }

    /**
     * 删除购物车
     */
    private void cartDelete(String pid) {
        networkRequest(StoreApi.cartDelete(pid), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                Logger.d(result.toString());
                getCart();
            }
        });
    }

    /**
     * 计算购物车商品总金额
     */
    private String caculateSumMoney(List<Cart> carts) {
        String sum = "0";
        for (Cart cart : carts) {
            sum = MathUtils.add(sum, MathUtils.multiplication(cart.getPrice(), cart.getQt()));
        }
        return sum;
    }


    private void addListener() {
        btn_sel_all.setOnSwitchClickListener(this);
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
                switch (tv_closing.getText().toString()) {
                    case PAY:
                        startActivity(WriteOrderActivity.class);
                        break;
                    case DELETE:

                        //删除操作

                        break;
                }
                break;
//                if (isEditable) {
//                    startActivity(WriteOrderActivity.class);
//                } else {
//                    Iterator iter = adapter.map.keySet().iterator();
//                    while (iter.hasNext()) {
//                        Object key = iter.next();
//                        Cart cart = adapter.map.get(key);
//                        cartDelete(cart.getPid());
////                        tv_money.setText(caculateSumMoney());
//                    }
//                    setRightTitle("编辑");
//                    isEditable = true;
//                }
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
        if (!saveable) {//这里编辑操作的入口
            setButtonStyle(SAVE, DELETE, true);
            adapter.startEdit();
        }else {//这里做保存操作
            setButtonStyle(EDIT, PAY, false);
            saveGoodsInfo();
            adapter.finishEdit();
        }
//        if (adapter.getItemState()) {
//            if (isEditable && adapter.map.size() != 0) {//编辑
//                ToastUtils.showToastShort(this, "点击编辑");
//                adapter.updateItem();//变成可编辑
//                setRightTitle("完成");
//                isEditable = false;
//                ll_money.setVisibility(View.INVISIBLE);
//                tv_closing.setText("删除");
//            } else {//完成
//                adapter.selectedmap = adapter.map;
//                Iterator iter = adapter.map.keySet().iterator();
//                List<EditShopCart> products = new ArrayList<>();
//                EditShopCart editShopCart;
//                while (iter.hasNext()) {
//                    Object key = iter.next();
//                    editShopCart = new EditShopCart();
//                    //Cart cart = adapter.map.get(key);
//                    editShopCart.setPid((adapter.ShoppingCarts.get((int) key).getPid()));
//                    editShopCart.setQt((adapter.ShoppingCarts.get((int) key).getQt()));
//                    products.add(editShopCart);
//                }
//                multiManage(products);
////                tv_money.setText(caculateSumMoney());
//                //getCart();
//                isEditable = true;
//                setRightTitle("编辑");
//                adapter.recoverItem();//变成不可编辑
//
//                ll_money.setVisibility(View.VISIBLE);
//                tv_closing.setText("结算");
//            }
//        }
    }

    /**
     *设置不同状态时的Button显示
     */
    private void setButtonStyle(String topText, String bottomText, boolean canSave) {
        setRightTitle(topText);
        tv_closing.setText(bottomText);
        saveable = canSave;
        ll_money.setVisibility(canSave == true ? View.GONE : View.VISIBLE);
    }

    /**
     * 保存商品编辑信息
     */
    private void saveGoodsInfo() {
        List<CartEdit> cartEdits = new ArrayList<>();
        CartEdit cartEdit = new CartEdit();
        cartEdit.setPid(mCart.getPid());
        cartEdit.setQt(mCart.getQt());
        cartEdits.add(cartEdit);
        networkRequest(StoreApi.multiManage(cartEdits), new SimpleFastJsonCallback<ShopCarItem>(ShopCarItem.class, loading) {
            @Override
            public void onSuccess(String url, ShopCarItem result) {
                ToastUtils.showToastShort(mContext, "编辑商品保存成功");
                Logger.w("保存成功 = " + result.toString());
                mCart.setQt(mCart.getGoodsCount());
                mCart.setEditable(false);
                setGoodsPrice(mCart);
                loading.dismiss();
            }
        });
    }

    /**
     * 设置商品价格
     */
    private void setGoodsPrice(Cart cart) {
        String goodsCount = cart.isEditable()? cart.getQt() : cart.getGoodsCount();
        float price = Float.parseFloat(cart.getPrice());
        float qt = Float.parseFloat(goodsCount);
        tv_money.setText(price * qt + "");
    }

    @Subcriber(tag = EditShoppingCarPopupWindow.EVENT_UPDATE_NORMS)
    public void eventUpdateNorms(Cart cart) {
        adapter.editNorms(currentPosition, cart);
    }

    @Subcriber(tag = ShoppingCarAdapter.EVENT_EDITABLE)
    public void eventEditable(Cart cart) {
        navigation_bar.setRightAction(true);
        setRightTitleColor(R.color.text_main_color_nor);
        mCart = cart;
        setGoodsPrice(cart);
    }

    @Subcriber(tag = ShoppingCarAdapter.EVENT_UNEDITABLE)
    public void eventUneditable(Cart cart) {
        navigation_bar.setRightAction(false);
        setRightTitleColor(R.color.text_color_gray);
        setButtonStyle(EDIT, PAY, false);
        cart.setEditable(false);
    }

    @Subcriber(tag = ShoppingCarAdapter.EVENT_EDIT_NORMS)
    public void eventEditNorms(Bundle bundle) {
        currentPosition = bundle.getInt(ShoppingCarAdapter.BUNDLE_POSITION);
        Cart cart = (Cart) bundle.getSerializable(ShoppingCarAdapter.BUNDLE_CART);
        mEditShoppingCarPopupWindow = new EditShoppingCarPopupWindow(mContext, cart.getPid(), cart);
        mEditShoppingCarPopupWindow.show(rl_shopping_car);
    }
}
