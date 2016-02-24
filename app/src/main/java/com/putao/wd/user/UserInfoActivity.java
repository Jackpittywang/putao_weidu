package com.putao.wd.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;

import butterknife.Bind;

/**
 * 保存昵称
 * Created by guchenkai on 2015/11/29.
 */
public class UserInfoActivity extends PTWDActivity {

    @Bind(R.id.et_intro)
    EditText et_intro;
    @Bind(R.id.tv_info_limit)
    TextView tv_info_limit;

    private String mUserInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        initData();
        initView();
        addListener();
    }

    private void initView() {
        et_intro.setText(mUserInfo);
        tv_info_limit.setText((40 - mUserInfo.length()) + "");
        et_intro.setSelection(mUserInfo.length());
    }

    private void initData() {
        Bundle bundle = args.getBundle(CompleteActivity.USER_INFO);
        mUserInfo = bundle.getString(CompleteActivity.USER_INFO);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 保存用户信息
     * by yanghx
     * 请求参数 String类型 昵称、图片url、个人简介
     */
    @Override
    public void onRightAction() {
        String userInfo = et_intro.getText().toString();
        if (mUserInfo.equals(userInfo)) {
            ToastUtils.showToastShort(mContext, "没有更改无需保存");
            return;
        }
        upload();
    }

    /**
     * 上传PHP服务器
     */
    private void upload() {
        final String etIntro = et_intro.getText().toString();
        networkRequest(UserApi.userInfo(etIntro),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        Logger.i("保存用户信息");
                        Intent intent = new Intent();
                        intent.putExtra(CompleteActivity.USER_INFO, etIntro);
                        setResult(1, intent);
                        finish();
                    }

                    @Override
                    public void onFinish(String url, boolean isSuccess, String msg) {
                        loading.dismiss();
                        ToastUtils.showToastShort(mContext, msg);
                    }
                });
    }

    private void addListener() {
        et_intro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_info_limit.setText((40 - et_intro.getText().toString().length()) + "");
            }
        });
    }
}
