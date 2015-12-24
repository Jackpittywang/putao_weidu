package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.model.http.request.RequestMethod;

/**
 * Created by yanghx on 2015/12/24.
 */
public class MessageApi {

    private static final String REQUEST_PAGE = "page";

    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api.weidu.start.wang/message/center" : "http://api-weidu.putao.com/";//基础url


    /**
     * 获取通知消息
     */
    public static final String URL_MESSAGE = BASE_URL + "user/edit";

    /**
     * 获取通知消息
     */
    public static Request getMessage(String page) {
        FormEncodingRequestBuilder builder = FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, "60000265")
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
//                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, AppUtils.getDeviceId(GlobalApplication.getInstance()))
//                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, GlobalApplication.app_id)
                .addParam(REQUEST_PAGE, page);
        return builder.build(RequestMethod.POST, URL_MESSAGE);
    }


}
