package com.putao.wd.start.user;

import android.os.Bundle;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.image.ImageDraweeView;

import butterknife.Bind;

/**
 * 用户详情
 * Created by guchenkai on 2015/12/24.
 */
public class UserDetailActivity extends PTWDActivity {
    @Bind(R.id.iv_action_icon)
    ImageDraweeView iv_user_icon;
    @Bind(R.id.tv_sign)
    TextView tv_sign;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
