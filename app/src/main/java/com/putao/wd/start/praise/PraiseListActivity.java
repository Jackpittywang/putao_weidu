package com.putao.wd.start.praise;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.dto.PraiseListItem;
import com.putao.wd.model.Cool;
import com.putao.wd.model.CoolEventList;
import com.putao.wd.model.CoolList;
import com.putao.wd.start.praise.adapter.PraiseListAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 点赞列表
 * Created by wangou on 2015/12/4.
 */
public class PraiseListActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.rv_praiselist)
    LoadMoreRecyclerView rv_praiselist;
    @Bind(R.id.tv_nomore)
    TextView tv_nomore;
    @Bind(R.id.ll_praiselist)
    LinearLayout ll_praiselist;

    private PraiseListAdapter adapter;
    private String action_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_praise_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        adapter = new PraiseListAdapter(mContext, null);
        rv_praiselist.setAdapter(adapter);
        Bundle bundle = getIntent().getExtras();
        action_id = bundle.getString("action_id");

        getCoolList();
        addListener();
    }

    private void addListener() {
        rv_praiselist.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            public void onLoadMore() {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        getCoolList();
                    }
                }, 3000L);
            }
        });
    }

    /**
     * 获取赞列表
     */
    private void getCoolList() {
        networkRequest(StartApi.getCoolList(action_id), new SimpleFastJsonCallback<Cool>(Cool.class, loading) {
            @Override
            public void onSuccess(String url, Cool result) {
                Logger.i("赞列表获取成功");
                CoolEventList eventCoolList = result.getEventCoolList();
                ArrayList<CoolList> user_list = eventCoolList.getUser_list();
                if (user_list.size() != 0) {
                    adapter.replaceAll(user_list);
                } else {
                    rv_praiselist.noMoreLoading();
                }
                loading.dismiss();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }

//    private void initPraiseList(){
//        if(this.initPraiseListData().size() != 0) {
//            this.ll_praiselist.setVisibility(View.VISIBLE);
//            this.tv_nomore.setVisibility(View.GONE);
//            PraiseListAdapter praiseListAdapter = new PraiseListAdapter(mContext, this.initPraiseListData());
//            this.brv_praiselist.setAdapter(praiseListAdapter);
//        } else {
//            this.ll_praiselist.setVisibility(View.GONE);
//            this.tv_nomore.setVisibility(View.VISIBLE);
//        }
//    }

//    private List<PraiseListItem> initPraiseListData(){
//        List<PraiseListItem> praiseList=new ArrayList<>();
//        PraiseListItem item;
//
//        for (int i = 0; i < 20; i++) {
//            item=new PraiseListItem();
//            item.setUserIconUrl("https://static.pexels.com/photos/5854/sea-woman-legs-water-medium.jpg");
//            item.setUsername("username" + i);
//            item.setPraiseCount(i+"");
//            if(i%2==0)
//                item.setUserattr("游客");
//            else if(i==9){
//                item.setUserattr("用户本人");
//            }
//            else{
//                item.setUserattr("普通用户");
//            }
//            praiseList.add(item);
//        }
//
//        return praiseList;
//    }

}
