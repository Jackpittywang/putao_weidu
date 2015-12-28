package com.putao.wd.me.order;

/**
 * 售后状态
 * Created by yanguoqiang on 15/12/2.
 */
public final class OrderCommonState {


    //----------商品配送情况---------//

    /**
     * 待发货
     */
    public static final int ORDER_WAITING_SHIPMENT = 1;

    /**
     * 已发货
     */
    public static final int ORDER_WAITING_SIGN = 2;

    /**
     * 已收货
     */
    public static final int ORDER_SALE_SERVICE = 3;

    /**
     * 退签
     */
    public static final int ORDER_NO_SIGN = 4;

    /**
     *已生成发货单
     */
    public static final int ORDER_FORM_FINISH= 5;

    /**
     *疑难
     */
    public static final int ORDER_PROBLEM= 5;

    /**
     *退回
     */
    public static final int ORDER_BACK= 6;

    /**
     * 关闭
     */
   /* public static final int ORDER_CLOSED = 5;

    *//**
     * 取消
     *//*
    public static final int ORDER_CANCLED = 6;*/


    //----------支付状态---------//
    /**
     * 待支付
     */
    public static final int PAY_WAIT = 0;
    /**
     * 已付款
     */
    public static final int PAY_FINISH= 1;
    /**
     * 待支付
     */
    public static final int PAY_REFUND= 2;
    /**
     * 部分款
     */
    public static final int PAY_PART = 3;

    /**
     * 根据订单的状态来获取相应的显示文字
     *
     * @param status
     * @return
     */
  /*  public static String getOrderStatusShowString(int status) {
        switch (status) {
            case ORDER_WAITING_PAY:
                return "等付款";
            case ORDER_WAITING_SHIPMENT:
                return "待发货";
            case ORDER_WAITING_SIGN:
                return "待收货";
            case ORDER_SALE_SERVICE:
                return "售后";
            case ORDER_CLOSED:
                return "已关闭";
            case ORDER_CANCLED:
                return "取消";
            case ORDER_NO_SIGN:
                return "退签";
        }
        return "";
    }*/
}
