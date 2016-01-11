package com.putao.wd.home;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.dto.StoreItem;
import com.putao.wd.home.adapter.StoreAdapter;
import com.putao.wd.store.product.ProductDetailActivity;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 精选
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoPickFragment extends PTWDFragment {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;

    private StoreAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();

        adapter = new StoreAdapter(mActivity, getData());
        rv_content.setAdapter(adapter);
        addListener();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void addListener() {
        rv_content.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Serializable serializable, int position) {
                Bundle bundle = new Bundle();
//                bundle.putString(ProductDetailActivity.BUNDLE_PRODUCT_ID, storeProduct.getId());
//                bundle.putString(ProductDetailActivity.BUNDLE_PRODUCT_IMAGE, storeProduct.getIcon());
                startActivity(ProductDetailActivity.class, bundle);
            }
        });
    }

    private List<StoreItem> getData() {
        List<StoreItem> lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            StoreItem storeItem = new StoreItem();
            storeItem.setIamgeURL(R.drawable.test_flaunt_taotao_bg_01);
            lists.add(storeItem);
        }
        return lists;
    }

}



