package com.putao.wd.home;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.home.adapter.StoreAdapter;
import com.putao.wd.model.StoreProduct;
import com.putao.wd.model.StoreProductHome;
import com.putao.wd.store.product.ProductDetailActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

import java.util.List;

import butterknife.Bind;

/**
 * 精选
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoStoreFragment extends PTWDFragment {
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;

    private StoreAdapter adapter;

    private int currentpage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();

        adapter = new StoreAdapter(mActivity, null);
        rv_content.setAdapter(adapter);
        addListener();

        getStoreHome();
    }

    /**
     * 加载商品列表
     */
    private void getStoreHome() {
        networkRequest(StoreApi.getStoreHome(String.valueOf(currentpage)),
                new SimpleFastJsonCallback<StoreProductHome>(StoreProductHome.class, loading) {
                    @Override
                    public void onSuccess(String url, StoreProductHome result) {
                        List<StoreProduct> products = result.getData();
                        if (products != null && products.size() > 0)
                            adapter.addAll(products);
                        if (result.getCurrent_page() != result.getTotal_page() && result.getTotal_page() != 0) {
                            currentpage++;
                            rv_content.loadMoreComplete();
                        } else rv_content.noMoreLoading();
                        loading.dismiss();
                    }
                });
    }

    /**
     * 刷新商品列表
     */
    private void refreshStoreHome() {
        currentpage = 1;
        networkRequest(StoreApi.getStoreHome(String.valueOf(currentpage)),
                new SimpleFastJsonCallback<StoreProductHome>(StoreProductHome.class, loading) {
                    @Override
                    public void onSuccess(String url, StoreProductHome result) {
                        List<StoreProduct> products = result.getData();
                        if (products != null && products.size() > 0)
                            adapter.replaceAll(products);
                        if (result.getCurrent_page() != result.getTotal_page() && result.getTotal_page() != 0) {
                            currentpage++;
                            rv_content.loadMoreComplete();
                        } else rv_content.noMoreLoading();
                        ptl_refresh.refreshComplete();
                        loading.dismiss();
                    }
                });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void addListener() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshStoreHome();
            }
        });
        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getStoreHome();
            }
        });
        rv_content.setOnItemClickListener(new OnItemClickListener<StoreProduct>() {
            @Override
            public void onItemClick(StoreProduct product, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(ProductDetailActivity.BUNDLE_PRODUCT_ID, product.getId());
                bundle.putString(ProductDetailActivity.BUNDLE_PRODUCT_ICON, product.getIcon());
                startActivity(ProductDetailActivity.class, bundle);
            }
        });
    }
}


