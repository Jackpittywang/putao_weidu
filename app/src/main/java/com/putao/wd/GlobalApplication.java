package com.putao.wd;

import android.database.sqlite.SQLiteDatabase;

import com.putao.wd.account.AccountApi;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.api.StartApi;
import com.putao.wd.api.StoreApi;
import com.putao.wd.api.UserApi;
import com.putao.wd.db.AddressDBManager;
import com.putao.wd.db.CityDBManager;
import com.putao.wd.db.DataBaseManager;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.db.ProvinceDBManager;
import com.putao.wd.db.dao.DaoMaster;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.util.AppUtils;
import com.sunnybear.library.util.SDCardUtils;

import java.io.File;

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
        AccountApi.install("1", app_id, "515d7213721042a5ac31c2de95d2c7a7");

//        WechatShareTools.regToWX(getApplicationContext());
//        installApi();
    }

    /**
     * 安装api接口
     */
    private void installApi() {
        StartApi.install(isDebug() ? "http://api.event.start.wang/" : "http://api.event.start.wang/");
        StoreApi.install(isDebug() ? "http://api.sotre.putao.com/" : "http://api.sotre.putao.com/");
        ExploreApi.install(isDebug() ? "http://api.weidu.start.wang/" : "http://api.weidu.start.wang/");
        UserApi.install(isDebug() ? "http://api.weidu.start.wang/" : "http://api.weidu.start.wang/");
    }

    /**
     * 安装数据库
     */
    private void installDataBase() {
        if (mHelper == null)
            if (isDebug())
                mHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "putao-weidu.db", null);
            else
                mHelper = new DaoMaster.OpenHelper(getApplicationContext(), "putao-weidu.db", null) {
                    @Override
                    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

                    }
                };
    }

    /**
     * 获取DataBaseManager
     *
     * @param clazz 类型
     * @return DataBaseManager实例
     */
    public DataBaseManager getDataBaseManager(Class<? extends DataBaseManager> clazz) {
        switch (clazz.getSimpleName()) {
            case "AddressDBManager":
                return AddressDBManager.getInstance(mHelper);
            case "CityDBManager":
                return CityDBManager.getInstance(mHelper);
            case "DistrictDBManager":
                return DistrictDBManager.getInstance(mHelper);
            case "ProvinceDBManager":
                return ProvinceDBManager.getInstance(mHelper);
        }
        return null;
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
    protected String getLogTag() {
        return "putao-weidu";
    }

    @Override
    protected String getSdCardPath() {
        return SDCardUtils.getSDCardPath() + File.separator + getLogTag();
    }

    @Override
    protected String getNetworkCacheDirectoryPath() {
        return SDCardUtils.getSDCardPath();
    }

    @Override
    protected int getNetworkCacheSize() {
        return 20 * 1024 * 1024;
    }

    @Override
    protected int getNetworkCacheMaxAgeTime() {
        return 0;
    }

    /**
     * 有此至下为常量定义
     */
    //===================preference key============================
    public static final String PREFERENCE_KEY_UID = "uid";
    public static final String PREFERENCE_KEY_TOKEN = "token";
    public static final String PREFERENCE_KEY_NICKNAME = "nickname";
    public static final String PREFERENCE_KEY_EXPIRE_TIME = "expire_tim";
    public static final String PREFERENCE_KEY_REFRESH_TOKEN = "refresh_token";
}
