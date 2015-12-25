package com.putao.wd.me.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;
import com.sunnybear.library.view.viewpager.UnScrollableViewPager;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 消息中心
 * Created by wangou on 2015/12/1.
 */
public class MessageCenterActivity extends PTWDActivity implements TitleBar.OnTitleItemSelectedListener {
    @Bind(R.id.rv_messages)
    LoadMoreRecyclerView rv_messages;//消息列表
    @Bind(R.id.ll_title_bar)
    TitleBar ll_title_bar;//顶部导航菜单
    @Bind(R.id.vp_message)
    UnScrollableViewPager vp_message;

    private FragmentPagerAdapter adapter;
    private ArrayList<BasicFragment> listFragmentsa;

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
                return listFragmentsa.get(position);
            }

            @Override
            public int getCount() {
                return listFragmentsa.size();
            }
        };
        vp_message.setAdapter(adapter);

        addListener();
    }

    /**
     * 初始化Fragment
     */
    private void addFragment() {
        listFragmentsa = new ArrayList<>();
        MsgNotifyFragment notifyFragment = new MsgNotifyFragment();
        MsgReplyFragment replyFragment = new MsgReplyFragment();
        MsgPraiseFragment praiseFragment = new MsgPraiseFragment();
        listFragmentsa.add(notifyFragment);
        listFragmentsa.add(replyFragment);
        listFragmentsa.add(praiseFragment);
    }

    /**
     * 添加监听器
     */
    private void addListener() {
//        rv_messages.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//
//            }
//        });
        ll_title_bar.setOnTitleItemSelectedListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        switch (item.getId()) {
            case R.id.ll_notice://通知
                vp_message.setCurrentItem(0);
                ToastUtils.showToastShort(this, "通知");
                break;
            case R.id.ll_reply://回复
                vp_message.setCurrentItem(1);
                ToastUtils.showToastShort(this, "回复");
                break;
            case R.id.ll_cool://赞
                vp_message.setCurrentItem(2);
                ToastUtils.showToastShort(this, "赞");
                break;
        }
    }
}
