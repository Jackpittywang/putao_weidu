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
    public static final String REQUEST_KEY_DEVICE_ID = "masterSlaveDeviceId";
    public static final String REQUEST_KEY_APP_ID = "appid";

    /**
     * 封装固定请求参数(不加登录参数)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder start() {
        return start(false);
    }

    /**
     * 封装固定请求参数
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder start(boolean isAddLoginParams) {
        FormEncodingRequestBuilder builder = FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, GlobalApplication.app_id);
        if (isAddLoginParams)
            builder.addParam(PTWDRequestHelper.REQUEST_KEY_UID, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_UID, ""))
                    .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, PreferenceUtils.getValue(GlobalApplication.PREFERENCE_KEY_TOKEN, ""));
        return builder;
    }
}
