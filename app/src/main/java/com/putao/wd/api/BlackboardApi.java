package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.RequestMethod;

/**
 * Created by Administrator on 2016/4/6.
 */
public class BlackboardApi {
    private static final String REQUEST_PAGE = "page";//页码
    private static final String UID = "uid";//用户id
    private static final String TOKEN = "token";//登入后的标志 token

    private static final String COMPANION_URL = GlobalApplication.isDebug ? "http://api-weidu.ptdev.cn/" : "http://api-weidu.putao.com/";//基础url

    /**
     * 查询葡萄活动
     */
    public static final String URL_DIARY_DATA = COMPANION_URL + "company/blackboard";
    /**
     * 查询对应游戏下的葡萄活动
     */
    public static Request getCreateList(int page) {
        return PTWDRequestHelper.explore()
                .addParam(REQUEST_PAGE, page + "")
                .build(RequestMethod.GET, URL_DIARY_DATA);
    }

//    /**
//     * 创造列表
//     *
//     * @param page 页数
//     */
//    public static Request getCreateList(int type, int page) {
//        return PTWDRequestHelper.start()
//                .addParam(TYPE, type + "")
//                .addParam(REQUEST_PAGE, page + "")
//                .build(RequestMethod.GET, URL_CREATE_LISTS);
//    }
}
