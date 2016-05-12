package com.putao.ptx.push.core;

/**
 * Created by yanguoqiang on 16/5/6.
 */
public class Constants {
    public static final String ACTION_PUSH = "com.putao.action.PUSH";
    public static final String EXTRA_APPID = "appId";
    public static final String EXTRA_PAYLOAD = "payload";

    public static final String DEFAULT_SERVER_DEBUG = "http://10.1.11.171:10090/mqtt_server";
    public static final String DEFAULT_SERVER = "http://push-gateway.putaocloud.com/mqtt_server";

    public static final String PLATFORM = "android";

    //public static final String GPUSH_APP_ID_DEBUG = "15";
    //public static final String GPUSH_APP_ID = "1009";

    public static final String GPUSH_KEY_DEBUG = "1234567890";
    public static final String GPUSH_KEY = "47bbc691175b11e69411005056c00000";

    public static final String GPUSH_TOKEN_DEBUG = "asdfghjkl";
    public static final String GPUSH_TOKEN = "c4d7d7870fde40f4b054459bf1e72957";

    public static final String EXTRA_ACTION = "action";
    public static final int ACTION_GPUSH_START = 0;
    public static final int ACTION_GPUSH_STOP = 1;

    public static String DEVICE_ID = "";


    public static String APP_ID = "";

    public static boolean isDebug = true;

    /**
     * 设置deviceid, appid 是否是debug
     *
     * @param deviceId
     * @param appId
     * @param isdebug
     */
    public static void setDeviceAndAppId(String deviceId, String appId, boolean isdebug) {
        DEVICE_ID = deviceId;
        APP_ID = appId;
        isDebug = isdebug;
    }

    public static String getServerUrl() {
        if (isDebug) return DEFAULT_SERVER_DEBUG;
        else return DEFAULT_SERVER;
    }

    public static String getPushKey() {
        if (isDebug) return GPUSH_KEY_DEBUG;
        else return GPUSH_KEY;
    }

    public static String getPushToken() {
        if (isDebug) return GPUSH_TOKEN_DEBUG;
        else return GPUSH_TOKEN_DEBUG;
    }

}
