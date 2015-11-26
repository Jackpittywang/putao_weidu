package com.putao.wd.me.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.db.entity.ShippingAddressDB;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 收货人地址适配器
 * Created by guchenkai on 2015/11/26.
 */
public class AddressAdapter extends BasicAdapter<ShippingAddressDB, AddressAdapter.AddressViewHolder> {

    public AddressAdapter(Context context, List<ShippingAddressDB> shippingAddressDBs) {
        super(context, shippingAddressDBs);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_address_list_item;
    }

    @Override
    public AddressViewHolder getViewHolder(View itemView, int viewType) {
        return new AddressViewHolder(itemView);
    }

    @Override
    public void onBindItem(AddressViewHolder holder, ShippingAddressDB shippingAddressDB, int position) {
        holder.tv_name.setText(shippingAddressDB.getName());
        boolean isDefault = shippingAddressDB.getIsDefault();
        if (isDefault)
            holder.iv_default.setVisibility(View.VISIBLE);
        else
            holder.iv_default.setVisibility(View.GONE);
        holder.tv_address.setText(getAddress(shippingAddressDB));
        holder.tv_mobile.setText(shippingAddressDB.getMobile());
    }

    /**
     * 获得收货地址
     *
     * @param shippingAddressDB shippingAddressDB
     * @return 收货地址
     */
    private String getAddress(ShippingAddressDB shippingAddressDB) {
        String province = shippingAddressDB.getProvince();//省
        String city = shippingAddressDB.getCity();//市
        String district = shippingAddressDB.getDistrict();//区;
        String street = shippingAddressDB.getStreet();//街道
        return province + province + city + district + street;
    }

    /**
     * 收货人地址布局
     */
    static class AddressViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.iv_default)
        ImageView iv_default;
        @Bind(R.id.tv_address)
        TextView tv_address;
        @Bind(R.id.tv_mobile)
        TextView tv_mobile;

        public AddressViewHolder(View itemView) {
            super(itemView);
        }
    }
}
