package com.putao.wd.home.praise;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.PraiseListItem;
import com.putao.wd.home.praise.adapter.PraiseListAdapter;
import com.putao.wd.me.activities.adapter.MyActivitiesAdapter;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * 点赞列表
 * Created by wangou on 2015/12/4.
 */
public class PraiseListActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.brv_praiselist)
    LoadMoreRecyclerView brv_praiselist;
    @Bind(R.id.tv_nomore)
    TextView tv_nomore;
    @Bind(R.id.ll_acitivites)
    LinearLayout ll_acitivites;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_praise_list;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        addListener();
        this.initPraiseList();
    }

    private void addListener() {
        this.brv_praiselist.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            public void onLoadMore() {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        PraiseListActivity.this.brv_praiselist.noMoreLoading();
                    }
                }, 3000L);
            }
        });
    }

    private void initPraiseList(){
        if(this.initPraiseListData().size() != 0) {
            this.ll_acitivites.setVisibility(View.VISIBLE);
            this.tv_nomore.setVisibility(View.GONE);
            PraiseListAdapter praiseListAdapter = new PraiseListAdapter(this, this.initPraiseListData());
            this.brv_praiselist.setAdapter(praiseListAdapter);
        } else {
            this.ll_acitivites.setVisibility(View.GONE);
            this.tv_nomore.setVisibility(View.VISIBLE);
        }
    }

    private List<PraiseListItem> initPraiseListData(){
        return null;
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
