package com.putao.wd.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.CleanableEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 重置密码
 * Created by wango on 2015/12/1.
 */
public class ResetPasswordAcitivity extends PTWDActivity implements View.OnClickListener, TextWatcher {
    @Bind(R.id.et_password)
    CleanableEditText et_password;//密码
    @Bind(R.id.iv_lock)
    ImageView iv_lock;//密码是否可见图标
    @Bind(R.id.btn_complete)
    Button btn_complete;

    private boolean isPasswordVisible = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置为隐藏密码
        et_password.addTextChangedListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.btn_complete, R.id.ll_password_visible})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_complete://完成
                startActivity(LoginActivity.class);
                break;
            case R.id.ll_password_visible://是否显示密码
                if (isPasswordVisible) { //设置为隐藏
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPasswordVisible = false;
                    iv_lock.setImageResource(R.drawable.icon_20_10);
                } else {//设置为可见
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isPasswordVisible = true;
                    iv_lock.setImageResource(R.drawable.icon_20_11);
                }
                removeCursor(et_password);
                break;
        }
    }

    /**
     * 移动光标到最后
     *
     * @param editText editText
     */
    private void removeCursor(EditText editText) {
        Editable etext = editText.getText();
        Selection.setSelection(etext, etext.length());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            btn_complete.setClickable(true);
            btn_complete.setBackgroundResource(R.drawable.btn_get_focus);
        } else {
            btn_complete.setClickable(false);
            btn_complete.setBackgroundResource(R.drawable.btn_los_focus);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}