package com.putao.wd.explore;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.home.PutaoExploreFragment;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.praise.PraiseListActivity;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.Subcriber;
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

    private int position;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_common;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        getTest();

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void getTest() {
        switch(position) {
            case 0:
                iv_video.setBackgroundResource(R.drawable.icon_40_01);
                tv_title.setText("多点时间陪孩子");
                tv_content.setText("现代家长总是忙忙忙，还是多抽点时间陪孩子吧，给孩子留下一个美好的童年");
                break;
            case 1:
                iv_video.setBackgroundResource(R.drawable.icon_40_02);
                tv_title.setText("和孩子一起玩耍");
                tv_content.setText("让葡萄探索号和孩子一起玩耍");
                break;
            case 2:
                iv_video.setBackgroundResource(R.drawable.icon_40_03);
                tv_title.setText("探索号的陪伴");
                tv_content.setText("探索号是葡萄科技为孩子倾力打造的一款智能玩具，让您的孩子不再孤单");
                break;
            case 3:
                iv_video.setBackgroundResource(R.drawable.icon_40_04);
                tv_title.setText("多玩只能游戏");
                tv_content.setText("多玩智能游戏，可以有效提高孩子的学习能力哦");
                break;
            case 4:
                iv_video.setBackgroundResource(R.drawable.icon_40_05);
                tv_title.setText("淘淘向右走");
                tv_content.setText("淘淘向右走，让您的孩子体验闯关的乐趣");
                break;
            case 5:
                iv_video.setBackgroundResource(R.drawable.icon_40_06);
                tv_title.setText("魔方");
                tv_content.setText("挖掘孩子的潜能从魔方开始吧");
                break;
            case 6:
                iv_video.setBackgroundResource(R.drawable.icon_40_07);
                tv_title.setText("魔方");
                tv_content.setText("魔方不只是大人才能玩转的，葡萄魔方陪伴您的孩子更好的成长");
                break;
        }
    }

    @OnClick({R.id.iv_video, R.id.ll_count_cool})
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_video :
                startActivity(ExploreDetailActivity.class);
                break;
            case R.id.ll_count_cool :
                bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, "1");
                startActivity(PraiseListActivity.class, bundle);
                break;
        }
    }

    @Subcriber(tag = PutaoExploreFragment.EVENT_CURRENT_ITEM)
    public void eventData(int positon) {
        this.position = positon;
    }

}
