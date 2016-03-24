package com.putao.wd.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MyViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.created.CreateDetailActivity;
import com.putao.wd.explore.ExploreCommonFragment;
import com.putao.wd.explore.ExploreDetailFragment;
import com.putao.wd.explore.ExploreDetailNActivity;
import com.putao.wd.explore.ExploreMoreActivity;
import com.putao.wd.explore.ExploreMoreFragment;
import com.putao.wd.explore.MarketingActivity;
import com.putao.wd.model.ExploreIndex;
import com.putao.wd.model.ExploreIndexs;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.viewpager.ChildViewPager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索(首页)
 * Created by guchenkai on 2016/1/11.
 */
public class PutaoExploreFragment extends BasicFragment implements View.OnClickListener {

    @Bind(R.id.tv_thinking)
    TextView tv_thinking;
    @Bind(R.id.tv_modified)
    TextView tv_modified;
    //    @Bind(R.id.iv_scan)
//    ImageView iv_scan;
    @Bind(R.id.vp_content)
    ChildViewPager vp_content;
    @Bind(R.id.tv_modified_time_day)
    TextView tv_modified_time_day;
    @Bind(R.id.tv_modified_time_mon)
    TextView tv_modified_time_mon;

    private static final String INDEX_CACHE = "index_cache";
    public static final String BLUR = "blur";
    public static final String POSITION = "position";
    private static int SAVE_TIME = 5;
    //    private static int BACKGROUND_CAN_CHANGGE = 1;
//    private static int BACKGROUND_CAN_NOT_CHANGGE = 2;
    public static boolean BACKGROUND_CAN_CHANGGE = true;


