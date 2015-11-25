package com.putao.wd.account;

import com.putao.wd.GlobalApplication;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.model.http.request.RequestMethod;
import com.sunnybear.library.util.AppUtils;

import java.security.MessageDigest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 通行证api
 * Created by guchenkai on 2015/11/2.
 */
public class AccountApi {
    public static final String PLATFORM_ID = "1";   //平台id
    public static final String CLIENT_TYPE = "2";  //设备类型

    public static String APP_ID;//app_id
    public static String VERSION;//版本号
    public static String BASE_URL;
    public static String SECRETKEY;

    public static void updateDebugStatus(boolean isDebug, String version, String appid, String secretkey) {
        BASE_URL = isDebug ? "https://account-api-dev.putao.com/" : "https://account-api.putao.com/";
        VERSION = version;
        APP_ID = appid;
        SECRETKEY = secretkey;
    }

    /**
     * 注册
     */
    public static final String URL_REGISTER = BASE_URL + AccountConstants.Url.URL_REGISTER;

    /**
     * 手机注册与否检测
     */
    public static final String URL_CHECK_MOBILE = BASE_URL + AccountConstants.Url.URL_CHECK_MOBILE;

    /**
     * 发送验证码
     */
    public static final String URL_SEND_VERIFY_CODE = BASE_URL + AccountConstants.Url.URL_SEND_VERIFY_CODE;

    /**
     * 忘记密码
     */
    public static final String URL_FORGET = BASE_URL + AccountConstants.Url.URL_FORGET;

    /**
     * 登录
     */
    public static final String URL_LOGIN = BASE_URL + AccountConstants.Url.URL_LOGIN;

    /**
     * 修改密码
     */
    public static final String URL_UPDATE_PASSWORD = BASE_URL + AccountConstants.Url.URL_UPDATE_PASSWORD;

    /**
     * 刷新token
     */
    public static final String URL_UPDATE_TOKEN = BASE_URL + AccountConstants.Url.URL_UPDATE_TOKEN;

    /**
     * 验证token
     */
    public static final String URL_CHECK_TOKEN = BASE_URL + AccountConstants.Url.URL_CHECK_TOKEN;

    /**
     * 获取昵称
     */
    public static final String URL_GET_NAICK_NAME = BASE_URL + AccountConstants.Url.URL_GET_NICK_NAME;

    /**
     * 设置昵称
     */
    public static final String URL_SET_NAICK_NAME = BASE_URL + AccountConstants.Url.URL_SET_NICK_NAME;

    /**
     * 注册
     *
     * @param mobile   账号
     * @param password 密码
     * @param code     验证码
     */
    public static Request register(String mobile, String password, String code) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(AccountConstants.Parameter.PARAM_MOBILE, mobile)
                .addParam(AccountConstants.Parameter.PARAM_PASSWD_ONCE, password)
                .addParam(AccountConstants.Parameter.PARAM_PASSWD_TWICE, password)
                .addParam(AccountConstants.Parameter.PARAM_CLIENT_TYPE, AccountApi.CLIENT_TYPE)
                .addParam(AccountConstants.Parameter.PARAM_VERSION, AccountApi.VERSION)
                .addParam(AccountConstants.Parameter.PARAM_PLATFORM_ID, AccountApi.PLATFORM_ID)
                .addParam(AccountConstants.Parameter.PARAM_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
                .addParam(AccountConstants.Parameter.PARAM_CODE, code)
                .addParam(AccountConstants.Parameter.PARAM_APPID, AccountApi.APP_ID)
                .build(RequestMethod.POST, URL_REGISTER);

    }

