package com.putao.wd.explore;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.explore.adapter.ExploreMoreAdapter;
import com.putao.wd.model.PagerExplore;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 探索--更多
 * Created by yanghx on 2016/1/11.
 */
public class ExploreMoreFragment extends BasicFragment {

    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;

    private ExploreMoreAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nexplore_more;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        adapter = new ExploreMoreAdapter(mActivity, getTest());
        rv_content.setAdapter(adapter);
        addListener();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    private void addListener() {
        rv_content.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Serializable serializable, int position) {
                startActivity(ExploreMoreActivity.class);
            }
        });
    }


    private List<PagerExplore> getTest() {
        List<PagerExplore> list = new ArrayList<>();
        PagerExplore pagerExplore = null;
       for(int i = 0; i < 2; i ++) {
           pagerExplore = new PagerExplore();
           pagerExplore.setImageUrl(R.drawable.test_flaunt_taotao_bg_01);
           list.add(pagerExplore);
       }
        return list;
    }
}
