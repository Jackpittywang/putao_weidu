package com.putao.wd.api;

import com.putao.wd.GlobalApplication;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.base.PTWDRequestHelper;
import com.squareup.okhttp.Request;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.model.http.request.RequestMethod;
import com.sunnybear.library.util.StringUtils;

/**
 * 订单接口
 * Created by guchenkai on 2015/12/9.
 */
public class OrderApi {
    public static final String TYPE_ALL = "0";//全部
    public static final String TYPE_WAIT_PAY = "1";//待付款
    public static final String TYPE_WAIT_SEND = "2";//待发货
    public static final String TYPE_WAIT_RECEIVER = "3";//待发货

    private static final String REQUEST_TYPE = "type";//订单状态
    private static final String REQUEST_PAGE = "page";//数据分页

    private static final String REQUEST_ORDER_ID = "order_id";//订单id
    private static final String REQUEST_ID = "id";//订单id
    private static final String REQUEST_REALNAME = "realname";//收货人姓名
    private static final String REQUEST_CITY_ID = "city_id";//市ID
    private static final String REQUEST_PROVINCE_ID = "province_id";//省ID
    private static final String REQUEST_AREA_ID = "area_id";//区ID
    private static final String REQUEST_ADDRESS = "address";//地址
    private static final String REQUEST_MOBILE = "mobile";//手机
    private static final String REQUEST_TEL = "tel";//电话
    private static final String REQUEST_POSTCODE = "postcode";//邮编
    private static final String REQUEST_STATUS = "status";//是否为默认地址
    private static final String REQUEST_ADDRESS_ID = "id";//收货地址id

    public static final String STATUS = "status";//待发货
    public static final String SERVICE_TYPE = "service_type";//待发货
    public static final String ADDRESSID = "addressId";//待发货
    public static final String ALL_PRODUCT_ID = "all_product_id";//待发货
    public static final String PRODUCT_DATA = "product_data";//待发货

    public static final String REQUEST_EXPRESS_NAME = "express_name";//快递公司
    public static final String REQUEST_EXPRESS_CODE = "express_code";//快递单号

    private static final String BASE_URL = GlobalApplication.isDebug ? "http://api-weidu-store.start.wang/" : "http://api-store.putao.com/";//基础url

    public static void install(String base_url) {
//        BASE_URL = base_url;
    }

    /**
     * 订单列表
     */
    public static final String URL_ORDER_LIST = BASE_URL + "order/lists";