    private ArrayList<ExploreIndex> mExploreIndexs;
    private ExploreIndexs mExploreIndex;
    private SparseArray<Fragment> mFragments;
    //    private PageChangeThread mThread;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private int mI;
    // 标志位，标志已经初始化完成。
    public static boolean isPrepared;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        Logger.d("PutaoExploreFragment启动");
        vp_content.setOffscreenPageLimit(3);
        addListener();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }, 200);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (null == vp_content) return;
        isPrepared = false;
        //填充各控件的数据
        initData();
    }

    private void addListener() {
        //切换页面刷新日期
        vp_content.addOnPageChangeListener(new MyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                if (position == 0) {
                    vp_content.setCurrentItem(1);
                    return;
                } else if (position == mExploreIndexs.size() + 2) {
                    vp_content.setCurrentItem(mExploreIndexs.size() + 1);
                    return;
                }
                if (position == mExploreIndexs.size() + 1) {
                    hindDate("MORE");
                } else {
                    showDate();
                    addDate(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_content.setOnSingleTouchListener(new ChildViewPager.OnSingleTouchListener() {
            @Override
            public void onSingleTouch() {
                int position = vp_content.getCurrentItem() - 1;
                if (position == 7) {
                    startActivity(ExploreMoreActivity.class);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(ExploreDetailNActivity.DATAS, mExploreIndexs);
                bundle.putInt(ExploreDetailNActivity.POSITION, position);
                bundle.putBoolean(CreateDetailActivity.HAS_MORE_DATA, false);
                startActivity(ExploreDetailNActivity.class, bundle);
                mActivity.overridePendingTransition(R.anim.in_from_down, R.anim.companion_in_from_down);
            }
        });
    }

    private void showDate() {
        tv_modified_time_day.setVisibility(View.VISIBLE);
        tv_modified_time_mon.setVisibility(View.VISIBLE);
        tv_modified.setVisibility(View.GONE);
    }

    private void hindDate(String text) {
        tv_modified_time_day.setVisibility(View.GONE);
        tv_modified_time_mon.setVisibility(View.GONE);
        tv_modified.setVisibility(View.VISIBLE);
        tv_modified.setText(text);
    }

    private void addDate(int position) {
        DateFormat formatMon = new SimpleDateFormat("MM");
        DateFormat formatDay = new SimpleDateFormat("dd");
        Date date = new Date(mExploreIndexs.get(position - 1).getSend_time());
        Date dateNow = new Date(System.currentTimeMillis());
        String day = formatDay.format(date);
        String mon = formatMon.format(date);
        String dayNow = formatDay.format(dateNow);
        String monNow = formatMon.format(dateNow);
        tv_modified_time_day.setText(day);
        tv_modified_time_mon.setText(getEngMon(mon));
        if (dayNow.equals(day) && monNow.equals(mon)) hindDate("今天");
    }

    private void initData() {
        mExploreIndex = (ExploreIndexs) mDiskFileCacheHelper.getAsSerializable(INDEX_CACHE);
        if (null != mExploreIndex) {
            mExploreIndexs = mExploreIndex.getExploreIndexes();
            if (null != mExploreIndexs && mExploreIndexs.size() > 0) {
                ((IndexActivity) getActivity()).hideLoading();
                addFragments();
                addDate(vp_content.getCurrentItem());
                if ((mExploreIndex.getSaveTime() + SAVE_TIME * 1000) < System.currentTimeMillis()) {
                    getIndexList();
                }
            } else {
                ((IndexActivity) getActivity()).showLoading();
                getIndexList();
            }
        } else {
            getIndexList();
        }
    }

    private void getIndexList() {
        networkRequest(ExploreApi.getArticleList(),
                new SimpleFastJsonCallback<ArrayList<ExploreIndex>>(ExploreIndex.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<ExploreIndex> result) {
                        if (null != result && result.size() > 0) {
                            mExploreIndexs = result;
                            mExploreIndex = mExploreIndex == null ? new ExploreIndexs() : mExploreIndex;
                            mExploreIndex.setSaveTime(System.currentTimeMillis());
                            mExploreIndex.setExploreIndexes(result);
                            mDiskFileCacheHelper.remove(INDEX_CACHE);
                            for (ExploreIndex exploreIndex : mExploreIndexs) {
                                mDiskFileCacheHelper.remove(ExploreDetailFragment.COOL_COUNT + exploreIndex.getArticle_id());
                                mDiskFileCacheHelper.remove(ExploreDetailFragment.COMMENT_COUNT + exploreIndex.getArticle_id());
                            }
                            mDiskFileCacheHelper.put(INDEX_CACHE, mExploreIndex);
                            mFragments = null;
                            addFragments();
                            addDate(vp_content.getCurrentItem());
                        }
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        if (null == mExploreIndexs) return;
                        addFragments();
                        addDate(vp_content.getCurrentItem());
                    }

                    @Override
                    public void onFinish(String url, boolean isSuccess, String msg) {
                        super.onFinish(url, isSuccess, msg);
                        ((IndexActivity) getActivity()).hideLoading();
                    }
                }, false);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.tv_thinking})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_thinking:
                startActivity(MarketingActivity.class);
                break;
        }
    }

    /**
     * 初始化Fragment
     */
    private void addFragments() {
        mFragments = new SparseArray<>();
        mFragments.put(0, new Fragment());
        for (int i = 1; i <= mExploreIndexs.size(); i++) {
            mFragments.put(i, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName(), addBundle(i - 1)));
        }
        mFragments.put(mExploreIndexs.size() + 1, Fragment.instantiate(mActivity, ExploreMoreFragment.class.getName()));
        mFragments.put(mExploreIndexs.size() + 2, new Fragment());
        vp_content.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
        vp_content.setCurrentItem(1);
    }

    private Bundle addBundle(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ExploreCommonFragment.INDEX_DATA_PAGE, position);
        bundle.putSerializable(ExploreCommonFragment.INDEX_DATA, mExploreIndexs);
        return bundle;
    }

    public String getEngMon(String mon) {
        switch (mon) {
            case "01":
                return "JAN";
            case "02":
                return "FEB";
            case "03":
                return "MAR";
            case "04":
                return "APR";
            case "05":
                return "MAY";
            case "06":
                return "JUNE";
            case "07":
                return "JULY";
            case "08":
                return "AUG";
            case "09":
                return "SEPT";
            case "10":
                return "OCT";
            case "11":
                return "NOV";
            case "12":
                return "DEC";
        }
        return "";
    }
}
