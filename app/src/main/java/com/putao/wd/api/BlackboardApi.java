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

    private static final String COMPANION_URL = GlobalApplication.isDebug ? "http://api-weidu.ptdev.cn/" : "http://api-weidu.putao.com/";//基础url

    /**
     * 查询葡萄活动
     */
    public static final String URL_DIARY_DATA = COMPANION_URL + "diary/data";
    /**
     * 查询对应游戏下的葡萄活动
     */
    public static Request getDiaryData(int page) {
        return PTWDRequestHelper.explore()
                .addParam(REQUEST_PAGE, page + "")
                .build(RequestMethod.POST, URL_DIARY_DATA);
    }
}
