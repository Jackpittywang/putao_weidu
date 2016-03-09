package com.putao.wd.explore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.putao.wd.R;
import com.putao.wd.model.ExploreIndex;
import com.sunnybear.library.controller.BasicFragmentActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 首页详情
 * Created by guchenkai on 2016/1/11.
 */
public class ExploreDetailActivity extends BasicFragmentActivity implements View.OnClickListener /*implements SwipeBackLayout.SwipeBackListener*/ {

    //    private SwipeBackLayout mSwipeBackLayout;
    private ImageView ivShadow;

    private SparseArray<Fragment> mFragments;
    private List<ExploreIndex> mExploreIndexs;
    private int mPosition;

    @Bind(R.id.vp_container)
    ViewPager vp_container;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nexplore_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        Intent intent = getIntent();
        mExploreIndexs = (List<ExploreIndex>) intent.getSerializableExtra(ExploreCommonFragment.INDEX_DATA);
        mPosition = intent.getIntExtra(ExploreCommonFragment.INDEX_DATA_PAGE, 1);
        addFragment();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        mExploreIndexs = (List<ExploreIndex>) intent.getSerializableExtra(ExploreCommonFragment.INDEX_DATA);
        mPosition = intent.getIntExtra(ExploreCommonFragment.INDEX_DATA_PAGE, 1);
        addFragment();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

/*    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(getContainer());
        Intent intent = getIntent();
        mExploreIndexs = (List<ExploreIndex>) intent.getSerializableExtra(ExploreCommonFragment.INDEX_DATA);
        mPosition = intent.getIntExtra(ExploreCommonFragment.INDEX_DATA_PAGE,1);
//        initView();
        addFragment();
    }*/

    /**
     * 初始化视图
     */
/*    private void initView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_nexplore_detail, null);
//        mSwipeBackLayout.addView(view);
        vp_container = (ViewPager) findViewById(R.id.vp_container);
//        mSwipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.TOP);
    }*/

    /**
     * 初始化容器视图
     * @return
     */
/*    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
//        mSwipeBackLayout = new SwipeBackLayout(this);
//        mSwipeBackLayout.setOnSwipeBackListener(this);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.popup_background));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(mSwipeBackLayout);
        return container;
    }*/

    /**
     * 初始化Fragment
     */
    private void addFragment() {
        mFragments = new SparseArray<>();
        for (int i = 0; i < mExploreIndexs.size(); i++) {
            mFragments.put(i, Fragment.instantiate(getApplicationContext(), ExploreDetailFragment.class.getName(), addBundle(i)));
        }
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
        };
        vp_container.setAdapter(adapter);
        vp_container.setCurrentItem(mPosition);
    }

    /**
     * viewpager每个页面的bundle
     *
     * @param position 页面序号
     * @return
     */
    private Bundle addBundle(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ExploreCommonFragment.INDEX_DATA_PAGE, position);
        bundle.putSerializable(ExploreCommonFragment.INDEX_DATA, mExploreIndexs.get(position));
        return bundle;
    }

/*
    @Override
    public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
        ivShadow.setAlpha(1 - fractionScreen);
    }
*/


    //    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_nexplore_detail;
//    }
//
//    @Override
//    protected void onViewCreatedFinish(Bundle saveInstanceState) {
//        addFragment();
//        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public int getCount() {
//                return mFragments.size();
//            }
//
//            @Override
//            public Fragment getItem(int position) {
//                return mFragments.get(position);
//            }
//        };
//        vp_container.setAdapter(adapter);
//        vp_container.setCurrentItem(0);
//    }
//
//    @Override
//    protected String[] getRequestUrls() {
//        return new String[0];
//    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.companion_in_from_down, R.anim.out_from_down);
    }

    @OnClick(R.id.iv_close)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_close) finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
