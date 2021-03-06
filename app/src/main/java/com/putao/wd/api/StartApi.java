package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.base.PTWDRequestHelper;
import com.putao.wd.model.AuditType;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.model.http.request.RequestMethod;
import com.sunnybear.library.util.AppUtils;
import com.sunnybear.library.util.PreferenceUtils;

/**
 * 葡星圈接口
 * Created by guchenkai on 2015/12/3.
 */
public class StartApi {
    private static final String REQUEST_ACTION_ID = "eventId";//活动id
    private static final String REQUEST_INDEX = "index";//页码
    private static final String REQUEST_PAGE = "page";//页码

    private static final String REQUEST_USER_ID = "user_id";//用户ID
    private static final String REQUEST_IDENTITY = "identity";//家长身份
    private static final String REQUEST_PHONE = "phone";//手机号码
    private static final String REQUEST_NICK_NAME = "nickName";//昵称
    private static final String REQUEST_AGE = "age";//年龄
    private static final String REQUEST_WECHAT = "wechat";//微信
    private static final String REQUEST_PARENT_NAME = "parentName";//家长姓名
    private static final String REQUEST_CHILD_NAME = "nick_name";//宝宝昵称
    private static final String REQUEST_MSG = "message";//留言

    private static final String REQUEST_TYPE = "type";//类型
    private static final String REQUEST_COMMENT_ID = "commentId";//当评论类型为REPLY时comment_id是必须要传的
    private static final String REQUEST_USERPROFILEPHOTO = "userProfilePhoto";//用户头像

    private static final String REQUEST_STATUS = "status";//状态
    private static final String REQUEST_UID = "uid";//用户的唯一标识ID
    private static final String REQUEST_USER_LIKE = "is_user_like";//用户是否赞过


    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api-event-dev.putao.com/" : "http://api-event-dev.putao.com/";//基础url
    private static final String BASE_WEIDU_URL = GlobalApplication.isDebug ? "http://api-weidu.ptdev.cn/" : "http://api-weidu.putao.com/";//基础url

    public static void install(String base_url) {
//        BASE_URL = base_url;
    }

    /**
     * 广告图片url请求
     */
    public static final String URL_BANNER = BASE_URL + "banner/list";

    /**
     * 广告图片url请求
     */
    public static Request getBannerList() {
        return PTWDRequestHelper.start()
                .build(RequestMethod.GET, URL_BANNER);
    }

    /**
     * 活动详情（查询）
     */
    public static final String URL_ACTION_DETAIL = BASE_URL + "event/detail";

