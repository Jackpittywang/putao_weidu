package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.RequestMethod;

/**
 * Created by Administrator on 2016/4/7.
 */
public class DisCoveryApi {
    private static final String DISCOVERY_PAGE = "pages";//页码

    //    公网线上地址：api-weidu.putao.com ； admin-weidu.putao.com
//    内网测试地址：api-weidu.ptdev.cn ; admin-weidu.ptdev.cn
    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api-weidu.ptdev.cn/" : "http://api-weidu.putao.com/";//基础url

    /**
     * 视频发现
     */
    public static final String URL_VIDEO_FIND = BASE_URL + "find";

    /**
     * 视频发现
     */
    public static Request getfindVideo(int page) {
        return PTWDRequestHelper.find()
                .addParam(DISCOVERY_PAGE, page + "")
                .build(RequestMethod.GET, URL_VIDEO_FIND);
    }
}
