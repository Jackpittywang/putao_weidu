package com.putao.wd.me.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountApi;
import com.putao.wd.account.AccountCallback;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 修改密码
 * Created by yanghx on 2015/12/18.
 */
public class ModifyPasswardActivity extends PTWDActivity<GlobalApplication> {

    @Bind(R.id.et_pw_old)
    EditText et_pw_old;
    @Bind(R.id.et_pw_new)
    EditText et_pw_new;
    @Bind(R.id.et_pw_repeat)
    EditText et_pw_repeat;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.tv_change)
    public void onClick(View v) {
        String oldPassword = et_pw_old.getText().toString();
        String newPassword = et_pw_new.getText().toString();
        String repeatPassword = et_pw_repeat.getText().toString();
        networkRequest(AccountApi.updatePassword(oldPassword, newPassword, repeatPassword), new AccountCallback(loading) {
            @Override
            public void onSuccess(JSONObject result) {
                loading.dismiss();
                finish();
            }

            @Override
            public void onError(String error_msg) {
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
    }
}
