package com.putao.wd.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.explore.ExploreCommonFragment;
import com.putao.wd.explore.ExploreMoreFragment;
import com.putao.wd.explore.MarketingActivity;
import com.putao.wd.model.ExploreIndex;
import com.putao.wd.qrcode.CaptureActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.viewpager.BounceBackViewPager;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Bind(R.id.iv_scan)
    ImageView iv_scan;
    @Bind(R.id.vp_content)
    BounceBackViewPager vp_content;
    @Bind(R.id.tv_modified_time_day)
    TextView tv_modified_time_day;
    @Bind(R.id.tv_modified_time_mon)
    TextView tv_modified_time_mon;

    private List<ExploreIndex> mExploreIndexs;
    private SparseArray<Fragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        Logger.d("PutaoExploreFragment启动");
        addListener();
        initData();
    }

    private void addListener() {
        //切换页面刷新日期
        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position < mExploreIndexs.size()) {
                    showDate();
                    addDate(position);
                } else {
                    hindDate("更多内容");
                }
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
        Date date = new Date(mExploreIndexs.get(position).getModified_time() * 1000);
        Date dateNow = new Date(System.currentTimeMillis());
        String day = formatDay.format(date);
        String mon = formatMon.format(date);
        String dayNow = formatDay.format(dateNow);
        String monNow = formatMon.format(dateNow);
        tv_modified_time_day.setText(formatDay.format(date));
        tv_modified_time_mon.setText(getEngMon(formatMon.format(date)));
        if (dayNow.equals(day)&&monNow.equals(mon))hindDate("今天");
    }

    private void initData() {
        networkRequest(ExploreApi.getArticleList(),
                new SimpleFastJsonCallback<ArrayList<ExploreIndex>>(ExploreIndex.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<ExploreIndex> result) {
                        mExploreIndexs = result;
                        addFragments();
                        addDate(vp_content.getCurrentItem());
                    }
                });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.tv_thinking, R.id.iv_scan})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_thinking:
                startActivity(MarketingActivity.class);
                break;
            case R.id.iv_scan:
                startActivity(CaptureActivity.class);
                break;
        }
    }

    /**
     * 初始化Fragment
     */
    private void addFragments() {
        mFragments = new SparseArray<>();
        for (int i = 0; i < mExploreIndexs.size(); i++) {
            mFragments.put(i, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName(), addBundle(i)));
        }
        mFragments.put(mExploreIndexs.size(), Fragment.instantiate(mActivity, ExploreMoreFragment.class.getName()));
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
    }

    private Bundle addBundle(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ExploreCommonFragment.INDEX_DATA_PAGE, position);
        bundle.putSerializable(ExploreCommonFragment.INDEX_DATA, (Serializable) mExploreIndexs);
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
