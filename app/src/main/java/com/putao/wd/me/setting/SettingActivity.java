package com.putao.wd.me.setting;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.address.AboutUsActivity;
import com.putao.wd.user.LoginActivity;
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
public class SettingActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    private DownloadFileTask task;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        task = new DownloadFileTask("http://static.uzu.wang/source/app_5/resource/patch_10000_10002.zip",
                new DownloadFileCallback() {
                    @Override
                    public void onStart() {
                        Logger.d("下载开始");
                    }

                    @Override
                    public void onProgress(int progress, long networkSpeed) {
                        Logger.d("progress:" + progress + ",networkSpeed:" + networkSpeed);
                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        Logger.i(isSuccess ? "下载成功" : "下载失败");
                        if (isSuccess)
                            try {
                                FileUtils.unZipInSdCard(task.getDownloadFile().getAbsolutePath(), task.getDownloadFileName(), true);
                                FileUtils.delete(task.getDownloadFile());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }
                });
        task.execute();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    //-------|关于我们----------|修改密码
    @OnClick({R.id.si_about_us, R.id.si_modify_password})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.si_about_us:
                startActivity(AboutUsActivity.class);
                break;//关于我们
            case R.id.si_modify_password:
                Bundle bundle = new Bundle();
                bundle.putBoolean("isresetpass", true);
                startActivity(LoginActivity.class, bundle);
                break;//修改密码
        }
    }
}
