//package com.putao.wd.pt_companion.view;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.internal.widget.ViewUtils;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.putao.wd.R;
//import com.sunnybear.library.util.DensityUtil;
//import com.sunnybear.library.view.PullToRefreshLayout;
//import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
//
///**
// * Created by Administrator on 2016/4/12.
// */
//
//public class SecondCommentRecycleView extends PullToRefreshLayout {
//
//    private int mRefreshLayoutHeight;
//
//
//    private View header;
//    private int headerHeight;
//    public SecondCommentRecycleView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        addHeader();
//    }
//
//    private void addHeader() {
//        header = View.inflate(getContext(), R.layout.activity_article_detail, null);
//        ViewUtils.inject(this, header);
//        // 隐藏头布局,此时控件还没有显示在界面，必须要测量才能获取高度
//        header.measure(0, 0);
//        headerHeight = header.getMeasuredHeight();
//        header.setPadding(0, -headerHeight, 0, 0);
//
//        addHeaderView(header);
//
//    }
//
//    /**
//     * 初始化布局
//     *
//     * @param context context
//     */
//    private void initView(Context context) {
//        View parentView = LayoutInflater.from(context).inflate(mRefreshLayoutId, null);
//        parentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
//                , DensityUtil.dp2px(this.getContext(), mRefreshLayoutHeight)));
//
//        setHeaderView(parentView);
//        setPtrHandler(this);
//        addPtrUIHandler(this);
//    }
//}
