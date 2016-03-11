package com.putao.wd.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.putao.wd.R;
import com.putao.wd.created.FancyFragment;
import com.putao.wd.store.pay.PaySuccessActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.viewpager.UnScrollableViewPager;

import butterknife.Bind;

/**
 * 创造(首页)
 * Created by guchenkai on 2016/1/13.
 */
public class PutaoCreatedFragment extends BasicFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.vp_content)
    UnScrollableViewPager vp_content;
    @Bind(R.id.rg_create)
    RadioGroup rg_create;
    @Bind(R.id.rb_step1)
    RadioButton rb_step1;
    @Bind(R.id.rb_step2)
    RadioButton rb_step2;
    @Bind(R.id.rb_step3)
    RadioButton rb_step3;
    private SparseArray<Fragment> mFragments;
    // 标志位，标志已经初始化完成。
    public static boolean isPrepared;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_created;
    }

    @Override
    public void onViewCreatedFinish(Bundle saveInstanceState) {
        Logger.d("PutaoCreatedFragment启动");
        isPrepared = true;
        rg_create.check(R.id.rb_step2);
        vp_content.setCurrentItem(1, false);
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isPrepared = false;
        //填充各控件的数据
        addFragments();
        rg_create.setOnCheckedChangeListener(this);
        rg_create.check(R.id.rb_step2);
        vp_content.setCurrentItem(1, false);
        vp_content.setOffscreenPageLimit(3);
    }

    /**
     * 添加Fragment
     */
    private void addFragments() {
        vp_content.setOffscreenPageLimit(1);
        mFragments = new SparseArray<>();
        mFragments.put(0, Fragment.instantiate(mActivity, FancyFragment.class.getName()));
        mFragments.put(1, Fragment.instantiate(mActivity, PutaoCreatedSecondFragment.class.getName()));
        mFragments.put(2, Fragment.instantiate(mActivity, PutaoStoreFragment.class.getName()));
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_step1:
                vp_content.setCurrentItem(0, false);
                break;
            case R.id.rb_step2:
                vp_content.setCurrentItem(1, false);
                break;
            case R.id.rb_step3:
                vp_content.setCurrentItem(2, false);
                break;
        }
    }

    @Subcriber(tag = PaySuccessActivity.PAY_FINISH)
    private void setPay(String pay) {
        rg_create.check(R.id.rb_step3);
        vp_content.setCurrentItem(2, false);
    }
}
