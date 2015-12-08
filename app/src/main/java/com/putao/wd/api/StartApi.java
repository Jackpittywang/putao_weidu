package com.putao.wd.api;

import com.putao.wd.account.AccountHelper;
import com.putao.wd.model.AuditType;
import com.putao.wd.model.CommentType;
import com.putao.wd.model.CoolType;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.model.http.request.RequestMethod;
import com.sunnybear.library.util.StringUtils;

/**
 * 葡星圈接口
 * Created by guchenkai on 2015/12/3.
 */
public class StartApi {
    private static final String REQUEST_ACTION_ID = "event_id";//活动id
    private static final String REQUEST_INDEX = "index";//页码

    private static final String REQUEST_USER_ID = "user_id";//用户ID
    private static final String REQUEST_IDENTITY = "identity";//家长身份
    private static final String REQUEST_PHONE = "phone";//手机号码
    private static final String REQUEST_NICK_NAME = "nick_name";//昵称
    private static final String REQUEST_AGE = "age";//年龄
    private static final String REQUEST_WECHAT = "wechat";//微信
    private static final String REQUEST_PARENT_NAME = "parent_name";//家长姓名
    private static final String REQUEST_MSG = "msg";//留言

    private static final String REQUEST_TYPE = "type";//类型
    private static final String REQUEST_COMMENT_ID = "comment_id";//当评论类型为REPLY时comment_id是必须要传的

    private static final String REQUEST_STATUS = "status";//状态
    private static final String REQUEST_UID = "uid";//用户的唯一标识ID


    private static String BASE_URL;//基础url

    public static void install(String base_url) {
        BASE_URL = base_url;
    }

    /**
     * 广告图片url请求
     */
    public static final String URL_BANNER = BASE_URL + "banner/list";

    /**
     * 广告图片url请求
     */
    public static Request getBannerList() {
        return FormEncodingRequestBuilder.newInstance()
                .build(RequestMethod.GET, URL_BANNER);
    }

    /**
     * 活动详情（查询）
     */
    public static final String URL_ACTION_DETAIL = BASE_URL + "event/detail";

    /**
     * 活动详情（查询）
     *
     * @param action_id 活动ID
     */
    public static Request getActionDetail(String action_id) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(REQUEST_ACTION_ID, action_id)
                .build(RequestMethod.POST, URL_ACTION_DETAIL);
    }

    /**
     * 活动列表（查询）
     */
    public static final String URL_ACTION_LIST = BASE_URL + "event/list";

    /**
     * 活动列表（查询）
     *
     * @param index 获取第几页活动列表
     */
    public static Request getActionList(String index) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(REQUEST_INDEX, index)
                .build(RequestMethod.POST, URL_ACTION_LIST);
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
    public static Request getEnrollment(String action_id) {
        return FormEncodingRequestBuilder.newInstance()
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
     * @param user_id     用户ID
     * @param identity    家长身份
     * @param phone       手机号码
     * @param nick_name   昵称
     * @param age         年龄
     * @param wechat      微信
     * @param parent_name 家长姓名
     * @param msg         留言
     */
    public static Request participateAdd(String user_id, String identity, String phone, String nick_name, String age, String wechat, String parent_name, String msg) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(REQUEST_USER_ID, user_id)
                .addParam(REQUEST_IDENTITY, identity)
                .addParam(REQUEST_PHONE, phone)
                .addParam(REQUEST_NICK_NAME, nick_name)
                .addParam(REQUEST_AGE, age)
                .addParam(REQUEST_WECHAT, wechat)
                .addParam(REQUEST_PARENT_NAME, parent_name)
                .addParam(REQUEST_MSG, msg)
                .build(RequestMethod.POST, URL_PARTICIPATE_ADD);
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
        return FormEncodingRequestBuilder.newInstance()
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
     * @param user_id    用户ID
     * @param action_id  活动ID
     * @param msg        评论内容
     * @param type       评论的类型
     * @param comment_id 当评论类型为REPLY时comment_id是必须要传的
     */
    public static Request commentAdd(String user_id, String action_id, String msg, CommentType type, String comment_id) {
        FormEncodingRequestBuilder builder = FormEncodingRequestBuilder.newInstance()
                .addParam(REQUEST_USER_ID, user_id)
                .addParam(REQUEST_ACTION_ID, action_id)
                .addParam(REQUEST_MSG, msg)
                .addParam(REQUEST_TYPE, type.name());
        if (type == CommentType.REPLY && StringUtils.isEmpty(comment_id))
            throw new RuntimeException("当前评论类型为回复,comment_id必须传递");
        if (!StringUtils.isEmpty(comment_id))
            builder.addParam(REQUEST_COMMENT_ID, comment_id);
        return builder.build(RequestMethod.POST, URL_COMMENT_ADD);
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
    public static Request getCommentList(String action_id) {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(REQUEST_ACTION_ID, action_id)
                .build(RequestMethod.POST, URL_COMMENT_LIST);
    }

    /**
     * 赞（提交）
     */
    public static final String URL_COOL_ADD = BASE_URL + "cool/add";

    /**
     * 赞（提交）
     *
     * @param user_id    用户ID
     * @param action_id  活动ID
     * @param type       赞的类型
     * @param comment_id 当赞类型为COMMENT时comment_id 是必须要传的
     */
    public static Request coolAdd(String user_id, String action_id, CoolType type, String comment_id) {
        FormEncodingRequestBuilder builder = FormEncodingRequestBuilder.newInstance()
                .addParam(REQUEST_USER_ID, user_id)
                .addParam(REQUEST_ACTION_ID, action_id)
                .addParam(REQUEST_TYPE, type.name());
        if (type == CoolType.COMMENT && StringUtils.isEmpty(comment_id))
            throw new RuntimeException("当前赞类型为评论,comment_id必须传递");
        if (!StringUtils.isEmpty(comment_id))
            builder.addParam(REQUEST_COMMENT_ID, comment_id);
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
        return FormEncodingRequestBuilder.newInstance()
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
        return FormEncodingRequestBuilder.newInstance()
                .addParam(REQUEST_ACTION_ID, action_id)
                .build(RequestMethod.POST, URL_MAP);
    }

    /**
     * 提交葡萄籽问题
     */
    public static final String URL_QUESTION = BASE_URL + "question";

    /**
     * 提交葡萄籽问题
     *
     * @param msg 问题
     */
    public static Request question(String msg) {
        FormEncodingRequestBuilder builder = FormEncodingRequestBuilder.newInstance()
                .addParam(REQUEST_MSG, msg);
        String uid = AccountHelper.getCurrentUid();
        if (!StringUtils.isEmpty(uid))
            throw new RuntimeException("当前用户没有登录");
        builder.addParam(REQUEST_UID, uid);
        return builder.build(RequestMethod.POST, URL_QUESTION);
    }
}
