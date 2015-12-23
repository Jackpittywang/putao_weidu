package com.putao.wd.api;

import com.putao.wd.util.ScanUrlParseUtils;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.model.http.request.RequestMethod;

import java.util.Map;

/**
 * 扫描Api
 * Created by guchenkai on 2015/12/23.
 */
public class ScanApi {

    /**
     * 扫描登录官网
     *
     * @param url url
     */
    public static Request scanLogin(String url) {
        return FormEncodingRequestBuilder.newInstance()
                .build(RequestMethod.GET, url);
    }

    /**
     * 确认登录
     *
     * @param url url
     */
    public static Request confirmLogin(String url) {
        FormEncodingRequestBuilder builder = FormEncodingRequestBuilder.newInstance();
        Map<String, String> params = ScanUrlParseUtils.getParams(url);
        for (String key : params.keySet()) {
            builder.addParam(key, params.get(key));
        }
        return builder.build(RequestMethod.POST, url);
    }
}
