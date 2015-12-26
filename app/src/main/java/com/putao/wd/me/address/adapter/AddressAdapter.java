package com.putao.wd.me.address.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.address.AddressEditActivity;
import com.putao.wd.model.Address;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 收货人地址适配器
 * Created by guchenkai on 2015/11/26.
 */
public class AddressAdapter extends BasicAdapter<Address, AddressAdapter.AddressViewHolder> {
    private int index;

    public AddressAdapter(Context context, List<Address> shippingAddressDBs) {
        super(context, shippingAddressDBs);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_address_list_item;
    }

    @Override
    public AddressViewHolder getViewHolder(View itemView, int viewType) {
        return new AddressViewHolder(itemView);
    }

    @Override
    public void onBindItem(AddressViewHolder holder, final Address address, final int position) {
        holder.tv_name.setText(address.getRealname());
        holder.tv_default.setVisibility(StringUtils.equals(address.getStatus(), "1") ? View.VISIBLE : View.GONE);
        setAddressName(holder, address);
        holder.tv_mobile.setText(address.getMobile());
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                Bundle bundle = new Bundle();
                bundle.putSerializable(AddressEditActivity.BUNDLE_KEY_ADDRESS, address);
                ((PTWDActivity) context).startActivity(AddressEditActivity.class, bundle);
            }
        });
    }

    /**
     * 设置地址
     *
     * @param holder
     * @param address
     */
    private void setAddressName(AddressViewHolder holder, Address address) {
        JSONObject object = JSON.parseObject(address.getAddressName());
        String addr = object.getString(address.getProvince_id()) +
                object.getString(address.getCity_id()) +
                object.getString(address.getArea_id()) +
                address.getAddress();
        holder.tv_address.setText(addr);
    }

    /**
     * 获得编辑条目标号
     *
     * @return 编辑条目标号
     */
    public int getEditPosition() {
        return index;
    }

    /**
     * 收货人地址布局
     */
    static class AddressViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.tv_default)
        TextView tv_default;
        @Bind(R.id.tv_customer_address)
        TextView tv_address;
        @Bind(R.id.tv_mobile)
        TextView tv_mobile;
        @Bind(R.id.tv_edit)
        TextView tv_edit;

        public AddressViewHolder(View itemView) {
            super(itemView);
        }
    }
}
