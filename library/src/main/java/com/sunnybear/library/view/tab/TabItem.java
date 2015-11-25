package com.sunnybear.library.view.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.sunnybear.library.R;
import com.sunnybear.library.view.BadgeView;

/**
 * 切换工具条上的按钮,配合TabBar使用
 * Created by guchenkai on 2015/11/24.
 */
public class TabItem extends View {
    private static final String TAG = TabItem.class.getSimpleName();
    private boolean mActive = false;//是否活动状态
    private Drawable mActiveDrawable;
    private Drawable mInActiveDrawable;

    private Drawable mCurrentDrawable;//当前Drawable
    private BadgeView mIndicatorView;
    private int mIndicatorBackgroundColorId;

    public TabItem(Context context) {
        this(context, null, 0);
    }

    public TabItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyleable(context, attrs);
        setActive(mActive);
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabItem);
        mIndicatorBackgroundColorId = array.getColor(R.styleable.TabItem_indicator_background_color, 0);
        mActive = array.getBoolean(R.styleable.TabItem_active, false);
        mActiveDrawable = array.getDrawable(R.styleable.TabItem_activeDrawable);
        mInActiveDrawable = array.getDrawable(R.styleable.TabItem_inactiveDrawable);
        array.recycle();
    }

    /**
     * 初始化指示点
     *
     * @param context
     */
    private void initIndicator(Context context) {
        mIndicatorView = new BadgeView(context, this);
        mIndicatorView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        mIndicatorView.setBadgeBackgroundColor(mIndicatorBackgroundColorId);
        mIndicatorView.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC));
    }

    /**
     * 显示指示点
     *
     * @param count 指示条数
     */
    public void show(int count) {
        initIndicator(getContext());
        if (count != 0)
            mIndicatorView.setText(String.valueOf(count));
        mIndicatorView.show();
    }

    /**
     * 显示指示点
     */
    public void show() {
        show(0);
    }

    /**
     * 隐藏指示点
     */
    public void hide() {
        if (mIndicatorView != null)
            mIndicatorView.hide();
    }

    public void setActive(boolean active) {
        mActive = active;
        mCurrentDrawable = active ? mActiveDrawable : mInActiveDrawable;
        postInvalidate();
    }

    public boolean getActive() {
        return mActive;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int drawableWidth = mCurrentDrawable.getIntrinsicWidth();
        final int drawableHeight = mCurrentDrawable.getIntrinsicHeight();
        final int measuredWidth = getMeasuredWidth();
        final int measuredHeight = getMeasuredHeight();

        canvas.save();
        canvas.translate(measuredWidth / 2 - drawableWidth / 2, measuredHeight / 2 - drawableHeight / 2);
        mCurrentDrawable.setBounds(0, 0, mCurrentDrawable.getIntrinsicWidth(), mCurrentDrawable.getIntrinsicHeight());
        mCurrentDrawable.draw(canvas);
        canvas.restore();
    }
}
