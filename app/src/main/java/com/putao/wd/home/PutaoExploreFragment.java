package com.putao.wd.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.explore.ExploreCommonFragment;
import com.putao.wd.explore.MarketingActivity;
import com.putao.wd.qrcode.CaptureActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.viewpager.BounceBackViewPager;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索(首页)
 * Created by guchenkai on 2016/1/11.
 */
public class PutaoExploreFragment extends BasicFragment implements View.OnClickListener {

    @Bind(R.id.tv_thinking)
    TextView tv_thinking;
    @Bind(R.id.iv_scan)
    ImageView iv_scan;
    @Bind(R.id.vp_content)
    BounceBackViewPager vp_content;

    private SparseArray<Fragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        Logger.d("PutaoExploreFragment启动");
        addFragments();
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
        Bundle bundle0 = new Bundle();
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();
        Bundle bundle4 = new Bundle();
        Bundle bundle5 = new Bundle();
        Bundle bundle6 = new Bundle();
        mFragments.put(0, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(1, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(2, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(3, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(4, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(5, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
        mFragments.put(6, Fragment.instantiate(mActivity, ExploreCommonFragment.class.getName()));
//        mFragments.put(7, Fragment.instantiate(mActivity, ExploreMoreFragment.class.getName()));

//        switch(position) {
//            case 0:
//                iv_video.setBackgroundResource(R.drawable.icon_40_01);
//                tv_title.setText("多点时间陪孩子");
//                tv_content.setText("现代家长总是忙忙忙，还是多抽点时间陪孩子吧，给孩子留下一个美好的童年");
//                break;
//            case 1:
//                iv_video.setBackgroundResource(R.drawable.icon_40_02);
//                tv_title.setText("和孩子一起玩耍");
//                tv_content.setText("让葡萄探索号和孩子一起玩耍");
//                break;
//            case 2:
//                iv_video.setBackgroundResource(R.drawable.icon_40_03);
//                tv_title.setText("探索号的陪伴");
//                tv_content.setText("探索号是葡萄科技为孩子倾力打造的一款智能玩具，让您的孩子不再孤单");
//                break;
//            case 3:
//                iv_video.setBackgroundResource(R.drawable.icon_40_04);
//                tv_title.setText("多玩只能游戏");
//                tv_content.setText("多玩智能游戏，可以有效提高孩子的学习能力哦");
//                break;
//            case 4:
//                iv_video.setBackgroundResource(R.drawable.icon_40_05);
//                tv_title.setText("淘淘向右走");
//                tv_content.setText("淘淘向右走，让您的孩子体验闯关的乐趣");
//                break;
//            case 5:
//                iv_video.setBackgroundResource(R.drawable.icon_40_06);
//                tv_title.setText("魔方");
//                tv_content.setText("挖掘孩子的潜能从魔方开始吧");
//                break;
//            case 6:
//                iv_video.setBackgroundResource(R.drawable.icon_40_07);
//                tv_title.setText("魔方");
//                tv_content.setText("魔方不只是大人才能玩转的，葡萄魔方陪伴您的孩子更好的成长");
//                break;
//        }
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
