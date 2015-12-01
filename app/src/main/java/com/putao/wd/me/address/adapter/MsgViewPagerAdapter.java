package com.putao.wd.me.address.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by wango on 2015/12/1.
 */
public class MsgViewPagerAdapter extends PagerAdapter{
    //    private List<View> views;
    private List<View> views;
    private Context context;
    public MsgViewPagerAdapter(List<View> views,Context context){
        this.views=views;
        this.context=context;
    }
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager)container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager)container).addView(views.get(position));
        return views.get(position);
    }
}
