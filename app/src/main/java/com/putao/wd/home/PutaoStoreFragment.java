package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.putao.wd.MainActivity;
import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.dto.ProductItem;
import com.putao.wd.home.adapter.ProductAdapter;
import com.putao.wd.home.adapter.StoreBannerAdapter;
import com.putao.wd.model.StoreBanner;
import com.putao.wd.model.StoreHome;
import com.putao.wd.model.StoreProduct;
import com.putao.wd.store.product.ProductDetailActivity;
import com.putao.wd.store.shopping.ShoppingCarActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;
import com.sunnybear.library.view.recycler.RecyclerViewHeader;
import com.sunnybear.library.view.viewpager.BannerLayout;
import com.sunnybear.library.view.viewpager.BannerViewPager;

import java.util.List;

import butterknife.Bind;

/**
 * 葡商城
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoStoreFragment extends PTWDFragment {

    @Bind(R.id.rvh_header)
    RecyclerViewHeader mHeader;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.bl_banner)
    BannerLayout bl_banner;
    private boolean isStop;//广告栏是否被停止

    private ProductAdapter adapter;
    private StoreBannerAdapter bannerAdapter;
    private List<ProductItem> products;
    private List<StoreBanner> banners;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        Logger.d(MainActivity.TAG, "PutaoStoreFragment启动");
        addNavigation();
        setMainTitleColor(Color.WHITE);
        mHeader.attachTo(rv_content, true);
        adapter = new ProductAdapter(mActivity, null);
        rv_content.setAdapter(adapter);
        addListener();
        //广告位
        getStoreHome();

        refresh();
    }


    /**
     * 获取葡商城首页信息
     */
    private void getStoreHome() {
        networkRequest(StoreApi.getStoreHome(), new SimpleFastJsonCallback<StoreHome>(StoreHome.class, loading) {
            @Override
            public void onSuccess(String url, StoreHome result) {
                Logger.d(result.toString());
                //初始化商品列表
                if (result.getProduct() != null)
                    if (result.getProduct().getData() != null)
                        adapter.addAll(result.getProduct().getData());
                if (banners == null) {
                    //初始化广告位
                    banners = result.getBanner();
                    bannerAdapter = new StoreBannerAdapter(mActivity, result.getBanner(), new BannerViewPager.OnPagerClickListenr() {
                        @Override
                        public void onPagerClick(int position) {
                            //ToastUtils.showToastLong(mActivity, "点击第" + position + "项");
                        }
                    });
                    bl_banner.setAdapter(bannerAdapter);
                    bl_banner.setOffscreenPageLimit(banners.size());//缓存页面数
                }
            }
        });
    }

    /**
     * 刷新方法
     */
    private void refresh() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                getStoreHome();
                loading.dismiss();
                ptl_refresh.refreshComplete();
            }
        });
    }

    private void addListener() {
        rv_content.setOnItemClickListener(new OnItemClickListener<StoreProduct>() {
            @Override
            public void onItemClick(StoreProduct storeProduct, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(ProductDetailActivity.PRODUCT_ID, storeProduct.getId());
                startActivity(ProductDetailActivity.class, bundle);
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        if (isStop)
            isStop = bl_banner.startAutoScroll();
        loading.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
        isStop = bl_banner.stopAutoScroll();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onRightAction() {
        startActivity(ShoppingCarActivity.class);
    }
}