    /**
     * 活动详情（查询）
     *
     * @param action_id    活动ID
     * @param is_user_like 是否被赞过
     */
    public static Request getActionDetail(String action_id, boolean is_user_like) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_ACTION_ID, action_id)
                .addParam(REQUEST_USER_LIKE, is_user_like ? "1" : "0")
                .build(RequestMethod.POST, URL_ACTION_DETAIL);
    }

    /**
     * 活动列表（查询）
     */
    public static final String URL_ACTION_LIST = BASE_URL + "event/list";

    /**
     * 活动列表（查询）
     *
     * @param index  获取第几页活动列表
     * @param status 活动状态
     * @param type   活动新闻类型
     */
    public static Request getActionList(String index, String status, String type) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_PAGE, index)
                .addParam(REQUEST_STATUS, status)
                .addParam(REQUEST_TYPE, type)
                .build(RequestMethod.GET, URL_ACTION_LIST);
    }

    /**
     * 活动标签（查询）
     */
    public static final String URL_ACTION_LABEL = BASE_URL + "event/label";

    /**
     * 活动标签（查询）
     */
    public static Request getActionLabel() {
        return FormEncodingRequestBuilder.newInstance()
                .build(RequestMethod.GET, URL_ACTION_LABEL);
    }

    /**
     * 活动报名列表（查询）
     */
    public static final String URL_ENROLLMENT = BASE_URL + "event/enrollment";

    /**
     * 活动报名列表（查询）
     *
     * @param action_id 活动ID
     */
    public static Request getEnrollment(String page, String action_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_PAGE, page)
                .addParam(REQUEST_ACTION_ID, action_id)
                .build(RequestMethod.POST, URL_ENROLLMENT);
    }

    /**
     * 活动报名参加（提交）
     */
    public static final String URL_PARTICIPATE_ADD = BASE_URL + "event/participate/add";

    /**
     * 活动报名参加（提交）
     *
     * @param action_id   活动ID
     * @param identity    家长身份
     * @param phone       手机号码
     * @param nick_name   宝宝昵称
     * @param age         年龄
     * @param wechat      微信
     * @param parent_name 家长姓名
     * @param msg         留言
     */
    public static Request participateAdd(String action_id, String identity, String phone, String nick_name, String age, String wechat, String parent_name, String msg) {
        return PTWDRequestHelper.start()
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, GlobalApplication.app_id)
                .addParam(PTWDRequestHelper.REQUEST_KEY_START_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_UID, ""))
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_TOKEN, ""))
                .addParam(REQUEST_NICK_NAME, AccountHelper.getCurrentUserInfo().getNick_name())
                .addParam(REQUEST_USERPROFILEPHOTO, AccountHelper.getCurrentUserInfo().getHead_img())
                .addParam(REQUEST_ACTION_ID, action_id)
                .addParam(REQUEST_IDENTITY, identity)
                .addParam(REQUEST_PHONE, phone)
                .addParam(REQUEST_CHILD_NAME, nick_name)
                .addParam(REQUEST_AGE, age)
                .addParam(REQUEST_WECHAT, wechat)
                .addParam(REQUEST_PARENT_NAME, parent_name)
                .addParam(REQUEST_MSG, msg)
                .build(RequestMethod.POST, URL_PARTICIPATE_ADD);
    }

    /**
     * 活动详情（查询）
     *
     * @param action_id    活动ID
     * @param is_user_like 是否被赞过
     */
    public static Request getArticleDetail(String action_id, boolean is_user_like) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_ACTION_ID, action_id)
                .addParam(REQUEST_USER_LIKE, is_user_like ? "1" : "0")
                .build(RequestMethod.POST, URL_ACTION_DETAIL);
    }

    /**
     * 获取个人信息（查询）
     */
    public static final String URL_PROFILE = BASE_URL + "user/profile";

    /**
     * 获取个人信息（查询）
     *
     * @param user_id 用户ID
     */
    public static Request getProfile(String user_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_USER_ID, user_id)
                .build(RequestMethod.POST, URL_PROFILE);
    }

    /**
     * 评论与回复评论（提交）
     */
    public static final String URL_COMMENT_ADD = BASE_URL + "comment/add";

    /**
     * comment_id 是必须要传的
     *
     * @param action_id        活动ID
     * @param nickname         昵称
     * @param msg              评论内容
     * @param type             评论的类型
     * @param comment_id       当评论类型为REPLY或COMMENT
     * @param userProfilePhoto 头像url
     */
    public static Request commentAdd(String action_id, String nickname, String msg, String type, String comment_id, String userProfilePhoto) {
        FormEncodingRequestBuilder builder = PTWDRequestHelper.start()
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, GlobalApplication.app_id)
                .addParam(PTWDRequestHelper.REQUEST_KEY_START_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_UID, ""))
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_TOKEN, ""))
                .addParam(REQUEST_ACTION_ID, action_id)
                .addParam(REQUEST_NICK_NAME, nickname)
                .addParam(REQUEST_MSG, msg)
                .addParam(REQUEST_TYPE, type)
                .addParam(REQUEST_COMMENT_ID, comment_id)
                .addParam(REQUEST_USERPROFILEPHOTO, userProfilePhoto);
