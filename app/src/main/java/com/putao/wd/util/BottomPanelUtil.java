package com.putao.wd.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.util.DensityUtil;


public class BottomPanelUtil {
    private static Activity activity;
    private static ViewGroup bottomSelectPanelView;
    private static PopupWindow bottomPopupWindow;

    private static boolean isBottomShow = false;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                if (bottomSelectPanelView != null && isBottomShow) {
                    ((ViewGroup) activity.getWindow().getDecorView())
                            .removeView(bottomSelectPanelView);
                    isBottomShow = false;
                }
            }
        }

    };

    /**
     * 显示底部功能面板
     *
     * @param context
     * @param functionNames
     * @param callbacks
     */
    public static void showBottomFunctionPanel(Context context,
                                               String[] functionNames,
                                               FunctionPanelCallBack[] callbacks,
                                               final FunctionPanelCallBack cancleCallback) {
        if (isBottomShow) {
            return;
        }
        if (functionNames == null || callbacks == null) {
            return;
        }
        if (functionNames.length != callbacks.length) {
            return;
        }
        activity = (Activity) context;

        bottomSelectPanelView = (ViewGroup) LayoutInflater.from(context)
                .inflate(R.layout.bottom_panel_layout, null);

        final LinearLayout functionMain = (LinearLayout) bottomSelectPanelView
                .findViewById(R.id.function_main);
        LinearLayout containLayout = (LinearLayout) bottomSelectPanelView
                .findViewById(R.id.function_contain);
        TextView cancle = (TextView) bottomSelectPanelView
                .findViewById(R.id.function_cancle);
        TextView nullView = (TextView) bottomSelectPanelView
                .findViewById(R.id.function_null);
        nullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (bottomPopupWindow != null && bottomPopupWindow.isShowing()) {
                    bottomPopupWindow.dismiss();
                }
                handler.sendEmptyMessage(100);
                if (cancleCallback != null) cancleCallback.doFunction();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (bottomPopupWindow != null && bottomPopupWindow.isShowing()) {
                    bottomPopupWindow.dismiss();
                }
                handler.sendEmptyMessage(100);
                if (cancleCallback != null) cancleCallback.doFunction();
            }
        });
        for (int i = 0; i < functionNames.length; i++) {
            TextView itemView = new TextView(context);
            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) DensityUtil.dp2px(context, 40));
            itemView.setLayoutParams(itemParams);
            itemView.setText(functionNames[i]);
            itemView.setGravity(Gravity.CENTER);
            itemView.setTextSize(16);
            itemView.setTextColor(Color.parseColor("#6666CC"));
            final FunctionPanelCallBack callBack = callbacks[i];
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    callBack.doFunction();
                    hideBottomFunctionPanel(activity);
                }
            });
            containLayout.addView(itemView);

            if (i < functionNames.length - 1) {
                TextView lineView = new TextView(context);
                LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) DensityUtil.dp2px(context, 1));
                lineView.setLayoutParams(lineParams);
                lineView.setBackgroundColor(Color.parseColor("#E7E7E7"));
                containLayout.addView(lineView);
            }

        }

        int width = DensityUtil.dp2px(context, 56) + functionNames.length
                * DensityUtil.dp2px(context, 40) + (functionNames.length - 1)
                * DensityUtil.dp2px(context, 1);
        bottomSelectPanelView.removeView(functionMain);
        bottomPopupWindow = new PopupWindow(functionMain,
                DensityUtil.getDeviceWidth(context)
                        - (int) DensityUtil.dp2px(context, 16), width);
        //解决被华为手机虚拟按键遮挡
        bottomPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomPopupWindow.setAnimationStyle(R.style.bottom_anim_style);
        bottomPopupWindow.setBackgroundDrawable(new ColorDrawable());
        bottomPopupWindow.setFocusable(true);
        bottomPopupWindow.setOutsideTouchable(false);
        bottomPopupWindow.update();
        bottomPopupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                handler.sendEmptyMessage(100);
            }
        });

        ((ViewGroup) activity.getWindow().getDecorView())
                .addView(bottomSelectPanelView);
        isBottomShow = true;
        if (bottomPopupWindow != null) {
            bottomPopupWindow.showAtLocation(bottomSelectPanelView,
                    Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 隐藏底部功能选择面板
     *
     * @param activity
     */
    public static void hideBottomFunctionPanel(Activity activity) {
        if (bottomPopupWindow != null && bottomPopupWindow.isShowing()) {
            bottomPopupWindow.dismiss();
        }
        if (bottomSelectPanelView != null && isBottomShow) {
            ((ViewGroup) activity.getWindow().getDecorView())
                    .removeView(bottomSelectPanelView);
            isBottomShow = false;
        }
    }

    /**
     * 每一个选项点击的回调接口
     *
     * @author horizon
     */
    public interface FunctionPanelCallBack {
        void doFunction();
    }
}
