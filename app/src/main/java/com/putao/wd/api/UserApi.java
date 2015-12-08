package com.putao.wd.api;

import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.RequestMethod;

/**
 * 用户接口
 * Created by guchenkai on 2015/12/8.
 */
public class UserApi {
    private static final String REQUEST_NICK_NAME = "nick_name";//昵称
    private static final String REQUEST_HEAD_IMG = "head_img";//头像图片名
    private static final String REQUEST_PROFILE = "profile";//简介

    private static String BASE_URL;//基础url

    public static void install(String base_url) {
        BASE_URL = base_url;
    }

    /**
     * 登录接口（查询）
     */
    public static final String URL_LOGIN = BASE_URL + "login/verification";

    /**
     * 登录接口（查询）
     */
    public static Request login() {
        return PTWDRequestHelper.explore()
                .build(RequestMethod.POST, URL_LOGIN);
    }

    /**
     * 完善用户信息（更新）
     */
    public static final String URL_USER_EDIT = BASE_URL + "user/edit";

    /**
     * 完善用户信息（更新）
     *
     * @param nick_name 昵称
     * @param head_img  头像图片名
     * @param profile   简介
     */
    public static Request userEdit(String nick_name, String head_img, String profile) {
        return PTWDRequestHelper.explore()
                .addParam(REQUEST_NICK_NAME, nick_name)
                .addParam(REQUEST_HEAD_IMG, head_img)
                .addParam(REQUEST_PROFILE, profile)
                .build(RequestMethod.POST, URL_USER_EDIT);
    }

    /**
     * 完善用户信息（查询）
     */
    public static final String URL_USER_INFO = BASE_URL + "user/info";

    /**
     * 完善用户信息（查询）
     */
    public static Request getUserInfo() {
        return PTWDRequestHelper.explore()
                .build(RequestMethod.POST, URL_USER_INFO);
    }
}
