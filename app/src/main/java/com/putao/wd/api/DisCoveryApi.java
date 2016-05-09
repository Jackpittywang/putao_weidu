package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.RequestMethod;

/**
 * Created by Administrator on 2016/4/7.
 */
public class DisCoveryApi {
    private static final String DISCOVERY_PAGE = "page";//页码
    private static final String TAG_ID = "tag_id";//标签tag_id

    //    公网线上地址：api-weidu.putao.com ； admin-weidu.putao.com
//    内网测试地址：api-weidu.ptdev.cn ; admin-weidu.ptdev.cn
    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api-weidu.ptdev.cn/" : "http://api-wd.putao.com/";//基础url

    /**
     * 视频发现
     */
    public static final String URL_VIDEO_FIND = BASE_URL + "find";

    /**
     * 找资源
     */
    public static final String URL_RESOURCE_FIND = BASE_URL + "resources/resources";

    /**
     * 顶部banner（轮播图）
     */
    public static final String URL_RESOURCE_BANNER = BASE_URL + "resources/banner";

    /**
     * 热门标签
     */
    public static final String URL_RESOURCE_HOT_TAG = BASE_URL + "resources/tag";

    /**
     * 资源头部（TOP）
     */
    private static final String URL_RESOURCE_TOP = BASE_URL + "resources/top";


    /**
     * 视频发现
     */
    public static Request getfindVideo(String page) {
        return PTWDRequestHelper.find()
                .addParam(DISCOVERY_PAGE, page)
                .build(RequestMethod.POST, URL_VIDEO_FIND);
    }

    /**
     * 找资源
     *
     * @param page
     * @return
     */
    public static Request getFindResource(String page) {
        return PTWDRequestHelper.find()
                .addParam(DISCOVERY_PAGE, page)
                .build(RequestMethod.POST, URL_RESOURCE_FIND);
    }

    /**
     * 轮播图
     *
     * @return
     */
    public static Request getResourceBanner() {
        return PTWDRequestHelper.find()
                .build(RequestMethod.POST, URL_RESOURCE_BANNER);
    }

    /**
     * 热门标签
     *
     * @return
     */
    public static Request getHotTag() {
        return PTWDRequestHelper.find()
                .build(RequestMethod.POST, URL_RESOURCE_HOT_TAG);
    }

    public static Request getResouceTop() {
        return PTWDRequestHelper.find()
                .build(RequestMethod.POST, URL_RESOURCE_TOP);
    }
//    public static Request getHotTag(){
//
//    }


    /**
     * 获取tag相关文章列表(活动列表)
     */
    private static final String URL_DISCOVERY_TAG_RESOURCES = BASE_URL + "resources/tag/resources";

    /**
     * 获取tag相关文章列表（活动列表）
     */
    public static Request getTagResources(String tagId) {
        return PTWDRequestHelper.find()
                .addParam(TAG_ID, tagId)
                .build(RequestMethod.POST, URL_DISCOVERY_TAG_RESOURCES);
    }

}
