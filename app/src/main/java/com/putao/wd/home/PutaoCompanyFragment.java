package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.putao.wd.MainActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.explore.SmartActivity;
import com.putao.wd.home.adapter.ProductsAdapter;
import com.putao.wd.model.Management;
import com.putao.wd.model.ManagementProduct;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;

/**
 * 陪伴
 * Created by guchenkai on 2015/11/25.
 */
@Deprecated
public class PutaoCompanyFragment extends BasicFragment implements View.OnClickListener, OnItemClickListener {

//    @Bind(R.id.tv_title_bar_left)
//    TextView tv_title_bar_left;
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
    @Bind(R.id.rv_products)
    BasicRecyclerView rv_products;

    ProductsAdapter mProductsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_company;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addListener();
    }

    private void addListener() {
//        tv_title_bar_left.setOnClickListener(this);
        iv_title_bar_right1.setOnClickListener(this);
        iv_title_bar_right2.setOnClickListener(this);
        btn_explore_empty.setOnClickListener(this);
        rv_products.setOnItemClickListener(this);
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
//                                tv_title_bar_left.setTextColor(Color.WHITE);
//                                tv_title_bar_left.setEnabled(true);
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
                    for (ManagementProduct product : result.getProduct_list()) {
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
        ll_empty.setVisibility(View.VISIBLE);
        ll_no_empty.setVisibility(View.GONE);
//        tv_title_bar_left.setEnabled(false);
//        tv_title_bar_left.setTextColor(getResources().getColor(R.color.text_color_gray));
    }


    @Override
    public void onClick(View v) {
        if (!AccountHelper.isLogin()) {
            Bundle bundle = new Bundle();
            toLoginActivity(v, bundle);
            bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, MainActivity.class);
            startActivity(LoginActivity.class, bundle);
            return;
        }
        switch (v.getId()) {
//            case R.id.tv_title_bar_left:
//                startActivity(ManageActivity.class);
//                break;
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
//            case R.id.tv_title_bar_left:
//                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, MainActivity.class);
//                break;
            case R.id.iv_title_bar_right1:
                bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, MainActivity.class);
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
//        if (!MainActivity.isNotRefreshUserInfo && AccountHelper.isLogin()) {
//            addListener();
//            checkDevices();
//            ArrayList<String> icons = new ArrayList<>();
//            mProductsAdapter = new ProductsAdapter(mActivity, icons);
//            rv_products.setAdapter(mProductsAdapter);
//        }
    }

    @Override
    public void onItemClick(Serializable serializable, int position) {

    }
}
