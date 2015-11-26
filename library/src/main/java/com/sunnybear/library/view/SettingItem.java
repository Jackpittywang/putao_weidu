package com.sunnybear.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunnybear.library.R;

/**
 * 设置条目按钮
 * Created by guchenkai on 2015/11/26.
 */
public class SettingItem extends RelativeLayout {
    private static final String TAG = SettingItem.class.getSimpleName();
    private static final int ENUM_TOP_BOTTOM = 0;
    private static final int ENUM_TOP = 1;
    private static final int ENUM_BOTTOM = 2;
    private final Drawable mSettingIcon;
    private final String mSettingText;
    private final View mRootView;
    private final int mSettingDivider;
    private final ImageView mSettingIndicator;
    private final ImageView mSettingIndicator2;

    public SettingItem(Context context) {
        this(context, null);
    }

    public SettingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SettingItem);
        mSettingIcon = typedArray.getDrawable(R.styleable.SettingItem_setting_icon);
        mSettingText = typedArray.getString(R.styleable.SettingItem_setting_text);
        mSettingDivider = typedArray.getInt(R.styleable.SettingItem_setting_divider, 0);
        typedArray.recycle();

        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_setting_item, this);
        ImageView settingIconView = (ImageView) mRootView.findViewById(R.id.setting_icon);
        mSettingIndicator = (ImageView) mRootView.findViewById(R.id.setting_indicator);
        mSettingIndicator2 = (ImageView) mRootView.findViewById(R.id.setting_indicator_2);
        TextView settingTextView = (TextView) mRootView.findViewById(R.id.setting_text);
        View dividerTop = mRootView.findViewById(R.id.divider_top);
        View dividerBottom = mRootView.findViewById(R.id.divider_bottom);
        settingIconView.setImageDrawable(mSettingIcon);
        settingTextView.setText(mSettingText);
        mSettingIndicator.setVisibility(GONE);
        mSettingIndicator2.setVisibility(GONE);
        if (mSettingIcon == null) {
            settingIconView.setVisibility(GONE);
        }

        switch (mSettingDivider) {
            case ENUM_TOP_BOTTOM:
                dividerTop.setVisibility(VISIBLE);
                dividerBottom.setVisibility(VISIBLE);
                break;
            case ENUM_TOP:
                dividerTop.setVisibility(VISIBLE);
                break;
            case ENUM_BOTTOM:
                dividerBottom.setVisibility(VISIBLE);
                break;
        }
    }
}
