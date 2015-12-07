package com.putao.wd.me.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.MyActivitiesItem;
import com.putao.wd.me.activities.adapter.MyActivitiesAdapter;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView.OnLoadMoreListener;
import java.util.ArrayList;
import java.util.List;
/**
 * 我参加的活动
 * Created by wangou on 2015/12/4.
 */
public class MyActivitiesActivity extends PTWDActivity<GlobalApplication> implements OnClickListener {
    @Bind(R.id.brv_activitylist)
    LoadMoreRecyclerView brv_activitylist;
    @Bind(R.id.tv_nomore)
    TextView tv_nomore;
    @Bind(R.id.ll_acitivites)
    LinearLayout ll_acitivites;

    public MyActivitiesActivity() {
    }

    protected int getLayoutId() {
        return R.layout.activity_my_activities;
    }

    protected void onViewCreateFinish(Bundle saveInstanceState) {
        this.addNavgation();
        this.addListener();
        this.initActivityList();
    }

    private void addListener() {
        this.brv_activitylist.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        MyActivitiesActivity.this.brv_activitylist.noMoreLoading();
                    }
                }, 3000L);
            }
        });
    }

    private void initActivityList() {
        if(this.initActivityData().size() != 0) {
            this.ll_acitivites.setVisibility(View.VISIBLE);
            this.tv_nomore.setVisibility(View.GONE);
            MyActivitiesAdapter myActivitiesAdapter = new MyActivitiesAdapter(this, this.initActivityData());
            this.brv_activitylist.setAdapter(myActivitiesAdapter);
        } else {
            this.ll_acitivites.setVisibility(View.GONE);
            this.tv_nomore.setVisibility(View.VISIBLE);
        }

    }

    private List<MyActivitiesItem> initActivityData() {
        ArrayList list = new ArrayList();

        for(int i = 0; i < 10; ++i) {
            MyActivitiesItem item = new MyActivitiesItem();
            if(i % 2 == 1) {
                item.setStatus("体验");
            } else {
                item.setStatus("聚会");
            }

            item.setActionIcon("https://static.pexels.com/photos/5854/sea-woman-legs-water-medium.jpg");
            item.setIntroduction(i + "活动简介字数限制为四十字，这里完整显示全部简介的信息这里是两行活动简介字数限制为四十字，这里完整显示全部简介的信息这里是两行");
            item.setTitle(i + "活动标题最多15个汉字限制2行活动简介字数限制为四十字，这里完整显示全部简介的信息这里是两行");
            list.add(item);
        }

        return list;
    }

    protected String[] getRequestUrls() {
        return new String[0];
    }

    public void onClick(View v) {
    }
}
