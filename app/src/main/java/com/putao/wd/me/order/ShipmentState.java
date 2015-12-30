package com.putao.wd.me.order;

/**
 * 售后状态
 * Created by yanguoqiang on 15/12/2.
 */
public final class ShipmentState {


    //----------商品配送情况---------//

    /**
     *  在途中
     */
    public static final int EXPRESS_ONROAD = 0;

    /**
     * 已揽收
     */
    public static final int EXPRESS_TAKING = 1;

    /**
     * 疑难
     */
    public static final int EXPRESS_PROBLEMS = 2;

    /**
     * 已签收
     */
    public static final int EXPRESS_RECEIVED= 3;

    /**
     * 退签
     */
    public static final int ORDER_SIGNOUT  = 4  ;

    /**
     * 同城派送中
     */
    public static final int ORDER_DELIVERING= 5;

    /**
     * 退回
     */
    public static final int ORDER_BACK = 6;

    /**
     * 转单
     */
    public static final int ORDER_CONVERT = 7;


    /**
     * 根据订单的状态来获取相应的显示文字
     *
     * @param status
     * @return
     */
    public static String getExpressStatusShowString(int status) {
        switch (status) {
            case EXPRESS_ONROAD:
                return "在途中";
            case EXPRESS_TAKING:
                return "已揽收";
            case EXPRESS_PROBLEMS:
                return "疑难";
            case EXPRESS_RECEIVED:
                return "已签收";
            case ORDER_SIGNOUT:
                return "退签";
            case ORDER_DELIVERING:
                return "同城派送中";
            case ORDER_BACK:
                return "退回";
            case ORDER_CONVERT:
                return "转单";
        }
        return "";
    }
}
