package com.sunnybear.library.view;

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
import com.sunnybear.library.util.DensityUtil;

import butterknife.ButterKnife;

/**
 * 基础PopupWindow
 * Created by guchenkai on 2015/11/29.
 */
public abstract class BasicPopupWindow extends PopupWindow implements View.OnTouchListener, View.OnKeyListener {
    private View mRootView;
    private ViewGroup mMainLayout;

    /**
     * 设置布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    public BasicPopupWindow(Context context) {
        this(context, true, false);
    }

    public BasicPopupWindow(Context context, boolean isOpenSelfAdaptionHeight) {
        this(context, true, isOpenSelfAdaptionHeight);
    }

    public BasicPopupWindow(Context context, boolean isClickOtherClosePopupWindow, boolean isOpenSelfAdaptionHeight) {
        super(context);
        int layoutId = getLayoutId();
        if (layoutId == 0)
            throw new RuntimeException("找不到Layout资源,Fragment初始化失败!");
        mRootView = LayoutInflater.from(context).inflate(getLayoutId(), null);
        setContentView(mRootView);//设置布局
        ButterKnife.bind(this, mRootView);

        mMainLayout = (ViewGroup) mRootView.findViewById(R.id.popup_layout);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);//设置宽
        if (!isOpenSelfAdaptionHeight)
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//设置高
        else
            setHeight(DensityUtil.getScreenH(context) * 2 / 3);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(dw);//设置背景
        if (isClickOtherClosePopupWindow)
            //点击PopupWindow之外的区域关闭PopupWindow
            mRootView.setOnTouchListener(this);
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
