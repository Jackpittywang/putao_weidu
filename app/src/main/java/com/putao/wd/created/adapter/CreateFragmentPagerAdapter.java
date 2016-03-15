package com.putao.wd.created.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.putao.wd.created.CreateBasicDetailActivity;
import com.putao.wd.created.CreateBasicDetailFragment;
import com.putao.wd.model.Create;

import java.util.ArrayList;

/**
 * Created by zhanghao on 2016/3/15.
 */
public abstract class CreateFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private float size;
    private String categoryId;
    private ArrayList idlList;
    private boolean[] positionValue;
    private int[] eachItemPicNum;
    private String[] shareStr;
    private String[] shareImg;
    private ArrayList<Fragment> fragments;
    private ArrayList<Create> datas;
    /* private final CreateBasicDetailFragment firstBasicDetailFragment;
     private final CreateBasicDetailFragment secondBasicDetailFragment;
     private final CreateBasicDetailFragment thirdBasicDetailFragment;*/
    private boolean isFirstFirst;
    private boolean isSecondFirst;
    private boolean isThirdFirst;


    public CreateFragmentPagerAdapter(Context context, FragmentManager fm, ArrayList<Create> datas, int position/*, ArrayList<Fragment> fragments, float size,
                                      String categoryId*/) {
        super(fm);
        this.context = context;
        this.datas = datas;
       /* Bundle bundle = new Bundle();
        if (position != 0) {
            bundle.putInt(CreateBasicDetailActivity.POSITION, position - 1);
            bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position - 1));
            firstBasicDetailFragment = new CreateBasicDetailFragment(bundle);
        } else {
            bundle.putInt(CreateBasicDetailActivity.POSITION, position);
            bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position));
            firstBasicDetailFragment = new CreateBasicDetailFragment(bundle);
        }
        bundle.putInt(CreateBasicDetailActivity.POSITION, position);
        bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position));
        secondBasicDetailFragment = new CreateBasicDetailFragment(bundle);
        if (position != datas.size() - 1) {
            bundle.putInt(CreateBasicDetailActivity.POSITION, position + 1);
            bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position + 1));
            thirdBasicDetailFragment = new CreateBasicDetailFragment(bundle);
        } else {
            bundle.putInt(CreateBasicDetailActivity.POSITION, position);
            bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position));
            thirdBasicDetailFragment = new CreateBasicDetailFragment(bundle);
        }*/
       /* this.size = size;
        this.categoryId = categoryId;
        this.fragments = fragments;*/
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(CreateBasicDetailActivity.POSITION, position);
        bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position));
       /* switch (position % 3) {
            case 0:
                if (position != 0) {
                    bundle.putInt(CreateBasicDetailActivity.POSITION, position - 1);
                    bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position - 1));
                } else {
                    bundle.putInt(CreateBasicDetailActivity.POSITION, position);
                    bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position));
                }
                break;
            case 1:
                bundle.putInt(CreateBasicDetailActivity.POSITION, position);
                bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position));
                break;
            case 2:
                if (position != datas.size() - 1) {
                    bundle.putInt(CreateBasicDetailActivity.POSITION, position + 1);
                    bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position + 1));
                } else {
                    bundle.putInt(CreateBasicDetailActivity.POSITION, position);
                    bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position));
                }
                break;
        }*/
        return new CreateBasicDetailFragment(bundle);
    }

    public void addPagerData(ArrayList<Bundle> list) {
        /*idlList = list;
        positionValue = new boolean[idlList.size()];
        eachItemPicNum = new int[idlList.size()];
        for (int i = 0; i < positionValue.length; i++) {
            positionValue[i] = false;
            eachItemPicNum[i] = 0;
        }*/
       /* for (int i = 0; i < idlList.size(); i++) {
            CreateBasicDetailFragment createBasicDetailFragment = new CreateBasicDetailFragment();
            createBasicDetailFragment.setArguments(list.get(i));
            fragments.add(createBasicDetailFragment);
        }*/
        /*shareStr = new String[fragments.size()];
        shareImg = new String[fragments.size()];
        notifyDataSetChanged();*/
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        /*Bundle bundle = new Bundle();
        bundle.putInt(CreateBasicDetailActivity.POSITION, position);
        bundle.putSerializable(CreateBasicDetailActivity.CREATE, datas.get(position));
        switch (position % 3) {
            case 0:
                if (isFirstFirst)
                    firstBasicDetailFragment.setData(bundle);
                else
                    isFirstFirst = true;
                break;
            case 1:
                if (isSecondFirst)
                    secondBasicDetailFragment.setData(bundle);
                else
                    isSecondFirst = true;
                break;
            case 2:
                if (isThirdFirst)
                    thirdBasicDetailFragment.setData(bundle);
                else
                    isThirdFirst = true;
                break;
        }*/
        //因为这个方法每次跳转会调用多次，在这里设置一个标示，只执行一次就可以了
       /* if (positionValue[position] == false) {
            //这个方法就是联网获取数据
            getNewsData(position);
            positionValue[position] = true;
        }*/
    }

    public abstract void getNewsData(int position);
   /* @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        ((ViewPager)container).addView(views.get(position));
        //给每个item的view 就是刚才views存放着的view
        return views.get(position);
    }*/


    @Override
    public void startUpdate(ViewGroup container) {
        // TODO Auto-generated method stub
        super.startUpdate(container);
    }

   /* @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }*/
}
