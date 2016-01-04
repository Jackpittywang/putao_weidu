package com.putao.wd.me.address;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.db.CityDBManager;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.db.ProvinceDBManager;
import com.putao.wd.me.address.adapter.AddressAdapter;
import com.putao.wd.model.Address;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;
import com.sunnybear.library.view.recycler.OnItemLongClickListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 收货地址列表
 * Created by guchenkai on 2015/11/26.
 */
public class AddressListActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    public static final String EVENT_SELECT_ADDRESS = "select_address";
    public static final String BUNDLE_IS_CLOSE = "is_close";

    @Bind(R.id.rv_addresses)
    BasicRecyclerView rv_addresses;
    @Bind(R.id.rl_no_address)
    RelativeLayout rl_no_address;//没有收货地址时的布局

    private AddressAdapter adapter;

    private ProvinceDBManager mProvinceDBManager;
    private CityDBManager mCityDBManager;
    private DistrictDBManager mDistrictDBManager;

    private boolean isClose = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        isClose = args.getBoolean(BUNDLE_IS_CLOSE);

        mProvinceDBManager = (ProvinceDBManager) mApp.getDataBaseManager(ProvinceDBManager.class);
        mCityDBManager = (CityDBManager) mApp.getDataBaseManager(CityDBManager.class);
        mDistrictDBManager = (DistrictDBManager) mApp.getDataBaseManager(DistrictDBManager.class);

        adapter = new AddressAdapter(mContext, null);
        rv_addresses.setAdapter(adapter);
        getAddressLists();

        addListener();
    }

    /**
     * 收货地址列表
     */
    private void getAddressLists() {
        networkRequest(OrderApi.getAddressLists(), new SimpleFastJsonCallback<ArrayList<Address>>(Address.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Address> result) {
                if (result != null && result.size() > 0) {
                    adapter.addAll(result);
                    rl_no_address.setVisibility(View.GONE);
                }
                loading.dismiss();
            }
        });
    }

    private void addListener() {
        rv_addresses.setOnItemClickListener(new OnItemClickListener<Address>() {
            @Override
            public void onItemClick(Address address, int position) {
                if (isClose) {
                    EventBusHelper.post(address, EVENT_SELECT_ADDRESS);
                    finish();
                }
            }
        });
        rv_addresses.setOnItemLongClickListener(new OnItemLongClickListener<Address>() {
            @Override
            public void onItemLongClick(final Address address, int position) {
                new AlertDialog.Builder(mContext)
                        .setTitle("删除收货地址")
                        .setMessage("是否删除该条收货地址")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                networkRequest(OrderApi.addressDelete(address.getId()), new SimpleFastJsonCallback<String>(String.class, loading) {
                                    @Override
                                    public void onSuccess(String url, String result) {
                                        Logger.d(result.toString());
                                        loading.dismiss();
                                    }
                                });
                                adapter.delete(address);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.rl_add_address)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_add_address://新增地址
                startActivity(AddressEditActivity.class);
                break;
        }
    }

    @Subcriber(tag = AddressEditActivity.EVENT_ADDRESS_ADD)
    public void eventAddressAdd(Address address) {
        adapter.add(address);
    }

    @Subcriber(tag = AddressEditActivity.EVENT_ADDRESS_UPDATE)
    public void eventAddressUpdate(Address address) {
        adapter.replace(adapter.getEditPosition(), address);
    }

    @Subcriber(tag = AddressEditActivity.EVENT_ADDRESS_DELETE)
    public void eventAddressDelete(Address address) {
        adapter.delete(adapter.getEditPosition());
    }
}
