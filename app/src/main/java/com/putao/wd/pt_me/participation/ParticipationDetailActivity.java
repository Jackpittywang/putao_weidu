package com.putao.wd.pt_me.participation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.api.CollectionApi;
import com.putao.wd.api.ParticipationApi;
import com.putao.wd.model.Collection;
import com.putao.wd.model.Participation;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.viewpager.adapter.LoadMoreFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的参与详情
 * Created by Administrator on 2016/4/5.
 */
public class ParticipationDetailActivity extends BasicFragmentActivity {
    @Bind(R.id.vp_content)
    ViewPager vp_content;

    private ArrayList<Participation> mParticipations;
    private int mPosition;
    private int mPageCount;
    private boolean has_more_data;

    public static final String POSITION = "position";
    public static final String PAGE_COUNT = "page_count";
    public static final String CREATE = "participation";
    public static final String HAS_MORE_DATA = "has_more_data";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scroll_viewpager_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        mParticipations = (ArrayList<Participation>) args.getSerializable(CREATE);
        mPosition = args.getInt(POSITION);
        mPageCount = args.getInt(PAGE_COUNT);
        has_more_data = args.getBoolean(HAS_MORE_DATA);
        LoadMoreFragmentPagerAdapter fragmentPagerAdapter = new LoadMoreFragmentPagerAdapter<Participation>(getSupportFragmentManager(), mParticipations) {
            @Override
            public void loadMoreData() {
                if (!has_more_data) return;
                networkRequest(ParticipationApi.getQueryPart(mPageCount),
                        new SimpleFastJsonCallback<ArrayList<Participation>>(Participation.class, loading) {
                            @Override
                            public void onSuccess(String url, ArrayList<Participation> result) {
                                if (result.size() > 0)
                                    addData(result);
//                                if (result.getCurrentPage() == result.getTotalPage())
//                                    has_more_data = false;
                                else mPageCount++;
                                loading.dismiss();
                            }
                        });
            }

            @Override
            public Fragment getItem(List<Participation> datas, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(POSITION, position);
                bundle.putSerializable(CREATE, datas.get(position));
                return null;
//                return Fragment.instantiate(getApplicationContext(), CreateBasicDetailFragment.class.getName(), bundle);
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
                finish();
                break;
        }

    }
}
