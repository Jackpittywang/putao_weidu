package com.putao.wd.me.address;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.ColorConstant;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.db.CityDBManager;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.db.ProvinceDBManager;
import com.putao.wd.me.address.fragment.DistrictFragment;
import com.putao.wd.model.Address;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;
import com.sunnybear.library.view.SwitchButton;

import java.util.List;

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
    public static final String BUNDLE_KEY_ADDRESS = "address";

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

    private ProvinceDBManager mProvinceDBManager;
    private CityDBManager mCityDBManager;
    private DistrictDBManager mDistrictDBManager;

    private Address mAddress;

    private boolean isAdd = false;//是否是新增地址

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_edit;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mProvinceDBManager = (ProvinceDBManager) mApp.getDataBaseManager(ProvinceDBManager.class);
        mCityDBManager = (CityDBManager) mApp.getDataBaseManager(CityDBManager.class);
        mDistrictDBManager = (DistrictDBManager) mApp.getDataBaseManager(DistrictDBManager.class);

        mAddress = (Address) args.getSerializable(BUNDLE_KEY_ADDRESS);
        if (mAddress == null) {
            ll_delete_address.setVisibility(View.GONE);
            mAddress = new Address();
            isAdd = true;
        } else initView();
        addListener();
    }

    /**
     * 编辑时初始化页面
     */
    private void initView() {
        et_name.setText(mAddress.getRealname());
        tv_province.setText(mProvinceDBManager.getProvinceNameByProvinceId(mAddress.getProvince_id() + ""));
        tv_city.setText(mCityDBManager.getCityNameByCityId(mAddress.getCity_id() + ""));
        tv_district.setText(mDistrictDBManager.getdistrictNameByDistrictId(mAddress.getArea_id()));
        et_street.setText(mAddress.getAddress());
        et_phone.setText(mAddress.getMobile());
        btn_default.setState(StringUtils.equals(mAddress.getStatus(), "1"));
    }

    private void addListener() {
        et_name.addTextChangedListener(this);
        et_phone.addTextChangedListener(this);
        btn_default.setOnSwitchClickListener(this);
    }

    /**
     * 添加收货地址
     */
    private void addressAdd(String realname, String city_id, String province_id, String area_id, String address, String mobile, String tel, String postcode, String status) {
        networkRequest(OrderApi.addressAdd(realname, city_id, province_id, area_id, address, mobile, tel, postcode, status),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        Logger.d(result.toString());
                        EventBusHelper.post(EVENT_ADDRESS_ADD, EVENT_ADDRESS_ADD);
                        loading.dismiss();
                    }
                });
    }

    /**
     * 更新收货地址
     */
    private void addressUpdate(String address_id, String realname, String city_id, String province_id, String area_id, String address, String mobile, String tel, String postcode, String status) {
        networkRequest(OrderApi.addressUpdate(address_id, realname, city_id, province_id, area_id, address, mobile, tel, postcode, status),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        Logger.d(result.toString());
                        EventBusHelper.post(EVENT_ADDRESS_UPDATE, EVENT_ADDRESS_UPDATE);
                        loading.dismiss();
                    }
                });
    }

    /**
     * 删除收货地址
     */
    private void addressDelete(String address_id) {
        networkRequest(OrderApi.addressDelete(address_id), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                Logger.d(result.toString());
                loading.dismiss();
            }
        });
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
                addressDelete(mAddress.getId() + "");
                EventBusHelper.post(mAddress, EVENT_ADDRESS_DELETE);
                finish();
                break;
            case R.id.ll_city_sel://地区选择
                startActivity(CitySelectActivity.class);
                break;
        }
    }

    @Subcriber(tag = DistrictFragment.EVENT_DISTRICT_SELECT)
    public void eventDistrictSelect(List<String> list) {
        String provinceName = list.get(0);
        String cityName = list.get(1);
        String districtName = list.get(2);

        tv_province.setText(provinceName);
        tv_city.setText(cityName);
        tv_district.setText(districtName);

        mAddress.setProvince_id(mProvinceDBManager.getProvinceId(provinceName));
        mAddress.setCity_id(mCityDBManager.getCityId(mAddress.getProvince_id(), cityName));
        mAddress.setArea_id(mDistrictDBManager.getDistrictId(mAddress.getProvince_id(), mAddress.getCity_id(), districtName));

        String addressName = "{\"" + mAddress.getProvince_id() + "\":\"" + provinceName + "\",\"" + mAddress.getCity_id() + "\":\"" + cityName + "\",\"" + mAddress.getArea_id() + "\":\"" + districtName + "\"}";
        mAddress.setAddressName(addressName);
    }

    /**
     * 保存
     */
    @Override
    public void onRightAction() {
        if (!checkData()) {
            ToastUtils.showToastShort(mContext, "您还有地址信息没有填写哟!");
            return;
        }
        mAddress.setRealname(et_name.getText().toString());
        mAddress.setMobile(et_phone.getText().toString());
        mAddress.setAddress(et_street.getText().toString());
        if (StringUtils.equals(mAddress.getStatus(), "1"))
            EventBusHelper.post(mAddress, EVENT_ADDRESS_IS_DEFAULT);
        if (isAdd)
            addressAdd(mAddress.getRealname(),
                    mAddress.getCity_id(),
                    mAddress.getProvince_id(),
                    mAddress.getArea_id(),
                    mAddress.getAddress(),
                    mAddress.getMobile(),
                    null, null,
                    mAddress.getStatus());
        else
            addressUpdate(mAddress.getId(),
                    mAddress.getRealname(), mAddress.getCity_id(),
                    mAddress.getProvince_id(),
                    mAddress.getArea_id(),
                    mAddress.getAddress(), mAddress.getMobile(),
                    null, null, mAddress.getStatus());
        finish();
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
        if (null == street || "".equals(street)) {
            str.append(" 地址 ");
            noFill = true;
        }
        if (null == phone || "".equals(phone)) {
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
        mAddress.setStatus(isSelect ? "1" : "0");
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s != null && s.length() > 0)
            setRightTitleColor(ColorConstant.MAIN_COLOR_NOR);
        else
            setRightTitleColor(ColorConstant.MAIN_COLOR_DIS);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
