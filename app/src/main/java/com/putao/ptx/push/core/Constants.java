package com.putao.ptx.push.core;

/**
 * Created by yanguoqiang on 16/5/6.
 */
public class Constants {
    public static final String ACTION_PUSH = "com.putao.action.PUSH";
    public static final String EXTRA_APPID = "appId";
    public static final String EXTRA_PAYLOAD = "payload";

    public static final String DEFAULT_SERVER = "http://10.1.11.171:10090/mqtt_server";
    public static final String PLATFORM = "android";

    public static final String GPUSH_KEY = "0d87e77f509a419285db58f985836901";
    public static final String GPUSH_TOKEN = "2fa77ec72e6a4c338515bfef98b97c42";

    public static final String EXTRA_ACTION = "action";
    public static final int ACTION_GPUSH_START = 0;
    public static final int ACTION_GPUSH_STOP = 1;

    public static String DEVICE_ID = "";

    public static String APP_ID = "";

    /**
     * 设置deviceid 和 appid
     * @param deviceId
     * @param appId
     */
    public static void setDeviceAndAppId(String deviceId, String appId){
        DEVICE_ID = deviceId;
        APP_ID = appId;
    }

}
