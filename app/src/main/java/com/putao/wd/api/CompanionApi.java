package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.RequestMethod;

/**
 * 创造接口
 * Created by zhanghao on 2015/1/28.
 */
public class CompanionApi {
    private static final String REQUEST_CAPTCHA_TOKEN = "captcha_token";//扫描受控设备二维码获取的参数
    private static final String REQUEST_PRODUCT_ID = "product_id";//游戏应用产品ID
    private static final String REQUEST_PAGES = "pages";//页码，没传默认为第一页
    private static final String REQUEST_ARTICLE_ID = "article_id";//文章id
    private static final String REQUEST_CID = "cid";//如果cid＝0 操作文章  如果cid != 0, 操作用户to_uid
    private static final String REQUEST_TO_UID = "to_uid";//操作用户id
    private static final String REQUEST_COMMENT = "comment";//作品内容
    private static final String REQUEST_PICS = "pics";//作品图片，多图
    private static final String REQUEST_SERVICE_ID = "service_id";//服务号id
    private static final String REQUEST_SEND_DATA = "send_data";//接收推送的数据包，转发给纬度服务器
    private static final String REQUEST_URL = "url";
    private static final String REQUEST_RWD_MID = "wd_mid";//点赞文章ID
    private static final String SERVICE_ID = "sid"; //服务号唯一service_id


    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api-weidu.ptdev.cn" : "http://api-weidu.putao.com";//基础url


    /**
     * 设备管理(需登录)
     */
    private static final String URL_ADD_SCAN = BASE_URL + "/scan/add";

