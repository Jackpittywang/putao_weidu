package com.putao.wd.store.shopping.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ShoppingCar;
import com.putao.wd.model.Cart;
import com.putao.wd.model.ProductNormsSku;
import com.sunnybear.library.util.MathUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.AmountSelectLayout;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;

/**
 * 购物车适配器
 * Created by guchenkai on 2015/12/4.
 */
public class ShoppingCarAdapter extends BasicAdapter<Cart, ShoppingCarAdapter.ShoppingCarViewHolder> {

    private boolean itemState;//记录当前状态值
    public HashMap<Integer, Cart> map;//记录进入编辑状态前选中的商品
    public HashMap<Integer, Cart> selectedmap;//记录进入编辑状态后选中的商品
    public List<Cart> ShoppingCarts;//商品

    public ShoppingCarAdapter(Context context, List<Cart> shoppingCars) {
        super(context, shoppingCars);
        map = new HashMap<>();
        selectedmap = new HashMap<>();
        this.ShoppingCarts = shoppingCars;
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
    public void onBindItem(final ShoppingCarViewHolder holder, final Cart shoppingCar, final int position) {
        holder.tv_big_title.setText(!shoppingCar.isNull() ? "商品信息" : "无效商品");
        if (position != 0) {//和上一条信息比较
            Cart previCar = getItem(position - 1);
            if (previCar.isNull() == shoppingCar.isNull()) {
                holder.tv_big_title.setVisibility(View.GONE);
                holder.divider.setVisibility(View.GONE);
            }
        } else {
            holder.tv_big_title.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.GONE);
        }
        holder.iv_car_icon.setImageURL(shoppingCar.getIcon());
        holder.tv_title.setText(shoppingCar.getTitle());
        holder.tv_sku.setText(shoppingCar.getSku());
//        holder.tv_color.setText(shoppingCar.getColor());
//        holder.tv_size.setText(shoppingCar.getSku());
        holder.tv_money.setText(shoppingCar.getPrice());
        if (!StringUtils.isEmpty(shoppingCar.getQt())) {
            holder.asl_num_sel.setCount(Integer.parseInt(shoppingCar.getQt()));
        }

        holder.tv_count.setText(shoppingCar.getQt());
        holder.btn_sel.setState(shoppingCar.isSelect());
        showEdit(holder, shoppingCar);
        holder.btn_sel.setOnSwitchClickListener(new SwitchButton.OnSwitchClickListener() {
            @Override
            public void onSwitchClick(View v, boolean isSelect) {
                shoppingCar.setIsSelect(isSelect);
                itemState = isSelect;
                shoppingCar.setEditable(false);
                map.put(position, shoppingCar);
                replace(position, shoppingCar);
            }
        });

        //修改购买数量
        holder.asl_num_sel.setOnAmountSelectedListener(new AmountSelectLayout.OnAmountSelectedListener() {
            @Override
            public void onAmountSelected(int count, boolean isPlus) {
                shoppingCar.setQt(holder.asl_num_sel.getCurrentCount() + "");
                ShoppingCarts.set(position, shoppingCar);
            }
        });

        //修改规格参数
        holder.iv_update_norms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateNorms.updateNorms(shoppingCar.getPid(),position);
            }
        });

    }

    //定义修改购物车商品规格参数回调函数
    public interface OnUpdateNorms{
        void updateNorms(String pid,int position);
    }
    private OnUpdateNorms onUpdateNorms;
    public void setOnUpdateNorms(OnUpdateNorms onUpdateNorms){
        this.onUpdateNorms=onUpdateNorms;
    }


    /**
     * 全部选中
     */
    public void selAll(boolean isSelect) {
        List<Cart> cars = getItems();
        for (Cart car : cars) {
            int index = cars.indexOf(car);
            car.setIsSelect(isSelect);
            replace(index, car);
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
     * 获取当前item状态,判断是否是可编辑
     */
    public boolean getItemState() {
        return itemState;
    }

    /**
     * 更新Item显示编辑
     */
    public void updateItem() {
//        ToastUtils.showToastShort(context, "updateItem");
        Set<Integer> keys = map.keySet();
        for (Integer key : keys) {
            Cart shoppingCar = map.get(key);
            shoppingCar.setEditable(true);
            replace(key, shoppingCar);
        }
    }

    /**
     * 更新Item显示恢复不可编辑状态
     */
    public void recoverItem() {
//        ToastUtils.showToastShort(context, "recoverItem");
        Set<Integer> keys=selectedmap.keySet();
        for (Integer key :keys) {
            Cart shoppingCar = selectedmap.get(key);
            shoppingCar.setEditable(false);
            replace(key, shoppingCar);
        }
    }

    /**
     * 根据修改操作实时更新UI的规格参数
     */
    public void updateUINorm(int position,Cart updateCart){
        Cart cart=ShoppingCarts.get(position);
        cart.setPid(updateCart.getPid());
        cart.setPrice(updateCart.getPrice());
        cart.setSku(updateCart.getSku());
        replace(position, cart);
        ShoppingCarts.set(position, cart);
        map.put(position, cart);
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
//        @Bind(R.id.tv_color)
//        TextView tv_color;
//        @Bind(R.id.tv_size)
//        TextView tv_size;
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