    /**
     * 手机注册与否检测
     *
     * @param mobile 账号
     */
    public static Request checkMobile(String mobile) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(AccountConstants.Parameter.PARAM_MOBILE, mobile)
                .addParam(AccountConstants.Parameter.PARAM_PLATFORM_ID, AccountApi.PLATFORM_ID)
                .build(RequestMethod.POST, URL_CHECK_MOBILE);
    }

    /**
     * 发送验证码
     *
     * @param mobile 账号
     * @param action 验证码发送原因
     */
    public static Request sendVerifyCode(String mobile, String action) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(AccountConstants.Parameter.PARAM_MOBILE, mobile)
                .addParam(AccountConstants.Parameter.PARAM_ACTION, action)
                .addParam(AccountConstants.Parameter.PARAM_APPID, AccountApi.APP_ID)
                .build(RequestMethod.POST, URL_SEND_VERIFY_CODE);
    }

    /**
     * 忘记密码
     *
     * @param mobile   账号
     * @param password 密码
     * @param code     验证码
     */
    public static Request forget(String mobile, String password, String code) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(AccountConstants.Parameter.PARAM_MOBILE, mobile)
                .addParam(AccountConstants.Parameter.PARAM_PASSWD_ONCE, password)
                .addParam(AccountConstants.Parameter.PARAM_PASSWD_TWICE, password)
                .addParam(AccountConstants.Parameter.PARAM_CODE, code)
                .addParam(AccountConstants.Parameter.PARAM_APPID, AccountApi.APP_ID)
                .build(RequestMethod.POST, URL_FORGET);
    }

    /**
     * 登录
     *
     * @param mobile   账号
     * @param password 密码
     */
    public static Request login(String mobile, String password) {
        Map<String, String> params = new HashMap<>();
        params.put(AccountConstants.Parameter.PARAM_MOBILE, mobile);
        params.put(AccountConstants.Parameter.PARAM_PASSWD, password);
        params.put(AccountConstants.Parameter.PARAM_CLIENT_TYPE, AccountApi.CLIENT_TYPE);
        params.put(AccountConstants.Parameter.PARAM_VERSION, AccountApi.VERSION);
        params.put(AccountConstants.Parameter.PARAM_PLATFORM_ID, AccountApi.PLATFORM_ID);
        params.put(AccountConstants.Parameter.PARAM_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()));
        params.put(AccountConstants.Parameter.PARAM_APPID, AccountApi.APP_ID);
        String sign = generateSign(params, SECRETKEY);

        return FormEncodingRequestBuilder.newInstance()
                .addParam(AccountConstants.Parameter.PARAM_MOBILE, mobile)
                .addParam(AccountConstants.Parameter.PARAM_PASSWD, password)
                .addParam(AccountConstants.Parameter.PARAM_CLIENT_TYPE, AccountApi.CLIENT_TYPE)
                .addParam(AccountConstants.Parameter.PARAM_VERSION, AccountApi.VERSION)
                .addParam(AccountConstants.Parameter.PARAM_PLATFORM_ID, AccountApi.PLATFORM_ID)
                .addParam(AccountConstants.Parameter.PARAM_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
                .addParam(AccountConstants.Parameter.PARAM_APPID, AccountApi.APP_ID)
                .addParam(AccountConstants.Parameter.PARAM_SIGN, sign)
                .build(RequestMethod.POST, URL_LOGIN);
    }

    /**
     * 修改密码
     *
     * @param uid           账号
     * @param oldPassword   旧密码
     * @param resetPassword 新密码确认
     * @param token         令牌
     */
    public static Request updatePassword(String uid, String oldPassword, String newPassword, String resetPassword, String token) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(AccountConstants.Parameter.PARAM_UID, uid)
                .addParam(AccountConstants.Parameter.PARAM_OLD_PASSWD, oldPassword)
                .addParam(AccountConstants.Parameter.PARAM_PASSWD_ONCE, newPassword)
                .addParam(AccountConstants.Parameter.PARAM_PASSWD_TWICE, resetPassword)
                .addParam(AccountConstants.Parameter.PARAM_VERSION, AccountApi.VERSION)
                .addParam(AccountConstants.Parameter.PARAM_PLATFORM_ID, AccountApi.PLATFORM_ID)
                .addParam(AccountConstants.Parameter.PARAM_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
                .addParam(AccountConstants.Parameter.PARAM_TOKEN, token)
                .addParam(AccountConstants.Parameter.PARAM_APPID, AccountApi.APP_ID)
                .build(RequestMethod.POST, URL_UPDATE_PASSWORD);
    }

    /**
     * 刷新token
     *
     * @param token         即将过期的token
     * @param refresh_token 刷新的token
     */
    public static Request updateToken(String token, String refresh_token) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(AccountConstants.Parameter.PARAM_APPID, AccountApi.APP_ID)
                .addParam(AccountConstants.Parameter.PARAM_TOKEN, token)
                .addParam(AccountConstants.Parameter.PARAM_REFRESH_TOKEN, refresh_token)
                .build(RequestMethod.POST, URL_UPDATE_TOKEN);
    }

    /**
     * 验证token
     *
     * @param token 令牌
     */
    public static Request checkToken(String token) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(AccountConstants.Parameter.PARAM_APPID, AccountApi.APP_ID)
                .addParam(AccountConstants.Parameter.PARAM_TOKEN, token)
                .build(RequestMethod.POST, URL_CHECK_TOKEN);
    }

    /**
     * 获取昵称
     *
     * @param token 令牌
     * @param uid   用户id
     */
    public static Request getNickName(String token, String uid) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(AccountConstants.Parameter.PARAM_TOKEN, token)
                .addParam(AccountConstants.Parameter.PARAM_UID, uid)
                .addParam(AccountConstants.Parameter.PARAM_APPID, AccountApi.APP_ID)
                .build(RequestMethod.POST, URL_GET_NAICK_NAME);
    }

    /**
     * 设置昵称
     *
     * @param token     令牌
     * @param uid       用户id
     * @param nick_name 昵称
     */
    public static Request setNickName(String token, String uid, String nick_name) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(AccountConstants.Parameter.PARAM_TOKEN, token)
                .addParam(AccountConstants.Parameter.PARAM_UID, uid)
                .addParam(AccountConstants.Parameter.PARAM_NICK_NAME, nick_name)
                .build(RequestMethod.POST, URL_SET_NAICK_NAME);
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    private static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty())
            return null;
        Map<String, String> sortMap = new TreeMap<>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 排序器
     */
    private static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    private static String generateSign(Map<String, String> param, String secretkey) {
        Map<String, String> map = sortMapByKey(param);
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String result = sb.delete(sb.length() - 1, sb.length()).append(secretkey).toString();
        return getMD5Str(result);
    }

    private static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString().toLowerCase();
    }
}
