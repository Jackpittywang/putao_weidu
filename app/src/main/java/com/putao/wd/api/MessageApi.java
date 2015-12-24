package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.RequestMethod;

/**
 * Created by yanghx on 2015/12/24.
 */
public class MessageApi {

    private static final String REQUEST_EXT = "ext";
    private static final String REQUEST_FILENAME = "file_name";
    private static final String REQUEST_FILEHASH = "hash";

    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api.weidu.start.wang/message/center" : "http://api-weidu.putao.com/";//基础url


//    /**
//     * 获取通知消息
//     */
//    public static final String URL_MESSAGE = BASE_URL + "user/edit";
//
//    public static Request getMessage(String nick_name, String profile, String ext, String filename, String filehash) {
//        return PTWDRequestHelper.explore()
//                .addParam(REQUEST_NICK_NAME, nick_name)
//                .addParam(REQUEST_PROFILE, profile)
//                .addParam(REQUEST_EXT, ext)
//                .addParam(REQUEST_FILENAME, filename)
//                .addParam(REQUEST_FILEHASH, filehash)
//                .build(RequestMethod.POST, URL_MESSAGE);
//    }







}
