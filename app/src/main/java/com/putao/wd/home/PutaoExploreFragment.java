package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.explore.manage.ManageActivity;
import com.putao.wd.home.adapter.ExploreAdapter;
import com.putao.wd.model.ExploreProduct;
import com.putao.wd.qrcode.CaptureActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 探索号
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoExploreFragment extends PTWDFragment {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;

    private ExploreAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        setMainTitleColor(Color.WHITE);
        adapter = new ExploreAdapter(mActivity, null);
        rv_content.setAdapter(adapter);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onLeftAction() {
        startActivity(CaptureActivity.class);
    }

    @Override
    public void onRightAction() {
        startActivity(ManageActivity.class);
    }

    /**
     * 添加监听器
     */
    private void addListener(){
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getExploreList(0, "", "", false, false);
            }
        });
    }

    /**
     * 获得探索号产品列表
     * by yanghx
     */
    private void getExploreList(int device_id, String start_time, String end_time, final boolean isRefresh, final boolean isLoadMore) {
        networkRequest(ExploreApi.getDiaryIndex(String.valueOf(device_id), start_time, end_time),
                new SimpleFastJsonCallback<ArrayList<ExploreProduct>>(ExploreProduct.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<ExploreProduct> result) {
                        Logger.d(result.toString());
                        Log.i("pt", "探索号请求成功");
                        //adapter.addAll(result);
                    }
                });
    }

//    private List<ExploreItem> getTestData() {
//        List<ExploreItem> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            ExploreItem exploreItem = new ExploreItem();
//            if (i == 1 || i == 3 || i == 5) {
//                exploreItem.setIsMixed(true);
//                switch (i) {
//                    case 1:
//                        exploreItem.setIconNum(1);
//                        break;
//                    case 3:
//                        exploreItem.setIconNum(4);
//                        break;
//                    case 5:
//                        exploreItem.setIconNum(9);
//                        break;
//                    default:
//                        break;
//                }
//            } else {
//                exploreItem.setIsMixed(false);
//            }
//            if (i < 4) {
//                exploreItem.setDate("2015-12-10");
//            } else if (i >= 4 && i < 7) {
//                exploreItem.setDate("2015-12-09");
//            } else {
//                exploreItem.setDate("2015-12-08");
//            }
//            exploreItem.setSkill_name("技能" + i);
//            exploreItem.setContent("这里显示每个关卡背后的教育理念的主要文字，大概是一句话左右。" +
//                    "完成多个关卡，则以分号隔开显示，一直向下展示。这里显示每个关卡背后的教育理念的主要文字，" +
//                    "大概是一句话左右。完成多个关卡，则以分号隔开显示，一直向下展示。" + i);
//            list.add(exploreItem);
//        }
//        return list;
//    }

}





