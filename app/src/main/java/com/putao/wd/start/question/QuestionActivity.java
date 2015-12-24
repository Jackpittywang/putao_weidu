package com.putao.wd.start.question;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.address.AboutUsActivity;
import com.putao.wd.me.setting.ModifyPasswardActivity;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.model.http.DownloadFileTask;
import com.sunnybear.library.model.http.callback.DownloadFileCallback;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.Logger;

import java.io.IOException;

import butterknife.OnClick;

/**
 * 设置
 * create by wangou
 */
public class QuestionActivity extends PTWDActivity<GlobalApplication> {
    public static final String EVENT_LOGOUT = "logout";
    private DownloadFileTask task;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myquestion;
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
