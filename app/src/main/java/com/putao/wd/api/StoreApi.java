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
    private static final String REQUEST_PRODUCT_PID = "pid";//产品id(添加购物车时用）
    private static final String REQUEST_QT = "qt";//数量
    private static final String REQUEST_OLD_PID = "old_pid";//旧商品id
    private static final String REQUEST_NEW_PID = "new_pid";//新商品id

    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api.store.start.wang/" : "http://api.sotre.putao.com/";//基础url

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
                .build(RequestMethod.POST, URL_PRODUCT_DETAIL);
    }

    /**
     * 商品规格
     */
    public static final String URL_PRODUCT_SPEC = BASE_URL + "product/spec";

    /**
     * 商品规格
     *
     * @param product_id 产品id
     */
    public static Request getProductSpce(String product_id) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_PRODUCT_ID, product_id)
                .build(RequestMethod.GET, URL_PRODUCT_SPEC);
    }

    /**
     * 查看购物车
     */
    public static final String URL_VIEW_CART = BASE_URL + "cart/view";

    /**
     * 查看购物车
     */
    public static Request getCart() {
        return PTWDRequestHelper.store()
                .build(RequestMethod.GET, URL_VIEW_CART);
    }

    /**
     * 添加购物车
     */
    public static final String URL_CART_ADD = BASE_URL + "cart/add";

    /**
     * 添加购物车
     *
     * @param product_id 产品id
     * @param qt         数量(默认是1)
     */
    public static Request cartAdd(String product_id, String qt) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_PRODUCT_PID, product_id)
                .addParam(REQUEST_QT, qt)
                .build(RequestMethod.POST, URL_CART_ADD);
    }

    /**
     * 编辑购物车
     */
    public static final String URL_CART_EDIT = BASE_URL + "cart/edit";

    /**
     * 编辑购物车
     *
     * @param product_id 产品id
     * @param qt         数量(默认是1)
     */
    public static Request cartEdit(String product_id, String qt) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_PRODUCT_ID, product_id)
                .addParam(REQUEST_QT, qt)
                .build(RequestMethod.POST, URL_CART_EDIT);
    }

    /**
     * 删除购物车
     */
    public static final String URL_CART_DELETE = BASE_URL + "cart/del";

    /**
     * 删除购物车
     *
     * @param product_id 产品id
     * @param qt         数量(默认是1)
     */
    public static Request cartDelete(String product_id, String qt) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_PRODUCT_ID, product_id)
                .addParam(REQUEST_QT, qt)
                .build(RequestMethod.POST, URL_CART_DELETE);
    }

    /**
     * 更改商品规格购物车
     */
    public static final String URL_CART_CHANGE = BASE_URL + "cart/change";

    /**
     * 更改商品规格购物车
     *
     * @param old_pid 旧商品id
     * @param new_pid 新商品id
     */
    public static Request cartChange(String old_pid, String new_pid) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_OLD_PID, old_pid)
                .addParam(REQUEST_NEW_PID, new_pid)
                .build(RequestMethod.POST, URL_CART_CHANGE);
    }
}
