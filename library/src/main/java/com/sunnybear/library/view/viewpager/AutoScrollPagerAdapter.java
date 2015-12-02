package com.sunnybear.library.view.viewpager;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by guchenkai on 2015/12/1.
 */
public abstract class AutoScrollPagerAdapter extends RecyclingPagerAdapter {
    private OnClickItemListener mOnClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        return null;
    }

    @Override
    /**
     * Note: use getItemCount instead*/
    public final int getCount() {
        return getItemCount() * AutoScrollViewPager.FakePositionHelper.MULTIPLIER;
    }

    @Deprecated

    protected View getViewInternal(final int position, View convertView, ViewGroup container) {
        if (getItemCount() == 0)
            return null;
        final View view = getView(position % getItemCount(), convertView, container);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItemListener != null)
                    mOnClickItemListener.onClickItem(view, position % getItemCount());
            }
        });
        return view;
    }

    public abstract int getItemCount();

    /**
     * 点击item事件
     */
    public interface OnClickItemListener {

        void onClickItem(View view, int position);
    }
}
