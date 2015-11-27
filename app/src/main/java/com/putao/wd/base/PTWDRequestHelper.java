package com.putao.wd.base;

import com.putao.wd.GlobalApplication;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.util.PreferenceUtils;

/**
 * 继承固定请求参数
 * Created by guchenkai on 2015/11/26.
 */
public class PTWDRequestHelper {
    //===================request key================================
    public static final String REQUEST_KEY_UID = "uid";
    public static final String REQUEST_KEY_TOKEN = "token";

    /**
     * 封装固定请求参数
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder start() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_UID, ""))
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_TOKEN, ""));
    }
}
