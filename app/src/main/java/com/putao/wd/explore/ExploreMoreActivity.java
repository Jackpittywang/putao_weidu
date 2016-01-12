package com.putao.wd.explore;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.select.DynamicTitleBar;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;

import java.util.Arrays;

import butterknife.Bind;

/**
 * 更多内容
 * Created by yanghx on 2016/1/12.
 */
public class ExploreMoreActivity extends PTWDActivity {

    @Bind(R.id.tb_bar)
    DynamicTitleBar tb_bar;

    private String[] titles = new String[]{"牛人说", "玩物志", "葡萄+"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        tb_bar.addTitles(Arrays.asList(titles), 0);
        tb_bar.setOnTitleItemSelectedListener(new TitleBar.OnTitleItemSelectedListener() {
            @Override
            public void onTitleItemSelected(TitleItem item, int position) {
                ToastUtils.showToastLong(mContext, "点击了第" + position + "项");
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
