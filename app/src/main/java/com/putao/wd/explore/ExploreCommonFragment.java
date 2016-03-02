package com.putao.wd.explore;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.model.ExploreIndex;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;

import java.util.List;

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
    @Bind(R.id.sb_cool_icon)
    SwitchButton sb_cool_icon;
    @Bind(R.id.rl_nexplore)
    RelativeLayout rl_nexplore;
    public static final String INDEX_DATA_PAGE = "index_data_page";
    public static final String INDEX_DATA = "index_data";
    private ExploreIndex mExploreIndex;
    private List<ExploreIndex> mExploreIndexs;
    private int mPosition;
    private boolean isCool;//是否赞过

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_common;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        mPosition = args.getInt(INDEX_DATA_PAGE);
        mExploreIndexs = ((List<ExploreIndex>) args.getSerializable(INDEX_DATA));
        mExploreIndex = mExploreIndexs.get(mPosition);
//        int cache_count = (int) mDiskFileCacheHelper.getAsSerializable(ExploreDetailFragment.COOL_COUNT + mExploreIndex.getArticle_id(),0);
//        mExploreIndex.setCount_likes(cache_count == null ? mExploreIndex.getCount_likes() : cache_count);
        sb_cool_icon.setClickable(false);
        isCool = null != mDiskFileCacheHelper.getAsString(ExploreDetailFragment.COOL + mExploreIndex.getArticle_id());
        sb_cool_icon.setState(isCool);
        initView();
    }


    private void initView() {
        iv_video.setImageURL(mExploreIndex.getBanner().get(0).getUrl());
        tv_title.setText(mExploreIndex.getTitle());
        tv_content.setText(mExploreIndex.getDescription());
        tv_count_cool.setText(mExploreIndex.getCount_likes() == 0 ? "赞" : mExploreIndex.getCount_likes() + "");
        if ("VIDEO".equals(mExploreIndex.getBanner().get(0).getType())) {
            iv_player.setVisibility(View.VISIBLE);
            iv_video.setImageURL(mExploreIndex.getBanner().get(0).getCover_pic());
        } else {
            iv_video.setImageURL(mExploreIndex.getBanner().get(0).getUrl());
        }
    }


    @OnClick({R.id.rl_nexplore, R.id.ll_count_cool})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_nexplore:
                Bundle bundleDetial = new Bundle();
                bundleDetial.putSerializable(INDEX_DATA, args.getSerializable(INDEX_DATA));
                bundleDetial.putInt(INDEX_DATA_PAGE, mPosition);
                startActivity(ExploreDetailActivity.class, bundleDetial);
                mActivity.overridePendingTransition(R.anim.in_from_down, R.anim.companion_in_from_down);
                break;

            case R.id.ll_count_cool:
                Animation anim = AnimationUtils.loadAnimation(mActivity, R.anim.anim_cool);
                sb_cool_icon.startAnimation(anim);
                if (isCool) break;
                isCool = true;
                sb_cool_icon.setState(true);
                mExploreIndexs.get(mPosition).setCount_likes(mExploreIndex.getCount_likes() + 1);
                tv_count_cool.setText(mExploreIndex.getCount_likes() + "");
                networkRequest(ExploreApi.addLike(mExploreIndex.getArticle_id()),
                        new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                mDiskFileCacheHelper.put(ExploreDetailFragment.COOL + mExploreIndex.getArticle_id(), true);
                                mDiskFileCacheHelper.put(ExploreDetailFragment.COOL_COUNT + mExploreIndex.getArticle_id(), mExploreIndex.getCount_likes() + 1 + "");
                                loading.dismiss();
                            }
                        });
                break;
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Subcriber(tag = ExploreDetailFragment.EVENT_ADD_COOL)
    public void eventAddCoolCount(int position) {
        if (mPosition == position) {
            sb_cool_icon.setState(true);
            mExploreIndex.setCount_likes(mExploreIndex.getCount_likes() + 1);
            tv_count_cool.setText(mExploreIndex.getCount_likes() + "");
        }
    }
}
