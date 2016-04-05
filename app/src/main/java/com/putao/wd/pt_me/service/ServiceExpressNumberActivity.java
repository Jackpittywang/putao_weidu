package com.putao.wd.pt_me.service;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.ColorConstant;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ResourcesUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.picker.OptionPicker;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 售后--填写快递单号
 * Created by yanghx on 2016/1/4.
 */
public class ServiceExpressNumberActivity extends PTWDActivity implements OnClickListener {
    public static final String KEY_SERVICE_ID = "serviceId";
    @Bind(R.id.ll_select_company)
    LinearLayout ll_select_company;
    @Bind(R.id.tv_company)
    TextView tv_company;
    @Bind(R.id.et_express_number)
    EditText et_express_number;

    private OptionPicker mCompanyPicker;
    private Map<String, String> mCompanyMap;
    private String company_code;//快递公司code
    private String express_code;//快递单号

    @Override
    protected int getLayoutId() {
        return R.layout.activity_express_number;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mCompanyMap = new ArrayMap<>();
        String[] companys = ResourcesUtils.getStringArray(mContext, R.array.company);
        String[] company_codes = ResourcesUtils.getStringArray(mContext, R.array.company_code);
        for (int i = 0; i < companys.length; i++) {
            mCompanyMap.put(companys[i], company_codes[i]);
        }
        mCompanyPicker = new OptionPicker(this, companys);
        mCompanyPicker.setTextColor(ColorConstant.MAIN_COLOR_SEL, ColorConstant.MAIN_COLOR_NOR);
        mCompanyPicker.setLineColor(ColorConstant.MAIN_COLOR_NOR);
        mCompanyPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {
                tv_company.setText(option);
                company_code = mCompanyMap.get(option);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.ll_select_company)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_company:
                mCompanyPicker.show();
                break;
        }
    }

    @Override
    public void onRightAction() {
        if (checkInfo()) {
            return;
        }
        networkRequest(OrderApi.fillExpress(args.getString(KEY_SERVICE_ID), company_code, express_code), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                Logger.d("填写快递单号 = " + result.toString());
                ActivityManager.getInstance().removeCurrentActivity();
                finish();
                loading.dismiss();
            }
        });
    }

    /**
     * 校验必填项
     */
    private boolean checkInfo() {
        express_code = et_express_number.getText().toString();
        if (StringUtils.isEmpty(company_code)) {
            ToastUtils.showToastShort(mContext, "请选择快递公司");
            return true;
        }
        if (StringUtils.isEmpty(express_code)) {
            ToastUtils.showToastShort(mContext, "请填写快递单号");
            return true;
        }
        return false;
    }

}
