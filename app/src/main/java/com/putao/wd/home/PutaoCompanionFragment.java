package com.putao.wd.home;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.putao.wd.IndexActivity;
import com.putao.wd.MainActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.explore.SmartActivity;
import com.putao.wd.home.adapter.ProductsAdapter;
import com.putao.wd.me.MeActivity;
import com.putao.wd.model.Management;
import com.putao.wd.model.ManagementProduct;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 陪伴
 * Created by guchenkai on 2016/1/13.
 */
public class PutaoCompanionFragment extends BasicFragment implements View.OnClickListener, OnItemClickListener {
    @Bind(R.id.iv_title_bar_right1)
    ImageView iv_title_bar_right1;
    @Bind(R.id.iv_title_bar_right2)
    ImageView iv_title_bar_right2;
    @Bind(R.id.cv_empty)
    CardView cv_empty;
    @Bind(R.id.cv_no_empty)
    CardView cv_no_empty;
    @Bind(R.id.btn_explore_empty)
    Button btn_explore_empty;
    @Bind(R.id.rv_products)
    BasicRecyclerView rv_products;
    @Bind(R.id.iv_user_icon)
    ImageDraweeView iv_user_icon;

    ProductsAdapter mProductsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_companion;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
    }

    private void addClickListener() {
        iv_user_icon.setOnClickListener(this);
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
                                cv_empty.setVisibility(View.GONE);
                                cv_no_empty.setVisibility(View.VISIBLE);
                                btn_explore_empty.setVisibility(View.GONE);
                                getManagement();
                            }
                        }
                        loading.dismiss();
                    }
                });
    }

    private void getManagement() {
        networkRequest(ExploreApi.getManagement(), new SimpleFastJsonCallback<Management>(Management.class, loading) {
            @Override
            public void onSuccess(String url, Management result) {
                if (null != result) {
                    mProductsAdapter.clear();
                    List<ManagementProduct> products = result.getProduct_list();
                    for (int i = 0; i < 6; i++) {
                        if (i < products.size())
                            mProductsAdapter.add(products.get(i).getProduct_icon());
                        else
                            mProductsAdapter.add("");
                    }
                }
                loading.dismiss();
            }
        });
    }

    /**
     * 没有登录或没有绑定设备时的界面
     */
    private void empty() {
        cv_empty.setVisibility(View.VISIBLE);
        btn_explore_empty.setVisibility(View.VISIBLE);
        cv_no_empty.setVisibility(View.GONE);
        iv_user_icon.setDefaultImage(R.drawable.img_head_default);
    }


    @Override
    public void onClick(View v) {
        if (!AccountHelper.isLogin()) {
            Bundle bundle = new Bundle();
            toLoginActivity(v, bundle);
            bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, IndexActivity.class);
            startActivity(LoginActivity.class, bundle);
            return;
        }
        switch (v.getId()) {
            case R.id.iv_user_icon:
                startActivity(MeActivity.class);
                break;
            case R.id.iv_title_bar_right1:
                startActivity(SmartActivity.class);
                break;
            case R.id.iv_title_bar_right2:
                startActivity(CaptureActivity.class);
                break;
            case R.id.btn_explore_empty:
                startActivity(CaptureActivity.class);
                break;
        }
    }

    private void toLoginActivity(View v, Bundle bundle) {
        switch (v.getId()) {
            case R.id.iv_user_icon:
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, MeActivity.class);
                break;
            case R.id.iv_title_bar_right1:
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, SmartActivity.class);
                break;
            case R.id.iv_title_bar_right2:
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, CaptureActivity.class);
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        empty();
        addClickListener();
        if (AccountHelper.isLogin()) {
            mProductsAdapter = new ProductsAdapter(mActivity, null);
            rv_products.setAdapter(mProductsAdapter);
            checkDevices();
            rv_products.setOnItemClickListener(this);
            iv_user_icon.setImageURL(AccountHelper.getCurrentUserInfo().getHead_img());
        }
    }

    @Override
    public void onItemClick(Serializable serializable, int position) {

    }
}
