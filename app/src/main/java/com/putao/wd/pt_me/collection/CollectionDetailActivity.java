package com.putao.wd.pt_me.collection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.api.CollectionApi;
import com.putao.wd.api.CreateApi;
import com.putao.wd.created.CreateBasicDetailFragment;
import com.putao.wd.model.Collection;
import com.putao.wd.model.Cool;
import com.putao.wd.model.Create;
import com.putao.wd.model.Creates;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.viewpager.adapter.LoadMoreFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的收藏详情托管activity
 * Created by zhanghao on 2016/1/15.
 */
public class CollectionDetailActivity extends BasicFragmentActivity {
    @Bind(R.id.vp_content)
    ViewPager vp_content;

    private ArrayList<Collection> mCollections;
    private int mPosition;
    private int mPageCount;
    private boolean has_more_data;

    public static final String POSITION = "position";
    public static final String PAGE_COUNT = "page_count";
    public static final String CREATE = "collection";
    public static final String HAS_MORE_DATA = "has_more_data";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scroll_viewpager_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        mCollections = (ArrayList<Collection>) args.getSerializable(CREATE);
        mPosition = args.getInt(POSITION);
        mPageCount = args.getInt(PAGE_COUNT);
        has_more_data = args.getBoolean(HAS_MORE_DATA);
        LoadMoreFragmentPagerAdapter fragmentPagerAdapter = new LoadMoreFragmentPagerAdapter<Collection>(getSupportFragmentManager(), mCollections) {
            @Override
            public void loadMoreData() {
                if (!has_more_data) return;
                networkRequest(CollectionApi.getCollection(mPageCount),
                        new SimpleFastJsonCallback<ArrayList<Collection>>(Collection.class, loading) {
                            @Override
                            public void onSuccess(String url, ArrayList<Collection> result) {
                                if (result.size() > 0)
                                    addData(result);
                                checkLoadMoreComplete(result);
                                loading.dismiss();
                            }
                        });
            }

            @Override
            public Fragment getItem(List<Collection> datas, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(POSITION, position);
                bundle.putSerializable(CREATE, datas.get(position));
                return Fragment.instantiate(getApplicationContext(), CreateBasicDetailFragment.class.getName(), bundle);
            }
        };
        vp_content.setAdapter(fragmentPagerAdapter);
        vp_content.setOffscreenPageLimit(3);
        vp_content.setCurrentItem(mPosition);
    }

    private void checkLoadMoreComplete(ArrayList<Collection> list) {
        if (null == list) {
            has_more_data = false;
        } else {
            mPageCount++;
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.iv_close})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;
        }

    }
}