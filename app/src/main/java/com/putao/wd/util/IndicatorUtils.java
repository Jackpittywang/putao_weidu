package com.putao.wd.util;

import android.content.Context;
import android.view.View;

import com.sunnybear.library.view.BadgeView;

/**
 * 指示工具
 * Created by guchenkai on 2015/12/28.
 */
public class IndicatorUtils {
    private static IndicatorUtils mInstance;
    private BadgeView mIndicator;

    public IndicatorUtils(Context context, View target) {
        mIndicator = new BadgeView(context, target);
    }

    public static IndicatorUtils getInstance(Context context, View target) {
        if (mInstance == null)
            mInstance = new IndicatorUtils(context, target);
        return mInstance;
    }

    /**
     * 显示指示
     *
     * @param count           数量
     * @param position        方位
     * @param backgroundResId 背景资源
     * @param textColor       字体颜色
     */
    public void indicator(int count, int position, int backgroundResId, int textColor) {
        mIndicator.setBadgePosition(position);
        mIndicator.setBackgroundResource(backgroundResId);
        mIndicator.setText(String.valueOf(count));
        mIndicator.setTextColor(textColor);
        mIndicator.show();
    }

    public void hide() {
        if (mIndicator != null && mIndicator.isShown())
            mIndicator.hide();
    }
}
