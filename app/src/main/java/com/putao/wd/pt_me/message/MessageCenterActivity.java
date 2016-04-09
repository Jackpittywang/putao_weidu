package com.putao.wd.pt_me.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.util.SparseArray;

import com.putao.wd.R;
import com.putao.wd.RedDotReceiver;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;
import com.sunnybear.library.view.viewpager.UnScrollableViewPager;


import java.util.HashMap;

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
    @Bind(R.id.ll_reply)
    TitleItem ll_reply;
    @Bind(R.id.ll_cool)
    TitleItem ll_cool;
    @Bind(R.id.ll_remind)
    TitleItem ll_remind;
    @Bind(R.id.ll_notice)
    TitleItem ll_notice;

    private FragmentPagerAdapter adapter;
    private SparseArray<Fragment> mFragments;
    private HashMap<String, String> mRedDotMap;

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

        //打开预加载 一次就加载所有的fragment
        vp_message.setOffscreenPageLimit(mFragments.size());
        //红点显示
        mRedDotMap = (HashMap<String, String>) mDiskFileCacheHelper.getAsSerializable(RedDotReceiver.MESSAGECENTER + AccountHelper.getCurrentUid());
        if (null != mRedDotMap) {
            if (!TextUtils.isEmpty(mRedDotMap.get(RedDotReceiver.MESSAGECENTER_REPLY)))
                ll_reply.show();
            if (!TextUtils.isEmpty(mRedDotMap.get(RedDotReceiver.MESSAGECENTER_PRAISE)))
                ll_cool.show();
            if (!TextUtils.isEmpty(mRedDotMap.get(RedDotReceiver.MESSAGECENTER_REMIND)))
                ll_remind.show();
            if (!TextUtils.isEmpty(mRedDotMap.get(RedDotReceiver.MESSAGECENTER_NOTICE)))
                ll_notice.show();
        } else
            mRedDotMap = new HashMap<>();
        addListener();
    }

    /**
     * 初始化Fragment
     */
    private void addFragment() {
        mFragments = new SparseArray<>();
        mFragments.put(0, Fragment.instantiate(mContext, ReplyFragment.class.getName()));
        mFragments.put(1, Fragment.instantiate(mContext, PraiseFragment.class.getName()));
        mFragments.put(2, Fragment.instantiate(mContext, RemindFragment.class.getName()));
        mFragments.put(3, Fragment.instantiate(mContext, NotifyFragment.class.getName()));
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
        String messagecenter = "";
        switch (item.getId()) {
            case R.id.ll_reply://回复
                ll_reply.hide();
                messagecenter = RedDotReceiver.MESSAGECENTER_REPLY;
                YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_infocenter_reply);
                break;
            case R.id.ll_cool://赞
                ll_cool.hide();
                messagecenter = RedDotReceiver.MESSAGECENTER_PRAISE;
                YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_infocenter_good);
                break;
            case R.id.ll_remind://提醒
                ll_remind.hide();
                messagecenter = RedDotReceiver.MESSAGECENTER_REMIND;
                YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_infocenter_remind);
                break;
            case R.id.ll_notice://通知
                ll_notice.hide();
                messagecenter = RedDotReceiver.MESSAGECENTER_NOTICE;
                YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_infocenter_notice);
                break;
        }
        mRedDotMap.remove(messagecenter);
        mDiskFileCacheHelper.put(RedDotReceiver.MESSAGECENTER + AccountHelper.getCurrentUid(), mRedDotMap);
        vp_message.setCurrentItem(position, false);
    }

    @Subcriber(tag = RedDotReceiver.MESSAGECENTER)
    private void setDot(String messagecenter) {
        switch (messagecenter) {
            case RedDotReceiver.MESSAGECENTER_REPLY:
                ll_reply.show();
                break;
            case RedDotReceiver.MESSAGECENTER_PRAISE:
                ll_cool.show();
                break;
            case RedDotReceiver.MESSAGECENTER_REMIND:
                ll_remind.show();
                break;
            case RedDotReceiver.MESSAGECENTER_NOTICE:
                ll_notice.show();
                break;
        }
        mRedDotMap.put(messagecenter, messagecenter);
    }

}
