package com.putao.wd.base;

import com.putao.wd.GlobalApplication;
import com.putao.wd.account.AccountHelper;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.util.AppUtils;

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
    public static final String REQUEST_KEY_START_DEVICE_ID1 = "device_id";
    public static final String REQUEST_KEY_START_DEVICE_NAME = "device_name";


    /**
     * 封装固定请求参数(葡商城使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder store() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, GlobalApplication.app_id)
                .addParam(PTWDRequestHelper.REQUEST_KEY_START_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()));
    }

    /**
     * 封装固定请求参数(葡商城使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder start() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_START_DEVICE_ID1, AppUtils.getDeviceId(GlobalApplication.getInstance()))
                .addParam(PTWDRequestHelper.REQUEST_KEY_START_DEVICE_NAME, AppUtils.getDeviceName())
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, GlobalApplication.app_id);
    }

    /**
     * 封装固定请求参数(购物车使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder shopCar() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, GlobalApplication.app_id);
    }

    /**
     * 封装固定请求参数(探索号使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder explore() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
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
                .addParam(REQUEST_KEY_UID, AccountHelper.getCurrentUid());
    }

    /**
     * 封装固定请求参数(用户使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder user() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_START_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, GlobalApplication.app_id);
    }

    /**
     * 封装固定请求参数(发现视频使用)
     */
    public static FormEncodingRequestBuilder find() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, GlobalApplication.app_id);
    }
}
