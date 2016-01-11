package com.putao.wd.explore;

import android.os.Bundle;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import butterknife.Bind;

/**
 * 探索--更多
 * Created by yanghx on 2016/1/11.
 */
public class ExploreMoreFragment extends BasicFragment {

    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_more;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
