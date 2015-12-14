package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.explore.manage.ManageActivity;
import com.putao.wd.home.adapter.ExploreAdapter;
import com.putao.wd.model.ExploreProduct;
import com.putao.wd.model.PlotDetail;
import com.putao.wd.qrcode.CaptureActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 探索号
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoExploreFragment extends PTWDFragment {
    @Bind(R.id.rl_empty)
    RelativeLayout rl_empty;
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
        setRightTitleColor(Color.WHITE);
        adapter = new ExploreAdapter(mActivity, null);
        rv_content.setAdapter(adapter);
        getDiaryIndex(3, "", "");
        addListener();
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
                getDiaryIndex(0, "", "");
            }
        });
    }

    /**
     * 获得探索号产品列表
     * by yanghx
     * @param slave_device_id 关联设备唯一id，无关注设备时不请求
     * @param start_time      起始时间的时间戳
     * @param end_time        结束时间的时间戳
     */
    private void getDiaryIndex(int slave_device_id, String start_time, String end_time) {
        networkRequest(ExploreApi.getDiaryIndex(String.valueOf(slave_device_id), start_time, end_time),
                new SimpleFastJsonCallback<ArrayList<ExploreProduct>>(ExploreProduct.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<ExploreProduct> result) {
                        Logger.d(result.toString());
                        Log.i("pt", "探索号请求成功");
                        if (null != result) {
                            rv_content.setVisibility(View.VISIBLE);
                            adapter.replaceAll(result);
                        }else {
//                            rl_empty.setVisibility(View.VISIBLE);
                        }
                        //adapter.addAll(result);
                        loading.dismiss();
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

    /**
     * 获取剧情理念详情
     * by yanghx
     * @param plot_id 剧情理念详情id
     */
    private void getPlotDetails(String plot_id) {
        networkRequest(ExploreApi.getPlotDetails(plot_id), new SimpleFastJsonCallback<ArrayList<PlotDetail>>(PlotDetail.class, loading) {
            @Override
            public void onSuccess(String url, ArrayList<PlotDetail> result) {
                Log.i("pt", "剧情理念详情请求成功");
            }
        });
    }


}





