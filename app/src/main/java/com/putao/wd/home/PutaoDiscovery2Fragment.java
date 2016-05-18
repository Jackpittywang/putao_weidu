package com.putao.wd.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.pt_discovery.ResourceFragment;
import com.putao.wd.pt_discovery.VideoFragment;
import com.sunnybear.library.controller.FragmentSwitchAdapter;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.select.TabBar;
import com.sunnybear.library.view.select.TabItem;
import com.sunnybear.library.view.viewpager.UnScrollableViewPager;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/5/4.
 */
public class PutaoDiscovery2Fragment extends PTWDFragment<GlobalApplication>{

    @Bind(R.id.tb_bar)
    TabBar tb_bar;
    @Bind(R.id.ti_video)
    TabItem ti_video;
    @Bind(R.id.ti_resource)
    TabItem ti_resource;
    @Bind(R.id.vp_content)
    UnScrollableViewPager vp_content;

    private SparseArray<Fragment> mFragments;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery2;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        loadFragment();
        navigation_bar.setLeftClickable(false);
        navigation_bar.getLeftView().setVisibility(View.GONE);
        vp_content.setAdapter(new FragmentSwitchAdapter(getChildFragmentManager(), mFragments));
        vp_content.setOffscreenPageLimit(2);
        addListener();
        tb_bar.setTabItemSelected(R.id.ti_video);
        vp_content.setCurrentItem(0, false);
    }

    private void addListener() {
        tb_bar.setOnTabItemSelectedListener(new TabBar.OnTabItemSelectedListener() {
            @Override
            public void onTabItemSelected(TabItem item, int position) {
                switch (position){
                    case 0:
                        YouMengHelper.onEvent(mActivity,YouMengHelper.DiscoverHome_title,"发现-视频");
                        break;
                    case 1:
                        YouMengHelper.onEvent(mActivity,YouMengHelper.DiscoverHome_title,"发现-找资源");
                        break;
                }


                vp_content.setCurrentItem(position,false);
            }
        });
    }

    private void loadFragment() {
        mFragments = new SparseArray<>();
        mFragments.put(0,Fragment.instantiate(mActivity, VideoFragment.class.getName()));
        mFragments.put(1,Fragment.instantiate(mActivity, ResourceFragment.class.getName()));
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

//    @OnClick({R.id.ti_video,R.id.ti_resource})
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.ti_video:
//                ToastUtils.showToastShort(mActivity,"看视屏");
//                break;
//            case R.id.ti_resource:
//                ToastUtils.showToastShort(mActivity,"找资源");
//                break;
//        }
//    }
}
