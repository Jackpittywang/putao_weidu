package com.putao.wd.user;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.api.ScanApi;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.model.http.callback.JSONObjectCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 网页版登录确认
 * Created by guchenkai on 2015/12/9.
 */
public class WebLoginActivity extends PTWDActivity implements View.OnClickListener {
    public static final String URL_LOGIN = "url_login";

    @Bind(R.id.ll_qr_code_toast)
    LinearLayout ll_qr_code_toast;//二维码toast

    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_login;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        url = args.getString(URL_LOGIN);
        Logger.d("url:" + url);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.btn_login, R.id.tv_cancel_login})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登录
                networkRequest(ScanApi.confirmLogin(url), new JSONObjectCallback() {
                    @Override
                    public void onSuccess(String url, JSONObject result) {
                        int error_code = result.getInteger("error_code");
                        if (error_code == 0)
                            ToastUtils.showToastLong(mContext, "葡萄官网登录成功");
                        loading.dismiss();
//                        finish();
                        ActivityManager.getInstance().popOtherActivity(IndexActivity.class);
                    }

                    @Override
                    public void onCacheSuccess(String url, JSONObject result) {

                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        loading.dismiss();
                    }
                });
                break;
            case R.id.tv_cancel_login://取消登录
                ActivityManager.getInstance().popOtherActivity(IndexActivity.class);
                break;
        }
    }
}
