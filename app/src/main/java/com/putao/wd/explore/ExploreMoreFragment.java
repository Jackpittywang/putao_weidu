package com.putao.wd.explore;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.home.PutaoExploreFragment;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.ImageUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 探索--更多
 * Created by yanghx on 2016/1/11.
 */
public class ExploreMoreFragment extends BasicFragment {

    //  @Bind(R.id.rv_content)
//  BasicRecyclerView rv_content;
//    @Bind(R.id.iv_said)
//    ImageView iv_said;
//    @Bind(R.id.iv_play)
//    ImageView iv_play;
    @Bind(R.id.iv_video)
    ImageView iv_video;

//    private ExploreMoreAdapter adapter;

    @Override
    protected void lazyLoad() {
        if (!isVisible || !PutaoExploreFragment.BACKGROUND_CAN_CHANGGE) {
            return;
        }
        PutaoExploreFragment.BACKGROUND_CAN_CHANGGE = false;
        EventBusHelper.post(ImageUtils.cutOutViewToSmallBitmap(iv_video), PutaoExploreFragment.BLUR);
        iv_video.setBackgroundResource(R.drawable.img_explore_more_cover);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_more;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
//        adapter = new ExploreMoreAdapter(mActivity, getTest());
//        rv_content.setAdapter(adapter);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @OnClick({R.id.ll_more})
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            /*case R.id.iv_said :
                bundle.putInt(ExploreMoreActivity.KEY_TAB, 0);
                startActivity(ExploreMoreActivity.class, bundle);
                break;
            case R.id.iv_play :
                bundle.putInt(ExploreMoreActivity.KEY_TAB, 1);
                startActivity(ExploreMoreActivity.class, bundle);
                break;
            case R.id.iv_plus :
                bundle.putInt(ExploreMoreActivity.KEY_TAB, 2);
                startActivity(ExploreMoreActivity.class, bundle);
                break;*/
        }
        startActivity(ExploreMoreActivity.class, bundle);
    }

}
