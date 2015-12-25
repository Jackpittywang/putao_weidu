package com.putao.wd.me.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;
import com.sunnybear.library.view.viewpager.UnScrollableViewPager;

import butterknife.Bind;

/**
 * 消息中心
 * Created by wangou on 2015/12/1.
 */
public class MessageCenterActivity extends PTWDActivity implements TitleBar.OnTitleItemSelectedListener {
    @Bind(R.id.ll_title_bar)
    TitleBar ll_title_bar;//顶部导航菜单
    @Bind(R.id.vp_message)
    UnScrollableViewPager vp_message;

    private FragmentPagerAdapter adapter;
    private SparseArray<Fragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        addFragment();
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        vp_message.setAdapter(adapter);
        addListener();
    }

    /**
     * 初始化Fragment
     */
    private void addFragment() {
        mFragments = new SparseArray<>();
        mFragments.put(0, Fragment.instantiate(mContext, NotifyFragment.class.getName()));
        mFragments.put(1, Fragment.instantiate(mContext, ReplyFragment.class.getName()));
        mFragments.put(2, Fragment.instantiate(mContext, ReplyFragment.class.getName()));
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        ll_title_bar.setOnTitleItemSelectedListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        switch (item.getId()) {
            case R.id.ll_notice://通知
                ToastUtils.showToastShort(this, "通知");
                break;
            case R.id.ll_reply://回复
                ToastUtils.showToastShort(this, "回复");
                break;
            case R.id.ll_cool://赞
                ToastUtils.showToastShort(this, "赞");
                break;
        }
        vp_message.setCurrentItem(position);
    }
}
