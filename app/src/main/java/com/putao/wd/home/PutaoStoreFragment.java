package com.putao.wd.home;

import android.os.Bundle;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.ProductItem;
import com.putao.wd.home.adapter.ProductAdapter;
import com.putao.wd.product.ProductDetailActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.handler.WeakHandler;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;
import com.sunnybear.library.view.recycler.RecyclerViewHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 葡商城
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoStoreFragment extends BasicFragment {
    @Bind(R.id.rvh_header)
    RecyclerViewHeader mHeader;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.tv_header)
    TextView tv_header;

    private ProductAdapter adapter;
    private List<ProductItem> products;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        mHeader.attachTo(rv_content, true);
        adapter = new ProductAdapter(mActivity, null);
        rv_content.setAdapter(adapter);
        addTestData();//测试假数据
        adapter.addAll(products);

        refresh();
        addListener();
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

    private void addTestData() {
        products = new ArrayList<>();
        ProductItem productItem = new ProductItem();
        productItem.setId("1");
        productItem.setTitle("葡萄探索号");
        productItem.setIntro("快乐不至于屏幕虚拟+现实儿童科技益智玩具(本产品需搭配iPad使用)");
        productItem.setPrice("798.00");
        for (int i = 0; i < 10; i++) {
            products.add(productItem);
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
