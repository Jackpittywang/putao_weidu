package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.RequestMethod;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ParticipationApi {
    private static final String REGISTER_PAGE = "pages";//页码

    //    公网线上地址：api-weidu.putao.com ； admin-weidu.putao.com
//    内网测试地址：api-weidu.ptdev.cn ; admin-weidu.ptdev.cn
    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api-weidu.ptdev.cn/" : "http://api-weidu.putao.com/";//基础url

    /**
     * 我的参与
     */
    public static final String BASE_URL_PART = BASE_URL + "user/attends";

    /**
     * 我的参与查询
     */
    public static Request getQueryPart(int page) {
        return PTWDRequestHelper.find()
                .addParam(REGISTER_PAGE, page + "")
                .build(RequestMethod.GET, BASE_URL_PART);
    }

}
