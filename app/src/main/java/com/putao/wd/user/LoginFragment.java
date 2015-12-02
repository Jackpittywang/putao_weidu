package com.putao.wd.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.MainActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountApi;
import com.putao.wd.account.AccountCallback;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.base.PTWDFragment;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.CleanableEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录页面
 * Created by guchenkai on 2015/11/29.
 */
public class LoginFragment extends PTWDFragment implements View.OnClickListener, TextWatcher {
    @Bind(R.id.et_mobile)
    CleanableEditText et_mobile;
    @Bind(R.id.et_password)
    CleanableEditText et_password;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.rl_graph_verify)
    RelativeLayout rl_graph_verify;//图形验证码

    private int mErrorCount = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavgation();
        et_mobile.addTextChangedListener(this);
        et_password.addTextChangedListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[]{AccountApi.URL_LOGIN};
    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forget})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登录
                startActivity(MainActivity.class);
                networkRequest(AccountApi.login(et_mobile.getText().toString(), et_password.getText().toString()),
                        new AccountCallback(loading) {
                            @Override
                            public void onSuccess(JSONObject result) {
                                AccountHelper.login(result);
                                startActivity(MainActivity.class);
                            }

                            @Override
                            public void onError(String error_msg) {
                                ToastUtils.showToastLong(mActivity, error_msg);
                                mErrorCount++;
                                if (mErrorCount == 3)
                                    rl_graph_verify.setVisibility(View.VISIBLE);
                            }
                        });
                break;
            case R.id.tv_register://注册新用户
                startFragment(RegisterFragment.class);
                break;
            case R.id.tv_forget://忘记密码
                startFragment(ForgetPasswordFragment.class);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            btn_login.setClickable(true);
            btn_login.setBackgroundResource(R.drawable.btn_get_focus);
        } else {
            btn_login.setClickable(false);
            btn_login.setBackgroundResource(R.drawable.btn_los_focus);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
