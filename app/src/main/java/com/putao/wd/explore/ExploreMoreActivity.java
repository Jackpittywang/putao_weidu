package com.putao.wd.explore;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.explore.adapter.MoreAdapter;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;
import com.sunnybear.library.view.select.DynamicTitleBar;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;

import java.io.Serializable;
import java.util.Arrays;

import butterknife.Bind;

/**
 * 更多内容
 * Created by yanghx on 2016/1/12.
 */
public class ExploreMoreActivity extends PTWDActivity {

    public static final String KEY_TAB = "key_title";

    @Bind(R.id.tb_bar)
    DynamicTitleBar tb_bar;
    @Bind(R.id.rv_more)
    LoadMoreRecyclerView rv_more;

    private String[] titles = new String[]{"牛人说", "玩物志", "葡萄+"};
    private MoreAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        int tab = args.getInt(KEY_TAB);
        tb_bar.addTitles(Arrays.asList(titles), tab);
        tb_bar.setOnTitleItemSelectedListener(new TitleBar.OnTitleItemSelectedListener() {
            @Override
            public void onTitleItemSelected(TitleItem item, int position) {
                ToastUtils.showToastLong(mContext, "点击了第" + position + "项");
            }
        });

        adapter = new MoreAdapter(mContext, null);
        rv_more.setAdapter(adapter);
        addListener();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        rv_more.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(Serializable serializable, int position) {
                startActivity(ExploreMoreDetailActivity.class);
            }
        });
    }

}
