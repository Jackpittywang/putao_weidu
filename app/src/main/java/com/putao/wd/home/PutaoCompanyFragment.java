package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.putao.wd.MainActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.companion.manage.ManageActivity;
import com.putao.wd.model.Management;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;

import butterknife.Bind;

/**
 * 陪伴
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoCompanyFragment extends BasicFragment implements View.OnClickListener {

    @Bind(R.id.tv_title_bar_left)
    TextView tv_title_bar_left;
    @Bind(R.id.iv_title_bar_right1)
    ImageView iv_title_bar_right1;
    @Bind(R.id.iv_title_bar_right2)
    ImageView iv_title_bar_right2;
    @Bind(R.id.ll_empty)
    LinearLayout ll_empty;
    @Bind(R.id.ll_no_empty)
    LinearLayout ll_no_empty;
    @Bind(R.id.btn_explore_empty)
    Button btn_explore_empty;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_company;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        checkDevices();
        addListener();
    }

    private void addListener() {
        tv_title_bar_left.setOnClickListener(this);
        iv_title_bar_right1.setOnClickListener(this);
        iv_title_bar_right2.setOnClickListener(this);
        btn_explore_empty.setOnClickListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 是否已经添加设备
     */
    private void checkDevices() {
        networkRequest(ExploreApi.getManagement(),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        if (!"".equals(result)) {
                            Management management = JSON.parseObject(result, Management.class);
                            if (management.getSlave_device_list() != null && management.getSlave_device_list().size() > 0) {
                                ll_empty.setVisibility(View.GONE);
                                ll_no_empty.setVisibility(View.VISIBLE);
                            } else {
                                ll_empty.setVisibility(View.VISIBLE);
                                ll_no_empty.setVisibility(View.GONE);
                            }
                        }
                        loading.dismiss();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if (!AccountHelper.isLogin()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, MainActivity.class);
            startActivity(LoginActivity.class, bundle);
            return;
        }
        switch (v.getId()) {
            case R.id.tv_title_bar_left:
                startActivity(ManageActivity.class);
                break;
            case R.id.iv_title_bar_right1:
                break;
            case R.id.iv_title_bar_right2:
                startActivity(CaptureActivity.class);
                break;
            case R.id.btn_explore_empty:
                startActivity(CaptureActivity.class);
                break;
        }
    }
}
