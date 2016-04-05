package com.putao.wd.pt_me.setting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountApi;
import com.putao.wd.account.AccountCallback;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 修改密码
 * Created by yanghx on 2015/12/18.
 */
public class ModifyPasswardActivity extends PTWDActivity implements TextWatcher{

    @Bind(R.id.et_pw_old)
    CleanableEditText et_pw_old;
    @Bind(R.id.et_pw_new)
    CleanableEditText et_pw_new;
    @Bind(R.id.et_pw_repeat)
    CleanableEditText et_pw_repeat;
    @Bind(R.id.btn_change)
    Button btn_change;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        btn_change.setClickable(false);
        addListener();
    }

    private void addListener() {
        et_pw_old.addTextChangedListener(this);
        et_pw_new.addTextChangedListener(this);
        et_pw_repeat.addTextChangedListener(this);
    }

    /**
     * 验证密码
     */
    private void checkPsw() {
        String etPwOld = et_pw_old.getText().toString();
        String etPwNew = et_pw_new.getText().toString();
        String etPwRepeat = et_pw_repeat.getText().toString();
        if (etPwOld.length() < 6 || etPwNew.length() < 6 || etPwRepeat.length() < 6) {
            btn_change.setClickable(false);
            btn_change.setBackgroundResource(R.drawable.text_userinfo_limit_shape);
            return;
        }
        btn_change.setClickable(true);
        btn_change.setBackgroundResource(R.drawable.btn_order_express_selector);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.btn_change)
    public void onClick(View v) {
        String oldPassword = et_pw_old.getText().toString();
        String newPassword = et_pw_new.getText().toString();
        String repeatPassword = et_pw_repeat.getText().toString();
        if(! check(newPassword, repeatPassword)) return;
        networkRequest(AccountApi.updatePassword(oldPassword, newPassword, repeatPassword), new AccountCallback(loading) {
            @Override
            public void onSuccess(JSONObject result) {
                loading.dismiss();
                finish();
            }

            @Override
            public void onError(String error_msg) {
                ToastUtils.showToastLong(mContext, error_msg);
            }
        });
    }

    /**
     * 验证数据
     *
     * @param newPassword
     * @param repeatPassword
     */
    private boolean check(String newPassword, String repeatPassword) {
        if (StringUtils.isEmpty(newPassword)) {
            ToastUtils.showToastShort(mContext, "请输入新密码");
            return false;
        } else if (!StringUtils.equals(newPassword, repeatPassword)) {
            ToastUtils.showToastShort(mContext, "两次输入的密码不一致");
            return false;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        checkPsw();
    }
}
