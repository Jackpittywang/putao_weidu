package com.putao.wd.me.setting;

import android.os.Bundle;
import android.view.View;

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
public class ModifyPasswardActivity extends PTWDActivity {

    @Bind(R.id.et_pw_old)
    CleanableEditText et_pw_old;
    @Bind(R.id.et_pw_new)
    CleanableEditText et_pw_new;
    @Bind(R.id.et_pw_repeat)
    CleanableEditText et_pw_repeat;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
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
        check(oldPassword, newPassword, repeatPassword);
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
     * @param oldPassword
     * @param newPassword
     * @param repeatPassword
     */
    private void check(String oldPassword, String newPassword, String repeatPassword) {
        if (StringUtils.isEmpty(oldPassword)) {
            ToastUtils.showToastShort(mContext, "请输入旧密码");
            return;
        } else if (StringUtils.isEmpty(newPassword)) {
            ToastUtils.showToastShort(mContext, "请输入新密码");
            return;
        } else if (!StringUtils.equals(newPassword, repeatPassword)) {
            ToastUtils.showToastShort(mContext, "两次输入的密码不一致");
            return;
        }
    }
}