    /**
     * 设备管理(需登录)
     *
     * @param captcha_token 扫描受控设备二维码获取的参数
     */
    public static Request addScan(String captcha_token) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_CAPTCHA_TOKEN, captcha_token)
                .build(RequestMethod.POST, URL_ADD_SCAN);
    }

    /**
     * 管理产品（查询）
     */
    private static final String URL_MANAGEMENT_INDEX = BASE_URL + "/management/index";

    /**
     * 管理产品（查询）
     *
     * @param product_id 游戏应用产品ID
     */
    public static Request getManagementIndex(String product_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_PRODUCT_ID, product_id)
                .build(RequestMethod.POST, URL_MANAGEMENT_INDEX);
    }

    /**
     * 陪伴首页
     */
    private static final String URL_SERVICE_RELATION = BASE_URL + "/service/user/relation";

    /**
     * 陪伴首页
     */
    public static Request getServiceUserRelation() {
        return PTWDRequestHelper.start()
                .build(RequestMethod.POST, URL_SERVICE_RELATION);
    }


    /**
     * 菜单列表
     */
    private static final String URL_COMPANY_SERVICE_MENUS = BASE_URL + "/company/service/menus";

    /**
     * 菜单列表
     *
     * @param service_id 页码，没传默认为第一页
     */
    public static Request getCompanyServiceMenus(String service_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_SERVICE_ID, service_id)
                .build(RequestMethod.POST, URL_COMPANY_SERVICE_MENUS);
    }

    /**
     * 公众号首页消息获取
     */
    private static final String URL_SERVICE_MESSAGE_LISTS = BASE_URL + "/service/message/lists";

    /**
     * 公众号首页消息获取
     *
     * @param send_data  接收推送的数据包，转发给纬度服务器
     * @param service_id 服务号唯一id
     */
    public static Request getServiceLists(String send_data, String service_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_SEND_DATA, send_data)
                .addParam(REQUEST_SERVICE_ID, service_id)
                .build(RequestMethod.POST, URL_SERVICE_MESSAGE_LISTS);
    }

    /**
     * 文章详情
     *
     * @param article_id 文章id
     */
    public static Request getCompanyArticle(String article_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_ARTICLE_ID, article_id)
                .build(RequestMethod.POST, URL_COMPANY_ARTICLE);
    }

    /**
     * 文章评论
     */
    private static final String URL_COMPANY_ARTICLE_COMMENTS = BASE_URL + "/company/article/comments";

    /**
     * 文章评论
     *
     * @param article_id 文章id
     * @param pages      页码，没传默认为第一页
     */
    public static Request getArticleComments(String article_id, int pages) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_ARTICLE_ID, article_id)
                .addParam(REQUEST_PAGES, pages + "")
                .build(RequestMethod.POST, URL_COMPANY_ARTICLE_COMMENTS);
    }

    /**
     * 发布评论
     */
    private static final String URL_COMPANY_ARTICLE_ADDCOMMENT = BASE_URL + "/company/article/addcomment";

    /**
     * 发布评论
     *
     * @param article_id 文章id
     * @param cid        如果cid＝0 评论文章  如果cid != 0, 回复用户to_uid
     * @param to_uid     回复用户id
     */
    public static Request addArticleComment(String article_id, String cid, String to_uid) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_ARTICLE_ID, article_id)
                .addParam(REQUEST_CID, cid)
                .addParam(REQUEST_TO_UID, to_uid)
                .build(RequestMethod.POST, URL_COMPANY_ARTICLE_ADDCOMMENT);
    }

    /**
     * 点赞
     */
    private static final String URL_COMPANY_ARTICLE_LIKE = BASE_URL + "/company/article/like";

    /**
     * 点赞
     *
     * @param article_id 文章id
     * @param cid        如果cid＝0 点赞文章  如果cid != 0, 点赞用户to_uid
     * @param to_uid     点赞用户id
     */
    public static Request addArticleLike(String article_id, String cid, String to_uid) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_ARTICLE_ID, article_id)
                .addParam(REQUEST_CID, cid)
                .addParam(REQUEST_TO_UID, to_uid)
                .build(RequestMethod.POST, URL_COMPANY_ARTICLE_LIKE);
    }



    /**
     * 二级回复评论 参数
     */
    public static final String URL_COMMENT_ADD = BASE_URL + "/set/second/comment";
    private static final String MESSAGE = "message";//首页评论内容
    private static final String ARTICLE_ID = "article_id";//首页文章ID
    private static final String COMMENT_ID = "comment_id";//首页评论ID


    /**
     * 二级回复评论
     *
     * @param message    首页评论内容
     * @param article_id 首页文章id
     * @param comment_id 首页评论id
     */
    public static Request addComment(String message, String article_id, String comment_id) {
        return PTWDRequestHelper.start()
                .addParam(MESSAGE, message)
                .addParam(ARTICLE_ID, article_id)
                .addParam(COMMENT_ID, comment_id)
                .build(RequestMethod.POST, URL_COMMENT_ADD);
    }




    /**
     * 二级回复评论点赞 参数
     */
    public static final String URL_COMMENT_PARISE_ADD = BASE_URL + "/set/second/like";

    private static final String REQUEST_COMMENT_WD_MID = "wd_mid";//一级评论的wd_mid
    private static final String REQUEST_COMMENT_SID = "sid";//服务号唯一service_id
    private static final String REQUEST_COMMENT_PCID = "pcid";//一级评论内的comment_id
    private static final String REQUEST_COMMENT_PAGE = "page";//页数
    private static final String REQUEST_COMMENT_SN = "2312fdsfdsa23532fdfdsf";//

    /**
     * 二级回复评论点赞
     * @param wd_mid
     * @param sid
     * @param comment_id
     * @return
     */
    public static Request addCommentPraise(String wd_mid,String sid,String comment_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_COMMENT_WD_MID, wd_mid)
                .addParam(REQUEST_COMMENT_SID, sid)
                .addParam(COMMENT_ID, comment_id)
                .build(RequestMethod.POST, URL_COMMENT_PARISE_ADD);

    }

    /**
     * 话题/创意收集/运营任务详情
     */
    private static final String URL_COMPANY_ACTIVE = BASE_URL + "/company/active";

    /**
     * 话题/创意收集/运营任务详情
     *
     * @param article_id 文章id
     */
    public static Request getCompanyActive(String article_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_ARTICLE_ID, article_id)
                .build(RequestMethod.POST, URL_COMPANY_ACTIVE);
    }

    /**
     * 参与话题/创意收集/运营任务
     */
    private static final String URL_COMPANY_ACTIVE_ATTEND = BASE_URL + "/company/active/attend";

    /**
     * 参与话题/创意收集/运营任务
     *
     * @param article_id 文章id
     * @param comment    作品内容
     * @param pics       作品图片，多图
     */
    public static Request attendCompanyActive(String article_id, String comment, String pics) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_ARTICLE_ID, article_id)
                .addParam(REQUEST_COMMENT, comment)
                .addParam(REQUEST_PICS, pics)
                .build(RequestMethod.POST, URL_COMPANY_ACTIVE_ATTEND);
    }


    /**
     * 文章详情
     */
    private static final String URL_COMPANY_ARTICLE = BASE_URL + "/second/comment";

    /**
     * 文章详情
     *
     * @param article_id 文章id
     */
    public static Request getCompanyArticleComment(String article_id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_ARTICLE_ID, article_id)
                .build(RequestMethod.POST, URL_COMPANY_ARTICLE);
    }

    /**
     * 取消绑定服务号
     */
    private static final String URL_COMPANY_SERVICE_BINDDEL = BASE_URL + "/service/bindDel";

    /**
     * 取消绑定服务号
     */
    public static Request cancelService(String service_id) {
        return PTWDRequestHelper.find()
                .addParam(REQUEST_SERVICE_ID, service_id)
                .build(RequestMethod.GET, URL_COMPANY_SERVICE_BINDDEL);
    }

    /**
     * 查询当前文章是否可以被评论、点赞数、评论数
     */
    private static final String URL_COMPANY_ARTICLE_PROPERTY = BASE_URL + "/article/property";

    /**
     * 查询当前文章是否可以被评论、点赞数、评论数
     */
    public static Request getProperty(String url) {
        return PTWDRequestHelper.find()
                .addParam(REQUEST_URL, url)
                .build(RequestMethod.GET, URL_COMPANY_ARTICLE_PROPERTY);
    }

    /**
     * 对文章点赞
     */
    private static final String URL_COMPANY_FIRST_LIKE = BASE_URL + "/set/first/like";

    /**
     * 对文章点赞
     */
    public static Request addCompanyFirstLike(String article_id, String service_id) {
        return PTWDRequestHelper.find()
                .addParam(REQUEST_RWD_MID, article_id)
                .addParam(SERVICE_ID, service_id)
                .build(RequestMethod.POST, URL_COMPANY_FIRST_LIKE);
    }


    /**
     * 绑定服务号参数
     */
    private static final String REQUEST_CODE = "code";//用户id

    /**
     * 绑定服务号
     */
    private static final String URL_COMPANY_SERVICE_BIND = BASE_URL + "/service/bind";

    /**
     * 参与话题/创意收集/运营任务
     *
     * @param sId 服务号id
     * @param code  服务号code
     */
    public static Request bindService(String sId, String code) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_SERVICE_ID, sId)
                .addParam(REQUEST_CODE, code)
                .build(RequestMethod.POST, URL_COMPANY_SERVICE_BIND);
    }



}
