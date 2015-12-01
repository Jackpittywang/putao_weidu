package com.putao.wd.base;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicPopupWindow;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 选择弹框
 * Created by guchenkai on 2015/12/1.
 */
public abstract class SelectPopupWindow extends BasicPopupWindow implements View.OnClickListener {
    @Bind(R.id.tv_first)
    TextView tv_first;
    @Bind(R.id.tv_second)
    TextView tv_second;

    public SelectPopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_select;
    }

    @OnClick({R.id.ll_first, R.id.ll_second, R.id.ll_cancel})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_first:
                onFirstClick(v);
                break;
            case R.id.ll_second:
                onSecondClick(v);
                break;
            case R.id.ll_cancel:
                dismiss();
                break;
        }
    }

    /**
     * 点击第一行
     *
     * @param v view
     */
    public abstract void onFirstClick(View v);

    /**
     * 点击第二行
     *
     * @param v view
     */
    public abstract void onSecondClick(View v);
}
