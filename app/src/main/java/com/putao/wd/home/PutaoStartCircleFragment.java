package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;

import com.putao.wd.MainActivity;
import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.home.adapter.ActionNewsAdapter;
import com.putao.wd.home.adapter.StartBannerAdapter;
import com.putao.wd.model.ActionNews;
import com.putao.wd.model.Banner;
import com.putao.wd.start.action.ActionsDetailActivity;
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

import butterknife.Bind;

/**
 * 葡星圈
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoStartCircleFragment extends PTWDFragment implements TitleBar.TitleItemSelectedListener {
    private static final String STATUS_ONGOING = "ONGOING";//进行中
    private static final String STATUS_CLOSE = "CLOSE";//截止
    private static final String TYPE_ACTION = "TEXT";//活动
    private static final String TYPE_NEWS = "NEWS";//新闻

    @Bind(R.id.sticky_layout)
    StickyHeaderLayout sticky_layout;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.stickyHeaderLayout_scrollable)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.bl_banner)
    BannerLayout bl_banner;
    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar ll_title;

    private ActionNewsAdapter adapter;
    private boolean isStop;//广告栏是否被停止

    private int currentPage = 0;//当前页码

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
        rv_content.setAdapter(adapter);
        addListener();

        getBannerList();//获取广告列表
        networkRequest(StartApi.getActionList(String.valueOf(currentPage), currentStatus, currentType)
                , new SimpleFastJsonCallback<ArrayList<ActionNews>>(ActionNews.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<ActionNews> result) {
//                        cacheEnterDisk(url, result);
                adapter.addAll(result);
                if (result.size() >= pageCount)
                    currentPage++;
                else
                    rv_content.noMoreLoading();
                loading.dismiss();
            }
        });
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
                currentPage = 0;
                rv_content.reset();
                networkRequest(StartApi.getActionList(String.valueOf(currentPage), currentStatus, currentType)
                        , new SimpleFastJsonCallback<ArrayList<ActionNews>>(ActionNews.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<ActionNews> result) {
                        adapter.replaceAll(result);
                        if (result.size() >= pageCount)
                            currentPage++;
                        else
                            rv_content.noMoreLoading();
                        loading.dismiss();
                        ptl_refresh.refreshComplete();
                    }
                });
            }
        });
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                networkRequest(StartApi.getActionList(String.valueOf(currentPage), currentStatus, currentType)
                        , new SimpleFastJsonCallback<ArrayList<ActionNews>>(ActionNews.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<ActionNews> result) {
                        adapter.addAll(result);
                        if (result.size() >= pageCount) {
                            rv_content.loadMoreComplete();
                            currentPage++;
                        } else rv_content.noMoreLoading();
                        loading.dismiss();
                    }
                });
            }
        });
        rv_content.setOnItemClickListener(new OnItemClickListener<ActionNews>() {
            @Override
            public void onItemClick(ActionNews actionNewsList, int position) {
                startActivity(ActionsDetailActivity.class);
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
        return new String[]{StartApi.URL_BANNER,StartApi.URL_ACTION_LIST};
    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        currentPage = 0;
        rv_content.reset();
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
        networkRequest(StartApi.getActionList(String.valueOf(currentPage), currentStatus, currentType)
                , new SimpleFastJsonCallback<ArrayList<ActionNews>>(ActionNews.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<ActionNews> result) {
                adapter.replaceAll(result);
                if (result.size() >= pageCount)
                    currentPage++;
                else
                    rv_content.noMoreLoading();
                loading.dismiss();
            }
        });
    }

//    /**
//     * 获取活动标签
//     * by yanghx
//     */
//    private void getActionLabel() {
//        networkRequest(StartApi.getActionLabel(), new SimpleFastJsonCallback<ArrayList<ActionLabel>>(ActionLabel.class, loading) {
//            @Override
//            public void onSuccess(String url, ArrayList<ActionLabel> result) {
//                Log.i("pt", "活动标签请求成功");
//            }
//        });
//    }
//
//    /**
//     * 获取活动报名列表
//     * by yanghx
//     *
//     * @param action_id 活动ID
//     */
//    private void getEnrollment(String action_id) {
//        networkRequest(StartApi.getEnrollment(action_id), new SimpleFastJsonCallback<ArrayList<ActionEnrollment>>(ActionEnrollment.class, loading) {
//            @Override
//            public void onSuccess(String url, ArrayList<ActionEnrollment> result) {
//                Log.i("pt", "活动报名列表请求成功");
//            }
//        });
//    }
//
//    /**
//     * model暂无
//     * <p/>
//     * 活动报名参加(提交)
//     * by yanghx
//     *
//     * @param user_id     用户ID
//     * @param identity    家长身份
//     * @param phone       手机号码
//     * @param nick_name   昵称
//     * @param age         年龄
//     * @param wechat      微信
//     * @param parent_name 家长姓名
//     * @param msg         留言
//     */
//    private void participateAdd(String user_id, String identity, String phone, String nick_name, String age, String wechat, String parent_name, String msg) {
//        networkRequest(StartApi.participateAdd(user_id, identity, phone, nick_name, age, wechat, parent_name, msg),
//                new SimpleFastJsonCallback<ArrayList<String>>(String.class, loading) {
//                    @Override
//                    public void onSuccess(String url, ArrayList<String> result) {
//                        Log.i("pt", "活动报名参加提交成功");
//                    }
//                });
//    }
//
//    /**
//     * 获取个人信息
//     * by yanghx
//     *
//     * @param user_id 用户ID
//     */
//    private void getProfile(String user_id) {
//        networkRequest(StartApi.getProfile(user_id), new SimpleFastJsonCallback<ArrayList<Profile>>(Profile.class, loading) {
//            @Override
//            public void onSuccess(String url, ArrayList<Profile> result) {
//                Log.i("pt", "个人信息请求成功");
//            }
//        });
//    }
//
//    /**
//     * model暂无
//     * <p/>
//     * 审核活动报名用户
//     * by yanghx
//     *
//     * @param user_id   用户ID
//     * @param action_id 活动ID
//     * @param type      审核类型
//     */
//    private void auditUser(String user_id, String action_id, AuditType type) {
//        networkRequest(StartApi.auditUser(user_id, action_id, type), new SimpleFastJsonCallback<ArrayList<String>>(String.class, loading) {
//            @Override
//            public void onSuccess(String url, ArrayList<String> result) {
//                Log.i("pt", "审核活动报名用户提交成功");
//            }
//        });
//    }
//
//    /**
//     * 地图接口查询
//     * by yanghx
//     *
//     * @param action_id 活动ID
//     */
//    private void getMap(String action_id) {
//        networkRequest(StartApi.getMap(action_id), new SimpleFastJsonCallback<ArrayList<MapInfo>>(MapInfo.class, loading) {
//            @Override
//            public void onSuccess(String url, ArrayList<MapInfo> result) {
//                Log.i("pt", "地图接口查询请求成功");
//            }
//        });
//    }
//
//    /**
//     * model暂无
//     * <p/>
//     * 提交葡萄籽问题
//     * by yanghx
//     *
//     * @param msg 问题
//     */
//    private void question(String msg) {
//        networkRequest(StartApi.question(msg), new SimpleFastJsonCallback<ArrayList<String>>(String.class, loading) {
//            @Override
//            public void onSuccess(String url, ArrayList<String> result) {
//                Log.i("pt", "葡萄籽问题提交成功");
//            }
//        });
//    }
}
