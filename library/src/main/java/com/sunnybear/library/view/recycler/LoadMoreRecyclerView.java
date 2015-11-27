package com.sunnybear.library.view.recycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.sunnybear.library.util.Logger;

/**
 * 上拉加载更多RecyclerView
 * Created by guchenkai on 2015/11/25.
 */
public class LoadMoreRecyclerView extends BasicRecyclerView {
    private static final String TAG = LoadMoreRecyclerView.class.getSimpleName();
    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public LoadMoreRecyclerView(Context context) {
        this(context, null, 0);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动,即是否向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://当不滚动时
                        //获取最后一个完全显示的ItemPosition
                        int lastVisibleItem = manager.findLastVisibleItemPosition();
                        int totalItemCount = manager.getItemCount();
                        //判断是否滚动到底部
                        if (lastVisibleItem == totalItemCount - 1 && isSlidingToLast) {
                            Logger.d(TAG, "加载更多");
                            if (mOnLoadMoreListener != null)
                                mOnLoadMoreListener.onLoadMore();
                        }
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //dy用来判断纵向滑动
                if (dy > 0)
                    isSlidingToLast = true;
                else
                    isSlidingToLast = false;
            }
        });
    }


    /**
     * 加载更多回调
     */
    public interface OnLoadMoreListener {

        void onLoadMore();
    }
}
