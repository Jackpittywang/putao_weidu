package com.putao.wd.me.message;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.message.adapter.MsgFragmentAdapter;

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
    ViewPager vp_content;   //viewpager
    @Bind(R.id.iv_cursor)
    ImageView iv_cursor;    //导航条
    @Bind(R.id.tv_notify)
    TextView tv_notify;     //通知
    @Bind(R.id.tv_reply)
    TextView tv_reply;      //回复
    @Bind(R.id.tv_praise)
    TextView tv_praise;     //赞

    //-------------页面相关信息----------------------
    private List<Fragment> fragments;           //fragment集合
    private MsgFragmentAdapter fAdapter;        //fragment集合适配器

    //-------------切换效果相关变量---------------------
    private Animation animation;                //
    private int offSet;                         //导航条起始点
    private Matrix matrix = new Matrix();
    private int bmWidth;                        //导航条宽度
    private ViewGroup.LayoutParams imagepara;   //导航条信息（为了获得宽度）
    private int currentItem;                    //当前页

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();

        //初始化fragment集合
        initFragmentView();

        //获得导航条信息
        imagepara= iv_cursor.getLayoutParams();

        //加上cursor混动当前页导航提示
        initeCursor();

        //导航条默认为第一页的位置
        setAnimation(-1);
        //滑动切换页面
        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {  //当滑动时，顶部的imageView是通过animation缓慢的滑动
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
    //初始化fragment集合
    private void initFragmentView(){
        fragments= new ArrayList<Fragment>();
        fragments.add(new MessageNotifyFragment());
        fragments.add(new MessageReplyFragment());
        fragments.add(new MessagePraiseFragment());
        fAdapter= new MsgFragmentAdapter(this.getSupportFragmentManager(), fragments);
        vp_content.setAdapter(fAdapter);
        vp_content.setCurrentItem(0);
    }


    //初始化滑动导航条图标
    private void initeCursor()
    {
        bmWidth=imagepara.width*5/3;
        DisplayMetrics dm;
        dm = getResources().getDisplayMetrics();      //获得屏幕参数

        offSet = (dm.widthPixels) / 6+5;
        matrix.setTranslate(offSet, 0);
        iv_cursor.setImageMatrix(matrix);             //需要iamgeView的scaleType为matrix
        currentItem = 0;
    }

    /**
     * 根据滑动页面或点击菜单来切换页面和实现滑动导航条效果
     * “通知”的位置：offSet+5
     * “回复”的位置：offSet + bmWidth
     * “赞”的位置：offSet + 2 * bmWidth-15
     */
    private void setAnimation(int index){
        switch (index)
        {
            case 0://第0页：通知
                if (currentItem == 1)
                {
                    animation = new TranslateAnimation(offSet + bmWidth,offSet+5 , 0, 0);
                }
                else if(currentItem == 2)
                {
                    animation = new TranslateAnimation(offSet + 2 * bmWidth-15, offSet+5, 0, 0);
                }
                tv_notify.setTextColor(Color.parseColor("#9851c9"));
                tv_reply.setTextColor(Color.parseColor("#959595"));
                tv_praise.setTextColor(Color.parseColor("#959595"));
                break;
            case 1://第1页：回复
                if (currentItem == 0)
                {
                    animation = new TranslateAnimation(offSet+5, offSet+ bmWidth, 0, 0);
                }
                else if (currentItem == 2)
                {
                    animation = new TranslateAnimation(offSet + 2 * bmWidth-15, offSet  + bmWidth, 0, 0);
                }
                tv_reply.setTextColor(Color.parseColor("#9851c9"));
                tv_notify.setTextColor(Color.parseColor("#959595"));
                tv_praise.setTextColor(Color.parseColor("#959595"));
                break;
            case 2://第2页面：赞
                if (currentItem == 0)
                {
                    animation = new TranslateAnimation(offSet+5, offSet + 2 * bmWidth, 0, 0);
                }
                else if (currentItem == 1)
                {
                    animation = new TranslateAnimation(offSet + bmWidth, offSet + 2 * bmWidth-15, 0, 0);
                }
                tv_praise.setTextColor(Color.parseColor("#9851c9"));
                tv_notify.setTextColor(Color.parseColor("#959595"));
                tv_reply.setTextColor(Color.parseColor("#959595"));
                break;
            default:
                animation = new TranslateAnimation(0, offSet, 0, 0);

        }
        if(index==-1) {
            currentItem = 0;
        }
        else{
            currentItem = index;
        }


        animation.setDuration(500);
        animation.setFillAfter(true);
        iv_cursor.startAnimation(animation);
    }


    //顶部菜单点击事件
    @OnClick({R.id.iv_back,R.id.tv_praise,R.id.tv_reply,R.id.tv_notify})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back://返回退出当前页面
                finish();break;
            case R.id.tv_notify://通知
                setAnimation(0);vp_content.setCurrentItem(0);
                break;
            case R.id.tv_reply://回复
                setAnimation(1);vp_content.setCurrentItem(1);
                break;
            case R.id.tv_praise:setAnimation(2);//赞
                vp_content.setCurrentItem(2);
                break;
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}