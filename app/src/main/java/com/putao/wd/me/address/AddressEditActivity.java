package com.putao.wd.me.address;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.DistrictSelect;
import com.putao.wd.me.address.fragment.DistrictFragment;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.view.SwitchButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 收货地址编辑
 * Created by guchenkai on 2015/11/27.
 */
public class AddressEditActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.et_name)
    EditText et_name;//收货人姓名
    @Bind(R.id.tv_province)
    TextView tv_province;//省份
    @Bind(R.id.tv_city)
    TextView tv_city;//城市
    @Bind(R.id.tv_district)
    TextView tv_district;//城区
    @Bind(R.id.et_street)
    EditText et_street;//街道
    @Bind(R.id.et_phone)
    EditText et_phone;//电话号码
    @Bind(R.id.btn_default)
    SwitchButton btn_default;//是否设置默认
    @Bind(R.id.ll_delete_address)
    LinearLayout ll_delete_address;//删除

    private boolean isAdd = false;//是否是新增地址

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_edit;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        isAdd = args.getBoolean(AddressListActivity.KEY_IS_ADD);
        if (isAdd)
            ll_delete_address.setVisibility(View.GONE);
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

                break;
            case R.id.ll_city_sel://地区选择
                startActivity(CitySelectActivity.class);
                break;
        }
    }

    @Subcriber(tag = DistrictFragment.EVENT_DISRICT_SELECT)
    public void eventDistrictSelect(DistrictSelect districtSelect) {
        tv_province.setText(districtSelect.getProvinceName());
        tv_city.setText(districtSelect.getCityName());
        tv_district.setText(districtSelect.getDistrictName());
    }

    /**
     * 保存
     */
    @Override
    public void onRightAction() {

    }
}
