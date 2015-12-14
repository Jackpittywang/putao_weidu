package com.sunnybear.library.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunnybear.library.R;

/**
 * 数量选择控件
 * Created by guchenkai on 2015/11/30.
 */
public class AmountSelectLayout extends RelativeLayout {
    private View mRootView;
    private Button mMinusBtn, mPlusBtn;
    private TextView tv_count;

    private int mCurrentCount = 1;//当前数量

    private int mMaxCount;
    private Drawable mBtnBackground;//按钮颜色

    public AmountSelectLayout(Context context) {
        this(context, null, 0);
    }

    public AmountSelectLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AmountSelectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_amount_select, this);
        mMinusBtn = (Button) mRootView.findViewById(R.id.btn_minus);
        mPlusBtn = (Button) mRootView.findViewById(R.id.btn_plus);
        tv_count = (TextView) mRootView.findViewById(R.id.tv_count);
        tv_count.setText(String.valueOf(mCurrentCount));

        mMinusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentCount > 1)
                    mCurrentCount--;
                tv_count.setText(String.valueOf(mCurrentCount));
            }
        });
        mPlusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentCount++;
                tv_count.setText(String.valueOf(mCurrentCount));
            }
        });
    }

    /**
     * 设置数量
     *
     * @param count
     */
    public void setCount(int count) {
        mCurrentCount = count;
        tv_count.setText(String.valueOf(count));
    }

    /**
     * 获取当前数量
     *
     * @return
     */
    public int getCurrentCount() {
        return mCurrentCount;
    }
}
