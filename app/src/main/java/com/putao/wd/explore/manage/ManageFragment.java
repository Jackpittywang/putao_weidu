package com.putao.wd.explore.manage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索号-管理
 * Created by wangou on 2015/12/2.
 */
public class ManageFragment extends BasicFragment implements View.OnClickListener {
    @Bind(R.id.btn_stopuse)
    Button btn_stopuse;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manage;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
    @OnClick({R.id.btn_stopuse})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_stopuse://停止使用
                new AlertDialog.Builder(mActivity).setTitle("").setMessage("让宝宝停止使用葡萄产品?")
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
        }
    }
}