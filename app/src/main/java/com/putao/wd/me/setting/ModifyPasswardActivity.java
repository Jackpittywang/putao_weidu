package com.putao.wd.me.setting;

import android.os.Bundle;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * 修改密码
 * Created by yanghx on 2015/12/18.
 */
public class ModifyPasswardActivity extends PTWDActivity<GlobalApplication> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
