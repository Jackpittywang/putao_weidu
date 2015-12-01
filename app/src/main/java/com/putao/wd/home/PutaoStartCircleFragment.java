package com.putao.wd.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;
import com.sunnybear.library.view.recycler.RecyclerViewHeader;
import com.sunnybear.library.view.viewpager.AutoScrollPagerAdapter;
import com.sunnybear.library.view.viewpager.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 葡星圈
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoStartCircleFragment extends BasicFragment {
    private static final int[] resIds = new int[]{
            R.drawable.test_1, R.drawable.test_2, R.drawable.test_3, R.drawable.test_4, R.drawable.test_5, R.drawable.test_6, R.drawable.test_7
    };
    @Bind(R.id.rvh_header)
    RecyclerViewHeader mHeader;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.vp_banner)
    AutoScrollViewPager vp_banner;

    private TextAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_start_circle;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        mHeader.attachTo(rv_content, true);
        adapter = new TextAdapter(mActivity, getTestData());
        rv_content.setAdapter(adapter);

//        vp_banner.setAdapter(new PagerAdapter() {
//
//            @Override
//            public int getCount() {
//                return resIds.length;
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                return view == object;
//            }
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                ImageView imageView = new ImageView(mActivity);
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                imageView.setLayoutParams(params);
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                imageView.setImageResource(resIds[position]);
//                container.addView(imageView);
//                return imageView;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeView((View) object);
//            }
//        });
        vp_banner.setAdapter(new AutoScrollPagerAdapter() {
            @Override
            public int getItemCount() {
                return resIds.length;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup container) {
                ImageView imageView = new ImageView(mActivity);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(resIds[position]);
                container.addView(imageView);
                return imageView;
            }
        });
        vp_banner.setAutoScrollTime(2000);
        vp_banner.startAutoScroll();

        refresh();
        addListener();
    }

    /**
     * 刷新方法
     */
    private void refresh() {
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
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        rv_content.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(String serializable, int position) {
                ToastUtils.showToastShort(mActivity, "点击第" + position + "项");
                startActivity(LoginActivity.class);
            }
        });
    }

    private List<String> getTestData() {
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
