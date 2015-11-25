package com.sunnybear.library;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.okhttp.OkHttpClient;
import com.sunnybear.library.model.http.OkHttpManager;
import com.sunnybear.library.util.DiskFileCacheHelper;
import com.sunnybear.library.view.image.ImagePipelineConfigFactory;

import butterknife.ButterKnife;

/**
 * 基础Application
 * Created by guchenkai on 2015/11/19.
 */
public abstract class BasicApplication extends Application {
    private static Context mContext;
    private static RefWatcher mRefWatcher;//内存泄露检查
    private static OkHttpClient mOkHttpClient;//OkHttp
    private static int maxAge;//网络缓存最大时间

    private static DiskFileCacheHelper mDiskFileCacheHelper;//磁盘文件缓存器

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //leakCanary初始化
        mRefWatcher = LeakCanary.install(this);
        //ButterKnife的Debug模式
        ButterKnife.setDebug(isDebug());
        //偏好设置文件初始化
//        Hawk.init(getApplicationContext(), getPackageName(), isDebug() ? LogLevel.FULL : LogLevel.NONE);
        //日志输出
//        Logger.init(getPackageName()).hideThreadInfo().setLogLevel(isDebug() ? Logger.LogLevel.FULL : Logger.LogLevel.NONE);
        //OkHttp初始化
        mOkHttpClient = OkHttpManager.getInstance(getNetworkCacheDirectoryPath(), getNetworkCacheSize()).build();
        //Fresco初始化
        Fresco.initialize(getApplicationContext(),
                ImagePipelineConfigFactory.getOkhttpImagePipelineConfig(getApplicationContext(), mOkHttpClient));
        //开启bugly
//        CrashReport.initCrashReport(getApplicationContext(), getBuglyKey(), isDebug());
        //网络缓存最大时间
        maxAge = getNetworkCacheMaxAgeTime();
        //磁盘文件缓存器
        mDiskFileCacheHelper = DiskFileCacheHelper.get(getApplicationContext(), "putao_weidu");
    }

    public static Context getInstance() {
        return mContext;
    }

    public static RefWatcher getRefWatcher() {
        return mRefWatcher;
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static int getMaxAge() {
        return maxAge;
    }

    public static DiskFileCacheHelper getDiskFileCacheHelper() {
        return mDiskFileCacheHelper;
    }

    /**
     * 设置腾讯bugly的AppKey
     *
     * @return 腾讯bugly的AppKey
     */
    protected abstract String getBuglyKey();

    /**
     * debug模式
     *
     * @return 是否开启
     */
    protected abstract boolean isDebug();

    /**
     * 填写工程包名
     *
     * @return 工程包名
     */
    public abstract String getPackageName();

    /**
     * 网络缓存文件夹路径
     *
     * @return 缓存文件夹路径
     */
    protected abstract String getNetworkCacheDirectoryPath();

    /**
     * 网络缓存文件大小
     *
     * @return 缓存文件大小
     */
    protected abstract int getNetworkCacheSize();

    /**
     * 网络缓存最大时间
     *
     * @return 缓存最大时间
     */
    protected abstract int getNetworkCacheMaxAgeTime();
}
