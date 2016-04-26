package com.sunnybear.library.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Android Toast 封装
 * Created by guchenkai on 2015/11/6.
 */
public final class ToastUtils {

    private static Toast mToast;

    public static void showToastLong(Context context, String msg) {
        showNoRepeatToast(context, msg, Toast.LENGTH_LONG);
        //      showToast(context, msg, Toast.LENGTH_LONG);
    }

    public static void showToastShort(Context context, String msg) {
//        showToast(context, msg, Toast.LENGTH_SHORT);
        showNoRepeatToast(context, msg);
    }

    public static void showToastShort(Context context, int strRes) {
        showToast(context, context.getString(strRes), Toast.LENGTH_SHORT);
    }

    public static void showToastLong(Context context, int strRes) {
        showToast(context, context.getString(strRes), Toast.LENGTH_LONG);
    }

    public static void showToast(Context context, String msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }

    public static void showNoRepeatToast(Context context, String msg) {

        showNoRepeatToast(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showNoRepeatToast(Context context, String msg, int duration) {

        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), msg, duration);
        }
        mToast.setText(msg);
        mToast.show();
    }


}
