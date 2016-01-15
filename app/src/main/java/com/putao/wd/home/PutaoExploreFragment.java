package com.putao.wd.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.explore.ExploreCommonFragment;
import com.putao.wd.explore.ExploreMoreFragment;
import com.putao.wd.explore.MarketingActivity;
import com.putao.wd.qrcode.CaptureActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.Logger;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索(首页)
 * Created by guchenkai on 2016/1/11.
 */
public class PutaoExploreFragment extends BasicFragment implements View.OnClickListener {

    public static final String EVENT_CURRENT_ITEM =  "current_item";

    @Bind(R.id.tv_thinking)
    TextView tv_thinking;
    @Bind(R.id.iv_scan)
    ImageView iv_scan;
    @Bind(R.id.vp_content)
    ViewPager vp_content;


    private SparseArray<Fragment> mFragments;
    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vp_content.setCurrentItem(0);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        Logger.d("PutaoExploreFragment启动");
        addFragments();
        setViewPage();
//        vp_content.setCurrentItem(7);
//        mHandle.sendEmptyMessageDelayed(0x123, 5000);
//        for (int i = 6; i > -1; i--) {
//            Message msg = mHandle.obtainMessage();
//            msg.arg1 = i;
//            mHandle.sendMessageDelayed(msg, 3000);
//        }

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
        mFragments.put(0, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(1, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(2, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(3, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(4, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(5, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(6, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(7, Fragment.instantiate(mActivity, ExploreMoreFragment.class.getName()));
    }

    /**
     * viewpager添加适配器、监听器
     */
    private void setViewPage() {
        vp_content.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                EventBusHelper.post(position, EVENT_CURRENT_ITEM);
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
//        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Logger.w("position = " + position);
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
    }




//    /**
//     * viewpager临时数据
//     */
//    private List<View> getData() {
//        lists = new ArrayList<>();
//        inflater = LayoutInflater.from(mActivity);
//        View view = null;
//        for (int i = 0; i < 8; i++) {
//            if (i != 7) {
//                view = inflater.inflate(R.layout.fragment_nexplore_item, null);
//            } else {
//                view = inflater.inflate(R.layout.fragment_nexplore_item, null);
//            }
//            lists.add(view);
//        }
//        return lists;
//    }


//    /**
//     * ViewPager适配器
//     */
//    class ExplorePagerAdapter extends PagerAdapter {
//
//        private List<View> views;
//        private List<PagerExplore> datas;
//
//        public ExplorePagerAdapter(List<View> views, List<PagerExplore> datas) {
//            this.views = lists;
//            this.datas = datas;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            View view = views.get(position);
//            container.addView(view);
//            initItemView(view);
//            return view;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(views.get(position));
//        }
//
//        @Override
//        public int getCount() {
//            return views.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View arg0, Object arg1) {
//            return arg0 == arg1;
//        }
//
//        /**
//         * viewpager中item注册点击事件
//         */
//        private void initItemView(View view) {
//            ImageDraweeView iv_video = (ImageDraweeView) view.findViewById(R.id.iv_video);
//            iv_video.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(CaptureActivity.class);
//                }
//            });
//            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
//            tv_title.setText("探索首页");
//            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
//            tv_content.setText("内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
//            TextView tv_count_comment = (TextView) view.findViewById(R.id.tv_count_comment);
//            tv_count_comment.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(MarketingActivity.class);
//                }
//            });
//            view.findViewById(R.id.ll_count_cool).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(MarketingActivity.class);
//                }
//            });
//        }
//
//    }

}
