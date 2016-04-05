package com.putao.wd.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.home.adapter.CompanionAdapter;
import com.putao.wd.home.adapter.StoreAdapter;
import com.putao.wd.model.Companion;
import com.putao.wd.model.StoreProduct;
import com.putao.wd.model.StoreProductHome;
import com.putao.wd.pt_companion.BlackboardActivity;
import com.putao.wd.pt_store.product.ProductDetailActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.SettingItem;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.animators.ScaleInAnimation;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 精选(首页)
 * Created by zhanghao on 2016/04/05.
 */
public class PutaoCompanionFragment extends PTWDFragment implements OnItemClickListener {
    private CompanionAdapter mCompanionAdapter;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_companion;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        ArrayList<Companion> companions = new ArrayList<>();
        companions.add(new Companion());
        companions.add(new Companion());
        companions.add(new Companion());
        companions.add(new Companion());
        mCompanionAdapter = new CompanionAdapter(mActivity, companions);
        rv_content.setAdapter(mCompanionAdapter);
        addListener();
    }

    private void addListener() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ptl_refresh.refreshComplete();
            }
        });
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rv_content.noMoreLoading();
            }
        });
        mCompanionAdapter.setOnItemClickListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onItemClick(Serializable serializable, int position) {
        if (0 == position) startActivity(BlackboardActivity.class);
    }
}


