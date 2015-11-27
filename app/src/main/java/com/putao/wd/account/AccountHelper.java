package com.putao.wd.account;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.sunnybear.library.util.PreferenceUtils;
import com.sunnybear.library.util.StringUtils;

/**
 * 通行证助手
 * Created by guchenkai on 2015/11/25.
 */
public final class AccountHelper {

    /**
     * 登录
     *
     * @param jsonObject jsonObject
     */
    public static void login(JSONObject jsonObject) {
        String uid = jsonObject.getString("uid");
        String token = jsonObject.getString("token");
        String nickname = jsonObject.getString("nickname");
        String expire_time = jsonObject.getString("expire_time");
        String refresh_token = jsonObject.getString("refresh_token");

        if (!StringUtils.isEmpty(uid))
            PreferenceUtils.save(GlobalApplication.PREFERENCE_KEY_UID, uid);
        if (!StringUtils.isEmpty(token))
            PreferenceUtils.save(GlobalApplication.PREFERENCE_KEY_TOKEN, token);
        if (!StringUtils.isEmpty(nickname))
            PreferenceUtils.save(GlobalApplication.PREFERENCE_KEY_NICKNAME, nickname);
        if (!StringUtils.isEmpty(expire_time))
            PreferenceUtils.save(GlobalApplication.PREFERENCE_KEY_EXPIRE_TIME, expire_time);
        if (!StringUtils.isEmpty(uid))
            PreferenceUtils.save(GlobalApplication.PREFERENCE_KEY_REFRESH_TOKEN, refresh_token);
    }

    /**
     * 登出
     */
    public static void logout() {
        PreferenceUtils.clear();
    }

    /**
     * 获取当前登录的Uid
     *
     * @return 当前登录的Uid
     */
    public static String getCurrentUid() {
        return PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_UID, "");
    }

    /**
     * 获取当前的token
     *
     * @return 当前登录的Uid
     */
    public static String getCurrentToken() {
        return PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_TOKEN, "");
    }
}
