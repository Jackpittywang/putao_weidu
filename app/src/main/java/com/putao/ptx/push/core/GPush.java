package com.putao.ptx.push.core;

/**
 * Created by yanguoqiang on 16/5/6.
 */
public class GPush {

    static {
        System.loadLibrary("gpush_client");
        System.loadLibrary("gpush");
    }


    /**
     * 初始化GPUSH
     * @param httpUrl 服务器地址
     * @param platform 　平台
     * @param deviceToken 设备id
     * @return 0 成功，否则失败
     */
    public static int initGPush(String httpUrl, String platform, String deviceToken){
        return initial(httpUrl, platform, deviceToken);
    }

    /**
     * 初始化完后，登陆GPUSH
     * @param secretKey 登录key由GPUSH开发组提供
     * @param secretToken 登录token由GPUSH开发组提供
     * @return 0 成功，否则失败
     */
    public static int loginGPush(String secretKey, String secretToken){
        return login(secretKey, secretToken);
    }

    /**
     * 登录完后注册app
     * @param appId
     * @return 0 成功，否则失败
     */
    public static int registerGPush(String appId){
        return reg(appId);
    }

    /**
     * 取消注册
     * @param appId
     * @return 0 成功，否则失败
     */
    public static int unregisterGPush(String appId){
        return unreg(appId);
    }

    /**
     * app 销毁的时候uninit gpush
     * @return
     */
    public static int uninitGPush(){
        return uninitial();
    }


    private static native int initial(String httpUrl, String platform, String deviceToken);

    private static native int login(String secretKey, String secretToken);

    private static native int reg(String appId);

    private static native int unreg(String appId);

    private static native int uninitial();


}
