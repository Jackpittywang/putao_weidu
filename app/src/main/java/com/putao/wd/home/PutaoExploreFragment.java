package com.putao.wd.home;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.explore.MarketingActivity;
import com.putao.wd.qrcode.CaptureActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.image.ImageDraweeView;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索(首页)
 * Created by guchenkai on 2016/1/11.
 */
public class PutaoExploreFragment extends BasicFragment implements View.OnClickListener {

    @Bind(R.id.iv_thinking)
    ImageView iv_thinking;
    @Bind(R.id.iv_scan)
    ImageView iv_scan;
    @Bind(R.id.vp_content)
    ViewPager vp_content;

    private LayoutInflater inflater;
    private List<View> lists;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        ExplorePagerAdapter pagerAdapter = new ExplorePagerAdapter(getData());
        vp_content.setAdapter(pagerAdapter);
        vp_content.setCurrentItem(0);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.iv_thinking, R.id.iv_scan})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_thinking:
                startActivity(MarketingActivity.class);
                break;
            case R.id.iv_scan:
                startActivity(CaptureActivity.class);
                break;
        }
    }

    /**
     * viewpager临时数据
     */
    private List<View> getData() {
        lists = new ArrayList<>();
        inflater = LayoutInflater.from(mActivity);
        for (int i = 0; i < 8; i++) {
            View view = inflater.inflate(R.layout.fragment_nexplore_item, null);
            lists.add(view);
        }
        return lists;
    }

    /**
     * ViewPager适配器
     */
    class ExplorePagerAdapter extends PagerAdapter {

        private List<View> lists;

        public ExplorePagerAdapter(List<View> lists) {
            this.lists = lists;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = lists.get(position);
            container.addView(view);
            initItemView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(lists.get(position));
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        /**
         * viewpager中item注册点击事件
         */
        private void initItemView(View view) {
            ImageDraweeView iv_video = (ImageDraweeView) view.findViewById(R.id.iv_video);
            iv_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(CaptureActivity.class);
                }
            });
            TextView tv_title = (TextView) view.findViewById(R.id.tv_count_comment);
            tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(MarketingActivity.class);
                }
            });
            view.findViewById(R.id.ll_count_cool).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(MarketingActivity.class);
                }
            });
        }

    }

}
