package com.sunnybear.library.util;

/**
 * 控件工具
 * Created by guchenkai on 2015/11/29.
 */
public class WidgetUtils {
    private static long lastClickTime = 0;//最后一次点击时间

    /**
     * 是否连续点击
     *
     * @return 是否连续点击
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000)
            return true;
        lastClickTime = time;
        return false;
    }
}
