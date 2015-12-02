package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.RequestMethod;

/**
 * 用户Api
 * Created by guchenkai on 2015/12/2.
 */
public class UserApi {
    public static final String BASE_URL = GlobalApplication.isDebug ? "http://api.putao.io/" : "http://api.putao.com/";

    public static void install() {
    }

    /**
     * 登录
     */
    public static final String URL_LOGIN = BASE_URL + "login";

    /**
     * 登录
     */
    public static Request login() {
        return PTWDRequestHelper.start(true).build(RequestMethod.POST, URL_LOGIN);
    }

    /**
     * 完善用户信息
     */
    public static final String URL_EDIT_INFO = BASE_URL + "editInfo";

    /**
     * 完善用户信息
     *
     * @param nickname 昵称
     * @param headimg  头像图片名
     * @param profile  简介
     */
    public static Request editInfo(String nickname, String headimg, String profile) {
        return PTWDRequestHelper.start(true)
                .addParam("nickName", nickname)
                .addParam("headImg", headimg)
                .addParam("profile", profile)
                .build(RequestMethod.POST, URL_EDIT_INFO);
    }
}
