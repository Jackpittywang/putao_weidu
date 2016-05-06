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

    public static GPushService instance() {
        return sInstance;
    }

    /**
     * 启动GPushservice
     * @param context
     * @param deviceId 设备id
     * @param appId appid
     */
    public static void startGPushService(Context context, String deviceId, String appId){
        Constants.setDeviceAndAppId(deviceId, appId);
        Intent i = new Intent(context, GPushService.class);
        context.startService(i);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initGPush();
    }

    public int initGPush() {
        String deviceId = Constants.DEVICE_ID;
        String appId = Constants.APP_ID;
        int initialCode = GPush.initGPush(Constants.DEFAULT_SERVER, Constants.PLATFORM, deviceId);
        if (initialCode != 0) return initialCode;

        int loginCode = GPush.loginGPush(Constants.GPUSH_KEY, Constants.GPUSH_TOKEN);
        Log.d(TAG, "login result: " + loginCode);
        if (loginCode != 0) return loginCode;

        int registerCode = GPush.registerGPush(appId);
        return registerCode;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int action = intent.getIntExtra(Constants.EXTRA_ACTION, -1);
        switch (action) {
            case Constants.ACTION_GPUSH_START:
                initGPush();
                break;
            case Constants.ACTION_GPUSH_STOP:
                GPush.uninitGPush();
                break;
            default:
                break;
        }
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        GPush.uninitGPush();

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
