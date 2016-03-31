package com.putao.wd.api;

import com.alibaba.fastjson.JSON;
import com.putao.wd.GlobalApplication;
import com.putao.wd.base.PTWDRequestHelper;
import com.putao.wd.model.CartEdit;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.RequestMethod;

import java.util.List;

/**
 * 葡商城接口
 * Created by guchenkai on 2015/12/8.
 */
public class StoreApi {
    private static final String REQUEST_PRODUCT_ID = "id";//产品id
    private static final String REQUEST_TYPE = "type";//购买方式--1立即/2购物车
    private static final String REQUEST_PRODUCT_PID = "pid";//产品id(添加购物车时用）
    private static final String REQUEST_QT = "qt";//数量
    private static final String REQUEST_OLD_PID = "old_pid";//旧商品id
    private static final String REQUEST_NEW_PID = "new_pid";//新商品id
    private static final String REQUEST_PRODUCTS = "products";//产品(批量编辑购物车用）
    private static final String REQUEST_PAGE = "page";
    //发票相关
    private static final String REQUEST_iNVOICE_ID = "id";//发票ID
    private static final String REQUEST_INVOICE_TYPE = "type";//旧商品id
    private static final String REQUEST_INVOICE_CONTENT = "Content";//发票内容
    private static final String REQUEST_INVOICE_TITLE = "title";//发票抬头
    //订单提交 "N"非必传
    private static final String REQUEST_ORDER_ADDRESS_ID = "address_id";//地址id
    private static final String REQUEST_ORDER_NEED_INVOICE = "need_invoice";//是否需要发票--1需要/0不需要
    private static final String REQUEST_ORDER_INVOICE_TYPE = "invoice_type";//发票抬头类型--1个人/2公司    N
    private static final String REQUEST_ORDER_INVOICE_TITLE = "invoice_title";//发票抬头--个人/公司名      N
    private static final String REQUEST_ORDER_INVOICE_CONTENT = "invoice_content";//发票类型--1商品明细/2电子产品/3玩具   N
    private static final String REQUEST_ORDER_CONSIGNEE = "consignee";//收货人
    private static final String REQUEST_ORDER_MOBILE = "mobile";//手机
    private static final String REQUEST_ORDER_TEL = "tel";//固话   N
    private static final String REQUEST_ORDER_ID = "order_id";//订单id
    private static final String REQUEST_PAYMENT_TYPE = "payment_type";//微信支付时的支付方式

    //    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api.store.start.wang/" : "http://api.sotre.putao.com/";//基础url
    //商城请求所使用的预发布域名   //api-weidu-store.start.wang
    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api.store.test.putao.com/" : "http://api-store.putao.com/";//基础url

    public static void install(String base_url) {
//        BASE_URL = base_url;
    }

    /**
     * 商城首页
     */
    public static final String URL_STORE_HOME = BASE_URL + "home/index_v2";
//    public static final String URL_STORE_HOME = BASE_URL + "home/index";

