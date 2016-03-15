package com.putao.wd.created;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;

import com.putao.wd.R;
import com.sunnybear.library.view.viewpager.adapter.LoadMoreFragmentPagerAdapter;
import com.putao.wd.model.Create;
import com.sunnybear.library.controller.BasicFragmentActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

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
        LoadMoreFragmentPagerAdapter fragmentPagerAdapter = new LoadMoreFragmentPagerAdapter<Create>(getSupportFragmentManager(), mCreates) {
            @Override
            public ArrayList loadMoreData() {
                return null;
            }

            @Override
            public Fragment getItem(ArrayList<Create> datas, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(CreateBasicDetailActivity.POSITION, position);
                bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position));
                return new CreateBasicDetailFragment(bundle);
            }
        };
        vp_content.setAdapter(fragmentPagerAdapter);
        vp_content.setOffscreenPageLimit(3);
        vp_content.setCurrentItem(mPosition, true);
    }

    @OnClick({R.id.iv_close})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;
        }

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
