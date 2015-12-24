package com.putao.wd.api;

import com.facebook.common.internal.DoNotStrip;
import com.putao.wd.GlobalApplication;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.model.http.request.RequestMethod;
import com.sunnybear.library.util.StringUtils;

/**
 * 用户接口
 * Created by guchenkai on 2015/12/8.
 */
public class UserApi {
    private static final String REQUEST_NICK_NAME = "nick_name";//昵称
    private static final String REQUEST_PROFILE = "profile";//简介
    private static final String REQUEST_EXT = "ext";
    private static final String REQUEST_FILENAME = "file_name";
    private static final String REQUEST_FILEHASH = "hash";
    private static final String REQUEST_HEAD_ICON = "userProfilePhoto";//头像
    private static final String REQUEST_PAGE = "page";//页码
    private static final String REQUEST_TYPE = "type";//类型
    private static final String REQUEST_MSG = "message";//提问问题

    private static final String REQUEST_NICKNAME = "nickName";

    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api.weidu.start.wang/" : "http://api-weidu.putao.com/";//基础url
    private static final String BASE_ACTION_URL = GlobalApplication.isDebug ? "http://api-event-dev.putao.com/" : "http://api-event-dev.putao.com/";//活动,消息,提问使用的地址

    public static void install(String base_url) {
//        BASE_URL = base_url;
    }

    /**
     * 登录接口（查询）
     */
    @Deprecated
    public static final String URL_LOGIN = BASE_URL + "login/verification";

    /**
     * 登录接口（查询）
     */
    @Deprecated
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
     * @param profile   简介
     * @param ext       图片后缀
     * @param filename  文件名
     * @param filehash  文件hash
     */
    public static Request userEdit(String nick_name, String profile, String ext, String filename, String filehash) {
        return PTWDRequestHelper.explore()
                .addParam(REQUEST_NICK_NAME, nick_name)
                .addParam(REQUEST_PROFILE, profile)
                .addParam(REQUEST_EXT, ext)
                .addParam(REQUEST_FILENAME, filename)
                .addParam(REQUEST_FILEHASH, filehash)
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

    /**
     * 获取上传UploadToken
     */
    public static final String URL_UPLOAD_TOKEN = BASE_URL + "get/upload/token";

    /**
     * 获取uploadToken
     */
    public static Request getUploadToken() {
        return PTWDRequestHelper.upload()
                .addParam(REQUEST_TYPE, "uploadPhotos")
                .build(RequestMethod.GET, URL_UPLOAD_TOKEN);
    }

    /**
     * 我参与的活动
     */
    public static final String URL_GET_ME_ACTIONS = BASE_ACTION_URL + "user/event/list";

    /**
     * 我参与的活动
     *
     * @param nick_name 昵称
     * @param head_icon 头像
     * @param page      页码
     */
    public static Request getMeActions(String nick_name, String head_icon, String page) {
        return PTWDRequestHelper.user()
                .addParam(REQUEST_NICKNAME, nick_name)
                .addParam(REQUEST_HEAD_ICON, head_icon)
                .addParam(REQUEST_PAGE, page)
                .build(RequestMethod.GET, URL_GET_ME_ACTIONS);
    }

    /**
     * 提交葡萄籽问题
     */
    public static final String URL_QUESTION_ADD = BASE_ACTION_URL + "question/add";

    /**
     * 提交葡萄籽问题
     *
     * @param nickName         昵称
     * @param userProfilePhoto 头像
     * @param msg              问题
     */
    public static Request questionAdd(String msg, String nickName, String userProfilePhoto) {
        FormEncodingRequestBuilder builder = PTWDRequestHelper.user()
                .addParam(REQUEST_MSG, msg);
        String uid = AccountHelper.getCurrentUid();
        if (StringUtils.isEmpty(uid))
            throw new RuntimeException("当前用户没有登录");
        builder.addParam(REQUEST_NICKNAME, nickName)
                .addParam(REQUEST_HEAD_ICON, userProfilePhoto);
        return builder.build(RequestMethod.POST, URL_QUESTION_ADD);
    }

    /**
     * 获取提问
     */
    public static final String URL_QUESTION_LIST = BASE_ACTION_URL + "user/question/list";

    /**
     * 提交葡萄籽问题
     *
     * @param nickName         昵称
     * @param userProfilePhoto 头像
     */
    public static Request questionList(String nickName, String userProfilePhoto) {
        FormEncodingRequestBuilder builder = PTWDRequestHelper.user();
        String uid = AccountHelper.getCurrentUid();
        if (!StringUtils.isEmpty(uid))
            throw new RuntimeException("当前用户没有登录");
        builder.addParam(REQUEST_NICKNAME, nickName)
                .addParam(REQUEST_HEAD_ICON, userProfilePhoto);
        return builder.build(RequestMethod.POST, URL_QUESTION_LIST);
    }
}