//public class PutaoStoreFragment extends PTWDFragment {
//
//    @Bind(R.id.rvh_header)
//    RecyclerViewHeader mHeader;
//    @Bind(R.id.ptl_refresh)
//    PullToRefreshLayout ptl_refresh;
//    @Bind(R.id.rv_content)
//    LoadMoreRecyclerView rv_content;
//    @Bind(R.id.bl_banner)
//    BannerLayout bl_banner;
//    private boolean isStop;//广告栏是否被停止
//
//    private ProductAdapter adapter;
//    private StoreBannerAdapter bannerAdapter;
//
//    private int currentPage = 1;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_store;
//    }
//
//    @Override
//    public void onViewCreatedFinish(Bundle savedInstanceState) {
//        addNavigation();
//        setMainTitleColor(Color.WHITE);
//        mHeader.attachTo(rv_content, true);
//
////        mIndicatorHelper = IndicatorHelper.getInstance(mActivity, navigation_bar.getRightView());
//        adapter = new ProductAdapter(mActivity, null);
//        rv_content.setAdapter(adapter);
//        addListener();
//        //广告位
//        getStoreHome();
//        refresh();
//    }
//
//    /**
//     * 获取葡商城首页信息
//     */
//    private void getStoreHome() {
////        networkRequest(StoreApi.getStoreHome(String.valueOf(currentPage)),
////                new SimpleFastJsonCallback<StoreProductHome>(StoreProductHome.class, loading) {
////                    @Override
////                    public void onSuccess(String url, StoreProductHome result) {
////                        List<StoreProduct> products = result.getData();
////                        if (products != null && products.size() > 0)
////                            adapter.addAll(products);
////                        if (result.getTotal_page() != 0 && result.getTotal_page() != result.getCurrent_page()) {
////                            currentPage++;
////                            rv_content.loadMoreComplete();
////                        } else rv_content.noMoreLoading();
////                    }
////                });
//        networkRequest(StoreApi.getStoreHome(String.valueOf(currentPage)), new SimpleFastJsonCallback<StoreHome>(StoreHome.class, loading) {
//            @Override
//            public void onSuccess(String url, StoreHome result) {
//                final List<StoreBanner> banners = result.getBanner();
//                //初始化广告位
//                if (banners != null && banners.size() > 0) {
//                    bannerAdapter = new StoreBannerAdapter(mActivity, banners, new BannerViewPager.OnPagerClickListenr() {
//                        @Override
//                        public void onPagerClick(int position) {
//                            StoreBanner banner = banners.get(position);
//                            String project_id = banner.getItem_id();
//                            if (!StringUtils.isEmpty(project_id)) {
//                                Bundle bundle = new Bundle();
//                                bundle.putString(ProductDetailActivity.BUNDLE_PRODUCT_ID, project_id);
//                                bundle.putString(ProductDetailActivity.BUNDLE_PRODUCT_IMAGE, banner.getImage());
//                                startActivity(ProductDetailActivity.class, bundle);
//                            }
//                        }
//                    });
//                    bl_banner.setAdapter(bannerAdapter);
//                    bl_banner.setOffscreenPageLimit(banners.size());//缓存页面数
//                }
//
//                //初始化商品列表
//                StoreProductHome productHome = result.getProduct();
//                adapter.addAll(productHome.getData());
//                if (productHome.getCurrent_page() != productHome.getTotal_page()) {
//                    rv_content.loadMoreComplete();
//                    currentPage++;
//                } else rv_content.noMoreLoading();
//                loading.dismiss();
//            }
//        });
//    }
//
//    /**
//     * 获得购物车数量
//     */
//    private void getCartCount() {
//        networkRequest(StoreApi.getCartCount(), new SimpleFastJsonCallback<String>(String.class, loading) {
//            @Override
//            public void onSuccess(String url, String result) {
//                Logger.d(result);
//                JSONObject object = JSON.parseObject(result);
//                int count = object.getInteger("qt");
//                if (count != 0) {
//                    navigation_bar.hideRrightTitleIcon(false);
//                    navigation_bar.setRightTitleIcon(count + "");
//                } else {
//                    navigation_bar.hideRrightTitleIcon(true);
//                }
////                    mIndicatorHelper.indicator(count, BadgeView.POSITION_TOP_LEFT,
////                            com.sunnybear.library.R.drawable.indicator_background_2, Color.WHITE);
//                loading.dismiss();
//            }
//        });
//    }
//
//    /**
//     * 刷新方法
//     */
//    private void refresh() {
//        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                adapter.clear();
////                getStoreHome();
//                networkRequest(StoreApi.getStoreHome("1"), new SimpleFastJsonCallback<StoreHome>(StoreHome.class, loading) {
//                    @Override
//                    public void onSuccess(String url, StoreHome result) {
//                        final List<StoreBanner> banners = result.getBanner();
//                        //初始化广告位
//                        if (banners != null && banners.size() > 0) {
//                            bannerAdapter = new StoreBannerAdapter(mActivity, banners, new BannerViewPager.OnPagerClickListenr() {
//                                @Override
//                                public void onPagerClick(int position) {
//                                    StoreBanner banner = banners.get(position);
//                                    String project_id = banner.getItem_id();
//                                    if (!StringUtils.isEmpty(project_id)) {
//                                        Bundle bundle = new Bundle();
//                                        bundle.putString(ProductDetailActivity.BUNDLE_PRODUCT_ID, project_id);
//                                        bundle.putString(ProductDetailActivity.BUNDLE_PRODUCT_IMAGE, banner.getImage());
//                                        startActivity(ProductDetailActivity.class, bundle);
//                                    }
//                                }
//                            });
//                            bl_banner.setAdapter(bannerAdapter);
//                            bl_banner.setOffscreenPageLimit(banners.size());//缓存页面数
//                        }
//
//                        //初始化商品列表
//                        StoreProductHome productHome = result.getProduct();
//                        adapter.addAll(productHome.getData());
//                        if (productHome.getCurrent_page() != productHome.getTotal_page()) {
//                            rv_content.loadMoreComplete();
////                            currentPage++;
//                        } else rv_content.noMoreLoading();
//                        loading.dismiss();
//                    }
//                });
//
////                loading.dismiss();
//                ptl_refresh.refreshComplete();
//            }
//        });
//        rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                networkRequest(StoreApi.getStoreHome(String.valueOf(currentPage)), new SimpleFastJsonCallback<StoreHome>(StoreHome.class, loading) {
//                    @Override
//                    public void onSuccess(String url, StoreHome result) {
//                        //初始化商品列表
//                        StoreProductHome productHome = result.getProduct();
//                        adapter.addAll(productHome.getData());
//                        if (productHome.getCurrent_page() != productHome.getTotal_page()) {
//                            rv_content.loadMoreComplete();
//                            currentPage++;
//                        } else rv_content.noMoreLoading();
//                        loading.dismiss();
//                    }
//                });
//
////                rv_content.loadMoreComplete();
//            }
//        });
//    }
//
//    private void addListener() {
//        rv_content.setOnItemClickListener(new OnItemClickListener<StoreProduct>() {
//            @Override
//            public void onItemClick(StoreProduct storeProduct, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString(ProductDetailActivity.BUNDLE_PRODUCT_ID, storeProduct.getId());
//                bundle.putString(ProductDetailActivity.BUNDLE_PRODUCT_IMAGE, storeProduct.getIcon());
//                startActivity(ProductDetailActivity.class, bundle);
//            }
//        });
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (isStop)
//            isStop = bl_banner.startAutoScroll();
//        if (!MainActivity.isNotRefreshUserInfo && AccountHelper.isLogin()) {
//            getCartCount();
//        } else {
//            navigation_bar.hideRrightTitleIcon(true);
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        isStop = bl_banner.stopAutoScroll();
//    }
//
//    @Override
//    protected String[] getRequestUrls() {
//        return new String[0];
//    }
//
//    @Override
//    public void onRightAction() {
////        mIndicatorHelper.hide();
//        if (!AccountHelper.isLogin()) {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, ShoppingCarActivity.class);
//            startActivity(LoginActivity.class, bundle);
//            return;
//        }
//        startActivity(ShoppingCarActivity.class);
//    }
//
//    @Subcriber(tag = ShoppingCarActivity.EVENT_DELETE_CART)
//    public void eventDeleteCart(String tag) {
//        getCartCount();
//    }
//}

