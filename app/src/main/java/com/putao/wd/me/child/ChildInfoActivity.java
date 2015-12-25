package com.putao.wd.me.child;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.ResourcesUtils;
import com.sunnybear.library.view.CleanableEditText;
import com.sunnybear.library.view.picker.DatePicker;
import com.sunnybear.library.view.picker.OptionPicker;
import com.sunnybear.library.view.picker.SexPicker;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 孩子信息
 * Created by guchenkai on 2015/12/25.
 */
public class ChildInfoActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.et_nickname)
    CleanableEditText et_nickname;
    @Bind(R.id.tv_sex)
    TextView tv_sex;
    @Bind(R.id.tv_birthday)
    TextView tv_birthday;
    @Bind(R.id.tv_identity)
    TextView tv_identity;

    private DatePicker mDatePicker;//日期选择器
    private SexPicker mSexPicker;//性别选择器
    private OptionPicker mFamilyPicker;//亲友选择器

    @Override
    protected int getLayoutId() {
        return R.layout.activity_child_info;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        initSexPicker();
        initDatePicker();
        initFamilyPicker();
    }

    /**
     * 初始化性别选择器
     */
    private void initSexPicker() {
        mSexPicker = new SexPicker(this);
        mSexPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {
                tv_sex.setText(option);
            }
        });
    }

    /**
     * 初始化日期选择器
     */
    private void initDatePicker() {
        mDatePicker = new DatePicker(this);
        mDatePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tv_birthday.setText(year + "/" + month + "/" + day);
            }
        });
    }

    /**
     * 初始化身份选择器
     */
    private void initFamilyPicker() {
        mFamilyPicker = new OptionPicker(this, ResourcesUtils.getStringArray(mContext, R.array.family));
        mFamilyPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {
                tv_identity.setText(option);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.tv_sex, R.id.tv_birthday, R.id.tv_identity})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sex://性別
                mSexPicker.show();
                break;
            case R.id.tv_birthday://生日
                mDatePicker.show();
                break;
            case R.id.tv_identity://身份
                mFamilyPicker.show();
                break;
        }
    }
}
