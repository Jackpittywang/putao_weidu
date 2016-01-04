package com.putao.wd;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.SDKInitializer;
import com.putao.wd.account.AccountApi;
import com.putao.wd.db.CityDBManager;
import com.putao.wd.db.DataBaseManager;
import com.putao.wd.db.DistrictDBManager;
import com.putao.wd.db.ProvinceDBManager;
import com.putao.wd.db.dao.DaoMaster;
import com.putao.wd.util.DistrictUtils;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.controller.ActivityManager;
import com.sunnybear.library.model.http.DownloadFileTask;
import com.sunnybear.library.model.http.callback.DownloadFileCallback;
import com.sunnybear.library.model.http.callback.JSONObjectCallback;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.model.http.request.RequestMethod;
import com.sunnybear.library.util.AppUtils;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.PreferenceUtils;
import com.sunnybear.library.util.SDCardUtils;
import com.sunnybear.library.util.StringUtils;

import java.io.File;
import java.io.IOException;
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

    private DownloadFileTask task;
    public static String shareImagePath;
    public static String resourcePath;

    @Override
    public void onCreate() {
        super.onCreate();
        shareImagePath = sdCardPath + File.separator + "screenshot.jpg";
        resourcePath = sdCardPath + File.separator + "patch";

        installDataBase();
        //安装通行证
        AccountApi.install("1", app_id, "515d7213721042a5ac31c2de95d2c7a7");
        checkResource();
//        decompressionPatch();
//        parseEmoji();//表情解析
        //开启shareSDK
        ShareSDK.initSDK(getApplicationContext());//开启shareSDK
        //Baidu地图初始化
        SDKInitializer.initialize(getApplicationContext());
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

    /**
     * 解压资源包
     */
    private void decompressionPatch() {
        try {
            FileUtils.unZipInAsset(getApplicationContext(), "patch_10002_10003.zip", "patch", true);
            parseRegions();//解析城市列表
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析emoji表情
     */
    private void parseEmoji() {
        mEmojis = (ConcurrentHashMap<String, String>) getDiskFileCacheHelper().getAsSerializable(MAP_EMOJI);
        if (mEmojis == null)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mEmojis = new ConcurrentHashMap<>();
//                        FileUtils.unZipInAsset(getApplicationContext(), "emoji.zip", "", true);//解压表情包
//                        Logger.d("表情包解压完成");
                    File emoji = new File(resourcePath + File.separator + "biaoqing", "set.txt");
                    String source = FileUtils.readFile(emoji).replace("\uFEFF", "");
                    Logger.d(source);
                    String[] sources = source.split("\\n");
                    for (String s : sources) {
                        String[] s1 = s.split(",");
                        mEmojis.put(s1[0], resourcePath + File.separator + "biaoqing" + File.separator + s1[1]);
                    }
                    GlobalApplication.setEmojis(mEmojis);
                    getDiskFileCacheHelper().put(MAP_EMOJI, mEmojis);
                    Logger.d("表情包设置完成");
                }
            }).start();
        else
            GlobalApplication.setEmojis(mEmojis);
    }

    /**
     * 解析城市列表
     */
    private void parseRegions() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DistrictUtils.insertRegion();
            }
        }).start();
    }

    /**
     * 验证资源版本
     */
    private void checkResource() {
        getOkHttpClient().newCall(FormEncodingRequestBuilder.newInstance()
                .addParam("appid", app_id)
                .addParam("client_id", "1")
                .addParam("client_secret", "d3159867d3525452773206e189ef6966")
                .addParam("op_id", "1")
                .addParam("resource_version", PreferenceUtils.getValue("resource_version", "10000"))
                .addParam("game_id", "game_id")
                .addParam("app_version", AppUtils.getVersionName(getApplicationContext()).substring(1))
                .build(RequestMethod.GET, "http://source.start.wang/client/resource"))
                .enqueue(new JSONObjectCallback() {
                    @Override
                    public void onSuccess(String url, JSONObject result) {
                        Logger.d(result.toJSONString());
                        if (!StringUtils.equals(result.getString("status"), "200")) return;
                        JSONObject object = result.getJSONObject("data");
                        String last_resource_version = object.getString("last_resource_version");
                        String last_version = object.getString("last_version");
                        PreferenceUtils.save("resource_version", last_resource_version);
                        if (!StringUtils.equals(last_version, last_resource_version)) {
                            task = new DownloadFileTask("http://static.uzu.wang/source/app_5/resource/patch_" + last_version + "_" + last_resource_version + ".zip",
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
                                                    FileUtils.unZipInSdCard(task.getDownloadFile().getAbsolutePath(), "patch", true);
                                                    FileUtils.delete(task.getDownloadFile());
                                                    parseEmoji();//表情解析
                                                    parseRegions();//解析城市列表
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                        }
                                    });
                            task.execute();
                        } else {
                            decompressionPatch();
                            parseEmoji();//表情解析
                        }
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {

                    }
                });
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
        stopService(new Intent(ACTION_PUSH_SERVICE));
        ActivityManager.getInstance().killProcess(getApplicationContext());
    }

    /**
     * 有此至下为常量定义
     */
    private static final String MAP_EMOJI = "map_emoji";
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
