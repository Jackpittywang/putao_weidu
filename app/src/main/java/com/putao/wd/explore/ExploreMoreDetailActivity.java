package com.putao.wd.explore;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.comment.CommentActivity;
import com.putao.wd.video.VideoPlayerActivity;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 更多内容详情
 * Created by yanghx on 2016/1/12.
 */
public class ExploreMoreDetailActivity extends PTWDActivity implements View.OnClickListener {

    @Bind(R.id.iv_top)
    ImageDraweeView iv_top;
    @Bind(R.id.iv_player)
    ImageView iv_player;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.wb_explore_detail)
    BasicWebView wb_explore_detail;
    @Bind(R.id.iv_close)
    ImageView iv_close;
    @Bind(R.id.ll_cool)
    LinearLayout ll_cool;
    @Bind(R.id.sb_cool_icon)
    SwitchButton sb_cool_icon;
    @Bind(R.id.ll_comment)
    LinearLayout ll_comment;
    @Bind(R.id.ll_share)
    LinearLayout ll_share;

    private SharePopupWindow mSharePopupWindow;//分享弹框

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        wb_explore_detail.loadUrl("http://static.putaocdn.com/weidu/view/active_info.html?id=2&device=m&nav=0");
        mSharePopupWindow = new SharePopupWindow(mContext);
        addListener();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void addListener() {
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ShareTools.wechatWebShare(mContext, true, "创造详情", "和孩子一起打造创造空间", "http://mall.file.putaocdn.com/file/a007d1dad9979d9caa70c3988e2bb2cab70679f3.jpg", "http://www.putao.com/product/11");
            }

            @Override
            public void onWechatFriend() {
                ShareTools.wechatWebShare(mContext, false, "创造详情", "和孩子一起打造创造空间", "http://mall.file.putaocdn.com/file/a007d1dad9979d9caa70c3988e2bb2cab70679f3.jpg", "http://www.putao.com/product/11");
            }
        });
    }

    @OnClick({R.id.iv_player, R.id.iv_close, R.id.ll_cool, R.id.ll_comment, R.id.ll_share})
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_player :
                bundle.putString(VideoPlayerActivity.BUNDLE_VIDEO_URL, "hafohfoafoa");
                startActivity(VideoPlayerActivity.class, bundle);
                break;
            case R.id.iv_close :
                finish();
                overridePendingTransition(R.anim.in_from_down, R.anim.out_from_top);
                break;
            case R.id.ll_cool :
                bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, "1");
                Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.anim_cool);
                sb_cool_icon.startAnimation(anim);
//                startActivity(PraiseListActivity.class, bundle);
                break;
            case R.id.ll_comment :
                bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, "1");
                startActivity(CommentActivity.class, bundle);
                break;
            case R.id.ll_share :
                mSharePopupWindow.show(ll_share);
                break;
        }
    }
}

