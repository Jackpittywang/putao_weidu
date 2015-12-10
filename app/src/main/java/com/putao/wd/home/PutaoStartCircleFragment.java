package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.home.adapter.ActionNewsAdapter;
import com.putao.wd.home.adapter.StartBannerAdapater;
import com.putao.wd.model.ActionNews;
import com.putao.wd.model.Banner;
import com.putao.wd.start.comment.CommentActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;
import com.sunnybear.library.view.sticky.StickyHeaderLayout;
import com.sunnybear.library.view.viewpager.BannerLayout;
import com.sunnybear.library.view.viewpager.BannerViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 葡星圈
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoStartCircleFragment extends PTWDFragment implements TitleBar.TitleItemSelectedListener {
    @Bind(R.id.sticky_layout)
    StickyHeaderLayout sticky_layout;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.stickyHeaderLayout_scrollable)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.bl_banner)
    BannerLayout bl_banner;
    @Bind(R.id.ci_indicator)
    CirclePageIndicator ci_indicator;
    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar ll_title;

    private ActionNewsAdapter adapter;
    private boolean isStop;//广告栏是否被停止

    private int currentPage = 0;//当前页码

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_start_circle;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        setMainTitleColor(Color.WHITE);
        sticky_layout.canScrollView();
        adapter = new ActionNewsAdapter(mActivity, null);
        rv_content.setAdapter(adapter);
        refresh();
        addListener();

        getBannerList();
        getActionsList(currentPage, false, false);
    }

    /**
     * 获取广告列表
     */
    private void getBannerList() {
        networkRequestCache(StartApi.getBannerList()
                , new SimpleFastJsonCallback<ArrayList<Banner>>(Banner.class, loading) {
            @Override
            public void onSuccess(String url, final ArrayList<Banner> result) {
                cacheEnterDisk(url, result);
                bl_banner.setAdapter(new StartBannerAdapater(mActivity, result, new BannerViewPager.OnPagerClickListenr() {
                    @Override
                    public void onPagerClick(int position) {
                        ToastUtils.showToastLong(mActivity, "点击第" + position + "项");
                    }
                }));
                bl_banner.setOffscreenPageLimit(result.size());//缓存页面数
                loading.dismiss();
            }
        });
    }

    /**
     * 获得活动列表
     *
     * @param page       分页
     * @param isRefresh  是否刷新
     * @param isLoadMore 是否加载更多
     */
    private void getActionsList(int page, final boolean isRefresh, final boolean isLoadMore) {
        networkRequestCache(StartApi.getActionList(String.valueOf(page)),
                new SimpleFastJsonCallback<ArrayList<ActionNews>>(ActionNews.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<ActionNews> result) {
                        Logger.d(result.toString());
                        if (isRefresh) {
                            adapter.replaceAll(result);
                            ptl_refresh.refreshComplete();
                            currentPage = 0;
                        }
                        adapter.addAll(result);
                        currentPage++;
                        if (isLoadMore)
                            if (result.size() >= 10)
                                rv_content.loadMoreComplete();
                            else
                                rv_content.noMoreLoading();
                        loading.dismiss();
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
                getActionsList(0, true, false);
            }
        });
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getActionsList(currentPage, false, true);
            }
        });
        rv_content.setOnItemClickListener(new OnItemClickListener<ActionNews>() {
            @Override
            public void onItemClick(ActionNews actionNewsList, int position) {
                startActivity(CommentActivity.class);
            }
        });

        ll_title.setTitleItemSelectedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isStop)
            isStop = bl_banner.startAutoScroll();
    }

    @Override
    public void onStop() {
        super.onStop();
        isStop = bl_banner.stopAutoScroll();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[]{StartApi.URL_BANNER};
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
