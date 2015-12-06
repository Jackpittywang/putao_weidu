package com.putao.wd.home.apply;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.ApplyListItem;
import com.putao.wd.dto.PraiseListItem;
import com.putao.wd.home.apply.adapter.ApplyListAdapter;
import com.putao.wd.home.praise.adapter.PraiseListAdapter;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 活动详情
 * Created by wango on 2015/12/4.
 *
 */
public class ApplyListActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.brv_applylist)
    LoadMoreRecyclerView brv_applylist;
    @Bind(R.id.tv_nomore)
    TextView tv_nomore;
    @Bind(R.id.ll_applylist)
    LinearLayout ll_applylist;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_list;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        addListener();
        this.initPraiseList();
    }

    private void addListener() {
        this.brv_applylist.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            public void onLoadMore() {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        ApplyListActivity.this.brv_applylist.noMoreLoading();
                    }
                }, 3000L);
            }
        });
    }

    private void initPraiseList(){
        if(this.initApplyListData().size() != 0) {
            this.ll_applylist.setVisibility(View.VISIBLE);
            this.tv_nomore.setVisibility(View.GONE);
            ApplyListAdapter applyListAdapter = new ApplyListAdapter(mContext, this.initApplyListData());
            this.brv_applylist.setAdapter(applyListAdapter);
        } else {
            this.ll_applylist.setVisibility(View.GONE);
            this.tv_nomore.setVisibility(View.VISIBLE);
        }
    }

    private List<ApplyListItem> initApplyListData(){
        List<ApplyListItem> applyList=new ArrayList<>();
        ApplyListItem item;

        for (int i = 0; i < 20; i++) {
            item=new ApplyListItem();
            item.setUserIconUrl("https://static.pexels.com/photos/5854/sea-woman-legs-water-medium.jpg");
            item.setUsername("username" + i);
            if(i%2==0)
                item.setUserattr("游客");
            else if(i==9){
                item.setUserattr("用户本人");
            }
            else{
                item.setUserattr("普通用户");
            }
            applyList.add(item);
        }

        return applyList;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
