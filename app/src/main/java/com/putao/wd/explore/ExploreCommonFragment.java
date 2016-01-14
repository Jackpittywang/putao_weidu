package com.putao.wd.explore;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
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
        tv_title.setText("这里是title");
        tv_content.setText("这里是content这里是content这里是content这里是content这里是content这里是content这里是content这里是content这里是content这里是content");
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

}
