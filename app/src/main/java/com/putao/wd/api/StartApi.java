package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.model.http.request.RequestMethod;

/**
 * 葡星圈接口
 * Created by guchenkai on 2015/12/3.
 */
public class StartApi {
    public static final String BASE_URL = GlobalApplication.isDebug ? "http://api.event.start.wang/" : "http://api.event.start.wang/";

    public static void install() {
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


}