//        if (type == CommentType.REPLY && StringUtils.isEmpty(comment_id))
//            throw new RuntimeException("当前评论类型为回复,comment_id必须传递");
//        if (!StringUtils.isEmpty(comment_id))
//            builder.addParam(REQUEST_COMMENT_ID, comment_id);
        return builder.build(RequestMethod.POST, URL_COMMENT_ADD);
    }

    /**
     * 评论删除
     */
    public static final String URL_COMMENT_REMOVE = BASE_URL + "comment/remove";

    /**
     * @param comment_id
     */
    public static Request commentRemove(String comment_id) {
        FormEncodingRequestBuilder builder = PTWDRequestHelper.start()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_UID, ""))
                .addParam(REQUEST_COMMENT_ID, comment_id);
        return builder.build(RequestMethod.POST, URL_COMMENT_REMOVE);
    }

    /**
     * 活动评论列表（查询）
     */
    public static final String URL_COMMENT_LIST = BASE_URL + "comment/list";

    /**
     * 活动评论列表（查询）
     *
     * @param action_id 活动ID
     */
    public static Request getCommentList(String page, String action_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_PAGE, page)
                .addParam(REQUEST_ACTION_ID, action_id)
                .build(RequestMethod.POST, URL_COMMENT_LIST);
    }

    /**
     * 活动赞列表（查询）
     */
    public static final String URL_COOL_LIST = BASE_URL + "event/cool/list";

    /**
     * 活动赞列表（查询）
     *
     * @param action_id 活动ID
     */
    public static Request getCoolList(String page, String action_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_PAGE, page)
                .addParam(REQUEST_ACTION_ID, action_id)
                .build(RequestMethod.POST, URL_COOL_LIST);
    }

    /**
     * 赞（提交）
     */
    public static final String URL_COOL_ADD = BASE_URL + "cool/add";

    /**
     * 赞（提交）
     *
     * @param action_id        活动ID
     * @param nickname         昵称
     * @param type             赞的类型 EVENT或COMMENT 活动/评论
     * @param comment_id       当赞类型为COMMENT时comment_id 是必须要传的
     * @param userProfilePhoto 头像url
     */
    public static Request coolAdd(String action_id, String nickname, String type, String comment_id, String userProfilePhoto) {
        FormEncodingRequestBuilder builder = PTWDRequestHelper.start()
                .addParam(REQUEST_ACTION_ID, action_id)
                .addParam(REQUEST_NICK_NAME, nickname)
                .addParam(REQUEST_TYPE, type)
                .addParam(REQUEST_COMMENT_ID, comment_id)
                .addParam(REQUEST_USERPROFILEPHOTO, userProfilePhoto);
//        if (type == CoolType.COMMENT && StringUtils.isEmpty(comment_id))
//            throw new RuntimeException("当前赞类型为评论,comment_id必须传递");
//        if (!StringUtils.isEmpty(comment_id))
//            builder.addParam(REQUEST_COMMENT_ID, comment_id);
        return builder.build(RequestMethod.POST, URL_COOL_ADD);
    }

    /**
     * 审核活动报名用户（提交）
     */
    public static final String URL_AUDIT_USER = BASE_URL + "event/audit/user";

    /**
     * 审核活动报名用户（提交）
     *
     * @param user_id   用户ID
     * @param action_id 活动ID
     * @param type      审核类型
     */
    public static Request auditUser(String user_id, String action_id, AuditType type) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_USER_ID, user_id)
                .addParam(REQUEST_ACTION_ID, action_id)
                .addParam(REQUEST_STATUS, type.name())
                .build(RequestMethod.POST, URL_AUDIT_USER);
    }

    /**
     * 地图接口（查询）
     */
    public static final String URL_MAP = BASE_URL + "event/map";

    /**
     * 地图接口（查询）
     *
     * @param action_id 活动ID
     */
    public static Request getMap(String action_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_ACTION_ID, action_id)
                .build(RequestMethod.POST, URL_MAP);
    }

    /**
     * 消息列表
     */
    public static final String URL_NOTIFY_LIST = BASE_WEIDU_URL + "message/center";

    /**
     * 消息列表
     *
     * @param page 分页
     */
    public static Request getNotifyList(String page) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_PAGE, page)
                .build(RequestMethod.POST, URL_NOTIFY_LIST);
    }

    /**
     * 回复列表
     */
//    public static final String URL_REPLY_LIST = BASE_URL + "user/comment/reply";
//    public static final String URL_REPLY_LIST = BASE_WEIDU_URL + "message/comments";
    public static final String URL_REPLY_LIST = BASE_WEIDU_URL + "user/comments";

    /**
     * 回复列表
     *
     * @param page 分页
     */
    public static Request getReplyList(String page) {
        return PTWDRequestHelper.find()
                .addParam(REQUEST_PAGE, page)
                .build(RequestMethod.POST, URL_REPLY_LIST);
    }

    /**
     * 赞列表
     */
//    public static final String URL_PRAISE_LIST = BASE_URL + "user/liked";
    public static final String URL_PRAISE_LIST = BASE_WEIDU_URL + "user/likes";

    /**
     * 赞列表
     *
     * @param page 分页
     */
    public static Request getPraiseList(String page) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_PAGE, page)
                .build(RequestMethod.POST, URL_PRAISE_LIST);
    }

    /**
     * 提醒列表
     */
    public static final String URL_REMIND_LIST = BASE_WEIDU_URL + "message/remind";

    /**
     * 提醒列表
     *
     * @param page 分页
     */
    public static Request getRemindList(String page) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_PAGE, page)
                .build(RequestMethod.POST, URL_REMIND_LIST);
    }
}
