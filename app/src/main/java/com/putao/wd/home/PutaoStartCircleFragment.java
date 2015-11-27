package com.putao.wd.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.RecyclerViewHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 葡星圈
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoStartCircleFragment extends BasicFragment {
    @Bind(R.id.rvh_header)
    RecyclerViewHeader mHeader;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.tv_header)
    TextView tv_header;

    private TextAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_start_circle;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        mHeader.attachTo(rv_content, true);
        adapter = new TextAdapter(mActivity, getData());
        rv_content.setAdapter(adapter);
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptl_refresh.refreshComplete();
                    }
                }, 3 * 1000);
            }
        });

        tv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToastLong(mActivity, "点击头部");
            }
        });
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add("第" + (i + 1) + "项");
        }
        return list;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     *
     */
    static class TextAdapter extends BasicAdapter<String, TextAdapter.TextViewHolder> {

        public TextAdapter(Context context, List<String> strings) {
            super(context, strings);
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.fragment_start_circle_item;
        }

        @Override
        public TextViewHolder getViewHolder(View itemView, int viewType) {
            return new TextViewHolder(itemView);
        }

        @Override
        public void onBindItem(TextViewHolder holder, String s, int position) {
            holder.tv_content.setText(s);
        }

        /**
         *
         */
        static class TextViewHolder extends BasicViewHolder {
            @Bind(R.id.tv_content)
            TextView tv_content;

            public TextViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
