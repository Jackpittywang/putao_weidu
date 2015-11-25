package com.sunnybear.library.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * dp与px转换工具
 * Created by guchenkai on 2015/11/10.
 */
public final class DensityUtil {

    private DensityUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context context
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context context
     * @param pxValue px值
     * @return dp值
     */
    public static float px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return pxValue / scale;
    }

    /**
     * px转sp
     *
     * @param context context
     * @param pxValue px值
     * @return dp值
     */
    public static float px2sp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return pxValue / scale;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenW(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenH(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}
