package com.putao.wd.created;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import com.putao.wd.R;
import com.putao.wd.created.adapter.CreateFragmentPagerAdapter;
import com.putao.wd.model.Create;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.view.select.TabItem;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 只有一个viewpager和一个关闭按钮的activity
 * Created by zhanghao on 2016/3/15.
 */
public class CreateDetailActivity extends BasicFragmentActivity {

    @Bind(R.id.vp_content)
    ViewPager vp_content;

    private SparseArray<Fragment> mFragments;
    private ArrayList<Create> mCreates;
    private int mPosition;
    private boolean isShowProgress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scroll_viewpager_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        mCreates = (ArrayList<Create>) args.getSerializable(CreateBasicDetailActivity.CREATE);
        mPosition = args.getInt(CreateBasicDetailActivity.POSITION);
        isShowProgress = args.getBoolean(CreateBasicDetailActivity.SHOW_PROGRESS);
        CreateFragmentPagerAdapter fragmentPagerAdapter = new CreateFragmentPagerAdapter(mContext, getSupportFragmentManager(), mCreates, mPosition) {
            @Override
            public void getNewsData(int position) {

            }
        };
        vp_content.setAdapter(fragmentPagerAdapter);
        vp_content.setOffscreenPageLimit(3);
        vp_content.setCurrentItem(mPosition, true);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
