package com.putao.wd.explore;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * 探索--前7
 * Created by yanghx on 2016/1/11.
 */
public class SmartDetailFragment extends BasicFragment {
    @Bind(R.id.tv_smart_list_title)
    TextView tv_smart_list_title;
    @Bind(R.id.tv_smart_list_detail)
    TextView tv_smart_list_detail;

    public static String CONTENT_NUM = "content_num";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_smart_list_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        int content_num = getArguments().getInt(CONTENT_NUM);
        String s = getResources().getStringArray(R.array.smart_list)[content_num];
        tv_smart_list_title.setText(s);
        tv_smart_list_detail.setText(s);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


}
