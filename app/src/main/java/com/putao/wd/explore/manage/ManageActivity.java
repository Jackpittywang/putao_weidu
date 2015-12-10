package com.putao.wd.explore.manage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.BasicFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索号-管理
 * Created by wangou on 2015/12/2.
 */
public class ManageActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.btn_stopuse)
    Button btn_stopuse;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manage;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.btn_stopuse, R.id.ll_equipment, R.id.ll_product, R.id.ll_usecount, R.id.usetime})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stopuse://停止使用
                new AlertDialog.Builder(mContext).setTitle("").setMessage("让宝宝停止使用葡萄产品?")
                        .setCancelable(false)
                        .setPositiveButton("停止", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("再玩玩", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
                break;
            case R.id.ll_equipment://受控设备

                break;
            case R.id.ll_product://受控产品

                break;
            case R.id.ll_usecount://使用次数
                addFragment(UseCountEveryDayFragment.class);
                break;
            case R.id.usetime://使用时间

                break;
        }
    }

    /**
     * 获取管理数据
     * by yanghx
     */
    private void getManagementList(){
        networkRequest(ExploreApi.getManagement(), new SimpleFastJsonCallback<ArrayList<Management>>(Management.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Management> result) {
                Log.i("pt", "探索号管理查询请求成功");
            }
        });
    }

    /**
     * 保存管理数据
     * by yanghx
     */
    private void sendManagementList(String edit_list){
        networkRequest(ExploreApi.managementEdit(edit_list), new SimpleFastJsonCallback<ArrayList<ManagementEdit>>(ManagementEdit.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<ManagementEdit> result) {
                Log.i("pt", "探索号管理查询请求成功");
            }
        });
    }


}