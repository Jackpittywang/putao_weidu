package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.explore.manage.ManageActivity;
import com.putao.wd.home.adapter.ExploreAdapter;
import com.putao.wd.model.Explore;
import com.putao.wd.model.ExploreProduct;
import com.putao.wd.model.PlotDetail;
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
    private void addListener(){
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //getDiaryIndex(0, "", "");
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
                        Logger.i("探索号请求成功");
                        Logger.i("探索号请求结果 = " + result.toString());
                        List<ExploreProduct> exploreProductList = result.getData();
                        if (null != exploreProductList && exploreProductList.size() != 0) {

                            for (int i = 0; i < exploreProductList.size(); i++) {
                                ExploreProduct exploreProduct = exploreProductList.get(i);
                                // type = 1,2,3; data类型分别对应Array,Object,Object
                                switch (exploreProduct.getType()){
                                    case 1:

                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;
                                    default:
                                        break;
                                }


                            }
                        }



//                        if (true) {
//                            rl_empty.setVisibility(View.GONE);
//                            rv_content.setVisibility(View.VISIBLE);
//                            adapter.replaceAll(result);
//                        }else {
//                            rv_content.setVisibility(View.GONE);
//                            rl_empty.setVisibility(View.VISIBLE);
//                        }
                        loading.dismiss();
                    }
                });
    }

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





