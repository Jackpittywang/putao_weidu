package com.putao.wd.created;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.api.CreateApi;
import com.putao.wd.model.Creates;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.viewpager.adapter.LoadMoreFragmentPagerAdapter;
import com.putao.wd.model.Create;
import com.sunnybear.library.controller.BasicFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 只有一个viewpager和一个关闭按钮的activity
 * Created by zhanghao on 2016/3/15.
 */
public class CreateDetailActivity extends BasicFragmentActivity {

    @Bind(R.id.vp_content)
    ViewPager vp_content;

    private SparseArray<Fragment> mFragments;
    private ArrayList<Create> mCreates;
    private int mPosition;
    private int mPageCount;
    private boolean isShowProgress;
    private boolean has_more_data;

    public static final String POSITION = "position";
    public static final String SHOW_PROGRESS = "show_progress";
    public static final String PAGE_COUNT = "page_count";
    public static final String CREATE = "create";
    public static final String HAS_MORE_DATA = "has_more_data";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scroll_viewpager_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        mCreates = (ArrayList<Create>) args.getSerializable(CREATE);
        mPosition = args.getInt(POSITION);
        isShowProgress = args.getBoolean(SHOW_PROGRESS);
        mPageCount = args.getInt(PAGE_COUNT);
        has_more_data = args.getBoolean(HAS_MORE_DATA);
        LoadMoreFragmentPagerAdapter fragmentPagerAdapter = new LoadMoreFragmentPagerAdapter<Create>(getSupportFragmentManager(), mCreates) {
            @Override
            public void loadMoreData() {
                if (!has_more_data) return;
                networkRequest(CreateApi.getCreateList(1, mPageCount),
                        new SimpleFastJsonCallback<Creates>(Creates.class, loading) {
                            @Override
                            public void onSuccess(String url, Creates result) {
                                if (result.getData().size() > 0)
                                    addData(result.getData());
                                if (result.getCurrentPage() == result.getTotalPage())
                                    has_more_data = false;
                                else mPageCount++;
                                loading.dismiss();
                            }
                        });
            }

            @Override
            public Fragment getItem(List<Create> datas, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(POSITION, position);
                bundle.putSerializable(CREATE, datas.get(position));
                return new CreateBasicDetailFragment(bundle);
            }
        };
        vp_content.setAdapter(fragmentPagerAdapter);
        vp_content.setOffscreenPageLimit(3);
        vp_content.setCurrentItem(mPosition, true);
    }

    @OnClick({R.id.iv_close})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;
        }

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
