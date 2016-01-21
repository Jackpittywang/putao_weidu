package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.model.http.request.RequestMethod;
import com.sunnybear.library.util.AppUtils;

/**
 * 探索号接口
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreApi {
    private static final String REQUEST_SLAVE_DEVICE_ID = "slave_device_id";//关联设备唯一id，无关注设备时不请求
    private static final String REQUEST_START_TIME = "start_time";//起始时间的时间戳
    private static final String REQUEST_END_TIME = "end_time";//结束时间的时间戳
    private static final String REQUEST_PRODUCT_ID = "product_id";//产品id
    private static final String REQUEST_EDIT_LIST = "edit_list";//用户修改的数据列表
    private static final String REQUEST_PLOT_ID = "plot_id";//剧情理念详情id
    private static final String REQUEST_PAGE = "page";//页码

    private static final String LIMIT = "limit";//首页数据条目数量
    private static final String ARTICLE_ID = "article_id";//首页文章ID
    private static final String COMMENT_ID = "comment_id";//首页评论ID
    private static final String COMMENT_MSG = "comment_msg";//首页评论内容

    private static final String CAPTCHA_TOKEN = "captcha_token";//扫描受控设备二维码获取的参数
    private static final String REQUEST_MASTER_DEVICE_NAME = "master_device_name";//控制设备名称
    private static final String STATUS = "status";//控制设备名称

    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api.weidu.start.wang/" : "http://api.weidu.start.wang/";//基础url

    public static void install(String base_url) {
//        BASE_URL = base_url;
    }

    /**
     * 成长日记首页接口（查询）
     */
    public static final String URL_DIARY_INDEX = BASE_URL + "diary/index";

    /**
     * 成长日记首页接口（查询）
     */
    public static Request getDiaryIndex(String page) {
        FormEncodingRequestBuilder builder = PTWDRequestHelper.explore()
                .addParam(REQUEST_SLAVE_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
                .addParam(REQUEST_PAGE, page);
//                .addParam(REQUEST_START_TIME, start_time)
//                .addParam(REQUEST_END_TIME, end_time);
//        if (!StringUtils.isEmpty(slave_device_id))
//            builder.addParam(REQUEST_SLAVE_DEVICE_ID, slave_device_id);
        return builder.build(RequestMethod.GET, URL_DIARY_INDEX);
    }

//    /**
//     * 成长日记首页接口（查询）(暂时使用)
//     */
//    public static Request getDiaryIndex(String page) {
//        FormEncodingRequestBuilder builder = FormEncodingRequestBuilder.newInstance()
//                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, "60000529")
//                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
//                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
//                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, GlobalApplication.app_id)
//                .addParam(REQUEST_SLAVE_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
//                .addParam(REQUEST_PAGE, page);
////                .addParam(REQUEST_START_TIME, start_time)
////                .addParam(REQUEST_END_TIME, end_time);
////        if (!StringUtils.isEmpty(slave_device_id))
////            builder.addParam(REQUEST_SLAVE_DEVICE_ID, slave_device_id);
//        return builder.build(RequestMethod.GET, URL_DIARY_INDEX);
//    }

    /**
     * 扫码关注产品（添加）
     */
    public static final String URL_SCAN_ADD = BASE_URL + "scan/add";

    /**
     * 扫码关注产品（添加）
     *
     * @param slave_device_id 受控设备id号
     * @param product_id      对应产品idc
     */
    public static Request scanAdd(String slave_device_id, String product_id) {
        return PTWDRequestHelper.explore()
                .addParam(REQUEST_SLAVE_DEVICE_ID, slave_device_id)
                .addParam(REQUEST_PRODUCT_ID, product_id)
                .build(RequestMethod.POST, URL_SCAN_ADD);
    }

    public static final String url_scan_add_deivce = BASE_URL + "get/captcha";

    /**
     * 扫码关注产品（维度客户端添加）
     *
     * @param captcha_token 受控设备id号
     */
    public static Request addDevice(String captcha_token) {
        return PTWDRequestHelper.explore()
                .addParam(CAPTCHA_TOKEN, captcha_token)
                .addParam(REQUEST_MASTER_DEVICE_NAME, AppUtils.getDeviceName())
                .build(RequestMethod.POST, URL_SCAN_ADD);
    }

    /**
     * 管理产品（查询）
     */
    public static final String URL_MANAGEMENT_LIST = BASE_URL + "management/index";

    /**
     * 管理产品（查询）
     */
    public static Request getManagement() {
//        return PTWDRequestHelper.explore()
//                .build(RequestMethod.POST, URL_MANAGEMENT_LIST);
        FormEncodingRequestBuilder builder = FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, GlobalApplication.app_id);
        return builder.build(RequestMethod.POST, URL_MANAGEMENT_LIST);
    }

    /**
     * 管理产品（保存）
     */
    public static final String URL_MANAGEMENT_EDIT = BASE_URL + "management/edit";

    /**
     * 管理产品（保存）
     *
     * @param edit_list 用户修改的数据列表(JSON类型)
     */
    public static Request managementEdit(String edit_list) {
        return PTWDRequestHelper.explore()
                .addParam(REQUEST_EDIT_LIST, edit_list)
                .build(RequestMethod.POST, URL_MANAGEMENT_EDIT);
    }

    /**
     * 立即停止使用所有产品
     */
    public static final String URL_MANAGEMENT_SETALL = BASE_URL + "management/setall";

    /**
     * 立即停止使用所有产品
     */
    public static Request managementSetall() {
        return PTWDRequestHelper.explore()
                .addParam(STATUS, "-1")
                .build(RequestMethod.POST, URL_MANAGEMENT_SETALL);
    }


    /**
     * 剧情理念详情（查询）
     */
    public static final String URL_PLOT_DETAILS = BASE_URL + "plot/details";

    /**
     * 剧情理念详情id
     *
     * @param plot_id 剧情理念详情id
     */
    public static Request getPlotDetails(String plot_id) {
        return PTWDRequestHelper.explore()
                .addParam(REQUEST_PLOT_ID, plot_id)
                .build(RequestMethod.POST, URL_PLOT_DETAILS);
    }

    /**
     *
     *
     *v1.1.0
     *
     */

    /**
     * 查询用户是否关联产品
     */
    public static final String URL_DIARY_APP = BASE_URL + "diary/app";

    /**
     * 查询用户是否关联产品
     */
    public static Request getDiaryApp() {
        return PTWDRequestHelper.explore()
                .build(RequestMethod.POST, URL_DIARY_APP);
    }

    /**
     * 查询对应游戏下的陪伴数据
     */
    public static Request getDiaryData(String productId) {
        return PTWDRequestHelper.explore()
                .addParam(REQUEST_PRODUCT_ID, productId)
                .build(RequestMethod.POST, URL_DIARY_DATA);
    }

    /**
     * 查询对应游戏下的陪伴数据
     */
    public static final String URL_DIARY_DATA = BASE_URL + "diary/data";

    /**
     * 首页七条列表
     */
    public static final String URL_ARTICLE_LIST= BASE_URL + "article/list";

    /**
     * 首页七条列表
     */
    public static Request getArticleList() {
        return PTWDRequestHelper.explore()
                .addParam(LIMIT, "7")
                .build(RequestMethod.GET, URL_ARTICLE_LIST);
    }

    /**
     * 首页七条详情
     */
    public static final String URL_ARTICLE_DETAIL= BASE_URL + "article/detail";

    /**
     * 首页七条列表(和列表数据相同)
     */
    public static Request getDetailList() {
        return PTWDRequestHelper.explore()
                .build(RequestMethod.GET, URL_ARTICLE_DETAIL);
    }

    /**
     * 评论列表
     */
    public static final String URL_COMMENT_LIST = BASE_URL + "article/comment/list";

    /**
     * 评论列表
     *
     * @param article_id 首页文章id
     * @param page 首页评论页面
     */
    public static Request getCommentList(String page,String article_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_PAGE, page)
                .addParam(ARTICLE_ID, article_id)
                .build(RequestMethod.GET, URL_COMMENT_LIST);
    }
    /**
     * 添加评论
     */
    public static final String URL_COMMENT_ADD = BASE_URL + "article/comment/add";

    /**
     * 添加评论
     *
     * @param article_id 首页文章id
     * @param msg 首页评论内容
     */
    public static Request addComment(String msg,String article_id) {
        return PTWDRequestHelper.start()
                .addParam(COMMENT_MSG, msg)
                .addParam(ARTICLE_ID, article_id)
                .build(RequestMethod.POST, URL_COMMENT_ADD);
    }
    /**
     * 回复评论
     *
     * @param msg 首页评论内容
     * @param article_id 首页文章id
     * @param comment_id 首页评论id
     */
    public static Request addComment(String msg,String article_id,String comment_id) {
        return PTWDRequestHelper.start()
                .addParam(COMMENT_MSG, msg)
                .addParam(ARTICLE_ID, article_id)
                .addParam(COMMENT_ID, comment_id)
                .build(RequestMethod.POST, URL_COMMENT_ADD);
    }
    /**
     * 删除评论
     */
    public static final String URL_COMMENT_DELETE = BASE_URL + "article_comment_delete";

    /**
     * 删除评论
     *
     * @param comment_id 评论id
     */
    public static Request deleteComment(String comment_id) {
        return PTWDRequestHelper.start()
                .addParam(ARTICLE_ID, comment_id)
                .build(RequestMethod.POST, URL_COMMENT_DELETE);
    }
}
