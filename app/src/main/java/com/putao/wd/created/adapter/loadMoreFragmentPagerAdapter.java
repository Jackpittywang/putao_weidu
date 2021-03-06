package com.putao.wd.created.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhanghao on 2016/3/15.
 */
public abstract class loadMoreFragmentPagerAdapter<T extends Serializable> extends FragmentPagerAdapter {
    private ArrayList<T> mDatas;

    public loadMoreFragmentPagerAdapter(FragmentManager fm, ArrayList<T> datas) {
        super(fm);
        this.mDatas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        return getItem(mDatas, position);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (position >= mDatas.size() - 3) {
            mDatas.addAll(mDatas);
            notifyDataSetChanged();
        }
    }


    public abstract ArrayList<T> getmDatas();

    public abstract Fragment getItem(ArrayList<T> datas, int position);
}
