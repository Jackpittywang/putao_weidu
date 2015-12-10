package com.putao.wd.me.service;

/**
 * Created by wangou on 15/12/2.
 */
public class ServiceCommon {

    /**
     * 待付款
     */
    public static final int ORDER_WAITING_PAY = 1;
    /**
     * 待发货
     */
    public static final int ORDER_WAITING_SHIPMENT = 2;

    /**
     * 待收货
     */
    public static final int ORDER_WAITING_SIGN = 3;

    /**
     * 售后
     */
    public static final int ORDER_SALE_SERVICE = 4;

    /**
     * 关闭
     */
    public static final int ORDER_CLOSED = 5;

    /**
     * 取消
     */
    public static final int ORDER_CANCLED = 6;


    /**
     * 退签
     */
    public static final int ORDER_NO_SIGN = 7;

    /**
     * 根据订单的状态来获取相应的显示文字
     *
     * @param status
     * @return
     */
    public static String getOrderStatusShowString(int status) {
        switch (status) {
            case ORDER_WAITING_PAY: {
                return "等付款";
            }
            case ORDER_WAITING_SHIPMENT: {
                return "待发货";
            }
            case ORDER_WAITING_SIGN: {
                return "待收货";
            }
            case ORDER_SALE_SERVICE: {
                return "售后";
            }
            case ORDER_CLOSED: {
                return "已关闭";
            }
            case ORDER_CANCLED: {
                return "取消";
            }
            case ORDER_NO_SIGN: {
                return "退签";
            }
            default: {
                return "";
            }


        }

    }

}
