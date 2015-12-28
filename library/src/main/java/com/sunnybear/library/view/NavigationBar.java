package com.sunnybear.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunnybear.library.R;

/**
 * 标题栏
 * Created by guchenkai on 2015/11/3.
 */
public class NavigationBar extends RelativeLayout {
    private String mMainTitle;
    private String mLeftTitle;
    private String mRightTitle;

    private int mMainTitleColor;
    private int mLeftTitleColor;
    private int mRightTitleColor;

    private int mMainTitleSize;
    private int mLeftTitleSize;
    private int mRightTitleSize;

    private boolean isMainAction = true;
    private boolean isLeftAction = true;
    private boolean isRightAction = true;

    private Drawable mLeftDrawable;
    private Drawable mRightDrawable;

    private Drawable mMainDrawable;

    private View mLeftView;
    private View mRightView;
    private TextView mMainView;//TODO: make it support custom main view

    private int mDividerColor = -1;
    private ActionsListener mListener;
    private boolean mHasDivider = true;

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initNavigationBar(context, attrs);
    }

    public NavigationBar(Context context) {
        this(context, null);
    }

    private void initNavigationBar(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
        mMainTitle = array.getString(R.styleable.NavigationBar_nav_main_title);
        mLeftTitle = array.getString(R.styleable.NavigationBar_nav_left_title);
        mRightTitle = array.getString(R.styleable.NavigationBar_nav_right_title);
        mMainTitleColor = array.getColor(R.styleable.NavigationBar_nav_main_title_color, -1);
        mLeftTitleColor = array.getColor(R.styleable.NavigationBar_nav_left_title_color, -1);
        mRightTitleColor = array.getColor(R.styleable.NavigationBar_nav_right_title_color, -1);
        mMainTitleSize = array.getDimensionPixelSize(R.styleable.NavigationBar_nav_main_title_size, -1);
        mLeftTitleSize = array.getDimensionPixelSize(R.styleable.NavigationBar_nav_left_title_size, -1);
        mRightTitleSize = array.getDimensionPixelSize(R.styleable.NavigationBar_nav_right_title_size, -1);
        mLeftDrawable = array.getDrawable(R.styleable.NavigationBar_nav_left_icon);
        mRightDrawable = array.getDrawable(R.styleable.NavigationBar_nav_right_icon);
        mDividerColor = array.getColor(R.styleable.NavigationBar_nav_divider_color, -1);
        mHasDivider = array.getBoolean(R.styleable.NavigationBar_nav_has_divider, true);

        array.recycle();


        //TODO://use custom view to do this to improve performance
        //setTitles
        View v = LayoutInflater.from(getContext()).inflate(R.layout.widget_navigation_bar, this);
        mMainView = (TextView) v.findViewById(R.id.main_title);
        TextView leftTitle = (TextView) v.findViewById(R.id.left_title);
        TextView rightTitle = (TextView) v.findViewById(R.id.right_title);

        mLeftView = leftTitle;
        mRightView = rightTitle;

        setMainTitle(mMainTitle);
        setLeftTitle(mLeftTitle);
        setRightTitle(mRightTitle);

        setDrawableLeft(leftTitle, mLeftDrawable);
        setDrawableLeft(rightTitle, mRightDrawable);

        //setDivider
        if (mDividerColor != -1)
            findViewById(R.id.divider).setBackgroundColor(mDividerColor);
        findViewById(R.id.divider).setVisibility(mHasDivider ? VISIBLE : GONE);
        //setId
        if (getId() == View.NO_ID)
            setId(R.id.navigation_bar);
    }

    public void setMainTitle(String title) {
        mMainTitle = title;
        mMainView.setText(mMainTitle);
        if (mMainTitleColor != -1)
            mMainView.setTextColor(mMainTitleColor);
        if (mMainTitleSize != -1)
            mMainView.setTextSize(mMainTitleSize);
    }

    public void setMainTitleColor(int colorId) {
        mMainView.setTextColor(colorId);
    }

    public void setLeftTitle(String title) {
        mLeftTitle = title;
        if (mLeftView instanceof TextView) {
            ((TextView) mLeftView).setText(title);
            if (mLeftTitleColor != -1)
                ((TextView) mLeftView).setTextColor(mLeftTitleColor);
            if (mLeftTitleSize != -1)
                ((TextView) mLeftView).setTextSize(mLeftTitleSize);
        }
    }

    public void setLeftTitleColor(int colorId) {
        ((TextView) mLeftView).setTextColor(colorId);
    }

    public void setLeftClickable(boolean isClick) {
        mLeftView.setClickable(isClick);
    }

    public void setRightTitle(String title) {
        mRightTitle = title;
        if (mRightView instanceof TextView) {
            ((TextView) mRightView).setText(title);
            if (mRightTitleColor != -1)
                ((TextView) mRightView).setTextColor(mRightTitleColor);
            if (mRightTitleSize != -1)
                ((TextView) mRightView).setTextSize(mRightTitleSize);
        }
    }

    public void setRightTitleColor(int colorId) {
        ((TextView) mRightView).setTextColor(colorId);
    }

    public void setRightClickable(boolean isClick) {
        mRightView.setClickable(isClick);
    }

    public void setRightView(View view) {
        ViewGroup.LayoutParams params = mRightView.getLayoutParams();
        view.setLayoutParams(params);
        removeView(mRightView);
        addView(view);
        mRightView = view;
    }

    public void setMainAction(boolean mainAction) {
        isMainAction = mainAction;
    }

    public void setLeftAction(boolean leftAction) {
        isLeftAction = leftAction;
    }

    public void setRightAction(boolean rightAction) {
        isRightAction = rightAction;
    }

    /**
     * convenience method for set left drawable
     */
    private void setDrawableLeft(TextView textView, Drawable drawable) {
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * getLeftView
     */
    public View getLeftView() {
        return mLeftView;
    }


    /**
     * getRightView
     */
    public View getRightView() {
        return mRightView;
    }

    /**
     * getMainView
     */
    public View getMainView() {
        return mMainView;
    }

    /**
     * setActionListener
     * TODO:is it necessary to give three methods for each?
     */
    public void setActionListener(ActionsListener listener) {
        mListener = listener;
        for (View v : new View[]{mMainView, mLeftView, mRightView}) {
            v.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (v == mMainView && isMainAction) {
                        mListener.onMainAction();
                    } else if (v == mLeftView && isLeftAction) {
                        mListener.onLeftAction();
                    } else if (v == mRightView && isRightAction) {
                        mListener.onRightAction();
                    }
                }
            });
        }
    }

    @Override
    protected void onFinishInflate() {
        View background = findViewById(R.id.nav_background);
        if (background != null) {
            removeView(background);
            addView(background, 0);
        }
        super.onFinishInflate();
    }

    /**
     * Listener for click event on NavigationBar
     */
    public static interface ActionsListener {
        public void onLeftAction();

        public void onRightAction();

        public void onMainAction();
    }

    public static class SimpleNavigationListener implements ActionsListener {

        @Override
        public void onLeftAction() {

        }

        @Override
        public void onRightAction() {

        }

        @Override
        public void onMainAction() {

        }
    }
}