    /**
     * 商城首页
     */
    public static Request getStoreHome(String page) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_PAGE, page)
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
                .addParam(REQUEST_PRODUCT_PID, product_id)
                .build(RequestMethod.POST, URL_PRODUCT_DETAIL);
    }

    /**
     * 商品详情html+pager
    */
    public static Request getProductDetailPager(String product_id) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_PRODUCT_PID, product_id)
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
                .build(RequestMethod.POST, URL_PRODUCT_SPEC);
    }

    /**
     * 查看购物车
     */
    public static final String URL_VIEW_CART = BASE_URL + "cart/view";

    /**
     * 查看购物车
     */
    public static Request getCart() {
        return PTWDRequestHelper.shopCar()
                .build(RequestMethod.POST, URL_VIEW_CART);
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
        return PTWDRequestHelper.shopCar()
                .addParam(REQUEST_PRODUCT_PID, product_id)
                .addParam(REQUEST_QT, qt)
                .build(RequestMethod.POST, URL_CART_ADD);
    }

    /**
     * 编辑购物车
     */
    public static final String URL_CART_EDIT = BASE_URL + "cart/multiManage";

    /**
     * 编辑购物车
     *
     * @param products 产品list
     */
    public static Request multiManage(List<CartEdit> products) {
        return PTWDRequestHelper.shopCar()
                .addParam(REQUEST_PRODUCTS, JSON.toJSONString(products))
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
     */
    public static Request cartDelete(String product_id) {
        return PTWDRequestHelper.shopCar()
                .addParam(REQUEST_PRODUCT_PID, product_id)
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
        return PTWDRequestHelper.shopCar()
                .addParam(REQUEST_OLD_PID, old_pid)
                .addParam(REQUEST_NEW_PID, new_pid)
                .build(RequestMethod.POST, URL_CART_CHANGE);
    }

    /**
     * 购物车数量
     */
    public static final String URL_CART_COUNT = BASE_URL + "cart/qt";

    /**
     * 购物车数量
     */
    public static Request getCartCount() {
        return PTWDRequestHelper.store()
                .build(RequestMethod.POST, URL_CART_COUNT);
    }

    /**
     * 发票列表
     */
    public static final String URL_INVOICE_LIST = BASE_URL + " invoices/list";

    /**
     * 发票列表
     */
    public static Request InvoiceList() {
        return PTWDRequestHelper.shopCar()
                .build(RequestMethod.POST, URL_INVOICE_LIST);
    }

    /**
     * 编辑发票
     */
    public static final String URL_EDIT_INVOICE = BASE_URL + " invoices/edit";

    /**
     * 编辑发票
     *
     * @param invoice_id      发票的id
     * @param invoice_type    旧商品id
     * @param invoice_content 发票内容
     * @param invoice_title   发票抬头
     */
    public static Request editInvoice(String invoice_id, String invoice_type, String invoice_content, String invoice_title) {
        return PTWDRequestHelper.shopCar()
                .addParam(REQUEST_iNVOICE_ID, invoice_id)
                .addParam(REQUEST_INVOICE_TYPE, invoice_type)
                .addParam(REQUEST_INVOICE_CONTENT, invoice_content)
                .addParam(REQUEST_INVOICE_TITLE, invoice_title)
                .build(RequestMethod.POST, URL_EDIT_INVOICE);
    }

    /**
     * 删除发票
     */
    public static final String URL_DELETE_INVOICE = BASE_URL + " invoices/delete";

    /**
     * 删除发票
     *
     * @param invoice_id 发票的id
     */
    public static Request deleteInvoice(String invoice_id) {
        return PTWDRequestHelper.shopCar()
                .addParam(REQUEST_iNVOICE_ID, invoice_id)
                .build(RequestMethod.POST, URL_DELETE_INVOICE);
    }

    /**
     * 订单确认
     */
    public static final String URL_ORDER_CONFIRM = BASE_URL + "order/confirm";

    /**
     * 订单确认
     *
     * @param type 购买方式--"1"立即购买，"2"购物车购买
     * @param pid  商品id
     */
    public static Request orderConfirm(String type, String pid, String qt) {
        return PTWDRequestHelper.shopCar()
                .addParam(REQUEST_TYPE, type)
                .addParam(REQUEST_PRODUCT_PID, pid)
                .addParam(REQUEST_QT, qt)
                .build(RequestMethod.POST, URL_ORDER_CONFIRM);
    }

    /**
     * 订单提交
     */
    public static final String URL_ORDER_SUBMIT = BASE_URL + "order/save";

    /**
     * 订单提交
     * N非必传
     *
     * @param type            购买方式--"1"立即购买，"2"购物车购买
     * @param pid             商品id
     * @param qt              商品数量    N
     * @param address_id      地址id
     * @param need_invoice    是否需要发票--1需要/0不需要
     * @param invoice_type    发票抬头类型--1个人/2公司    N
     * @param invoice_title   发票抬头--个人/公司名        N
     * @param invoice_content 发票类型--1商品明细/2电子产品/3玩具   N
     * @param consignee       收货人
     * @param mobile          手机
     * @param tel             固话   N
     * @return
     */
    public static Request orderSubmit(String type, String pid, String qt, String address_id, String need_invoice, String invoice_type,
                                      String invoice_title, String invoice_content, String consignee, String mobile, String tel) {
        return PTWDRequestHelper.shopCar()
                .addParam(REQUEST_INVOICE_TYPE, type)
                .addParam(REQUEST_PRODUCT_PID, pid)
                .addParam(REQUEST_QT, qt)
                .addParam(REQUEST_ORDER_ADDRESS_ID, address_id)
                .addParam(REQUEST_ORDER_NEED_INVOICE, need_invoice)
                .addParam(REQUEST_ORDER_INVOICE_TYPE, invoice_type)
                .addParam(REQUEST_ORDER_INVOICE_TITLE, invoice_title)
                .addParam(REQUEST_ORDER_INVOICE_CONTENT, invoice_content)
                .addParam(REQUEST_ORDER_CONSIGNEE, consignee)
                .addParam(REQUEST_ORDER_MOBILE, mobile)
//                .addParam(REQUEST_ORDER_TEL, tel)
                .build(RequestMethod.POST, URL_ORDER_SUBMIT);
    }

    /**
     * 支付宝支付
     */
    public static final String URL_ALIPAY = BASE_URL + "pay/mobile/order";

    /**
     * 支付宝支付
     *
     * @param order_id 订单id
     */
    public static Request aliPay(String order_id) {
        return PTWDRequestHelper.shopCar()
                .addParam(REQUEST_ORDER_ID, order_id)
                .addParam(REQUEST_ORDER_ID, order_id)
                .build(RequestMethod.POST, URL_ALIPAY);
    }

    /**
     * 微信支付
     */
    public static final String WEIXIN_PAY = BASE_URL + "pay/mobile/toPay";

    /**
     * 微信支付
     *
     * @param order_id 订单id
     */
    public static Request weixPay(String order_id) {
        return PTWDRequestHelper.shopCar()
                .addParam(REQUEST_ORDER_ID, order_id)
                .addParam(REQUEST_PAYMENT_TYPE, "WX_APP")
                .build(RequestMethod.POST, WEIXIN_PAY);
    }

    /**
     * 获取默认地址
     */
    public static final String URL_DEFAULT_ADDRESS = BASE_URL + "address/getDefault";

    /**
     * 获取默认地址
     */
    public static Request getDefaultAddress() {
        return PTWDRequestHelper.store()
                .build(RequestMethod.POST, URL_DEFAULT_ADDRESS);
    }

    /**
     * 获取详情页面
     */
    public static final String URL_PRODUCT_VIEW_V2 = BASE_URL + "product/view_v2";

    /**
     * 获取详情页面
     */
    public static Request getHTML(String pid) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_PRODUCT_PID, pid)
                .build(RequestMethod.POST, URL_PRODUCT_VIEW_V2);
    }

    /**
     * 获取商品是否下架(跳转到精品还是非精品)
     */
    public static final String URL_PRODUCT_STATUS = BASE_URL + "product/status";

    /**
     * 获取商品是否下架
     */
    public static Request getProductStatus(String pid) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_PRODUCT_PID, pid)
                .build(RequestMethod.POST, URL_PRODUCT_STATUS);
    }
}
