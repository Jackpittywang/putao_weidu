package com.putao.wd;

import android.database.sqlite.SQLiteDatabase;

import com.baidu.mapapi.SDKInitializer;
import com.putao.wd.account.AccountApi;
import com.putao.wd.db.AddressDBManager;
import com.putao.wd.db.CityDBManager;
import com.putao.wd.db.DataBaseManager;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.db.ProvinceDBManager;
import com.putao.wd.db.dao.DaoMaster;
import com.putao.wd.util.RegionUtils;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.util.AppUtils;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ResourcesUtils;
import com.sunnybear.library.util.SDCardUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.sharesdk.framework.ShareSDK;

/**
 * 全局变量
 * Created by guchenkai on 2015/11/25.
 */
public class GlobalApplication extends BasicApplication {
    private DaoMaster.OpenHelper mHelper;
    private Map<String, String> mEmojis;//表情集合

    @Override
    public void onCreate() {
        super.onCreate();
        mEmojis = new ConcurrentHashMap<>();
        installDataBase();
        //安装通行证
        AccountApi.install("1", app_id, "515d7213721042a5ac31c2de95d2c7a7");
        parseEmoji();//表情解析
//        parseRegions();//解析城市列表
        //开启shareSDK
        ShareSDK.initSDK(getApplicationContext());//开启shareSDK
        //Baidu地图初始化
        SDKInitializer.initialize(getApplicationContext());
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

    /**
     * 解析emoji表情
     */
    private void parseEmoji() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ResourcesUtils.unZip(getApplicationContext(), "emoji.zip", "", true);//解压表情包
                    Logger.d("表情包解压完成");
                    File emoji = new File(GlobalApplication.sdCardPath + File.separator + "emoji", "set.txt");
                    String source = FileUtils.readFile(emoji).replace("\uFEFF", "");
                    Logger.d(source);
                    String[] sources = source.split("\\n");
                    for (String s : sources) {
                        String[] s1 = s.split(",");
                        mEmojis.put(s1[0], GlobalApplication.sdCardPath + File.separator + "emoji" + File.separator + s1[1]);
                    }
                    GlobalApplication.setEmojis(mEmojis);
                } catch (IOException e) {
                    Logger.e("表情包解压完成", e);
                }
            }
        }).start();
    }

    /**
     * 解析城市列表
     */
    private void parseRegions() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RegionUtils.insertRegion();
            }
        }).start();
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
        return "putao_weidu";
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
    public static final String PREFERENCE_KEY_USER_INFO = "user_info";
}
