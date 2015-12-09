package com.putao.wd.me.actions;

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
import com.putao.wd.dto.MyActionsItem;
import com.putao.wd.me.actions.adapter.MyActionsAdapter;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView.OnLoadMoreListener;
import java.util.ArrayList;
import java.util.List;
/**
 * 我参加的活动
 * Created by wangou on 2015/12/4.
 */
public class MyActionsActivity extends PTWDActivity<GlobalApplication> implements OnClickListener {
    @Bind(R.id.brv_acitionlist)
    LoadMoreRecyclerView brv_acitionlist;
    @Bind(R.id.tv_nomore)
    TextView tv_nomore;
    @Bind(R.id.ll_acitions)
    LinearLayout ll_acitions;

    public MyActionsActivity() {
    }

    protected int getLayoutId() {
        return R.layout.activity_my_actions;
    }

    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        this.addNavigation();
        this.addListener();
        this.initActivityList();
    }

    private void addListener() {
        this.brv_acitionlist.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        MyActionsActivity.this.brv_acitionlist.noMoreLoading();
                    }
                }, 3000L);
            }
        });
    }

    private void initActivityList() {
        if(this.initActivityData().size() != 0) {
            this.ll_acitions.setVisibility(View.VISIBLE);
            this.tv_nomore.setVisibility(View.GONE);
            MyActionsAdapter myActivitiesAdapter = new MyActionsAdapter(mContext, this.initActivityData());
            this.brv_acitionlist.setAdapter(myActivitiesAdapter);
        } else {
            this.ll_acitions.setVisibility(View.GONE);
            this.tv_nomore.setVisibility(View.VISIBLE);
        }

    }

    private List<MyActionsItem> initActivityData() {
        ArrayList list = new ArrayList();

        for(int i = 0; i < 10; ++i) {
            MyActionsItem item = new MyActionsItem();
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
