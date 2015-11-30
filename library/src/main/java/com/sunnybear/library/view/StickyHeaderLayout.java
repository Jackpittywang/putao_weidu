package com.sunnybear.library.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.sunnybear.library.R;

import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;

/**
 * 粘性头部
 * Created by guchenkai on 2015/11/6.
 */
public class StickyHeaderLayout extends ScrollableLayout {
    private View mHeaderView;//头部
    private View mStickyView;//粘滞头部
    private View mScrollableView;//底部滚动视图

    private View mMinusView;

    private int maxScrollY;

    public StickyHeaderLayout(Context context) {
        super(context);
    }

    public StickyHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMinusView(View minusView) {
        mMinusView = minusView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeaderView = findViewById(R.id.stickyHeaderLayout_header);
        mStickyView = findViewById(R.id.stickyHeaderLayout_sticky);
        mScrollableView = findViewById(R.id.stickyHeaderLayout_scrollable);

        setDraggableView(mStickyView);
        setFriction(0.1f);

        setOnScrollChangedListener(new OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int y, int oldY, int maxY) {
                float stickyTranslationY = 0f;
                if (y >= maxY)
                    stickyTranslationY = y - maxY;
                mStickyView.setTranslationY(stickyTranslationY);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mMinusView != null)
            maxScrollY = mHeaderView.getMeasuredHeight() - mMinusView.getMeasuredHeight();
        else
            maxScrollY = mHeaderView.getMeasuredHeight();
        setMaxScrollY(maxScrollY);
    }

    public boolean canScrollVerticallyListView(ListView listView, int direction) {
        return listView != null && listView.canScrollVertically(direction);
    }

    public boolean canScrollVerticallyRecyclerView(RecyclerView recyclerView, int direction) {
        return recyclerView != null && recyclerView.canScrollVertically(direction);
    }

    public void canScrollView() {
        setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                return mScrollableView != null && mScrollableView.canScrollVertically(direction);
            }
        });
    }

    public View getStickyView() {
        return mStickyView;
    }
}
