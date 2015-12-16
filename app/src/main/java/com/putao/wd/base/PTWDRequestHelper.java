package com.putao.wd.base;

import com.putao.wd.GlobalApplication;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.util.AppUtils;
import com.sunnybear.library.util.PreferenceUtils;

/**
 * 继承固定请求参数
 * Created by guchenkai on 2015/11/26.
 */
public class PTWDRequestHelper {
    //===================request key================================
    public static final String REQUEST_KEY_UID = "uid";
    public static final String REQUEST_KEY_TOKEN = "token";
    public static final String REQUEST_KEY_DEVICE_ID = "master_device_id";
    public static final String REQUEST_KEY_APP_ID = "appid";

    public static final String REQUEST_KEY_START_DEVICE_ID = "deviceid";


    /**
     * 封装固定请求参数(葡商城使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder store() {

        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_UID, ""))
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_TOKEN, ""));

    }

    /**
     * 封装固定请求参数(探索号使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder explore() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_UID, ""))
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_TOKEN, ""))
                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, GlobalApplication.app_id);
    }

    /**
     * 封装固定请求参数(上传使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder upload() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(REQUEST_KEY_UID, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_UID, "60000277"));
    }
}
