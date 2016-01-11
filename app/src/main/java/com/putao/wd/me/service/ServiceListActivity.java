package com.putao.wd.me.service;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.MainActivity;
import com.putao.wd.R;
import com.putao.wd.api.OrderApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.service.adapter.ServiceListAdapter;
import com.putao.wd.model.Service;
import com.putao.wd.model.ServiceList;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;


import butterknife.Bind;


/**
 * 售后列表
 * Created by wangou on 15/12/7.
 */
public class ServiceListActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    @Bind(R.id.rv_service)
    LoadMoreRecyclerView rv_service;//售后列表
    @Bind(R.id.rl_no_service)
    RelativeLayout rl_no_service;//没有售后时的布局

    private ServiceListAdapter adapter;
    private String serviceId;
    private int page = 1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        adapter = new ServiceListAdapter(mContext, null);
        rv_service.setAdapter(adapter);

        addListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getServiceList();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 获取售后列表
     */
    private void getServiceList() {
        networkRequest(OrderApi.getServiceLists(String.valueOf(page)), new SimpleFastJsonCallback<Service>(Service.class, loading) {
            @Override
            public void onSuccess(String url, Service result) {
                Logger.w("售后 = " + result.toString());
                if (result.getCurrentPage() <= result.getTotalPage() && result.getTotalPage() != 0) {
                    adapter.addAll(result.getData());
                    rl_no_service.setVisibility(View.GONE);
                    rv_service.setVisibility(View.VISIBLE);
                    rv_service.loadMoreComplete();
                    page++;
                } else {
                    rv_service.noMoreLoading();
                }
                loading.dismiss();
            }
        });
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        rv_service.setOnItemClickListener(new OnItemClickListener<ServiceList>() {
            @Override
            public void onItemClick(ServiceList ServiceList, int position) {
                serviceId = ServiceList.getId();
                Bundle bundle = new Bundle();
                bundle.putString(ServiceDetailActivity.KEY_SERVICE_ID, serviceId);
                bundle.putString(ServiceDetailActivity.KEY_SERVICE_STATUS, ServiceList.getStatusText());
                startActivity(ServiceDetailActivity.class, bundle);
            }
        });
        rv_service.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getServiceList();
            }
        });
    }

    /**
     * 取消订单dialog
     */
    private void showDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("确定取消")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        networkRequest(OrderApi.cancelService(serviceId), new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                MainActivity.isNotRefreshUserInfo = false;
                                page--;
                                networkRequest(OrderApi.getServiceLists(String.valueOf(page)), new SimpleFastJsonCallback<Service>(Service.class, loading) {
                                    @Override
                                    public void onSuccess(String url, Service result) {
                                        Logger.w("售后 = " + result.toString());
                                        if (result.getCurrentPage() <= result.getTotalPage() && result.getTotalPage() != 0) {
                                            adapter.replaceAll(result.getData());
                                            rl_no_service.setVisibility(View.GONE);
                                            rv_service.setVisibility(View.VISIBLE);
                                            rv_service.loadMoreComplete();
                                            page++;
                                        } else {
                                            rv_service.noMoreLoading();
                                        }
                                        loading.dismiss();
                                        ToastUtils.showToastShort(mContext, "取消售后成功");
                                    }
                                });
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Subcriber(tag = ServiceListAdapter.EVENT_RIGHT_CLICK)
    public void eventClick(Bundle bundle) {
        String button_id = (String) bundle.get(ServiceListAdapter.BUTTON_ID);
        serviceId = (String) bundle.get(ServiceListAdapter.SERVICE_ID);
        switch (button_id) {
            case ServiceListAdapter.SERVICE_CANCEL:
                showDialog();
                break;
            case ServiceListAdapter.SERVICE_FILL_EXPRESS:
                bundle.putString(ServiceExpressNumberActivity.KEY_SERVICE_ID, serviceId);
                startActivity(ServiceExpressNumberActivity.class, bundle);
                break;
        }
    }

}
