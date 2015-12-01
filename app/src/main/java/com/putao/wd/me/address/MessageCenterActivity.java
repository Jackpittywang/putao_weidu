package com.putao.wd.me.address;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.putao.wd.R;
import com.putao.wd.me.address.adapter.MsgFragmentAdapter;
import com.putao.wd.me.address.adapter.MsgViewPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/*
**create by wangou
**消息中心
 */
public class MessageCenterActivity extends FragmentActivity {

    private ViewPager vp;
    //“直接对xml布局切换”的变量申明
    private MsgViewPagerAdapter vAdapter;
    private List<View> views;

    //“直接对fragment布局切换”的变量申明
    private MsgFragmentAdapter fAdapter;
    private List<Fragment> fragments;

    //加上cursor混动当前页导航提示
    private Bitmap cursor;
    private int offSet;
    private int currentItem;
    private Matrix matrix = new Matrix();
    private int bmWidth;
    private ImageView imageView;
    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);

        //“直接对fragment布局切换”的初始化
        initFragmentView();

        //加上cursor混动当前页导航提示
        imageView=(ImageView)findViewById(R.id.cursor);
        initeCursor();
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {  //当滑动式，顶部的imageView是通过animation缓慢的滑动
                // TODO Auto-generated method stub
                switch (arg0)
                {
                    case 0:
                        if (currentItem == 1)
                        {
                            animation = new TranslateAnimation(
                                    offSet * 2 + bmWidth, 0 , 0, 0);
                        }
                        else if(currentItem == 2)
                        {
                            animation = new TranslateAnimation(
                                    offSet * 4 + 2 * bmWidth, 0, 0, 0);
                        }
                        break;
                    case 1:
                        if (currentItem == 0)
                        {
                            animation = new TranslateAnimation(
                                    0, offSet * 2 + bmWidth, 0, 0);
                        }
                        else if (currentItem == 2)
                        {
                            animation = new TranslateAnimation(4* offSet + 2 * bmWidth, offSet * 2 + bmWidth, 0, 0);
                        }
                        break;
                    case 2:
                        if (currentItem == 0)
                        {
                            animation = new TranslateAnimation(
                                    0, 4 * offSet + 2 * bmWidth, 0, 0);
                        }
                        else if (currentItem == 1)
                        {
                            animation = new TranslateAnimation(
                                    offSet * 2 + bmWidth, 4 * offSet + 2 * bmWidth, 0, 0);
                        }
                }
                currentItem = arg0;

                animation.setDuration(500);
                animation.setFillAfter(true);
                imageView.startAnimation(animation);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    //“直接对fragment布局切换”的初始化
    private void initFragmentView(){
        fragments= new ArrayList<Fragment>();
        fragments.add(new MessageNotifyFragment());
        fragments.add(new MessageReplyFragment());
        fragments.add(new MessagePraiseFragment());
        fAdapter= new MsgFragmentAdapter(this.getSupportFragmentManager(), fragments);
        vp=(ViewPager)findViewById(R.id.viewpager);
        vp.setAdapter(fAdapter);
        vp.setCurrentItem(0);
    }


    private void initeCursor()
    {
        cursor = BitmapFactory.decodeResource(getResources(), R.drawable.img_details_as_steps_cover);
        bmWidth = cursor.getWidth();

        DisplayMetrics dm;
        dm = getResources().getDisplayMetrics();

        offSet = (dm.widthPixels - 3 * bmWidth) / 6;
        matrix.setTranslate(offSet, 0);
        imageView.setImageMatrix(matrix);                                             //需要iamgeView的scaleType为matrix
        currentItem = 0;
    }
}
