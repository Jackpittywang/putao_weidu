package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.MainActivity;
import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.home.adapter.ActionNewsAdapter;
import com.putao.wd.home.adapter.StartBannerAdapter;
import com.putao.wd.model.AcitonNewsList;
import com.putao.wd.model.ActionNews;
import com.putao.wd.model.Banner;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.putaozi.GrapestoneActivity;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 葡星圈
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoStartCircleFragment extends PTWDFragment implements TitleBar.OnTitleItemSelectedListener {
    private static final String STATUS_ONGOING = "ONGOING";//进行中
    //    private static final String STATUS_CLOSE = "CLOSE";//截止
    private static final String STATUS_CLOSE = "LOOKBACK";// 暂用LOOKBACK列表内容代替截止
    private static final String TYPE_ACTION = "TEXT";//活动
    private static final String TYPE_NEWS = "NEWS";//新闻

    @Bind(R.id.sticky_layout)
    StickyHeaderLayout sticky_layout;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_acition)
    LoadMoreRecyclerView rv_action;
    @Bind(R.id.bl_banner)
    BannerLayout bl_banner;
    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar ll_title;
    @Bind(R.id.rl_no_content)
    RelativeLayout rl_no_content;//没有内容时的空页面

    private ActionNewsAdapter adapter;
    private boolean isStop;//广告栏是否被停止

    private int currentPage = 1;//当前页码

    private String currentStatus = "";//当前的状态
    private String currentType = "";//当前的类型

    private int pageCount = 10;//分页的每页条目数

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_start_circle;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        Logger.d(MainActivity.TAG, "PutaoStartCircleFragment启动");
        addNavigation();
        setMainTitleColor(Color.WHITE);
        sticky_layout.canScrollView();
        adapter = new ActionNewsAdapter(mActivity, null);
        rv_action.setAdapter(adapter);
        addListener();

        getBannerList();//获取广告列表
        getActionList();//获取活动列表
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
                        bl_banner.setAdapter(new StartBannerAdapter(mActivity, result, new BannerViewPager.OnPagerClickListenr() {
                            @Override
                            public void onPagerClick(int position) {
                                ToastUtils.showToastLong(mActivity, "点击第" + position + "项");
                            }
                        }));
                        bl_banner.setOffscreenPageLimit(result.size());//缓存页面数
//                        loading.dismiss();
                    }
                });
    }

    /**
     * 获取活动列表
     */
    private void getActionList() {
        networkRequest(StartApi.getActionList(String.valueOf(currentPage), currentStatus, currentType)
                , new SimpleFastJsonCallback<AcitonNewsList>(AcitonNewsList.class, loading) {
                    @Override
                    public void onSuccess(String url, AcitonNewsList result) {
                        List<ActionNews> actionNewses = result.getGetEventList();
                        if (actionNewses != null && actionNewses.size() > 0) {
                            rl_no_content.setVisibility(View.GONE);
                            adapter.replaceAll(result.getGetEventList());
                        } else
                            rl_no_content.setVisibility(View.VISIBLE);
                        if (result.getCurrent_page() != result.getTotal_page() || result.getTotal_page() != 0) {
                            rv_action.loadMoreComplete();
                            currentPage++;
                        } else rv_action.noMoreLoading();
                        loading.dismiss();
                    }
                });
    }

    /**
     * 刷新活动列表
     */
    private void refreshActionList() {
        currentPage = 1;
        rv_action.reset();
        networkRequest(StartApi.getActionList(String.valueOf(currentPage), currentStatus, currentType)
                , new SimpleFastJsonCallback<AcitonNewsList>(AcitonNewsList.class, loading) {
                    @Override
                    public void onSuccess(String url, AcitonNewsList result) {
                        if (result.getCurrent_page() != result.getTotal_page() && result.getTotal_page() != 0) {
                            rl_no_content.setVisibility(View.GONE);
                            adapter.replaceAll(result.getGetEventList());
                            rv_action.loadMoreComplete();
                            currentPage++;
                        } else rv_action.noMoreLoading();
                        ptl_refresh.refreshComplete();
                        loading.dismiss();
                    }
                });
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshActionList();
            }
        });
        rv_action.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getActionList();
            }
        });
        rv_action.setOnItemClickListener(new OnItemClickListener<ActionNews>() {
            @Override
            public void onItemClick(ActionNews actionNewsList, int position) {
                Bundle bundle = new Bundle();
                ActionNews actionNews = adapter.getItem(position);
                bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, actionNews.getId());
                startActivity(ActionsDetailActivity.class, bundle);
            }
        });
        ll_title.setOnTitleItemSelectedListener(this);
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
        return new String[]{StartApi.URL_BANNER, StartApi.URL_ACTION_LIST};
    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        currentPage = 1;
        rv_action.reset();
        switch (item.getId()) {
            case R.id.ll_all://全部
                currentStatus = "";
                currentType = "";
                break;
            case R.id.ll_ing://进行中
                currentStatus = STATUS_ONGOING;
                currentType = TYPE_ACTION;
                break;
            case R.id.ll_finish://已结束
                currentStatus = STATUS_CLOSE;
                currentType = TYPE_ACTION;
                break;
            case R.id.ll_news://新闻
                currentStatus = "";
                currentType = TYPE_NEWS;
                break;
        }
        getActionList();
    }

    @Override
    public void onLeftAction() {
        startActivity(CaptureActivity.class);
    }

    @Override
    public void onRightAction() {
        startActivity(GrapestoneActivity.class);
    }
}
