package com.sunnybear.library.view.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 标题指示器
 * Created by guchenkai on 2015/12/2.
 */
public class TitleBar extends LinearLayout implements View.OnClickListener {
    private TitleItemSelectedListener mTitleItemSelectedListener;
    private TitleItem mLastSelectedItem = null;

    private SparseIntArray array;

    public void setTitleItemSelectedListener(TitleItemSelectedListener titleItemSelectedListener) {
        mTitleItemSelectedListener = titleItemSelectedListener;
    }

    public TitleBar(Context context) {
        this(context, null, 0);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        array = new SparseIntArray();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            array.put(child.getId(), i);
            if (!(child instanceof TitleItem))
                throw new RuntimeException("TitleBar's child must be subclass of TitleItem!");
            if (((TitleItem) child).isActive())
                mLastSelectedItem = (TitleItem) child;
            child.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        selectTitleItem((TitleItem) v);
    }

    /**
     * 选择TabItem
     *
     * @param item 选中的TabItem
     */
    public void selectTitleItem(TitleItem item) {
        if (mLastSelectedItem == item) {
            mLastSelectedItem.setActive(true);
            return;
        } else {
            if (mLastSelectedItem != null)
                mLastSelectedItem.setActive(false);
            item.setActive(true);
            mLastSelectedItem = item;
        }
        int position = array.get(item.getId());
        if (mTitleItemSelectedListener != null)
            mTitleItemSelectedListener.onTitleItemSelected(item, position);
    }

    /**
     * TabItem选择监听器
     */
    public interface TitleItemSelectedListener {

        void onTitleItemSelected(TitleItem item, int position);
    }
}
