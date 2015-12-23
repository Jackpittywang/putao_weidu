package com.putao.wd.me.service;

/**
 * Created by wangou on 15/12/2.
 */
public class ServiceCommon {

    /**
     * 退款请求审核中
     */
    public static final int SERVICE_REFUND_CHECK = 1;
    /**
     * 退货已结束
     */
    public static final int SERVICE_REFUND_OVER = 2;

    /**
     * 同意退货请求
     */
    public static final int SERVICE_REFUND_AGREE = 3;

    /**
     * 您已发货
     */
    public static final int SERVICE_REFUND_SEND = 4;

    /**
     * 退货已收到
     */
    public static final int SERVICE_REFUND_RECEIVE = 5;

    /**
     * 退货已完成
     */
    public static final int SERVICE_REFUND_FINISH = 6;

    /**
     * 换货请求审核中
     */
    public static final int SERVICE_EXCHANGE_CHECK = 11;

    /**
     * 换货已结束
     */
    public static final int SERVICE_EXCHANGE_OVER = 12;

    /**
     * 同意换货请求
     */
    public static final int SERVICE_EXCHANGE_AGREE = 13;

    /**
     * 您已发货
     */
    public static final int SERVICE_EXCHANGE_SEND = 14;

    /**
     * 换货商品已收到
     */
    public static final int SERVICE_EXCHANGE_RECEIVE = 15;

    /**
     * 换货寄回中
     */
    public static final int SERVICE_EXCHANGE_BACK = 16;

    /**
     * 换货已完成
     */
    public static final int SERVICE_EXCHANGE_FINISH = 17;

    /**
     * 退款请求审核中
     */
    public static final int SERVICE_DRAWBACK_CHECK = 21;

    /**
     * 退款已结束
     */
    public static final int SERVICE_DRAWBACK_OVER = 22;

    /**
     * 退款已成功
     */
    public static final int SERVICE_DRAWBACK_FINISH = 23;


    /**
     * 根据订单的状态来获取相应的显示文字
     *
     * @param status
     * @return
     */
    public static String getServiceStatusShowString(int status) {
        switch (status) {
            case SERVICE_REFUND_CHECK: {
                return "退货请求审核中";
            }
            case SERVICE_REFUND_OVER: {
                return "退货已结束";
            }
            case SERVICE_REFUND_AGREE: {
                return "同意请求";
            }
            case SERVICE_REFUND_SEND: {
                return "您已发货";
            }
            case SERVICE_REFUND_RECEIVE: {
                return "退货已收到";
            }
            case SERVICE_REFUND_FINISH: {
                return "退货已完成";
            }
            case SERVICE_EXCHANGE_CHECK: {
                return "换货请求审核中";
            }
            case SERVICE_EXCHANGE_OVER: {
                return "换货已结束";
            }
            case SERVICE_EXCHANGE_AGREE: {
                return "同意换货请求";
            }
            case SERVICE_EXCHANGE_SEND: {
                return "您已发货";
            }
            case SERVICE_EXCHANGE_RECEIVE: {
                return "换货商品已收到";
            }
            case SERVICE_EXCHANGE_BACK: {
                return "换货寄回中";
            }
            case SERVICE_EXCHANGE_FINISH: {
                return "换货已完成";
            }
            case SERVICE_DRAWBACK_CHECK: {
                return "退款请求审核中";
            }
            case SERVICE_DRAWBACK_OVER: {
                return "退款已结束";
            }
            case SERVICE_DRAWBACK_FINISH: {
                return "退款已成功";
            }
            default: {
                return "";
            }
        }

    }
}
