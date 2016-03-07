package com.putao.wd.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.explore.ExploreCommonFragment;
import com.putao.wd.explore.ExploreDetailFragment;
import com.putao.wd.explore.ExploreMoreFragment;
import com.putao.wd.explore.MarketingActivity;
import com.putao.wd.model.ExploreIndex;
import com.putao.wd.model.ExploreIndexs;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.image.FastBlur;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
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
    ViewPager vp_content;
    @Bind(R.id.tv_modified_time_day)
    TextView tv_modified_time_day;
    @Bind(R.id.tv_modified_time_mon)
    TextView tv_modified_time_mon;

    private static final String INDEX_CACHE = "index_cache";
    public static final String BLUR = "blur";
    public static final String POSITION = "position";
    private static int SAVE_TIME = 5;
    private static int BACKGROUND_CAN_CHANGGE = 1;
    private static int BACKGROUND_CAN_NOT_CHANGGE = 2;

    private ArrayList<ExploreIndex> mExploreIndexs;
    private ExploreIndexs mExploreIndex;
    private SparseArray<Fragment> mFragments;
    //    private PageChangeThread mThread;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private int mI;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        Logger.d("PutaoExploreFragment启动");
        vp_content.setOffscreenPageLimit(3);
        mHandlerThread = new HandlerThread("blurThread");
        mHandlerThread.start();
        Looper looper = mHandlerThread.getLooper();
        mHandler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                mI = BACKGROUND_CAN_NOT_CHANGGE;
                Bundle obj = (Bundle) msg.obj;
                int position = obj.getInt(POSITION);
                switch (msg.what) {
                    case 1:
                        try {
                            Bitmap map;
                            URL url = new URL(mExploreIndexs.get(position - 1).getBanner().get(0).getCover_pic());
                            URLConnection conn = url.openConnection();
                            conn.connect();
                            InputStream in;
                            in = conn.getInputStream();
                            map = BitmapFactory.decodeStream(in);
                            if (mI != BACKGROUND_CAN_NOT_CHANGGE || null == map) return;
                            Bitmap apply = FastBlur.doBlur(map, 50, false);
                            if (mI != BACKGROUND_CAN_NOT_CHANGGE) return;
                            EventBusHelper.post(apply, BLUR);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.img_explore_more_cover);
                        if (mI != BACKGROUND_CAN_NOT_CHANGGE) return;
                        Bitmap apply = FastBlur.doBlur(bmp, 50, false);
                        if (mI != BACKGROUND_CAN_NOT_CHANGGE) return;
                        EventBusHelper.post(apply, BLUR);
                        break;
                }
            }
        };
        initData();
        addListener();
       /* mThread = new PageChangeThread() {
            @Override
            public void run() {
                super.run();
            }
        };*/
    }

    private void pageSelected(int position) {
        Message message = new Message();
        message.what = position == mExploreIndexs.size() + 1 ? 2 : 1;
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        message.obj = bundle;
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessage(message);
    }

    private void addListener() {
        //切换页面刷新日期
        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                if (position == 0) {
                    vp_content.setCurrentItem(1);
                    return;
                } else if (position == mFragments.size() + 1) {
                    vp_content.setCurrentItem(mFragments.size());
                    return;
                }
                mI = BACKGROUND_CAN_CHANGGE;
                if (position == mExploreIndexs.size() + 1) {
                    hindDate("MORE");
                } else {
                    showDate();
                    addDate(position);
                }
                pageSelected(position);
//                mThread.start(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        Date date = new Date(mExploreIndexs.get(position - 1).getSend_time() * 1000);
        Date dateNow = new Date(System.currentTimeMillis());
        String day = formatDay.format(date);
        String mon = formatMon.format(date);
        String dayNow = formatDay.format(dateNow);
        String monNow = formatMon.format(dateNow);
        tv_modified_time_day.setText(formatDay.format(date));
        tv_modified_time_mon.setText(getEngMon(formatMon.format(date)));
        if (dayNow.equals(day) && monNow.equals(mon)) hindDate("今天");
    }

    private void initData() {
        mExploreIndex = (ExploreIndexs) mDiskFileCacheHelper.getAsSerializable(INDEX_CACHE);
        if (null != mExploreIndex) {
            mExploreIndexs = mExploreIndex.getExploreIndexes();
            if (null != mExploreIndexs && mExploreIndexs.size() > 0) {
                addFragments();
                addDate(vp_content.getCurrentItem());
                if ((mExploreIndex.getSaveTime() + SAVE_TIME * 1000) < System.currentTimeMillis()) {
                    getIndexList();
                }
            } else {
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
/*            case R.id.iv_scan:
                startActivity(CaptureActivity.class);
                break;*/
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

    /*class PageChangeThread extends Thread {
        private int position;
        private boolean isStart = false;

        @Override
        public void run() {
            super.run();
            Logger.d(Thread.currentThread().getName() + "---------------------------");
            pageSelected(position);
        }

        public void start(int position) {
            if (!isStart) {
                isStart = true;
                start();
            }
            this.position = position;
            run();
        }
    }*/
}
