package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.RequestMethod;

/**
 * 创造接口
 * Created by 章浩 on 2015/1/28.
 */
public class CreateApi {
    private static final String REQUEST_SLAVE_DEVICE_ID = "slave_device_id";//关联设备唯一id，无关注设备时不请求
    private static final String REQUEST_START_TIME = "start_time";//起始时间的时间戳
    private static final String REQUEST_END_TIME = "end_time";//结束时间的时间戳
    private static final String REQUEST_PRODUCT_ID = "product_id";//产品id
    private static final String REQUEST_EDIT_LIST = "edit_list";//用户修改的数据列表
    private static final String REQUEST_PLOT_ID = "plot_id";//剧情理念详情id
    private static final String REQUEST_PAGE = "page";//页码
    private static final String TYPE = "type";//创造类型
    private static final String ID = "id";//创造id
    private static final String ACTION = "action";//操作类型
    private static final String CREATE_ID = "create_id";//创造id
    private static final String COMMENT_SOURCE = "comment_source";//创造评论id
    private static final String COMMENT_ID = "comment_id";//创造评论id
    private static final String CONTENT = "content";//评论内容

//    private static final String LIMIT = "limit";//首页数据条目数量
//    private static final String ARTICLE_ID = "article_id";//首页文章ID
//    private static final String COMMENT_ID = "comment_id";//首页评论ID
//    private static final String MESSAGE = "message";//首页评论内容
//    private static final String COOL_TYPE = "cool_type";//赞类型


    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api-bbs-ng-test.start.wang" : "http://api-bbs-ng-test.start.wang";//基础url

    public static void install(String base_url) {
//        BASE_URL = base_url;
    }


    /**
     * 创造列表
     */
    public static final String URL_CREATE_LISTS = BASE_URL + "/create/create/lists";

    /**
     * 创造列表
     *
     * @param page 页数
     */
    public static Request getCreateList(int type, int page) {
        return PTWDRequestHelper.start()
                .addParam(TYPE, type + "")
                .addParam(REQUEST_PAGE, page + "")
                .build(RequestMethod.POST, URL_CREATE_LISTS);
    }

    /**
     * 我的关注
     */
    public static final String URL_CREATE_MYFOLLOWS = BASE_URL + "/create/create/myFollows";

    /**
     * 我的关注
     *
     * @param page 页数
     */
    public static Request getCreateMyfollows(int page) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_PAGE, page + "")
                .build(RequestMethod.POST, URL_CREATE_MYFOLLOWS);
    }

    /**
     * 操作
     */
    public static final String URL_CREATE_ACTION = BASE_URL + "/create/create/action";

    /**
     * 操作
     *
     * @param action 操作类型
     */
    public static Request setCreateAction(String id, int action) {
        return PTWDRequestHelper.start()
                .addParam(ID, id)
                .addParam(ACTION, action + "")
                .build(RequestMethod.POST, URL_CREATE_ACTION);
    }

    /**
     * 评论列表
     */
    public static final String URL_COMMENT_LIST = BASE_URL + "/create/comment/lists";

    /**
     * 评论列表
     */
    public static Request getCommentList(int page, String id) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_PAGE, page + "")
                .addParam(ID, id)
                .build(RequestMethod.POST, URL_COMMENT_LIST);
    }

    /**
     * 添加评论
     */
    public static final String URL_COMMENT_ADD = BASE_URL + "/create/comment/add";

    /**
     * 回复评论
     */
    public static Request addComment(String content, String create_id, String comment_source) {
        return PTWDRequestHelper.start()
                .addParam(CREATE_ID, create_id)
                .addParam(COMMENT_SOURCE, comment_source)
                .addParam(CONTENT, content)
                .build(RequestMethod.POST, URL_COMMENT_ADD);
    }

    /**
     * 回复文章
     */
    public static Request addComment(String content,String create_id) {
        return PTWDRequestHelper.start()
                .addParam(CREATE_ID, create_id)
                .addParam(COMMENT_SOURCE, "0")
                .addParam(CONTENT, content)
                .build(RequestMethod.POST, URL_COMMENT_ADD);
    }

    /**
     * 删除评论
     */
    public static final String URL_COMMENT_DELETE = BASE_URL + "/create/comment/delete";

    /**
     * 删除评论
     */
    public static Request deleteComment(String comment_id) {
        return PTWDRequestHelper.start()
                .addParam(COMMENT_ID, comment_id)
                .build(RequestMethod.POST, URL_COMMENT_DELETE);
    }

    /**
     * 赞评论
     */
    public static final String URL_COMMENT_LIKE = BASE_URL + "/create/comment/like";

    /**
     * 删除评论
     */
    public static Request addCommentLike(String create_id, String comment_id) {
        return PTWDRequestHelper.start()
                .addParam(CREATE_ID, create_id)
                .addParam(COMMENT_ID, comment_id)
                .build(RequestMethod.POST, URL_COMMENT_LIKE);
    }

    /**
     * 删除赞
     */
    public static final String URL_COMMENT_CANCEL_LIKE = BASE_URL + "/create/comment/cancelLike";

    /**
     * 删除评论
     */
    public static Request cancelLike(String comment_id) {
        return PTWDRequestHelper.start()
                .addParam(COMMENT_ID, comment_id)
                .build(RequestMethod.POST, URL_COMMENT_CANCEL_LIKE);
    }
}
