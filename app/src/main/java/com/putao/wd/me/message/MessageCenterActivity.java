package com.putao.wd.me.message;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.message.adapter.MsgFragmentAdapter;
import com.putao.wd.me.message.adapter.MsgViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/*
**消息中心
**create by wangou
**
 */
public class MessageCenterActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.vp_content)
    ViewPager vp_content;
    @Bind(R.id.iv_cursor)
    ImageView iv_cursor;


    //“直接对xml布局切换”的变量申明
    private MsgViewPagerAdapter vAdapter;
    private List<View> views;
    private Animation animation;
    //“直接对fragment布局切换”的变量申明
    private MsgFragmentAdapter fAdapter;
    private List<Fragment> fragments;

    //加上cursor混动当前页导航提示
    private Bitmap cursor;
    private int offSet;
    private int currentItem;
    private Matrix matrix = new Matrix();
    private int bmWidth;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        //“直接对fragment布局切换”的初始化
        initFragmentView();

        //加上cursor混动当前页导航提示
        initeCursor();
        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {  //当滑动式，顶部的imageView是通过animation缓慢的滑动
                // TODO Auto-generated method stub
                setAnimation(arg0);

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
        vp_content.setAdapter(fAdapter);
        vp_content.setCurrentItem(0);
    }

    @Override
    public void onLeftAction() {
        finish();
    }

    private void initeCursor()
    {
        cursor = BitmapFactory.decodeResource(getResources(), R.drawable.img_details_as_steps_cover);
        bmWidth = cursor.getWidth();

        DisplayMetrics dm;
        dm = getResources().getDisplayMetrics();

        offSet = (dm.widthPixels - 3 * bmWidth) / 6;
        matrix.setTranslate(offSet, 0);
        iv_cursor.setImageMatrix(matrix);                                             //需要iamgeView的scaleType为matrix
        currentItem = 0;
    }
    private void setAnimation(int index){
        switch (index)
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
        currentItem = index;

        animation.setDuration(500);
        animation.setFillAfter(true);
        iv_cursor.startAnimation(animation);
    }
    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @OnClick({R.id.tv_praise,R.id.tv_reply,R.id.tv_notify})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_notify:setAnimation(0);vp_content.setCurrentItem(0);break;
            case R.id.tv_reply:setAnimation(1);vp_content.setCurrentItem(1);break;
            case R.id.tv_praise:setAnimation(2);vp_content.setCurrentItem(2);break;
        }
    }
}
