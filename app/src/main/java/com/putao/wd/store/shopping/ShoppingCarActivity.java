package com.putao.wd.store.shopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.ColorConstant;
import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Cart;
import com.putao.wd.model.CartEdit;
import com.putao.wd.model.ShopCarItem;
import com.putao.wd.store.order.WriteOrderActivity;
import com.putao.wd.store.shopping.adapter.ShoppingCarAdapter;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.MathUtils;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;
import com.sunnybear.library.view.recycler.listener.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 编辑购物车规格
 * Created by wangou on 2015/12/4.
 */
public class ShoppingCarActivity extends PTWDActivity implements View.OnClickListener {
    public static final String PRODUCT_ID = "productId";
    public static final String SHOPPING_CAR = "购物车";
    private static final String PAY = "去结算";
    private static final String DELETE = "删除";
    private static final String SAVE = "保存";
    private static final String EDIT = "编辑";

    public static final String EVENT_DELETE_CART = "delete_cart";

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
    private EditShoppingCarPopupWindow mEditShoppingCarPopupWindow;//购物车弹窗
    private Map<Integer, Cart> mSelected;//记录进入编辑状态后选中的商品
    private int currentPosition;//当前修改的位置
    //    private int currClickPosition;//当前点击的位置
    private boolean isSelectAll;//全选
    private boolean saveable;//保存按钮标志
    private int useCount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_car;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        setBottomButtonStyle(false);
        btn_sel_all.setClickable(false);

