package com.putao.wd.me.address;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.db.AddressDBManager;
import com.putao.wd.db.CityDBManager;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.db.ProvinceDBManager;
import com.putao.wd.db.entity.AddressDB;
import com.putao.wd.me.address.adapter.AddressAdapter;
import com.putao.wd.model.Address;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 收货地址列表
 * Created by guchenkai on 2015/11/26.
 */
public class AddressListActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.rv_addresses)
    BasicRecyclerView rv_addresses;
    @Bind(R.id.rl_no_address)
    RelativeLayout rl_no_address;//没有收货地址时的布局

    private AddressAdapter adapter;
    private List<AddressDB> addresses;

    private AddressDBManager mAddressDBManager;
    private ProvinceDBManager mProvinceDBManager;
    private CityDBManager mCityDBManager;
    private DistrictDBManager mDistrictDBManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        mAddressDBManager = (AddressDBManager) mApp.getDataBaseManager(AddressDBManager.class);
        mProvinceDBManager = (ProvinceDBManager) mApp.getDataBaseManager(ProvinceDBManager.class);
        mCityDBManager = (CityDBManager) mApp.getDataBaseManager(CityDBManager.class);
        mDistrictDBManager = (DistrictDBManager) mApp.getDataBaseManager(DistrictDBManager.class);

        addNavigation();
//        addresses = mApp.getDataBaseManager(AddressDBManager.class).loadAll();
//        if (addresses == null || addresses.size() == 0) {
//            rl_no_address.setVisibility(View.VISIBLE);
//            return;
//        }
//        adapter = new AddressAdapter(mContext, addresses);
//        rv_addresses.setAdapter(adapter);
        addresses = mApp.getDataBaseManager(AddressDBManager.class).loadAll();
        if(addresses.size()==0)
            getAddressLists();
        else{
            adapter = new AddressAdapter(mContext, addresses);
            rv_addresses.setAdapter(adapter);
        }
        //网络请求Demo
//        networkRequest("自己组合的request", new SimpleFastJsonCallback<"自己的接收model">() {
//            @Override
//            public void onSuccess("自己的接收model" url, String result) {
//
//            }
//        });
    }

    /**
     * 收货地址列表
     */
    private void getAddressLists(){
        networkRequest(OrderApi.getAddressLists(), new SimpleFastJsonCallback<ArrayList<Address>>(Address.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Address> result) {
                Logger.d(result.toString());

                if (result == null || result.size() == 0) {
                    rl_no_address.setVisibility(View.VISIBLE);
                    return;
                }
                List<AddressDB> addressDBs=new ArrayList<AddressDB>();
                AddressDB addressDB;
                for (int i = 0; i < result.size(); i++) {
                    addressDB=new AddressDB();
                    addressDB.setProvince_id(result.get(0).getProvince_id() + "");
                    addressDB.setProvince(mProvinceDBManager.getProvinceNameByProvinceId(result.get(0).getProvince_id() + ""));
                    addressDB.setCity_id(result.get(0).getCity_id() + "");
                    addressDB.setCity(mCityDBManager.getCityNameByCityId(result.get(0).getCity_id() + ""));
                    addressDB.setDistrict_id(result.get(0).getArea_id() + "");
                    addressDB.setDistrict(result.get(0).getAddress());
                    addressDB.setMobile(result.get(0).getMobile());
                    addressDB.setName(result.get(0).getRealname());
                    addressDB.setIsDefault(result.get(0).getStatus() == 1 ? true : false);
                    addressDBs.add(addressDB);
                    mAddressDBManager.insert(addressDB);
                }

                adapter = new AddressAdapter(mContext, addressDBs);
                rv_addresses.setAdapter(adapter);
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
                bundle.putBoolean(AddressEditActivity.KEY_IS_ADD, true);
                startActivity(AddressEditActivity.class, bundle);
                break;
        }
    }

    @Subcriber(tag = AddressEditActivity.EVENT_ADDRESS_ADD)
    public void eventAddressAdd(AddressDB address) {
        adapter.add(address);
    }

    @Subcriber(tag = AddressEditActivity.EVENT_ADDRESS_UPDATE)
    public void eventAddressUpdate(AddressDB address) {
        adapter.replace(adapter.getEditPosition(), address);
    }

    @Subcriber(tag = AddressEditActivity.EVENT_ADDRESS_DELETE)
    public void eventAddressDelete(AddressDB address) {
        adapter.delete(adapter.getEditPosition());
    }

    @Subcriber(tag = AddressEditActivity.EVENT_ADDRESS_IS_DEFAULT)
    public void eventAddressIsDefault(String tag) {
        adapter.replaceAll(mApp.getDataBaseManager(AddressDBManager.class).loadAll());
    }
}
