package com.putao.wd.pt_me.child;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.putao.wd.ColorConstant;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ChildInfo;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ResourcesUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;
import com.sunnybear.library.view.picker.DatePicker;
import com.sunnybear.library.view.picker.OptionPicker;
import com.sunnybear.library.view.picker.SexPicker;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Bind(R.id.iv_sex)
    ImageView iv_sex;
    @Bind(R.id.iv_birthday)
    ImageView iv_birthday;
    @Bind(R.id.iv_identity)
    ImageView iv_identity;

    private DatePicker mDatePicker;//日期选择器
    private SexPicker mSexPicker;//性别选择器
    private OptionPicker mFamilyPicker;//亲友选择器

    private String mEtNickname;//昵称
    private String mTvIdentity;//关系
    private String mTvSex;//性别
    private String mTvBirthday;//生日

    private byte[] previousArray;
    private String currentStr;
    private int maxLen = 16;

    private boolean isEditable = true;//是否可以修改

    @Override
    protected int getLayoutId() {
        return R.layout.activity_child_info;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        getChildInfo();
        initSexPicker();
        initDatePicker();
        initFamilyPicker();
        // 限制中文8个字，英文16个字的filter
        InputFilter[] filters = {new NameLengthFilter(16)};
        et_nickname.setFilters(filters);

        //et_nickname.addTextChangedListener(new MaxLengthWatcher(16, et_nickname));
//        et_nickname.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (et_nickname == null) {
//                    return;
//                }
//                Editable editable = et_nickname.getText();
//
//                if (editable.toString() == null) {
//                    return;
//                }
//
//                previousArray = editable.toString().getBytes();
//
//
//                if (previousArray.length > maxLen) {
//                    if (previousArray[maxLen - 2] < 0) {
//                        currentStr = new String(previousArray);
//                    } else {
//                        if (previousArray[maxLen - 1] < 0) {
//                            currentStr = new String(previousArray, 0, previousArray.length - 1);
//                        } else {
//                            currentStr = new String(previousArray);
//                        }
//                    }
//
//                    et_nickname.setText(currentStr);
//                    editable = et_nickname.getText();
//                    Selection.setSelection(editable, editable.length());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    /**
     * 检查孩子信息是否完整
     *
     * @return
     */
    private boolean checkInfo() {
        mEtNickname = et_nickname.getText().toString();

        mTvBirthday = tv_birthday.getText().toString();
        if (mEtNickname.trim().isEmpty()) {
            ToastUtils.showToastShort(mContext, "昵称不能为空");
            return false;
        }

        if (StringUtils.isEmpty(tv_sex.getText().toString())) {
            ToastUtils.showToastShort(mContext, "请选择性别");
            return false;
        }

        if (StringUtils.isEmpty(mTvBirthday)) {
            ToastUtils.showToastShort(mContext, "请选择生日");
            return false;
        }

        if (StringUtils.isEmpty(tv_identity.getText().toString())) {
            ToastUtils.showToastShort(mContext, "请选择身份");
            return false;
        }

        switch (tv_identity.getText().toString()) {
            case "我是爸爸":
                mTvIdentity = "FATHER";
                break;
            case "我是妈妈":
                mTvIdentity = "MATHER";
                break;
            case "我是爷爷":
                mTvIdentity = "YEYE";
                break;
            case "我是奶奶":
                mTvIdentity = "NAINAI";
                break;
            case "我是外公":
                mTvIdentity = "WAIGONG";
                break;
            case "我是外婆":
                mTvIdentity = "WAIPO";
                break;
            default:
                mTvIdentity = "QITA";
        }


        switch (tv_sex.getText().toString()) {
            case "女":
                mTvSex = "F";
                break;
            default:
                mTvSex = "M";
        }

        return true;
    }

    /**
     * 获取孩子信息
     */
    private void getChildInfo() {
        networkRequest(UserApi.getChildInfo(),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        if (!StringUtils.isEmpty(result)) {
                            ChildInfo info = JSON.parseObject(result, ChildInfo.class);
                            if (result != null) {
                                et_nickname.setText(info.getBaby_name());
                                et_nickname.setSelection(info.getBaby_name().length());
                                tv_identity.setText(info.getRelation());
                                tv_sex.setText(!StringUtils.equals(info.getSex(), "F") ? "男" : "女");
                                tv_birthday.setText(info.getBirthday());
                                AccountHelper.setChildInfo(info);
                                isEditable = false;
                                cancelChoose();
                                switch (info.getRelation()) {
                                    case "FATHER":
                                        tv_identity.setText("我是爸爸");
                                        break;
                                    case "MATHER":
                                        tv_identity.setText("我是妈妈");
                                        break;
                                    case "YEYE":
                                        tv_identity.setText("我是爷爷");
                                        break;
                                    case "NAINAI":
                                        tv_identity.setText("我是奶奶");
                                        break;
                                    case "WAIGONG":
                                        tv_identity.setText("我是外公");
                                        break;
                                    case "WAIPO":
                                        tv_identity.setText("我是外婆");
                                        break;
                                    default:
                                        tv_identity.setText("其他亲友");
                                }
                            }
                        }
                        loading.dismiss();
                    }
                });
    }

    /**
     * 取消选择按钮
     */
    private void cancelChoose() {
        iv_sex.setVisibility(View.GONE);
        iv_birthday.setVisibility(View.GONE);
        iv_identity.setVisibility(View.GONE);
    }

    /**
     * 初始化性别选择器
     */
    private void initSexPicker() {
        mSexPicker = new SexPicker(this);
        mSexPicker.setTextColor(ColorConstant.MAIN_COLOR_SEL, ColorConstant.MAIN_COLOR_NOR);
        mSexPicker.setLineColor(ColorConstant.MAIN_COLOR_NOR);
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
        mDatePicker.setTextColor(ColorConstant.MAIN_COLOR_SEL, ColorConstant.MAIN_COLOR_NOR);
        mDatePicker.setLineColor(ColorConstant.MAIN_COLOR_NOR);
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
        mFamilyPicker.setTextColor(ColorConstant.MAIN_COLOR_SEL, ColorConstant.MAIN_COLOR_NOR);
        mFamilyPicker.setLineColor(ColorConstant.MAIN_COLOR_NOR);
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
        if (isEditable)
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

    @Override
    public void onRightAction() {
        if (checkInfo())
            networkRequest(UserApi.setChildInfo(AccountHelper.getChildInfo().getBaby_id() + "", mEtNickname, mTvIdentity, mTvSex, mTvBirthday),
                    new SimpleFastJsonCallback<String>(String.class, loading) {
                        @Override
                        public void onSuccess(String url, String result) {
                            loading.dismiss();
                            Logger.i(result + "-----------------");
                            ToastUtils.showToastShort(ChildInfoActivity.this, "保存成功！");
                            finish();
                        }
                    });
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
    }


    private class NameLengthFilter implements InputFilter {
        int MAX_EN;// 最大英文/数字长度 一个汉字算两个字母
        String regEx = "[\\u4e00-\\u9fa5]"; // unicode编码，判断是否为汉字

        public NameLengthFilter(int mAX_EN) {
            super();
            MAX_EN = mAX_EN;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            int destCount = dest.toString().length()
                    + getChineseCount(dest.toString());
            int sourceCount = source.toString().length()
                    + getChineseCount(source.toString());
            if (destCount + sourceCount > MAX_EN) {
                return "";

            } else {
                return source;
            }
        }

        private int getChineseCount(String str) {
            int count = 0;
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            while (m.find()) {
                for (int i = 0; i <= m.groupCount(); i++) {
                    count = count + 1;
                }
            }
            return count;
        }
    }
}