        adapter = new ShoppingCarAdapter(mContext, null);
        rv_cars.setAdapter(adapter);
        networkRequest(StoreApi.getCart(), new SimpleFastJsonCallback<ShopCarItem>(ShopCarItem.class, loading) {
            @Override
            public void onSuccess(String url, ShopCarItem result) {
                List<Cart> carts = result.getUse();
                List<Cart> cartsUseless = result.getUseless();
                addUselessProducts(carts, cartsUseless);
                if (null != carts && carts.size() > 0) {
                    adapter.addAll(result.getUse());
                    rl_empty.setVisibility(View.GONE);
                    rv_cars.setVisibility(View.VISIBLE);
                }
                getCartCount();
            }
        });
        addListener();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        rv_cars.setOnItemClickListener(new OnItemClickListener<Cart>() {
            @Override
            public void onItemClick(Cart cart, int position) {
                if (cart.isSelect()) {
                    cart.setIsSelect(false);
                    cart.setEditable(false);
                    if(cart.isNull()) {
                        cart.setIsSelectUseless(false);
                    }
                } else {
                    cart.setIsSelect(true);
                    if(cart.isNull()) {
                        cart.setIsSelectUseless(true);
                    }
                }
                adapter.replace(position, cart);
            }
        });
        rv_cars.setOnItemLongClickListener(new OnItemLongClickListener<Cart>() {
            @Override
            public void onItemLongClick(Cart cart, int position) {
                showDeleteDialog(cart.getPid());
            }
        });
    }

    /**
     * 获得购物车数量
     */
    private void getCartCount() {
        networkRequest(StoreApi.getCartCount(), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                JSONObject object = JSON.parseObject(result);
                int count = object.getInteger("qt");
                setTitleCount(count);
                loading.dismiss();
            }
        });
    }

    /**
     * 查看购物车
     */
    private void getCart() {
        networkRequest(StoreApi.getCart(), new SimpleFastJsonCallback<ShopCarItem>(ShopCarItem.class, loading) {
            @Override
            public void onSuccess(String url, ShopCarItem result) {
                List<Cart> carts = result.getUse();
                List<Cart> cartsUseless = result.getUseless();
                addUselessProducts(carts, cartsUseless);
                if (null != carts && carts.size() > 0) {
                    adapter.replaceAll(carts);
                } else {
                    rv_cars.setVisibility(View.GONE);
                    rl_empty.setVisibility(View.VISIBLE);
                }
                getCartCount();
            }
        });
    }

    /**
     * 添加无效商品到适配器数据源
     */
    private void addUselessProducts(List<Cart> carts, List<Cart> cartsUseless) {
        useCount = carts.size();
        if(null != cartsUseless && cartsUseless.size() > 0) {
            for(Cart cart : cartsUseless) {
                cart.setIsNull(true);
                carts.add(cart);
            }
        }
    }

    /**
     * 设置标题购物车数量
     */
    private void setTitleCount(int count) {
        if(count != 0) {
            navigation_bar.setMainTitle(SHOPPING_CAR + "(" + count + ")");
        }else {
            navigation_bar.setMainTitle(SHOPPING_CAR);
        }
    }

    /**
     * 商品编辑保存
     */
    private void saveEdit() {
        networkRequest(StoreApi.multiManage(getReqParam(adapter.getItems())), new SimpleFastJsonCallback<ShopCarItem>(ShopCarItem.class, loading) {
            @Override
            public void onSuccess(String url, ShopCarItem result) {
//                ToastUtils.showToastShort(mContext, "编辑商品保存成功");
//                Logger.w("保存成功 = " + result.toString());
//                String sum = "0.00";
                Set<Integer> keys = mSelected.keySet();
                for (Integer key : keys) {
                    Cart cart = mSelected.get(key);
                    cart.setQt(cart.getGoodsCount());
//                    sum = MathUtils.add(sum, MathUtils.multiplication(cart.getPrice(), cart.getQt()));
                }
                if (useCount < adapter.getItems().size()) {
                    adapter.initUselessState(useCount);
                }
                initData();
                tv_money.setText("0.00");
                adapter.finishEdit();
                getCartCount();
            }
        });
    }

    /**
     * 删除购物车--单条长按删除
     */
    private void cartDelete(String pid) {
        networkRequest(StoreApi.cartDelete(pid), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
//                ToastUtils.showToastShort(mContext, "购物车删除成功");
//                Logger.w("购物车删除成功 = " + result.toString());
                EventBusHelper.post(EVENT_DELETE_CART, EVENT_DELETE_CART);
                getCart();
                initData();
                adapter.finishEdit();
            }
        });
    }

    /**
     * 获取请求List
     */
    private List<CartEdit> getReqParam(List<Cart> carts) {
        List<CartEdit> cartEdits = new ArrayList<>();
        if(carts.size() > 0) {
            for (Cart cart : carts) {
                CartEdit cartEdit = new CartEdit();
                cartEdit.setPid(cart.getPid());
                cartEdit.setQt(cart.getGoodsCount());
                cart.setEditable(false);
                cartEdits.add(cartEdit);
            }
        }
        return cartEdits;
    }

    /**
     * 获取删除的请求List
     */
    private List<CartEdit> getDeleteReq() {
        List<CartEdit> reqList;
        if(btn_sel_all.getState()){
            reqList = new ArrayList<>();
        }else {
            List<Cart> noSelect = new ArrayList<>();
            for(int i = 0; i < adapter.getItems().size(); i++) {
                Cart cart = adapter.getItem(i);
                if(!mSelected.containsKey(i) && !cart.isSelectUseless()){
                    noSelect.add(cart);
                }
            }
            reqList = getReqParam(noSelect);
        }
        return reqList;
    }

    /**
     * 产生“去结算”页的跳转Bundle，装载pid
     */
    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        StringBuilder pid = new StringBuilder();
        Set<Integer> keys = mSelected.keySet();
        for (Integer key : keys) {
            pid.append(mSelected.get(key).getPid() + "|");
        }
        bundle.putString(PRODUCT_ID, pid.substring(0, pid.length() - 1));
        return bundle;
    }

    /**
     * 初始化相关控制值
     */
    private void initData() {
        btn_sel_all.setState(false);
        setBottomButtonStyle(false);
        isSelectAll = false;
        saveable = false;
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

    /**
     * 设置商品价格
     */
    private void setGoodsPrice() {
        Set<Integer> keys = mSelected.keySet();
        String sum = "0.00";
        for (Integer key : keys) {
            Cart cart = mSelected.get(key);
//            String goodsCount = cart.isEditable() ? cart.getQt() : cart.getGoodsCount();
            String goodsCount = cart.isEditable() ? cart.getGoodsCount() : cart.getQt();
            sum = MathUtils.add(sum, MathUtils.multiplication(cart.getPrice(), goodsCount));
        }
        tv_money.setText(sum);
    }

    /**
     * 设置“编辑”按钮显示状态
     */
    private void setTopButtonStyle(String topText, String bottomText, boolean canSave) {
        setRightTitle(topText);
        tv_closing.setText(bottomText);
        saveable = canSave;
//        ll_money.setVisibility(canSave ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置“结算”按钮显示状态
     */
    private void setBottomButtonStyle(boolean canNext) {
        navigation_bar.setRightAction(canNext);
        ll_closing.setClickable(canNext);
        ll_closing.setBackgroundResource(canNext ? R.color.text_main_color_nor : R.color.color_C2C2C2);
    }

    @OnClick({R.id.ll_closing, R.id.ll_all})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_all://全选
                if(useCount != 0){
                    isSelectAll = isSelectAll ? false : true;
                    btn_sel_all.setState(isSelectAll);
                    adapter.selAll(isSelectAll);
                    if (isSelectAll) {
                        setBottomButtonStyle(true);
                    } else {
                        setTopButtonStyle(EDIT, PAY, false);
                        setBottomButtonStyle(false);
                    }
                }
                break;
            case R.id.ll_closing://去结算/删除
                switch (tv_closing.getText().toString()) {
                    case PAY:
                        startActivity(WriteOrderActivity.class, getBundle());
                        break;
                    case DELETE:
                        networkRequest(StoreApi.multiManage(getDeleteReq()), new SimpleFastJsonCallback<ShopCarItem>(ShopCarItem.class, loading) {
                            @Override
                            public void onSuccess(String url, ShopCarItem result) {
                                initData();
                                adapter.finishEdit();
                                getCart();
                            }
                        });
                        break;
                }
                break;
        }
    }

    @Override
    public void onRightAction() {
        Logger.d("点击右上角");
        if (!saveable) {//这里编辑操作的入口
            setTopButtonStyle(SAVE, DELETE, true);
            if (mSelected.size() == 0) {
                setBottomButtonStyle(true);
            }
            /*isSelectAll=true;
            btn_sel_all.setState(isSelectAll);
            adapter.selAll(isSelectAll);*/
            adapter.startEdit();
        } else {//这里做保存操作
            setTopButtonStyle(EDIT, PAY, false);
            saveEdit();
        }
    }

    /**
     * 删除dialog
     */
    private void showDeleteDialog(final String pid) {
        new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("确定删除")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartDelete(pid);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @Subcriber(tag = EditShoppingCarPopupWindow.EVENT_UPDATE_NORMS)
    public void eventUpdateNorms(Cart cart) {
        adapter.editNorms(currentPosition, cart);
    }

//    @Subcriber(tag = ShoppingCarAdapter.EVENT_CURR_CLICK)
//    public void eventcurrClick(int position) {
//        currClickPosition = position;
//    }

    @Subcriber(tag = ShoppingCarAdapter.EVENT_EDITABLE)
    public void eventEditable(Map<Integer, Cart> selected) {
        mSelected = selected;
        navigation_bar.setRightAction(true);
        if(selected.size() != 0) {
            setBottomButtonStyle(true);
            setGoodsPrice();
        }
        if (useCount != 0 && selected.size() == useCount)
            btn_sel_all.setState(true);
        else
            btn_sel_all.setState(false);
    }

    @Subcriber(tag = ShoppingCarAdapter.EVENT_UNEDITABLE)
    public void eventUneditable(Map<Integer, Cart> selected) {
        mSelected = selected;
        setGoodsPrice();
        if (selected.size() == 0) {
            setBottomButtonStyle(false);
            setRightTitleColor(ColorConstant.MAIN_COLOR_DIS);
            setTopButtonStyle(EDIT, PAY, false);
            initData();
        }
        if(selected.size() != useCount) {
            btn_sel_all.setState(false);
        }
    }

    @Subcriber(tag = ShoppingCarAdapter.EVENT_EDIT_COUNT)
    public void eventEditCount(Map<Integer, Cart> selected) {
        mSelected = selected;
        setGoodsPrice();
    }

    @Subcriber(tag = ShoppingCarAdapter.EVENT_EDIT_NORMS)
    public void eventEditNorms(Bundle bundle) {
        currentPosition = bundle.getInt(ShoppingCarAdapter.BUNDLE_POSITION);
        Cart cart = (Cart) bundle.getSerializable(ShoppingCarAdapter.BUNDLE_CART);
        mEditShoppingCarPopupWindow = new EditShoppingCarPopupWindow(mContext, cart.getPid(), cart);
        mEditShoppingCarPopupWindow.show(rl_shopping_car);
    }
}
