package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.putao.wd.MainActivity;
import com.putao.wd.R;
import com.putao.wd.api.StoreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.dto.ProductItem;
import com.putao.wd.home.adapter.ProductAdapter;
import com.putao.wd.model.StoreHome;
import com.putao.wd.store.product.ProductDetailActivity;
import com.putao.wd.store.shopping.ShoppingCarActivity;
import com.sunnybear.library.controller.handler.WeakHandler;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;
import com.sunnybear.library.view.recycler.RecyclerViewHeader;
import com.sunnybear.library.view.viewpager.BannerAdapter;
import com.sunnybear.library.view.viewpager.BannerLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 葡商城
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoStoreFragment extends PTWDFragment {
    private static final int[] resIds = new int[]{
            R.drawable.test_1, R.drawable.test_2, R.drawable.test_3, R.drawable.test_4, R.drawable.test_5, R.drawable.test_6, R.drawable.test_7
    };
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
    private List<ProductItem> products;

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
        //addTestData();//测试假数据
        //adapter.addAll(products);
        //广告位
        bl_banner.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position) {
                ImageView imageView = new ImageView(mActivity);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(resIds[position]);
                return imageView;
            }

            @Override
            public int getCount() {
                return resIds.length;
            }
        });
        refresh();
        //addListener();
        getStoreHome();
    }

    /**
     * 获取葡商城首页信息
     */
    private void getStoreHome() {
        networkRequest(StoreApi.getStoreHome(), new SimpleFastJsonCallback<StoreHome>(StoreHome.class, loading) {
            @Override
            public void onSuccess(String url, StoreHome result) {
                Logger.d(result.toString());
                adapter.addAll(result.getProduct());
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
                new WeakHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptl_refresh.refreshComplete();
                    }
                }, 3 * 1000);
            }
        });
    }

    private void addListener() {
        rv_content.setOnItemClickListener(new OnItemClickListener<ProductItem>() {
            @Override
            public void onItemClick(ProductItem productItem, int position) {
                startActivity(ProductDetailActivity.class);
            }
        });
    }

//    private void addTestData() {
//        products = new ArrayList<>();
//        ProductItem productItem = new ProductItem();
//        productItem.setId("1");
//        productItem.setTitle("葡萄探索号");
//        productItem.setIntro("快乐不至于屏幕虚拟+现实儿童科技益智玩具(本产品需搭配iPad使用)");
//        productItem.setPrice("798.00");
//        for (int i = 0; i < 10; i++) {
//            products.add(productItem);
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        if (isStop)
            isStop = bl_banner.startAutoScroll();
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
