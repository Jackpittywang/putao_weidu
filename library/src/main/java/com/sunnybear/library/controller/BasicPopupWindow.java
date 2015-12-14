package com.sunnybear.library.controller;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.sunnybear.library.R;
import com.sunnybear.library.view.LoadingHUD;

import butterknife.ButterKnife;

/**
 * 基础PopupWindow
 * Created by guchenkai on 2015/11/29.
 */
public abstract class BasicPopupWindow extends PopupWindow implements View.OnTouchListener, View.OnKeyListener {
    protected Context mContext;
    private View mRootView;
    private ViewGroup mMainLayout;

    protected BasicFragmentActivity mActivity;
    protected LoadingHUD loading;

    /**
     * 设置布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    public BasicPopupWindow(Context context) {
        super(context);
        mContext = context;
        mActivity = (BasicFragmentActivity) context;
        this.loading = mActivity.loading;
        int layoutId = getLayoutId();
        if (layoutId == 0)
            throw new RuntimeException("找不到Layout资源,Fragment初始化失败!");
        mRootView = LayoutInflater.from(context).inflate(getLayoutId(), null);
        setContentView(mRootView);//设置布局
        ButterKnife.bind(this, mRootView);

        mMainLayout = (ViewGroup) mRootView.findViewById(R.id.popup_layout);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);//设置宽
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//设置高
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(dw);//设置背景
        setFocusable(true);
        setOutsideTouchable(true);
        //点击PopupWindow之外的区域关闭PopupWindow
//            mRootView.setOnTouchListener(this);
        //响应返回键
        mRootView.setOnKeyListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int height = mMainLayout.getHeight();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP && y < height)
            dismiss();
        return true;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK)
            dismiss();
        return false;
    }

    @Override
    public final void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
    }

    /**
     * 设置layout在PopupWindow中显示的位置
     *
     * @param target 目标view
     */
    public void show(View target) {
        showAtLocation(target, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
