package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Switch;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.dto.ExploreItem;
import com.putao.wd.explore.manage.ManageActivity;
import com.putao.wd.home.adapter.ExploreAdapter;
import com.putao.wd.qrcode.CaptureActivity;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 探索号
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoExploreFragment extends PTWDFragment {

    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;

    private ExploreAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        setMainTitleColor(Color.WHITE);
        adapter = new ExploreAdapter(mActivity, getTestData());
        rv_content.setAdapter(adapter);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onLeftAction() {
        startActivity(CaptureActivity.class);
    }

    private List<ExploreItem> getTestData() {
        List<ExploreItem> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExploreItem exploreItem = new ExploreItem();
            if (i == 1 || i == 3 || i == 5) {
                exploreItem.setIsMixed(true);
                switch(i){
                    case 1:
                        exploreItem.setIconNum(1);
                        break;
                    case 3:
                        exploreItem.setIconNum(4);
                        break;
                    case 5:
                        exploreItem.setIconNum(9);
                        break;
                    default:
                        break;
                }
            } else {
                exploreItem.setIsMixed(false);
            }
            if (i < 4) {
                exploreItem.setDate("2015-12-10");
            }else if (i >= 4 && i < 7) {
                exploreItem.setDate("2015-12-09");
            }else {
                exploreItem.setDate("2015-12-08");
            }
            exploreItem.setSkill_name("技能" + i);
            exploreItem.setContent("这里显示每个关卡背后的教育理念的主要文字，大概是一句话左右。" +
                    "完成多个关卡，则以分号隔开显示，一直向下展示。这里显示每个关卡背后的教育理念的主要文字，" +
                    "大概是一句话左右。完成多个关卡，则以分号隔开显示，一直向下展示。" + i);
            list.add(exploreItem);
        }
        return list;
    }

    @Override
    public void onRightAction() {
        startActivity(ManageActivity.class);
    }
}





