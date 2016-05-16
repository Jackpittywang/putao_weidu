package com.putao.wd.pt_me.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.IndexActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.pt_me.address.AboutUsActivity;
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

    @OnClick({R.id.si_about_us, R.id.si_modify_password, R.id.tv_exit, R.id.si_clear})
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
                YouMengHelper.onEvent(mContext, YouMengHelper.UserHome_setup_logout);
                AccountHelper.logout();
                EventBusHelper.post(EVENT_LOGOUT, EVENT_LOGOUT);
                IndexActivity.isNotRefreshUserInfo = false;
                //重置陪伴页面
                EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
                //立即关闭内部推送
//                mContext.sendBroadcast(new Intent(GlobalApplication.OUT_FORE_MESSAGE_SOON));
                //清除红点
                EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_ME_TAB);
                EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION_TAB);
                finish();
                break;
            case R.id.si_clear:
//                CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
//                dataBaseManager.deleteContent();
//                ToastUtils.showToastShort(mContext, "清除成功");
                break;
        }
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
    }
}
