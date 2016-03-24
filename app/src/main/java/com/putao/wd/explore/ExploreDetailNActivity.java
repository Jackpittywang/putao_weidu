package com.putao.wd.explore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.CreateApi;
import com.putao.wd.created.CreateBasicDetailFragment;
import com.putao.wd.model.Create;
import com.putao.wd.model.Creates;
import com.putao.wd.model.ExploreIndex;
import com.putao.wd.model.ExploreIndexs;
import com.putao.wd.model.HomeExploreMore;
import com.putao.wd.model.HomeExploreMores;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.viewpager.adapter.LoadMoreFragmentPagerAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 只有一个viewpager和一个关闭按钮的activity
 * Created by zhanghao on 2016/3/15.
 */
public class ExploreDetailNActivity extends BasicFragmentActivity {

    @Bind(R.id.vp_content)
    ViewPager vp_content;

    private ArrayList<ExploreIndex> mDatas;
    private int mPosition;
    private int mPageCount;
    private boolean has_more_data;

    public static final String POSITION = "position";
    public static final String PAGE_COUNT = "page_count";
    public static final String DATAS = "datas";
    public static final String HAS_MORE_DATA = "has_more_data";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scroll_viewpager_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        mDatas = (ArrayList<ExploreIndex>) args.getSerializable(DATAS);
        mPosition = args.getInt(POSITION);
        mPageCount = args.getInt(PAGE_COUNT);
        has_more_data = args.getBoolean(HAS_MORE_DATA);
        LoadMoreFragmentPagerAdapter fragmentPagerAdapter = new LoadMoreFragmentPagerAdapter<ExploreIndex>(getSupportFragmentManager(), mDatas) {
            @Override
            public void loadMoreData() {
                if (!has_more_data) return;
                networkRequest(CreateApi.getCreateList(1, mPageCount),
                        new SimpleFastJsonCallback<HomeExploreMores>(HomeExploreMores.class, loading) {
                            @Override
                            public void onSuccess(String url, HomeExploreMores result) {
                                if (result.getList().size() > 0)
                                    addData(result.getList());
                                if (result.getCurrent_page() == result.getTotal_page())
                                    has_more_data = false;
                                else mPageCount++;
                                loading.dismiss();
                            }
                        }, false);
            }

            @Override
            public Fragment getItem(List<ExploreIndex> datas, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(ExploreCommonFragment.INDEX_DATA_PAGE, position);
                bundle.putSerializable(ExploreCommonFragment.INDEX_DATA, datas.get(position));
                return Fragment.instantiate(getApplicationContext(), ExploreDetailFragment.class.getName(), bundle);

            }
        };
        vp_content.setAdapter(fragmentPagerAdapter);
        vp_content.setOffscreenPageLimit(3);
        vp_content.setCurrentItem(mPosition);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.iv_close})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                MobclickAgent.onEvent(mContext, YouMengHelper.ChoiceHome_detail_close, "按钮点击");
                finish();
                break;
        }

    }
}
