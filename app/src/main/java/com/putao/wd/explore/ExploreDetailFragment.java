package com.putao.wd.explore;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.comment.CommentActivity;
import com.putao.wd.start.praise.PraiseListActivity;
import com.putao.wd.video.VideoPlayerActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.image.ImageDraweeView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索--详情
 * Created by yanghx on 2016/1/13.
 */
public class ExploreDetailFragment extends BasicFragment implements View.OnClickListener{

    @Bind(R.id.iv_top)
    ImageDraweeView iv_top;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.wb_explore_detail)
    BasicWebView wb_explore_detail;
    @Bind(R.id.iv_close)
    ImageView iv_close;
    @Bind(R.id.ll_cool)
    LinearLayout ll_cool;
    @Bind(R.id.ll_comment)
    LinearLayout ll_comment;
    @Bind(R.id.ll_share)
    LinearLayout ll_share;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        wb_explore_detail.loadUrl("http://static.putaocdn.com/weidu/view/active_info.html?id=2&device=m&nav=0");
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.iv_top, R.id.iv_close, R.id.ll_cool, R.id.ll_comment, R.id.ll_share})
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_top :
                bundle.putString(VideoPlayerActivity.BUNDLE_VIDEO_URL, "hafohfoafoa");
                startActivity(VideoPlayerActivity.class, bundle);
                break;
            case R.id.iv_close :
                mActivity.finish();
                break;
            case R.id.ll_cool :
                bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, "1");
                startActivity(PraiseListActivity.class, bundle);
                break;
            case R.id.ll_comment :
                bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, "1");
                startActivity(CommentActivity.class, bundle);
                break;
            case R.id.ll_share :
//                startActivity();
                break;
        }
    }
}
