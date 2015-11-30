package com.putao.wd.product;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.product.adapter.NormsSelectAdapter;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.Arrays;

import butterknife.Bind;

/**
 * Created by guchenkai on 2015/11/30.
 */
public class TestSelectActivity extends BasicFragmentActivity {
    private static final String[] colors = new String[]{
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
            "塔塔紫", "淘淘粉", "萌撕拉蓝", "班得瑞绿", "魔方橙",
    };

    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;

    private NormsSelectAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.test_select;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        adapter = new NormsSelectAdapter(mContext, Arrays.asList(colors));
        rv_content.setAdapter(adapter);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
