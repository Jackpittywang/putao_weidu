package com.putao.ptx.push.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by yanguoqiang on 16/5/6.
 */
public class GPushService extends Service {

    private static final String TAG = GPushService.class.getName();
    private static GPush mPush;
    public static final int PUSH_SERVICE_CODE = -9999;


    private static GPushService sInstance = null;

    private boolean gpushConnected = false;
    // 是否在初始化中
    private boolean isInitGPushing = false;

    //失败最多尝试几次
    private int maxConnectCount = 3;
    private int tryCount = 0;

    public static GPushService instance() {
        return sInstance;
    }

    /**
     * 启动GPushservice
     *
     * @param context
     * @param deviceId 设备id
     * @param appId    appid
     */
    public static void startGPushService(Context context, String deviceId, String appId, boolean isDebug) {
        // Log.i(TAG, "start service called, deviceId is:"+deviceId +" app id is:"+ appId);
        Constants.setDeviceAndAppId(deviceId, appId, isDebug);
        Intent i = new Intent(context, GPushService.class);
        context.startService(i);
        GPushCallback.mContext = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initGPush();
    }


    private void initGPush() {
        Log.i(TAG, "init gpush service called");
        if (isInitGPushing == true) return;
        isInitGPushing = true;
        tryCount = 0;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (tryCount < maxConnectCount) {
                    // Log.i(TAG, "connect to gpush try count is:" + tryCount);
                    int res = initGPushHandle();
                    if (res == 0) break;
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                    }
                    tryCount = tryCount + 1;
                }
                isInitGPushing = false;
            }
        });
        thread.start();
    }

    public int initGPushHandle() {
        Log.i(TAG, "gpush initGPushHandle called");
        String deviceId = Constants.DEVICE_ID;
        String appId = Constants.APP_ID;
        int initialCode = GPush.initGPush(Constants.getServerUrl(), Constants.PLATFORM, deviceId);
        if (initialCode != 0) return initialCode;
        gpushConnected = true;

        int loginCode = GPush.loginGPush(Constants.getPushKey(), Constants.getPushToken());
        if (loginCode != 0) return loginCode;

        int registerCode = GPush.registerGPush(appId);
        return registerCode;
    }


    private void uninitGPush() {
        if (gpushConnected == false) return;
        Log.i(TAG, "uninit gpush called");
        gpushConnected = false;
        GPush.unregisterGPush(Constants.APP_ID);
        GPush.uninitGPush();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int action = intent.getIntExtra(Constants.EXTRA_ACTION, -1);
        Log.i(TAG, "gpush service onstartcommand called, action is:" + action);
        switch (action) {
            case Constants.ACTION_GPUSH_START:
                initGPush();
                break;
            case Constants.ACTION_GPUSH_STOP:
                uninitGPush();
                break;
            default:
                break;
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        uninitGPush();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
