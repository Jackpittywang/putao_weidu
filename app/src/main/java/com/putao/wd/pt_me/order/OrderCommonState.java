package com.putao.wd.pt_me.order;

/**
 * 售后状态
 * Created by yanguoqiang on 15/12/2.
 */
public final class OrderCommonState {


    //----------商品配送情况---------//

    /**
     * 待支付
     */
    public static final int ORDER_PAY_WAIT = 1;

    /**
     * 待发货
     */
    public static final int ORDER_WAITING_SHIPMENT = 2;

    /**
     * 正在准备商品中
     */
    public static final int ORDER_GOOD_PREPARE = 3;

    /**
     * 已发货
     */
    public static final int ORDER_WAITING_SIGN = 4;

    /**
     * 已收货
     */
    public static final int ORDER_SALE_SERVICE = 5  ;

    /**
     * 已完成
     */
    public static final int ORDER_FINISH= 6;

    /**
     * 订单被取消
     */
    public static final int ORDER_CANCLED = 7;

    /**
     * 订单正在申请售后
     */
    public static final int ORDER_AFTER_SALE = 8;

    /**
     * 退款成功
     */
    public static final int ORDER_REFUND_FINISH = 9;

    /**
     * 订单异常
     */
    public static final int ORDER_EXCEPTION= 10;



    /**
     * 根据订单的状态来获取相应的显示文字
     *
     * @param status
     * @return
     */
    public static String getOrderStatusShowString(int status) {
        switch (status) {
            case ORDER_PAY_WAIT:
                return "待支付";
            case ORDER_WAITING_SHIPMENT:
                return "待发货";
            case ORDER_GOOD_PREPARE:
                return "正在准备商品中";
            case ORDER_WAITING_SIGN:
                return "已发货";
            case ORDER_SALE_SERVICE:
                return "已签收";
            case ORDER_FINISH:
                return "已完成";
            case ORDER_CANCLED:
                return "已取消";
            case ORDER_AFTER_SALE:
                return "此订单正在申请售后";
            case ORDER_REFUND_FINISH:
                return "退款成功";
            case ORDER_EXCEPTION:
                return "订单异常，请联系客服";
        }
        return "";
    }
}
