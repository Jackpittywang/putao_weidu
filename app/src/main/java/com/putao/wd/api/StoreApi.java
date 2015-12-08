package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.RequestMethod;

/**
 * 葡商城接口
 * Created by guchenkai on 2015/12/8.
 */
public class StoreApi {
    private static final String REQUEST_PRODUCT_ID = "id";//产品id

    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api.sotre.putao.com/" : "http://api.sotre.putao.com/";//基础url

    public static void install(String base_url) {
//        BASE_URL = base_url;
    }

    /**
     * 商城首页
     */
    public static final String URL_STORE_HOME = BASE_URL + "home/index";

    /**
     * 商城首页
     */
    public static Request getStoreHome() {
        return PTWDRequestHelper.store()
                .build(RequestMethod.POST, URL_STORE_HOME);
    }

    /**
     * 商品详情
     */
    public static final String URL_PRODUCT_DETAIL = BASE_URL + "product/view";

    /**
     * 商品详情
     *
     * @param product_id 产品id
     */
    public static Request getProductDetail(String product_id) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_PRODUCT_ID, product_id)
                .build(RequestMethod.GET, URL_PRODUCT_DETAIL);
    }
}
