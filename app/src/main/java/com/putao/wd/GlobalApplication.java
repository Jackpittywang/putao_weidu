package com.putao.wd;

import android.database.sqlite.SQLiteDatabase;

import com.putao.wd.account.AccountApi;
import com.putao.wd.db.dao.DaoMaster;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.util.AppUtils;
import com.sunnybear.library.util.SDCardUtil;

import cn.sharesdk.framework.ShareSDK;

/**
 * 全局变量
 * Created by guchenkai on 2015/11/25.
 */
public class GlobalApplication extends BasicApplication {
    private DaoMaster.OpenHelper mHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        installDataBase();
        ShareSDK.initSDK(getApplicationContext());//开启shareSDK
        AccountApi.updateDebugStatus(isDebug(), "1", "1109", "515d7213721042a5ac31c2de95d2c7a7");
    }

    /**
     * 安装数据库
     */
    private void installDataBase() {
        if (mHelper == null)
            if (isDebug())
                mHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "album.db", null);
            else
                mHelper = new DaoMaster.OpenHelper(getApplicationContext(), "album.db", null) {
                    @Override
                    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

                    }
                };
    }

    public DaoMaster.OpenHelper getDBHelper() {
        return mHelper;
    }


    @Override
    protected String getBuglyKey() {
        return "900013684";
    }

    @Override
    protected boolean isDebug() {
        return AppUtils.getVersionName(getApplicationContext()).startsWith("D");
    }

    @Override
    public String getPackageName() {
        return "com.putao.wd";
    }

    @Override
    protected String getNetworkCacheDirectoryPath() {
        return SDCardUtil.getSDCardPath();
    }

    @Override
    protected int getNetworkCacheSize() {
        return 20 * 1024 * 1024;
    }

    @Override
    protected int getNetworkCacheMaxAgeTime() {
        return 0;
    }
}
