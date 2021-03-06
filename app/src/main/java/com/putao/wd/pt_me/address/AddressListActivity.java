package com.putao.wd.pt_me.address;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.db.CityDBManager;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.db.ProvinceDBManager;
import com.putao.wd.pt_me.address.adapter.AddressAdapter;
import com.putao.wd.model.Address;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;
import com.sunnybear.library.view.recycler.listener.OnItemLongClickListener;


import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 收货地址列表
 * Created by guchenkai on 2015/11/26.
 */
public class AddressListActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    public static final String EVENT_SELECT_ADDRESS = "select_address";
    public static final String EVENT_SELECT_ADDRESS_EMPTY = "event_select_address_empty";
    public static final String BUNDLE_IS_CLOSE = "is_close";
    public static final String IS_ADDRESS_EMPTY = "is_address_empty";
    public static final String BUNDLE_ADDRESS_ID = "bundle_address_id";

    @Bind(R.id.rv_addresses)
    BasicRecyclerView rv_addresses;
    @Bind(R.id.rl_no_address)
    RelativeLayout rl_no_address;//没有收货地址时的布局

    private AddressAdapter adapter;

    private ProvinceDBManager mProvinceDBManager;
    private CityDBManager mCityDBManager;
    private DistrictDBManager mDistrictDBManager;
    private boolean isAddressEmpty = true;

    private boolean isClose = false;
    private String mId = "";
    private ArrayList<Address> mAddress;

    private AlertDialog mDeleteDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        isClose = args.getBoolean(BUNDLE_IS_CLOSE);
        mId = args.getString(BUNDLE_ADDRESS_ID);

        mProvinceDBManager = (ProvinceDBManager) mApp.getDataBaseManager(ProvinceDBManager.class);
        mCityDBManager = (CityDBManager) mApp.getDataBaseManager(CityDBManager.class);
        mDistrictDBManager = (DistrictDBManager) mApp.getDataBaseManager(DistrictDBManager.class);

        adapter = new AddressAdapter(mContext, null);
        rv_addresses.setAdapter(adapter);
//        getAddressLists();

        addListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAddressLists();
    }

    /**
     * 收货地址列表
     */
    private void getAddressLists() {
        networkRequest(OrderApi.getAddressLists(), new SimpleFastJsonCallback<ArrayList<Address>>(Address.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Address> result) {
                if (result != null && result.size() > 0) {
                    isAddressEmpty = false;
                    adapter.replaceAll(result);
                    rl_no_address.setVisibility(View.GONE);
                    mAddress = result;
                    if (result.size() == 1)
                        EventBusHelper.post(mAddress.get(0), EVENT_SELECT_ADDRESS);
                } else rl_no_address.setVisibility(View.VISIBLE);
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
                mDeleteDialog = new AlertDialog.Builder(mContext)
                        .setTitle("删除收货地址")
                        .setMessage("是否删除该条收货地址")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                networkRequest(OrderApi.addressDelete(address.getId()), new SimpleFastJsonCallback<String>(String.class, loading) {
                                    @Override
                                    public void onSuccess(String url, String result) {
                                        Logger.d(result.toString());
                                        adapter.delete(address);
                                        if (adapter.getItemCount() == 0) {
                                            isAddressEmpty = true;
                                            rl_no_address.setVisibility(View.VISIBLE);
                                        }
                                        eventAddressDelete(address.getId());
                                        getAddressLists();
//                                        EventBusHelper.post(address, AddressEditActivity.EVENT_ADDRESS_DELETE);
//                                        loading.dismiss();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDeleteDialog.dismiss();
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
                Bundle bundle = new Bundle();
                bundle.putBoolean(IS_ADDRESS_EMPTY, isAddressEmpty);
                startActivity(AddressEditActivity.class, bundle);
                YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_address_add);
                break;
        }
    }

    //    @Subcriber(tag = AddressEditActivity.EVENT_ADDRESS_ADD)
//    public void eventAddressAdd(String tag) {
////        adapter.add(address);
//        rl_no_address.setVisibility(View.GONE);
//        networkRequest(OrderApi.getAddressLists(), new SimpleFastJsonCallback<ArrayList<Address>>(Address.class, loading) {
//            @Override
//            public void onSuccess(String url, ArrayList<Address> result) {
//                if (result != null && result.size() > 0)
//                    adapter.replaceAll(result);
//                loading.dismiss();
//            }
//        });
//    }
//
    @Subcriber(tag = AddressEditActivity.EVENT_ADDRESS_UPDATE)
    public void eventAddressUpdate(String tag) {
        getAddressLists();
    }

    @Subcriber(tag = AddressEditActivity.EVENT_ADDRESS_DELETE)
    public void eventAddressDelete(String id) {
//        adapter.delete(adapter.getEditPosition());
        if (mId.equals(id)) {
            if (1 == mAddress.size()) {
                EventBusHelper.post(EVENT_SELECT_ADDRESS_EMPTY, EVENT_SELECT_ADDRESS_EMPTY);
                rl_no_address.setVisibility(View.VISIBLE);
                return;
            }
            int i = 0;
            for (Address address : mAddress) {
                if (mId.equals(address.getId()) && "1".equals(address.getStatus())) {
                    EventBusHelper.post(i == 0 ? mAddress.get(1) : mAddress.get(0), EVENT_SELECT_ADDRESS);
                    getAddressLists();
                    return;
                }
                i++;
            }
            i = 0;
            boolean b = false;
            for (Address address : mAddress) {
                if (mId.equals(address.getId()) && i == 0) b = true;
                if ("1".equals(address.getStatus())) {
                    EventBusHelper.post(address, EVENT_SELECT_ADDRESS);
                    getAddressLists();
                    return;
                }
                i++;
            }
            EventBusHelper.post(b ? mAddress.get(1) : mAddress.get(0), EVENT_SELECT_ADDRESS);
            getAddressLists();
        }
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_address_back);
    }
}
