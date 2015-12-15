package com.putao.wd.me.address;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.db.AddressDBManager;
import com.putao.wd.db.CityDBManager;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.db.ProvinceDBManager;
import com.putao.wd.db.entity.AddressDB;
import com.putao.wd.me.address.fragment.DistrictFragment;
import com.putao.wd.model.Address;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;
import com.sunnybear.library.view.SwitchButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 收货地址编辑
 * Created by guchenkai on 2015/11/27.
 */
public class AddressEditActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener, TextWatcher, SwitchButton.OnSwitchClickListener {
    public static final String EVENT_ADDRESS_ADD = "address_add";
    public static final String EVENT_ADDRESS_UPDATE = "address_update";
    public static final String EVENT_ADDRESS_DELETE = "address_delete";
    public static final String EVENT_ADDRESS_IS_DEFAULT = "address_is_default";
    public static final String KEY_IS_ADD = "isAdd";
    public static final String KEY_ADDRESS = "address";

    @Bind(R.id.et_name)
    CleanableEditText et_name;//收货人姓名
    @Bind(R.id.tv_province)
    TextView tv_province;//省份
    @Bind(R.id.tv_city)
    TextView tv_city;//城市
    @Bind(R.id.tv_district)
    TextView tv_district;//城区
    @Bind(R.id.et_street)
    CleanableEditText et_street;//街道
    @Bind(R.id.et_phone)
    CleanableEditText et_phone;//电话号码
    @Bind(R.id.btn_default)
    SwitchButton btn_default;//是否设置默认
    @Bind(R.id.ll_delete_address)
    LinearLayout ll_delete_address;//删除

    private boolean isAdd = false;//是否是新增地址

    private AddressDB address;//地址model

    private AddressDBManager mAddressDBManager;
    private ProvinceDBManager mProvinceDBManager;
    private CityDBManager mCityDBManager;
    private DistrictDBManager mDistrictDBManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_edit;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mAddressDBManager = (AddressDBManager) mApp.getDataBaseManager(AddressDBManager.class);
        mProvinceDBManager = (ProvinceDBManager) mApp.getDataBaseManager(ProvinceDBManager.class);
        mCityDBManager = (CityDBManager) mApp.getDataBaseManager(CityDBManager.class);
        mDistrictDBManager = (DistrictDBManager) mApp.getDataBaseManager(DistrictDBManager.class);

        isAdd = args.getBoolean(KEY_IS_ADD);
        if (isAdd) {
            ll_delete_address.setVisibility(View.GONE);
        } else {
            address = (AddressDB) args.getSerializable(KEY_ADDRESS);
            initView();
        }
        addListener();
    }


    /**
     * 添加收货地址
     */
    private void addressAdd(){
//        networkRequest(OrderApi.addressAdd("realname", "city_id", "province_id", "area_id", "address", "mobile", "tel", "postcode", "status"), new SimpleFastJsonCallback<Address>(Address.class, loading) {
        networkRequest(OrderApi.addressAdd("realname", "1", "1", "1", "address", "13711111111", "02525458565", "111111", "1"), new SimpleFastJsonCallback<Address>(Address.class, loading) {
            @Override
            public void onSuccess(String url, Address result) {
                Logger.d(result.toString());
            }

        });
    }

    /**
     * 更新收货地址
     */
    private void addressUpdate(){
        networkRequest(OrderApi.addressUpdate("address_id","realname", "city_id", "province_id", "area_id", "address", "mobile", "tel", "postcode", "status"), new SimpleFastJsonCallback<Address>(Address.class, loading) {
            @Override
            public void onSuccess(String url, Address result) {
                Logger.d(result.toString());
            }

        });
    }
    /**
     * 删除收货地址
     */
    private void addressDelete(){
        networkRequest(OrderApi.addressDelete(""), new SimpleFastJsonCallback<Address>(Address.class, loading) {
            @Override
            public void onSuccess(String url, Address result) {
                Logger.d(result.toString());
            }

        });
    }

    /**
     * 编辑时初始化页面
     */
    private void initView() {
        et_name.setText(address.getName());
        tv_province.setText(address.getProvince());
        tv_city.setText(address.getCity());
        tv_district.setText(address.getDistrict());
        et_street.setText(address.getStreet());
        et_phone.setText(address.getMobile());
        if (address.getIsDefault() != null)
            btn_default.setState(address.getIsDefault());
    }

    private void addListener() {
        et_name.addTextChangedListener(this);
        et_phone.addTextChangedListener(this);
        btn_default.setOnSwitchClickListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.ll_delete_address, R.id.ll_city_sel})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_delete_address://删除本条地址
                mAddressDBManager.delete(address);
                EventBusHelper.post(address, EVENT_ADDRESS_DELETE);
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.ll_city_sel://地区选择
                startActivity(CitySelectActivity.class);
                break;
        }
    }

    @Subcriber(tag = DistrictFragment.EVENT_DISTRICT_SELECT)
    public void eventDistrictSelect(AddressDB addressDB) {
        if (isAdd) address = addressDB;
        String provinceName = addressDB.getProvince();
        String cityName = addressDB.getCity();
        String districtName = addressDB.getDistrict();

        tv_province.setText(provinceName);
        tv_city.setText(cityName);
        tv_district.setText(districtName);

        address.setProvince_id(mProvinceDBManager.getProvinceId(provinceName));
        address.setCity_id(mCityDBManager.getCityId(cityName));
        address.setDistrict_id(mDistrictDBManager.getDistrictId(districtName));
        if (!isAdd) {
            address.setProvince(provinceName);
            address.setCity(cityName);
            address.setDistrict(districtName);
        }
    }

    /**
     * 保存
     */
    @Override
    public void onRightAction() {
        //if (checkData()) {
//            address.setName(et_name.getText().toString());
//            address.setMobile(et_phone.getText().toString());
//            address.setStreet(et_street.getText().toString());
            if (isAdd) {
                addressAdd();
                //mAddressDBManager.insert(address);
                //EventBusHelper.post(address, EVENT_ADDRESS_ADD);
            } else {
                mAddressDBManager.update(address);
                EventBusHelper.post(address, EVENT_ADDRESS_UPDATE);
            }
            ActivityManager.getInstance().finishCurrentActivity();
        //}
    }

    /**
     * 验证信息
     */
    private boolean checkData() {
        String name = et_name.getText().toString();
        String street = et_street.getText().toString();
        String phone = et_phone.getText().toString();
        boolean noFill = false;
        StringBuilder str = new StringBuilder();
        if (null == name || "".equals(name)) {
            str.append(" 姓名 ");
            noFill = true;
        }
        if (null == street || "".equals(street)){
            str.append(" 地址 ");
            noFill = true;
        }
        if (null == phone || "".equals(phone)){
            str.append(" 电话 ");
            noFill = true;
        }
        if (noFill) {
            ToastUtils.showToastShort(this, "收货人" + str + "未填写");
            return false;
        }
        return true;
    }

    @Override
    public void onSwitchClick(View v, boolean isSelect) {
        address.setIsDefault(isSelect);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isAdd)
            if (s != null && s.length() > 0)
                setRightTitleColor(R.color.text_main_color_nor);
            else
                setRightTitleColor(R.color.text_color_gray);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
