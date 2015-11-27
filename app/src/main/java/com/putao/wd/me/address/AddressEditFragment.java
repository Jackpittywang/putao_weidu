package com.putao.wd.me.address;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 收货地址标记
 * Created by guchenkai on 2015/11/26.
 */
public class AddressEditFragment extends PTWDFragment<GlobalApplication> implements View.OnClickListener {
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
    @Bind(R.id.cb_default)
    CheckBox ck_default;//是否设置默认
    @Bind(R.id.ll_delete_address)
    LinearLayout ll_delete_address;//删除

    private boolean isAdd = false;//是否是新增地址

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_address_edit;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavgation();
        isAdd = args.getBoolean(AddressActivity.KEY_IS_ADD);
        if (isAdd)
            ll_delete_address.setVisibility(View.GONE);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.ll_delete_address)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_delete_address://删除本条地址

                break;
        }
    }

    /**
     * 保存
     */
    @Override
    public void onRightAction() {

    }
}
