package com.putao.wd.me.child;

import android.os.Bundle;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.CleanableEditText;

import butterknife.Bind;

/**
 * 孩子信息
 * Created by guchenkai on 2015/12/25.
 */
public class ChildInfoActivity extends PTWDActivity {
    @Bind(R.id.et_nickname)
    CleanableEditText et_nickname;
    @Bind(R.id.tv_sex)
    TextView tv_sex;
    @Bind(R.id.tv_birthday)
    TextView tv_birthday;
    @Bind(R.id.tv_identity)
    TextView tv_identity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_child_info;
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
