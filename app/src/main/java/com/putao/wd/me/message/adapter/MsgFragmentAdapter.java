package com.putao.wd.me.message.adapter;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * FragmentPager
 * @author wwj
 *
 */
public class MsgFragmentAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments;


    public MsgFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public MsgFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
