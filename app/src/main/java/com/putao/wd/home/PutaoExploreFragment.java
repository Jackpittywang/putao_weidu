package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.explore.manage.ManageActivity;
import com.putao.wd.home.adapter.ExploreAdapter;
import com.putao.wd.model.Explore;
import com.putao.wd.model.ExploreProduct;
import com.putao.wd.model.ExploreProductPlot;
import com.putao.wd.model.ExploreProductDetail;
import com.putao.wd.qrcode.CaptureActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.List;

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
    private int page = 1;

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
        getDiaryIndex();
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
    private void addListener() {
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getDiaryIndex();
            }
        });
    }

    /**
     * 获得探索号产品列表
     */
    private void getDiaryIndex() {
        networkRequest(ExploreApi.getDiaryIndex(String.valueOf(page)),
                new SimpleFastJsonCallback<Explore>(Explore.class, loading) {
                    @Override
                    public void onSuccess(String url, Explore result) {
                        Logger.i("探索号请求结果 = " + result.toString());
                        if (result.getTotal_page() == 1 || result.getCurrent_page() != result.getTotal_page()) {
                            List<ExploreProduct> datas = result.getData();
                            for (ExploreProduct data : datas) {
                                switch (data.getType()) {
                                    case 1:
                                        data.setDetails(parseDetail(data.getData()));
                                        break;
                                    default:
                                        data.setPlot(parsePlot(data.getData()));
                                        break;
                                }
                            }
                            rl_empty.setVisibility(View.GONE);
                            rv_content.setVisibility(View.VISIBLE);
                            adapter.replaceAll(datas);
                            rv_content.loadMoreComplete();
                            page++;
                        }else {
                            rv_content.noMoreLoading();
                        }
                        loading.dismiss();
                    }
                });
    }

    /**
     * 解析详情
     */
    private List<ExploreProductDetail> parseDetail(String json) {
        return JSON.parseArray(json, ExploreProductDetail.class);
    }

    /**
     * 解析情节
     */
    private ExploreProductPlot parsePlot(String json) {
        return JSON.parseObject(json, ExploreProductPlot.class);
    }

//    /**
//     * 获取剧情理念详情
//     * by yanghx
//     *
//     * @param plot_id 剧情理念详情id
//     */
//    private void getPlotDetails(String plot_id) {
//        networkRequest(ExploreApi.getPlotDetails(plot_id), new SimpleFastJsonCallback<ArrayList<PlotDetail>>(PlotDetail.class, loading) {
//            @Override
//            public void onSuccess(String url, ArrayList<PlotDetail> result) {
//                Log.i("pt", "剧情理念详情请求成功");
//            }
//        });
//    }

}





