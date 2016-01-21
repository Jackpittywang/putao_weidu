package com.putao.wd.explore;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ExploreIndex;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.praise.PraiseListActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.image.ImageDraweeView;


import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索--前7
 * Created by yanghx on 2016/1/11.
 */
public class ExploreCommonFragment extends BasicFragment implements View.OnClickListener {

    @Bind(R.id.iv_video)
    ImageDraweeView iv_video;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.tv_count_cool)
    TextView tv_count_cool;
    @Bind(R.id.iv_player)
    ImageView iv_player;
    public static final String INDEX_DATA_PAGE = "index_data_page";
    public static final String INDEX_DATA = "index_data";
    private ExploreIndex mExploreIndex;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_common;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        mExploreIndex = (ExploreIndex) args.getSerializable(INDEX_DATA_PAGE);
        initView();
    }


    private void initView() {
        iv_video.setImageURL(mExploreIndex.getBanner().get(0).getUrl());
        tv_title.setText(mExploreIndex.getTitle());
        tv_content.setText(mExploreIndex.getDescription());
        tv_count_cool.setText(mExploreIndex.getCount_likes() + "");
        if ("VIDEO".equals(mExploreIndex.getBanner().get(0).getType()))
            iv_player.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.iv_video, R.id.ll_count_cool})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_video:
                Bundle bundleDetial = new Bundle();
                bundleDetial.putSerializable(INDEX_DATA, args.getSerializable(INDEX_DATA));
                startActivity(ExploreDetailActivity.class, bundleDetial);
                mActivity.overridePendingTransition(R.anim.in_from_down, R.anim.out_from_down);
                break;
            case R.id.ll_count_cool:
                Bundle bundleCool = new Bundle();
                bundleCool.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, "1");
                startActivity(PraiseListActivity.class, bundleCool);
                break;
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
