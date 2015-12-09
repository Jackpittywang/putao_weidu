package com.putao.wd.user;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 网页版登录确认
 * Created by guchenkai on 2015/12/9.
 */
public class WebLoginActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.ll_qr_code_toast)
    LinearLayout ll_qr_code_toast;//二维码toast

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_login;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.btn_login, R.id.btn_cancel_login})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登录

                break;
            case R.id.btn_cancel_login://取消登录

                break;
        }
    }
}
