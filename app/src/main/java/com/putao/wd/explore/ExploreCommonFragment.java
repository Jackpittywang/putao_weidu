package com.putao.wd.explore;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.explore.adapter.ExploreCommonAdapter;
import com.putao.wd.model.PagerExplore;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 探索--前7
 * Created by yanghx on 2016/1/11.
 */
public class ExploreCommonFragment extends BasicFragment {

    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;

    private ExploreCommonAdapter adapter;
    private List<PagerExplore> list;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_common;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        adapter = new ExploreCommonAdapter(mActivity, getTest());
        rv_content.setAdapter(adapter);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private List<PagerExplore> getTest() {
        List<PagerExplore> list = new ArrayList<>();
        PagerExplore pagerExplore = new PagerExplore();
        pagerExplore.setImageUrl(R.drawable.test_flaunt_taotao_bg_01);
        pagerExplore.setTitle("这里是title");
        pagerExplore.setContent("这里是content这里是content这里是content这里是content这里是content这里是content这里是content这里是content这里是content这里是content");
        list.add(pagerExplore);
        return list;
    }

}
