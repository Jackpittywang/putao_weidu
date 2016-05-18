package com.putao.wd.pt_me.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.putao.ptx.push.core.GPushCallback;
import com.putao.wd.GPushMessageReceiver;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.util.PreferenceUtils;
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
    private boolean[] mRedDots;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        addFragment();
        mRedDots = new boolean[]{false, false, false, false};
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
        mRedDots = PreferenceUtils.getValue(GPushCallback.MESSAGECENTER + AccountHelper.getCurrentUid(), mRedDots);
        if (mRedDots[0]) {
            mRedDots[0] = false;
            PreferenceUtils.save(GPushCallback.MESSAGECENTER + AccountHelper.getCurrentUid(), mRedDots);
            EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_ME_TAB);
        }
        if (mRedDots[1]) ll_cool.show();
        if (mRedDots[2]) ll_remind.show();
        if (mRedDots[3]) ll_notice.show();
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
                YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_infocenter_reply);
                break;
            case R.id.ll_cool://赞
                ll_cool.hide();
                YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_infocenter_good);
                break;
            case R.id.ll_remind://提醒
                ll_remind.hide();
                YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_infocenter_remind);
                break;
            case R.id.ll_notice://通知
                ll_notice.hide();
                YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_infocenter_notice);
                break;
        }
        mRedDots[position] = false;
        PreferenceUtils.save(GPushCallback.MESSAGECENTER + AccountHelper.getCurrentUid(), mRedDots);
        vp_message.setCurrentItem(position, false);
        EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_ME_TAB);

    }

    @Subcriber(tag = GPushCallback.MESSAGECENTER)
    private void setDot(String messagecenter) {
        switch (messagecenter) {
            case GPushCallback.MESSAGECENTER_REPLY:
                mRedDots[0] = true;
                ll_reply.show();
                break;
            case GPushCallback.MESSAGECENTER_PRAISE:
                mRedDots[1] = true;
                ll_cool.show();
                break;
            case GPushCallback.MESSAGECENTER_REMIND:
                mRedDots[2] = true;
                ll_remind.show();
                break;
            case GPushCallback.MESSAGECENTER_NOTICE:
                mRedDots[3] = true;
                ll_notice.show();
                break;
        }
        PreferenceUtils.save(GPushCallback.MESSAGECENTER + AccountHelper.getCurrentUid(), mRedDots);
    }
}