    /**
     * 订单
     *
     * @param type 订单分类
     * @param page 数据分页
     */
    public static Request getOrderLists(String type, String page) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_TYPE, type)
                .addParam(REQUEST_PAGE, page)
                .build(RequestMethod.POST, URL_ORDER_LIST);
    }

    /**
     * 取消订单
     */
    public static final String URL_ORDER_CANCEL = BASE_URL + "order/cancel";

    /**
     * 取消订单
     *
     * @param order_id 订单ID
     */
    public static Request orderCancel(String order_id) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_ORDER_ID, order_id)
                .build(RequestMethod.POST, URL_ORDER_CANCEL);
    }

    /**
     * 订单详情
     */
    public static final String URL_ORDER_DETAIL = BASE_URL + "order/detail";

    /**
     * 订单详情
     *
     * @param order_id 订单ID
     */
    public static Request getOrderDetail(String order_id) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_ID, order_id)
                .build(RequestMethod.POST, URL_ORDER_DETAIL);
    }

    /**
     * 收货地址列表
     */
    public static final String URL_ADDRESS_LIST = BASE_URL + "address/lists";

    /**
     * 收货地址列表
     */
    public static Request getAddressLists() {
        return PTWDRequestHelper.store()
                .build(RequestMethod.POST, URL_ADDRESS_LIST);
    }

    /**
     * 添加收货地址
     */
    public static final String URL_ADDRESS_ADD = BASE_URL + "address/add";

    /**
     * 添加收货地址
     *
     * @param realname    收货人姓名
     * @param city_id     市ID
     * @param province_id 省ID
     * @param area_id     区ID
     * @param address     地址
     * @param mobile      手机
     * @param tel         电话
     * @param postcode    邮编
     * @param status      是否为默认地址
     */
    public static Request addressAdd(String realname, String city_id, String province_id, String area_id, String address, String mobile, String tel, String postcode, String status) {
        FormEncodingRequestBuilder builder = PTWDRequestHelper.store()
                .addParam(REQUEST_REALNAME, realname)
                .addParam(REQUEST_CITY_ID, city_id)
                .addParam(REQUEST_PROVINCE_ID, province_id)
                .addParam(REQUEST_AREA_ID, area_id)
                .addParam(REQUEST_ADDRESS, address);
        if (!StringUtils.isEmpty(mobile))
            builder.addParam(REQUEST_MOBILE, mobile);
        if (!StringUtils.isEmpty(tel))
            builder.addParam(REQUEST_TEL, tel);
        if (!StringUtils.isEmpty(postcode))
            builder.addParam(REQUEST_POSTCODE, postcode);
        if (!StringUtils.isEmpty(status))
            builder.addParam(REQUEST_STATUS, status);
        return builder.build(RequestMethod.POST, URL_ADDRESS_ADD);
    }

    /**
     * 删除收货地址
     */
    public static final String URL_ADDRESS_DELETE = BASE_URL + "address/del";

    /**
     * 删除收货地址
     *
     * @param address_id 收货地址id
     */
    public static Request addressDelete(String address_id) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_ADDRESS_ID, address_id)
                .build(RequestMethod.POST, URL_ADDRESS_DELETE);
    }

    /**
     * 更新收货地址
     */
    public static final String URL_ADDRESS_UPDATE = BASE_URL + "address/update";

    /**
     * 更新收货地址
     *
     * @param address_id  收货地址id
     * @param realname    收货人姓名
     * @param city_id     市ID
     * @param province_id 省ID
     * @param area_id     区ID
     * @param address     地址
     * @param mobile      手机
     * @param tel         电话
     * @param postcode    邮编
     * @param status      是否为默认地址
     */
    public static Request addressUpdate(String address_id, String realname, String city_id, String province_id, String area_id, String address, String mobile, String tel, String postcode, String status) {
        FormEncodingRequestBuilder builder = PTWDRequestHelper.store()
                .addParam(REQUEST_ADDRESS_ID, address_id)
                .addParam(REQUEST_REALNAME, realname)
                .addParam(REQUEST_CITY_ID, city_id)
                .addParam(REQUEST_PROVINCE_ID, province_id)
                .addParam(REQUEST_AREA_ID, area_id)
                .addParam(REQUEST_ADDRESS, address);
        if (!StringUtils.isEmpty(mobile))
            builder.addParam(REQUEST_MOBILE, mobile);
        if (!StringUtils.isEmpty(tel))
            builder.addParam(REQUEST_TEL, tel);
        if (!StringUtils.isEmpty(postcode))
            builder.addParam(REQUEST_POSTCODE, postcode);
        if (!StringUtils.isEmpty(status))
            builder.addParam(REQUEST_STATUS, status);
        return builder.build(RequestMethod.POST, URL_ADDRESS_UPDATE);
    }

    /**
     * 订单物流信息
     */
    public static final String URL_EXPRESS_ORDER = BASE_URL + "express/order";

    /**
     * 订单物流信息
     *
     * @param order_id 订单ID
     */
    public static Request getExpressOrder(String order_id) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_ORDER_ID, order_id)
                .build(RequestMethod.POST, URL_EXPRESS_ORDER);
    }

    /**
     * 获得订单数量
     */
    public static final String URL_ORDER_COUNT = BASE_URL + "order/order/getOrderCount";

    /**
     * 获得订单数量
     */
    public static Request getOrderCount() {
        return PTWDRequestHelper.user()
                .build(RequestMethod.POST, URL_ORDER_COUNT);
    }

    /**
     * 申请售后
     */
    public static final String URL_AFTER_SALE = BASE_URL + "service/apply";

    /**
     * 申请售后
     *
     * @param order_id 订单ID
     */
    public static Request orderAfterSale(String order_id) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_ORDER_ID, order_id)
                .build(RequestMethod.POST, URL_AFTER_SALE);
    }

    /**
     * 取消售后
     */
    public static final String URL_CANCEL_SALE = BASE_URL + "service/cancel";

    /**
     * 取消售后
     */
    public static Request cancelService(String serviceId) {
//        FormEncodingRequestBuilder builder = FormEncodingRequestBuilder.newInstance();
//        builder.addParam(PTWDRequestHelper.REQUEST_KEY_UID, "60000417")
//                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
//                .addParam(REQUEST_ID, serviceId);
//        return builder.build(RequestMethod.POST, URL_CANCEL_SALE);
        return PTWDRequestHelper.store()
                .addParam(REQUEST_ID, serviceId)
                .build(RequestMethod.POST, URL_CANCEL_SALE);
    }

    /**
     * 提交售后
     */
    public static final String URL_SUBMIT_AFTER_SALE = BASE_URL + "service/doApply";

    /**
     * 提交售后
     *
     * @param order_id 订单ID
     */
    public static Request orderSubmitAfterSale(String order_id, String service_type, String addressId, String all_product_id, String product_data) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_ORDER_ID, order_id)
                .addParam(SERVICE_TYPE, service_type)
                .addParam(ADDRESSID, addressId)
                .addParam(ALL_PRODUCT_ID, all_product_id)
                .addParam(PRODUCT_DATA, product_data)
                .addParam(STATUS, "1")
                .build(RequestMethod.POST, URL_SUBMIT_AFTER_SALE);
    }

    /**
     * 售后列表
     */
    public static final String URL_SERVICE_LIST = BASE_URL + "service/lists";

    /**
     * 售后列表
     *
     * @param page 取第几页
     */
    public static Request getServiceLists(String page) {
//        FormEncodingRequestBuilder builder = FormEncodingRequestBuilder.newInstance();
//        builder.addParam(PTWDRequestHelper.REQUEST_KEY_UID, "60000417")
//                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
//                .addParam(REQUEST_PAGE, page);
//        return builder.build(RequestMethod.POST, URL_SERVICE_LIST);
        return PTWDRequestHelper.store()
                .addParam(REQUEST_PAGE, page)
                .build(RequestMethod.POST, URL_SERVICE_LIST);
    }

    /**
     * 售后详情
     */
    public static final String URL_SERVICE_DETAIL = BASE_URL + "service/detail";

    /**
     * 售后详情
     */
    public static Request getServiceDetail(String id) {
//        FormEncodingRequestBuilder builder = FormEncodingRequestBuilder.newInstance();
//        builder.addParam(PTWDRequestHelper.REQUEST_KEY_UID, "60000417")
//                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
//                .addParam(REQUEST_ID, id);
//        return builder.build(RequestMethod.GET, URL_SERVICE_DETAIL);
        return PTWDRequestHelper.store()
                .addParam(REQUEST_ID, id)
                .build(RequestMethod.POST, URL_SERVICE_DETAIL);
    }

    /**
     * 填写快递单号
     */
    public static final String URL_FILL_EXPRESS = BASE_URL + "service/writeExpress";

    /**
     * 填写快递单号
     *
     * @param service_id   售后ID
     * @param express_type 快递公司type
     * @param express_code 快递单号
     */
    public static Request fillExpress(String service_id, String express_type, String express_code) {
        return PTWDRequestHelper.store()
                .addParam(REQUEST_ID, service_id)
                .addParam(REQUEST_EXPRESS_NAME, express_type)
                .addParam(REQUEST_EXPRESS_CODE, express_code)
                .build(RequestMethod.POST, URL_FILL_EXPRESS);
    }

    /**
     * 售后物流信息
     */
    @Deprecated
    public static final String URL_EXPRESS_SERVICE = BASE_URL + "express/service";

    /**
     * 售后物流信息
     *
     * @param order_id 订单ID
     */
    @Deprecated
    public static Request getExpressService(String order_id) {
//        FormEncodingRequestBuilder builder = FormEncodingRequestBuilder.newInstance();
//        builder.addParam(PTWDRequestHelper.REQUEST_KEY_UID, "60000417")
//                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
//                .addParam(REQUEST_ORDER_ID, order_id);
//        return builder.build(RequestMethod.GET, URL_EXPRESS_SERVICE);
        return PTWDRequestHelper.store()
                .addParam(REQUEST_ORDER_ID, order_id)
                .build(RequestMethod.POST, URL_EXPRESS_SERVICE);
    }

}
