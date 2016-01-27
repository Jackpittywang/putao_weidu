package com.putao.wd.explore;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.putao.wd.R;
import com.putao.wd.model.HomeExplore;
import com.sunnybear.library.controller.BasicFragment;

import java.util.ArrayList;
import java.util.List;

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
//    @Bind(R.id.iv_plus)
//    ImageView iv_plus;

//    private ExploreMoreAdapter adapter;

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

//    private void addListener() {
//        rv_content.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(Serializable serializable, int position) {
//                startActivity(ExploreMoreActivity.class);
//            }
//        });
//    }
//
//
//    private List<HomeExplore> getTest() {
//        List<HomeExplore> list = new ArrayList<>();
//        HomeExplore homeExplore = null;
//        for (int i = 0; i < 3; i++) {
//            homeExplore = new HomeExplore();
//            homeExplore.setImageUrl(R.drawable.test_flaunt_taotao_bg_01);
//            list.add(homeExplore);
//        }
//        return list;
//    }

    @OnClick({R.id.ll_more})
    public void onClick(View v){
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
