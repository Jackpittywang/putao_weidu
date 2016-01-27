package com.putao.wd;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.baidu.mapapi.SDKInitializer;
import com.putao.wd.account.AccountApi;
import com.putao.wd.db.CityDBManager;
import com.putao.wd.db.DataBaseManager;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.db.ProvinceDBManager;
import com.putao.wd.db.dao.DaoMaster;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.util.AppUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.SDCardUtils;
import com.youku.player.YoukuPlayerBaseConfiguration;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import cn.sharesdk.framework.ShareSDK;

/**
 * 全局变量
 * Created by guchenkai on 2015/11/25.
 */
public class GlobalApplication extends BasicApplication {
    public static final String ACTION_PUSH_SERVICE = "com.putao.wd.PUSH";

    private DaoMaster.OpenHelper mHelper;
    public static ConcurrentHashMap<String, String> mEmojis;//表情集合

    public static String shareImagePath;
    public static String resourcePath;

    public static YoukuPlayerBaseConfiguration mYoukuPlayerBaseConfiguration;

    @Override
    public void onCreate() {
        super.onCreate();
        shareImagePath = sdCardPath + File.separator + "screenshot.jpg";
        resourcePath = sdCardPath + File.separator + "patch";

        installDataBase();
        //安装通行证
        AccountApi.install("1", app_id, "515d7213721042a5ac31c2de95d2c7a7");
        //开启shareSDK
        ShareSDK.initSDK(getApplicationContext());//开启shareSDK
        //Baidu地图初始化
        SDKInitializer.initialize(getApplicationContext());
        //初始化优酷播放器
        try {
            mYoukuPlayerBaseConfiguration = new YoukuPlayerBaseConfiguration(getApplicationContext()) {
                /**
                 * 通过覆写该方法，返回“正在缓存视频信息的界面”，
                 * 则在状态栏点击下载信息时可以自动跳转到所设定的界面.
                 * 用户需要定义自己的缓存界面
                 */
                @Override
                public Class<? extends Activity> getCachingActivityClass() {
                    return null;
                }

                /**
                 * 通过覆写该方法，返回“已经缓存视频信息的界面”，
                 * 则在状态栏点击下载信息时可以自动跳转到所设定的界面.
                 * 用户需要定义自己的已缓存界面
                 */
                @Override
                public Class<? extends Activity> getCachedActivityClass() {
                    return null;
                }

                /**
                 * 配置视频的缓存路径，格式举例： /appname/videocache/
                 * 如果返回空，则视频默认缓存路径为： /应用程序包名/videocache/
                 */
                @Override
                public String configDownloadPath() {
                    return sdCardPath + File.separator + "videocache";
                }
            };
        } catch (Exception e) {
            Logger.e(e);
            e.printStackTrace();
        }
        //启动推送
//        startService(new Intent(ACTION_PUSH_SERVICE));
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
        return "putao_weidu";
    }

    @Override
    protected String getSdCardPath() {
        return SDCardUtils.getSDCardPath() + File.separator + getLogTag();
    }

    @Override
    protected String getNetworkCacheDirectoryPath() {
        return sdCardPath + File.separator + "http_cache";
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
     * 捕捉到异常就退出App
     *
     * @param ex 异常信息
     */
    @Override
    protected void onCrash(Throwable ex) {
        Logger.e("APP崩溃了,错误信息是" + ex.getMessage());
        ex.printStackTrace();
//        Intent intent=new Intent(this,MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        stopService(new Intent(ACTION_PUSH_SERVICE));
        ActivityManager.getInstance().killProcess(getApplicationContext());
    }

    /**
     * 有此至下为常量定义
     */
    public static final String MAP_EMOJI = "map_emoji";
    //===================preference key===========================
    public static final String PREFERENCE_KEY_UID = "uid";
    public static final String PREFERENCE_KEY_TOKEN = "token";
    public static final String PREFERENCE_KEY_NICKNAME = "nickname";
    public static final String PREFERENCE_KEY_EXPIRE_TIME = "expire_tim";
    public static final String PREFERENCE_KEY_REFRESH_TOKEN = "refresh_token";
    public static final String PREFERENCE_KEY_USER_INFO = "user_info";
    public static final String PREFERENCE_KEY_BABY_ID = "baby_id";

    public static final String PREFERENCE_KEY_IS_FIRST = "is_first";

    /**
     * H5页面定义Scheme
     */
    public static final class Scheme {
        public static final String OPENWEBVIEW = "openWebview";
        public static final String VIEWPIC = "viewPic";
    }
}
