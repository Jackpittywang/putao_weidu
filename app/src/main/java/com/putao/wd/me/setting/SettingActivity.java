package com.putao.wd.me.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.jpush.JPushHeaper;
import com.putao.wd.me.address.AboutUsActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;


import butterknife.OnClick;

/**
 * 设置
 * create by wangou
 */
public class SettingActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    public static final String EVENT_LOGOUT = "logout";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.si_about_us, R.id.si_modify_password, R.id.tv_exit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.si_about_us://关于我们
                startActivity(AboutUsActivity.class);
                break;
            case R.id.si_modify_password://修改密码
//                Bundle bundle = new Bundle();
//                bundle.putBoolean("isresetpass", true);
                startActivity(ModifyPasswardActivity.class);
                break;
            case R.id.tv_exit://退出登录
                AccountHelper.logout();
                EventBusHelper.post(EVENT_LOGOUT, EVENT_LOGOUT);
                IndexActivity.isNotRefreshUserInfo = false;
                new JPushHeaper().setAlias(mContext, "");
                mContext.sendBroadcast(new Intent(GlobalApplication.Fore_Message));
                finish();
                break;
        }
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
    }
}
