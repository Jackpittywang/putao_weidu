package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.dto.ActionNewsItem;
import com.putao.wd.dto.Banner;
import com.putao.wd.home.adapter.ActionNewsAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;
import com.sunnybear.library.view.sticky.StickyHeaderLayout;
import com.sunnybear.library.view.viewpager.AutoScrollPagerAdapter;
import com.sunnybear.library.view.viewpager.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 葡星圈
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoStartCircleFragment extends PTWDFragment implements TitleBar.TitleItemSelectedListener {
    private static final int[] resIds = new int[]{
            R.drawable.test_1, R.drawable.test_2, R.drawable.test_3, R.drawable.test_4, R.drawable.test_5, R.drawable.test_6, R.drawable.test_7
    };
    @Bind(R.id.sticky_layout)
    StickyHeaderLayout sticky_layout;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.stickyHeaderLayout_scrollable)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.vp_banner)
    AutoScrollViewPager vp_banner;
    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar ll_title;

    private ActionNewsAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_start_circle;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        setMainTitleColor(Color.WHITE);
        sticky_layout.canScrollView();
        adapter = new ActionNewsAdapter(mActivity, getTestData());
        rv_content.setAdapter(adapter);

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
        refresh();
        addListener();

//        getBannerList();
    }

    /**
     * 获取广告列表
     */
    private void getBannerList() {
        networkRequestCache(StartApi.getBannerList()
                , new SimpleFastJsonCallback<ArrayList<Banner>>(Banner.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<Banner> result) {
                loading.dismiss();
                Logger.d(result.toString());
            }
        });
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
        vp_banner.setOnClickItemListener(new AutoScrollPagerAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(View view, int position) {
                ToastUtils.showToastLong(mActivity, "点击第" + position + "项");
            }
        });
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                adapter.addAll(getTestData());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rv_content.noMoreLoading();
                    }
                }, 3 * 1000);
            }
        });
        ll_title.setTitleItemSelectedListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        vp_banner.stopAutoScroll();
    }

    private List<ActionNewsItem> getTestData() {
        List<ActionNewsItem> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ActionNewsItem actionNewsItem = new ActionNewsItem();
            actionNewsItem.setId((i + 1) + "");
            actionNewsItem.setTitle("这是第" + (i + 1) + "个标题");
            actionNewsItem.setIntro("这是第" + (i + 1) + "个简介");
            actionNewsItem.setIsAction(i % 2 == 0);
            actionNewsItem.setType(i % 2 == 0 ? "活动" : "新闻");
            actionNewsItem.setAddress("上海.莘庄.凯德龙之梦");
            actionNewsItem.setTime("2015.12.02");
            actionNewsItem.setJoinCount("100000");
            actionNewsItem.setImgUrl("http://i1.mopimg.cn/img/tt/2015-11/551/20151124175324870.jpg790x600.jpg");
            list.add(actionNewsItem);
        }
        return list;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        switch (item.getId()) {
            case R.id.ll_all://全部
                ToastUtils.showToastLong(mActivity, "全部");
                break;
            case R.id.ll_ing://进行中
                ToastUtils.showToastLong(mActivity, "进行中");
                break;
            case R.id.ll_finish://已结束
                ToastUtils.showToastLong(mActivity, "已结束");
                break;
            case R.id.ll_news://新闻
                ToastUtils.showToastLong(mActivity, "新闻");
                break;
        }
    }
}
