package com.putao.wd.explore;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * 更多内容
 * Created by yanghx on 2016/1/12.
 */
public class ExploreMoreActivity extends PTWDActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_more;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
