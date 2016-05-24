package com.putao.wd.pt_store.shopping.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Cart;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.AmountSelectLayout;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * 购物车适配器
 * Created by guchenkai on 2015/12/4.
 */
public class ShoppingCarAdapter extends BasicAdapter<Cart, ShoppingCarAdapter.ShoppingCarViewHolder> {
    public static final String EVENT_EDITABLE = "editable";//可编辑
    public static final String EVENT_UNEDITABLE = "uneditable";//不可编辑
    public static final String EVENT_EDIT_NORMS = "edit_norms";//编辑规格
    public static final String EVENT_EDIT_COUNT = "edit_count";//编辑数量
//    public static final String EVENT_CURR_CLICK = "curr_click";//当前点击位置

    public static final String BUNDLE_POSITION = "position";
    public static final String BUNDLE_CART = "cart";

    public Map<Integer, Cart> selected;//记录进入编辑状态后选中的商品

    public ShoppingCarAdapter(Context context, List<Cart> shoppingCars) {
        super(context, shoppingCars);
        selected = new ConcurrentHashMap<>();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_shopping_car_item;
    }

    @Override
    public ShoppingCarViewHolder getViewHolder(View itemView, int viewType) {
        return new ShoppingCarViewHolder(itemView);
    }

    @Override
    public void onBindItem(final ShoppingCarViewHolder holder, final Cart cart, final int position) {
        holder.tv_big_title.setText(!cart.isNull() ? "商品信息" : "无效商品");
        setTitle(holder, cart, position);//设置标题
//        EventBusHelper.post(position, EVENT_CURR_CLICK);

        holder.iv_car_icon.setImageURL(cart.getIcon());
        holder.tv_title.setText(cart.getTitle());
        holder.tv_sku.setText(cart.getSku());
        holder.tv_money.setText(cart.getPrice());
        if (!StringUtils.isEmpty(cart.getQt())) {
            holder.asl_num_sel.setCount(Integer.parseInt(cart.getQt()));
            cart.setGoodsCount(cart.getQt());
        }

        holder.tv_count.setText(cart.getQt());
        holder.btn_sel.setState(cart.isSelect());
        showEdit(holder, cart);
//        if (!cart.isSelect()) {
//            holder.btn_sel.setState(false);
//        }
        //修改开关状态
        if (cart.isSelect()) {
            if (!cart.isNull()) {
                selected.put(position, cart);
            }
            EventBusHelper.post(selected, EVENT_EDITABLE);
        } else {
            if (!cart.isNull()) {
                selected.remove(position);
                if (selected.size() == 0)
                    cart.setEditable(false);
            }
            EventBusHelper.post(selected, EVENT_UNEDITABLE);
        }
        holder.btn_sel.setClickable(false);
//        holder.btn_sel.setOnSwitchClickListener(new SwitchButton.OnSwitchClickListener() {
//            @Override
//            public void onSwitchClick(View v, boolean isSelect) {
//                cart.setIsSelect(isSelect);
//                if (isSelect) {
//                    selected.put(position, cart);
//                    EventBusHelper.post(selected, EVENT_EDITABLE);
//                } else {
//                    selected.remove(position);
//                    if (selected.size() == 0)
//                        cart.setEditable(false);
//                    EventBusHelper.post(selected, EVENT_UNEDITABLE);
//                }
//                replace(position, cart);
//            }
//        });
        //修改购买数量
        holder.asl_num_sel.setOnAmountSelectedListener(new AmountSelectLayout.OnAmountSelectedListener() {
            @Override
            public void onAmountSelected(int count, boolean isPlus,boolean isLast) {
                Cart curCart = selected.get(position);
                if (null == curCart) return;
                curCart.setGoodsCount(count + "");
                selected.put(position, curCart);
                EventBusHelper.post(selected, EVENT_EDIT_COUNT);
            }
        });
        //修改规格参数
        holder.iv_update_norms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onUpdateNorms.updateNorms(shoppingCar.getPid(),position);
                Bundle bundle = new Bundle();
                bundle.putInt(BUNDLE_POSITION, position);
                bundle.putSerializable(BUNDLE_CART, selected.get(position));
                EventBusHelper.post(bundle, EVENT_EDIT_NORMS);
            }
        });
    }

    /**
     * 设置标题
     *
     * @param curCart
     */
    private void setTitle(ShoppingCarViewHolder holder, Cart curCart, int position) {
        if (position != 0) {//和上一条信息比较
            Cart previCar = getItem(position - 1);
            if (previCar.isNull() == curCart.isNull()) {
                holder.tv_big_title.setVisibility(View.GONE);
                holder.divider.setVisibility(View.GONE);
            } else {
                holder.tv_big_title.setVisibility(View.VISIBLE);
                holder.divider.setVisibility(View.GONE);
            }
        } else {
            holder.tv_big_title.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.GONE);
        }
    }

    /**
     * 编辑规格信息
     *
     * @param positon
     * @param cart
     */
    public void editNorms(int positon, Cart cart) {
        selected.put(positon, cart);
        replace(positon, cart);
    }

    /**
     * 全部选中
     */
    public void selAll(boolean isSelect) {
        List<Cart> cars = getItems();
        for (Cart cart : cars) {
            if (!cart.isNull()) {
                int index = cars.indexOf(cart);
                cart.setIsSelect(isSelect);
                if (isSelect) {
                    selected.put(index, cart);
                } else {
                    selected.remove(index);
                    cart.setEditable(false);
                    cart.setIsSelect(false);
                }
                EventBusHelper.post(selected, EVENT_EDITABLE);
                replace(index, cart);
            }
        }
    }

    /**
     * 开始编辑
     */
    public void startEdit() {
        Set<Integer> keys = selected.keySet();
        for (Integer key : keys) {
            Cart cart = selected.get(key);
            cart.setEditable(true);
            replace(key, cart);
        }
    }

    /**
     * 完成编辑
     */
    public void finishEdit() {
        Set<Integer> keys = selected.keySet();
        for (Integer key : keys) {
            Cart cart = selected.get(key);
            cart.setEditable(false);
            cart.setIsSelect(false);
            replace(key, cart);
        }
    }

    /**
     * 无效商品置为原始状态
     */
    public void initUselessState(int useCount) {
        List<Cart> carts = getItems();
        for (int i = useCount; i < carts.size(); i++) {
            Cart cart = carts.get(i);
            cart.setIsSelect(false);
            notifyItemChanged(i);
        }
    }

    /**
     * 显示编辑状态Item
     */
    private void showEdit(ShoppingCarViewHolder holder, Cart cart) {
        if (cart.isEditable()) {
            if (cart.isSelect()) {
                holder.asl_num_sel.setVisibility(View.VISIBLE);
                holder.ll_info.setVisibility(View.GONE);
                holder.iv_update_norms.setVisibility(View.VISIBLE);
            }
        } else {
            holder.asl_num_sel.setVisibility(View.GONE);
            holder.ll_info.setVisibility(View.VISIBLE);
            holder.iv_update_norms.setVisibility(View.GONE);
        }
    }

    /**
     * 购物车视图
     */
    static class ShoppingCarViewHolder extends BasicViewHolder {
        @Bind(R.id.divider)
        View divider;
        @Bind(R.id.tv_big_title)
        TextView tv_big_title;
        @Bind(R.id.iv_car_icon)
        ImageDraweeView iv_car_icon;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_sku)
        TextView tv_sku;
        @Bind(R.id.tv_money)
        TextView tv_money;
        @Bind(R.id.tv_count)
        TextView tv_count;
        @Bind(R.id.btn_sel)
        SwitchButton btn_sel;
        @Bind(R.id.ll_info)
        LinearLayout ll_info;
        @Bind(R.id.asl_num_sel)
        AmountSelectLayout asl_num_sel;
        @Bind(R.id.iv_update_norms)
        ImageView iv_update_norms;

        public ShoppingCarViewHolder(View itemView) {
            super(itemView);
        }
    }
}
