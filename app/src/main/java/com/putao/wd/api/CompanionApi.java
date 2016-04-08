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
    private static final String URL_COMPANY = BASE_URL + "/company";

    /**
     * 陪伴首页
     */
    public static Request getCompany() {
        return PTWDRequestHelper.start()
                .build(RequestMethod.POST, URL_COMPANY);
    }

    /**
     * 葡萄黑板报
     */
    private static final String URL_COMPANY_BLACKBOARD = BASE_URL + "/company/blackboard";

    /**
     * 葡萄黑板报
     *
     * @param pages 页码，没传默认为第一页
     */
    public static Request getCompanyBlackboard(int pages) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_PAGES, pages + "")
                .build(RequestMethod.POST, URL_COMPANY_BLACKBOARD);
    }

    /**
     * 葡萄黑板报
     */
    private static final String URL_COMPANY_ARTICLE = BASE_URL + "/company/article";

    /**
     * 葡萄黑板报
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

}